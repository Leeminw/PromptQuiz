import React, { useEffect, useState } from 'react';
import { IoIosLock } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';
import { LobbyApi } from '../../hooks/axios-lobby';

interface Props {
  roomInfo: RoomProps;
}
const Room = ({ roomInfo }: Props) => {
  const modalId = `my_modal${roomInfo.code}`;
  const navigate = useNavigate();
  const [gamePassword, setGamePassword] = useState<string>('');
  const findRoomType = (type: number) => {
    // 객관식, 주관식, 순서
    switch (roomInfo.mode) {
      case 0:
        return '객관식';
      case 1:
        return '주관식';
      case 2:
        return '순서';
    }
  };
  const gamePasswordHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setGamePassword(event.target.value);
  };
  const passwordCheck = () => {
    if (roomInfo.password === gamePassword) {
      enterRoom();
      // setTimeout(() => {
      //   navigate(`/game/${id}`);
      // }, 1000);
    } else {
      alert('잘못된 비밀번호입니다');
    }
  };
  const chooseRoom = () => {
    if (roomInfo.password) {
      (document.getElementById(modalId) as HTMLDialogElement).showModal(); // 'my_modal_2'
      return;
    } else {
      enterRoom();
    }
  };
  // 인증 절차에 문제가 없을 때 방으로 입장시키는 함수
  const enterRoom = () => {
    const response = LobbyApi.enterGame(roomInfo.code)
      .then((response) => {
        console.log('게임방 진입 성공!');
        console.log(response);
        setTimeout(() => {
          navigate(`/game/${roomInfo.code}`);
        }, 1000);
      })
      .catch((error) => {
        console.log('게임방 진입 실패');
        console.log(error);
      });
  };
  // console.log(`게임방 관련 정보 ${roomInfo.code}`);

  return (
    <div
      className="w-full h-20 relative gap-1 border-2 bg-white/50 border-mint rounded-3xl px-5 py-2 cursor-pointer hover:scale-105 hover:bg-white/80 transition ring-mint hover:ring-2"
      onClick={enterRoom}
    >
      <div className="flex items-start">
        <p className="w-full h-10 font-extrabold line-clamp-2 leading-5 pt-1 text-mint">{roomInfo.title}</p>
        {roomInfo.password ? <IoIosLock className="text-gray-500 min-w-6 min-h-6" /> : ''}
      </div>
      <div className="flex items-end font-bold text-lightmint">
        <span className="w-full text-nowrap text-xs flex gap-1.5">
          <p>{findRoomType(roomInfo.mode)}</p> <p>|</p> <p>{roomInfo.isTeam ? '팀전' : '개인전'}</p>
        </span>
        <span className="text-nowrap text-sm font-extrabold text-mint">
          {roomInfo.curPlayers} / {roomInfo.maxPlayers}
        </span>
      </div>
      <dialog id={modalId} className="modal">
        <div className="modal-box">
          <h3 className="font-bold text-lg">비밀번호를 입력해주세요</h3>
          <input
            type="password"
            placeholder="비밀번호"
            value={gamePassword}
            onChange={gamePasswordHandler}
          />
          <button onClick={passwordCheck}>입력</button>
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

export default Room;
