import React, { useEffect, useRef } from 'react';
import { useState } from 'react';
import { UserApi } from '../hooks/axios-user';
import { useNavigate, useParams } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import { IoMdLock } from 'react-icons/io';
import Header from '../components/lobby/Header';
import Chatting from '../components/lobby/Chatting';
import RoomList from '../components/lobby/RoomList';
import CreateRoom from '../components/lobby/CreateRoom';
import Room from '../components/lobby/Room';
import { IoSend } from 'react-icons/io5';

import { useLocation } from 'react-router-dom';
import { LobbyApi } from '../hooks/axios-lobby';
import CurrentUserList from '../components/lobby/CurrentUserList';
import { useWebSocketStore } from '../stores/socketStore';
import useUserStore from '../stores/userStore';
import { IMessage } from '@stomp/stompjs';
const Lobby = () => {
  const { channelUuid } = useParams();
  const { user } = useUserStore();
  const { connectWebSocket, disconnectWebSocket, publish } = useWebSocketStore();
  const chatBtn = useRef(null);
  const [chat, setChat] = useState<RecieveChannelChat[]>([]);
  const chatInput = useRef(null);
  const chattingBox = useRef(null);
  const location = useLocation();
  const channelId = location.state?.channelId;
  const [roomList, setRoomList] = useState<RoomProps[]>([]);

  const handleState = (data: RoomProps[]) => {
    setRoomList(data);
  };

  useEffect(() => {
    // 방정보 가져오기
    const response = LobbyApi.getGameList(channelId)
      .then((response) => {
        setRoomList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
    console.log(channelId);
  }, []);
  useEffect(() => {
    // 게임 로드하면 구독하기
    connectWebSocket(`/ws/sub/channel?uuid=${channelUuid}`, recieveChat, enterGame);
    document.addEventListener('click', handleOutsideClick);
    document.addEventListener('keydown', handleChatKey);
    return () => {
      document.removeEventListener('click', handleOutsideClick);
    };
  }, []);
  const recieveChat = (message: IMessage) => {
    if (message.body) {
      const body: RecieveChannelChat = JSON.parse(message.body);
      setChat((prev) => [...prev, body]);
    }
  };
  const enterGame = () => {};
  const publishChat = () => {
    const destination = '/ws/pub/channel/chat/send';
    const channelChat: ChannelChat = {
      nickname: user.nickName,
      uuid: channelUuid,
      content: chatInput.current.value,
    };
    publish(destination, channelChat);
    chatInput.current.value = '';
  };
  const chatFocusOut = () => {
    chatInput.current.blur();
  };
  const chatFocus = () => {
    chatInput.current?.focus();
  };
  const handleChatKey = (event: KeyboardEvent) => {
    const target = event.target as Node;
    if (event.key === 'Enter' && !chatInput.current?.contains(target)) {
      chatFocus();
    }
  };
  const handleOutsideClick = (event: MouseEvent) => {
    const target = event.target as Node;
    if (target && !chatInput.current?.contains(target) && !chatBtn.current?.contains(target)) {
      chatFocusOut();
    }
  };

  return (
    <div className="w-[60rem] h-[40rem] min-w-[40rem] min-h-[40rem] flex flex-col bg-white/80 px-8 py-6 rounded-3xl drop-shadow-lg z-10">
      <div className="grid grid-cols-8 gap-3 h-10 items-center">
        <label className="col-span-2 flex items-center border-custom-mint bg-white text-sm h-8">
          <p className="text-center w-full text-nowrap">{channelId}채널</p>
        </label>
        <div className="col-span-6 flex items-center pl-2">
          <Header channelId={channelId} handleState={handleState} />
        </div>
      </div>

      <div className="w-full h-2/3 bg-blue-200 grid grid-cols-8 gap-3 pt-4">
        <div className="w-full h-full flex flex-col col-span-2 border-custom-mint bg-mint">
          <div className="w-full h-5 bg-mint text-white font-bold text-sm flex items-center mb-2.5">
            <p className="w-full h-full flex items-center">접속 인원</p>
          </div>
          <CurrentUserList />
        </div>

        {/* <Chatting /> */}
        <div className="col-span-6 flex items-center">
          <RoomList {...roomList} />
        </div>
        
      </div>
      {/* 로비 채팅 */}
      <div className='w-full h-full'>
          <div className="w-full">
            <div className="relative w-full">
              <div
                className={`flex items-center w-full h-36 bottom-0 mb-2 transition-all origin-bottom duration-300`}
              >
                <div className="absolute w-full h-[90%] px-3 py-2 text-sm chat z-10">
                  <div className="z-10 text-gray-700" ref={chattingBox}>
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
                <div className="absolute w-full h-full border-custom-white opacity-90 bg-white z-0"></div>
              </div>
            </div>
          </div>
          <div className="w-full h-10 bg-white/80 rounded-full flex relative">
            <input
              ref={chatInput}
              className="w-full h-10 bg-transparent rounded-full pl-5 pr-20 text-sm placeholder-gray-400"
              maxLength={30}
              placeholder='Enter를 눌러 채팅 입력'
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  if (chatInput.current.value !== '') {
                    publishChat();
                  }
                } else if (e.key === 'Escape') chatFocusOut();
              }}
              onClick={chatFocus}
            ></input>
            <div
              className="w-16 bg-mint cursor-pointer absolute h-full right-0 rounded-r-full flex justify-center items-center hover:brightness-125 transition"
              ref={chatBtn}
              onClick={() => {
                if (chatInput.current.value !== '') {
                  publishChat();
                }
              }}
            >
              <IoSend className="text-white w-6 h-6" />
            </div>
          </div>
        </div>
    </div>
  );
};

export default Lobby;
