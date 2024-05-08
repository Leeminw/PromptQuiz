package com.ssafy.apm.game.service;

import com.ssafy.apm.channel.repository.ChannelRepository;
import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
import com.ssafy.apm.game.dto.request.GameUpdateRequestDto;
import com.ssafy.apm.game.dto.response.GameResponseDto;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gamequiz.repository.GameQuizRepository;
import com.ssafy.apm.gameuser.domain.GameUser;
import com.ssafy.apm.gameuser.exception.GameUserNotFoundException;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameServiceImpl implements GameService {

    private final UserService userService;
    private final GameRepository gameRepository;
    private final ChannelRepository channelRepository;
    private final GameUserRepository gameUserRepository;
    private final GameQuizRepository gameQuizRepository;

    @Override
    @Transactional
    public GameResponseDto createGame(GameCreateRequestDto requestDto) {
        /*
         * 방 만든 사람의 GameUser Entity도 생성해서 저장
         * */
        User user = userService.loadUser();
        Game game = gameRepository.save(requestDto.toEntity());
        GameUser gameUser = GameUser.builder()
                .gameCode(game.getCode())
                .userId(user.getId())
                .isHost(true)
                .score(0)
                .team("NOTHING")
                .build();
        gameUserRepository.save(gameUser);

        return new GameResponseDto(game);
    }

    @Override
    @Transactional
    public GameResponseDto updateGame(GameUpdateRequestDto requestDto) {
        Game game = gameRepository.findByCode(requestDto.getCode()).orElseThrow(
                () -> new GameNotFoundException("Game Not Found with gameCode: " + requestDto.getCode()));
        return new GameResponseDto(gameRepository.save(game.update(requestDto)));
    }

    @Override
    @Transactional
    public GameResponseDto deleteGame(String code) {
        Game game = gameRepository.findByCode(code).orElseThrow(
                () -> new GameNotFoundException(code));
        List<GameUser> gameUsers = gameUserRepository.findAllByGameCode(code).orElseThrow(
                () -> new GameUserNotFoundException("No entities exists by gameCode: " + code));

        gameUserRepository.deleteAll(gameUsers);
        gameRepository.delete(game);
        return new GameResponseDto(game);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<GameResponseDto> findGamesByChannelCode(String channelCode) {
        List<Game> entityList = gameRepository.findAllByChannelCode(channelCode)
                .orElseThrow(() -> new GameNotFoundException("No entities exists by channelId"));// 예외가 아니라 빈 리스트라도 던져야하나

        return entityList.stream()
                .map(GameResponseDto::new)
                .toList();

    }

    @Override
    public GameResponseDto findGameByGameCode(String gameCode) {
        Game game = gameRepository.findByCode(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        return new GameResponseDto(game);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public Integer updateGameRoundCnt(String gameCode, Boolean flag) {
        Game game = gameRepository.findById(gameCode)
                .orElseThrow(() -> new GameNotFoundException(gameCode));
        if (flag) {
//            curRound 1로 초기화
            /* TODO: initCurRounds() 로 추가 및 수정 필요 */
//            response = game.updateCurRound();
        } else {
//        마지막 라운드라면
            if (game.getCurRounds() >= game.getMaxRounds()) {
                return -1;
            }
            game.increaseCurRounds();
        }
        gameRepository.save(game);
        return game.getCurRounds();
    }

}
