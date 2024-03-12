import pickle

import pandas as pd
import numpy as np

from train import load_data


# 추천 함수
def recommend_champs(df_svd_preds, user_id, champ_data, score_data, num_recommendations=10):
    
    # index와 user_id를 맞추는 부분. 현재는 index와 user_id 둘 다 0부터 시작하므로 변화 x.
    user_row_number = user_id 
    
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
    recommendations = recommendations.rename(columns = {user_row_number: 'Predictions'}).sort_values('Predictions', ascending = False).iloc[:num_recommendations, :]
                      

    return user_history, recommendations

def recommend_champions_not_exist(df_svd_preds, new_user_ratings, Vt, user_scores_mean, champ_data):
    
    # 배열 하나 생성
    new_user_vector = np.zeros(Vt.shape[1])
    
    # 챔피언에 해당하는 점수를 가진 배열로 만듬
    for champ, score in new_user_ratings.items():
        if champ in champ_data['id'].values:
            champ_idx = champ_data[champ_data['id'] == champ].index[0]
            new_user_vector[champ_idx] = score

    # 평균 내고 원본에서 빼는 정규화 과정
    tempmatrix_mean = np.mean(new_user_vector)
    tempmatrix_adjusted = new_user_vector - tempmatrix_mean

    # 기존 정규화 배열 마지막 행에 붙임
    tempmatrix_adjusted_df = pd.DataFrame(tempmatrix_adjusted.reshape(1, -1), columns=df_svd_preds.columns)
    new_matrix_user_mean = pd.concat([df_svd_preds, tempmatrix_adjusted_df], ignore_index=True)
    # Vt 행렬 역행렬 내적
    new_user_features = np.dot(new_matrix_user_mean, np.linalg.pinv(Vt))
    # 다시 Vt 행렬 내적
    predicted_scores = np.dot(new_user_features, Vt)

    predicted_scores_df = pd.DataFrame(predicted_scores, columns = df_svd_preds.columns)

    # 새 사용자에 대한 예측 점수 선택
    new_user_predicted_scores = predicted_scores_df.iloc[-1]

    # 이미 평가한 챔피언의 개수 계산
    num_rated_champions = len(new_user_ratings)

    # 추천할 챔피언의 총 개수 설정 (이미 평가한 챔피언 수 + 10)
    num_recommendations = num_rated_champions + 5

    # 상위 N개의 추천 챔피언의 인덱스를 얻음
    top_indices = np.argsort(new_user_predicted_scores.values)[-num_recommendations:]  # .values를 추가해 numpy 배열로 변환

    # 상위 N개 추천의 챔피언 이름과 예측 점수를 얻음
    top_champions_with_scores = [(new_user_predicted_scores.index[i], new_user_predicted_scores.iloc[i]) for i in top_indices]

    # 이미 평가한 챔피언을 제외하고 추천 목록을 생성
    # 각 항목을 딕셔너리 형태로 변환
    unrated_top_champions_dicts = [{"champion": champion, "score": score} 
                                for champion, score in top_champions_with_scores 
                                if champion not in new_user_ratings.keys()]

    unrated_top_champions_dicts = sorted(unrated_top_champions_dicts, key=lambda x: x["score"], reverse=True)

    return unrated_top_champions_dicts

new_user_ratings = {'Yasuo': 100, 'Zed': 100, 'Akali': 100}
score_data, champ_data = load_data()

with open('models/df_svd_preds.pkl', 'rb') as f:
    df_svd_preds = pickle.load(f)
with open('models/Vt.pkl', 'rb') as f:
    Vt = pickle.load(f)
with open('models/user_scores_mean.pkl', 'rb') as f:
    user_scores_mean = pickle.load(f)

def get_recommendations(user_id):
    already_rated, predictions = recommend_champs(df_svd_preds, user_id, champ_data, score_data, 3)
    return predictions

def get_recommendations_not():
    unrated_top_champions = recommend_champions_not_exist(df_svd_preds, new_user_ratings, Vt, user_scores_mean, champ_data)
    return unrated_top_champions