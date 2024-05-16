import React, { useState } from 'react';
import { AiOutlineRight } from 'react-icons/ai';
import { useNavigate } from 'react-router-dom';
import { UserChannelApi } from '../../hooks/axios-user-channel';
import { error } from 'console';
import CustomButton from '../ui/CustomButton';

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
  const handleClick = () => {
    if (!btnActivate) {
      onClick();
      setActivateBtn(true);
      setTimeout(() => {
        navigate(`/lobby/${code}`, { state: { channelCode: id } });
        setActivateBtn(false);
      }, 500);
    }
  };
  return (
    <button
      className={`${activateBtn ? 'animate-clickbtn scale-105' : ''} hover:brightness-110 hover:scale-105 transition`}
      onClick={handleClick}
    >
      <div className="bg-white/90 px-2.5 py-2 rounded-lg w-full">
        <div className="flex items-center">
          <div className="w-full text-mint font-bold flex justify-start pl-1">{id}채널</div>
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
