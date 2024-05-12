import React from "react";

const HelpModal = () => {
    return  <dialog id="helpModal" className="modal">
    <div className="modal-box">
      <h3 className="font-bold text-lg">도움말</h3>
      <p className="py-4">내용</p>
      <div className="modal-action">
        <form method="dialog">
          <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
        </form>
      </div>
    </div>
  </dialog>
}

export default HelpModal;