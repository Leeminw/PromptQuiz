import React from 'react';
import App from '../App';
import ChannelSelectPage from '../pages/ChannelSelect';
import MainPage from '../pages/Main';
import GamePage from '../pages/Game';
import TestPage from '../pages/Test';
import Lobby from '../pages/Lobby';
import Dottegi from '../pages/Dottegi';

const RouterInfo = [
  {
    path: '/',
    element: <App />,
    children: [
      {
        index: true,
        element: <MainPage />,
        label: 'main',
      },
      {
        path: '/channel',
        element: <ChannelSelectPage />,
        label: 'channel',
      },
      {
        path: '/game/:roomCode',
        element: <GamePage />,
        label: 'game',
      },
      {
        // 동적 라우팅 설정
        path: '/lobby/:channelUuid',
        element: <Lobby />,
        label: 'lobby',
      },
      { // 시장통 라우팅 설정
        path: '/dottegi',
        element: <Dottegi />,
        label: 'dottegi',
      },
    ],
  },
  {
    path: '/test',
    element: <TestPage />,
    label: 'test',
  },
];

export default RouterInfo;
