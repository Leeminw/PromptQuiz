import React, { useEffect, useState } from 'react';
import ChannelBox from '../components/channel/ChannelBox';
import { ChannelApi } from '../hooks/axois-channel';
import { useNavigate } from 'react-router-dom';
import { IoLogOut } from 'react-icons/io5';

const ChannelSelectPage = () => {
  const [channelArray, setChannelList] = useState<Channel[]>([]);
  const [activateBtn, setActivateBtn] = useState<ActivateButton>({});
  const navigate = useNavigate();
  useEffect(() => {
    // 채널리스트 가져오기
    const response = ChannelApi.getChannelList()
      .then((response) => {
        setChannelList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  // 버튼 클릭 시 이벤트 처리
  const handleClick = (id: number) => {
    setActivateBtn((prev) => ({ ...prev, [id]: true }));
    console.log(activateBtn);
    setTimeout(() => {
      setActivateBtn((prev) => ({ ...prev, [id]: false }));
    }, 400);
  };

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
      style={
        {
          //   display: 'flex',
          //   justifyContent: 'center',
        }
      }
    >
      <div style={headerStyle}>
        <p className='w-full text-center'>채널목록</p>
        <button
          className={`btn-red text-white hover:brightness-125 hover:scale-105 transition 
            text-sm w-28 min-w-[3rem] ${activateBtn[1] ? 'animate-clickbtn scale-105' : ''}`}
          onClick={() => {
            handleClick(1);
            setTimeout(() => {
              navigate('/');
            }, 500);
          }}
        >
          <label className="flex gap-1 items-center px-2 cursor-pointer overflow-hidden max-xl:justify-center">
            <IoLogOut className="min-w-6 min-h-6 mb-0.5" />
            <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis xl:flex max-xl:hidden">
              로그아웃
            </p>
          </label>
        </button>
      </div>

      <div style={channelList}>
        {/* <ChannelBox id={1} code="code1" name="채널1" curPlayers={10} maxPlayers={100} /> */}
        {channelArray.map((item, idx) => (
          <ChannelBox
            key={idx}
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
