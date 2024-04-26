import React, { useEffect, useState } from 'react';
import { UserApi } from '../hooks/axios-user';
import { useNavigate } from 'react-router-dom';
import { FaUser } from 'react-icons/fa';
import { IoMdLock } from 'react-icons/io';

const LoginPage = ({ movePage }: { movePage: () => void }) => {
  const navigate = useNavigate();
  const [activateBtn, setActivateBtn] = useState<ActivateButton>({});
  const [userName, setUserName] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [moveInput, setMoveInput] = useState<boolean>(false);
  const [moveBtn, setMoveBtn] = useState<boolean>(false);

  const handleClick = (id: number) => {
    setActivateBtn((prev) => ({ ...prev, [id]: true }));
    setTimeout(() => {
      setActivateBtn((prev) => ({ ...prev, [id]: false }));
    }, 400);
  };

  const userNameHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(event.target.value);
  };

  const passwordHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const loginUser = () => {
    handleClick(0);
    setTimeout(async () => {
      try {
        const loginForm: LoginForm = {
          userName,
          password,
        };
        console.log(loginForm);
        const { data } = await UserApi.login(loginForm);
        localStorage.setItem('accessToken', data.accessToken);
        alert('로그인 완료!');
        setMoveBtn(false);
        setMoveInput(false);
        setTimeout(() => {
          navigate('/channel');
        }, 1000);
      } catch (error) {
        // 로그인 오류
        console.error(error);
      }
    }, 600);
  };

  useEffect(() => {
    setTimeout(() => {
      setTimeout(() => {
        setMoveBtn(true);
      }, 250);
      setMoveInput(true);
    }, 100);
  }, []);

  return (
    <div className="w-2/3 flex flex-col gap-4 min-w-[24rem] px-10">
      <label
        className={`input input-bordered flex items-center gap-2 rounded-full text-xs bg-white/80 transition duration-1000 cursor-text ${moveInput ? 'translate-x-0' : 'translate-x-[-100vw]'}`}
      >
        <FaUser className="min-w-[1rem] min-h-[1rem] opacity-80 text-mint ml-2" />
        <input
          type="text"
          className="grow text-mint font-bold overflow-hidden pr-2"
          placeholder="아이디"
          value={userName}
          onChange={userNameHandler}
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
              loginUser();
            }
          }}
        />
      </label>
      <label
        className={`input input-bordered flex items-center gap-1 rounded-full text-xs bg-white/80 transition duration-1000 cursor-text ${moveInput ? 'translate-x-0' : 'translate-x-[-100vw]'}`}
      >
        <IoMdLock className="min-w-[1.3rem] min-h-[1.3rem] opacity-80 text-mint ml-1.5" />
        <input
          type="password"
          className="grow text-mint font-bold overflow-hidden pr-2"
          placeholder="비밀번호"
          value={password}
          onChange={passwordHandler}
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
              loginUser();
            }
          }}
        />
      </label>
      <button
        className={`btn-mint hover:brightness-125 transition mt-1 py-[0.2rem] ease-in-out duration-1000 ${moveBtn ? 'translate-y-0' : 'translate-y-[100vh]'} ${activateBtn[0] ? 'animate-clickbtn' : ''}`}
        onClick={loginUser}
      >
        로그인
      </button>
      <button
        className={`btn-white hover:brightness-125 transition mt-1 py-[0.2rem] ease-in-out duration-1000 ${moveBtn ? 'translate-y-0' : 'translate-y-[100vh]'} ${activateBtn[1] ? 'animate-clickwhitebtn' : ''}`}
        onClick={() => {
          handleClick(1);
          setTimeout(() => {
            setMoveBtn(false);
            setMoveInput(false);
            setTimeout(() => {
              movePage();
            }, 1000);
          }, 600);
        }}
      >
        회원가입
      </button>
    </div>
  );
};

export default LoginPage;