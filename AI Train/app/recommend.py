import pickle
import re
import pandas as pd
import numpy as np
from sklearn.preprocessing import normalize
from sklearn.metrics.pairwise import cosine_similarity

from train import load_data, preprocess_data


# 추천 함수
def recommend_champs(df_svd_preds, user_id, champ_data, score_data, user_index, tier):
    
    # index와 user_id를 맞추는 부분. 현재는 index와 user_id 둘 다 0부터 시작하므로 변화 x.
    user_row_number = user_index
    
    # 최종적으로 만든 pred_df에서 사용자 index에 따라 챔피언 데이터 정렬 -> 챔피언 평점이 높은 순으로 정렬 됨
    sorted_user_predictions = df_svd_preds.iloc[user_row_number].sort_values(ascending=False)

    # 원본 평점 데이터에서 user id에 해당하는 데이터를 뽑아낸다. 
    user_data = score_data[score_data.user_id == user_id]
    
    # 위에서 뽑은 user_data와 원본 챔피언 데이터를 합친다.
    user_history = pd.merge(user_data, champ_data, left_on='champion', right_on='id', how='inner').sort_values(['score'], ascending=False)
    user_history.drop(columns=['name'], inplace=True)

    # user_history = user_data.merge(champ_data, on = 'id')
    # user_history = pd.merge(user_data, champ_data, left_on = 'champion', right_on='id', how='inner')# .sort_values(['score'], ascending=False)
    # 원본 챔피언 데이터에서 사용자가 본 챔피언 데이터를 제외한 데이터를 추출
    recommendations = champ_data[~champ_data['id'].isin(user_history['champion'])]

    # 사용자의 챔피언 평점이 높은 순으로 정렬된 데이터와 위 recommendations을 합친다. 
    recommendations = pd.merge(recommendations, sorted_user_predictions, left_on='id', right_on='champion', how='inner')
    # recommendations = recommendations.merge( pd.DataFrame(sorted_user_predictions).reset_index(), on = 'id')
    # 컬럼 이름 바꾸고 정렬해서 return
    
    recommendations = recommendations.rename(columns = {user_row_number: 'Predictions'}).sort_values('Predictions', ascending = False).iloc[:20, :]

    recommendations = tier_process(recommendations, tier)

    return recommendations

def recommend_champs_not(df_svd_preds, user_id, champ_data, score_data, user_index, tier, champions_list):
    # index와 user_id를 맞추는 부분. 현재는 index와 user_id 둘 다 0부터 시작하므로 변화 x.
    user_row_number = user_index
    
    # 최종적으로 만든 pred_df에서 사용자 index에 따라 챔피언 데이터 정렬 -> 챔피언 평점이 높은 순으로 정렬 됨
    sorted_user_predictions = df_svd_preds.iloc[user_row_number].sort_values(ascending=False)
    
    recommendations = champ_data[~champ_data['id'].isin(champions_list)]

    recommendations = pd.merge(recommendations, sorted_user_predictions, left_on='id', right_on='champion', how='inner')
    # recommendations = recommendations.merge( pd.DataFrame(sorted_user_predictions).reset_index(), on = 'id')
    # 컬럼 이름 바꾸고 정렬해서 return
    
    recommendations = recommendations.rename(columns = {user_row_number: 'Predictions'}).sort_values('Predictions', ascending = False).iloc[:20, :]

    recommendations = tier_process(recommendations, tier)

    return recommendations

