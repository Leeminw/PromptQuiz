import axios, {AxiosInstance} from "axios";
const BASE_URL = 'http://localhost:8080/api/v1'

const instance = axios.create({
    baseURL : BASE_URL,
    timeout : 5000,
});

const capiclient = axios.create({
    baseURL : BASE_URL,
    timeout :5000,
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
        if(error.response.status === 401){
            try {
                const refreshToken = localStorage.getItem("refreshToken");
                const response = await capiclient.get("/user", {
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
                localStorage.removeItem("accessToken")
                localStorage.removeItem("refreshToken")
                return Promise.reject(_error);
            }
        }
        else{
            localStorage.removeItem("accessToken")
            localStorage.removeItem("refreshToken")
            return Promise.reject(error);

        }
    }
)

export default instance