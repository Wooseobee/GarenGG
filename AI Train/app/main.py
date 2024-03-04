import sys, os
# sys.path.append(os.path.dirname(os.path.abspath(os.path.dirname('code'))))
import pickle
import pandas as pd

from typing import Union
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel

from recommend import get_recommendations

app = FastAPI()

# 닉네임
class PredictionInput(BaseModel):
    riotId: str

# MatrixFactorization or 학습된 모델 가져오기
with open('models/df_svd_preds.pkl', 'rb') as f:
    df_svd_preds = pickle.load(f)
# champio data

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}

@app.post("/predict/")
async def prediction(input_data: PredictionInput):
    user_id = int(input_data.riotId)  # riotId를 int로 변환
    predictions = get_recommendations(user_id)  # predict 함수 호출하여 결과 가져오기
    return predictions.to_dict(orient='records')  # DataFrame을 딕셔너리로 변환하여 반환

if __name__ == "__main__":
    pass