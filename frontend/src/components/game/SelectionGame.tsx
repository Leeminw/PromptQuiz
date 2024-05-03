import React, { useEffect, useState } from 'react';
interface SelectionGameProps {
  idx: number;
}

const SelectionGame = () => {
  return (
    <div className="absolute w-full h-full bg-slate-200 grid grid-rows-2 grid-cols-2 gap-2 text-sm text-white font-extrabold">
      <button className="w-full h-full bg-[#e37070] border-custom-red flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition">
        <div className="w-full h-full border-4 border-dashed rounded-lg border-white flex items-center justify-center px-3 py-4">
          1. 인사하는 푸바오의 환영 인사
        </div>
      </button>
      <button
        className={`w-full h-full bg-customYellow border-custom-yellow flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition`}
      >
        <div className="w-full h-full border-4 border-dashed rounded-lg border-white flex items-center justify-center px-3 py-4">
          2. 푸바오의 친근한 손짓
        </div>
      </button>
      <button
        className={`w-full h-full bg-customGreen border-custom-green flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition`}
      >
        <div className="w-full h-full border-4 border-dashed rounded-lg border-white flex items-center justify-center px-3 py-4">
          3. 푸바오의 첫 만남 반응
        </div>
      </button>
      <button
        className={`w-full h-full bg-customBlue border-custom-blue flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition`}
      >
        <div className="w-full h-full border-4 border-dashed rounded-lg border-white flex items-center justify-center px-3 py-4">
          4. 푸바오와의 기념사진 요청
        </div>
      </button>
    </div>
  );
};

export default SelectionGame;
