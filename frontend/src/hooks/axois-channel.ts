import axios from "axios";
import instance from "./axios-instance";
import useUserStore from "../stores/userStore";
const BASE_URL = process.env.REACT_APP_SERVER

 const ChannelApi= {
    // 채널 목록 조회
    getChannelList : async () => {
        try{
            const response = await instance.get(
                BASE_URL + `/channel/channelList`, 
            )
            return response.data
        }
        catch (error) {
            console.error(error)
            return Promise.reject(error)
        }
    },
}
export{ChannelApi}
