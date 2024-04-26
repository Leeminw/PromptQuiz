import React from 'react';
import App from '../App';
import ChannelSelectPage from '../pages/ChannelSelect';
import MainPage from '../pages/Main';
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
  {
    path: '/test',
    element: <TestPage />,
    label: 'test',
  },
];

export default RouterInfo;
