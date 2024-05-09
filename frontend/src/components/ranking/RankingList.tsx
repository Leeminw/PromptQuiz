import React, { useEffect, useState } from 'react';
import RankingBox from './RankingBox';
interface Props {
  rankingTitle: string;
  rankingType: number;
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

const RankingList = ({ rankingTitle, rankingType, users }: Props) => {
  return (
    <div className="w-1/3 h-full bg-mint border-custom-mint">
      <div className="w-full h-8 bg-mint text-white font-bold text-lg flex items-center mb-3">
        <p className="w-full h-full flex items-center pl-1.5">{rankingTitle}</p>
      </div>
      <div className="w-full h-[26.5rem] flex flex-col gap-3 overflow-y-scroll custom-scroll border-custom-white bg-white py-1 pr-2 pl-1">
        {users.map(
          (userInfo: User, index) =>
            index !== 0 && <RankingBox key={index} rankingType={rankingType} rank={index} userInfo={userInfo} />
        )}
      </div>
    </div>
  );
};

export default RankingList;
