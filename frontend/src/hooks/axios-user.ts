import axios from "axios";

const BASE_URL = "http://localhost:8080/api/v1"

const UserApi= {
    login : async (loginForm:LoginForm) => {
        try{
            const response = await axios.post(
                BASE_URL + "/user/login", 
                loginForm
            )
            return response.data
        }
        catch (error) {
            console.error(error)
            return Promise.reject(error)
        }
    },
    isExistUserName : async(userName:string) => {
        try{
            const response = await axios.get(
                BASE_URL + `/user/exist?userName=${userName}`
            )

            return response.data
        }
        catch(error){
            console.error(error)
            return Promise.reject(error)
        }
    },
    join : async (joinForm:JoinForm) => {
        try {
            const response = await axios.post(
                BASE_URL + `/user/create`,
                joinForm
            )
            return response.data
        }
        catch(error) {
            console.error(error)
            return Promise.reject(error)
        }
    }
}

export{UserApi}
