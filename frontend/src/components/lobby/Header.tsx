import React, { useEffect, useState } from 'react';

import { ImExit } from 'react-icons/im';
import { BsFillTrophyFill } from 'react-icons/bs';
import { IoMdRefresh } from 'react-icons/io';
import { FaForward } from 'react-icons/fa6';
import { MdAddHome } from 'react-icons/md';
import { Link, useNavigate } from 'react-router-dom';
import CreateRoom from './CreateRoom';
import { LobbyApi } from '../../hooks/axios-lobby';
import { UserChannelApi } from '../../hooks/axios-user-channel';
import { error } from 'console';
interface Props {
  channelId: number;
  handleState: (data: RoomProps[]) => void;
}
// const Header = ({ channelId }: Props, handleState: (data: RoomProps[]) => void) => {
const Header = ({ channelId, handleState }: Props) => {
  const navigate = useNavigate();
  const fastMatching = () => {
    alert('빠른대전 매칭완료!');
  };
  const refresh = async () => {
    alert('새로고침 버튼 누름');

    const { data } = await LobbyApi.getGameList(channelId);
    console.log('이거는 패치할 데이터 정보임');

    console.log(data);
    handleState(data);
  };
  const ranking = () => {};
  const exitChannel = () => {
    const response = UserChannelApi.exitChannel()
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
    setTimeout(() => {
      navigate(`/channel`);
    }, 1000);
  };

  return (
    <nav className="w-full h-[2rem] flex gap-4">
      <CreateRoom channelId={channelId} />
      <button
        className="w-fit h-full btn-mint hover:brightness-110 flex justify-center items-center gap-2 px-2"
        onClick={fastMatching}
      >
        <FaForward className="min-w-4 min-h-4 " />
        <p className="text-nowrap">빠른대전</p>
      </button>
      <button
        className="w-fit h-full btn-mint hover:brightness-110 flex justify-center items-center gap-2 pr-2 pl-1"
        onClick={refresh}
      >
        <IoMdRefresh className="min-w-5 min-h-5 " />
        <p className="text-nowrap">새로고침</p>
      </button>
      <button className="w-fit h-full border-custom-yellow bg-customYellow text-white font-extrabold hover:brightness-110 flex justify-center items-center gap-2 px-2">
        <BsFillTrophyFill className="min-w-4 min-h-4 " />
        <p className="text-nowrap">랭킹</p>
      </button>
      <div className="grow" />
      <button
        className="w-fit h-full border-custom-red bg-customRed text-white font-extrabold hover:brightness-110 flex justify-center items-center gap-2 px-2 mr-2"
        onClick={exitChannel}
      >
        <ImExit className="min-w-4 min-h-4 " />
        <p className="text-nowrap">나가기</p>
      </button>
    </nav>
  );
};

export default Header;
