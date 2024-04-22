import React, { useEffect, useRef, useState } from 'react';
import SockJS from 'sockjs-client'

const ChatScreen = () => {
  const [messages, setMessages] = useState<string[]>([]);
  const webSocket = useRef<WebSocket | null>(null);    
  useEffect(() => {
    webSocket.current = new WebSocket('ws://localhost:8080/stomp/chat');
    webSocket.current.onopen = () => {
      console.log('WebSocket 연결!');    
    };
    webSocket.current.onclose = (error) => {
      console.log(error);
    }
    webSocket.current.onerror = (error) => {
      console.log(error);
    }
    webSocket.current.onmessage = (event: MessageEvent) => {   
      setMessages((prev) => [...prev, event.data]);
    };

    return () => {
      webSocket.current?.close();
    };
  }, []);
  const sendMessage = (message:string) => {
  if (webSocket.current.readyState === WebSocket.OPEN) {
    webSocket.current.send(message);
  }
  };
  return (
  <div>
    {messages?.map((message, index) => (
      <div key={index}>{message}</div>
    ))}
  </div>
);
};

export default ChatScreen;