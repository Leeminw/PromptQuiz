import React from 'react';
import { Outlet } from 'react-router-dom';
const App = () => {
  return (
    <div
      className="w-[100vw] h-[100vh] flex justify-center items-center 
    bg-[url(/public/ui/bg.jpg)] bg-no-repeat bg-cover bg-center overflow-hidden"
    >
      <div className="absolute top-4 right-4 flex flex-col gap-3">
      <button
          className="btn-white-bdmint hover:brightness-90 transition text-xs"
          onClick={() => {
            alert('open modal');
          }}
        >
          설정
        </button>
        <button
          className="btn-white-bdmint hover:brightness-90 transition text-xs"
          onClick={() => {
            alert('open modal');
          }}
        >
          도움말
        </button>
      </div>
      <Outlet />
    </div>
  );
};

export default App;
