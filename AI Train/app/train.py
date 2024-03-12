from sklearn.decomposition import TruncatedSVD
from scipy.sparse.linalg import svds
import pickle

import pandas as pd
import numpy as np
import warnings
warnings.filterwarnings("ignore")

# 데이터 불러오기
def load_data():
    # result = get_player_prev_solo_rank()
    # score_data = pd.json_normalize(result)
    score_data = pd.read_csv('data/score_data.csv')
    champ_data = pd.read_csv('data/champ_data.csv')
    return score_data, champ_data

# 데이터 전처리
def preprocess_data(score_data, champ_data):
    # 피벗테이블 형태로 만들기
    user_champ_score = score_data.pivot(
        index='user_id',
        columns='champion',
        values='score'
    ).fillna(0)

    # champ_user_score = user_champ_score.T.to_numpy()

    matrix = user_champ_score.to_numpy()

    # 정규화
    # 유저의 평균 Score(영화의 별점)을 각 유저의 score에서 감산
    user_scores_mean = np.mean(matrix, axis = 1)

    matrix_user_mean = matrix - user_scores_mean.reshape(-1, 1)

    # svd(특이값 분해) 모델링 부분
    U, sigma, Vt = svds(matrix_user_mean, k = 150)
    # sigma는 0이 아닌 특이값의 나열(1차원 행렬)으로(sigma.shape (12,)), 0이 포함된 대칭행렬을 사용하기 위해 np.diag 적용(12,12)
    sigma = np.diag(sigma)

    print(sigma.shape)

    # U, Sigma, Vt의 내적을 수행하면, 다시 원본 행렬로 복원이 된다. 
    # 거기에 + 사용자 평균 rating을 적용한다.
     
    # svd_user_predicted_scores = np.dot(np.dot(U, sigma), Vt) + user_scores_mean.reshape(-1, 1)
    svd_user_predicted_scores = np.dot(np.dot(U, sigma), Vt)
    # df_svd_preds = pd.DataFrame(svd_user_predicted_scores, columns = user_champ_score.columns)
    return pd.DataFrame(svd_user_predicted_scores, columns = user_champ_score.columns), Vt, user_scores_mean

if __name__ == "__main__":
    score_data, champ_data = load_data()
    df_svd_preds, Vt, user_scores_mean = preprocess_data(score_data, champ_data)
    # 데이터를 pickle 파일로 저장
    print(df_svd_preds)
    file_path1 = 'models/df_svd_preds.pkl'
    with open(file_path1, 'wb') as f:
        pickle.dump(df_svd_preds, f)

    file_path2 = 'models/Vt.pkl'
    with open(file_path2, 'wb') as f:
        pickle.dump(Vt, f)

    file_path3 = 'models/user_scores_mean.pkl'
    with open(file_path3, 'wb') as f:
        pickle.dump(user_scores_mean, f)