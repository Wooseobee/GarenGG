# 시작하기

### 가상환경 생성

- 패키지 관리를 위해 가상환경 생성 후 라이브러리 설치
- python --version Python 3.12.1

```bash
python -m venv venv
source venv/Scripts/activate
pip install -r requirements.txt
```

### 라이브 서버 실행

```bash
cd app
uvicorn main:app --reload
```
