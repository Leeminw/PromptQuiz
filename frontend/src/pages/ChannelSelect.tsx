import React from 'react';
import ChannelBox from '../components/channel/ChannelBox';
// import ChannelBox from '../components/channel/ChannelBox';
// import BackgroundImg from '../../public/background.png';

const TestScreen = () => {
  // 테스트용 더미데이터
  // 채널
  // 채널코드 varchar
  // 채널이름 varchar
  // 현재인원 INT
  // 최대인원INT
  const channelDummy = [
    {
      code: 'uuid1',
      name: '토끼',
      cur_players: 79,
      max_players: 100,
    },
    {
      code: 'uuid2',
      name: '치타',
      cur_players: 34,
      max_players: 100,
    },
    {
      code: 'uuid3',
      name: '타조',
      cur_players: 55,
      max_players: 100,
    },
  ];
  const headerStyle = {
    width: '100%',
    height: '50px',
    border: '1px solid red',
    color: '#359DB0',
    display: 'flex',
    justifyContent: 'center',
    backgroundColor: 'white',
    opacity: '0.7',
  };
  const channelList = {
    width: '100%',
    height: '400px',
    border: '1px solid red',
    color: '#359DB0',
    display: 'flex',
    justifyContent: 'center',
    backgroundColor: 'white',
    opacity: '0.7',
    // margin: '10px',
  };
  return (
    <div
      className="w-fit p-2 m-4"
      style={{
        // backgroundColor: 'yellow',
        width: '80%',
        height: '80%',
      }}
    >
      <div
        className="container"
        style={{
          width: '80%',
          height: '80%',
          //   display: 'flex',
          //   justifyContent: 'center',
        }}
      >
        <div style={headerStyle}>채널목록</div>
        <div style={channelList}>
          <ChannelBox channelCurrentNum={66} channelMaxNum={100} />
          <ChannelBox channelCurrentNum={66} channelMaxNum={100} />
          <ChannelBox channelCurrentNum={66} channelMaxNum={100} />
        </div>
      </div>
    </div>
  );
};

export default TestScreen;
