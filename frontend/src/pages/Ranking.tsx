import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import RankingList from '../components/ranking/RankingList';
import { RankApi } from '../hooks/axios-rank';
const Ranking = () => {
  const [rankingArray, setRankingArray] = useState<Ranking[]>([]);
  const [teamRankingArray, setTeamRankingArray] = useState<Ranking[]>([]);
  const [soloRankingArray, setSoloRankingArray] = useState<Ranking[]>([]);
  const [totalRankingArray, setTotalRankingArray] = useState<Ranking[]>([]);
  useEffect(() => {
    // 랭킹 리스트 가져오기
    const response = RankApi.getRankingList()
      .then((response) => {
        setTeamRankingArray(response.data.teamRanking);
        setSoloRankingArray(response.data.soloRanking);
        setTotalRankingArray(response.data.totalRanking);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  return (
    <div className=" w-3/4 h-3/4 bg-white opacity-80">
      <div>랭킹화면</div>
      <div className="flex flex-row items-center">
        <RankingList rankingTitle={'통합 랭킹'} users={totalRankingArray} />
        <RankingList rankingTitle={'개인전 랭킹'} users={soloRankingArray} />
        <RankingList rankingTitle={'팀 랭킹'} users={teamRankingArray} />
      </div>
      <Link to="/channel">
        <button
          className="bg-customGreen text-white hover:brightness-125 hover:scale-105 transition 
            text-sm w-1/4 min-w-[3rem]"
        >
          홈으로
        </button>
      </Link>
    </div>
  );
};

export default Ranking;
