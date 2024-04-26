interface Room{
    id: number,
    channelId: number,
    type: number,
    style: number,
    code: string,
    title: string,
    password: null | string,
    status: boolean,
    isTeam: boolean,
    curRound: number,
    rounds: number,
    curPlayers: number,
    maxPlayers: number
}