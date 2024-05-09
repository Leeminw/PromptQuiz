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
  enterGame: async (gameCode: string) => {
    try {
      const response = await instance.post(`/game/enterGame/${gameCode}`);
      return response.data;
    } catch (error) {
      return Promise.reject(error);
    }
  },
  getRoundGame: async (gameCode:string) =>{
    try{
      const response = await instance.get(`/round/quiz/${gameCode}`)
      return response.data;
    }catch(error){
      return Promise.reject(error);
    }
  }
};

export default GameApi;
