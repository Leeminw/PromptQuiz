import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { last } from 'lodash';

type JsonItem = {
  key: string;
  value: number;
};

const WebSocketTest = () => {
  const wordClass = ['주어', '목적어', '동사', '주형용사', '목형용사'];
  // 통계 변수
  const [top3MessagesStyle, setTop3MessagesStyle] = useState<JsonItem[]>([]);
  const [top10Messages1, setTop10Messages1] = useState<JsonItem[]>([]);
  const [top10Messages2, setTop10Messages2] = useState<JsonItem[]>([]);
  const [top10Messages3, setTop10Messages3] = useState<JsonItem[]>([]);
  const [top10Messages4, setTop10Messages4] = useState<JsonItem[]>([]);
  const [top10Messages5, setTop10Messages5] = useState<JsonItem[]>([]);

  // 결과 변수
  const [remainTime, setRemainTime] = useState<string>('');
  const [resultStyle, setResultStyle] = useState<string>('');
  const [resultImage, setResultImage] = useState<string>('');
  const [resultSentence, setResultSentence] = useState<string>('');
  const [resultTop10Subjects, setResultTop10Subjects] = useState<JsonItem[]>([]);
  const [resultTop10Objects, setResultTop10Objects] = useState<JsonItem[]>([]);
  const [resultTop10Verbs, setResultTop10Verbs] = useState<JsonItem[]>([]);
  const [resultTop10SubAdjectives, setResultTop10SubAdjectives] = useState<JsonItem[]>([]);
  const [resultTop10ObjAdjectives, setResultTop10ObjAdjectives] = useState<JsonItem[]>([]);

  // 채팅 내역
  const [messages1, setMessages1] = useState<string[]>([]);
  const [messages2, setMessages2] = useState<string[]>([]);
  const [messages3, setMessages3] = useState<string[]>([]);
  const [messages4, setMessages4] = useState<string[]>([]);
  const [messages5, setMessages5] = useState<string[]>([]);

  // 채팅입력창
  const [inputMessage1, setInputMessage1] = useState('');
  const [inputMessage2, setInputMessage2] = useState('');
  const [inputMessage3, setInputMessage3] = useState('');
  const [inputMessage4, setInputMessage4] = useState('');
  const [inputMessage5, setInputMessage5] = useState('');

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws/socket/connect');
    const client = new Client({
      // brokerURL: 'ws://localhost:8080/ws/socket/connect',
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        // Define topics for subscription
        const topics = [
          {
            topic: '/ws/sub/dottegi/subject',
            setter: setMessages1,
            top10Setter: setTop10Messages1,
          },
          { topic: '/ws/sub/dottegi/object', setter: setMessages2, top10Setter: setTop10Messages2 },
          { topic: '/ws/sub/dottegi/verb', setter: setMessages3, top10Setter: setTop10Messages3 },
          {
            topic: '/ws/sub/dottegi/sub-adjective',
            setter: setMessages4,
            top10Setter: setTop10Messages4,
          },
          {
            topic: '/ws/sub/dottegi/obj-adjective',
            setter: setMessages5,
            top10Setter: setTop10Messages5,
          },
        ];

        // Subscribe for reamining time
        client.subscribe('/ws/sub/dottegi', (message) => {
          const jsonObject = JSON.parse(message.body);
          setRemainTime(jsonObject.remainingTime);
          if (Object.keys(jsonObject).length > 1) {
            console.log(jsonObject);
            setResultStyle(jsonObject.style);
            setResultImage(jsonObject.image);
            setResultSentence(jsonObject.sentence);
            setResultTop10Subjects(jsonObject.topSubjects);
            setResultTop10Objects(jsonObject.topObjects);
            setResultTop10Verbs(jsonObject.topVerbs);
            setResultTop10SubAdjectives(jsonObject.topSubAdjectives);
            setResultTop10ObjAdjectives(jsonObject.topObjAdjectives);

            // 투표현황 초기화
            setTop3MessagesStyle([]);
            setTop10Messages1([]);
            setTop10Messages2([]);
            setTop10Messages3([]);
            setTop10Messages4([]);
            setTop10Messages5([]);
          }
        });

        client.subscribe('/ws/sub/dottegi/style', (message) => {
          const jsonData = JSON.parse(message.body);
          setTop3MessagesStyle(jsonData);
        });

        // Subscribe to each topic
        topics.forEach(({ topic, setter, top10Setter }) => {
          client.subscribe(topic, (message) => {
            try {
              const jsonData = JSON.parse(message.body);
              console.log(jsonData);
              if (Array.isArray(jsonData)) {
                const top10JsonData: JsonItem[] = jsonData;
                top10Setter(top10JsonData);
              } else {
                setter((prevMessages) => [...prevMessages, message.body]);
              }
            } catch {
              setter((prevMessages) => [...prevMessages, message.body]);
            }
          });
        });
      },

      onStompError: (frame) => {
        console.error('Broker reported error: ' + frame.headers['message']);
        console.error('Additional details: ' + frame.body);
      },
    });

    client.activate();

    return () => {
      client.deactivate();
    };
  }, []);

  const sendMessage = (input: string, destination: string) => {
    const socket = new SockJS('http://localhost:8080/ws/socket/connect');
    const client = new Client({
      // brokerURL: 'ws://localhost:8080/ws/socket/connect',
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        client.publish({
          destination,
          body: input,
        });
        client.deactivate();
      },
    });
    client.activate();
  };

  // 한글 문자 받침 여부 확인
  const checkLastKoreanCharacter = (word: string) => {
    const lastCh = word.charCodeAt(word.length - 1);
    const checkLastCh = (lastCh - 0xac00) % 28;
    return checkLastCh ? true : false;
  };

  // 순위에 따라 다른 스타일을 입힘
  const getStyleByRank = (rank: number): string => {
    if (rank === 0) return 'mb-2 h-12 bg-yellow-200 border-custom-yellow';
    if (rank === 1) return 'mb-2 h-12 bg-gray-200 border-custom-gray';
    if (rank === 2) return 'mb-2 h-12 bg-yellow-500 border-custom-yellow';
    return 'mb-2 h-12 bg-white border-custom-mint';
  };
  return (
    <div className="flex flex-col items-center w-full">
      <h1 className="text-2xl font-bold my-4">WebSocket STOMP Multi-Channel Chat</h1>
      <h1 className="text-8xl font-bold my-4">{remainTime}</h1>
      <h1 className="text-4xl font-bold my-4">시장통 결과화면</h1>
      <div className="w-1/2 flex flex-col gap-3 custom-scroll py-1 pr-2 pl-1 border-custom-mint">
        <img src="https://source.unsplash.com/random/?seoul" alt="" className="w-full h-1/2" />
      </div>
      <h1 className="text-xl font-bold my-4">{resultStyle}</h1>
      <h1 className="text-2xl font-bold my-4">{resultSentence}</h1>
      <div className="flex justify-around w-full">
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">주어</div>
          {/* {resultTop10SubAdjectives.map((subAdjective, index) => ( */}
          {resultTop10Subjects.map((subAdjective, index) => (
            <div key={index} className="mb-2">
              {Object.keys(subAdjective)[0]} : {Object.values(subAdjective)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">목적어</div>
          {/* {resultTop10Subjects.map((subject, index) => ( */}
          {resultTop10Objects.map((subject, index) => (
            <div key={index} className="mb-2">
              {Object.keys(subject)[0]} : {Object.values(subject)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">동사</div>
          {/* {resultTop10ObjAdjectives.map((objAdjective, index) => ( */}
          {resultTop10Verbs.map((objAdjective, index) => (
            <div key={index} className="mb-2">
              {Object.keys(objAdjective)[0]} : {Object.values(objAdjective)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">주형용사</div>
          {/* {resultTop10Objects.map((object, index) => ( */}
          {resultTop10SubAdjectives.map((object, index) => (
            <div key={index} className="mb-2">
              {Object.keys(object)[0]} : {Object.values(object)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">목형용사</div>
          {/* {resultTop10Verbs.map((verb, index) => ( */}
          {resultTop10ObjAdjectives.map((verb, index) => (
            <div key={index} className="mb-2">
              {Object.keys(verb)[0]} : {Object.values(verb)[0]} 개
            </div>
          ))}
        </div>
      </div>
      <img src={resultImage} />
      <hr />

      <h1 className="text-4xl font-bold my-4">현재까지 결과</h1>
      <hr />

      <div>
        {top10Messages1.length > 0 && (
          <div className="text-xl">
            {top10Messages4.length > 0 && Object.keys(top10Messages4[0])[0]}&nbsp;
            {top10Messages1.length > 0 && Object.keys(top10Messages1[0])[0]}이(가)&nbsp;
            {top10Messages5.length > 0 && Object.keys(top10Messages5[0])[0]}&nbsp;
            {top10Messages2.length > 0 && Object.keys(top10Messages2[0])[0]}을(를)&nbsp;
            {top10Messages3.length > 0 && Object.keys(top10Messages3[0])[0]}
          </div>
        )}
      </div>
      <div className="flex justify-around w-full">
        <div
          className={top3MessagesStyle.length > 0 && 'flex flex-col justify-end border-custom-mint'}
        >
          {top3MessagesStyle.map((item, index) => {
            const key = Object.keys(item)[0];
            const value = Object.values(item)[0];

            if (key === 'Anime') {
              return (
                <div key={index} className={`mb-2`}>
                  {/* <div className="anime-style"> */}
                  <div className={getStyleByRank(index)}>
                    <strong>애니 스타일:</strong> {value}
                  </div>
                </div>
              );
            } else if (key === 'Cartoon') {
              return (
                <div key={index} className={`mb-2`}>
                  {/* <div className="cartoon-style"> */}
                  <div className={getStyleByRank(index)}>
                    <strong>디즈니 스타일:</strong> {value}
                  </div>
                </div>
              );
            } else if (key === 'Realistic') {
              return (
                <div key={index} className={`mb-2`}>
                  {/* <div className="realistic-style"> */}
                  <div className={getStyleByRank(index)}>
                    <strong>실사 스타일:</strong> {value}
                  </div>
                </div>
              );
            }
          })}
        </div>
      </div>
      <div className="flex justify-around w-full">
        <div className="flex flex-col mb-2">
          <button
            onClick={() => sendMessage('Anime', '/ws/pub/dottegi/style')}
            className="bg-red-500 text-white p-2 rounded hover:bg-blue-700 transition duration-200"
          >
            애니
          </button>
        </div>
        <div className="flex flex-col">
          <button
            onClick={() => sendMessage('Cartoon', '/ws/pub/dottegi/style')}
            className="bg-red-500 text-white p-2 rounded hover:bg-blue-700 transition duration-200"
          >
            카툰
          </button>
        </div>
        <div className="flex flex-col">
          <button
            onClick={() => sendMessage('Realistic', '/ws/pub/dottegi/style')}
            className="bg-red-500 text-white p-2 rounded hover:bg-blue-700 transition duration-200"
          >
            실사
          </button>
        </div>
      </div>

      <div className="flex justify-around w-full">
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">주어</div>
          {top10Messages1.map((subAdjective, index) => (
            // <div key={index} className="mb-2">
            <div key={index} className={getStyleByRank(index)}>
              {Object.keys(subAdjective)[0]} : {Object.values(subAdjective)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">목적어</div>
          {top10Messages2.map((subject, index) => (
            <div key={index} className={getStyleByRank(index)}>
              {Object.keys(subject)[0]} : {Object.values(subject)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">동사</div>
          {top10Messages3.map((objAdjective, index) => (
            <div key={index} className={getStyleByRank(index)}>
              {Object.keys(objAdjective)[0]} : {Object.values(objAdjective)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">주형용사</div>
          {top10Messages4.map((object, index) => (
            <div key={index} className={getStyleByRank(index)}>
              {Object.keys(object)[0]} : {Object.values(object)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">목형용사</div>
          {top10Messages5.map((verb, index) => (
            <div key={index} className={getStyleByRank(index)}>
              {Object.keys(verb)[0]} : {Object.values(verb)[0]} 개
            </div>
          ))}
        </div>
      </div>

      <div className="flex justify-around w-full px-4">
        {[inputMessage4, inputMessage1, inputMessage5, inputMessage2, inputMessage3].map(
          (input, index) => (
            <div key={index} className="flex flex-col w-1/6">
              {/* <div className="overflow-auto h-48 mb-2 p-2 border border-gray-200"> */}
              <div className="w-full p-2 h-48 mb-3 overflow-y-scroll custom-scroll border-custom-mint bg-white">
                {[messages1, messages2, messages3, messages4, messages5][index].map(
                  (msg, msgIndex) => (
                    <div key={msgIndex} className="text-sm">
                      {msg}
                    </div>
                  )
                )}
              </div>
              <input
                type="text"
                value={input}
                onChange={(e) => {
                  const setters = [
                    setInputMessage4,
                    setInputMessage1,
                    setInputMessage5,
                    setInputMessage2,
                    setInputMessage3,
                  ];
                  setters[index](e.target.value);
                }}
                // 엔터를 눌러도 단어 입력
                onKeyDown={(e) => {
                  if (e.key === 'Enter') {
                    sendMessage(
                      input,
                      `/ws/pub/dottegi/${['subject', 'object', 'verb', 'sub-adjective', 'obj-adjective'][index]}`
                    );
                  }
                }}
                // placeholder={`Type your message in chat ${index + 1}...`}
                placeholder={`${wordClass[index]}를 입력해주세요`}
                className="p-2 border border-gray-300 rounded mb-2"
              />
              <button
                onClick={() =>
                  sendMessage(
                    input,
                    `/ws/pub/dottegi/${['subject', 'object', 'verb', 'sub-adjective', 'obj-adjective'][index]}`
                  )
                }
                // className="w-fit h-full btn-mint-border-white hover:brightness-110 flex justify-center items-center gap-2 px-4"

                className="btn-mint-border-white bg-green-500 text-white p-2 rounded hover:brightness-110 transition duration-200"
              >
                입력
              </button>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default WebSocketTest;
