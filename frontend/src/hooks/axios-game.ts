import instance from "./axios-instance";

const GameApi = {
    getGame: async (roomId:number) => {
        try{
            const response = await instance.get(`game/gameInfo/${roomId}`)
            return response.data;
        }
        catch(error) {
            return Promise.reject(error)
        }

    }
}


export default GameApi