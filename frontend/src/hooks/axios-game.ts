import instance from './axios-instance';

const GameApi = {
  getGame: async (roomId: string) => {
    try {
      const response = await instance.get(`game/${roomId}`);
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  },
  getUserList: async (roomId: string) => {
    try {
      const response = await instance.get(`game-user/details?gameCode=${roomId}`);
      console.log('GetUserList', response.data);
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  },
  // getGameDetail : async ()
};

export default GameApi;
