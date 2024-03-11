import pickle

import pandas as pd
import numpy as np

from train import load_data


# 추천 함수
def recommend_champs(df_svd_preds, user_id, champ_data, score_data, num_recommendations=5):
    
    # index와 user_id를 맞추는 부분. 현재는 index와 user_id 둘 다 0부터 시작하므로 변화 x.
    user_row_number = user_id 
    
    # 최종적으로 만든 pred_df에서 사용자 index에 따라 챔피언 데이터 정렬 -> 챔피언 평점이 높은 순으로 정렬 됨
    sorted_user_predictions = df_svd_preds.iloc[user_row_number].sort_values(ascending=False)

    # 원본 평점 데이터에서 user id에 해당하는 데이터를 뽑아낸다. 
    user_data = score_data[score_data.user_id == user_id]
    
    # 위에서 뽑은 user_data와 원본 챔피언 데이터를 합친다. 
    user_history = pd.merge(user_data, champ_data, left_on='champion', right_on='name', how='inner').sort_values(['score'], ascending=False)
    user_history.drop(columns=['id', 'name'], inplace=True)
    # user_history = user_data.merge(champ_data, on = 'id')
    # user_history = pd.merge(user_data, champ_data, left_on = 'champion', right_on='id', how='inner')# .sort_values(['score'], ascending=False)
    # 원본 챔피언 데이터에서 사용자가 본 챔피언 데이터를 제외한 데이터를 추출
    recommendations = champ_data[~champ_data['name'].isin(user_history['champion'])]


    # 사용자의 챔피언 평점이 높은 순으로 정렬된 데이터와 위 recommendations을 합친다. 
    recommendations = pd.merge(recommendations, sorted_user_predictions, left_on='name', right_on='champion', how='inner')
    # recommendations = recommendations.merge( pd.DataFrame(sorted_user_predictions).reset_index(), on = 'id')
    # 컬럼 이름 바꾸고 정렬해서 return
    recommendations = recommendations.rename(columns = {user_row_number: 'Predictions'}).sort_values('Predictions', ascending = False).iloc[:num_recommendations, :]
                      

    return user_history, recommendations

def recommend_champions_not_exist(new_user_ratings, Vt, user_scores_mean, matrix_user_mean):
    
    # 배열 하나 생성
    new_user_vector = np.zeros(Vt.shape[1])
    
    # 챔피언에 해당하는 점수를 가진 배열로 만듬
    for champ, score in new_user_ratings.items():
        if champ in champ_data['champion'].values:
            champ_idx = champ_data[champ_data['champion'] == champ].index[0]
            new_user_vector[champ_idx] = score

    # 평균 내고 원본에서 빼는 정규화 과정
    tempmatrix_mean = np.mean(new_user_vector)
    tempmatrix_adjusted = new_user_vector - tempmatrix_mean

    # 기존 정규화 배열 마지막 행에 붙임
    new_matrix_user_mean = np.vstack((matrix_user_mean, tempmatrix_adjusted))
    # Vt 행렬 역행렬 내적
    new_user_features = np.dot(new_matrix_user_mean, np.linalg.pinv(Vt))
    # 다시 Vt 행렬 내적
    predicted_scores = np.dot(new_user_features, Vt)

    column_names_actual = np.array([
    "Aatrox", "Ahri", "Akali", "Akshan", "Alistar", "Amumu", "Anivia", "Annie",
    "Aphelios", "Ashe", "AurelionSol", "Azir", "Bard", "Bel'Veth", "Blitzcrank",
    "Brand", "Braum", "Briar", "Caitlyn", "Camille", "Cassiopeia", "Cho'Gath",
    "Corki", "Darius", "Diana", "Dr.Mundo", "Draven", "Ekko", "Elise", "Evelynn",
    "Ezreal", "Fiddlesticks", "Fiora", "Fizz", "Galio", "Gangplank", "Garen",
    "Gnar", "Gragas", "Graves", "Gwen", "Hecarim", "Heimerdinger", "Hwei", "Illaoi",
    "Irelia", "Ivern", "Janna", "JarvanIV", "Jax", "Jayce", "Jhin", "Jinx", "K'Sante",
    "Kai'Sa", "Kalista", "Karma", "Karthus", "Kassadin", "Katarina", "Kayle", "Kayn",
    "Kennen", "Kha'Zix", "Kindred", "Kled", "Kog'Maw", "LeBlanc", "LeeSin", "Leona",
    "Lillia", "Lissandra", "Lucian", "Lulu", "Lux", "Malphite", "Malzahar", "Maokai",
    "MasterYi", "Milio", "MissFortune", "Mordekaiser", "Morgana", "Naafiri", "Nami",
    "Nasus", "Nautilus", "Neeko", "Nidalee", "Nilah", "Nocturne", "Nunu&Willump", "Olaf",
    "Orianna", "Ornn", "Pantheon", "Poppy", "Pyke", "Qiyana", "Quinn", "Rakan", "Rammus",
    "Rek'Sai", "Rell", "RenataGlasc", "Renekton", "Rengar", "Riven", "Rumble", "Ryze",
    "Samira", "Sejuani", "Senna", "Seraphine", "Sett", "Shaco", "Shen", "Shyvana",
    "Singed", "Sion", "Sivir", "Skarner", "Smolder", "Sona", "Soraka", "Swain", "Sylas",
    "Syndra", "TahmKench", "Taliyah", "Talon", "Taric", "Teemo", "Thresh", "Tristana",
    "Trundle", "Tryndamere", "TwistedFate", "Twitch", "Udyr", "Urgot", "Varus", "Vayne",
    "Veigar", "Vel'Koz", "Vex", "Vi", "Viego", "Viktor", "Vladimir", "Volibear", "Warwick",
    "Wukong", "Xayah", "Xerath", "XinZhao", "Yasuo", "Yone", "Yorick", "Yuumi", "Zac",
    "Zed", "Zeri", "Ziggs", "Zilean", "Zoe", "Zyra"
    ])

    last_row = predicted_scores[-1, :]
    top_5_indices = np.argsort(last_row)[-5:]
    top_5_column_names_actual = column_names_actual[top_5_indices]
    top_5_column_names_actual = top_5_column_names_actual[::-1]

    return top_5_column_names_actual

new_user_ratings = {'Ahri': 5, 'Zed': 4, 'Yasuo': 1}
score_data, champ_data = load_data()

with open('models/df_svd_preds.pkl', 'rb') as f:
    df_svd_preds = pickle.load(f)

def get_recommendations(user_id):
    already_rated, predictions = recommend_champs(df_svd_preds, user_id, champ_data, score_data, 3)
    return predictions
