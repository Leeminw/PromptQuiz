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

import { Link } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import { LobbyApi } from '../hooks/axios-lobby';
import CurrentUserList from '../components/lobby/CurrentUserList';
const Ranking = () => {
  //   const location = useLocation();
  //   const channelId = location.state?.channelId;
  //   const [roomList, setRoomList] = useState<RoomProps[]>([]);

  //   const handleState = (data: RoomProps[]) => {
  //     setRoomList(data);
  //   };

  //   useEffect(() => {
  //     // 방정보 가져오기
  //     const response = LobbyApi.getGameList(channelId)
  //       .then((response) => {
  //         setRoomList(response.data);
  //       })
  //       .catch((error) => {
  //         console.log(error);
  //       });
  //   }, []);

  return (
    <div className="flex flex-col items-center w-3/4 h-3/4 bg-white opacity-80">
      {/* <Header channelId={channelId} handleState={handleState} /> */}
      {/* <CurrentUserList /> */}
      {/* <RoomList {...roomList} /> */}
      이건 랭킹임 ㅋㅋㅋ
      <Link to="/channel">
        <button
          className="bg-customGreen text-white hover:brightness-125 hover:scale-105 transition 
            text-sm w-1/4 min-w-[3rem]"
        >
          홈으로
        </button>
      </Link>
    </div>
  );
};

export default Ranking;
