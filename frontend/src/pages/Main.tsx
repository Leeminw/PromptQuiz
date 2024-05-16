import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import LoginPage from './Login';
import Join from './Join';

const MainPage = () => {
  const navigate = useNavigate();
  const [curpage, setCurpage] = useState<boolean>(false);
  const [mainLoad, setMainLoad] = useState<boolean>(false);
  const [logoMove, setLogoMove] = useState<boolean>(false);
  useEffect(() => {
    setTimeout(() => {
      setMainLoad(true);
    }, 1000);
  }, []);
  const logoMoveFunc = () => {
    setLogoMove(true);
  }
  /**
   * 
   * 회원가입 페이지 ui 구현, 채널 선택으로 라우팅, 설정 및 도움말 modal표시
   * 로그인 로딩, 실패 처리
   * 
   */
  return (
    <div className="flex flex-col items-center w-1/2 z-10">
      <div className={`min-w-[100vw] flex flex-col items-center justify-center mb-4 animate-logo transition duration-1000 ${logoMove?"-translate-y-[100vh]":""}`}>
        <div className="w-full h-28 bg-[url(/public/ui/logo_main.png)] bg-no-repeat bg-contain bg-center" />
        <div className="w-full h-20 bg-[url(/public/ui/logo_sub.png)] bg-no-repeat bg-contain bg-center" />
      </div>
      <div className='min-h-[40vh] flex justify-center items-center'>
        {mainLoad && (
          <div>
            {curpage ? (
              <Join
                movePage={() => {
                  setCurpage(false);
                }}
              />
            ) : (
              <LoginPage
                movePage={() => {
                  setCurpage(true);
                }}
                logoMoveFunc={logoMoveFunc}
              />
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default MainPage;
