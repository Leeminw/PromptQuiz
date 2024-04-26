import React, { useEffect, useState } from 'react';
import { RiLock2Fill } from 'react-icons/ri';

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
  const flag = true;
  return (
    <div className="w-[300px] h-[100px] bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      <div>
        <span className="font-semibold">2</span>
        <span>개인전 빠무 초보만</span>
        {/* {flag ? <RiLock2Fill className="w-[30px] h-[30px]" /> : '없음!'} */}
      </div>
      {/* <div className="flex flex-col items-center"> */}
      <div className="inline-flex flex-col">
        <div>객관식 | 개인전</div>
        <div>5 / 6</div>
      </div>
    </div>
  );
};

export default Room;
{
  /* <label className="input input-bordered flex items-center gap-2 rounded-full text-xs bg-white/80 cursor-text animate-logininput">
          <FaUser className="min-w-[1rem] min-h-[1rem] opacity-80 text-mint ml-2" />
          <input
            type="text"
            className="grow text-mint font-bold overflow-hidden pr-2"
            placeholder="아이디"
            value={userName}
            onChange={userNameHandler}
          />
        </label> */
}
