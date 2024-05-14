import React, { useEffect, useRef, useState } from 'react';
import { UserApi } from '../hooks/axios-user';
import CustomButton from '../components/ui/CustomButton';
const Join = ({ movePage }: { movePage: () => void }) => {
  const [userName, setUserName] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [nickName, setNickName] = useState<string>('');
  const [picture, setPicture] = useState<string>('');
  // 아이디 중복체크
  const [isExist, setIsExist] = useState<boolean | null>(null);
  const [pictureList, setPictureList] = useState<string[]>([]);
  const inputId = useRef(null);

  useEffect(() => {
    // 여기서 프로필 리스트 가져오자.
    setPictureList([
      'https://url1',
      'https://url2',
      'https://url3',
      'https://url4',
      'https://url5',
    ]);
  }, []);

  const userNameHandler = async (event: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(event.target.value);
    try {
      const response = await UserApi.isExistUserName(event.target.value);
      setIsExist(response.data);
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };
  const passwordHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };
  const nickNameHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setNickName(event.target.value);
  };
  const pictureHandler = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setPicture(event.target.value);
  };
  function isUserName(value: string) {
    return /^[a-z]+[a-z0-9]{5,19}$/.test(value);
  }
  const joinUser = async () => {
    if (isExist) {
      alert('이미 존재하는 아이디입니다.');
      return;
    }
    const joinForm: JoinForm = {
      userName,
      password,
      nickName,
      picture,
    };
    console.log(joinForm);
    try {
      const response = await UserApi.join(joinForm);
      console.log(response);
      alert('회원가입이 완료되었습니다.');
      movePage();
    } catch (error) {
      console.error(error);
      return;
    }
  };

  // 버튼 제어
  const [btnCurrentActivate, setBtnCurrentActivate] = useState<boolean>(false);
  const activateBtnFunc = async () => {
    setBtnCurrentActivate(true);
    await setTimeout(() => {
      setBtnCurrentActivate(false);
    }, 800);
  };
  const [moveModal, setMoveModal] = useState<boolean>(false);
  const [joinSuccess, setJoinSuccess] = useState<boolean>(false);

  useEffect(() => {
    inputId.current?.focus();
    setTimeout(() => {
      setMoveModal(true);
    }, 50);
  }, []);

  return (
    <div
      className={`w-full rounded-2xl border-2 border-lightmint flex flex-col gap-3 pb-20 bg-white/90 backdrop-blur-lg min-w-96 py-4 px-4 transition duration-1000 ease-in-out ${moveModal ? 'translate-x-0' : 'translate-x-[-100vw]'}`}
    >
      <h3 className="font-bold text-2xl text-mint pl-2">회원 가입</h3>
      <hr className="mb-1 border-extralightmint" />
      <div className="flex flex-col gap-3 overflow-x-hidden overflow-y-scroll custom-scroll">
        <div className="flex items-center mt-1 flex-col gap-1">
          <div className="w-full flex items-center px-2">
            <span className="font-extrabold text-nowrap pr-4 w-24 text-mint">아이디</span>
            <input
              type="text"
              ref={inputId}
              maxLength={20}
              placeholder="최대 20글자 입력..."
              className="input input-bordered input-sm w-full rounded-full border-2 border-lightmint mr-1 pl-4 bg-white/60"
              value={userName}
              onChange={userNameHandler}
            />
          </div>
          <div className={`${inputId.current?.value.length > 0 ? 'opacity-100' : 'opacity-0'}`}>
            {isExist ? (
              <p className="text-red-400 text-sm">중복된 아이디입니다.</p>
            ) : (
              <p className="text-green-500 text-sm">사용가능한 아이디입니다.</p>
            )}
          </div>

          <div className="w-full flex items-center px-2">
            <span className="font-extrabold text-nowrap pr-4 w-24 text-mint">비밀번호</span>
            <input
              type="password"
              className="input input-bordered input-sm w-full rounded-full border-2 border-lightmint mr-1 pl-4 bg-white/70"
              maxLength={20}
              placeholder="최대 20글자 입력..."
              value={password}
              onChange={passwordHandler}
            />
          </div>
          <div className="w-full flex items-center px-2">
            <span className="font-extrabold text-nowrap pr-4 w-24 text-mint">닉네임</span>
            <input
              type="text"
              className="input input-bordered input-sm w-full rounded-full border-2 border-lightmint mr-1 pl-4 bg-white/60"
              value={nickName}
              maxLength={15}
              placeholder="최대 15글자 입력..."
              onChange={nickNameHandler}
            />
          </div>
        </div>

        <div className="absolute bottom-6 right-6 flex gap-4 w-full justify-end">
          {joinSuccess ? (
            <div className="border-custom-gray bg-[#999999] text-white px-6 font-bold select-none">
              회원가입
            </div>
          ) : (
            <CustomButton
              btnCurrentActivate={btnCurrentActivate}
              className="btn-mint bg-mint text-white px-6"
              onClick={() => {
                activateBtnFunc();
                setTimeout(() => {
                  joinUser();
                }, 500);
              }}
            >
              회원가입
            </CustomButton>
          )}
          <CustomButton
            btnCurrentActivate={btnCurrentActivate}
            className="bg-[#999999] text-white border-custom-gray px-6 font-bold"
            onClick={() => {
              activateBtnFunc();
              setTimeout(() => {
                setMoveModal(false);
                setTimeout(() => {
                  movePage();
                }, 500);
              }, 500);
            }}
          >
            돌아가기
          </CustomButton>
        </div>
      </div>
      {/* <select value={picture} onChange={pictureHandler}>
        <option value="">프로필 선택</option>
        {pictureList?.map((picture, index) => (
          <option key={index} value={picture}>
            {picture}
          </option>
        ))}
      </select> */}
    </div>
  );
};

export default Join;
