import React, { useEffect, useRef, useState } from 'react';
import { IoSettings } from 'react-icons/io5';
import { IoLogOut } from 'react-icons/io5';
import { FaUserPlus } from 'react-icons/fa';
import { IoSend } from 'react-icons/io5';
import GameApi from '../hooks/axios-game';
import { useLoaderData } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Client, Message, IMessage } from '@stomp/stompjs';
import { useWebSocketStore } from '../stores/socketStore';
import useUserStore from '../stores/userStore';
const GamePage = () => {
  const [game, setGame] = useState<Game | null>(null);
  const { roomId } = useParams();
  const [chat, setChat] = useState<GameChatRecieve[]>([]);
  const client = useRef<Client | null>(null);
  const { user } = useUserStore();
  const [text, setText] = useState('');
  const { connectWebSocket, disconnectWebSocket, publish } = useWebSocketStore();

  const getGameData = async () => {
    const response = await GameApi.getGame(roomId);
    setGame(response.data);
    console.log(game);
    // enterGame();
  };

  useEffect(() => {
    getGameData();
  }, []);

  useEffect(() => {
    // 게임 로드하면 구독.
    connectWebSocket(`/ws/sub/game?uuid=${game?.code}`, recieveChat, enterGame);
    return () => {
      disconnectWebSocket();
    };
  }, [game]);

  const recieveChat = (message: IMessage) => {
    if (message.body) {
      const body: RecieveData = JSON.parse(message.body);
      gameController(body);
    }
  };

  const gameController = (data: RecieveData) => {
    if (data.tag === 'chat') {
      // console.log(body.data);
      setChat((prevItems) => [...prevItems, data.data]);
      // setChat((prevItems) => [...prevItems, body]);
    } else if (data.tag === 'enter') {
      // console.log(body.data);
      // const data: GameChatRecieve = {
      //   userId: -1,
      //   nickname: 'system',
      //   uuid: game.code,
      //   gameId: game.id,
      //   round: 0,
      //   content: `${body.data.nickname}님이 입장하셨습니다.`,
      //   createdDate: '',
      // };
      // setChat((prevItems) => [...prevItems, data]);
    } else if (data.tag === 'leave') {
    } else if (data.tag === 'timer') {
    } else if (data.tag === 'wrongSignal') {
    } else if (data.tag === 'similarity') {
    } else if (data.tag === 'game') {
    }
  };
  const enterGame = () => {
    const gameEnter: GameEnter = {
      userId: user.userId,
      uuid: game?.code,
      nickname: user.nickName,
    };
    const destination = '/ws/pub/game/enter';
    publish(destination, gameEnter);
  };

  const publishChat = () => {
    const destination = '/ws/pub/game/chat/send';
    const gameChat: GameChat = {
      userId: user.userId,
      nickname: user.nickName,
      uuid: game.code,
      gameId: game.id,
      round: 0,
      content: chatInput.current.value,
    };
    publish(destination, gameChat);
    chatInput.current.value = '';
  };

  const chattingBox = useRef(null);
  const chatInput = useRef(null);

  const chatBtn = useRef(null);
  const [chatOpen, setChatOpen] = useState(false);

  // const chatFunction = () => {
  //   const chatChild = document.createElement('div');
  //   chatChild.className = 'flex';
  //   const chatUser = document.createElement('p');
  //   const chatMessage = document.createElement('p');
  //   chatUser.className = 'font-extrabold pr-1 text-nowrap text-black';
  //   chatUser.innerText = '푸바오 ㅠㅠㅠ : ';
  //   chatMessage.innerText = chatInput.current.value;
  //   chatChild.appendChild(chatUser);
  //   chatChild.appendChild(chatMessage);
  //   chatInput.current.value = '';
  //   chattingBox.current.appendChild(chatChild);
  // };

  useEffect(() => {
    const handleOutsideClick = (event: MouseEvent) => {
      const target = event.target as Node;
      if (target && !chatInput.current?.contains(target) && !chatBtn.current?.contains(target)) {
        setChatOpen(false);
      }
    };
    const handleChatKey = (event: KeyboardEvent) => {
      const target = event.target as Node;
      if (event.key === 'Enter' && !chatInput.current?.contains(target)) {
        if (!chatOpen) {
          chatInput.current.focus();
          setChatOpen(true);
        }
      }
    };
    document.addEventListener('click', handleOutsideClick);
    document.addEventListener('keydown', handleChatKey);
    return () => {
      document.removeEventListener('click', handleOutsideClick);
    };
  }, []);

  return (
    <div className="bg-white/80 w-[80vw] h-[85vh] min-w-[50rem] min-h-[35rem] z-10 rounded-3xl drop-shadow-lg px-8 py-6 flex flex-col items-center justify-center">
      {/* 상단 : 제목, 버튼 */}
      <div className="w-full h-10 flex gap-4 mb-2">
        {/* 채널 */}
        <label className="flex items-center w-1/3 py-4 border-custom-mint bg-white text-sm">
          <p className="text-center w-full text-nowrap">{game?.channelId}채널</p>
        </label>
        {/* 제목 */}
        <label className="flex items-center w-full grow py-4 border-custom-mint bg-white text-sm">
          <div className="border-r border-gray-200 pl-3 pr-2.5">{roomId}</div>
          <p className="text-center w-full text-nowrap line-clamp-1">{game?.title}</p>
        </label>
        {/* 버튼 */}
        <div className="w-1/3 flex gap-4">
          <button className={`btn-mint-border-white hover:brightness-125 transition text-sm w-1/2`}>
            <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden max-xl:justify-center">
              <FaUserPlus className="min-w-5 min-h-5 mb-0.5" />
              <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis xl:flex max-xl:hidden">
                초대하기
              </p>
            </label>
          </button>
          <button
            className={`btn-red bg-red-400 text-white hover:brightness-125 transition text-sm w-1/2 min-w-[3rem]`}
          >
            <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden max-xl:justify-center">
              <IoLogOut className="min-w-6 min-h-6 mb-0.5" />
              <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis xl:flex max-xl:hidden">
                나가기
              </p>
            </label>
          </button>
        </div>
      </div>
      {/* 중간 : 플레이어, 문제 화면 */}
      <div className="w-full h-[22rem] flex flex-col items-center mb-4">
        <div className="w-full h-full flex gap-4">
          {/* 좌파 */}
          <div className="w-1/3 flex flex-col gap-3 pt-5">
            {Array.from({ length: 6 }, (_, index) => (
              <div key={index} className="border-custom-mint bg-mint h-1/6 flex items-center pl-1">
                <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
                <p className="pl-2 w-full text-xs font-bold text-white line-clamp-2 text-ellipsis">
                  푸바오 ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ
                </p>
                <p className="h-full text-nowrap text-white text-sm pr-1 pl-1 flex items-center">
                  0점
                </p>
              </div>
            ))}
          </div>
          {/* 문제 화면, 타이머 */}
          <div className="w-full grow flex flex-col">
            <div className="h-4 rounded-full w-full bg-white mb-1 border-extralightmint border relative overflow-hidden flex">
              <div className="w-full h-full rounded-full -translate-x-[50%] transition-transform duration-1000 bg-mint absolute"></div>
            </div>
            <div className="border-custom-mint w-full h-full flex items-center justify-center relative">
              <div className="w-16 h-7 absolute top-2 left-2 bg-yellow-500/80 text-white rounded-full flex items-center justify-center font-extrabold text-xs border border-gray-300">
                1 / 20
              </div>
              <div className="w-fit h-7 px-3 absolute top-2 bg-yellow-500/80 text-white rounded-full flex items-center justify-center font-extrabold text-xs border border-gray-300">
                흠 뭐넣지
              </div>
              <div className="w-full h-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover bg-center"></div>
            </div>
          </div>
          {/* 우파 */}
          <div className="w-1/3 flex flex-col gap-3 pt-5">
            {Array.from({ length: 6 }, (_, index) => (
              <div key={index} className="border-custom-mint bg-mint h-1/6 flex items-center pl-1">
                <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
                <p className="pl-2 w-full text-xs font-bold text-white">푸바오 ㅠㅠㅠ</p>
                <p className="text-nowrap text-white text-sm pr-1">0점</p>
              </div>
            ))}
          </div>
        </div>
      </div>
      {/* 광고, 채팅창, 게임 설정 */}
      <div className="w-full h-48 flex gap-4">
        {/* 광고 */}
        <div className="w-1/3 bg-red-200 flex justify-center items-center">광고</div>
        {/* 채팅창 */}
        <div className="w-full flex grow flex-col items-center justify-end">
          <div className="w-full h-[8rem] mb-2 relative">
            <div
              className={`absolute w-full h-full border-custom-white opacity-80 bg-white  transition-all origin-bottom duration-300 ${chatOpen ? 'opacity-100 scale-100' : 'opacity-0 scale-0'}`}
            >
              <div className="px-3 py-2 w-full h-full text-sm chat">
                <div>
                  {chat.map((chatItem, index) => (
                    <div className="flex" key={index}>
                      <p className="font-extrabold pr-1 text-nowrap text-black">
                        {chatItem.nickname}
                      </p>
                      <p>{chatItem.content}</p>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
          <div className="w-full bg-white/80 h-10 rounded-full flex relative">
            <input
              ref={chatInput}
              className="w-full h-10 bg-transparent rounded-full pl-5 pr-20 text-sm placeholder-gray-400"
              maxLength={30}
              placeholder="채팅 입력..."
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  if (chatInput.current.value !== '') {
                    publishChat();
                  } else {
                    chatInput.current.blur();
                    setChatOpen(false);
                  }
                }
              }}
              onClick={async () => {
                setChatOpen(true);
              }}
            ></input>
            <div
              className="w-16 bg-mint cursor-pointer absolute h-full right-0 rounded-r-full flex justify-center items-center hover:brightness-125 transition"
              ref={chatBtn}
            >
              <IoSend className="text-white w-6 h-6" />
            </div>
          </div>
        </div>
        {/* 게임 설정 */}
        <div className="w-1/3 bg-blue-200 flex"></div>
      </div>
    </div>
  );
};

export default GamePage;
