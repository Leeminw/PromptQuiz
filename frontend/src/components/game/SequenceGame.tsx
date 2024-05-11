import React, { useEffect, useState } from 'react';
interface SelectionGameProps {
  // idx: number;
  choiceList: SelectQuiz[] | null;
}

const SequenceGame = ({ choiceList }: SelectionGameProps) => {
  // if (!choiceList) return <div>no game</div>;
  return (
    <div className="w-full h-full grid grid-rows-2 grid-cols-2 gap-3 text-sm text-white font-extrabold relative">
      <div className='absolute bg-white border-custom-white w-full h-full opacity-80 -z-10'></div>
      
    </div>
  );
};

export default SequenceGame;
