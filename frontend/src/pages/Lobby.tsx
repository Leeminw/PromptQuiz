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
import { UserChannelApi } from '../hooks/axios-user-channel';
const Lobby = () => {
  const { channelUuid } = useParams();
  const { user } = useUserStore();
  const { connectWebSocket, disconnectWebSocket, publish } = useWebSocketStore();
  const chatBtn = useRef(null);
  const [chat, setChat] = useState<RecieveChannelChat[]>([]);
  const chatInput = useRef(null);
  const chattingBox = useRef(null);
  const location = useLocation();
  const channelCode = location.state?.channelCode;
  const [roomList, setRoomList] = useState<RoomProps[]>([]);
  const [testRoomIdx, setTestRoomIdx] = useState<number>(1);
  const [currentUserList, setCurrentUserList] = useState<CurrentUser[]>([]);
  const handleState = (data1: RoomProps[], data2: CurrentUser[]) => {
    setRoomList(data1);
    setCurrentUserList(data2);
  };

  useEffect(() => {
    // 방정보 가져오기
    const response = LobbyApi.getGameList(channelCode)
      .then((response) => {
        setRoomList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
    console.log(channelCode);
  }, []);
  useEffect(() => {
    // 현재 채널 접속 인원정보 가져오기
    const response = UserChannelApi.getChannelUserList(channelCode)
      .then((response) => {
        setCurrentUserList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
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
    chatInput.current?.blur();
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
    <div className="w-[60rem] h-[40rem] min-w-[40rem] min-h-[40rem] flex flex-col bg-white/60 px-8 py-6 rounded-3xl drop-shadow-lg z-10">
      <div className="grid grid-cols-8 gap-3 h-10 items-center">
        <label className="col-span-2 flex items-center border-custom-mint bg-white text-sm h-8">
          <p className="text-center w-full text-nowrap">{channelCode}채널</p>
        </label>
        <div className="col-span-6 flex items-center pl-2">
          <Header channelCode={channelCode} channelUuid={channelUuid} handleState={handleState} />
          <button
            className="btn"
            onClick={() => {
              setTestRoomIdx((prev) => {
                return prev + 1;
              });
              setRoomList((prev) => {
                return [
                  ...prev,
                  {
                    id: 213123,
                    channelCode: "123123",
                    type: 123,
                    style: 2,
                    code: '1234',
                    title: '테스트용클릭ㄴㄴㄴ' + testRoomIdx,
                    password: '1234',
                    status: false,
                    isTeam: false,
                    curRound: 1,
                    rounds: 1,
                    curPlayers: 1,
                    maxPlayers: 1,
                  },
                ];
              });
            }}
          >
            테스트
          </button>
        </div>
      </div>

      <div className="w-full h-2/3 grid grid-cols-8 gap-3 pt-4 mb-4">
        {/* 접속 인원 */}
        <div className="w-full h-full flex flex-col col-span-2 border-custom-mint bg-mint">
          <div className="w-full h-5 bg-mint text-white font-bold text-sm flex items-center mb-2.5">
            <p className="w-full h-full flex items-center pl-1.5">접속 인원</p>
          </div>
          <CurrentUserList {...currentUserList} />
        </div>
        {/* 방 리스트 */}
        <div className="col-span-6 flex items-center px-1">
          <RoomList {...roomList} />
        </div>
      </div>
      <div className="w-full grid grid-cols-8 gap-3">
        {/* 광고 */}
        <div className="w-full h-full bg-blue-200 flex items-center justify-center col-span-2">
          광고
        </div>
        <div className="w-full h-full col-span-6">
          {/* 로비 채팅창 */}
          <div className="w-full px-1">
            <div className="relative w-full">
              <div
                className={`flex items-center w-full h-36 mb-2 transition-all origin-bottom duration-300`}
              >
                <div className="absolute w-full h-[90%] px-3 py-0.5 text-sm chat z-10">
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
                <div className="absolute w-full h-full border-custom-white opacity-70 bg-white z-0"></div>
              </div>
            </div>
          </div>
          {/* 로비 채팅 입력 */}
          <div className="w-full h-10 bg-white/90 rounded-full flex relative">
            <input
              ref={chatInput}
              className="w-full h-10 bg-transparent rounded-full pl-5 pr-20 text-sm placeholder-gray-400 border border-gray-300"
              maxLength={30}
              placeholder="Enter를 눌러 채팅 입력"
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  if (chatInput.current?.value !== '') publishChat();
                  else chatFocusOut();
                }
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
    </div>
  );
};

export default Lobby;
