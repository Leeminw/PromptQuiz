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
  return (
    <div className="border-custom-green bg-customGreen w-full h-full relative flex items-center">
      <button
        className="w-full h-full btn-mint-border-white flex justify-center items-center gap-2 px-3 hover:brightness-110"
        onClick={() => (document.getElementById('invite_modal') as HTMLDialogElement).showModal()}
      >
        <p className="text-nowrap lg:flex max-lg:hidden">방 만들기</p>
      </button>
      <body>
        <a id="kakao-link-btn" href="javascript:kakaoShare()">
          <img src="https://developers.kakao.com/assets/img/about/logos/kakaolink/kakaolink_btn_medium.png" />
        </a>
      </body>

      <dialog id="invite_modal" className="modal">
        <div className="modal-box border-2 border-lightmint flex flex-col gap-3 pb-14 bg-white/90 backdrop-blur-lg min-w-96">
          <h3 className="font-bold text-2xl">초대코드 발송하기</h3>
          <hr className="mb-1 border-extralightmint" />
          <div className="flex flex-col gap-3 overflow-x-hidden overflow-y-scroll custom-scroll">
            <div className="flex items-center gap-3 mt-1">
              <span className="font-extrabold text-nowrap pr-6">방 이름</span>
            </div>
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
