interface Game {
    id: number,
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
interface GameRoomSettingProps {
    teammode: boolean;
    gamemode: string;
    round: number;
  }
interface GameChat{
    userId: number,
    nickname: string,
    uuid: string,
    gameId: number,
    round: number,
    content: string,
}

interface GameEnter{
    userId:number,
    uuid: string,
    nickname:string
}

interface GameChatRecieve {
    userId: number,
    nickname: string,
    uuid: string,
    gameId: number,
    round: number,
    content: string,
    createdDate: string
}
interface RecieveData {
    tag: string,
    data : GameChatRecieve
}
