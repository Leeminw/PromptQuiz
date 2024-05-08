import React, { useState } from 'react';
import { AiOutlineRight } from 'react-icons/ai';
import { useNavigate } from 'react-router-dom';
import { UserChannelApi } from '../../hooks/axios-user-channel';
import { error } from 'console';

interface Props {
  id: number;
  code: string;
  name: string;
  curPlayers: number;
  maxPlayers: number;
}
const ChannelBox = ({ id, code, name, curPlayers, maxPlayers }: Props) => {
  const percentage = Math.floor((curPlayers / maxPlayers) * 100);
  const navigate = useNavigate();

  const enterLobby = () => {
    const response = UserChannelApi.enterChannel(id)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
    setTimeout(() => {
      // navigate(`/lobby/${code}`);
      navigate(`/lobby/${code}`, { state: { channelId: id } });
    }, 1000);
  };
  return (
    <div
      className="channelBox"
      style={{
        border: '2px solid #359DB0',
        borderRadius: '15px',
        height: '50px',
        width: '300px',
        color: '#359DB0',
        // display: 'flex',
        // alignItems: 'center',
        lineHeight: '25px',
        fontSize: '10px', // 10px로
        margin: '2px',
      }}
    >
      <div
        className="ex"
        style={{ display: 'flex', justifyContent: 'space-between', width: '100%' }}
      >
        <div>{id}채널</div>
        {/* <div>
        </div> */}
        <button onClick={enterLobby}>
          <AiOutlineRight style={{ fontSize: '15px', marginTop: '5px' }} />
        </button>
      </div>
      <div style={{ display: 'flex', justifyContent: 'space-between', width: '100%' }}>
        <span
          className="w-full bg-gray-200 rounded-full h-2.5 mb-4 dark:bg-gray-700"
          style={{ width: '75%', border: '0.3px solid #359DB0' }}
        >
          <div
            // className="bg-green-600 h-2.5 rounded-full dark:bg-blue-500"
            className="h-2.5 rounded-full"
            style={{ width: `${(curPlayers / maxPlayers) * 100}%`, backgroundColor: '#359DB0' }}
          ></div>
        </span>
        <span>
          {curPlayers} / {maxPlayers}
        </span>
      </div>
    </div>
  );
};
export default ChannelBox;
