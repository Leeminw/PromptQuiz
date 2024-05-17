import React, { useState } from 'react';
import { AiOutlineRight } from 'react-icons/ai';
import { useNavigate } from 'react-router-dom';

interface Props {
  id: string;
  code: string;
  name: string;
  curPlayers: number;
  maxPlayers: number;
  btnActivate: boolean;
  onClick: () => void;
}
const ChannelBox = ({ id, code, name, curPlayers, maxPlayers, btnActivate, onClick }: Props) => {
  const percentage = Math.floor((curPlayers / maxPlayers) * 100);
  const navigate = useNavigate();
  const [activateBtn, setActivateBtn] = useState<boolean>(false);
  const [channelSelected, setChannelSelected] = useState<boolean>(false);
  const handleClick = () => {
    if (!btnActivate) {
      onClick();
      setActivateBtn(true);
      setChannelSelected(true);
      setTimeout(() => {
        setActivateBtn(false);
        setTimeout(() => {
          navigate(`/lobby/${code}`, { state: { channelCode: id } });
        }, 1000);
      }, 500);
    }
  };
  return (
    <button
      className={`${activateBtn ? 'animate-clickbtn scale-105 bg-mint/90 text-white' : ''} ${channelSelected ? 'bg-mint/90 text-white' : 'bg-white/90 text-mint'} rounded-lg hover:brightness-110 hover:bg-mint/90  hover:text-white hover:scale-105 transition`}
      onClick={handleClick}
    >
      <div className="px-2.5 py-2 w-full">
        <div className="flex items-center">
          <div className="w-full font-bold flex justify-start pl-1">{id}채널</div>
          <AiOutlineRight />
        </div>
        <div className="flex items-center gap-2 text-xs pt-1">
          <progress className="progress w-full " value={percentage} max="100"></progress>
          <div className="text-nowrap">
            {curPlayers} / {maxPlayers}
          </div>
        </div>
      </div>
    </button>
  );
};
export default ChannelBox;
