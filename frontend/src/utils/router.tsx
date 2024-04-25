import React from 'react';
import App from '../App';
import LoginPage from '../pages/Login';
import Join from '../pages/Join';
import ChannelSelectPage from '../pages/ChannelSelect';
import MainPage from '../pages/Main';

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
