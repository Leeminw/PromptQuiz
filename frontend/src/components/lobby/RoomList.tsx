import React, { useEffect, useState } from 'react';
import Room from '../../components/lobby/Room';
import { FaPlay } from 'react-icons/fa';
// roomList={roomList}
// { roomList }: RoomProps[]

const RoomList = (roomList: RoomProps[]) => {
  const roomArray = Object.values(roomList);
  if (!Array.isArray(roomArray)) {
    return <div>게임방이 없습니다.</div>;
  }
  return (
    <div className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      <div>
        방 내용
        <div>
          {roomArray.map((item, index) => (
            // <p key={index}>{item.channelId}</p>
            <Room
              id={item.id}
              channelId={item.channelId}
              type={item.type}
              style={item.style}
              code={item.code}
              title={item.title}
              password={item.password}
              status={item.status}
              isTeam={item.isTeam}
              curRound={item.curRound}
              rounds={item.rounds}
              curPlayers={item.curPlayers}
              maxPlayers={item.maxPlayers}
            />
          ))}
        </div>
        <div>
          <button>
            <FaPlay className="rotate-180" />
          </button>
          <span>1/2</span>
          <button>
            <FaPlay />
          </button>
        </div>
      </div>
    </div>
  );
};

export default RoomList;
