interface Room {
  userId: bigint;
  channelCode: string;
  type: number;
  style: string; // number -> string
  title: string;
  password: null | string;
  status: boolean;
  isTeam: boolean;
  curRound: number;
  rounds: number;
  curPlayers: number;
  maxPlayers: number;
}
interface RoomProps {
  id: number;
  channelCode: string;
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
  statusMessage: string | null;
  totalScore: number;
  teamScore: number;
  soloScore: number;
  created_date: string;
  updated_date: string;
}
interface CurrentUserProps {
  userId: number;
  userName: string;
  nickName: string;
  picture: string;
  statusMessage: string | null;
  totalScore: number;
  teamScore: number;
  soloScore: number;
  created_date: string;
  updated_date: string;
}
