interface Room {
  code: string|null,
  channelCode: string|null,
  password: string|null,
  title: string|null,
  mode: number|null,
  style: string|null,
  isTeam: boolean|null,
  isPrivate: boolean|null,
  isStarted: boolean|null,
  timeLimit: number|null,
  curRounds: number|null,
  maxRounds: number|null,
  curPlayers: number|null,
  maxPlayers: number|null
}
interface CreateRoom {
  channelCode: string|null,
  title: string|null,
  style: string|null,
  mode: number|null,
  password: string|null,
  isTeam: boolean|null,
  isPrivate: boolean|null,
  timeLimit: number|null,
  maxRounds: number|null,
  maxPlayers: number|null
}
interface RoomProps {
  code: string|null;
  channelCode: string|null;
  password: string|null;
  title: string|null;
  mode: number|null;
  style: number|null;
  isTeam: boolean|null;
  isPrivate:boolean|null;
  isStarted: boolean|null;
  timeLimit: number|null;
  curRounds: number|null;
  maxRounds: number|null;
  curPlayers: number|null;
  maxPlayers: number|null;
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
