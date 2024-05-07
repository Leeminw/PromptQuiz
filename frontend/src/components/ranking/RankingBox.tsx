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
      <div>{userInfo.userId}</div>
      <div>{userInfo.userName}</div>
      <div>{userInfo.nickName}</div>
      <div>{userInfo.picture}</div>
      <div>{userInfo.statusMessage}</div>
      <div>{userInfo.totalScore}</div>
      <div>{userInfo.teamScore}</div>
      <div>{userInfo.soloScore}</div>
      <div>{userInfo.created_date}</div>
      <div>{userInfo.updated_date}</div>
    </div>
  );
};

export default RankingBox;
