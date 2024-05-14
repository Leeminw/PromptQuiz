import React, { useEffect, useState } from 'react';
import { LobbyApi } from '../../hooks/axios-lobby';

import instance from '../../hooks/axios-instance';
import { Link, useNavigate } from 'react-router-dom';
import useUserStore from '../../stores/userStore';
import { RankApi } from '../../hooks/axios-rank';
import RankingList from '../ranking/RankingList';
import { BsFillTrophyFill } from 'react-icons/bs';

const Rank = () => {
  const [privacyStatus, setPrivacyStatus] = useState(0);
  const [isTeam, setIsTeam] = useState(false);
  const [type, setType] = useState(0);
  const [maxPlayers, setMaxPlayers] = useState(1);
  const [rounds, setRounds] = useState(1);
  const { user } = useUserStore();
  const [status, setStatus] = useState(false);
  const curPlayers = 1;
  const curRound = 0;
  const [styleIndex, setStyleIndex] = useState(0);
  const [password, setPassword] = useState('');
  const [title, setTitle] = useState<string>('');

  const [teamRankingArray, setTeamRankingArray] = useState<Ranking[]>([]);
  const [soloRankingArray, setSoloRankingArray] = useState<Ranking[]>([]);
  const [totalRankingArray, setTotalRankingArray] = useState<Ranking[]>([]);
  useEffect(() => {
    // 랭킹 리스트 가져오기
    const response = RankApi.getRankingList()
      .then((response) => {
        console.log('모달창 데이터 정보');

        console.log(response.data);

        setTeamRankingArray(response.data.teamRanking);
        setSoloRankingArray(response.data.soloRanking);
        setTotalRankingArray(response.data.totalRanking);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const navigate = useNavigate();
  const privacyHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPrivacyStatus(Number(event.target.value));
    console.log(privacyStatus);
  };
  const isTeamHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setIsTeam(Number(event.target.value) === 1);
    console.log(isTeam);
  };
  const typeHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setType(Number(event.target.value));
    console.log(type);
  };
  const maxPlayersHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setMaxPlayers(Number(event.target.value));
    console.log(maxPlayers);
  };
  const maxRoundHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setRounds(Number(event.target.value));
    console.log(rounds);
  };
  const styleHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setStyleIndex(Number(event.target.value));
    console.log(styleIndex);
  };

  const titleHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
    console.log(title);
  };
  const passwordHandler = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
    console.log(password);
  };
  return (
    <div className="w-full h-full">
      <button
        className="w-fit h-full bg-customYellow btn-yellow-border-white text-white flex justify-center items-center gap-2 px-4 hover:brightness-110"
        onClick={() => (document.getElementById('ranking_modal') as HTMLDialogElement).showModal()}
      >
        <BsFillTrophyFill className="min-w-4 min-h-4 " />
        <p className="text-nowrap font-extrabold lg:flex max-lg:hidden">랭킹</p>
      </button>
      <dialog id="ranking_modal" className="modal">
        <div className="modal-box max-w-[60rem] border-2 border-lightmint overflow-hidden">
          <h3 className="font-bold text-2xl pt-2">랭킹</h3>
          <hr className="my-4" />
          <div className="flex flex-row items-center h-[30rem] gap-6">
            <RankingList rankingTitle={'통합 랭킹'} users={totalRankingArray} rankingType={1} />
            <RankingList rankingTitle={'개인전 랭킹'} users={soloRankingArray} rankingType={2} />
            <RankingList rankingTitle={'팀 랭킹'} users={teamRankingArray} rankingType={3} />
          </div>
          <div className="modal-action">
            <form method="dialog">
              <button className="bg-[#999999] text-white border-custom-gray px-6 font-bold hover:brightness-110">
                닫기
              </button>
            </form>
          </div>
          {/* // */}
          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-4 top-5 text-lg mt-2">
                ✕
              </button>
            </form>
          </div>
          {/* // */}
        </div>
      </dialog>
    </div>
  );
};

export default Rank;
