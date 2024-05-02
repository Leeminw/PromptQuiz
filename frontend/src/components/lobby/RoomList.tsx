import React, { useEffect, useState } from 'react';
import Room from '../../components/lobby/Room';
import { FaPlay } from 'react-icons/fa';

const RoomList = () => {
  return (
    <div className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      <div>방 내용</div>
      <div>
        <button>
          <FaPlay className="rotate-180" />
        </button>
        <span>1/2</span>
        <button>
          <FaPlay />
        </button>
      </div>
    </div>
  );
};

export default RoomList;
