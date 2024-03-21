import sys, os
import pickle
import pandas as pd

from typing import Union, List, Optional
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

from recommend import get_recommendations, find_userId
from db import get_player_prev_solo_rank  # db.py에서 함수 가져오기
from train import load_data

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

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}

# MongoDB 연결 테스트 엔드포인트
@app.get("/mongodb-test")
async def mongodb_test():
    result = await get_player_prev_solo_rank()  # db.py의 함수 사용
    return result

@app.get("/test")
async def test():
    score_data, champ_data = load_data()  # score_data와 champ_data 모두 로드
    # 두 DataFrame을 각각 딕셔너리로 변환
    score_data_dict = score_data.to_dict(orient='records')
    champ_data_dict = champ_data.to_dict(orient='records')
    # 두 딕셔너리를 하나의 응답 객체로 합쳐서 반환
    return {
        "champ_data": champ_data_dict
    }

@app.post("/predict/not")
async def prediction_not(input_data: PredictionInput_Not):
    most_similar_user_id, most_similar_user_index = find_userId(input_data)
    predictions = get_recommendations(most_similar_user_id, most_similar_user_index, input_data.tier)
    return predictions.to_dict(orient='records')


@app.post("/predict/")
async def prediction(input_data: PredictionInput):
    user_ids = user_champ_score.index.tolist()
    row_index = user_ids.index(input_data.riotId)
    predictions = get_recommendations(input_data.riotId, row_index, input_data.tier)  # predict 함수 호출하여 결과 가져오기
    return predictions.to_dict(orient='records')  # DataFrame을 딕셔너리로 변환하여 반환


with open('models/user_champ_score.pkl', 'rb') as f:
    user_champ_score = pickle.load(f)

if __name__ == "__main__":
    pass
