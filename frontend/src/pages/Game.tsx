import React, { useEffect, useRef, useState } from 'react';
import { IoSettings } from 'react-icons/io5';
import { IoLogOut } from 'react-icons/io5';
import { FaUserPlus } from 'react-icons/fa';
import { IoSend } from 'react-icons/io5';
import GamePlayer from '../components/game/Player';
import SelectionGame from '../components/game/SelectionGame';
import GameRoomSetting from '../components/game/GameRoomSetting';
import GameApi from '../hooks/axios-game';
import { Navigate, useLoaderData, useNavigate } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import SockJS from 'sockjs-client';
import { Client, Message, IMessage } from '@stomp/stompjs';
import { useWebSocketStore } from '../stores/socketStore';
import useUserStore from '../stores/userStore';
import instance from '../hooks/axios-instance';
import { LobbyApi } from '../hooks/axios-lobby';
import badwordsFiltering from '../hooks/badwords-filtering';
import InviteUser from '../components/game/InviteUser';
import SequenceGame from '../components/game/SequenceGame';
import CustomButton from '../components/ui/CustomButton';
import SubjectiveGame from '../components/game/SubjectiveGame';
import QuizCorrect from '../components/game/QuizCorrect';

const GamePage = () => {
  const { roomCode } = useParams();
  const { user } = useUserStore();
  const navigate = useNavigate();
  const chatBtn = useRef(null);
  const chatInput = useRef(null);
  const chattingBox = useRef(null);
  const [gamestartui, setGamestartui] = useState(false);
  const [gamestart, setGamestart] = useState(false);
  const [earthquake, setEarthquake] = useState(false);
  const [game, setGame] = useState<Game | null>(null);
  const [chat, setChat] = useState<GameChatRecieve[]>([]);
  const [time, setTime] = useState<number>(0);
  const [maxRound, setMaxRound] = useState<number>(0);
  const [round, setRound] = useState<number>(0);
  const { connectWebSocket, disconnectWebSocket, publish } = useWebSocketStore();
  const [chatOpen, setChatOpen] = useState(false);
  const [gameUserList, setGameUserList] = useState<GameUser[]>([]);
  const [roundState, setRoundState] = useState<string>('wait');
  const [result, setResult] = useState<RoundUser[]>([]);
  const [isQuiz, setIsQuiz] = useState<boolean>(false);
  const [messageMap, setMessageMap] = useState<Map<bigint, GameChatRecieve>>(new Map());
  const [channelInfo, setChannelInfo] = useState<Channel | null>();
  const [imageUrl, setImageUrl] = useState<string>('');
  const [multipleChoice, setMultipleChoice] = useState<SelectQuiz[] | null>(null);
  const [gameUser, setGameUser] = useState<GameUser | null>(null);
  const [quizCorrectUser, setQuizCorrectUser] = useState<CorrectUser | null>(null);
  const [quizType, setQuizType] = useState<number>(0);
  const [choosedButton, setChoosedButton] = useState<boolean[]>([false, false, false, false]);
  const [answerWord, setAnswerWord] = useState<Word | null>(null);
  const [playerSimilarity, setPlayerSimilarity] = useState<PlayerSimilarity | null>(null);
  const [roundResult, setRoundResult] = useState<RoundUser[]>([]);
  //문제 틀렸을때 틀린거 표기
  const [timeRatio, setTimeRatio] = useState<number>(0);
  const getGameData = async () => {
    try {
      const response = await GameApi.getGame(roomCode);
      console.log('first response', response.data);
      const responseGame: Game = response.data;
      const userResponse = await GameApi.getUserList(roomCode);
      const gameUserList: GameUser[] = userResponse.data;
      console.log('onmount', gameUserList);
      setGame(responseGame);
      setGameUserList(gameUserList);
      getChannelInfo(responseGame?.channelCode);

      const foundUser: GameUser = gameUserList.find((gUser) => {
        return gUser.userId == user.userId;
      });

      console.log('foundUser', foundUser);

      setGameUser(foundUser);
    } catch (error) {
      console.error(error);
      // navigate(-1);
    }
    return () => {
      disconnectWebSocket();
    };
    // setMaxRound(responseGame.maxRounds);
    // enterGame();
  };
  useEffect(() => {
    const updatedUserMap = new Map<bigint, GameChatRecieve>();
    gameUserList.forEach((user) => {
      updatedUserMap.set(user.userId, null);
    });
    setMessageMap(updatedUserMap);
  }, [gameUserList]);

  const getChannelInfo = async (code: string) => {
    try {
      const response = await LobbyApi.getChannelInfo(code);
      // console.log('channel', response);
      setChannelInfo(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  //  문제를 받았는지 ?
  // false, , timer로받았을때>> 현재게임상태 ' '
  useEffect(() => {
    enterGameRoom();
    getGameData();
    // 채팅 입력 바깥 클릭 시 채팅창 닫기
    // 클릭 & 키다운 이벤트 추가
    document.addEventListener('click', handleOutsideClick);
    document.addEventListener('keydown', handleChatKey);
    return () => {
      document.removeEventListener('click', handleOutsideClick);
    };
  }, []);

  useEffect(() => {
    const updatedUserMap = new Map<bigint, GameChatRecieve>();
    gameUserList.forEach((user) => {
      updatedUserMap.set(user.userId, null);
    });
    setMessageMap(updatedUserMap);
  }, [gameUserList]);

  useEffect(() => {
    // 게임 로드하면 구독하기
    connectWebSocket(`/ws/sub/game?uuid=${game?.code}`, recieveChat, enterGame, user.userId);
    return () => {
      disconnectWebSocket();
    };
  }, [game]);
  const getGameDetail = async (gameCode: string) => {
    try {
      const userResponse = await GameApi.getUserList(roomCode);
      setGameUserList(userResponse.data);
      const response = await GameApi.getRoundGame(gameCode);
      const quiz: ReiceveQuiz = response.data;
      setQuizType(quiz.quizType);
      // 객관식 퀴즈
      if (quiz.quizType == 1) {
        const data: SelectQuiz[] = quiz.data as SelectQuiz[];
        // console.log(data);
        // 이미지 세팅
        data.forEach((element) => {
          console.log(element);
          if (element.isAnswer) {
            setImageUrl(element.url);
          }
        });
        // 보기 구성\
        const sortedData = data.sort((a: SelectQuiz, b: SelectQuiz) => a.number - b.number);
        setMultipleChoice(sortedData);
        // choosed button 초기화
        setChoosedButton([false, false, false, false]);
      } else if (quiz.quizType == 2) {
        // 순서 맞추기
      } else if (quiz.quizType == 4) {
        // 주관식 퀴즈
        const data: SimilarityQuiz = quiz.data as SimilarityQuiz;
        console.log('주관식 ', quiz.data);
        // 이미지 세팅
        setImageUrl(data.url);
        setAnswerWord(data.answerWord);
        setPlayerSimilarity(data.playerSimilarity);
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (isQuiz) {
      getGameDetail(game?.code);

      console.log('isQuiz updated:', isQuiz);
    } else {
      // 퀴즈 내리기
      setImageUrl('');
      setMultipleChoice(null);
      setAnswerWord(null);
      setPlayerSimilarity(null);
    }
  }, [isQuiz]);

  const enterGameRoom = async () => {
    try {
      const response = await GameApi.enterGame(roomCode);
      console.log('post game enter', response.data);
    } catch (error) {
      console.error(error);
      navigate(-1);
    }
  };

  const handleOutsideClick = (event: MouseEvent) => {
    const target = event.target as Node;
    if (target && !chatInput.current?.contains(target) && !chatBtn.current?.contains(target)) {
      chatFocusOut();
    }
  };

  // 채팅 입력 안하고 있을 때 Enter시 채팅창 열기
  const handleChatKey = (event: KeyboardEvent) => {
    const target = event.target as Node;
    if (event.key === 'Enter' && !chatInput.current?.contains(target) && !chatOpen) {
      chatFocus();
    }
  };

  // 채팅창 열기
  const chatFocus = () => {
    chatInput.current?.focus();
    setChatOpen(true);
  };

  // 채팅창 닫기
  const chatFocusOut = () => {
    chatInput.current?.blur();
    setChatOpen(false);
  };

  const recieveChat = (message: IMessage) => {
    if (message.body) {
      const body: RecieveData = JSON.parse(message.body);
      gameController(body);
    }
  };
  const gameController = async (recieve: RecieveData) => {
    console.log(recieve);
    if (recieve.tag === 'startGame') {
      console.log('game start!! ', recieve);
      handleGamestart();
    } else if (recieve.tag === 'chat') {
      const data: GameChatRecieve = recieve.data as GameChatRecieve;

      setChat((prevItems) => [...prevItems, data]);
      setMessageMap((prevMap) => {
        const updatedMap = new Map(prevMap);
        updatedMap.set(data.userId, data);
        return updatedMap;
      });
    } else if (recieve.tag === 'enter') {
      const userResponse = await GameApi.getUserList(roomCode);
      setGameUserList(userResponse.data);
    } else if (recieve.tag === 'leave') {
      const userResponse = await GameApi.getUserList(roomCode);
      setGameUserList(userResponse.data);
    } else if (recieve.tag === 'timer') {
      const data: GameTimer = recieve.data as GameTimer;
      setRoundState(data.state);
      setTime(data.time);
      setTimeRatio(Math.round((data.time / game.timeLimit) * 100));
      console.log('timeRatio', Math.round((data.time / game.timeLimit) * 100));
      setRound(data.round);
    } else if (recieve.tag === 'wrongSignal') {
      const data: WrongSignal = recieve.data as WrongSignal;
      if (data.userId === user.userId) {
        const updatedChoosedButton = [...choosedButton];
        console.log(choosedButton);
        updatedChoosedButton[data.answer - 1] = true;
        setChoosedButton(updatedChoosedButton);
        alert('난 틀렸어..');
      }
    } else if (recieve.tag === 'similarity') {
      console.log('유사도 갱신', recieve.data);
      const data: SimilarityQuiz = recieve.data as SimilarityQuiz;
      const sortedSimilarity: PlayerSimilarity = {};
      setAnswerWord(data.answerWord);
      for (const key in data.playerSimilarity) {
        const sorted = data.playerSimilarity[String(key)].sort(
          (a: Similarity, b: Similarity) => b.rate - a.rate
        );
        sortedSimilarity[key] = sorted;
      }
      setPlayerSimilarity(sortedSimilarity);
    } else if (recieve.tag === 'game') {
      const data: GameStatus = recieve.data as GameStatus;
      if (data.type === 'ready') {
        setIsQuiz(false);
      } else if (data.type === 'start') {
        setIsQuiz(true);
      } else if (data.type === 'end') {
        setIsQuiz(false);

        const roundInfo = data.content;
        // 라운드 결과
        // {
        //     gameCode, isCorrect, score, userId
        // }
        const result = roundInfo.roundList;
        const userResponse = await GameApi.getUserList(roomCode);
        const updateUserList = userResponse.data;
        for (const user of result) {
          if (user.isCorrect) {
            const correctUser = updateUserList.find(
              (item: GameUser) => item.userId === user.userId
            );
            setQuizCorrectUser({
              nickname: correctUser.nickName,
              round: round,
            });
          }
        }
        // todo 중간 결과 페이지 보여줘야됨.

        setRoundResult(result);

        setGameUserList(userResponse.data);
        // setGameUserList(updateUserList);
      } else if (data.type === 'result') {
        setRoundState('result');
        const roundInfo = data.content;
        const roundResult = roundInfo.roundList;
        setResult(roundResult);
        setIsStart(false);
      }
    }
  };

  const enterGame = () => {
    const gameEnter: GameEnter = {
      userId: user.userId,
      uuid: game?.code,
      nickname: user.nickName,
    };
    const destination = '/ws/pub/game/enter';
    publish(destination, gameEnter);
  };

  const publishChat = () => {
    let chatfilter = badwordsFiltering(chatInput.current?.value);
    const destination = '/ws/pub/game/chat/send';
    const gameChat: GameChat = {
      userId: user.userId,
      nickname: user.nickName,
      uuid: game.code,
      gameCode: game.code,
      round: round,
      content: chatfilter,
    };
    publish(destination, gameChat);
    chatInput.current.value = '';
  };
  const publishAnswer = (answer: number) => {
    // console.log('recieve', answer);
    const destination = '/ws/pub/game/chat/send';
    const gameChat: GameChat = {
      userId: user.userId,
      nickname: user.nickName,
      uuid: game.code,
      gameCode: game.code,
      round: round,
      content: String(answer),
    };
    publish(destination, gameChat);
  };
  const publishStart = async () => {
    console.log('publish start', gameUser);
    if (!gameUser.isHost) {
      return;
    }
    // 모두 레디가 되있는지?
    const destination = '/ws/pub/api/v1/game/start';
    const data = {
      gameCode: game.code,
    };
    publish(destination, data);
    // 소켓으로 start 전송
    // 받으면 >> response 보내느걸로 하면안되나..?
  };

  // 버튼 제어
  const [btnCurrentActivate, setBtnCurrentActivate] = useState<boolean>(false);
  const [isStart, setIsStart] = useState<boolean>(false);
  const activateBtnFunc = async () => {
    setBtnCurrentActivate(true);
    await setTimeout(() => {
      setBtnCurrentActivate(false);
    }, 800);
  };
  const postStart = async () => {
    try {
      setIsStart(true);
      if (gameUser?.isHost) {
        const response = await GameApi.startGame(game.code);
      }
    } catch (error) {
      console.error(error);
    }
  };
  // 게임 시작 이벤트
  const handleGamestart = () => {
    setGamestartui(true);
    setGamestart(true);
    setTimeout(() => {
      setEarthquake(true);
      setTimeout(() => {
        setEarthquake(false);
        setTimeout(() => {
          setGamestartui(false);
          postStart();
          //
        }, 1000);
      }, 600);
    }, 500);
  };

  // 초대코드 발송
  const inviteUser = () => {
    alert('초대코드 전송하기!!');
  };

  // 비율 표시
  const progressStyle = {
    transform: `translateX(-${timeRatio}%)`,
    transition: 'transform 1s ease-in-out', // transition 효과 추가
  };
  return (
    <div
      className={`w-[70rem] h-[37rem] min-w-[40rem] min-h-[37rem] max-w-[80vw] z-10 
      rounded-3xl drop-shadow-lg flex flex-col items-center justify-center 
      ${earthquake ? 'animate-earthquake' : ''}`}
    >
      <div
        className={`absolute bg-no-repeat bg-contain bg-center bg-[url(/public/ui/gamestart.png)] 
        w-full h-full flex items-center justify-center text-white text-6xl z-20 font-extrabold 
        transition ease-in duration-500 ${gamestartui ? 'block translate-y-0' : 'translate-y-[-100vh]'}`}
      ></div>
      {/* 상단 : 제목, 버튼 */}
      <div className="w-full h-10 grid grid-cols-5 gap-3 mb-2">
        {/* 채널 */}
        <label className="h-full flex items-center font-extrabold bg-white/80 border-custom-mint">
          <p className="text-center w-full text-nowrap text-mint">{channelInfo?.name}</p>
        </label>
        {/* 제목 */}
        <div className="w-full h-full flex col-span-3 px-4">
          <label className="flex items-center justify-center w-full border-custom-mint bg-white/80 text-sm">
            <p className="text-center w-full text-nowrap line-clamp-1 text-mint font-bold">
              {game?.title}
            </p>
          </label>
        </div>
        {/* 버튼 */}
        <div className="flex gap-3">
          {/* 초대코드 모달 창 추가 */}
          <InviteUser />
          {/* 기존 버튼 UI InviteUser에 적용필요 */}
          <CustomButton
            btnCurrentActivate={btnCurrentActivate}
            className="w-1/2 min-w-12 btn-mint-border-white gap-1 px-2 max-xl:justify-center"
            onClick={() => {
              activateBtnFunc();
              setTimeout(() => {
                (document.getElementById('invite_modal') as HTMLDialogElement).showModal();
              }, 500);
            }}
          >
            <FaUserPlus className="min-w-5 min-h-5 mb-0.5" />
            <p
              className="text-center w-full text-nowrap text-sm overflow-hidden 
              text-ellipsis xl:flex max-xl:hidden"
            >
              초대하기
            </p>
          </CustomButton>
          <CustomButton
            btnCurrentActivate={btnCurrentActivate}
            className="w-1/2 min-w-12 btn-red text-white gap-1 px-2 max-xl:justify-center"
            onClick={() => {
              activateBtnFunc();
              setTimeout(() => {
                navigate('/lobby/' + game?.channelCode);
              }, 500);
            }}
          >
            <IoLogOut className="min-w-6 min-h-6 mb-0.5" />
            <p className="text-center w-full text-nowrap text-sm overflow-hidden text-ellipsis xl:flex max-xl:hidden">
              나가기
            </p>
          </CustomButton>
          {/* <CustomButton
            btnCurrentActivate={btnCurrentActivate}
            onClick={() => {
              setQuizCorrectUser({
                nickname: 'sameName',
                round: Math.random(),
              });
              console.log(quizCorrectUser);
            }}
          >
            테스트
          </CustomButton> */}
        </div>
      </div>
      {/* 중간 : 플레이어, 문제 화면 */}
      <div className="w-full h-[25rem] mt-2 mb-4 grid grid-rows-6 grid-cols-5 grid-flow-row gap-3">
        {/* 첫번째 플레이어 */}
        <div className="w-full h-full">
          {gameUserList.length > 0 && (
            <GamePlayer
              userInfo={gameUserList[0]}
              gameChat={messageMap?.get(gameUserList[0].userId)}
            />
          )}
        </div>
        {/* 문제 화면, 타이머 */}
        <div className="w-full grow flex flex-col row-span-6 col-span-3 px-4">
          <div className="h-4 rounded-full w-full bg-white mb-1 border-extralightmint border relative overflow-hidden flex">
            {isQuiz ? (
              <div
                className="w-full h-full rounded-full bg-mint absolute"
                style={progressStyle}
              ></div>
            ) : (
              <div
                className={`w-full h-full rounded-full translate-x-[0%] transition-transform duration-1000 bg-mint absolute`}
              ></div>
            )}
          </div>
          <div className="border-custom- w-full h-full flex items-center justify-center relative">
            <div className="border-custom- w-full h-full flex items-center justify-center relative">
              {isQuiz ? (
                <div className="w-16 h-7 absolute top-2 left-2 bg-yellow-500/80 text-white rounded-full flex items-center justify-center font-extrabold text-xs border border-gray-300">
                  {round} 라운드
                </div>
              ) : (
                <div></div>
              )}
              {isQuiz ? (
                <div className="w-fit h-7 px-3 absolute top-2 bg-yellow-500/80 text-white rounded-full flex items-center justify-center font-extrabold text-xs border border-gray-300">
                  {time}
                </div>
              ) : (
                <div></div>
              )}
              <QuizCorrect correctUser={quizCorrectUser} />
              <div className={`w-full h-full bg-cover bg-center relative`}>
                <img src={imageUrl} alt="" />
              </div>
            </div>
          </div>
        </div>
        {/* 나머지 플레이어 */}
        {gameUserList.map(
          (userInfo: GameUser, index) =>
            index !== 0 && (
              <GamePlayer
                key={index}
                userInfo={userInfo}
                gameChat={messageMap.get(userInfo.userId)}
              />
            )
        )}
        {Array.from({ length: 12 - gameUserList.length }, (_, index) => (
          <div
            className="w-full h-full border-custom-mint bg-gray-200/70 backdrop-blur-sm z-0"
            key={index}
          ></div>
        ))}
      </div>
      {/* 광고, 채팅창, 게임 설정 */}
      <div className="w-full h-[10.5rem] flex gap-4">
        {/* 광고 */}
        <div className="w-1/3 bg-red-200 flex justify-center items-center">
          광고
          {result.map((item, index) => (
            <div key={index}>{JSON.stringify(item)}</div>
          ))}
        </div>
        {/* 채팅창, 객관식 선택, 순서 배치 등 */}
        <div className="w-full flex grow flex-col items-center justify-end px-4 mt-1">
          <div className="w-full h-36 mb-2 relative">
            {/* 객관식 선택 */}
            {isQuiz && (quizType & 1) > 0 ? (
              <SelectionGame
                choiceList={multipleChoice}
                onButtonClick={publishAnswer}
                choosedButton={choosedButton}
              />
            ) : (
              <div></div>
            )}

            {/* 순서 맞추기 */}
            {isQuiz && (quizType & 4) > 0 ? (
              <SubjectiveGame answerWord={answerWord} playerSimilarity={playerSimilarity} />
            ) : (
              <div></div>
            )}

            {isQuiz && (quizType & 2) > 0 ? <div>SequenceGame</div> : <div></div>}
            {/* <SequenceGame /> */}
          </div>
          {/* 채팅 */}
          <div className="w-full">
            <div className="relative w-full">
              <div
                className={`absolute flex items-center w-full h-[7.5rem] bottom-0 mb-2 transition-all origin-bottom duration-300 ${chatOpen && !gamestart ? 'opacity-100 scale-100' : 'opacity-0 scale-0'}`}
              >
                <div className="absolute w-full h-[90%] px-3 py-2 text-sm chat custom-scroll z-10">
                  <div className="z-10 text-gray-700" ref={chattingBox}>
                    {chat.map((chatItem, index) => (
                      <div className="flex" key={index}>
                        <p className="font-extrabold pr-1 text-nowrap text-black">
                          {chatItem.nickname}
                        </p>
                        <p>{chatItem.content}</p>
                      </div>
                    ))}
                  </div>
                </div>
                <div className="absolute w-full h-full border-custom-white opacity-90 bg-white z-0"></div>
              </div>
            </div>

            <div className="w-full h-10 bg-white/80 rounded-full flex relative">
              <input
                ref={chatInput}
                className="w-full h-10 bg-transparent rounded-full pl-5 pr-20 text-sm placeholder-gray-400"
                maxLength={30}
                placeholder={`${chatOpen ? 'Esc를 눌러 채팅창 닫기' : 'Enter를 눌러 채팅 입력'}`}
                onKeyDown={(e) => {
                  if (e.key === 'Enter') {
                    if (chatInput.current.value !== '') {
                      publishChat();
                    }
                  } else if (e.key === 'Escape') chatFocusOut();
                }}
                onClick={chatFocus}
              ></input>
              <div
                className="w-16 bg-mint cursor-pointer absolute h-full right-0 rounded-r-full flex justify-center items-center hover:brightness-125 transition"
                ref={chatBtn}
                onClick={() => {
                  if (chatInput.current.value !== '') {
                    publishChat();
                    if (!chatOpen) chatFocus();
                  }
                }}
              >
                <IoSend className="text-white w-6 h-6" />
              </div>
            </div>
          </div>
        </div>
        {/* 게임 설정 */}
        <div className="w-1/3 flex flex-col cursor-default select-none">
          {/* 방 설정 */}
          <GameRoomSetting gamestart={isStart} />

          {/* 팀 선택, 게임 시작 버튼 */}
          <div className="w-full h-6 my-3 flex">
            {isStart ? (
              <div className="w-full h-full flex gap-3">
                <div className="w-1/3 h-full flex items-center justify-center text-white text-sm font-bold transition text-nowrap border-custom-gray bg-[#999999] cursor-default">
                  1팀
                </div>
                <div className="w-1/3 h-full flex items-center justify-center text-white text-sm font-bold transition text-nowrap border-custom-gray bg-[#999999] cursor-default">
                  2팀
                </div>
                <div className="w-1/3 h-full flex items-center justify-center text-white text-sm font-bold transition text-nowrap border-custom-gray bg-[#999999] cursor-default">
                  랜덤
                </div>
              </div>
            ) : (
              <div className="w-full h-full flex gap-3">
                <CustomButton
                  btnCurrentActivate={btnCurrentActivate}
                  className="w-1/3 h-full text-white text-sm font-bold text-nowrap border-custom-red bg-customRed"
                  onClick={() => {
                    activateBtnFunc();
                    // 1팀 선택 시 실행
                  }}
                >
                  1팀
                </CustomButton>
                <CustomButton
                  btnCurrentActivate={btnCurrentActivate}
                  className="w-1/3 h-full text-white text-sm font-bold text-nowrap border-custom-blue bg-customBlue"
                  onClick={() => {
                    activateBtnFunc();
                    // 2팀 선택 시 실행
                  }}
                >
                  2팀
                </CustomButton>
                <CustomButton
                  btnCurrentActivate={btnCurrentActivate}
                  className="w-1/3 h-full text-white text-sm font-bold text-nowrap border-custom-green bg-customGreen"
                  onClick={() => {
                    activateBtnFunc();
                    // 랜덤 선택 시 실행
                  }}
                >
                  랜덤
                </CustomButton>
              </div>
            )}
          </div>
          {isStart ? (
            <div className="w-full h-10 font-extrabold mt-2 flex items-center justify-center transition mb-1 border-custom-gray bg-[#999999] cursor-default text-white">
              게임시작
            </div>
          ) : (
            <CustomButton
              btnCurrentActivate={btnCurrentActivate}
              className="w-full h-10 text-white font-extrabold text-nowrap btn-mint-border-white mt-2 mb-1"
              onClick={() => {
                activateBtnFunc();
                // 게임 시작
                setTimeout(() => {
                  publishStart();
                }, 500);
              }}
            >
              게임시작
            </CustomButton>
          )}
        </div>
      </div>
    </div>
  );
};

export default GamePage;
