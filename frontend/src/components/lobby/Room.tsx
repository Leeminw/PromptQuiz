import React, { useEffect, useState } from 'react';
import { IoIosLock } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';

interface Props {
  id: number;
  channelId: number;
  type: number;
  style: number;
  code: string;
  title: string;
  password: string | null;
  status: boolean;
  isTeam: boolean;
  curRound: number;
  rounds: number;
  curPlayers: number;
  maxPlayers: number;
}
const Room = ({
  id,
  channelId,
  type,
  style,
  code,
  title,
  password,
  status,
  isTeam,
  curRound,
  rounds,
  curPlayers,
  maxPlayers,
}: Props) => {
  const modalId = `my_modal${id}`;
  const navigate = useNavigate();
  const [gamePassword, setGamePassword] = useState<string>('');
  const findRoomType = (type: number) => {
    // 객관식, 주관식, 순서
    switch (type) {
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
    if (password === gamePassword) {
      setTimeout(() => {
        navigate(`/game/${id}`);
      }, 1000);
    } else {
      alert('잘못된 비밀번호입니다');
    }
  };
  const enterRoom = () => {
    if (password) {
      (document.getElementById(modalId) as HTMLDialogElement).showModal(); // 'my_modal_2'
      return;
    } else {
      setTimeout(() => {
        navigate(`/game/${id}`);
      }, 1000);
    }
  };
  return (
    <div
      className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 border-mint rounded-3xl "
      onClick={enterRoom}
    >
      <div>
        {title}
        {password ? <IoIosLock /> : ''}
      </div>
      <div className="flex-col items-center">
        <span>
          {findRoomType(type)} | {isTeam ? '팀전' : '개인전'}
        </span>
        <span>
          {curPlayers} / {maxPlayers}
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
