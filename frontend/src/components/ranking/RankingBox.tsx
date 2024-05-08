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
}

const RankingBox = ({ key, userInfo }: Props) => {
  return (
    <div className="w-2/3 h-[300px] bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      <div>사용자아이디 : {userInfo.userId}</div>
      <div>사용자이름 :{userInfo.userName}</div>
      <div>사용자닉네임 : {userInfo.nickName}</div>
      <div>이미지URL : {userInfo.picture}</div>
      <div>상태메시지 : {userInfo.statusMessage}</div>
      <div>전체점수 : {userInfo.totalScore}</div>
      <div>팀전점수 : {userInfo.teamScore}</div>
      <div>개인전점수 : {userInfo.soloScore}</div>
      <div>계정 생성일 : {userInfo.created_date}</div>
    </div>
  );
};

export default RankingBox;
