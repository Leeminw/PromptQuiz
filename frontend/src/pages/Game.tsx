import React from 'react';
import { IoSettings } from 'react-icons/io5';
import { IoLogOut } from 'react-icons/io5';
import { FaUserPlus } from 'react-icons/fa';

const GamePage = () => {
  return (
    <div className="bg-white/80 w-[80vw] h-[85vh] min-w-[50rem] min-h-[35rem] z-10 rounded-3xl drop-shadow-lg px-8 py-6 flex flex-col items-center justify-center">
      {/* 상단 : 제목, 버튼 */}
      <div className="w-full h-10 flex gap-4 mb-4">
        {/* 채널 */}
        <label className="flex gap-1 items-center w-1/5 px-2 py-4 border-custom-mint bg-white text-sm">
          <p className="text-center w-full text-nowrap">1채널</p>
        </label>
        {/* 제목 */}
        <label className="flex items-center w-full px-2 py-4 border-custom-mint bg-white text-sm">
          <div className="border-r border-gray-200 pl-1 pr-2.5">86</div>
          <p className="text-center w-full text-nowrap line-clamp-1">개인전 빠무 초보만</p>
        </label>
        {/* 버튼 */}
        <div className="w-fit flex gap-4">
          <button className={`btn-mint hover:brightness-125 transition text-sm min-w-24`}>
            <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden">
              <FaUserPlus className="min-w-4 min-h-4 mb-0.5" />
              <p className="text-center w-full text-nowrap text-sm">초대하기</p>
            </label>
          </button>
          <button
            className={`btn-red bg-red-400 text-white hover:brightness-125 transition text-sm min-w-24`}
          >
            <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden">
              <IoLogOut className="min-w-5 min-h-5 mb-0.5" />
              <p className="text-center w-full text-nowrap text-sm">나가기</p>
            </label>
          </button>
        </div>
      </div>

      {/* 중간 : 플레이어, 문제 화면 */}
      <div className="w-full h-96">
        {/* <span className="w-2/3 bg-gray-200 rounded-full h-4 mb-4 dark:bg-gray-700 border-gray-400 border">
          <div
            className="h-4 rounded-full w-[65%] bg-mint"
          ></div>
        </span> */}
        <div className="bg-gray-400 w-full h-full"></div>
        <div className=""></div>
      </div>
      <div className="w-full h-32"></div>
    </div>
  );
};

export default GamePage;
