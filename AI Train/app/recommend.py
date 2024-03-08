import pickle

import pandas as pd
import numpy as np

from train import load_data


# 추천 함수
def recommend_champs(df_svd_preds, user_id, ori_champs_df, ori_scores_df, num_recommendations=5):
    
    # index와 user_id를 맞추는 부분. 현재는 index와 user_id 둘 다 0부터 시작하므로 변화 x.
    user_row_number = user_id 
    
    # 최종적으로 만든 pred_df에서 사용자 index에 따라 챔피언 데이터 정렬 -> 챔피언 평점이 높은 순으로 정렬 됨
    sorted_user_predictions = df_svd_preds.iloc[user_row_number].sort_values(ascending=False)

    # 원본 평점 데이터에서 user id에 해당하는 데이터를 뽑아낸다. 
    user_data = ori_scores_df[ori_scores_df.user_id == user_id]
    
    # 위에서 뽑은 user_data와 원본 챔피언 데이터를 합친다. 
    user_history = user_data.merge(ori_champs_df, on = 'id').sort_values(['score'], ascending=False)

    # 원본 챔피언 데이터에서 사용자가 본 챔피언 데이터를 제외한 데이터를 추출
    recommendations = ori_champs_df[~ori_champs_df['id'].isin(user_history['id'])]

    # 사용자의 챔피언 평점이 높은 순으로 정렬된 데이터와 위 recommendations을 합친다. 
    recommendations = recommendations.merge( pd.DataFrame(sorted_user_predictions).reset_index(), on = 'id')
    # 컬럼 이름 바꾸고 정렬해서 return
    recommendations = recommendations.rename(columns = {user_row_number: 'Predictions'}).sort_values('Predictions', ascending = False).iloc[:num_recommendations, :]
                      

    return user_history, recommendations

score_data, champ_data = load_data()
with open('models/df_svd_preds.pkl', 'rb') as f:
    df_svd_preds = pickle.load(f)

def get_recommendations(user_id):
    already_rated, predictions = recommend_champs(df_svd_preds, user_id, champ_data, score_data, 3)
    return predictions
