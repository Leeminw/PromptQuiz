import React, { useEffect, useState } from 'react';
import { UserApi } from '../hooks/axios-user';
import { useNavigate } from 'react-router-dom';
const Join = ({ movePage }: { movePage: () => void }) => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [nickName, setNickName] = useState<string>('');
  const [picture, setPicture] = useState<string>('');
  // 아이디 중복체크
  const [isExist, setIsExist] = useState<boolean | null>(null);
  const [pictureList, setPictureList] = useState<string[]>([]);

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
      console.log('이미 있는 아이디야');
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
      navigate('/', { replace: true });
    } catch (error) {
      console.error(error);
      return;
    }
  };

  return (
    <div>
      <div>회원가입 페이지</div>
      <input
        type="text"
        className="border"
        placeholder="userName"
        value={userName}
        onChange={userNameHandler}
      />
      <p> {isExist ? '중복된 아이디입니다.' : '사용가능한 아이디입니다.'}</p>
      <input
        type="password"
        className="border"
        placeholder="password"
        value={password}
        onChange={passwordHandler}
      />
      <br />
      <input
        type="text"
        className="border"
        placeholder="nickName"
        value={nickName}
        onChange={nickNameHandler}
      />
      <br />
      <select value={picture} onChange={pictureHandler}>
        <option value="">프로필 선택</option>
        {pictureList?.map((picture, index) => (
          <option key={index} value={picture}>
            {picture}
          </option>
        ))}
      </select>
      <br />
      <button onClick={movePage}>뒤로가기</button>
      <button onClick={joinUser} className="border">
        회원 가입
      </button>
    </div>
  );
};

export default Join;
