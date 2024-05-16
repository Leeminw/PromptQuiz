import React, { useEffect, useState } from 'react';
import ChannelBox from '../components/channel/ChannelBox';
import { ChannelApi } from '../hooks/axois-channel';
import { useNavigate } from 'react-router-dom';
import { IoLogOut } from 'react-icons/io5';
import CustomButton from '../components/ui/CustomButton';

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
      <div className="w-full flex justify-center items-center bg-white/90 backdrop-blur-sm h-14 rounded-xl">
        <p className="text-mint font-extrabold text-lg">채널목록</p>
      </div>

      <div className="grid grid-cols-3 grid-rows-3 gap-x-3 gap-y-3 py-4 rounded-xl mt-2 mb-4">
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
            }}
          />
        ))}
      </div>
      <div className="flex justify-end">
        <CustomButton
          className="btn-red text-white text-sm w-28 min-w-[3rem] flex gap-1 items-center px-2 overflow-hidden max-xl:justify-center"
          onClick={() => {
            activateBtnFunc();
            setTimeout(() => {
              navigate('/');
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
