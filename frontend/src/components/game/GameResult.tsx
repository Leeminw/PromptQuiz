import React from 'react';
interface GameResultProps {
  result: GameUser[];
}
const GameResult = ({ result }: GameResultProps) => {
  // 정렬된 result

  return (
    <div
      className={`absolute w-full h-full justify-center items-center z-50 select-none flex flex-col gap-2`}
    >
      <div className="w-full h-full grid grid-cols-3 gap-3">
        {
          /* 2등 */
          result.length > 1 ? (
            <div className="w-full h-full flex items-center flex-col justify-end">
              <div
                className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)]
          bg-cover w-20 h-20 aspect-square mb-2 relative flex justify-center"
              >
                <div className="w-7 h-7 bg-gray-400 absolute flex justify-center items-center font-extrabold text-white rounded-full text-nowrap -top-5 border-2 border-white">
                  2
                </div>
              </div>
              <div className="text-white font-extrabold px-1 line-clamp-1 bg-mint border-custom-mint text-sm flex">
                <p className="w-fyll break-words line-clamp-1">{result[1].nickName}</p>
                <p className="pl-1 text-nowrap">{result[1].score}점</p>
              </div>
            </div>
          ) : (
            // 사람 없을때 빈 칸 채우기
            <div className="w-full h-full flex items-center flex-col justify-end"></div>
          )
        }
        {
          /* 1등 */
          result.length > 0 && (
            <div className="w-full h-full flex items-center flex-col justify-end">
              <div
                className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)]
          bg-cover w-24 h-24 aspect-square mb-2 relative flex justify-center"
              >
                <div className="w-9 h-9 bg-yellow-500 absolute flex justify-center items-center font-extrabold text-white rounded-full text-nowrap -top-5 border-2 border-white">
                  1
                </div>
              </div>
              <div className="text-white font-extrabold px-1 line-clamp-1 bg-mint border-custom-mint text-sm flex mb-2">
                <p className="w-fyll break-words line-clamp-1">{result[0].nickName}</p>
                <p className="pl-1 text-nowrap">{result[0].score}점</p>
              </div>
            </div>
          )
        }
        {
          /* 3등 */
          result.length > 2 ? (
            <div className="w-full h-full flex items-center flex-col justify-end">
              <div
                className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)]
            bg-cover w-20 h-20 aspect-square mb-2 relative flex justify-center"
              >
                <div className="w-7 h-7 bg-yellow-600 absolute flex justify-center items-center font-extrabold text-white rounded-full text-nowrap -top-5 border-2 border-white">
                  3
                </div>
              </div>
              <div className="text-white font-extrabold px-1 line-clamp-1 bg-mint border-custom-mint text-sm flex">
                <p className="w-fyll break-words line-clamp-1">{result[2].nickName}</p>
                <p className="pl-1 text-nowrap">{result[2].score}점</p>
              </div>
            </div>
          ) : (
            <div className="w-full h-full flex items-center flex-col justify-end"></div>
          )
        }
      </div>
      {
        /* 나머지 인원들 */
        result.length > 3 ? (
          <div className="w-full min-h-40 flex-grow grid grid-rows-3 grid-cols-3 gap-3 pt-2">
            {result.map(
              (gameUser, index) =>
                index >= 3 && (
                  <div
                    key={index}
                    className="w-full h-full pl-1 bg-mint backd border-custom-mint flex items-center relative overflow-hidden"
                  >
                    <div className="min-w-6 h-6 flex items-center justify-center pr-2 text-white font-extrabold text-nowrap">
                      {index + 1}
                    </div>
                    <div className="rounded-full bg-[url(https://contents-cdn.viewus.co.kr/image/2023/08/CP-2023-0056/image-7adf97c8-ef11-4def-81e8-fe2913667983.jpeg)] bg-cover w-8 h-8 aspect-square"></div>
                    <p className="pl-2 w-full text-xs font-bold text-white line-clamp-2">
                      {gameUser.nickName}
                    </p>
                    <p className="h-full text-nowrap text-white text-sm pr-1 pl-1 flex items-center font-bold translate-x-1">
                      {gameUser.score}점
                    </p>
                  </div>
                )
            )}
          </div>
        ) : (
          <div className="w-full min-h-40 flex-grow grid grid-rows-3 grid-cols-3 gap-3 pt-2"> </div>
        )
      }
    </div>
  );
};

export default GameResult;
