import axios from 'axios';
import instance from './axios-instance';
import useUserStore from '../stores/userStore';
const BASE_URL = process.env.REACT_APP_SERVER;

const UserChannelApi = {
  // 채널 입장
  enterChannel: async (channelCode: string) => {
    try {
      const response = await instance.post(BASE_URL + `/user-channel/${channelCode}`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 채널 퇴장
  exitChannel: async (userId: bigint, code: string) => {
    try {
      const response = await instance.delete(BASE_URL + `/user-channel/${userId}/${code}`);
      return response.data;
    } catch (error) {
      console.log('채널퇴장 API 에러 발생');
      console.log('-------------------------');

      console.log('채널 퇴장 요청 URL : ');
      console.log(BASE_URL + `/user-channel/${userId}/${code}`);

      console.error(error);
      return Promise.reject(error);
    }
  },
  // 채널 유저 리스트 반환
  getChannelUserList: async (channelCode: string) => {
    try {
      const response = await instance.get(
        BASE_URL + `/user-channel/userChannelList/${channelCode}`
      );
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
};
export { UserChannelApi };