def tier_process(recommendations, tier):
    if tier in ['IRON', 'BRONZE', 'SILVER']:
        conditions = [
            recommendations['difficulty'] >= 9,
            recommendations['difficulty'] >= 7,
            recommendations['difficulty'] >= 5,
            recommendations['difficulty'] >= 4
        ] 
        values = [
            recommendations['Predictions'] * 0.5,
            recommendations['Predictions'] * 0.6,
            recommendations['Predictions'] * 0.8,
            recommendations['Predictions'] * 1.0
        ]
        default = recommendations['Predictions'] * 1.3
    
    elif tier in ['GOLD', 'PLATINUM']:
        conditions = [
                recommendations['difficulty'] >= 8,
                recommendations['difficulty'] >= 7
            ] 
        values = [
                recommendations['Predictions'] * 0.7,
                recommendations['Predictions'] * 0.8
            ]
        default = recommendations['Predictions'] * 1.0
    elif tier in ['EMERALD', 'DIAMOND']:
        conditions = [
                recommendations['difficulty'] >= 9
            ] 
        values = [
                recommendations['Predictions'] * 0.8
            ]
        default = recommendations['Predictions'] * 1.0
    elif tier in ['UNRANKED']:
        conditions = [
                recommendations['difficulty'] >= 9
            ] 
        values = [
                recommendations['Predictions'] * 1.0
            ]
        default = recommendations['Predictions'] * 1.0
    else:
        conditions = [
                recommendations['difficulty'] >= 9,
                recommendations['difficulty'] >= 8
            ] 
        values = [
                recommendations['Predictions'] * 1.3,
                recommendations['Predictions'] * 1.2
            ]
        default = recommendations['Predictions'] * 1.0

    recommendations['Predictions'] = np.select(conditions, values, default=default)
    recommendations = recommendations.sort_values('Predictions', ascending = False).iloc[:3, :]
    return recommendations

def find_userId(player):
    
    if not player.mostDatas:
        return "데이터가 존재하지 않습니다"

    new_df = pd.DataFrame(0, index=[1], columns=df_svd_preds.columns)

    for champion_data in player.mostDatas:
        
        if not champion_data.game:
            continue  # 'game' 키가 없으면 이 챔피언 데이터를 건너뜀
        
        # 게임 데이터에서 총 게임 수와 승리율 추출
        game_data = champion_data.game
        total_games, win_rate = process_game_data(game_data)
        score = total_games * win_rate / 100
        new_df.at[1, champion_data.champion] = score if score == 0 else 0.01

    new_user_matrix = new_df.values[0]

    # 정규화
    # 유저의 평균 Score(영화의 별점)을 각 유저의 score에서 감산
    user_scores_mean = np.mean(new_user_matrix, axis = 0)

    matrix_user_mean = new_user_matrix - user_scores_mean.reshape(-1, 1)
    
    norm_new_user_vector = normalize(matrix_user_mean, axis=1, norm='l2')

    # 코사인 유사도 계산
    cosine_sim = cosine_similarity(norm_new_user_vector, norm_matrix_user_mean)
    pd.options.display.max_columns = 167
    
    # 가장 유사한 사용자 찾기
    most_similar_user_index = np.argmax(cosine_sim)
    
    most_similar_user_id = user_champ_score.index[most_similar_user_index]
    
    return most_similar_user_id, most_similar_user_index

def process_game_data(game_data):
    # 정규 표현식을 사용하여 승리 수, 패배 수, 승리율을 추출합니다.
    # 이 패턴은 "승리수W패배수L승리율%" 형식을 처리할 수 있으며,
    # 승리 수나 패배 수 중 하나만 존재하는 경우에도 작동합니다.
    match = re.match(r'(\d+)W(\d+)L(\d+)%', game_data)
    if not match:
        # "승리수W패배수L승리율%" 형식이 아닌 경우, 다른 패턴으로 시도합니다.
        match = re.match(r'(\d+)(W|L)(\d+)%', game_data)
        if not match:
            return 0, 0

    if 'W' in game_data:
        wins = int(match.group(1))
        losses = int(match.group(2)) if 'L' in game_data else 0
    else:
        wins = 0
        losses = int(match.group(1))
    
    win_rate = int(match.group(3))
    total_games = wins + losses

    return total_games, win_rate

new_user_ratings = {'Aatrox': 13.8, 'Jayce': 6.88, 'Gwen': 5.88}
score_data, champ_data = load_data()

with open('models/df_svd_preds.pkl', 'rb') as f:
    df_svd_preds = pickle.load(f)
with open('models/norm_matrix_user_mean.pkl', 'rb') as f:
    norm_matrix_user_mean = pickle.load(f)
with open('models/user_champ_score.pkl', 'rb') as f:
    user_champ_score = pickle.load(f)

def get_recommendations(user_id, user_index, tier):
    predictions = recommend_champs(df_svd_preds, user_id, champ_data, score_data, user_index, tier)
    return predictions

def get_recommendations_not(user_id, user_index, tier, champions_list):
    predictions = recommend_champs_not(df_svd_preds, user_id, champ_data, score_data, user_index, tier, champions_list)
    return predictions