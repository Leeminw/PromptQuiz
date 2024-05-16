import axios, {AxiosInstance} from "axios";
import useUserStore from "../stores/userStore";

const BASE_URL = process.env.REACT_APP_SERVER;

const instance = axios.create({
    baseURL : BASE_URL,
    // timeout : 5000,
});

const apiClient = axios.create({
    baseURL : BASE_URL,
    // timeout :5000,
})

instance.interceptors.request.use(
    config => {
        config.headers.Authorization = `Bearer ${localStorage.getItem("accessToken")}`
        return config;
    },
    error => {
        return Promise.reject(error)
    }
)

instance.interceptors.response.use(
    async (response) => {
        // console.log("response : ", response);
        return response;
    },
    async error => { 
        const originalRequest = error.config;
        // console.log("send refresh")
        if(error.response?.status === 401){
            try {
                const refreshToken = localStorage.getItem("refreshToken");
                const response = await apiClient.get("/user", {
                    headers:{
                        Authorization:`Bearer ${refreshToken}`
                    }
                })
                const accessToken = response.data?.accessToken;
                localStorage.setItem("accessToken", accessToken);
                originalRequest.headers.Authorization = `Bearer ${accessToken}`
                return axios(originalRequest)
            }
            catch (_error) {
                // 로그아웃 
                window.location.href="/"
                // localStorage.removeItem("refreshToken")
                return Promise.reject(_error);
            }
        }
        else if (error.response?.status >= 500 && error.response?.status < 600){
            window.location.href="/"
        }
        else{
            
            return Promise.reject(error);

        }
    }
)

export default instance