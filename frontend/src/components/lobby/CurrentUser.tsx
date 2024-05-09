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
    <div className="w-full h-12 flex relative items-center pl-1 border-custom-mint bg-white overflow-y-visible py-4">
      {/* <div>URL로 프로필 사진을 가져올부분</div> */}
      <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
        <div className='flex flex-col items-start pl-2'>
        <p className={`w-full font-bold text-black cursor-default text-ellipsis ${nickName.length > 8 ? "text-xs line-clamp-2" : "text-sm line-clamp-1"}`}>
          {nickName}
        </p>
        <p className='text-xs text-gray-500 line-clamp-2 leading-3'>{statusMessage}</p>
        </div>
    </div>
  );
};

export default CurrentUser;
