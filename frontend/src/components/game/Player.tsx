import React, { useEffect, useState } from 'react';
interface GamePlayerProps {
  idx: number;
}

const GamePlayer = ({ idx }: GamePlayerProps) => {
  return (
    <div className="border-custom-green bg-customGreen w-full h-full relative flex items-center">
      <div className="absolute w-full -translate-y-8">
        <div
          className="min-w-14 min-h-6 w-fit h-fit bg-white border border-gray-200 rounded-lg text-xs px-2 pt-1 line-clamp-2
        "
        >
          가나다
        </div>
        <svg className="absolute z-10 w-3 h-3 translate-x-8 -translate-y-[0.05rem]">
          <path d="M 0 0 V 10 L 7 0" stroke="#dde5e3" strokeWidth={1} fill="white"></path>
        </svg>
      </div>
      <div className='pl-1 w-full flex items-center'>
        <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
        <p className="pl-2 w-full text-xs font-bold text-white line-clamp-2 text-ellipsis">
          플레이어{idx}
        </p>
        <p className="h-full text-nowrap text-white text-sm pr-1 pl-1 flex items-center">0점</p>
      </div>
    </div>
  );
};

export default GamePlayer;
