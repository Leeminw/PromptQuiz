import React, { useEffect, useState } from 'react';

const SequenceGame = () => {
  // if (!choiceList) return <div>no game</div>;
  return (
    <div className="w-full h-full grid grid-rows-2 grid-cols-2 gap-3 text-sm text-white font-extrabold relative">
      <div className='absolute bg-white border-custom-white w-full h-full opacity-80 -z-10'></div>
      <button className='bg-blue-200'>테ㅅ트틍ㄹㄴㅇㄻ</button>
    </div>
  );
};

export default SequenceGame;
