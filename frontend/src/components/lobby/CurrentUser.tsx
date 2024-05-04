import React, { useEffect, useState } from 'react';
import { IoIosLock } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';

interface Props {
  userId: number;
  userName: string;
  nickName: string;
  picture: string;
  statusMessage: string;
  totalScore: number;
  teamScore: number;
  soloScore: number;
  created_date: string;
  updated_date: string;
}
const CurrentUser = ({
  userId,
  userName,
  nickName,
  picture,
  statusMessage,
  totalScore,
  teamScore,
  soloScore,
  created_date,
  updated_date,
}: Props) => {
  return (
    <div className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      {/* <div>URL로 프로필 사진을 가져올부분</div> */}
      <div>닉네임 : {nickName}</div>
      <div>상태메시지 : {statusMessage}</div>
    </div>
  );
};

export default CurrentUser;
