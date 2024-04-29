import React from 'react';
import { IoSettings } from 'react-icons/io5';
import { IoLogOut } from 'react-icons/io5';
import { FaUserPlus } from 'react-icons/fa';
import { IoSend } from 'react-icons/io5';

const GamePage = () => {
  return (
    <div className="bg-white/80 w-[80vw] h-[90vh] min-w-[50rem] min-h-[35rem] z-10 rounded-3xl drop-shadow-lg px-8 py-6 flex flex-col items-center justify-center">
      {/* 상단 : 제목, 버튼 */}
      <div className="w-full h-10 flex gap-4 mb-4">
        {/* 채널 */}
        <label className="flex items-center w-[25vw] min-w-36 py-4 border-custom-mint bg-white text-sm">
          <p className="text-center w-full text-nowrap">1채널</p>
        </label>
        {/* 제목 */}
        <label className="flex items-center w-full py-4 border-custom-mint bg-white text-sm">
          <div className="border-r border-gray-200 pl-3 pr-2.5">86</div>
          <p className="text-center w-full text-nowrap line-clamp-1">개인전 빠무 초보만</p>
        </label>
        {/* 버튼 */}
        <div className="w-[25vw] min-w-36 flex gap-4">
          <button className={`btn-mint hover:brightness-125 transition text-sm w-1/2`}>
            <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden">
              <FaUserPlus className="min-w-4 min-h-4 mb-0.5" />
              <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis lg:flex md:hidden">
                초대하기
              </p>
            </label>
          </button>
          <button
            className={`btn-red bg-red-400 text-white hover:brightness-125 transition text-sm w-1/2 min-w-[3rem]`}
          >
            <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden">
              <IoLogOut className="min-w-5 min-h-5 mb-0.5" />
              <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis lg:flex md:hidden">
                나가기
              </p>
            </label>
          </button>
        </div>
      </div>

      {/* 중간 : 플레이어, 문제 화면 */}
      <div className="w-full h-[22rem] flex flex-col items-center mb-4">
        <div className="bg-gray-400 w-full h-full flex gap-4">
          {/* 좌파 */}
          <div className="w-[25vw] bg-red-200 flex flex-col gap-4 pt-4">
            {Array.from({ length: 5 }, (_, index) => (
              <div key={index} className="bg-lime-200 h-1/6">
                푸바오
              </div>
            ))}
          </div>
          {/* 문제 화면, 타이머 */}
          <div className="w-full bg-emerald-200 flex flex-col">
            <div className="h-4 rounded-full w-[100%] bg-mint"></div>
            <div className="h-full w-full bg-gray-200 flex items-center justify-center">
              문제 화면
            </div>
          </div>
          {/* 우파 */}
          <div className="w-[25vw] bg-blue-200 flex flex-col gap-4 pt-4">
            {Array.from({ length: 5 }, (_, index) => (
              <div key={index} className="bg-lime-200 h-1/6">
                푸바오
              </div>
            ))}
          </div>
        </div>
      </div>
      {/* 광고, 채팅창, 게임 설정 */}
      <div className="w-full h-48 bg-slate-400 flex gap-4">
        {/* 광고 */}
        <div className="w-[25vw] bg-red-200 flex justify-center items-center">광고</div>
        {/* 채팅창 */}
        <div className="w-full flex flex-col items-center justify-end">
          <div className="w-full h-[8rem] mb-2 relative">
            <div className="absolute w-full h-full border-custom-white opacity-80 bg-white">
              <div className='px-2 py-1 overflow-y-scroll w-full h-full'>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
                <p>푸바오 ㅠㅠㅠ : 푸바오ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ</p>
              </div>
            </div>
          </div>
          <div className="w-full bg-white/80 h-10 rounded-full flex relative">
            <input
              className="w-full h-10 bg-transparent rounded-full pl-5 pr-20 text-sm placeholder-gray-400"
              maxLength={30}
              placeholder="채팅 입력..."
            ></input>
            <div className="w-16 bg-mint cursor-pointer absolute h-full right-0 rounded-r-full flex justify-center items-center">
              <IoSend className="text-white w-6 h-6" />
            </div>
          </div>
        </div>
        {/* 게임 설정 */}
        <div className="w-[25vw] bg-blue-200 flex"></div>
      </div>
    </div>
  );
};

export default GamePage;
