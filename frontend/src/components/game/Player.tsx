import React, { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
interface GamePlayerProps {
  idx: number;
}
// { idx }: GamePlayerProps
const GamePlayer = forwardRef((props, ref) => {
  const chatBubbleBox = useRef(null);
  const chatBubbleFunction = () => {
    console.log('HI');
  };

  return (
    <div className="border-custom-green bg-customGreen w-full h-full relative flex items-center">
      <div className="absolute w-full -translate-y-8">
        <div
          ref={chatBubbleBox}
          className="min-w-20 min-h-6 w-fit h-fit bg-white border border-gray-200 rounded-lg text-xs px-2 py-0.5 line-clamp-2
        "
        >
          가
        </div>
        <svg className="absolute z-10 w-3 h-3 translate-x-8 -translate-y-[0.05rem]">
          <path d="M 0 0 V 10 L 7 0" stroke="#dde5e3" strokeWidth={1} fill="white"></path>
        </svg>
      </div>
      <div className="pl-1 w-full flex items-center">
        <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
        <p className="pl-2 w-full text-xs font-bold text-white line-clamp-2 text-ellipsis">
          플레이어
        </p>
        <p className="h-full text-nowrap text-white text-sm pr-1 pl-1 flex items-center">0점</p>
      </div>
    </div>
  );
});

export default GamePlayer;
