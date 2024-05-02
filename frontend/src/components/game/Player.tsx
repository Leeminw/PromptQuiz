import React, { useEffect, useState } from 'react';
interface GamePlayerProps {
  idx: number;
}

const GamePlayer = ({ idx }: GamePlayerProps) => {
  return (
    <div className="border-custom-green bg-customGreen h-1/6 flex items-center pl-1">
      <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
      <p className="pl-2 w-full text-xs font-bold text-white line-clamp-2 text-ellipsis">
        플레이어{idx}
      </p>
      <p className="h-full text-nowrap text-white text-sm pr-1 pl-1 flex items-center">0점</p>
    </div>
  );
};

export default GamePlayer;
