import React from 'react';
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
            code: "uuid1",
            name: "토끼",
            cur_players: 79,
            max_players: 100
        },
        {
            code: "uuid2",
            name: "치타",
            cur_players: 34,
            max_players: 100
        },
        {
            code: "uuid3",
            name: "타조",
            cur_players: 55,
            max_players: 100
        }
    ];
    const headerStyle = {
        width: "300px",
        height: "50px",
        border: "1px solid red",
        color: "#359DB0",
        display: "flex",
        justifyContent: "center",
    }
  return (
    <div
      className="w-fit p-2 m-4"
      style={{
        // border: '24px solid transparent',
        // borderImage: "url('border-skyblue.png') round",
        // borderImageSlice: '14%',
        // borderImageRepeat: 'stretch',
        backgroundImage: "../../public/background.png",
        backgroundColor: "yellow",
      }}
    >
      <div style={headerStyle}>채널목록</div>
      <div>
      </div>
    </div>
  );
};

export default TestScreen;
