from pymongo import MongoClient
import motor.motor_asyncio

# MongoDB 연결 정보 설정
MONGODB_URL = "mongodb://root:ssafy605@15.164.142.18:27017"

# MongoDB 클라이언트 초기화 (비동기)
async_client = motor.motor_asyncio.AsyncIOMotorClient(MONGODB_URL)

# 데이터베이스 선택
db = async_client["a605"]

# MongoDB에서 데이터 가져오는 함수 (비동기)
async def get_player_prev_solo_rank(id: int):
    collection = db["player_prev_solo_rank"]
    result = await collection.find_one({"_id": id})
    return result
