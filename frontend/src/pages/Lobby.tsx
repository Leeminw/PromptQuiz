import React, { useEffect } from 'react';
import { useState } from 'react';
import { UserApi } from '../hooks/axios-user';
import { useNavigate } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import { IoMdLock } from 'react-icons/io';
import Header from '../components/lobby/Header';
import Chatting from '../components/lobby/Chatting';
import RoomList from '../components/lobby/RoomList';
import CreateRoom from '../components/lobby/CreateRoom';
import Room from '../components/lobby/Room';

import { useLocation } from 'react-router-dom';
import { LobbyApi } from '../hooks/axios-lobby';
import CurrentUserList from '../components/lobby/CurrentUserList';
const Lobby = () => {
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
  }, []);

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
      <div className="w-full h-2/3 grid grid-cols-8 gap-3 pt-4">
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
    </div>
  );
};

export default Lobby;
