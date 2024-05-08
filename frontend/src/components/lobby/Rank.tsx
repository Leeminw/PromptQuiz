import React, { useEffect, useState } from 'react';
import { MdAddHome } from 'react-icons/md';
import { LobbyApi } from '../../hooks/axios-lobby';

import instance from '../../hooks/axios-instance';
import { Link, useNavigate } from 'react-router-dom';
import useUserStore from '../../stores/userStore';
import { RankApi } from '../../hooks/axios-rank';
import RankingList from '../ranking/RankingList';

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
    // className="w-1/3 h-[100px] bg-white-300 gap-1 border-2 "
    <div className="w-fit h-full">
      <button
        className="w-full h-full bg-customYellow flex justify-center items-center gap-2 px-1 hover:brightness-110"
        onClick={() => (document.getElementById('ranking_modal') as HTMLDialogElement).showModal()}
      >
        <MdAddHome className="min-w-5 min-h-5 " />
        <p className="text-nowrap">랭킹 표시</p>
      </button>
      <dialog id="ranking_modal" className="modal">
        <div className="modal-box">
          <h3 className="font-bold text-lg">랭킹</h3>
          <div>랭킹화면</div>
          <div className="flex flex-row items-center">
            <RankingList rankingTitle={'통합 랭킹'} users={totalRankingArray} />
            <RankingList rankingTitle={'개인전 랭킹'} users={soloRankingArray} />
            <RankingList rankingTitle={'팀 랭킹'} users={teamRankingArray} />
          </div>
          <div className="modal-action">
            <form method="dialog">
              <button
                className="bg-customGreen text-white hover:brightness-125 hover:scale-105 transition 
            text-sm w-1/4 min-w-[3rem]"
              >
                로비화면으로
              </button>
            </form>
          </div>
          {/* // */}
          <div className="modal-action">
            <form method="dialog">
              <button className="btn btn-sm btn-circle btn-ghost absolute right-2 top-2">✕</button>
            </form>
          </div>
          {/* // */}
        </div>
      </dialog>
    </div>
  );
};

export default Rank;
