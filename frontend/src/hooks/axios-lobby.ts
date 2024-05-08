import axios from 'axios';
import instance from './axios-instance';
import useUserStore from '../stores/userStore';
const BASE_URL = process.env.REACT_APP_SERVER;

const LobbyApi = {
  // 게임방 목록 조회
  getGameList: async (channelId: number) => {
    try {
      const response = await instance.get(BASE_URL + `/game/gameList/${channelId}`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 현재 채널 접속 인원 조회
  getUserList: async (channelId: number) => {
    try {
      const response = await instance.get(
        BASE_URL + `/user-channel/getUserChannelList/${channelId}`
      );
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 게임방 생성
  createRoom: async (room: Room) => {
    try {
      const response = await instance.post(BASE_URL + `/game`, room);
      alert('게임방이 정상적으로 생성되었습니다.');
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 게임방 입장
  enterGame: async (gameId: number) => {
    try {
      const response = await instance.post(BASE_URL + `/game-user/enterGame/${gameId}`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
};
export { LobbyApi };
