import React, { useEffect, useState } from 'react';
import instance from '../hooks/axios-instance';
import ChannelUserComponent from '../components/user/ChannelUserComponent';
import { UserApi } from '../hooks/axios-user';
import { useQuery, useQueryClient } from '@tanstack/react-query';
import TestChild from './TestChild';
const TestPage = () => {
  const [userList, setUserList] = useState<ChannelUser[]>([]);
  const queryClient = useQueryClient();

  const { data, isLoading, isError } = useQuery({
    queryKey: ['user'],
    queryFn: UserApi.loadUser,
  });

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
      <div>{JSON.stringify(queryClient.getQueryData(['user']))}</div>
      <TestChild />
    </div>
  );
};

export default TestPage;
