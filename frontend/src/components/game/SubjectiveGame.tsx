import React, { useEffect, useState } from 'react';

const SubjectiveGame = () => {
  // if (!choiceList) return <div>no game</div>;
  return (
    <div className="w-full h-full flex relative">
      <div className="absolute w-full h-full flex gap-4 opacity-80 -z-10">
        <div className="bg-white border-custom-red text-black w-1/3 h-full flex flex-col items-center justify-center overflow-hidden">
          <div className="text-xl text-center font-bold">
            <p className="font-bold inline">풀</p>
            <p className="font-extrabold inline px-1">[72%]</p>
          </div>
          <div className="text-lg text-center mt-0.5">
            <p className="font-bold inline">비서</p>
            <p className="font-bold inline px-1">[43%]</p>
          </div>
          <div className="text-lg text-center">
            <p className="font-bold inline">꼼짝</p>
            <p className="font-bold inline px-1">[21%]</p>
          </div>
        </div>
        <div className="bg-white border-custom-blue text-black w-1/3 h-full flex flex-col items-center justify-center overflow-hidden">
          <div className="text-xl text-center font-bold">
            <p className="font-bold inline">먹는</p>
            <p className="font-extrabold inline px-1">[72%]</p>
          </div>
          <div className="text-lg text-center mt-0.5">
            <p className="font-bold inline">뜯는</p>
            <p className="font-bold inline px-1">[43%]</p>
          </div>
          <div className="text-lg text-center">
            <p className="font-bold inline">식사하는</p>
            <p className="font-bold inline px-1">[21%]</p>
          </div>
        </div>
        <div className="bg-white border-custom-green text-black w-1/3 h-full flex flex-col items-center justify-center overflow-hidden">
          <div className="text-xl text-center font-bold">
            <p className="font-bold inline">푸바오</p>
            <p className="font-extrabold inline px-1">[72%]</p>
          </div>
          <div className="text-lg text-center mt-0.5">
            <p className="font-bold inline">사장</p>
            <p className="font-bold inline px-1">[43%]</p>
          </div>
          <div className="text-lg text-center">
            <p className="font-bold inline">대통령</p>
            <p className="font-bold inline px-1">[21%]</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SubjectiveGame;
