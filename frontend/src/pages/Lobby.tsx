import React, { useEffect } from 'react';
import { useState } from 'react';
import { UserApi } from '../hooks/axios-user';
import { useNavigate } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import { IoMdLock } from 'react-icons/io';
import Header from '../components/lobby/Header';
import Room from '../components/lobby/Room';
import Chatting from '../components/lobby/Chatting';
import RoomList from '../components/lobby/RoomList';
const Lobby = () => {
  const [roomList, setRoomList] = useState<Room[]>([]);

  useEffect(() => {
    // 방정보 가져오기
    const response = {
      status: 'success',
      message: '방 목록 조회 완료',
      data: [
        {
          id: 8144742847851927974,
          channelId: 1,
          type: 1,
          style: 0,
          code: '20b3ed3c-965c-40c0-ad7c-62c9bdd29b3e',
          title: '들어와2',
          password: '1234', // or null
          status: true,
          isTeam: false,
          curRound: 0,
          rounds: 0,
          curPlayers: 1,
          maxPlayers: 8,
        },
        {
          id: 8144749997851927974,
          channelId: 1,
          type: 1,
          style: 0,
          code: '20b3ffff-965c-40c0-ad7c-62c9bdd29b3e',
          title: '들어와3',
          password: '1234aaaaa',
          status: true,
          isTeam: true,
          curRound: 0,
          rounds: 0,
          curPlayers: 3,
          maxPlayers: 8,
        },
      ],
    };
    setRoomList(response.data);
  }, []);

  return (
    <div className="flex flex-col items-center w-3/4 bg-white opacity-80">
      <Header />
      {/* <Room
        id={8144742847851927974}
        channelId={1}
        type={1}
        style={0}
        code={'20b3ed3c-965c-40c0-ad7c-62c9bdd29b3e'}
        title={'들어와2'}
        password={'1234'}
        status={true}
        isTeam={false}
        curRound={0}
        rounds={0}
        curPlayers={1}
        maxPlayers={8}
      /> */}
      <div className="h-[100px] w-[100px] bg-yellow-300">광고</div>
      <Chatting />
      <RoomList />
    </div>
  );
};

export default Lobby;
