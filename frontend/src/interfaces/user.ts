interface LoginForm {
    userName:string,
    password:string
}
interface JoinForm {
    userName:string,
    password:string,
    nickName:string,
    picture:string
}
interface User {
    userId: number,
    userName: string,
    nickName: string,
    picture: string,
    statusMessage: string,
    totalScore: number,
    teamScore: number,
    soloScore: number,
    created_date: string,
    updated_date: string
}
interface ChannelUser {
    userId: number,
    userName: string,
    nickName: string,
    picture: string,
    statusMessage: string,
    totalScore: number,
    teamScore: number,
    soloScore: number,
    created_date: string,
    updated_date: string
}
interface UserState {
    user: User | null;
    setUser: (user: User) => void;
    clearUser: () => void;
}