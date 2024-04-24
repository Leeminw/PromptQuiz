import React from 'react';
import { Outlet } from 'react-router-dom';

const App = () => {
  return (
    <div
      className="w-[100vw] h-[100vh] flex justify-center items-center 
    bg-[url(/public/ui/bg.jpg)] bg-no-repeat bg-cover bg-center overflow-hidden"
    >
      <Outlet />
    </div>
  );
};

export default App;
