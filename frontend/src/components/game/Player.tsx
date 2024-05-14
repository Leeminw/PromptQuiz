import React, { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
import customSetTimeout from '../../hooks/customSetTimeout';
interface GamePlayerProps {
  // idx: number;
  userInfo: GameUser | null;
  gameChat: GameChatRecieve | null;
  // correctAnswer: boolean | null;
  // wrongAnswer: boolean | null;
}
// { idx }: GamePlayerProps
// const GamePlayer = forwardRef((props, ref) => {
//   const chatBubbleBox = useRef(null);
//   const chatBubbleFunction = () => {
//     console.log('HI');
//   };

const GamePlayer = ({ userInfo, gameChat }: GamePlayerProps) => {
  const [showChat, setShowChat] = useState<boolean>(false);
  const [startChat, setStartChat] = useState<boolean>(false);
  customSetTimeout(
    () => {
      if (gameChat !== null && gameChat !== undefined && gameChat?.content !== '') {
        setStartChat(true);
        setShowChat(true);
      }
    },
    () => {
      setShowChat(false);
    },
    gameChat?.content.length * 100 + 3000,
    [gameChat?.createdDate]
  );
  return (
    <div className="border-custom-mint bg-mint backdrop-blur-sm w-full h-full relative flex items-center z-0">
      <div
        className={`absolute w-full -translate-y-8 opacity-0 ${startChat && (showChat ? 'opacity-100' : 'animate-chatBubble')}`}
      >
        <div
          className={`min-w-12 min-h-6 w-fit h-fit bg-white border border-gray-200 rounded-lg text-sm px-3 pt-1 line-clamp-2 leading-4 text-black
          ${gameChat?.content.length < 10 && 'text-center'}
          `}
        >
          {gameChat?.content}
        </div>
        <svg className="absolute z-10 w-3 h-3 translate-x-8 -translate-y-[0.05rem]">
          <path d="M 0 0 V 10 L 7 0" stroke="#dde5e3" strokeWidth={1} fill="white"></path>
        </svg>
      </div>
      <div className="pl-1 w-full flex items-center">
        <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
        <p className="pl-2 w-full text-xs font-bold text-white line-clamp-2 text-ellipsis">
          {userInfo.nickName}
        </p>
        <p className="h-full text-nowrap text-white text-sm pr-1 pl-1 flex items-center">
          {userInfo.score}점
        </p>
      </div>
    </div>
  );
};

export default GamePlayer;
