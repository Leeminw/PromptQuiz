import React, { useRef } from 'react';
import { IoSettings } from 'react-icons/io5';
import { IoLogOut } from 'react-icons/io5';
import { FaUserPlus } from 'react-icons/fa';
import { IoSend } from 'react-icons/io5';

const GamePage = () => {
  const chattingBox = useRef(null);
  const chatInput = useRef(null);
  const chatFunction = () => {
    if (chatInput.current.value !== '') {
      const chatChild = document.createElement('div');
      chatChild.className = 'flex';
      const chatUser = document.createElement('p');
      const chatMessage = document.createElement('p');
      chatUser.className = 'font-extrabold pr-1 text-nowrap text-black';
      chatUser.innerText = '푸바오 ㅠㅠㅠ : ';
      chatMessage.innerText = chatInput.current.value;
      chatChild.appendChild(chatUser);
      chatChild.appendChild(chatMessage);
      chatInput.current.value = '';
      chattingBox.current.appendChild(chatChild);
    }
  };
  return (
    <div className="bg-white/80 w-[80vw] h-[85vh] min-w-[50rem] min-h-[35rem] z-10 rounded-3xl drop-shadow-lg px-8 py-6 flex flex-col items-center justify-center">
      {/* 상단 : 제목, 버튼 */}
      <div className="w-full h-10 flex gap-4 mb-2">
        {/* 채널 */}
        <label className="flex items-center w-1/3 py-4 border-custom-mint bg-white text-sm">
          <p className="text-center w-full text-nowrap">1채널</p>
        </label>
        {/* 제목 */}
        <label className="flex items-center w-full grow py-4 border-custom-mint bg-white text-sm">
          <div className="border-r border-gray-200 pl-3 pr-2.5">86</div>
          <p className="text-center w-full text-nowrap line-clamp-1">개인전 빠무 초보만</p>
        </label>
        {/* 버튼 */}
        <div className="w-1/3 flex gap-4">
          <button className={`btn-mint-border-white hover:brightness-125 transition text-sm w-1/2`}>
            <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden max-xl:justify-center">
              <FaUserPlus className="min-w-5 min-h-5 mb-0.5" />
              <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis xl:flex max-xl:hidden">
                초대하기
              </p>
            </label>
          </button>
          <button
            className={`btn-red bg-red-400 text-white hover:brightness-125 transition text-sm w-1/2 min-w-[3rem]`}
          >
            <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden max-xl:justify-center">
              <IoLogOut className="min-w-6 min-h-6 mb-0.5" />
              <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis xl:flex max-xl:hidden">
                나가기
              </p>
            </label>
          </button>
        </div>
      </div>
      {/* 중간 : 플레이어, 문제 화면 */}
      <div className="w-full h-[22rem] flex flex-col items-center mb-4">
        <div className="w-full h-full flex gap-4">
          {/* 좌파 */}
          <div className="w-1/3 flex flex-col gap-3 pt-5">
            {Array.from({ length: 6 }, (_, index) => (
              <div key={index} className="border-custom-mint bg-mint h-1/6 flex items-center pl-1">
                <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
                <p className="pl-2 w-full text-xs font-bold text-white line-clamp-2 text-ellipsis">
                  푸바오 ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ
                </p>
                <p className="text-nowrap text-white text-sm pr-1">0점</p>
              </div>
            ))}
          </div>
          {/* 문제 화면, 타이머 */}
          <div className="w-full grow flex flex-col">
            <div className="h-4 rounded-full w-[100%] bg-mint mb-1"></div>
            <div className="h-full w-full bg-gray-200 flex items-center justify-center">
              문제 화면
            </div>
          </div>
          {/* 우파 */}
          <div className="w-1/3 flex flex-col gap-3 pt-5">
            {Array.from({ length: 6 }, (_, index) => (
              <div key={index} className="border-custom-mint bg-mint h-1/6 flex items-center pl-1">
                <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
                <p className="pl-2 w-full text-xs font-bold text-white">푸바오 ㅠㅠㅠ</p>
                <p className="text-nowrap text-white text-sm pr-1">0점</p>
              </div>
            ))}
          </div>
        </div>
      </div>
      {/* 광고, 채팅창, 게임 설정 */}
      <div className="w-full h-48 flex gap-4">
        {/* 광고 */}
        <div className="w-1/3 bg-red-200 flex justify-center items-center">광고</div>
        {/* 채팅창 */}
        <div className="w-full flex grow flex-col items-center justify-end">
          <div className="w-full h-[8rem] mb-2 relative">
            <div className="absolute w-full h-full border-custom-white opacity-80 bg-white">
              <div className="px-3 py-2 w-full h-full text-sm chat">
                <div ref={chattingBox}></div>
              </div>
            </div>
          </div>
          <div className="w-full bg-white/80 h-10 rounded-full flex relative">
            <input
              ref={chatInput}
              className="w-full h-10 bg-transparent rounded-full pl-5 pr-20 text-sm placeholder-gray-400"
              maxLength={30}
              placeholder="채팅 입력..."
              onKeyDown={(e) => {
                if (e.key === 'Enter') {
                  chatFunction();
                }
              }}
            ></input>
            <div
              className="w-16 bg-mint cursor-pointer absolute h-full right-0 rounded-r-full flex justify-center items-center"
              onClick={chatFunction}
            >
              <IoSend className="text-white w-6 h-6" />
            </div>
          </div>
        </div>
        {/* 게임 설정 */}
        <div className="w-1/3 bg-blue-200 flex"></div>
      </div>
    </div>
  );
};

export default GamePage;
