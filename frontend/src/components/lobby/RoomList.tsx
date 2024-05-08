import React, { useEffect, useState } from 'react';
import Room from '../../components/lobby/Room';
import { FaPlay } from 'react-icons/fa';
// roomList={roomList}
// { roomList }: RoomProps[]

const RoomList = (roomList: RoomProps[]) => {
  const roomArray = Object.values(roomList);
  console.log(roomArray);

  // 페이지네이션 처리 관련 변수들
  const [totalPageNum, setTotalPageNum] = useState<number>(0);
  const [curPageNum, setCurPageNum] = useState<number>(1);
  const [curRoomList, setCurRoomList] = useState<RoomProps[]>([]);
  const totalGameNum = roomArray.length;

  const updateRoomList = () => {};
  // 이전 페이지로 이동(왼쪽 버튼)
  const getPrevPage = () => {
    if (curPageNum === 1) return;
    // 이제 앞에 6개 리스트 내용을 보여준다.
    setCurPageNum(curPageNum - 1);
    setCurRoomList(roomArray.slice((curPageNum - 1) * 6, curPageNum * 6));
  };
  // 다음 페이지로 이동(오른쪽 버튼)
  const getNextPage = () => {
    if (curPageNum === totalPageNum) return;
    setCurPageNum(curPageNum + 1);
    if (curPageNum < totalPageNum)
      setCurRoomList(roomArray.slice((curPageNum - 1) * 6, curPageNum * 6));
    else setCurRoomList(roomArray.slice((curPageNum - 1) * 6));
  };

  const initiateTotalPageNum = (roomLength: number) => {
    if (roomLength <= 6) {
      setTotalPageNum(1);
      return;
    }
    setTotalPageNum(Math.ceil(roomLength / 6));
  };
  useEffect(() => {
    setCurRoomList(roomArray.slice(0, 6));
    setCurPageNum(1);
    console.log(roomArray);

    initiateTotalPageNum(roomArray.length);
    // if (roomArray.length > 6) setTotalPageNum(Math.ceil(roomArray.length / 6));
  }, [roomList]);

  if (!Array.isArray(roomArray)) {
    return <div>게임방이 없습니다.</div>;
  }
  return (
    <div className="w-full h-full gap-1 relative">
      <div className="grid grid-cols-2 grid-rows-3 gap-y-2 gap-x-3 pt-4 px-4">
        {roomArray.map(
          (item, index) =>
            index >= (curPageNum - 1) * 6 &&
            index < curPageNum * 6 && (
              <Room
                key={index}
                id={item.id}
                channelCode={item.channelCode}
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
            )
        )}
        {curPageNum * 6 > roomArray.length &&
          Array.from({ length: curPageNum * 6 - roomArray.length }).map((_, index) => (
            <div
              className="w-full h-20 relative gap-1 border bg-gray-100 border-gray-400 rounded-3xl px-5 py-2 cursor-default"
              key={index}
            ></div>
          ))}
      </div>
      <div className="w-full h-full bg-white absolute top-0 -z-10 border-custom-white opacity-80"></div>
      <div className="w-full h-14 flex justify-center items-center gap-4 pt-4">
        {curPageNum == 1 ? (
          <button className="cursor-default">
            <FaPlay className="rotate-180 text-gray-400 w-7 h-7" />
          </button>
        ) : (
          <button onClick={getPrevPage}>
            <FaPlay className="rotate-180 text-mint w-7 h-7 hover:text-lightmint transition" />
          </button>
        )}

        <span className="font-extrabold text-lg cursor-default">
          {curPageNum} / {totalPageNum}
        </span>
        {curPageNum == totalPageNum ? (
          <button className="cursor-default">
            <FaPlay className="text-gray-400 w-7 h-7" />
          </button>
        ) : (
          <button onClick={getNextPage}>
            <FaPlay className="text-mint w-7 h-7 hover:text-lightmint transition" />
          </button>
        )}
      </div>
    </div>
  );
};

export default RoomList;
