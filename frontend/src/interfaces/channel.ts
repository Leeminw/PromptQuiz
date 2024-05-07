interface Channel {
    id: number,
    code: string,
    name: string,
    curPlayers: number,
    maxPlayers: number
}
interface ChannelChat {
    nickname: string,
    uuid: string,
    content: string,
}

interface RecieveChannelChat {
    nickname: string,
    uuid: string,
    content: string,
    createdDate: string,    
}