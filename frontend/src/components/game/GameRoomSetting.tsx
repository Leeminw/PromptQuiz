import React, { useEffect, useState } from 'react';
import { IoSettings } from 'react-icons/io5';
interface GameRoomSettingProps {
  gamestart: boolean;
}

const GameRoomSetting = ({ gamestart }: GameRoomSettingProps) => {
  return (
    <div className="w-full h-28 flex flex-col cursor-default">
      <dialog id="modalopen" className="modal">
        <div className="modal-box">
          <h3 className="font-bold text-lg">방 만들기</h3>
          <p className="py-4">내용</p>
          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
            </form>
          </div>
        </div>
      </dialog>
      <div className="w-full h-7 border-custom-mint bg-mint pl-2 pr-1 text-white font-bold text-sm flex items-center">
        <p className="w-full">방 설정</p>
        <IoSettings
          className={`w-5 h-5 transition ${!gamestart && 'hover:scale-125 cursor-pointer'}`}
          onClick={() => {
            if (!gamestart) (document.getElementById('modalopen') as HTMLDialogElement).showModal();
          }}
        />
      </div>
      <div className="w-full h-40 border-custom-white bg-white flex flex-col text-xs text-gray-400 pt-0.5">
        <div className="w-full h-5 pb-1 flex border-b border-gray-200">
          <div className="w-1/2 flex items-center justify-center border-r border-gray-200">
            개인전
          </div>
          <div className="w-1/2 flex items-center justify-center font-extrabold text-mint">
            팀전
          </div>
        </div>
        <div className="w-full h-7 flex border-b border-gray-200 items-center">
          <div className="w-1/3 flex items-center justify-center text-nowrap font-extrabold text-mint">
            객관식
          </div>
          <div className="w-1/3 flex items-center justify-center text-nowrap border-x border-gray-200">
            순서배치
          </div>
          <div className="w-1/3 flex items-center justify-center text-nowrap">주관식</div>
        </div>
        {/* 라운드 수 */}
        <div className="w-full h-5 flex justify-center items-center font-extrabold text-mint mt-1">
          20 라운드
        </div>
      </div>
    </div>
  );
};

export default GameRoomSetting;
