import React, { useEffect, useState } from 'react';
import { MdAddHome } from 'react-icons/md';
import { LobbyApi } from '../../hooks/axios-lobby';

import instance from '../../hooks/axios-instance';
import { useNavigate } from 'react-router-dom';
import useUserStore from '../../stores/userStore';
import CustomButton from '../ui/CustomButton';
interface Props {
  channelCode: string;
}

const CreateRoom = ({ channelCode }: Props) => {
  const mappingStyle: string[] = ['realistic', 'anime', 'cartoon', 'random'];
  const [privacyStatus, setPrivacyStatus] = useState(0);
  const [isTeam, setIsTeam] = useState(false);
  const [mode, setMode] = useState(5);
  const [maxPlayers, setMaxPlayers] = useState(12);
  const [maxRounds, setMaxRounds] = useState(10);
  const [maxTimeLimit, setMaxTimeLimit] = useState(30);
  const { user } = useUserStore();
  const [status, setStatus] = useState(false);
  const curPlayers = 1;
  const curRound = 0;
  const [style, setStyle] = useState(3);
  const [password, setPassword] = useState('');
  const [title, setTitle] = useState<string>('');

  // 버튼 제어
  const [btnCurrentActivate, setBtnCurrentActivate] = useState<boolean>(false);
  const activateBtnFunc = async () => {
    setBtnCurrentActivate(true);
    await setTimeout(() => {
      setBtnCurrentActivate(false);
    }, 800);
  };

  const navigate = useNavigate();
  const privacyHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPrivacyStatus(Number(event.target.value));
    console.log(privacyStatus);
  };
  const isTeamHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setIsTeam(Number(event.target.value) === 1);
    console.log(isTeam);
  };
  const typeHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    // XOR토글 형식으로 변경
    setMode(mode ^ Number(event.target.value));
    console.log('TYPE', event.target.value);
    // console.log(mode);
  };
  const maxPlayersHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMaxPlayers(Number(event.target.value));
    console.log(maxPlayers);
  };
  const maxRoundHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMaxRounds(Number(event.target.value));
    console.log(maxRounds);
  };
  const maxTimeLimitHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMaxTimeLimit(Number(event.target.value));
    console.log(maxTimeLimit);
  };
  const styleHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setStyle(Number(event.target.value));
    console.log('style', event.target.value);
  };

  const titleHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
    console.log(title);
  };
  const passwordHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
    console.log(password);
  };
  const createRoom = async () => {
    const stateNum = status ? 1 : 0;
    // const style = styleArr[styleIndex]; // 다시 string에서 number로 변경
    // const style = 2; // 임시값

    if (title.trim().length === 0) {
      alert('방 이름을 입력해주세요');
      return;
    }
    if (privacyStatus === 1 && password.trim().length === 0) {
      alert('비밀번호를 입력해주세요');
      return;
    }
    if (mode === 0) {
      alert('모드를 선택해주세요');
      return;
    }

    // console.log('방 생성 정보 받음');
    // // console.log('사용자id:' + user.userId);
    // // console.log('채널id:' + channelCode);
    // console.log('방 생성 정보 받음');
    // console.log('사용자id:' + user.userId);
    // console.log('채널id:' + channelCode);
    // // console.log('방유형:' + type);
    // console.log('그림체:' + style);
    // console.log('방제목:' + title);
    // console.log('비밀번호:' + password);
    // console.log('방상태:' + status);
    // console.log('팀전여부:' + isTeam);
    // console.log('현재라운드:' + curRound);
    // // console.log('최대라운드:' + rounds);
    // console.log('현재플레이어:' + curPlayers);
    // console.log('최대플레이어:' + maxPlayers);

    // console.log('---------');

    try {
      const room: CreateRoom = {
        channelCode,
        isPrivate: privacyStatus === 1,
        isTeam,
        maxPlayers,
        maxRounds,
        mode,
        password,
        style: mappingStyle[style],
        timeLimit: maxTimeLimit,
        title,
      };

      console.log(room);
      const { data } = await LobbyApi.createRoom(room);
      navigate(`/game/${data.code}`);
    } catch (error) {
      console.error(error);
    }
  };
  return (
    // className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 "
    <div className="w-fit h-full text-mint">
      <button
        className="w-full h-full btn-mint-border-white flex justify-center items-center gap-2 px-3 hover:brightness-110"
        onClick={() => (document.getElementById('my_modal_1') as HTMLDialogElement).showModal()}
      >
        <MdAddHome className="min-w-5 min-h-5" />
        <p className="text-nowrap md:flex max-md:hidden select-none">방 만들기</p>
      </button>
      <dialog id="my_modal_1" className="modal">
        <div className="modal-box border-2 border-lightmint flex flex-col gap-2 pb-12 bg-white/90 backdrop-blur-lg min-w-96">
          <h3 className="font-bold text-2xl">방 만들기</h3>
          <hr className="mb-1 border-extralightmint" />
          <div className="flex flex-col gap-2 overflow-x-hidden overflow-y-scroll custom-scroll">
            <div className="flex items-center gap-3 mt-1">
              <span className="font-extrabold text-nowrap pr-6">방 이름</span>
              <input
                type="text"
                className="input input-bordered input-sm w-full rounded-full border-2 border-lightmint mr-1 pl-4 bg-white/60"
                placeholder="방 이름 (최대 20자)"
                maxLength={20}
                value={title}
                onChange={titleHandler}
              />
            </div>
            <div className="flex items-center gap-3">
              <div className="flex pr-2 items-center">
                <span className="font-extrabold text-nowrap pr-7">공개여부</span>
                <input
                  type="radio"
                  value={0}
                  id="open"
                  onChange={privacyHandler}
                  checked={privacyStatus == 0}
                  className="radio radio-sm border-lightmint bg-white checked:bg-mint"
                />
                <label
                  htmlFor="open"
                  className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                >
                  공개
                </label>
              </div>
              <div className="flex pr-2 items-center">
                <input
                  type="radio"
                  value={1}
                  id="close"
                  onChange={privacyHandler}
                  checked={privacyStatus == 1}
                  className="radio radio-sm border-lightmint bg-white checked:bg-mint"
                />
                <label
                  htmlFor="close"
                  className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                >
                  비공개
                </label>
              </div>

              <input
                type="password"
                disabled={privacyStatus != 1 && true}
                className="input input-bordered input-sm w-full rounded-full border-2 border-lightmint mr-1 pl-4 bg-white/70"
                maxLength={20}
                placeholder="비밀번호 (최대 20자)"
                onChange={passwordHandler}
                value={password}
              />
            </div>
            <span className="font-extrabold text-nowrap">게임 종류</span>
            <div className="flex gap-2 flex-col">
              <div className="flex gap-3">
                <div className="flex pr-2 items-center">
                  <input
                    type="radio"
                    value={0}
                    id="individual"
                    onChange={isTeamHandler}
                    checked={!isTeam}
                    className="radio radio-sm border-lightmint bg-white checked:bg-mint"
                  />
                  <label
                    htmlFor="individual"
                    className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                  >
                    개인전
                  </label>
                </div>
                <div className="flex pr-2 items-center">
                  <input
                    type="radio"
                    value={1}
                    id="team"
                    onChange={isTeamHandler}
                    checked={isTeam}
                    disabled
                    className="radio radio-sm border-lightmint bg-white checked:bg-mint"
                  />
                  <label
                    htmlFor="team"
                    className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                  >
                    팀전
                  </label>
                </div>
              </div>
              <div className="flex gap-3">
                <div className="flex pr-2 items-center">
                  <input
                    type="checkbox"
                    value={1}
                    id="choice"
                    defaultChecked
                    onChange={typeHandler}
                    checked={(mode & 1) > 0}
                    className="checkbox checkbox-sm border-lightmint bg-white checked:bg-mint [--chkbg:theme(colors.mint)] [--chkfg:white]"
                  />
                  <label
                    htmlFor="choice"
                    className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                  >
                    객관식
                  </label>
                </div>
                <div className="flex pr-2 items-center">
                  <input
                    type="checkbox"
                    value={4}
                    id="subjective"
                    defaultChecked
                    onChange={typeHandler}
                    checked={(mode & 4) > 0}
                    className="checkbox checkbox-sm border-lightmint bg-white checked:bg-mint [--chkbg:theme(colors.mint)] [--chkfg:white]"
                  />
                  <label
                    htmlFor="subjective"
                    className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                  >
                    주관식
                  </label>
                </div>

                <div className="flex pr-2 items-center">
                  <input
                    type="checkbox"
                    value={2}
                    id="sequence"
                    disabled
                    // onChange={typeHandler}
                    // checked={(mode & 2) > 0}
                    className="checkbox checkbox-sm border-lightmint bg-white checked:bg-mint [--chkbg:theme(colors.mint)] [--chkfg:white]"
                  />
                  <label
                    htmlFor="sequence"
                    className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                  >
                    순서 맞추기
                  </label>
                </div>
              </div>
            </div>
            <span className="font-extrabold text-nowrap mt-2">그림체</span>
            <div className="flex gap-3">
              <div className="flex pr-2 items-center">
                <input
                  type="radio"
                  value={0}
                  id="real"
                  onChange={styleHandler}
                  checked={style === 0}
                  className="radio radio-sm border-lightmint bg-white checked:bg-mint"
                />
                <label
                  htmlFor="real"
                  className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                >
                  실사체
                </label>
              </div>
              <div className="flex pr-2 items-center">
                <input
                  type="radio"
                  value={1}
                  id="comic"
                  onChange={styleHandler}
                  checked={style === 1}
                  className="radio radio-sm border-lightmint bg-white checked:bg-mint"
                />
                <label
                  htmlFor="comic"
                  className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                >
                  만화체
                </label>
              </div>
              <div className="flex pr-2 items-center">
                <input
                  type="radio"
                  value={2}
                  id="disney"
                  onChange={styleHandler}
                  checked={style === 2}
                  className="radio radio-sm border-lightmint bg-white checked:bg-mint"
                />
                <label
                  htmlFor="disney"
                  className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                >
                  디즈니체
                </label>
              </div>

              <div className="flex pr-2 items-center">
                <input
                  type="radio"
                  value={3}
                  id="random"
                  onChange={styleHandler}
                  checked={style === 3}
                  className="radio radio-sm border-lightmint bg-white checked:bg-mint"
                />
                <label
                  htmlFor="random"
                  className="text-nowrap text-sm font-bold pl-2 cursor-pointer select-none"
                >
                  랜덤
                </label>
              </div>
            </div>
            <span className="font-extrabold text-nowrap mt-2">인원 수</span>
            <div className="w-full">
              <input
                type="range"
                min={2}
                max="12"
                className="range range-xs [--range-shdw:#359DB0]"
                step="1"
                onChange={maxPlayersHandler}
              />
              <div className="w-full flex justify-between text-xs">
                <span className="pl-1.5">2</span>
                <span className="pl-1.5">3</span>
                <span className="pl-1.5">4</span>
                <span className="pl-1.5">5</span>
                <span className="pl-1.5">6</span>
                <span className="pl-1.5">7</span>
                <span className="pl-1.5">8</span>
                <span className="pl-1.5">9</span>
                <span>10</span>
                <span>11</span>
                <span>12</span>
              </div>
            </div>
            <span className="font-extrabold text-nowrap mt-1">라운드 수</span>
            <div className="w-full">
              <input
                type="range"
                min={5}
                max="25"
                className="range range-xs [--range-shdw:#359DB0]"
                step="5"
                value={maxRounds}
                onChange={maxRoundHandler}
              />
              <div className="w-full flex justify-between text-xs">
                <span>5</span>
                <span>10</span>
                <span>15</span>
                <span>20</span>
                <span>25</span>
              </div>
            </div>
            <span className="font-extrabold text-nowrap mt-1">시간 제한</span>
            <div className="w-full">
              <input
                type="range"
                min={10}
                max="60"
                className="range range-xs [--range-shdw:#359DB0]"
                step="10"
                value={maxTimeLimit}
                onChange={maxTimeLimitHandler}
              />
              <div className="w-full flex justify-between text-xs">
                <span>10</span>
                <span>20</span>
                <span>30</span>
                <span>40</span>
                <span>50</span>
                <span>60</span>
              </div>
            </div>
            <div className="absolute bottom-6 right-6 flex gap-4 w-full justify-end">
              <CustomButton
                className="bg-mint text-white btn-mint px-6"
                btnCurrentActivate={btnCurrentActivate}
                onClick={() => {
                  activateBtnFunc();
                  setTimeout(() => {
                    createRoom();
                  }, 500);
                }}
              >
                생성
              </CustomButton>
              <form method="dialog">
                <button className="bg-[#999999] text-white border-custom-gray px-6 font-bold hover:brightness-110">
                  취소
                </button>
              </form>
            </div>
          </div>

          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-4 top-5 text-lg ">
                ✕
              </button>
            </form>
          </div>
        </div>
      </dialog>
    </div>
  );
};

export default CreateRoom;
