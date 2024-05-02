import React, { useEffect, useRef, useState } from 'react';
import { IoSend } from 'react-icons/io5';

const GameChat = () => {
  const chattingBox = useRef(null);
  const chatInput = useRef(null);
  const chatBtn = useRef(null);
  const [chatOpen, setChatOpen] = useState(false);

  useEffect(() => {
    // 채팅 입력 바깥 클릭 시 채팅창 닫기
    const handleOutsideClick = (event: MouseEvent) => {
      const target = event.target as Node;
      if (target && !chatInput.current?.contains(target) && !chatBtn.current?.contains(target)) {
        chatFocusOut();
      }
    };

    // 채팅 입력 안하고 있을 때 Enter시 채팅창 열기
    const handleChatKey = (event: KeyboardEvent) => {
      const target = event.target as Node;
      if (event.key === 'Enter' && !chatInput.current?.contains(target) && !chatOpen) {
        chatFocus();
      }
    };

    // 클릭 & 키다운 이벤트 추가
    document.addEventListener('click', handleOutsideClick);
    document.addEventListener('keydown', handleChatKey);
    return () => {
      document.removeEventListener('click', handleOutsideClick);
    };
  }, []);

  // 채팅 입력
  const chatFunction = () => {
    const chatChild = document.createElement('div');
    chatChild.className = 'flex';
    const chatUser = document.createElement('p');
    const chatMessage = document.createElement('p');
    chatUser.className = 'font-extrabold pr-1 text-nowrap text-black';
    chatUser.innerText = '푸바오 ㅠㅠㅠ : ';
    chatMessage.innerText = chatInput.current.value;
    chatChild.appendChild(chatUser);
    chatChild.appendChild(chatMessage);
    chatInput.current.value = '';
    chattingBox.current.appendChild(chatChild);
  };

  // 채팅창 열기
  const chatFocus = () => {
    chatInput.current.focus();
    setChatOpen(true);
  };

  // 채팅창 닫기
  const chatFocusOut = () => {
    chatInput.current.blur();
    setChatOpen(false);
  };

  return (
    <div className="w-full">
      <div className="relative w-full">
        <div
          className={`absolute flex items-center w-full h-36 bottom-0 mb-2 transition-all origin-bottom duration-300 ${chatOpen ? 'opacity-100 scale-100' : 'opacity-0 scale-0'}`}
        >
          <div className="absolute w-full h-[90%] px-3 py-2 text-sm chat z-10">
            <div className="z-10 text-gray-700" ref={chattingBox}></div>
          </div>
          <div className="absolute w-full h-full border-custom-white opacity-90 bg-white z-0"></div>
        </div>
      </div>

      <div className="w-full h-10 bg-white/80 rounded-full flex relative">
        <input
          ref={chatInput}
          className="w-full h-10 bg-transparent rounded-full pl-5 pr-20 text-sm placeholder-gray-400"
          maxLength={30}
          placeholder={`${chatOpen ? 'Esc를 눌러 채팅창 닫기' : 'Enter를 눌러 채팅 입력'}`}
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
              if (chatInput.current.value !== '') {
                chatFunction();
              }
            } else if (e.key === 'Escape') chatFocusOut();
          }}
          onClick={chatFocus}
        ></input>
        <div
          className="w-16 bg-mint cursor-pointer absolute h-full right-0 rounded-r-full flex justify-center items-center hover:brightness-125 transition"
          ref={chatBtn}
          onClick={() => {
            if (chatInput.current.value !== '') {
              chatFunction();
              if (!chatOpen) chatFocus();
            }
          }}
        >
          <IoSend className="text-white w-6 h-6" />
        </div>
      </div>
    </div>
  );
};

export default GameChat;
