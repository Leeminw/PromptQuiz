import React from 'react';
import App from '../App';
import ChannelSelectPage from '../pages/ChannelSelect';
import MainPage from '../pages/Main';
import Lobby from '../pages/Lobby';
import TestPage from '../pages/Test';

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
];

export default RouterInfo;
