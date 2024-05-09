import React, { useEffect, useState } from 'react';
import { MdAddHome } from 'react-icons/md';
import { LobbyApi } from '../../hooks/axios-lobby';

import instance from '../../hooks/axios-instance';
import { useNavigate } from 'react-router-dom';
import useUserStore from '../../stores/userStore';
interface Props {
  channelCode: string;
}

const CreateRoom = ({ channelCode }: Props) => {
  const [privacyStatus, setPrivacyStatus] = useState(0);
  const [isTeam, setIsTeam] = useState(false);
  const [type, setType] = useState(0);
  const [maxPlayers, setMaxPlayers] = useState(1);
  const [rounds, setRounds] = useState(1);
  /**로그인 상태 정보를 가져오기 전에 임시로 userId 값을 부여 */
  // const [userId, setUserId] = useState(3);
  const { user } = useUserStore();
  // const [channelId, setChannelId] = useState(1);
  const [status, setStatus] = useState(false);
  const curPlayers = 1;
  const curRound = 0;
  const [styleIndex, setStyleIndex] = useState(0);
  const [password, setPassword] = useState('');
  const [title, setTitle] = useState<string>('');

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
    setType(Number(event.target.value));
    console.log(type);
  };
  const maxPlayersHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMaxPlayers(Number(event.target.value));
    console.log(maxPlayers);
  };
  const maxRoundHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRounds(Number(event.target.value));
    console.log(rounds);
  };
  const styleHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setStyleIndex(Number(event.target.value));
    console.log(styleIndex);
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
    const styleArr = ['realistic', 'anime', 'cartoon', 'random'];
    const stateNum = status ? 1 : 0;
    const style = styleArr[styleIndex];
    // const style = 2; // 임시값

    if (privacyStatus === 1 && password.trim().length === 0) {
      alert('비공개 방의 비밀번호를 입력해주세요');
      return;
    }

    const Room: Room = {
      userId: user.userId,
      channelCode,
      type,
      style,
      title,
      password,
      status,
      isTeam,
      curRound, // 게임 시작전 현재 라운드는 0라운드로 간주
      rounds,
      curPlayers, // 초기 플레이어는 방장 1명
      maxPlayers,
    };
    console.log('방 생성 정보 받음');
    console.log('사용자id:' + user.userId);
    console.log('채널id:' + channelCode);
    console.log('방유형:' + type);
    console.log('그림체:' + style);
    console.log('방제목:' + title);
    console.log('비밀번호:' + password);
    console.log('방상태:' + status);
    console.log('팀전여부:' + isTeam);
    console.log('현재라운드:' + curRound);
    console.log('최대라운드:' + rounds);
    console.log('현재플레이어:' + curPlayers);
    console.log('최대플레이어:' + maxPlayers);

    console.log('---------');
    console.log(Room);
    try {
      const { data } = await LobbyApi.createRoom(Room);
      console.log(data);
      setTimeout(() => {
        navigate(`/game/${data.code}`);
      }, 1000);
    } catch (error) {
      console.error(error);
    }
  };
  return (
    // className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 "
    <div className="w-fit h-full">
      <button
        className="w-full h-full btn-mint flex justify-center items-center gap-2 px-1 hover:brightness-110"
        onClick={() => (document.getElementById('my_modal_1') as HTMLDialogElement).showModal()}
      >
        <MdAddHome className="min-w-5 min-h-5 " />
        <p className="text-nowrap">방 만들기</p>
      </button>
      <dialog id="my_modal_1" className="modal">
        <div className="modal-box">
          <h3 className="font-bold text-2xl ">방 만들기</h3>
          {/* <p className="py-4">내용</p> */}

          <div className="pt-4 flex flex-col gap-3">
            <div className="flex items-center gap-3">
              <span className="font-bold text-nowrap">방 이름</span>
              <input
                type="text"
                className="input input-bordered input-sm w-full"
                placeholder="최대 20자"
                value={title}
                onChange={titleHandler}
              />
            </div>
            <div className="flex items-center gap-3">
              <span className="font-bold">공개여부</span>
              <input
                type="radio"
                value={0}
                id="open"
                onChange={privacyHandler}
                checked={privacyStatus == 0}
              />
              <label htmlFor="open">공개</label>
              <input
                type="radio"
                value={1}
                id="close"
                onChange={privacyHandler}
                checked={privacyStatus == 1}
              />
              <label htmlFor="close">비공개</label>
              {privacyStatus == 1 && (
                <input
                  type="password"
                  placeholder="비밀번호폼"
                  onChange={passwordHandler}
                  value={password}
                />
              )}
            </div>
            <div>
              <div>게임 종류</div>
              <input
                type="radio"
                value={0}
                id="individual"
                onChange={isTeamHandler}
                checked={!isTeam}
              />
              <label htmlFor="individual">개인전</label>
              <input type="radio" value={1} id="team" onChange={isTeamHandler} checked={isTeam} />
              <label htmlFor="team">팀전</label>

              <hr />
              <input
                type="radio"
                value={0}
                id="choice"
                onChange={typeHandler}
                checked={type === 0}
              />
              <label htmlFor="choice">객관식</label>
              <input
                type="radio"
                value={1}
                id="subjective"
                onChange={typeHandler}
                checked={type === 1}
              />
              <label htmlFor="subjective">주관식</label>
              <input
                type="radio"
                value={2}
                id="sequence"
                onChange={typeHandler}
                checked={type === 2}
              />
              <label htmlFor="sequence">순서</label>
            </div>
            <div>
              <div>그림체</div>
              <input
                type="radio"
                value={0}
                id="real"
                onChange={styleHandler}
                checked={styleIndex === 0}
              />
              <label htmlFor="real">실사체</label>
              <input
                type="radio"
                value={1}
                id="comic"
                onChange={styleHandler}
                checked={styleIndex === 1}
              />
              <label htmlFor="comic">만화체</label>
              <input
                type="radio"
                value={2}
                id="disney"
                onChange={styleHandler}
                checked={styleIndex === 2}
              />
              <label htmlFor="disney">디즈니체</label>
              <input
                type="radio"
                value={3}
                id="random"
                onChange={styleHandler}
                checked={styleIndex === 3}
              />
              <label htmlFor="random">랜덤</label>
            </div>
            <div>
              <div>인원수</div>
              <input
                type="number"
                min={1}
                max={12}
                placeholder="인원수입력하세요"
                onChange={maxPlayersHandler}
              />
            </div>
            <div>
              <div>라운드수</div>
              <input
                type="number"
                min={1}
                max={100}
                placeholder="라운드수입력하세요"
                onChange={maxRoundHandler}
              />
            </div>
            <div>
              <button onClick={createRoom}>생성</button>
              <button>취소</button>
            </div>
          </div>

          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-4 top-5 text-lg">
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
