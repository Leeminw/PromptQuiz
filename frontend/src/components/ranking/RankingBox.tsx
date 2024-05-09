import React, { useEffect, useState } from 'react';

interface Props {
  rank: number;
  rankingType: number;
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

const RankingBox = ({ rank, rankingType, userInfo }: Props) => {
  return (
    <div
      className={`
    w-full flex relative items-center pl-1 overflow-y-visible py-4
    ${rank == 1 && 'h-16 bg-yellow-200 border-custom-yellow'}
    ${rank == 2 && 'h-14 bg-gray-200 border-custom-gray'}
    ${rank == 3 && 'h-12 bg-yellow-500 border-custom-yellow'}
    ${rank > 3 && 'h-12 bg-white border-custom-mint'}
    `}
    >
      {/* <div>URL로 프로필 사진을 가져올부분</div> */}
      <div className="pl-0.5 pr-2.5 font-extrabold">{rank}</div>
      <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
      <div className="w-full flex flex-col items-start pl-2">
        <p
          className={`w-full font-bold text-black cursor-default text-ellipsis ${userInfo.nickName.length > 8 ? 'text-xs line-clamp-2' : 'text-sm line-clamp-1'}`}
        >
          {userInfo.nickName}
        </p>
        <p className="text-xs text-gray-500 line-clamp-2 leading-3 break-all">
          {userInfo.statusMessage}
        </p>
      </div>
      <p className="text-nowrap pl-2 pr-1 font-extrabold text-xs">
        {rankingType === 1
          ? userInfo.totalScore
          : rankingType === 2
            ? userInfo.soloScore
            : userInfo.teamScore}
        점
      </p>
    </div>
  );
};

export default RankingBox;
