import axios from 'axios';
import instance from './axios-instance';
import useUserStore from '../stores/userStore';
const BASE_URL = process.env.REACT_APP_SERVER;
const RankApi = {
  // 랭킹 리스트 조회
  getRankingList: async () => {
    try {
      const response = await instance.get(BASE_URL + `/user/rank`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
};
export { RankApi };
