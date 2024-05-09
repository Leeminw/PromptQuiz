import React, { useEffect, useState } from 'react';

import { ImExit } from 'react-icons/im';
import { IoMdRefresh } from 'react-icons/io';
import { FaForward } from 'react-icons/fa6';
import { MdAddHome } from 'react-icons/md';
import { Link, useNavigate } from 'react-router-dom';
import CreateRoom from './CreateRoom';
import { LobbyApi } from '../../hooks/axios-lobby';
import { UserChannelApi } from '../../hooks/axios-user-channel';
import { error } from 'console';
import Rank from './Rank';
import useUserStore from '../../stores/userStore';
interface HeaderProps {
  channelUuid: string; // 랭킹화면에서 다시 로비 URL로 넘어가기 위해 추가
  handleState: (data1: RoomProps[], data2: CurrentUser[]) => void;
}
// const Header = ({ channelCode }: Props, handleState: (data: RoomProps[]) => void) => {
const Header = ({ channelUuid, handleState }: HeaderProps) => {
  const { user } = useUserStore();
  const navigate = useNavigate();
  const fastMatching = () => {
    alert('빠른대전 매칭완료!');
  };
  const refresh = async () => {
    alert('새로고침 버튼 누름');
    const { roomData } = await LobbyApi.getGameList(channelUuid);
    const { currentUserData } = await UserChannelApi.channelUserList(channelUuid);
    console.log('이거는 패치할 데이터 정보임');
    console.log(roomData);
    console.log(currentUserData);
    handleState(roomData, currentUserData);
  };
  const exitChannel = () => {
    const response = UserChannelApi.exitChannel(user.userId, channelUuid)
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
    <nav className="w-full h-14 flex gap-6 pl-2 py-2">
      <CreateRoom channelCode={channelUuid} />
      <button
        className="w-fit h-full btn-mint hover:brightness-110 flex justify-center items-center gap-2 px-4"
        onClick={fastMatching}
      >
        <FaForward className="min-w-4 min-h-4 " />
        <p className="text-nowrap lg:flex max-lg:hidden">빠른대전</p>
      </button>
      <button
        className="w-fit h-full btn-mint hover:brightness-110 flex justify-center items-center gap-2 pl-2 pr-3"
        onClick={refresh}
      >
        <IoMdRefresh className="lg:min-w-5 lg:min-h-5 max-lg:min-w-6 max-lg:min-h-6" />
        <p className="text-nowrap lg:flex max-lg:hidden">새로고침</p>
      </button>

      <Rank />

      <div className="grow" />
      <button
        className="w-fit h-full border-custom-red bg-customRed text-white font-extrabold hover:brightness-110 flex justify-center items-center gap-2 px-4"
        onClick={exitChannel}
      >
        <ImExit className="min-w-4 min-h-4 " />
        <p className="text-nowrap lg:flex max-lg:hidden">나가기</p>
      </button>
    </nav>
  );
};

export default Header;
