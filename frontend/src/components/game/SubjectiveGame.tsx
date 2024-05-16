import React, { useEffect, useState } from 'react';
interface SubjectiveGameProps {
  answerWord: Word | null;
  playerSimilarity: PlayerSimilarity | null;
}
const SubjectiveGame = ({ answerWord, playerSimilarity }: SubjectiveGameProps) => {
  const sentenceOrder = [
    'kor_sub_adjective',
    'kor_subject',
    'kor_obj_adjective',
    'kor_object',
    'kor_verb',
  ];
  const translationMap: Record<string, string> = {
    kor_sub_adjective: '형용사',
    kor_subject: '주어',
    kor_obj_adjective: '형용사',
    kor_object: '목적어',
    kor_verb: '동사',
  };
  const conjunction = ['', '이(가)', '', '을(를)'];

  const similarityOrder = ['kor_sub_adjective', 'kor_subject', 'kor_obj_adjective', 'kor_object'];
  const firstFontClass = 'text-base text-center font-bold';
  const elseFontClass = 'text-base text-center mt-0.5';
  const firstRateFontClass = 'font-extrabold inline px-1';
  const elseRateFontClass = 'font-bold inline px-1';

  if (!answerWord || !playerSimilarity) return <div>대기화면</div>;
  // if (!choiceList) return <div>no game</div>;
  return (
    <div className="w-full h-full relative">
      <div className="w-full bg-white border-custom-green absolute -translate-y-16">
        <div className="w-full h-full">
          {sentenceOrder.map((field: string, index) => (
            <span key={index}>
              {answerWord[field] === null ? '[???]' : answerWord[field]} {conjunction[index]}{' '}
            </span>
          ))}
        </div>
      </div>
      <div className="w-full h-full flex relative">
        <div className="absolute w-full h-full flex gap-4 opacity-80 -z-10">
          {similarityOrder.map((field: string, index) => (
            <div
              className="bg-white border-custom-red text-black w-1/3 h-full flex flex-col items-center justify-center"
              key={index}
            >
              <div>{translationMap[field]}</div>
              {// 오답인경우
              playerSimilarity[field]?.map((similarity: Similarity, idx) => (
                <div className={idx == 0 ? firstFontClass : elseFontClass} key={idx}>
                  <p className="font-bold inline">{similarity.value}</p>
                  <p className={idx == 0 ? firstRateFontClass : elseRateFontClass}>
                    [{similarity.rate}%]
                  </p>
                </div>
              ))}
            </div>
          ))}
          {/* <div className="bg-white border-custom-blue text-black w-1/3 h-full flex flex-col items-center justify-center">
            <div className="text-2xl text-center font-bold">
              <p className="font-bold inline">먹는</p>
              <p className="font-extrabold inline px-1">[72%]</p>
            </div>
            <div className="text-lg text-center mt-0.5">
              <p className="font-bold inline">뜯는</p>
              <p className="font-bold inline px-1">[43%]</p>
            </div>
            <div className="text-lg text-center">
              <p className="font-bold inline">식사하는</p>
              <p className="font-bold inline px-1">[21%]</p>
            </div>
          </div>
          <div className="bg-white border-custom-green text-black w-1/3 h-full flex flex-col items-center justify-center">
            <div className="text-2xl text-center font-bold">
              <p className="font-bold inline">푸바오</p>
              <p className="font-extrabold inline px-1">[72%]</p>
            </div>
            <div className="text-lg text-center mt-0.5">
              <p className="font-bold inline">사장</p>
              <p className="font-bold inline px-1">[43%]</p>
            </div>
            <div className="text-lg text-center">
              <p className="font-bold inline">대통령</p>
              <p className="font-bold inline px-1">[21%]</p>
            </div>
          </div> */}
        </div>
      </div>
    </div>
  );
};

export default SubjectiveGame;
