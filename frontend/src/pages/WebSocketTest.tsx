import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';

type JsonItem = {
  key: string;
  value: number;
};

const WebSocketTest = () => {
  const wordClass = ['주어', '목적어', '동사', '주형용사', '목형용사'];
  const [top10Messages1, setTop10Messages1] = useState<JsonItem[]>([]);
  const [top10Messages2, setTop10Messages2] = useState<JsonItem[]>([]);
  const [top10Messages3, setTop10Messages3] = useState<JsonItem[]>([]);
  const [top10Messages4, setTop10Messages4] = useState<JsonItem[]>([]);
  const [top10Messages5, setTop10Messages5] = useState<JsonItem[]>([]);

  const [messages1, setMessages1] = useState<string[]>([]);
  const [messages2, setMessages2] = useState<string[]>([]);
  const [messages3, setMessages3] = useState<string[]>([]);
  const [messages4, setMessages4] = useState<string[]>([]);
  const [messages5, setMessages5] = useState<string[]>([]);

  const [inputMessage1, setInputMessage1] = useState('');
  const [inputMessage2, setInputMessage2] = useState('');
  const [inputMessage3, setInputMessage3] = useState('');
  const [inputMessage4, setInputMessage4] = useState('');
  const [inputMessage5, setInputMessage5] = useState('');

  useEffect(() => {
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
      onConnect: () => {
        // Define topics for subscription
        const topics = [
          { topic: '/dottegi/subject', setter: setMessages1, top10Setter: setTop10Messages1 },
          { topic: '/dottegi/object', setter: setMessages2, top10Setter: setTop10Messages2 },
          { topic: '/dottegi/verb', setter: setMessages3, top10Setter: setTop10Messages3 },
          { topic: '/dottegi/sub-adjective', setter: setMessages4, top10Setter: setTop10Messages4 },
          { topic: '/dottegi/obj-adjective', setter: setMessages5, top10Setter: setTop10Messages5 },
        ];

        // Subscribe to each topic
        topics.forEach(({ topic, setter, top10Setter }) => {
          client.subscribe(topic, (message) => {
            try {
              const jsonData = JSON.parse(message.body);
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
    const client = new Client({
      brokerURL: 'ws://localhost:8080/ws',
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

  return (
    <div className="flex flex-col items-center w-full">
      <h1 className="text-2xl font-bold my-4">WebSocket STOMP Multi-Channel Chat</h1>
      결과화면
      <hr />
      <div>
        {top10Messages1.length > 0 && (
          <div>
            {top10Messages4.length > 0 && Object.keys(top10Messages4[0])[0]}&nbsp;
            {top10Messages1.length > 0 && Object.keys(top10Messages1[0])[0]}이(가)&nbsp;
            {top10Messages5.length > 0 && Object.keys(top10Messages5[0])[0]}&nbsp;
            {top10Messages2.length > 0 && Object.keys(top10Messages2[0])[0]}을(를)&nbsp;
            {top10Messages3.length > 0 && Object.keys(top10Messages3[0])[0]}
          </div>
        )}
      </div>
      <div className="flex justify-around w-full">
        <div className="flex flex-col">
          {top10Messages1.map((item, index) => (
            <div key={index} className="mb-2">
              Key: {Object.keys(item)[0]}, Value: {Object.values(item)[0]}
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          {top10Messages2.map((item, index) => (
            <div key={index} className="mb-2">
              Key: {Object.keys(item)[0]}, Value: {Object.values(item)[0]}
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          {top10Messages3.map((item, index) => (
            <div key={index} className="mb-2">
              Key: {Object.keys(item)[0]}, Value: {Object.values(item)[0]}
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          {top10Messages4.map((item, index) => (
            <div key={index} className="mb-2">
              Key: {Object.keys(item)[0]}, Value: {Object.values(item)[0]}
            </div>
          ))}
        </div>
        <div className="flex flex-col">
          {top10Messages5.map((item, index) => (
            <div key={index} className="mb-2">
              Key: {Object.keys(item)[0]}, Value: {Object.values(item)[0]}
            </div>
          ))}
        </div>
      </div>
      <div className="flex justify-around w-full px-4">
        {[inputMessage1, inputMessage2, inputMessage3, inputMessage4, inputMessage5].map(
          (input, index) => (
            <div key={index} className="flex flex-col w-1/6">
              <h1>품사타이틀!!!</h1>
              <div className="overflow-auto h-48 mb-2 p-2 border border-gray-200">
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
                    setInputMessage1,
                    setInputMessage2,
                    setInputMessage3,
                    setInputMessage4,
                    setInputMessage5,
                  ];
                  setters[index](e.target.value);
                }}
                onKeyDown={(e) => {
                  if (e.key === 'Enter') {
                    sendMessage(
                      input,
                      `/chat/dottegi/${['subject', 'object', 'verb', 'sub-adjective', 'obj-adjective'][index]}`
                    );
                    // 엔터 눌렀을 때 input 초기화 코드
                    const setters = [
                      setInputMessage1,
                      setInputMessage2,
                      setInputMessage3,
                      setInputMessage4,
                      setInputMessage5,
                    ];
                    setters[index]('');
                  }
                }}
                placeholder={`Type your message in chat ${index + 1}...`}
                className="p-2 border border-gray-300 rounded mb-2"
              />
              <button
                onClick={() =>
                  sendMessage(
                    input,
                    `/chat/dottegi/${['subject', 'object', 'verb', 'sub-adjective', 'obj-adjective'][index]}`
                  )
                }
                className="bg-green-500 text-white p-2 rounded hover:bg-blue-700 transition duration-200"
              >
                Send Message
              </button>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default WebSocketTest;
