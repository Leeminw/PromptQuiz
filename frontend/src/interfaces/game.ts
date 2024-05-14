interface Game {
    channelCode: string,
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
    created_date: string,
    gameCode: string,
    gameUserCode: string,
    isHost: boolean,
    nickName: string,
    picture: string,
    score: number,
    soloScore: number,
    statusMessage: string|null,
    team: string,
    userId: bigint,
    userName: string,

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
    data : GameChatRecieve|GameTimer|GameReady|GameLeave|GameStatus|bigint|SimilarityQuiz|WrongSignal
}
interface WrongSignal { 
    answer: number,
    userId: bigint
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
    isCorrect : boolean
}
interface ReiceveQuiz {
    quizType:number,
    data : SelectQuiz[]|SimilarityQuiz
}
interface SelectQuiz {
    code: string,
    gameCode: string,
    quizId: number,
    type: number,
    number: number,
    round: number,
    isAnswer: boolean,
    url: string,
    style: string,
    groupCode: string,
    engVerb: string,
    engObject: string,
    engSubject: string,
    engSentence: string,
    engSubAdjective: string,
    engObjAdjective: string,
    korVerb: string,
    korObject: string,
    korSubject: string,
    korSentence: string,
    korSubAdjective: string,
    korObjAdjective: string
}

interface SimilarityQuiz{
    answerWord : Word
    playerSimilarity: PlayerSimilarity
    url:string

}
interface PlayerSimilarity{
    [key:string] : Similarity[]
}

interface Word {
    [key: string]: string;
}

interface Similarity{
    value:string,
    rate :number
}

interface QuizCorrectUser {
    nickname:string,
    
}