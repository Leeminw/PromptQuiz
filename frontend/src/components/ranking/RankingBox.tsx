import React, { useEffect, useState } from 'react';

interface Props {
  key: number;
  userInfo: UserInfo;
}
interface UserInfo {
  userId: number;
  userName: string;
  nickName: string;
  picture: string;
  statusMessage: null | string;
  totalScore: number;
  teamScore: number;
  soloScore: number;
  created_date: string;
  updated_date: string;
}

const RankingBox = ({ key, userInfo }: Props) => {
  return (
    <div className="w-2/3 h-[100px] bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      <div>{userInfo.nickName}</div>
    </div>
  );
};

export default RankingBox;
