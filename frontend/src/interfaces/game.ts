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

interface GameChatRecieve {
    userId: bigint|null,
    nickname: string|null,
    uuid: string|null,
    gameId: bigint|null,
    round: number|null,
    content: string|null,
    createdDate: string|null,
    time : number|null,
}
interface RecieveData {
    tag: string,
    data : GameChatRecieve
}

interface GameReady{
    gameId: bigint,
    uuid : string
}

interface GameTime {
    time : number,
    round : number
}