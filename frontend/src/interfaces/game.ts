interface Game {
    id: bigint,
    channelId: number,
    type: number,
    style: number,
    code: string,
    title: string,
    password: string|null,
    status: boolean,
    isTeam: boolean,
    curRound: number,
    rounds: number,
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
    gameId: bigint,
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
    gameId: bigint,
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
    data : GameChatRecieve
}

interface GameChatRecieve {
    userId: bigint|null,
    nickname: string|null,
    uuid: string|null,
    gameId: bigint|null,
    round: number|null,
    content: string|null,
    createdDate: string|null,
    time: number|null
}
interface GameTimer {
    time : number,
    round : number
}
interface GameReady{
    gameId: bigint,
    uuid : string
}

