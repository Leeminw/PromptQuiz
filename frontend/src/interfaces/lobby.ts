interface Room {
  userId: number;
  channelId: number;
  type: number;
  style: number; // number -> string
  title: string;
  password: null | string;
  status: boolean;
  isTeam: boolean;
  curRound: number;
  maxRound: number;
  curPlayers: number;
  maxPlayers: number;
}
interface RoomProps {
  id: number;
  channelId: number;
  type: number;
  style: number;
  code: string;
  title: string;
  password: string;
  status: boolean;
  isTeam: boolean;
  curRound: number;
  rounds: number;
  curPlayers: number;
  maxPlayers: number;
}
interface CurrentUser {
  userId: number;
  userName: string;
  nickName: string;
  picture: string;
  statusMessage: string;
  totalScore: number;
  teamScore: number;
  soloScore: number;
  created_date: string;
  updated_date: string;
}
