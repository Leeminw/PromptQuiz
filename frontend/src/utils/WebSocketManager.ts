import SockJS from 'sockjs-client';
import { Client, Message, messageCallbackType } from '@stomp/stompjs';

class WebSocketManager {
  private client: Client | null;
  private sessionIdKey: string;

  constructor() {
    this.client = null;
  }

  public connect(url:string, sub:messageCallbackType, enter:()=>void , userId:string): void {
    const socket = new SockJS(`${process.env.REACT_APP_SOCKET}`); // WebSocket 서버 주소
    this.client = new Client({
        webSocketFactory: () => socket,

        debug: function (str) {
          console.log(str);
        },
        reconnectDelay: 5000, //자동 재 연결
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
      });
      
      this.client.onConnect = (frame) => {
          this.client.subscribe(url, sub, {
            'userId': userId
          })
          enter()
          console.log('connected!!!');
      };

      this.client.onStompError = function (frame) {
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
      };

      this.client.activate();
  }

  public disconnect(): void {
    if (this.client !== null) {
      this.client.deactivate();
      console.log('WebSocket disconnected');
    }
  }

  public subscribe(subUrl:string , callback:messageCallbackType) : void {

    this.client.subscribe(subUrl,callback)
  }

  public publish(destination: string, body:string){
    this.client.publish(
        {
            destination,
            body
        }
    )
  }
}

  // 기타 WebSocket 관련 메서드 추가


export default new WebSocketManager();
