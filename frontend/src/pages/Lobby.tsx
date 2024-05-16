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
import badwordsFiltering from '../hooks/badwords-filtering';
import RankingModal from '../components/lobby/RankingModal';
const Lobby = () => {
  const { channelUuid } = useParams();
  const { user } = useUserStore();
  const { connectWebSocket, disconnectWebSocket, publish } = useWebSocketStore();
  const chatBtn = useRef(null);
  const [chat, setChat] = useState<RecieveChannelChat[]>([]);
  const chatInput = useRef(null);
  const chattingBox = useRef(null);
  const location = useLocation();
  // const channelCode = location.state?.channelCode;
  const [roomList, setRoomList] = useState<RoomProps[]>([]);
  const [testRoomIdx, setTestRoomIdx] = useState<number>(1);
  const [currentUserList, setCurrentUserList] = useState<CurrentUser[]>([]);
  const [channelInfo, setChannelInfo] = useState<Channel | null>();

  useEffect(() => {
    // 채널 구독하기
    connectWebSocket(
      `/ws/sub/channel?uuid=${channelUuid}`,
      recieveChat,
      publishEnterLobby,
      user.userId
    );
    document.addEventListener('click', handleOutsideClick);
    document.addEventListener('keydown', handleChatKey);
    return () => {
      disconnectWebSocket();
      document.removeEventListener('click', handleOutsideClick);
    };
  }, [channelInfo]);

  useEffect(() => {
    // 입장처리?
    getChannelInfo();
  }, []);
  useEffect(() => {
    console.log('channelUuid', channelUuid);
    LobbyApi.enterLobby(channelUuid);
  }, [channelInfo]);

  const handleState = (data1: RoomProps[], data2: CurrentUser[]) => {
    setRoomList(data1);
    setCurrentUserList(data2);
  };

  const getChannelInfo = async () => {
    try {
      const response = await LobbyApi.getChannelInfo(channelUuid);
      setChannelInfo(response.data);
      getGameList(response.data);
      getChannelUserList(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const getGameList = async (channelInfo: Channel) => {
    try {
      const response = await LobbyApi.getGameList(channelInfo.code);
      console.log('gameList', response);
      setRoomList(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const getChannelUserList = async (channelInfo: Channel) => {
    try {
      const response = await UserChannelApi.channelUserList(channelInfo.id);
      setCurrentUserList(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const recieveChat = async (message: IMessage) => {
    if (message.body) {
      // console.log(message.body);
      if (message.body === 'enter' || message.body === 'leave') {
        const response = await UserChannelApi.channelUserList(channelInfo.id);
        setCurrentUserList(response.data);
        return;
      }
      const body: RecieveChannelChat = JSON.parse(message.body);

      setChat((prev) => [...prev, body]);
    }
  };
  const publishEnterLobby = () => {
    const destination = '/ws/pub/channel/enter';
    const channelChat: ChannelChat = {
      userId: user.userId,
      nickname: user.nickName,
      uuid: channelUuid,
      content: 'entered',
    };
    publish(destination, channelChat);
  };
  const publishLeaveLobby = () => {
    const destination = '/ws/pub/channel/leave';
    const channelChat: ChannelChat = {
      userId: user.userId,
      nickname: user.nickName,
      uuid: channelUuid,
      content: 'leave',
    };
    publish(destination, channelChat);
  };

  const publishChat = () => {
    let chatFilter = badwordsFiltering(chatInput.current?.value);
    console.log(chatFilter);
    const destination = '/ws/pub/channel/chat/send';
    const channelChat: ChannelChat = {
      userId: user.userId,
      nickname: user.nickName,
      uuid: channelUuid,
      content: chatFilter,
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
    <div className="w-[70rem] h-[40rem] min-w-[40rem] min-h-[40rem] flex flex-col px-8 py-6 z-10">
      <RankingModal />
      <div className="grid grid-cols-8 gap-3 min-h-14 items-center drop-shadow-lg pl-4 backdrop-blur-sm">
        <label className="col-span-2 flex items-center border-custom-mint text-lg font-extrabold h-12 bg-white/90">
          <p className="text-center w-full text-nowrap text-mint">{channelInfo?.name}</p>
        </label>
        <div className="col-span-6 flex items-center">
          <Header channelUuid={channelUuid} handleState={handleState} />
        </div>
      </div>

      <div className="w-full h-2/3 grid grid-cols-8 gap-3 pt-4 mb-4 pl-4">
        {/* 접속 인원 */}
        <div className="w-full h-full flex flex-col col-span-2 backdrop-blur-sm bg-white/50">
          <div className="w-full h-7 border-custom-mint bg-mint text-white font-bold text-sm flex items-center mb-1">
            <p className="w-full h-full flex items-center pl-1.5">접속 인원</p>
          </div>
          <CurrentUserList currentUserList={currentUserList} />
        </div>
        {/* 방 리스트 */}
        <div className="col-span-6 flex items-center pl-1 relative">
          <RoomList roomList={roomList} />
        </div>
      </div>
      <div className="w-full grid grid-cols-8 gap-3 pl-4">
        {/* 광고 */}
        <div className="w-full h-full flex items-center justify-center col-span-2">

        </div>
        <div className="w-full h-full col-span-6 backdrop-blur-sm">
          {/* 로비 채팅창 */}
          <div className="w-full px-1">
            <div className="relative w-full">
              <div
                className={`flex items-center w-full h-36 mb-2 transition-all origin-bottom duration-300`}
              >
                <div className="absolute w-full h-[90%] px-3 py-0.5 text-sm chat custom-scroll z-10">
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
          <div className="w-full h-10 bg-white/80 rounded-full flex relative">
            <input
              ref={chatInput}
              className="w-full h-10 bg-transparent rounded-full pl-5 pr-20 text-sm border border-gray-300"
              maxLength={100}
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
