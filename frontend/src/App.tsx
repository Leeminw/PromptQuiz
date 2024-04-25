import React from 'react';
import { Outlet } from 'react-router-dom';
import { IoSettings } from 'react-icons/io5';
import { IoMdHelpCircle } from 'react-icons/io';
const App = () => {
  return (
    <div
      className="w-[100vw] h-[100vh] flex justify-center items-center 
    bg-[url(/public/ui/bg.jpg)] bg-no-repeat bg-cover bg-center overflow-hidden"
    >
      <div className="absolute top-4 right-4 flex flex-col gap-3">
        <button
          className="btn-mint  hover:brightness-125 transition text-sm"
          onClick={() => {
            alert('open modal');
          }}
        >
          <label className="flex gap-1 items-center h-[1.5rem] px-2 cursor-pointer">
            <IoSettings className='w-8 h-8' />
            <p className="text-center w-full">설정</p>
          </label>
        </button>
        <button
          className="btn-white-bdmint hover:brightness-125 transition text-sm"
          onClick={() => {
            alert('open modal');
          }}
        >
          <label className="flex gap-1 items-center h-[1.5rem] px-2 cursor-pointer">
            <IoMdHelpCircle className='w-8 h-8' />
            <p className="text-center w-full">도움말</p>
          </label>
        </button>
      </div>
      <Outlet />
    </div>
  );
};

export default App;
