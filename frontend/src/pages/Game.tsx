import React, { useEffect, useRef, useState } from 'react';
import { IoSettings } from 'react-icons/io5';
import { IoLogOut } from 'react-icons/io5';
import { FaUserPlus } from 'react-icons/fa';
import { IoSend } from 'react-icons/io5';
import GamePlayer from '../components/game/Player';
import SelectionGame from '../components/game/SelectionGame';
import GameChat from '../components/game/GameChat';
import GameRoomSetting from '../components/game/GameRoomSetting';

const GamePage = () => {
  const [gamestart, setGamestart] = useState(false);
  const [earthquake, setEarthquake] = useState(false);

  // 버튼 제어
  // [0]초대하기 | [1]나가기 | [2]1팀 | [3]2팀 | [4]랜덤 | [5]게임시작
  const [activateBtn, setActivateBtn] = useState<ActivateButton>({});
  const [isStart, setIsStart] = useState<boolean>(false);
  const [btnCurrentActivate, setBtnCurrentActivate] = useState<boolean>(false);
  const handleClick = (id: number) => {
    // 버튼 비활성화 상태라면 이벤트 방지
    setIsStart((disable) => {
      if (!disable || id === 1) {
        // 버튼이 활성돼있는 동안 버튼 클릭 방지
        setBtnCurrentActivate((current) => {
          if (!current) {
            setBtnCurrentActivate(true);
            setActivateBtn((prev) => ({ ...prev, [id]: true }));
            setTimeout(() => {
              // 게임 시작 시 버튼 비활성화
              if (id === 5) {
                console.log('start!!!');
                handleGamestart();
                setIsStart(true);
              }
              // 버튼 이벤트 활성화
              setActivateBtn((prev) => ({ ...prev, [id]: false }));
              setTimeout(() => {
                setBtnCurrentActivate(false);
              }, 300);
            }, 400);
            return true;
          }
          return current;
        });
      }
      return disable;
    });
  };

  // 게임 시작 이벤트
  const handleGamestart = () => {
    setGamestart(true);
    setTimeout(() => {
      setEarthquake(true);
      setTimeout(() => {
        setEarthquake(false);
        setTimeout(() => {
          setGamestart(false);
        }, 1000);
      }, 600);
    }, 500);
  };

  return (
    <div
      className={`bg-white/80 w-[75vw] h-[90vh] min-w-[50rem] min-h-[40rem] z-10 
      rounded-3xl drop-shadow-lg px-8 py-6 flex flex-col items-center justify-center 
      ${earthquake ? 'animate-earthquake' : ''}`}
    >
      <div
        className={`absolute bg-no-repeat bg-contain bg-center bg-[url(/public/ui/gamestart.png)] 
        w-full h-full flex items-center justify-center text-white text-6xl z-20 font-extrabold 
        transition ease-in duration-500 ${gamestart ? 'block translate-y-0' : 'translate-y-[-100vh]'}`}
      ></div>
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
          <button
            className={`
            transition text-sm w-1/2 text-white ${activateBtn[0] ? 'animate-clickbtn scale-105' : ''}
            ${
              isStart
                ? 'border-custom-gray bg-[#999999] cursor-default'
                : 'btn-mint-border-white hover:brightness-125 hover:scale-110 cursor-pointer'
            }
            `}
            onClick={() => {
              handleClick(0);
            }}
          >
            <label
              className={`
            flex gap-1 items-center px-2 overflow-hidden max-xl:justify-center font-extrabold
            ${isStart ? 'cursor-default' : 'cursor-pointer'}
            `}
            >
              <FaUserPlus className="min-w-5 min-h-5 mb-0.5" />
              <p
                className="text-center w-full text-nowrap text-sm overflow-hidden 
              text-ellipsis xl:flex max-xl:hidden"
              >
                초대하기
              </p>
            </label>
          </button>
          <button
            className={`btn-red text-white hover:brightness-125 hover:scale-105 transition 
            text-sm w-1/2 min-w-[3rem] ${activateBtn[1] ? 'animate-clickbtn scale-105' : ''}`}
            onClick={() => {
              handleClick(1);
            }}
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
        <div className="w-full h-full flex gap-4 pt-5">
          {/* 좌파 */}
          <div className="w-1/3 flex flex-col gap-3">
            {Array.from({ length: 6 }, (_, index) => (
              <GamePlayer key={index} idx={index + 1} />
            ))}
          </div>
          {/* 문제 화면, 타이머 */}
          <div className="w-full grow flex flex-col">
            <div className="border-custom-mint w-full h-full flex items-center justify-center relative">
              <div
                className="w-16 h-7 absolute top-2 left-2 bg-yellow-500/80 text-white
               rounded-full flex items-center justify-center font-extrabold text-xs border border-gray-300"
              >
                1/20
              </div>
              <div
                className="w-full h-full bg-[url(/public/testphoto.png)] 
              bg-cover bg-center"
              ></div>
            </div>
          </div>
          {/* 우파 */}
          <div className="w-1/3 flex flex-col gap-3">
            {Array.from({ length: 6 }, (_, index) => (
              <GamePlayer key={index} idx={index + 7} />
            ))}
          </div>
        </div>
      </div>
      {/* 광고, 채팅창, 게임 설정 */}
      <div className="w-full h-48 flex gap-4">
        {/* 광고 */}
        <div className="w-1/3 bg-red-200 flex justify-center items-center">광고</div>
        {/* 채팅창, 객관식 선택, 순서 배치 등 */}
        <div className="w-full flex grow flex-col items-center justify-end">
          <div className="w-full h-36 mb-2 relative">
            {/* 객관식 선택 */}
            <SelectionGame />
          </div>
          {/* 채팅 */}
          <GameChat />
        </div>
        {/* 게임 설정 */}
        <div className="w-1/3 flex flex-col cursor-default">
          {/* 방 설정 */}
          <GameRoomSetting gamestart={isStart} />

          {/* 팀 선택, 게임 시작 버튼 */}
          <div className="w-full h-7 my-2 flex gap-2">
            <button
              className={`w-1/3 h-full flex items-center justify-center text-white text-sm font-bold transition 
              ${activateBtn[2] ? 'animate-clickbtn scale-105' : ''}
              ${
                isStart
                  ? 'border-custom-gray bg-[#999999] cursor-default'
                  : 'border-custom-red bg-customRed hover:brightness-125 hover:scale-110 cursor-pointer'
              }
              `}
              onClick={() => {
                handleClick(2);
              }}
            >
              1팀
            </button>
            <button
              className={`w-1/3 h-full flex items-center justify-center
              text-white text-sm font-bold transition 
              ${activateBtn[3] ? 'animate-clickbtn scale-105' : ''}
              ${
                isStart
                  ? 'border-custom-gray bg-[#999999] cursor-default'
                  : 'border-custom-blue bg-customBlue hover:brightness-125 hover:scale-110 cursor-pointer'
              }
              `}
              onClick={() => {
                handleClick(3);
              }}
            >
              2팀
            </button>
            <button
              className={`w-1/3 h-full flex items-center justify-center text-white text-sm font-bold transition
              ${activateBtn[4] ? 'animate-clickbtn scale-105' : ''}
              ${
                isStart
                  ? 'border-custom-gray bg-[#999999] cursor-default'
                  : 'border-custom-green bg-customGreen hover:brightness-125 hover:scale-110 cursor-pointer'
              }
              `}
              onClick={() => {
                handleClick(4);
              }}
            >
              랜덤
            </button>
          </div>
          <button
            className={`w-full h-8 font-extrabold
             transition mb-1 ${activateBtn[5] ? 'animate-clickbtn scale-105' : ''}
             ${
               isStart
                 ? 'border-custom-gray bg-[#999999] cursor-default text-white'
                 : ' btn-mint-border-white hover:brightness-125 hover:scale-110 cursor-pointer'
             }
             `}
            onClick={() => {
              handleClick(5);
            }}
          >
            게임시작
          </button>
        </div>
      </div>
    </div>
  );
};

export default GamePage;
