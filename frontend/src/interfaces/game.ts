interface Game {
    channelCode: number,
    type: number,
    style: number,
    code: string,
    title: string,
    password: string|null,
    status: boolean,
    isTeam: boolean,
    timeLimit: number,
    curRounds: number,
    maxRounds: number,
    curPlayers: number,
    maxPlayers: number
}
interface GameUser {
    userId: bigint,
    userName: string,
    nickName: string,
    picture: string,
    statusMessage: string|null,
    totalScore: number,
    teamScore: number,
    soloScore: number,
    created_date: string,
    updated_date: string,
    gameUserId: bigint,
    gameCode: string,
    isHost: boolean,
    isReady: boolean,
    score: number,
    team: string,
    ranking: number
}
interface GameRoomSettingProps {
    teammode: boolean;
    gamemode: string;
    round: number;
  }
interface GameChat{
    userId: bigint,
    nickname: string,
    uuid: string,
    gameCode: string,
    round: number,
    content: string,
}


interface GameEnter{
    userId:bigint,
    uuid: string,
    nickname:string
}
interface GameLeave {
    userId:bigint,
    uuid : string,
    nickname:string
}

interface RecieveData {
    tag: string,
    data : GameChatRecieve|GameTimer|GameReady|GameLeave|GameStatus|bigint
}

interface GameChatRecieve {
    userId: bigint|null,
    nickname: string|null,
    uuid: string|null,
    gameCode: string|null,
    round: number|null,
    content: string|null,
    createdDate: string|null,
}

interface GameTimer {
    time : number,
    round : number,
    state : string,
}
interface GameReady{
    gameCode: string,
    uuid : string
}
interface GameStatus {
    type : string,
    content: RoundInfo
}

interface RoundInfo {
    round : number,
    roundList : RoundUser[]|null
}

interface RoundUser {
    userId : bigint,
    score : number,
    correct : boolean
}

