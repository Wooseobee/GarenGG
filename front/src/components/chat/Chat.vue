<template>
  <div class="chat-view" :class="{ 'minimized': minimized }">
    <div v-if="!minimized" class="chat-content">
      <!-- 채팅 내용 및 헤더 -->
      <div class="chat-header">
        <span class="chat-title">Chat Room</span>
        <button @click="toggleMinimize" class="minimize-button">
          <i class="bi bi-box-arrow-in-down-right"></i>
        </button>
      </div>
  <div class="messages-container" ref="messagesContainer">
    <div v-for="(message, index) in mList" :key="index" :class="{'message': true, 'me': message.me, 'other': !message.me}">
      <div class="message-userId">{{ message.userId }}</div>
      <div class="message-content">{{ message.content }}</div>
    </div>
  </div>
      <div class="chat-input">
        <input type="text" placeholder="메세지를 입력하세요" v-model="message" @keyup.enter="sendMessageToSocket">
        <button @click="sendMessageToSocket" >Send</button>
      </div>
    </div>
    <button v-else @click="toggleMinimize" class="minimize-icon">
      <i class="bi bi-chat-left-text"></i>  
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted, onUpdated } from 'vue';
import { Client } from '@stomp/stompjs'

const messagesContainer = ref(null);

onUpdated(() => {
  if (messagesContainer.value) {
    messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight;
  }
});

const adverbs = [
  "빠르게", "조용히", "용감하게", "밝게", "쾌활하게",
  "열심히", "우아하게", "행복하게", "소란스럽게", "자랑스럽게"
];

const adjectives = [
  "용감한", "밝은", "영리한", "열심인", "사나운",
  "온화한", "행복한", "즐거운", "친절한", "사랑스러운"
];

const champions = ["가렌", "애니", "애쉬", "아리", "야스오"];
const minimized = ref(false);

const client = ref(null);

// 랜덤 아이디 생성 함수
const generateRandomUserId = () => {
  const adverb = adverbs[Math.floor(Math.random() * adverbs.length)];
  const adjective = adjectives[Math.floor(Math.random() * adjectives.length)];
  const champion = champions[Math.floor(Math.random() * champions.length)];
  const randomNum = Math.floor(Math.random() * 10000) + 1;
  return `${adverb}${adjective}${champion}${randomNum}`;
};

const userId = ref('');
let message = ref("");
const mList = ref([]);

onMounted(() => {
  // localStorage에서 userId 가져오기
  const storedUserId = localStorage.getItem('userId');
  
  // localStorage에 userId가 없다면 새로 생성하고 저장
  if (!storedUserId) {
    const newUserId = generateRandomUserId();
    localStorage.setItem('userId', newUserId);
    userId.value = newUserId;
  } else {
    // 있으면 그 값을 사용
    userId.value = storedUserId;
  }

  client.value = new Client({
    brokerURL: 'ws://localhost:8080/ws-stomp',
    connectHeaders:{},
    onConnect: () => {
      client.value.subscribe('/sub/chat/room/' + 1, message =>
        onMessageReceivedFromSocket(message)
      )
      // client.publish({ destination: '/pub/chat/enterUser', body: JSON.stringify({meesageType: "ENTER", content: userInfo.name +"님 환영합니다!", userId: userId.value, chatRoomId: 1 }) });
    },
    onStompError: () => {
        console.log('STOMP connection error')
      }
  });

  client.value.activate();

});

function toggleMinimize() {
  minimized.value = !minimized.value;
}

// 메세지 받는 로직
function onMessageReceivedFromSocket (payload) {
  var chat = JSON.parse(payload.body);
  console.log(chat)
  const newMessage = {
    me: chat.userId === userId.value,
    content: chat.content,
    userId: chat.userId
  }
  mList.value.push(newMessage)
  if (mList.value.length > 100) {
    mList.value.shift(); // 배열의 첫 번째 요소를 제거
  }
}

  // 메세지 보내는 로직 
function sendMessageToSocket() {
  if(!message.value.trim()) return;
  var chatMessage = {
    "chatRoomId": 1,
    "userId": userId.value,
    "content": message.value,
    "messageType": "TALK"
  }
  message.value = '';
  client.value.publish({
    destination: "/pub/chat/sendMessage",
    body: JSON.stringify(chatMessage),
    headers: {}
  });
}

function discnnectChat() {
  client.disconnect({
    destination: '/pub/message',
    userId: userInfo.userId, chatRoomId: userInfo.roomId }, {userId: userInfo.userId, chatRoomId: userInfo.roomId 
    });
  alert("see you next Time!!")
}



</script>

<style scoped>
.chat-view {
  position: fixed;
  bottom: 0;
  right: 0;
  margin: 10px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: width 0.3s, height 0.3s;
  z-index: 1000;
  /* 기본 스타일 */
}

.chat-header {
  padding: 10px;
  background-color: #005A82;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.chat-title {
  font-weight: bold;
}

.minimize-button {
  background: none;
  border: none;
  color: white;
  font-size: 1.5rem;
}

/* 메세지 css 시작 */
.messages-container {
  max-width: 300px;
  height: 50vh;
  overflow-y: auto;
  border: none; /* 경계선 제거 */
  margin: 0; /* 상하 마진 제거 */
  padding: 0; /* 패딩 조정 */
  background-color: #0397AB; /* 배경색 변경 */
}

.message {
  padding: 10px;
  margin: 0 0 5px 0; /* 좌우 마진 제거, 상하 마진 조정 */
  border-radius: 10px;
}

.message-userId {
  font-size: 0.65rem; /* 폰트 크기를 작게 설정 */
  color: black; /* 사용자 ID 색상 설정 (예: 회색) */
  margin-bottom: 4px; /* 사용자 ID와 메시지 내용 사이의 마진 추가 */
  font-weight: bold;
}

.me {
  background-color: #ffffff;
  color: black; /* 텍스트 색상 변경 */
  text-align: left;
  margin-right: auto; /* 왼쪽 정렬 */
  max-width: 66%; /* 메시지 최대 너비 설정 */
}

.other {
  background-color: #ffffff; /* 상대방의 메시지 배경색 변경 */
  color: black;
  text-align: right;
  margin-left: auto; /* 오른쪽 정렬 */
  max-width: 66%; /* 메시지 최대 너비 설정 */
}

/* 스크롤바 스타일링 */
.messages-container::-webkit-scrollbar {
  width: 8px;
}

.messages-container::-webkit-scrollbar-track {
  background: #d1d1d1;
  border-radius: 4px;
}

.messages-container::-webkit-scrollbar-thumb {
  background: #a8a8a8;
  border-radius: 4px;
}

.messages-container::-webkit-scrollbar-thumb:hover {
  background: #787878;
}


/* 메세지 css 끝 */
.chat-input {
  display: flex;
  padding: 10px;
  background-color: #f7f7f7;
}

.chat-input input {
  flex-grow: 1;
  padding: 5px 10px;
  margin-right: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.chat-input button {
  padding: 5px 15px;
  background-color: #005A82;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.chat-view.minimized {
  width: 50px;
  height: 50px;
}

.minimize-icon {
  all: unset;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: #005A82;
}
</style>
