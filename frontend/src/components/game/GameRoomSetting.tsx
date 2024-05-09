import React, { useEffect, useState } from 'react';
import { IoSettings } from 'react-icons/io5';
interface GameRoomSettingProps {
  gamestart: boolean;
}

const GameRoomSetting = ({ gamestart }: GameRoomSettingProps) => {
  useEffect(()=>{
    console.log("게임이 시작됐나요?",gamestart)
  },[])
  return (
    <div className="w-full h-20 flex flex-col cursor-default">
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
      <div className="w-full h-6 border-custom-mint bg-mint pl-2 pr-1 text-white font-bold text-sm flex items-center z-10">
        <p className="w-full">방 설정</p>
        <IoSettings
          className={`w-5 h-5 transition ${!gamestart && 'hover:scale-125 cursor-pointer'}`}
          onClick={() => {
            if (!gamestart) (document.getElementById('modalopen') as HTMLDialogElement).showModal();
          }}
        />
      </div>
      <div className="w-full h-40 border-custom-white bg-white grid grid-cols-2 grid-rows-2 gap-3 text-xs pt-1.5">
        <div className='flex justify-center items-center text-mint font-extrabold'>개인전</div>
        <div className='flex justify-center items-center text-mint font-extrabold'>객관식</div>
        <div className='flex justify-center items-center text-mint font-extrabold'>20 라운드</div>
        <div className='flex justify-center items-center text-mint font-extrabold'>60초</div>
      </div>
    </div>
  );
};

export default GameRoomSetting;
