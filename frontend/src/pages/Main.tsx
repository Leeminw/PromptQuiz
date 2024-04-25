import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginPage from './Login';
import Join from './Join';

const MainPage = () => {
  const navigate = useNavigate();
  const [curpage, setCurpage] = useState<boolean>(false);
  const [mainLoad, setMainLoad] = useState<boolean>(false);
  useEffect(() => {
    setTimeout(() => {
      setMainLoad(true);
    }, 1000);
  }, []);

  return (
    <div className="flex flex-col items-center w-1/2">
      <div className="min-w-[100vw] flex flex-col items-center justify-center mb-4 animate-logo">
        <div className="w-full h-28 bg-[url(/public/ui/logo_main.png)] bg-no-repeat bg-contain bg-center" />
        <div className="w-full h-20 bg-[url(/public/ui/logo_sub.png)] bg-no-repeat bg-contain bg-center" />
      </div>
      <div className='min-h-[40vh] flex justify-center items-center'>
        {mainLoad && (
          <div>
            {curpage ? (
              <Join
                movePage={() => {
                  setCurpage(!curpage);
                }}
              />
            ) : (
              <LoginPage
                movePage={() => {
                  setCurpage(!curpage);
                }}
              />
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default MainPage;
