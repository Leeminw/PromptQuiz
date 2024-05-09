import React, { useEffect, useState } from 'react';

import { ImExit } from 'react-icons/im';
import { BsFillTrophyFill } from 'react-icons/bs';
import { IoMdRefresh } from 'react-icons/io';
import { FaForward } from 'react-icons/fa6';
import { Link } from 'react-router-dom';
import CreateRoom from './CreateRoom';
import { LobbyApi } from '../../hooks/axios-lobby';
import CurrentUser from './CurrentUser';
interface UserListProps {
  currentUserList: CurrentUser[];
}
const CurrentUserList = ({ currentUserList }: UserListProps) => {
  const currentUserArray = Object.values(currentUserList);
  console.log(currentUserArray);

  const createRoom = () => {
    alert('예시 함수');
  };
  return (
    <div className="w-full h-[18.5rem] flex flex-col gap-3 overflow-y-scroll userList border-custom-white bg-white py-1 pr-2 pl-1">
      {currentUserArray.map((item: CurrentUser, index) => (
        <CurrentUser key={index} user={item} />
      ))}
    </div>
  );
};

export default CurrentUserList;
