import {create} from 'zustand';
import { persist } from "zustand/middleware";
import { Client, Message, messageCallbackType } from '@stomp/stompjs';
import WebSocketManager from '../utils/WebSocketManager';
type WebSocketStore = {
  isConnected: boolean;
  connectWebSocket: (url:string, sub:messageCallbackType, enter:()=>void ,userId:bigint ) => void;
  disconnectWebSocket: () => void;
  subscribeWebSocket: (subUrl:string, callback: messageCallbackType) => void;
  publish: (destination:string, body:object) => void;
};

export const useWebSocketStore = create(
  persist<WebSocketStore>(
    (set) => ({
      isConnected: false,
      connectWebSocket: (url:string, callback:messageCallbackType,  enter:()=>void, userId:bigint) => {
        WebSocketManager.connect(url, callback, enter, String(userId));
        set({ isConnected: true });
      },
      disconnectWebSocket: () => {
        WebSocketManager.disconnect();
        set({ isConnected: false });
      },
      subscribeWebSocket : (subUrl:string, callback: messageCallbackType) => {
        WebSocketManager.subscribe(subUrl, callback)
      },
      publish : (destination:string, body:object) => {
        WebSocketManager.publish(destination,JSON.stringify(body))
      }
    }),
    {
      name: 'webSocketStore', // 로컬 스토리지에 저장될 키 이름
    }
  )
);