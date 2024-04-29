import React, { useEffect, useState } from 'react';
import { IoIosLock } from 'react-icons/io';
// {
//   ""status"": ""success"",
//   ""message"": ""방 목록 조회 완료"",
//   ""data"": [
//       {
//           ""id"": 8144742847851927974,
//           ""channelId"": 1,
//           ""type"": 1,
//           ""style"": 0,
//           ""code"": ""20b3ed3c-965c-40c0-ad7c-62c9bdd29b3e"",
//           ""title"": ""들어와2"",
//           ""password"": null,
//           ""status"": true,
//           ""isTeam"": false,
//           ""curRound"": 0,
//           ""rounds"": 0,
//           ""curPlayers"": 1,
//           ""maxPlayers"": 8
//       }
//   ]
// }

interface Props {
  index: number;
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
  index,
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
  return (
    <div className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      <div>
        {index}
        {title}
        {password == null ? '' : <IoIosLock />}
      </div>
      <div className="flex-col items-center">
        <span>
          {findRoomType(type)} | {isTeam ? '팀전' : '개인전'}
        </span>
        <span>
          {curPlayers} / {maxPlayers}
        </span>
      </div>
    </div>
  );
};

export default Room;
