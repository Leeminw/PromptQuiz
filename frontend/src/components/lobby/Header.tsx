import React, { useEffect, useState } from 'react';

import { ImExit } from 'react-icons/im';
import { BsFillTrophyFill } from 'react-icons/bs';
import { IoMdRefresh } from 'react-icons/io';
import { FaForward } from 'react-icons/fa6';
import { MdAddHome } from 'react-icons/md';
import { Link } from 'react-router-dom';

const Header = () => {
  const createRoom = () => {
    alert('방 생성 모달창 띄움');
  };
  const fastMatching = () => {};
  const refresh = () => {};
  const ranking = () => {};

  return (
    <nav className="flex flex-col w-full">
      <div className="w-full h-[10vh] bg-red-200">자유 1 채널</div>
      <div className="w-full h-[10vh] flex">
        <button className="w-1/4 h-[10vh] btn-mint" onClick={createRoom}>
          <MdAddHome />방 만들기
        </button>
        <button className="w-1/4 h-[10vh] btn-mint">
          <FaForward />
          빠른대전
        </button>
        <button className="w-1/4 h-[10vh] btn-mint">
          <IoMdRefresh />
          새로고침
        </button>
        <button className="w-1/4 h-[10vh] btn-mint">
          <BsFillTrophyFill />
          랭킹
        </button>
      </div>

      <div>
        <Link to="/channel">
          <button className="w-1/4 h-[10vh] bg-red-500">
            <ImExit />
            나가기
          </button>
        </Link>
      </div>
    </nav>
  );
};

export default Header;
