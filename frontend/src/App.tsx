import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { IoSettings } from 'react-icons/io5';
import { IoMdHelpCircle } from 'react-icons/io';
const App = () => {
  const [activateBtn, setActivateBtn] = useState<ActivateButton>({});

  const handleClick = (id: number) => {
    setActivateBtn((prev) => ({ ...prev, [id]: true }));
    setTimeout(() => {
      switch (id) {
        case 0:
          (document.getElementById('settingModal') as HTMLDialogElement).showModal();
          break;
        case 1:
          (document.getElementById('helpModal') as HTMLDialogElement).showModal();
          break;
      }
      setActivateBtn((prev) => ({ ...prev, [id]: false }));
    }, 400);
  };

  return (
    <div
      className="w-[100vw] h-[100vh] flex justify-center items-center 
    bg-[url(/public/ui/bg.jpg)] bg-no-repeat bg-cover bg-center overflow-hidden"
    >
      
      <dialog id="helpModal" className="modal">
        <div className="modal-box">
          <h3 className="font-bold text-lg">도움말</h3>
          <p className="py-4">내용</p>
          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
            </form>
          </div>
        </div>
      </dialog>
      <div className="absolute top-4 right-4 flex gap-3">
      <dialog id="settingModal" className="modal">
        <div className="modal-box">
          <h3 className="font-bold text-lg">설정</h3>
          <p className="py-4">내용</p>
          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
            </form>
          </div>
        </div>
      </dialog>
        <button
          className={`btn-mint hover:brightness-125 hover:scale-105 transition text-sm ${activateBtn[0] ? 'animate-clickbtn scale-105' : ''}`}
          onClick={() => {
            handleClick(0);
          }}
        >
          <label className="flex gap-1 items-center h-[1.5rem] px-2 cursor-pointer">
            <IoSettings className="w-8 h-8" />
            <p className="text-center w-full">설정</p>
          </label>
        </button>
        <button
          className={`btn-white-bdmint hover:brightness-125 hover:scale-105 transition text-sm ${activateBtn[1] ? 'animate-clickwhitebtn scale-105' : ''}`}
          onClick={() => {
            handleClick(1);
          }}
        >
          <label className="flex gap-1 items-center h-[1.5rem] px-2 cursor-pointer">
            <IoMdHelpCircle className="w-8 h-8" />
            <p className="text-center w-full">도움말</p>
          </label>
        </button>
      </div>
      <Outlet />
    </div>
  );
};

export default App;
