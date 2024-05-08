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
  const obj = {
    status: 'success',
    message: 'The request has been processed successfully.',
    data: {
      teamRanking: [
        {
          userId: 16,
          userName: '조싸피',
          nickName: 'ssafy6',
          picture: 'https://url5',
          statusMessage: '하이',
          totalScore: 21000,
          teamScore: 11000,
          soloScore: 10000,
          created_date: '2024-05-07T16:05:52.998842',
          updated_date: '2024-05-07T16:05:52.998878',
        },
        {
          userId: 1,
          userName: 'admin',
          nickName: 'admin',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-04-25T13:22:03.662704',
          updated_date: '2024-04-25T13:22:03.662704',
        },
        {
          userId: 2,
          userName: 'ssafy',
          nickName: 'ssafy',
          picture: 'https://s3url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-04-25T13:46:49.813837',
          updated_date: '2024-04-25T13:46:49.813837',
        },
        {
          userId: 11,
          userName: '김싸피',
          nickName: 'ssafy1',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:04:57.638229',
          updated_date: '2024-05-07T16:04:57.638296',
        },
        {
          userId: 12,
          userName: '박싸피',
          nickName: 'ssafy2',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:23.057204',
          updated_date: '2024-05-07T16:05:23.057236',
        },
        {
          userId: 13,
          userName: '최싸피',
          nickName: 'ssafy3',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:29.524129',
          updated_date: '2024-05-07T16:05:29.524173',
        },
        {
          userId: 14,
          userName: '홍싸피',
          nickName: 'ssafy4',
          picture: 'https://url2',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:38.881038',
          updated_date: '2024-05-07T16:05:38.881128',
        },
        {
          userId: 15,
          userName: '정싸피',
          nickName: 'ssafy5',
          picture: 'https://url4',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:45.838786',
          updated_date: '2024-05-07T16:05:45.838846',
        },
      ],
      soloRanking: [
        {
          userId: 16,
          userName: '조싸피',
          nickName: 'ssafy6',
          picture: 'https://url5',
          statusMessage: '안녕',
          totalScore: 21000,
          teamScore: 11000,
          soloScore: 10000,
          created_date: '2024-05-07T16:05:52.998842',
          updated_date: '2024-05-07T16:05:52.998878',
        },
        {
          userId: 1,
          userName: 'admin',
          nickName: 'admin',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-04-25T13:22:03.662704',
          updated_date: '2024-04-25T13:22:03.662704',
        },
        {
          userId: 2,
          userName: 'ssafy',
          nickName: 'ssafy',
          picture: 'https://s3url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-04-25T13:46:49.813837',
          updated_date: '2024-04-25T13:46:49.813837',
        },
        {
          userId: 11,
          userName: '김싸피',
          nickName: 'ssafy1',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:04:57.638229',
          updated_date: '2024-05-07T16:04:57.638296',
        },
        {
          userId: 12,
          userName: '박싸피',
          nickName: 'ssafy2',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:23.057204',
          updated_date: '2024-05-07T16:05:23.057236',
        },
        {
          userId: 13,
          userName: '최싸피',
          nickName: 'ssafy3',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:29.524129',
          updated_date: '2024-05-07T16:05:29.524173',
        },
        {
          userId: 14,
          userName: '홍싸피',
          nickName: 'ssafy4',
          picture: 'https://url2',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:38.881038',
          updated_date: '2024-05-07T16:05:38.881128',
        },
        {
          userId: 15,
          userName: '정싸피',
          nickName: 'ssafy5',
          picture: 'https://url4',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:45.838786',
          updated_date: '2024-05-07T16:05:45.838846',
        },
      ],
      totalRanking: [
        {
          userId: 16,
          userName: '조싸피',
          nickName: 'ssafy6',
          picture: 'https://url5',
          statusMessage: 'ㅋㅋㅋ',
          totalScore: 21000,
          teamScore: 11000,
          soloScore: 10000,
          created_date: '2024-05-07T16:05:52.998842',
          updated_date: '2024-05-07T16:05:52.998878',
        },
        {
          userId: 1,
          userName: 'admin',
          nickName: 'admin',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-04-25T13:22:03.662704',
          updated_date: '2024-04-25T13:22:03.662704',
        },
        {
          userId: 2,
          userName: 'ssafy',
          nickName: 'ssafy',
          picture: 'https://s3url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-04-25T13:46:49.813837',
          updated_date: '2024-04-25T13:46:49.813837',
        },
        {
          userId: 11,
          userName: '김싸피',
          nickName: 'ssafy1',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:04:57.638229',
          updated_date: '2024-05-07T16:04:57.638296',
        },
        {
          userId: 12,
          userName: '박싸피',
          nickName: 'ssafy2',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:23.057204',
          updated_date: '2024-05-07T16:05:23.057236',
        },
        {
          userId: 13,
          userName: '최싸피',
          nickName: 'ssafy3',
          picture: 'https://url1',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:29.524129',
          updated_date: '2024-05-07T16:05:29.524173',
        },
        {
          userId: 14,
          userName: '홍싸피',
          nickName: 'ssafy4',
          picture: 'https://url2',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:38.881038',
          updated_date: '2024-05-07T16:05:38.881128',
        },
        {
          userId: 15,
          userName: '정싸피',
          nickName: 'ssafy5',
          picture: 'https://url4',
          statusMessage: null,
          totalScore: 0,
          teamScore: 0,
          soloScore: 0,
          created_date: '2024-05-07T16:05:45.838786',
          updated_date: '2024-05-07T16:05:45.838846',
        },
      ],
    },
  };
  return (
    <div className=" w-3/4 h-3/4 bg-white opacity-80">
      <div>이건 랭킹임 ㅋㅋㅋ</div>
      <div className="flex flex-row items-center">
        <RankingList rankingTitle={'통합 랭킹'} users={totalRankingArray} />
        <RankingList rankingTitle={'개인전 랭킹'} users={soloRankingArray} />
        <RankingList rankingTitle={'팀 랭킹'} users={teamRankingArray} />
        {/* <RankingList rankingTitle={'팀 랭킹'} users={obj.data.teamRanking} /> */}
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
