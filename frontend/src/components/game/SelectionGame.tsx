import React, { useEffect, useState } from 'react';
interface SelectionGameProps {
  // idx: number;
  choiceList: SelectQuiz[] | null;
  choosedButton: boolean[];
  onButtonClick: (buttonNumber: number) => void;
}

const SelectionGame = ({ choiceList, onButtonClick, choosedButton }: SelectionGameProps) => {
  const buttonClass = [
    'w-full h-full bg-[#e37070] border-custom-red flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition',
    'w-full h-full bg-customYellow border-custom-yellow flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition',
    'w-full h-full bg-customGreen border-custom-green flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition',
    'w-full h-full bg-customBlue border-custom-blue flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition',
  ];

  const choosedButtonClass = [
    'w-full h-full bg-[#919191] border-custom-red flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition',
    'w-full h-full bg-[#919191] border-custom-yellow flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition',
    'w-full h-full bg-[#919191] border-custom-green flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition',
    'w-full h-full bg-[#919191] border-custom-blue flex items-center justify-center cursor-pointer hover:brightness-125 hover:scale-105 transition',
  ];
  const innerDivClass = [
    'w-full h-full flex items-center justify-center px-3 py-4',
    'w-full h-full flex items-center justify-center',
    'w-full h-full flex items-center justify-center px-3 py-4',
    'w-full h-ful flex items-center justify-center px-3 py-4',
  ];

  const sendAnswer = (answer: number) => {
    // console.log('send answer ', answer);
    onButtonClick(answer);
  };
  if (!choiceList) return <div></div>;
  return (
    <div className="absolute w-full h-full grid grid-rows-2 grid-cols-2 gap-3 text-sm text-white font-extrabold">
      {choiceList.map((item, index) => (
        <button className={buttonClass[index]} onClick={() => sendAnswer(index + 1)} key={index}>
          <div className={innerDivClass[index]}>
            {index + 1}. {item.korSentence}
          </div>
        </button>
      ))}
      {/* <button className="">
        <div className="w-full h-full flex items-center justify-center px-3 py-4">
          1. {choiceList[0]?.korSentence}
        </div>
      </button>
      <button className={``}>
        <div className="w-full h-full flex items-center justify-center">
          2. {choiceList[1]?.korSentence}
        </div>
      </button>
      <button className={``}>
        <div className="w-full h-full flex items-center justify-center px-3 py-4">
          3. {choiceList[2]?.korSentence}
        </div>
      </button>
      <button className={``}>
        <div className="w-full h-ful flex items-center justify-center px-3 py-4">
          4. {choiceList[3]?.korSentence}
        </div>
      </button> */}
    </div>
  );
};

export default SelectionGame;
