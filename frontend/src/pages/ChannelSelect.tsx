import React, { useEffect, useState } from 'react';
import ChannelBox from '../components/channel/ChannelBox';
import { ChannelApi } from '../hooks/axois-channel';
import { useNavigate } from 'react-router-dom';
import { IoLogOut } from 'react-icons/io5';
import CustomButton from '../components/ui/CustomButton';

const ChannelSelectPage = () => {
  const [channelArray, setChannelList] = useState<Channel[]>([]);
  const [moveBox, setMoveBox] = useState<boolean>(false);
  const navigate = useNavigate();
  useEffect(() => {
    // 채널리스트 가져오기
    const response = ChannelApi.getChannelList()
      .then((response) => {
        setChannelList(response.data);
        setMoveBox(true);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  // 버튼 제어
  const [btnCurrentActivate, setBtnCurrentActivate] = useState<boolean>(false);
  const activateBtnFunc = async () => {
    setBtnCurrentActivate(true);
    await setTimeout(() => {
      setBtnCurrentActivate(false);
    }, 800);
  };

  return (
    <div className="w-[55rem] min-w-[45rem]">
      <div
        className={`w-full flex justify-center items-center bg-white/90 backdrop-blur-sm h-14 rounded-xl transition duration-1000 ${moveBox ? 'translate-x-0' : '-translate-x-[100vw]'}`}
      >
        <p className="text-mint font-extrabold text-lg">채널목록</p>
      </div>

      <div
        className={`grid grid-cols-3 grid-rows-3 gap-x-3 gap-y-3 py-4 rounded-xl mt-2 mb-4 transition duration-1000 ${moveBox ? 'translate-x-0' : 'translate-x-[100vw]'}`}
      >
        {channelArray.map((item, idx) => (
          <ChannelBox
            key={idx}
            id={item.id}
            code={item.code}
            name={item.name}
            curPlayers={item.curPlayers}
            maxPlayers={item.maxPlayers}
            btnActivate={btnCurrentActivate}
            onClick={() => {
              activateBtnFunc();
              setTimeout(() => {
                setMoveBox(false);
              }, 600);
            }}
          />
        ))}
      </div>
      <div
        className={`flex justify-end transition duration-1000 ${moveBox ? 'translate-x-0' : 'translate-x-[100vw]'}`}
      >
        <CustomButton
          className="btn-red text-white text-sm w-28 min-w-[3rem] flex gap-1 items-center px-2 overflow-hidden max-xl:justify-center"
          onClick={() => {
            activateBtnFunc();
            setTimeout(() => {
              setMoveBox(false);
              setTimeout(() => {
                navigate('/');
              }, 500);
            }, 500);
          }}
          btnCurrentActivate={btnCurrentActivate}
        >
          <IoLogOut className="min-w-6 min-h-6 mb-0.5" />
          <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis">
            로그아웃
          </p>
        </CustomButton>
      </div>
    </div>
  );
};

export default ChannelSelectPage;
