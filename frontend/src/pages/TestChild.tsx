import React from 'react';
import { useQueryClient } from '@tanstack/react-query';

const TestChild = () => {
  const queryClient = useQueryClient();
  const data = queryClient.getQueryData(['user']);

  return (
    <div>
      자식페이지
      <div>{JSON.stringify(data)}</div>
    </div>
  );
};

export default TestChild;
