import axios from "axios";

const BASE_URL = "http://localhost:8080/api/v1"

const UserApi= {
    login : async (loginForm:LoginForm) => {
        console.log("try this")
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
    }
}

export{UserApi}
