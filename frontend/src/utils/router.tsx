import React from 'react';
import App from '../App';
import LoginPage from '../pages/Login';
import Join from '../pages/Join';
import ChannelSelectPage from '../pages/ChannelSelect';

const RouterInfo = [
  {
    path: '/',
    element: <App />,
    children: [
      {
        index: true,
        element: <LoginPage />,
        label: 'login',
      },
      {
        path: '/join',
        element: <Join />,
        label: 'join',
      },
      {
        path: '/channel',
        element: <ChannelSelectPage />,
        label: 'channel',
      },
    ],
  },
];

export default RouterInfo;
