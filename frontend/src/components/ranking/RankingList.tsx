import React, { useEffect, useState } from 'react';
import RankingBox from './RankingBox';
interface Props {
  rankingTitle: string;
  users: User[];
}
interface User {
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

const RankingList = ({ rankingTitle, users }: Props) => {
  return (
    <div className="w-2/3 h-2/3 bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      <div>{rankingTitle}</div>
      {users.map(
        (userInfo: User, index) => index !== 0 && <RankingBox key={index} userInfo={userInfo} />
      )}
    </div>
  );
};

export default RankingList;
