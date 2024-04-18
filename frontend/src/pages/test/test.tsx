import React from 'react';

const TestScreen = () => {
  return (
    <div
      className="w-fit p-2 m-4"
      style={{
        border: '24px solid transparent',
        borderImage: "url('border-skyblue.png') round",
        borderImageSlice: '14%',
        borderImageRepeat: 'stretch',
      }}
    >
      <div className="py-2">
        ID : <input className="border"></input>
      </div>
      <div className="py-2">
        PW : <input className="border"></input>
      </div>
    </div>
  );
};

export default TestScreen;
