import React, { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react';
import customSetTimeout from '../../hooks/CustomSetTimeout';

declare global {
  interface Window {
    kakao: any;
  }
}

const InviteUser = () => {
  const loadKakaoScript = () => {
    console.log(window.kakao.isInitialized());
  };
  const [message, setMessage] = useState<string>('');

  const messageHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMessage(event.target.value);
  };

  const sendKakaoInvite = () => {
    alert(message);
    // 이후 Kakao API 관련된 소스코드 추가됨
    shareKakao();
  };

  const shareKakao = () => {
    if (window.Kakao) {
      const kakao = window.Kakao;
      if (!kakao.isInitialized()) {
        kakao.init(process.env.KAKAO_API_JAVASCRIPT_KEY);
      }

      kakao.Link.sendDefault({
        objectType: 'feed',
        content: {
          title: message,
          description: '설명란입니다',
          imageUrl:
            'https://developers.kakao.com/assets/img/about/logos/kakaotalksharing/kakaotalk_sharing_btn_medium.png',
          link: {
            mobileWebUrl: 'https://www.naver.com',
            webUrl: 'https://www.google.com',
          },
        },
        buttons: [
          {
            title: '자세히 보러 가기',
            link: {
              mobileWebUrl: 'https://www.naver.com',
              webUrl: 'https://www.google.com',
            },
          },
        ],
      });
    }
  };

  return (
    <div className="border-custom-green bg-customGreen w-full h-full relative flex items-center">
      <button
        className="w-full h-full btn-mint-border-white flex justify-center items-center gap-2 px-3 hover:brightness-110"
        onClick={() => (document.getElementById('invite_modal') as HTMLDialogElement).showModal()}
      >
        <img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png" />
        <p className="text-nowrap lg:flex max-lg:hidden">초대하기</p>
      </button>
      {/* <body>
        <a id="kakao-link-btn" href="javascript:kakaoShare()">
          
        </a>
      </body> */}

      <dialog id="invite_modal" className="modal">
        <div className="modal-box border-2 border-lightmint flex flex-col gap-3 pb-14 bg-white/90 backdrop-blur-lg min-w-96">
          <h3 className="font-bold text-2xl">초대코드 발송하기</h3>
          <hr className="mb-1 border-extralightmint" />
          <div className="flex flex-col gap-3 overflow-x-hidden overflow-y-scroll custom-scroll">
            <div className="flex items-center gap-3 mt-1">
              <span className="font-extrabold text-nowrap pr-6">전달할 메시지</span>
            </div>
            <input type="text" onChange={messageHandler} />
            <button
              className="bg-[#440044] text-white border-custom-gray px-6 font-bold hover:brightness-110"
              onClick={sendKakaoInvite}
            >
              전송하기
            </button>
            <div className="absolute bottom-6 right-6 flex gap-4 w-full justify-end">
              <form method="dialog">
                <button className="bg-[#999999] text-white border-custom-gray px-6 font-bold hover:brightness-110">
                  취소
                </button>
              </form>
            </div>
          </div>

          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-4 top-5 text-lg ">
                ✕
              </button>
            </form>
          </div>
        </div>
      </dialog>
    </div>
  );
};

export default InviteUser;
