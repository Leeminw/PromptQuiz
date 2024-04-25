import React from 'react';
import { useState } from 'react';
import { UserApi } from '../hooks/axios-user';
import { useNavigate } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import { IoMdLock } from 'react-icons/io';

const LoginPage = () => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const userNameHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(event.target.value);
  };

  const passwordHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const loginUser = async () => {
    try {
      const loginForm: LoginForm = {
        userName,
        password,
      };
      console.log(loginForm);
      const { data } = await UserApi.login(loginForm);
      localStorage.setItem('accessToken', data.accessToken);
      alert('로그인 완료!');
      navigate('/', { replace: true });
    } catch (error) {
      // 로그인 오류
      console.error(error);
    }
  };

  return (
    <div className="flex flex-col items-center w-1/2">
      <div className="min-w-[100vw] flex flex-col items-center justify-center mb-4 animate-logo">
        <div className="w-full h-28 bg-[url(/public/ui/logo_main.png)] bg-no-repeat bg-contain bg-center" />
        <div className="w-full h-20 bg-[url(/public/ui/logo_sub.png)] bg-no-repeat bg-contain bg-center" />
      </div>
      <div className="w-2/3 flex flex-col gap-4 min-w-[24rem] px-10">
        <label className="input input-bordered flex items-center gap-2 rounded-full text-xs bg-white/80 cursor-text animate-logininput">
          <FaUser className="min-w-[1rem] min-h-[1rem] opacity-80 text-mint ml-2" />
          <input
            type="text"
            className="grow text-mint font-bold overflow-hidden pr-2"
            placeholder="아이디"
            value={userName}
            onChange={userNameHandler}
          />
        </label>
        <label className="input input-bordered flex items-center gap-1 rounded-full text-xs bg-white/80 cursor-text animate-logininput">
          <IoMdLock className="min-w-[1.3rem] min-h-[1.3rem] opacity-80 text-mint ml-1.5" />
          <input
            type="password"
            className="grow text-mint font-bold overflow-hidden pr-2"
            placeholder="비밀번호"
            value={password}
            onChange={passwordHandler}
          />
        </label>
        <button
          className="btn-mint hover:brightness-125 transition mt-1 animate-loginbtn"
          onClick={loginUser}
        >
          로그인
        </button>
        <button
          className="btn-white hover:brightness-90 transition mt-1 animate-loginbtn"
          onClick={() => {
            navigate('/join');
          }}
        >
          회원가입
        </button>
      </div>
    </div>
  );
};

export default LoginPage;
