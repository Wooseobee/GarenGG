import sys, os
import pickle
import pandas as pd

from typing import Union, List, Optional
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

from recommend import get_recommendations, get_recommendations_not
from db import get_player_prev_solo_rank  # db.py에서 함수 가져오기
from train import load_data

app = FastAPI()

# class MostData(BaseModel):
#     champion: str
#     game: str
#     rating: str

# class PredictionInput(BaseModel):
#     _id: int
#     tier: str
#     rankNum: str
#     mostDatas: List[MostData]

class PredictionInput(BaseModel):
    riotId : str
    

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

@app.get("/predict/not")
async def prediction_not():
    unrated_top_champions_dicts = get_recommendations_not()
    return unrated_top_champions_dicts


@app.post("/predict/")
async def prediction(input_data: PredictionInput):
    user_id = int(input_data.riotId)  # riotId를 int로 변환
    predictions = get_recommendations(user_id)  # predict 함수 호출하여 결과 가져오기
    return predictions.to_dict(orient='records')  # DataFrame을 딕셔너리로 변환하여 반환

if __name__ == "__main__":
    pass
