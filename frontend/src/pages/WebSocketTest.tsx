import React, { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import axios from 'axios';
import { last } from 'lodash';

const WebSocketTest = () => {
  const wordClass = ['주어', '목적어', '동사', '주형용사', '목형용사'];

  // 채팅 내역
  const [chatLogVerbs, setChatLogVerbs] = useState<string[]>([]);
  const [chatLogObjects, setChatLogObjects] = useState<string[]>([]);
  const [chatLogSubjects, setChatLogSubjects] = useState<string[]>([]);
  const [chatLogSubAdjectives, setChatLogSubAdjectives] = useState<string[]>([]);
  const [chatLogObjAdjectives, setChatLogObjAdjectives] = useState<string[]>([]);

  // 채팅입력창
  const [inputVerb, setInputVerb] = useState('');
  const [inputObject, setInputObject] = useState('');
  const [inputSubject, setInputSubject] = useState('');
  const [inputSubAdjective, setInputSubAdjective] = useState('');
  const [inputObjAdjective, setInputObjAdjective] = useState('');

  // 결과 변수
  const [remainTime, setRemainTime] = useState<string>('');
  const [lastUpdatedUrl, setLastUpdatedUrl] = useState<string>('');

  // 이전 결과 통계
  const [lastUpdatedStyles, setLastUpdatedStyles] = useState<Object[]>([]);
  const [lastUpdatedSubjects, setLastUpdatedSubjects] = useState<Object[]>([]);
  const [lastUpdatedObjects, setLastUpdatedObjects] = useState<Object[]>([]);
  const [lastUpdatedVerbs, setLastUpdatedVerbs] = useState<Object[]>([]);
  const [lastUpdatedSubAdjectives, setLastUpdatedSubAdjectives] = useState<Object[]>([]);
  const [lastUpdatedObjAdjectives, setLastUpdatedObjAdjectives] = useState<Object[]>([]);

  // 현재 결과 통계
  const [curUpdatedStyles, setCurUpdatedStyles] = useState<Object[]>([]);
  const [curUpdatedSubjects, setCurUpdatedSubjects] = useState<Object[]>([]);
  const [curUpdatedObjects, setCurUpdatedObjects] = useState<Object[]>([]);
  const [curUpdatedVerbs, setCurUpdatedVerbs] = useState<Object[]>([]);
  const [curUpdatedSubAdjectives, setCurUpdatedSubAdjectives] = useState<Object[]>([]);
  const [curUpdatedObjAdjectives, setCurUpdatedObjAdjectives] = useState<Object[]>([]);

  useEffect(() => {
    axios
      .get('http://localhost:8080/api/v1/dottegi')
      .then((response) => {
        setLastUpdatedUrl(response.data.data.lastUpdatedUrl);
        setLastUpdatedStyles(response.data.data.lastUpdatedStyles);
        setLastUpdatedSubjects(response.data.data.lastUpdatedSubjects);
        setLastUpdatedObjects(response.data.data.lastUpdatedObjects);
        setLastUpdatedVerbs(response.data.data.lastUpdatedVerbs);
        setLastUpdatedSubAdjectives(response.data.data.lastUpdatedSubAdjectives);
        setLastUpdatedObjAdjectives(response.data.data.lastUpdatedObjAdjectives);

        setCurUpdatedStyles(response.data.data.curUpdatedStyles);
        setCurUpdatedSubjects(response.data.data.curUpdatedSubjects);
        setCurUpdatedObjects(response.data.data.curUpdatedObjects);
        setCurUpdatedVerbs(response.data.data.curUpdatedVerbs);
        setCurUpdatedSubAdjectives(response.data.data.curUpdatedSubAdjectives);
        setCurUpdatedObjAdjectives(response.data.data.curUpdatedObjAdjectives);
      })
      .catch();

    const socket = new SockJS('http://localhost:8080/ws/socket/connect');
    const client = new Client({
      // brokerURL: 'ws://localhost:8080/ws/socket/connect',
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
      onConnect: () => {
        // Subscribe for reamining time
        client.subscribe('/ws/sub/dottegi', (message) => {
          const jsonObject = JSON.parse(message.body);
          setRemainTime(jsonObject.remainingTime);
          if (Object.keys(jsonObject).length > 1) {
            setLastUpdatedUrl(jsonObject.lastUpdatedUrl);
            setLastUpdatedStyles(jsonObject.lastUpdatedStyles);
            setLastUpdatedSubjects(jsonObject.lastUpdatedSubjects);
            setLastUpdatedObjects(jsonObject.lastUpdatedObjects);
            setLastUpdatedVerbs(jsonObject.lastUpdatedVerbs);
            setLastUpdatedSubAdjectives(jsonObject.lastUpdatedSubAdjectives);
            setLastUpdatedObjAdjectives(jsonObject.lastUpdatedObjAdjectives);

            setCurUpdatedStyles(jsonObject.curUpdatedStyles);
            setCurUpdatedSubjects(jsonObject.curUpdatedSubjects);
            setCurUpdatedObjects(jsonObject.curUpdatedObjects);
            setCurUpdatedVerbs(jsonObject.curUpdatedVerbs);
            setCurUpdatedSubAdjectives(jsonObject.curUpdatedSubAdjectives);
            setCurUpdatedObjAdjectives(jsonObject.curUpdatedObjAdjectives);
          }
        });

        client.subscribe('/ws/sub/dottegi/style', (message) => {
          const jsonData = JSON.parse(message.body);
          setCurUpdatedStyles(jsonData);
        });

        // Define topics for subscription
        const topics = [
          {
            topic: '/ws/sub/dottegi/subject',
            setter: setChatLogSubjects,
            top10Setter: setCurUpdatedSubjects,
          },
          {
            topic: '/ws/sub/dottegi/object',
            setter: setChatLogObjects,
            top10Setter: setCurUpdatedObjects,
          },
          {
            topic: '/ws/sub/dottegi/verb',
            setter: setChatLogVerbs,
            top10Setter: setCurUpdatedVerbs,
          },
          {
            topic: '/ws/sub/dottegi/sub-adjective',
            setter: setChatLogSubAdjectives,
            top10Setter: setCurUpdatedSubAdjectives,
          },
          {
            topic: '/ws/sub/dottegi/obj-adjective',
            setter: setChatLogObjAdjectives,
            top10Setter: setCurUpdatedObjAdjectives,
          },
        ];

        // Subscribe to each topic
        topics.forEach(({ topic, setter, top10Setter }) => {
          client.subscribe(topic, (message) => {
            try {
              const jsonData = JSON.parse(message.body);
              if (Array.isArray(jsonData)) {
                top10Setter(jsonData);
              } else {
                setter((prevMessages) => [...prevMessages, message.body]);
              }
            } catch (error) {
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
      <h1 className="text-4xl font-bold my-4">결과화면</h1>
      <h1 className="text-xl font-bold my-4">
        {lastUpdatedStyles.map((item, index) => {
          if (Object.keys(item)[0] === 'Anime') {
            return (
              <div key={index} className="mb-2">
                애니 스타일: {Object.values(item)[0]}개
              </div>
            );
          } else if (Object.keys(item)[0] === 'Cartoon') {
            return (
              <div key={index} className="mb-2">
                디즈니 스타일: {Object.values(item)[0]}개
              </div>
            );
          } else if (Object.keys(item)[0] === 'Realistic') {
            return (
              <div key={index} className="mb-2">
                실사 스타일: {Object.values(item)[0]}개
              </div>
            );
          }
        })}
      </h1>
      <h1 className="text-2xl font-bold my-4">
        {lastUpdatedSubAdjectives.length > 0 && Object.keys(lastUpdatedSubAdjectives[0])[0]}&nbsp;
        {lastUpdatedSubjects.length > 0 && Object.keys(lastUpdatedSubjects[0])[0]}이(가)&nbsp;
        {lastUpdatedObjAdjectives.length > 0 && Object.keys(lastUpdatedObjAdjectives[0])[0]}&nbsp;
        {lastUpdatedObjects.length > 0 && Object.keys(lastUpdatedObjects[0])[0]}을(를)&nbsp;
        {lastUpdatedVerbs.length > 0 && Object.keys(lastUpdatedVerbs[0])[0]}하(고) 있다.
      </h1>

      <div className="flex justify-around w-full mb-10">
        <div className="flex flex-col bg-mint border-custom-mint">
          <div className="flex justify-center w-full text-xl text-white font-bold">주형용사</div>
          {lastUpdatedSubAdjectives.map((item, index) => (
            <div key={index} className="mb-2 text-white font-bold">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col bg-mint border-custom-mint">
          <div className="flex justify-center w-full text-xl text-white font-bold">주어</div>
          {lastUpdatedSubjects.map((item, index) => (
            <div key={index} className="mb-2 text-white font-bold">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col bg-mint border-custom-mint">
          <div className="flex justify-center w-full text-xl text-white font-bold">목형용사</div>
          {lastUpdatedObjAdjectives.map((item, index) => (
            <div key={index} className="mb-2 text-white font-bold">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>

        <div className="flex flex-col bg-mint border-custom-mint">
          <div className="flex justify-center w-full text-xl text-white font-bold">목적어</div>
          {lastUpdatedObjects.map((item, index) => (
            <div key={index} className="mb-2 text-white font-bold">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col bg-mint border-custom-mint">
          <div className="flex justify-center w-full text-xl text-white font-bold">동사</div>
          {lastUpdatedVerbs.map((item, index) => (
            <div key={index} className="mb-2 text-white font-bold">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
      </div>
      <div className="w-1/2 flex flex-col gap-3 custom-scroll py-1 pr-2 pl-1 border-custom-mint">
        <img src={lastUpdatedUrl} />
      </div>
      <hr />

      <h1 className="text-4xl font-bold my-4">현재까지 생성된 프롬프트 결과</h1>
      <hr />

      <div>
        <h1 className="text-2xl font-bold my-4">
          {curUpdatedSubAdjectives.length > 0 && Object.keys(curUpdatedSubAdjectives[0])[0]}&nbsp;
          {curUpdatedSubjects.length > 0 && Object.keys(curUpdatedSubjects[0])[0]}이(가)&nbsp;
          {curUpdatedObjAdjectives.length > 0 && Object.keys(curUpdatedObjAdjectives[0])[0]}&nbsp;
          {curUpdatedObjects.length > 0 && Object.keys(curUpdatedObjects[0])[0]}을(를)&nbsp;
          {curUpdatedVerbs.length > 0 && Object.keys(curUpdatedVerbs[0])[0]}하(고) 있다.
        </h1>
      </div>
      <h1 className="text-xl font-bold my-4">
        <div className="w-full h-full bg-mint border-custom-mint">
          <div className="w-full h-8 bg-mint text-white font-bold text-lg flex items-center mb-3">
            그림체 순위
          </div>
          <div className="w-full  flex flex-col gap-3 border-custom-white bg-white py-1 pr-2 pl-1">
            {curUpdatedStyles.map((item, index) => {
              if (Object.keys(item)[0] === 'Anime') {
                return (
                  <div key={index} className={getStyleByRank(index)}>
                    애니 스타일: {Object.values(item)[0]}개
                  </div>
                );
              } else if (Object.keys(item)[0] === 'Cartoon') {
                return (
                  <div key={index} className={getStyleByRank(index)}>
                    디즈니 스타일: {Object.values(item)[0]}개
                  </div>
                );
              } else if (Object.keys(item)[0] === 'Realistic') {
                return (
                  <div key={index} className={getStyleByRank(index)}>
                    실사 스타일: {Object.values(item)[0]}개
                  </div>
                );
              }
            })}
          </div>
        </div>
      </h1>
      <div className="flex justify-around w-full">
        <div className="flex flex-col mb-2">
          <button
            onClick={() => sendMessage('Anime', '/ws/pub/dottegi/style')}
            className="btn-mint-border-white w-[5rem] bg-green-500 text-white p-2 rounded hover:brightness-110 transition duration-200"
          >
            애니
          </button>
        </div>
        <div className="flex flex-col">
          <button
            onClick={() => sendMessage('Cartoon', '/ws/pub/dottegi/style')}
            className="btn-mint-border-white w-[5rem]  bg-green-500 text-white p-2 rounded hover:brightness-110 transition duration-200"
          >
            카툰
          </button>
        </div>
        <div className="flex flex-col">
          <button
            onClick={() => sendMessage('Realistic', '/ws/pub/dottegi/style')}
            className="btn-mint-border-white w-[5rem]  bg-green-500 text-white p-2 rounded hover:brightness-110 transition duration-200"
          >
            실사
          </button>
        </div>
      </div>

      {/* <div className="flex justify-around w-full">
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">주형용사</div>
          {curUpdatedSubAdjectives.map((item, index) => (
            <div key={index} className="mb-2">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">주어</div>
          {curUpdatedSubjects.map((item, index) => (
            <div key={index} className="mb-2">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">목형용사</div>
          {curUpdatedObjAdjectives.map((item, index) => (
            <div key={index} className="mb-2">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">목적어</div>
          {curUpdatedObjects.map((item, index) => (
            <div key={index} className="mb-2">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          <div className="flex justify-center w-full text-xl">동사</div>
          {curUpdatedVerbs.map((item, index) => (
            <div key={index} className="mb-2">
              {Object.keys(item)[0]} : {Object.values(item)[0]} 개
            </div>
          ))}
        </div>
      </div> */}

      <div className="flex justify-around w-full px-4">
        {[inputSubAdjective, inputSubject, inputObjAdjective, inputObject, inputVerb].map(
          (input, index) => (
            <div key={index} className="flex flex-col w-1/6">
              <div className="overflow-setChatLogSubjects h-48 mb-2 p-2 border border-gray-200">
                {[
                  chatLogSubAdjectives,
                  chatLogSubjects,
                  chatLogObjAdjectives,
                  chatLogObjects,
                  chatLogVerbs,
                ][index].map((msg, index2) => (
                  <div key={index2} className="text-sm">
                    {msg}
                  </div>
                ))}
              </div>
              <input
                type="text"
                value={input}
                onChange={(e) => {
                  const setters = [
                    setInputSubAdjective,
                    setInputSubject,
                    setInputObjAdjective,
                    setInputObject,
                    setInputVerb,
                  ];
                  setters[index](e.target.value);
                }}
                // 엔터를 눌러도 단어 입력
                onKeyDown={(e) => {
                  if (e.key === 'Enter') {
                    sendMessage(
                      input,
                      // `/ws/pub/dottegi/${['subject', 'object', 'verb', 'sub-adjective', 'obj-adjective'][index]}`
                      `/ws/pub/dottegi/${['sub-adjective', 'subject', 'obj-adjective', 'object', 'verb'][index]}`
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
                    `/ws/pub/dottegi/${['sub-adjective', 'subject', 'obj-adjective', 'object', 'verb'][index]}`
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
