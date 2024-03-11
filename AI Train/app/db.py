from pymongo import MongoClient
import motor.motor_asyncio
import logging
import re
import csv

logging.basicConfig(level=logging.INFO)

# MongoDB 연결 정보 설정
MONGODB_URL = "mongodb://root:ssafy605@15.164.142.18:27017"

# MongoDB 클라이언트 초기화 (비동기)
async_client = motor.motor_asyncio.AsyncIOMotorClient(MONGODB_URL)

# 데이터베이스 선택
db = async_client["a605"]

# MongoDB에서 데이터 가져오는 함수 (비동기)!
async def get_player_prev_solo_rank():
    collection = db["player_cur_solo_rank"]
    
    # here
    # _id가 50 이하인 플레이어 데이터만 조회 {"_id": {"$lte": 10000}}
    players = collection.find()
    result = []  # 최종 결과를 저장할 빈 리스트

    async for player in players:
        
        if "mostDatas" not in player or not player["mostDatas"]:
            continue  # 'mostDatas' 필드가 없거나 비어있으면 이 문서를 건너뜀

        for champion_data in player["mostDatas"]:
            
            if "game" not in champion_data:
                continue  # 'game' 키가 없으면 이 챔피언 데이터를 건너뜀
            
            # 게임 데이터에서 총 게임 수와 승리율 추출
            game_data = champion_data["game"]
            total_games, win_rate = process_game_data(game_data)
            if total_games <= 10:
                continue
            
            # 최종 결과에 추가
            result.append({
                "id": player["_id"],
                "champion": champion_data["champion"],
                "score": total_games * win_rate / 100  # 총 게임 수와 승리율을 배열로 저장
            })
    return result

def process_game_data(game_data):
    # 정규 표현식을 사용하여 승리 수, 패배 수, 승리율을 추출합니다.
    # 이 패턴은 "승리수W패배수L승리율%" 형식을 처리할 수 있으며,
    # 승리 수나 패배 수 중 하나만 존재하는 경우에도 작동합니다.
    match = re.match(r'(\d+)W(\d+)L(\d+)%', game_data)
    if not match:
        # "승리수W패배수L승리율%" 형식이 아닌 경우, 다른 패턴으로 시도합니다.
        match = re.match(r'(\d+)(W|L)(\d+)%', game_data)
        if not match:
            logging.error(f"Invalid game_data format: {game_data}")
            return 0, 0

    if 'W' in game_data:
        wins = int(match.group(1))
        losses = int(match.group(2)) if 'L' in game_data else 0
    else:
        wins = 0
        losses = int(match.group(1))
    
    win_rate = int(match.group(3))
    total_games = wins + losses

    return total_games, win_rate

async def save_result_to_csv(result):
    filepath = "data/score_data.csv"
     # CSV 파일을 쓰기 모드로 엽니다
    with open(filepath, mode='w', newline='', encoding='utf-8') as f:
        writer = csv.writer(f)

        # CSV 파일의 헤더를 작성합니다
        writer.writerow(['user_id', 'champion', 'score'])
        
        # 결과를 CSV 파일에 작성합니다
        for item in result:
            writer.writerow([item['id'], item['champion'], item['score']])

if __name__ == "__main__":
    import asyncio

    async def main():
        result = await get_player_prev_solo_rank()

        await save_result_to_csv(result)
    asyncio.run(main())
