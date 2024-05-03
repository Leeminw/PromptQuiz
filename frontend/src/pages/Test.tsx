import React, { useEffect, useState } from 'react';
import instance from '../hooks/axios-instance';
import ChannelUserComponent from '../components/user/ChannelUserComponent';
import { UserApi } from '../hooks/axios-user';
import TestChild from './TestChild';
import useUserStore from '../stores/userStore';
import { useWebSocketStore } from '../stores/socketStore';
const TestPage = () => {
  const [userList, setUserList] = useState<ChannelUser[]>([]);
  const { user } = useUserStore();
  const { isConnected, connectWebSocket, disconnectWebSocket } = useWebSocketStore();
  useEffect(() => {
    // connectWebSocket();
    return () => {
      // disconnectWebSocket();
    };
  }, []);

  const logoutUser = async () => {
    console.log('로그아웃');
    try {
      const response = await UserApi.logoutUser();
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };
  const loadUserTest = async () => {
    try {
      const response = await UserApi.loadUser();
      const user: User = response.data;
      console.log(user);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="">
      {userList.map((user: ChannelUser) => (
        <ChannelUserComponent key={user.userId} {...user} />
      ))}
      <button className="border" onClick={loadUserTest}>
        loadUser
      </button>
      <TestChild />
      <div>{JSON.stringify(user)}</div>
      <button onClick={logoutUser} className="border">
        로그아웃
      </button>
      <div>{isConnected ? 'connected' : 'not connected'}</div>
    </div>
  );
};

export default TestPage;
