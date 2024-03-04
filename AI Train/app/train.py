from sklearn.decomposition import TruncatedSVD
from scipy.sparse.linalg import svds
import pickle

import pandas as pd
import numpy as np
import warnings
warnings.filterwarnings("ignore")

import motor.motor_asyncio

# 데이터 불러오기
def load_data():
    score_data = pd.read_csv('data/score_data.csv')
    champ_data = pd.read_csv('data/champion_data.csv')
    return score_data, champ_data

# 데이터 전처리
def preprocess_data(score_data, champ_data):
    # 피벗테이블 형태로 만들기
    user_champ_score = score_data.pivot(
        index='userId',
        columns='champId',
        values='Score'
    ).fillna(0)

    # 
    # champ_user_score = user_champ_score.T.to_numpy()

    matrix = user_champ_score.to_numpy()

    # 정규화
    # 유저의 평균 Score(영화의 별점)을 각 유저의 score에서 감산
    user_scores_mean = np.mean(matrix, axis = 1)

    matrix_user_mean = matrix - user_scores_mean.reshape(-1, 1)

    # svd(특이값 분해) 모델링 부분
    U, sigma, Vt = svds(matrix_user_mean, k = 12)
    # sigma는 0이 아닌 특이값의 나열(1차원 행렬)으로(sigma.shape (12,)), 0이 포함된 대칭행렬을 사용하기 위해 np.diag 적용(12,12)
    sigma = np.diag(sigma)

    # U, Sigma, Vt의 내적을 수행하면, 다시 원본 행렬로 복원이 된다. 
    # 거기에 + 사용자 평균 rating을 적용한다. 
    svd_user_predicted_scores = np.dot(np.dot(U, sigma), Vt) + user_scores_mean.reshape(-1, 1)
    # df_svd_preds = pd.DataFrame(svd_user_predicted_scores, columns = user_champ_score.columns)
    return pd.DataFrame(svd_user_predicted_scores, columns = user_champ_score.columns)

# 추천 함수
def recommend_champs(df_svd_preds, user_id, ori_champs_df, ori_scores_df, num_recommendations=5):
    
    # index와 user_id를 맞추는 부분. 현재는 index와 user_id 둘 다 0부터 시작하므로 변화 x.
    user_row_number = user_id 
    
    # 최종적으로 만든 pred_df에서 사용자 index에 따라 챔피언 데이터 정렬 -> 챔피언 평점이 높은 순으로 정렬 됨
    sorted_user_predictions = df_svd_preds.iloc[user_row_number].sort_values(ascending=False)

    # 원본 평점 데이터에서 user id에 해당하는 데이터를 뽑아낸다. 
    user_data = ori_scores_df[ori_scores_df.userId == user_id]
    
    # 위에서 뽑은 user_data와 원본 챔피언 데이터를 합친다. 
    user_history = user_data.merge(ori_champs_df, on = 'champId').sort_values(['Score'], ascending=False)

    # 원본 챔피언 데이터에서 사용자가 본 챔피언 데이터를 제외한 데이터를 추출
    recommendations = ori_champs_df[~ori_champs_df['champId'].isin(user_history['champId'])]

    # 사용자의 챔피언 평점이 높은 순으로 정렬된 데이터와 위 recommendations을 합친다. 
    recommendations = recommendations.merge( pd.DataFrame(sorted_user_predictions).reset_index(), on = 'champId')
    # 컬럼 이름 바꾸고 정렬해서 return
    recommendations = recommendations.rename(columns = {user_row_number: 'Predictions'}).sort_values('Predictions', ascending = False).iloc[:num_recommendations, :]
                      

    return user_history, recommendations


if __name__ == "__main__":
    score_data, champ_data = load_data()
    df_svd_preds = preprocess_data(score_data, champ_data)
    # 데이터를 pickle 파일로 저장
    file_path = 'models/df_svd_preds.pkl'
    with open(file_path, 'wb') as f:
        pickle.dump(df_svd_preds, f)
    
