import React from 'react';
import App from '../App';
import ChannelSelectPage from '../pages/ChannelSelect';
import MainPage from '../pages/Main';
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
    ],
  },
  {
    path: '/channel',
    element: <ChannelSelectPage />,
    label: 'channel',
  },
  {
    path: '/lobby',
    element: <App />,
    children: [
      {
        index: true,
        element: <Lobby />,
        label: 'lobby',
      },
    ],
  },
];

export default RouterInfo;
