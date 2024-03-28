import sys, os
import pickle
import pandas as pd

from typing import Union, List, Optional
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

from recommend import get_recommendations, find_userId

app = FastAPI()

class MostData(BaseModel):
    champion: str
    game: str
    mostSeq: str

class PredictionInput_Not(BaseModel):
    id: Optional[int] = Field(None, alias='_id')
    tier: str
    rankNum: str
    mostDatas: List[MostData]

class PredictionInput(BaseModel):
    tier : str
    riotId : int
    

# MatrixFactorization or 학습된 모델 가져오기
with open('models/df_svd_preds.pkl', 'rb') as f:
    df_svd_preds = pickle.load(f)

@app.post("/api/predict/not")
async def prediction_not(input_data: PredictionInput_Not):
    most_similar_user_id, most_similar_user_index = find_userId(input_data)
    predictions = get_recommendations(most_similar_user_id, most_similar_user_index, input_data.tier)
    return predictions.to_dict(orient='records')


@app.post("/api/predict")
async def prediction(input_data: PredictionInput):
    user_ids = user_champ_score.index.tolist()
    row_index = user_ids.index(input_data.riotId)
    predictions = get_recommendations(input_data.riotId, row_index, input_data.tier)  # predict 함수 호출하여 결과 가져오기
    return predictions.to_dict(orient='records')  # DataFrame을 딕셔너리로 변환하여 반환


with open('models/user_champ_score.pkl', 'rb') as f:
    user_champ_score = pickle.load(f)

if __name__ == "__main__":
    pass
