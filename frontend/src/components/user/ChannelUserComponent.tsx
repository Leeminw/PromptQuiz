import React from 'react';

const ChannelUserComponent = (props: ChannelUser) => {
  return (
    <div className="border-custom-mint border-dashed">
      <div className="flex">
        <div className="w-1/6 h-1/4 ">
          <img src={props.picture} alt="" />
        </div>
        <div className="align-middle">
          <div>{props.nickName}</div>
          <div>{props.statusMessage}</div>
        </div>
      </div>
    </div>
  );
};

export default ChannelUserComponent;
