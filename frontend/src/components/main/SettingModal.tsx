import React from "react";

const SettingModal = () => {
    return  <dialog id="settingModal" className="modal">
    <div className="modal-box">
      <h3 className="font-bold text-lg">설정</h3>
      <p className="py-4">내용</p>
      <div className="modal-action">
        <form method="dialog">
          <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
        </form>
      </div>
    </div>
  </dialog>
}

export default SettingModal;