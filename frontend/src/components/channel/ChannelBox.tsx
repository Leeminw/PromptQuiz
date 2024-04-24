import React, { useState } from 'react';

const ChannelBox = ({channelCurrentNum, channelMaxNum}: {channelCurrentNum: number, channelMaxNum: number}) => {
//   const [channelCurrentNum, setChannelCurrentNum] = useState(1);
const bar = {
    border: "1px solid #359DB0",
    height: "10px",
    width: "160px"
};
const progressBar = {
    height: "10px"
}
const percentage = Math.floor((channelCurrentNum/channelMaxNum)*100);
return (
    <div className="channelBox" style={{
        border: "2px solid #359DB0",
        borderRadius: "30px",
        width: "300px",
        height: "50px",
        color: "#359DB0",
        display: "flex",
        alignItems: "center",
        fontSize: "10px"
    }}>
      <span>1채널</span>
      <span>{channelCurrentNum} / {channelMaxNum}</span>
    </div>
  );
};
export default ChannelBox;