import React, { useEffect, useState } from 'react';
import { IoSettings } from 'react-icons/io5';
import { MdKeyboardArrowDown } from 'react-icons/md';
interface GameRoomSettingProps {
  gamestart: boolean;
  gamesetting: Game | null;
}

const GameRoomSetting = ({ gamestart, gamesetting }: GameRoomSettingProps) => {
  const [unfold, setUnfold] = useState<boolean>(false);
  useEffect(() => {
    console.log('게임세팅 확인', gamesetting);
    console.log('모드 확인', gamesetting.mode);
    console.log('게임이 시작됐나요?', gamestart);
  }, []);
  return (
    <div className="w-full mt-1 h-full flex flex-col cursor-default relative">
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
        {/* <IoSettings
          className={`w-5 h-5 transition ${!gamestart && 'hover:scale-125 cursor-pointer'}`}
          onClick={() => {
            if (!gamestart) (document.getElementById('modalopen') as HTMLDialogElement).showModal();
          }}
        /> */}
      </div>
      <div
        className={`absolute w-full grid grid-cols-3 grid-rows-3 bg-white h-20 border-custom-white translate-y-14 transition text-xs origin-top z-10 ${unfold ? '' : 'scale-y-0'}`}
      >
        <div
          className={`flex justify-center items-center font-extrabold text-nowrap ${gamesetting.mode & (1 << 0) ? 'text-mint' : 'text-gray-300'}`}
        >
          객관식
        </div>
        <div
          className={`flex justify-center items-center font-extrabold text-nowrap ${gamesetting.mode & (1 << 2) ? 'text-mint' : 'text-gray-300'}`}
        >
          주관식
        </div>
        <div
          className={`flex justify-center items-center font-extrabold text-nowrap ${gamesetting.mode & (1 << 1) ? 'text-mint' : 'text-gray-300'}`}
        >
          순서배치
        </div>
        <div
          className={`w-full col-span-3 flex justify-center items-center font-extrabold text-nowrap text-mint translate-y-0.5`}
        >
          {gamesetting.maxRounds} 라운드
        </div>
        <div
          className={`w-full col-span-3 flex justify-center items-center font-extrabold text-nowrap text-mint translate-y-1`}
        >
          {gamesetting.timeLimit}초
        </div>
      </div>

      <div
        className="w-full h-8 border-custom-white bg-white grid grid-cols-7 text-xs pt-0.5 cursor-pointer"
        onMouseOver={() => {
          setUnfold(true);
        }}
        onMouseOut={() => {
          setUnfold(false);
        }}
      >
        <div className="col-span-3 flex justify-center items-center text-mint font-extrabold text-nowrap border-r border-gray-200">
          {gamesetting.isTeam ? '팀전' : '개인전'}
        </div>
        <div className="col-span-3 flex justify-center items-center text-mint font-extrabold text-nowrap">
          {gamesetting.style === 'realistic' ? (
            <p>실사체</p>
          ) : gamesetting.style === 'anime' ? (
            <p>만화체</p>
          ) : gamesetting.style === 'cartoon' ? (
            <p>디즈니체</p>
          ) : (
            <p>랜덤</p>
          )}
        </div>
        <div className="h-full flex items-center justify-end text-2xl text-gray-500">
          <MdKeyboardArrowDown />
        </div>
      </div>
    </div>
  );
};

export default GameRoomSetting;
