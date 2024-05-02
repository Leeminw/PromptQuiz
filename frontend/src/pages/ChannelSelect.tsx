import React, { useEffect, useState } from 'react';
import ChannelBox from '../components/channel/ChannelBox';
// import ChannelBox from '../components/channel/ChannelBox';

const ChannelSelectPage = () => {
  const [channelArray, setChannelList] = useState<Channel[]>([]);
  useEffect(() => {
    // 채널리트스 가져오기
    const response = {
      status: 'success',
      message: 'The request has been processed successfully.',
      data: [
        {
          id: 1,
          code: 'code1',
          name: '1채널',
          curPlayers: 100,
          maxPlayers: 100,
        },
        {
          id: 2,
          code: 'code2',
          name: '2채널',
          curPlayers: 100,
          maxPlayers: 100,
        },
        {
          id: 3,
          code: 'code3',
          name: '3채널',
          curPlayers: 97,
          maxPlayers: 100,
        },
        {
          id: 4,
          code: 'code4',
          name: '4채널',
          curPlayers: 95,
          maxPlayers: 100,
        },
        {
          id: 5,
          code: 'code5',
          name: '5채널',
          curPlayers: 85,
          maxPlayers: 100,
        },
        {
          id: 6,
          code: 'code6',
          name: '6채널',
          curPlayers: 76,
          maxPlayers: 100,
        },
        {
          id: 7,
          code: 'code7',
          name: '7채널',
          curPlayers: 50,
          maxPlayers: 100,
        },
        {
          id: 8,
          code: 'code8',
          name: '8채널',
          curPlayers: 32,
          maxPlayers: 100,
        },
        {
          id: 9,
          code: 'code9',
          name: '9채널',
          curPlayers: 16,
          maxPlayers: 100,
        },
        {
          id: 10,
          code: 'code10',
          name: '10채널',
          curPlayers: 3,
          maxPlayers: 100,
        },
      ],
    };
    setChannelList(response.data);
  }, []);

  const headerStyle = {
    width: '100%',
    height: '50px',
    color: '#359DB0',
    display: 'flex',
    justifyContent: 'center',
    backgroundColor: 'white',
    opacity: '0.7',
    fontSize: '20px',
  };
  const channelList = {
    width: '100%',
    height: '400px',
    color: '#359DB0',
    display: 'flex',
    flexFlow: 'row wrap',
    // justifyContent: 'center',
    backgroundColor: 'white',
    opacity: '0.7',
    marginTop: '10px',
  };
  return (
    <div
        className="container"
        style={{
          //   display: 'flex',
          //   justifyContent: 'center',
        }}
      >
        <div style={headerStyle}>채널목록</div>
        <div style={channelList}>
          {/* <ChannelBox id={1} code="code1" name="채널1" curPlayers={10} maxPlayers={100} /> */}
          {channelArray.map((item, idx) => (
            <ChannelBox
              id={item.id}
              code={item.code}
              name={item.name}
              curPlayers={item.curPlayers}
              maxPlayers={item.maxPlayers}
            />
          ))}
        </div>
      </div>
  );
};

export default ChannelSelectPage;
