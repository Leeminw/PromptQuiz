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

interface GameChat{
    userId: number,
    nickName: string,
    uuid: string,
    gameId: number,
    round: number,
    content: string,
}