import React from 'react';
import App from '../App';
import Join from '../pages/Join';
import ChannelSelectPage from '../pages/ChannelSelect';

const RouterInfo = [
  {
    path: '/',
    element: <App />,
    children: [
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
