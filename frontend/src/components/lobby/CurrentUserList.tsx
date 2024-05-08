import React, { useEffect, useState } from 'react';

import { ImExit } from 'react-icons/im';
import { BsFillTrophyFill } from 'react-icons/bs';
import { IoMdRefresh } from 'react-icons/io';
import { FaForward } from 'react-icons/fa6';
import { Link } from 'react-router-dom';
import CreateRoom from './CreateRoom';
import { LobbyApi } from '../../hooks/axios-lobby';
import CurrentUser from './CurrentUser';
interface Props {
  userId: number;
  userName: string;
  nickName: string;
  picture: string;
  statusMessage: string | null;
  totalScore: number;
  teamScore: number;
  soloScore: number;
  created_date: string;
  updated_date: string;
}
const CurrentUserList = (currentUserList: Props[]) => {
  const currentUserArray = Object.values(currentUserList);
  console.log(currentUserArray);

  const createRoom = () => {
    alert('예시 함수');
  };
  return (
    <div className="w-full h-[18.5rem] flex flex-col gap-3 overflow-y-scroll userList border-custom-white bg-white py-1 pr-2 pl-1">
      {currentUserArray.map((item, index) => (
        <CurrentUser
          key={index}
          userId={item.userId}
          userName={item.userName}
          nickName={item.nickName}
          picture={item.picture}
          statusMessage={item.statusMessage}
          totalScore={item.totalScore}
          teamScore={item.teamScore}
          soloScore={item.soloScore}
          created_date={item.created_date}
          updated_date={item.updated_date}
        />
      ))}
    </div>
  );
};

export default CurrentUserList;
