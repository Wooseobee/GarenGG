import sys, os
# sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname('code'))))
import pickle
import pandas as pd

from typing import Union
from fastapi import FastAPI, HTTPException
from asyncio import current_task
from pydantic import BaseModel

from pymongo import MongoClient, ReturnDocument
import motor.motor_asyncio

from code.recommend import get_recommendations

app = FastAPI()

# 닉네임
class PredictionInput(BaseModel):
    riotId: str

# MatrixFactorization or 학습된 모델 가져오기
with open('models/df_svd_preds.pkl', 'rb') as f:
    df_svd_preds = pickle.load(f)
# champio data
    
# MongoDB 연결 정보 설정
MONGODB_URL = "mongodb://root:ssafy605@15.164.142.18:27017"

# MongoDB 클라이언트 초기화
client = motor.motor_asyncio.AsyncIOMotorClient(MONGODB_URL)

# 데이터베이스 선택
db = client["a605"]

@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}

# MongoDB 연결 테스트 엔드포인트
@app.get("/mongodb-test")
async def mongodb_test():

    try:
        # 데이터베이스 목록을 가져옵니다.
        databases = await client.list_database_names()
        # 성공적으로 데이터베이스 목록을 가져왔다면, 연결이 성공적임을 의미합니다.
        return {"message": "MongoDB connection successful", "databases": databases}
    except Exception as e:
        # 연결에 실패한 경우, 오류 메시지를 반환합니다.
        raise HTTPException(status_code=500, detail=str(e))
    


@app.post("/predict/")
async def prediction(input_data: PredictionInput):
    user_id = int(input_data.riotId)  # riotId를 int로 변환
    predictions = get_recommendations(user_id)  # predict 함수 호출하여 결과 가져오기
    return predictions.to_dict(orient='records')  # DataFrame을 딕셔너리로 변환하여 반환

if __name__ == "__main__":
    pass