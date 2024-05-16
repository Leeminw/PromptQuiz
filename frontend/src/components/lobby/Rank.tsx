import React from 'react';
import { BsFillTrophyFill } from 'react-icons/bs';

const Rank = () => {
  return (
    <div className="w-full h-full">
      <button
        className="w-fit h-full bg-customYellow btn-yellow-border-white text-white flex justify-center items-center gap-2 px-4 hover:brightness-110"
        onClick={() => (document.getElementById('ranking_modal') as HTMLDialogElement).showModal()}
      >
        <BsFillTrophyFill className="min-w-4 min-h-4 " />
        <p className="text-nowrap font-extrabold md:flex max-md:hidden select-none">랭킹</p>
      </button>
    </div>
  );
};

export default Rank;
