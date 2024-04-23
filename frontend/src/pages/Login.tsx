import React from 'react';
import { useState } from 'react';
import { UserApi } from './hook/axios-user';

const Login = () => {

    const [userName, setUserName] = useState<string>("")
    const [password, setPassword] = useState<string>("")

    const userNameHandler = (event:React.ChangeEvent<HTMLInputElement>) =>{
        setUserName(event.target.value);
    }

    const passwordHandler = (event:React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    }

    const loginUser = async () =>{ 
        const loginForm:LoginForm = {
            userName,
            password
        }
        console.log(loginForm)
        const {data} = await UserApi.login(loginForm)
        
        localStorage.setItem("accessToken", data.accessToken)
        
        
    }

    return (
        <div className="border ">
            <p>로그인페이지</p>
            <input type="text" placeholder='id' value={userName} onChange={userNameHandler} />
            <br />
            <input type="password" placeholder='password' value={password} onChange={passwordHandler}/>
            <br />
            <button onClick={loginUser}> 로그인</button>
            <br />
            <button> 회원가입</button>
        </div>
    )
}

export default Login;