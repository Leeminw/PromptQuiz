// {
//     ""status"": ""success"",
//     ""message"": ""The request has been processed successfully."",
//     ""data"": [
//         {
//             ""userId"": 2,
//             ""userName"": ""testname1"",
//             ""nickName"": ""닉네임1"",
//             ""picture"": ""https://s3Urlupdated"",
//             ""statusMessage"": ""수정된 상태 메시지"",
//             ""totalScore"": 100,
//             ""teamScore"": 80,
//             ""soloScore"": 70,
//             ""created_date"": ""2024-04-23T09:15:22.553696"",
//             ""updated_date"": ""2024-04-23T10:50:46.711904""
//         },
//         {
//             ""userId"": 3,
//             ""userName"": ""testname2"",
//             ""nickName"": ""닉네임2"",
//             ""picture"": ""https://s3Urlupdated"",
//             ""statusMessage"": ""푸바오 ㅠㅜㅠㅜ"",
//             ""totalScore"": 100,
//             ""teamScore"": 80,
//             ""soloScore"": 70,
//             ""created_date"": ""2024-04-23T11:15:22.553696"",
//             ""updated_date"": ""2024-04-23T12:50:46.711904""
//         }
//     ]
// }"

import React, { useEffect, useState } from 'react';

import { ImExit } from 'react-icons/im';
import { BsFillTrophyFill } from 'react-icons/bs';
import { IoMdRefresh } from 'react-icons/io';
import { FaForward } from 'react-icons/fa6';
import { Link } from 'react-router-dom';
import CreateRoom from './CreateRoom';
import { LobbyApi } from '../../hooks/axios-lobby';
interface Props {
  channelId: number;
}
const CurrentUserList = ({ channelId }: Props) => {
  const createRoom = () => {
    alert('방 생성 모달창 띄움');
  };
  return <div>ㅋㅋㅋ 굿굿</div>;
};

export default CurrentUserList;
