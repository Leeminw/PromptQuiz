import React, { useEffect, useState } from 'react';
import { IoIosLock } from 'react-icons/io';
import { useNavigate } from 'react-router-dom';

interface UserProps {
  user: CurrentUser;
}
const CurrentUser = ({ user }: UserProps) => {
  return (
    <div className="w-full h-12 flex relative items-center pl-1 border-custom-mint bg-white/60 overflow-y-visible py-4">
      {/* <div>URL로 프로필 사진을 가져올부분</div> */}
      <div className="rounded-full bg-cover w-8 h-8 aspect-square" style={{ backgroundImage: `url(${user.picture})` }}></div>
      <div className="flex flex-col items-start pl-2">
        <p
          className={`w-full font-bold text-black text-ellipsis ${user.nickName.length > 8 ? 'text-xs line-clamp-2' : 'text-sm line-clamp-1'}`}
        >
          {user.nickName}
        </p>
        <p className="text-xs text-gray-500 line-clamp-2 leading-3">{user.statusMessage}</p>
      </div>
    </div>
  );
};

export default CurrentUser;
