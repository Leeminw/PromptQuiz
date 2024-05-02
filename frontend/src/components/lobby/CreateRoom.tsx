import React, { useEffect, useState } from 'react';
import { MdAddHome } from 'react-icons/md';
import { LobbyApi } from '../../hooks/axios-lobby';

import instance from '../../hooks/axios-instance';

// 방생성 BE API
// "{
//     ""userId"" : 1,
//     ""channelId"" : 1,
//     ""type"" : 1,
//      ""style"" : 0,
//     ""title"" : ""들어와"",
//     ""password"" : null,
//     ""status"" : true,
//     ""isTeam"" : false,
//     ""curRound"" : 0,
//     ""maxRound"" : 0,
//     ""curPlayers"" : 1,
//     ""maxPlayers"" : 8
// }"

interface Props {
  channelId: number;
}

const CreateRoom = () => {
  const [privacyStatus, setPrivacyStatus] = useState(0);
  const [isTeam, setIsTeam] = useState(false);
  const [type, setType] = useState(0);
  const [maxPlayers, setMaxPlayers] = useState(1);
  const [maxRound, setMaxRound] = useState(1);
  /**로그인 상태 정보를 가져오기 전에 임시로 userId 값을 부여 */
  const [userId, setUserId] = useState(3);
  const [channelId, setChannelId] = useState(1);
  const [status, setStatus] = useState(false);
  const curPlayers = 1;
  const curRound = 0;
  const [style, setStyle] = useState(0);
  const [password, setPassword] = useState('');
  const [title, setTitle] = useState<string>('');

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
    setMaxRound(Number(event.target.value));
    console.log(maxRound);
  };
  const styleHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setStyle(Number(event.target.value));
    console.log(style);
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
    const Room: Room = {
      userId,
      channelId,
      type,
      style,
      title,
      password,
      status,
      isTeam,
      curRound, // 게임 시작전 현재 라운드는 0라운드로 간주
      maxRound,
      curPlayers, // 초기 플레이어는 방장 1명
      maxPlayers,
    };
    console.log('방 생성 정보 받음');
    console.log('사용자id:' + userId);
    console.log('채널id:' + channelId);
    console.log('방유형:' + type);
    console.log('그림체:' + style);
    console.log('방제목:' + title);
    console.log('비밀번호:' + password);
    console.log('방상태:' + status);
    console.log('팀전여부:' + isTeam);
    console.log('현재라운드:' + curRound);
    console.log('최대라운드:' + maxRound);
    console.log('현재플레이어:' + curPlayers);
    console.log('최대플레이어:' + maxPlayers);

    instance.interceptors.request.use();
    const { data } = await LobbyApi.createRoom(Room);
  };
  return (
    // className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 "
    <div>
      <button
        className="btn"
        onClick={() => (document.getElementById('my_modal_1') as HTMLDialogElement).showModal()}
      >
        <MdAddHome />방 만들기
      </button>
      <dialog id="my_modal_1" className="modal">
        <div className="modal-box">
          <h3 className="font-bold text-lg">방 만들기</h3>
          {/* <p className="py-4">내용</p> */}

          <div>
            <div>방 이름</div>

            <input
              type="text"
              className="border"
              placeholder="roomName"
              value={title}
              onChange={titleHandler}
            />
            <div>공개여부</div>
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
            {privacyStatus == 1 ? (
              <input
                type="password"
                placeholder="비밀번호폼"
                onChange={passwordHandler}
                value={password}
              />
            ) : (
              ''
            )}
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
            <input type="radio" value={0} id="choice" onChange={typeHandler} checked={type === 0} />
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
            <div>그림체</div>
            <input type="radio" value={0} id="real" onChange={styleHandler} checked={style === 0} />
            <label htmlFor="real">실사체</label>
            <input
              type="radio"
              value={1}
              id="comic"
              onChange={styleHandler}
              checked={style === 1}
            />
            <label htmlFor="comic">만화체</label>
            <input
              type="radio"
              value={2}
              id="disney"
              onChange={styleHandler}
              checked={style === 2}
            />
            <label htmlFor="disney">디즈니체</label>
            <div>인원수</div>
            <input
              type="number"
              min={1}
              max={12}
              placeholder="인원수입력하세요"
              onChange={maxPlayersHandler}
              //   onChange={typeHandler}
              //   checked={type === 2}
            />
            <div>라운드수</div>
            <input
              type="number"
              min={1}
              max={100}
              placeholder="라운드수입력하세요"
              onChange={maxRoundHandler}
            />
            <button onClick={createRoom}>생성</button>
            <button>취소</button>
          </div>

          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
            </form>
          </div>
        </div>
      </dialog>
    </div>
  );
};

export default CreateRoom;
