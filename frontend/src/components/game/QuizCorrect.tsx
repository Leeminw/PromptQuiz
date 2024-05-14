import React, { useState } from 'react';
import customSetTimeout from '../../hooks/customSetTimeout';
const QuizCorrect = ({ nickname }: { nickname: string }) => {
  const [show, setShow] = useState<boolean>(false);
  const [startShow, setStartShow] = useState<boolean>(false);
  const [earthquake, setEarthquake] = useState<boolean>(false);
  customSetTimeout(
    () => {
      if (nickname !== null && nickname !== undefined && nickname !== '') {
        console.log('HI');
        setShow(true);
        setStartShow(true);
        setTimeout(() => {
          setEarthquake(true);
          setTimeout(() => {
            setEarthquake(false);
          }, 300);
        }, 500);
      }
    },
    () => {
      setShow(false);
    },
    3000,
    [nickname]
  );
  return (
    <div
      className={`absolute w-full h-full flex justify-center items-center px-10 z-20 ${earthquake ? 'animate-earthquake' : ''}`}
    >
      <div
        className={`w-fit h-fit bg-lightmint backdrop-blur-sm px-14 py-8 rounded-full border-gray-300 border 
      ease-in duration-500 transition
      ${startShow ? (show ? 'translate-y-0' : 'translate-y-[-100vh]') : 'translate-y-[-100vh]'}
      `}
      >
        <p className="font-extrabold text-5xl text-white text-center leading-[3.2rem] text-nowrap">
          <p className="text-white inline text-nowrap">{nickname}</p>님 정답!
        </p>
      </div>
    </div>
  );
};

export default QuizCorrect;
