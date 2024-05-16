import axios from 'axios';
import instance from './axios-instance';
import useUserStore from '../stores/userStore';
// const BASE_URL = process.env.REACT_APP_SERVER;

const LobbyApi = {
  // 게임방 목록 조회
  getGameList: async (channelCode: string) => {
    try {
      const response = await instance.get(`/game/gameList/${channelCode}`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 현재 채널 접속 인원 조회
  getUserList: async (channelCode: string) => {
    try {
      const response = await instance.get(
        `/user-channel/getUserChannelList/${channelCode}`
      );
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 게임방 생성
  createRoom: async (room: CreateRoom) => {
    try {
      const response = await instance.post(`/game`, room);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  // 게임방 입장

  enterGame: async (gameCode: string) => {
    try {
      const response = await instance.post(`/game/enterGame/${gameCode}`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
  
  getChannelInfo : async (uuid : string) => { 
    try{
      const response = await instance.get(`/channel/code/${uuid}`);
      return response.data;

    }catch (error) { 
      return Promise.reject(error);
    }
  },
  enterLobby: async (channelCode: string) => {
    try {
      const response = await instance.post(`/user-channel/code/${channelCode}`);
      return response.data;
    } catch (error) {
      console.error(error);
      return Promise.reject(error);
    }
  },
};
export { LobbyApi };
