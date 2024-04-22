import React, { useEffect, useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import {Client, Message} from '@stomp/stompjs';




const ChatScreen = () => {
    

    const client = useRef<Client|null>(null)
    const [text, setText] = useState<string>('')
    const [chat, setChat] = useState<string[]>([])
    
    const socket = new SockJS(`http://localhost:8080/stomp/chat`);
    const connect = () => {
        
        client.current = new Client({
            webSocketFactory : () => socket,
            
            debug: function (str) {
                console.log(str);
            },
            reconnectDelay: 5000, //자동 재 연결
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
        });
        client.current.onConnect = (frame) => {
            client.current?.subscribe('/sub/chatting',(message)=>{
                if(message.body){
                    setChat(prevItems => [...prevItems, message.body])
                }
            })
            client.current?.publish({
                destination : '/pub/enter',
                body: '유저이름 : ' 
            })
        };
        
        client.current.onStompError = function (frame) {
            console.log('Broker reported error: ' + frame.headers['message']);
            console.log('Additional details: ' + frame.body);
        };
        
        client.current.activate();
    }

    useEffect( () =>{
        connect()
        
    },[])
    const sendMessage = () =>{
        console.log("send!!", text)
        client.current.publish({
            destination : '/pub/sendChat',
            body:text,
        })
    }
    const textHandler = (event:React.ChangeEvent<HTMLInputElement>) =>{
        setText(event.target.value)
    }
    return (
        
        <div>
            <div>채팅내역</div>
            <div>
                {chat.map((message, index) =>(
                    <ul key={index}>
                        <li>{index}, {message}</li>
                    </ul>
                ) )}
            </div>
            <div>인풋태그</div>
            <input type="text" className="border " value={text} onChange={textHandler} /> <button className='border ' onClick={sendMessage}>입력</button>
        </div>
        
    );
};

export default ChatScreen;