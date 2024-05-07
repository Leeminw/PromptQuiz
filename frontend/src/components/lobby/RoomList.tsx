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
    alert('이전페이지로!!');
    if (curPageNum === 1) return;
    // 이제 앞에 6개 리스트 내용을 보여준다.
    setCurPageNum(curPageNum - 1);
    setCurRoomList(roomArray.slice((curPageNum - 1) * 6, curPageNum * 6));
  };
  // 다음 페이지로 이동(오른쪽 버튼)
  const getNextPage = () => {
    alert('다음페이지로!!');

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
    <div className="w-full h-full bg-white-300 gap-1 border-2 border-mint rounded-3xl ">
      <div className="flex ">
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
          <button onClick={getPrevPage}>
            <FaPlay className="rotate-180" />
          </button>
          <span style={{ color: 'red', fontSize: '30px' }}>
            {curPageNum}/{totalPageNum}
          </span>
          <button onClick={getNextPage}>
            <FaPlay />
          </button>
        </div>
    </div>
  );
};

export default RoomList;
