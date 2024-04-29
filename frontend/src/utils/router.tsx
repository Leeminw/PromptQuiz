import React from 'react';
import App from '../App';
import ChannelSelectPage from '../pages/ChannelSelect';
import MainPage from '../pages/Main';
import GamePage from '../pages/Game';
import TestPage from '../pages/Test';
import Lobby from '../pages/Lobby';

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
        path: '/game',
        element: <GamePage />,
        label: 'game',
      },
      {
        path: '/lobby',
        element: <Lobby />,
        label: 'lobby',
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
