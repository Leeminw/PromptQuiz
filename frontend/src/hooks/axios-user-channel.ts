import axios from 'axios';
import instance from './axios-instance';
import useUserStore from '../stores/userStore';
const BASE_URL = process.env.REACT_APP_SERVER;

const UserChannelApi = {
  // 채널 입장
  enterChannel: async (channelId: number) => {
    try {
      const response = await instance.post(BASE_URL + `/user-channel/${channelId}`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 채널 퇴장
  exitChannel: async () => {
    try {
      const response = await instance.delete(BASE_URL + `/user-channel`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 채널 유저 리스트 반환
  getChannelUserList: async (channelId: number) => {
    try {
      const response = await instance.get(BASE_URL + `/user-channel/userChannelList/${channelId}`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
};
export { UserChannelApi };
