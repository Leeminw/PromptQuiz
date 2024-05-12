import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import { IoSettings } from 'react-icons/io5';
import { IoMdHelpCircle } from 'react-icons/io';
import SettingModal from './components/main/SettingModal';
import HelpModal from './components/main/HelpModal';
import CustomButton from './components/ui/CustomButton';

// 카카오 API 적용을 위해 추가
declare global {
  interface Window {
    Kakao: any;
  }
}
// (document.getElementById('settingModal') as HTMLDialogElement).showModal();
const App = () => {
  // 버튼 제어
  const [btnCurrentActivate, setBtnCurrentActivate] = useState<boolean>(false);
  const activateBtnFunc = async () => {
    setBtnCurrentActivate(true);
    await setTimeout(() => {
      setBtnCurrentActivate(false);
    }, 800);
  };
  return (
    <div
      className="w-[100vw] h-[100vh] flex justify-center items-center 
    bg-[url(/public/ui/bg.jpg)] bg-no-repeat bg-cover bg-center overflow-hidden relative"
    >
      <div className="absolute w-full h-full"></div>

      <SettingModal />
      <HelpModal />
      <div className="absolute top-6 right-6 flex gap-3">
        <CustomButton
          btnCurrentActivate={btnCurrentActivate}
          className="btn-mint flex gap-1 items-center h-8 px-2 cursor-pointer"
          onClick={() => {
            activateBtnFunc();
            setTimeout(() => {
              (document.getElementById('settingModal') as HTMLDialogElement).showModal();
            }, 500);
          }}
        >
          <IoSettings className="lg:w-8 lg:h-8 max-lg:w-6 max-lg:h-6" />
          <p
            className="text-center w-full text-nowrap text-sm overflow-hidden 
              text-ellipsis justify-center lg:flex max-lg:hidden"
          >
            설정
          </p>
        </CustomButton>
        <CustomButton
          btnCurrentActivate={btnCurrentActivate}
          className="btn-white-bdmint flex gap-1 items-center h-8 px-2 cursor-pointer"
          onClick={() => {
            activateBtnFunc();
            setTimeout(() => {
              (document.getElementById('helpModal') as HTMLDialogElement).showModal();
            }, 500);
          }}
        >
          <IoMdHelpCircle className="lg:w-8 lg:h-8 max-lg:w-6 max-lg:h-6" />
          <p
            className="text-center w-full text-nowrap text-sm overflow-hidden 
              text-ellipsis justify-center lg:flex max-lg:hidden"
          >
            도움말
          </p>
        </CustomButton>
      </div>
      <Outlet />
    </div>
  );
};

export default App;
