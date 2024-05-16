import React from 'react';

declare global {
  interface Window {
    kakao: any;
  }
}

const InviteUser = () => {
  // const loadKakaoScript = () => {
  //   console.log(window.kakao.isInitialized());
  // };
  const sendKakaoInvite = () => {
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
          title: '[APM] 게임 초대가 도착했습니다!',
          description: '참여하기를 눌러 게임에 참여하실 수 있습니다.',
          imageUrl: 'https://tenbizt.com/wp-content/uploads/2024/04/2-34.jpg',
          link: {
            mobileWebUrl: 'https://www.naver.com',
            webUrl: 'https://www.google.com',
          },
        },
        buttons: [
          {
            title: '참여하기',
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
    <dialog id="invite_modal" className="modal">
      <div className="modal-box border-2 border-lightmint flex flex-col gap-3 pb-14 bg-white/90 backdrop-blur-lg min-w-96">
        <h3 className="font-bold text-2xl">초대하기</h3>
        <hr className="mb-1 border-extralightmint" />
        <div className="flex flex-col gap-3 overflow-x-hidden overflow-y-scroll custom-scroll">
          {/* <button
            className="bg-black text-white border-custom-gray px-6 font-bold hover:brightness-110"
            onClick={sendKakaoInvite}
          >
            초대 코드 공유하기
          </button> */}
          <p>추후 추가될 예정입니다.</p>
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
  );
};

export default InviteUser;
