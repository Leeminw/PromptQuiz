package com.ssafy.apm.gameuser.service;

import com.ssafy.apm.game.domain.Game;
import com.ssafy.apm.game.exception.GameNotFoundException;
import com.ssafy.apm.game.repository.GameRepository;
import com.ssafy.apm.gameuser.domain.GameUser;
import com.ssafy.apm.gameuser.dto.request.GameUserCreateRequestDto;
import com.ssafy.apm.gameuser.dto.request.GameUserUpdateRequestDto;
import com.ssafy.apm.gameuser.dto.response.GameUserDetailResponseDto;
import com.ssafy.apm.gameuser.dto.response.GameUserSimpleResponseDto;
import com.ssafy.apm.gameuser.exception.GameUserNotFoundException;
import com.ssafy.apm.gameuser.repository.GameUserRepository;
import com.ssafy.apm.user.exceptions.UserNotFoundException;
import com.ssafy.apm.user.repository.UserRepository;
import com.ssafy.apm.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameUserServiceImpl implements GameUserService {

    private final GameUserRepository gameUserRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final GameRepository gameRepository;

    @Transactional
    public GameUserSimpleResponseDto createGameUser(GameUserCreateRequestDto requestDto) {
        GameUser gameUser = gameUserRepository.save(requestDto.toEntity());
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUser(GameUserUpdateRequestDto requestDto) {
        GameUser gameUser = gameUserRepository.findById(requestDto.getCode()).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with code: " + requestDto.getCode()));
        gameUser = gameUserRepository.save(gameUser.update(requestDto));
        return new GameUserSimpleResponseDto(gameUser);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public GameUserSimpleResponseDto deleteGameUser(String code) {
        GameUser gameUser = gameUserRepository.findById(code).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with code: " + code));
        gameUserRepository.delete(gameUser);
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto deleteGameUser(String gameCode, Long userId) {
        GameUser gameUser = gameUserRepository.findByGameCodeAndUserId(gameCode, userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with " +
                        "GameCode, UserId: " + gameCode + ", " + userId));
        gameUserRepository.delete(gameUser);
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public List<GameUserSimpleResponseDto> deleteGameUsersByGameCode(String gameCode) {
        List<GameUser> gameUsers = gameUserRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameUserNotFoundException("Entities Not Found with GameCode: " + gameCode));
        gameUserRepository.deleteAll(gameUsers);
        return gameUsers.stream().map(GameUserSimpleResponseDto::new).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public GameUserSimpleResponseDto findGameUser(String code) {
        GameUser gameUser = gameUserRepository.findById(code).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with code: " + code));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    public GameUserSimpleResponseDto findGameUser(String gameCode, Long userId) {
        GameUser gameUser = gameUserRepository.findByGameCodeAndUserId(gameCode, userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with " +
                        "GameCode, UserId: " + gameCode + ", " + userId));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    public List<GameUserSimpleResponseDto> findSimpleGameUsersByGameCode(String gameCode) {
        List<GameUser> gameUsers = gameUserRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameUserNotFoundException("Entities Not Found with GameCode: " + gameCode));
        return gameUsers.stream().map(GameUserSimpleResponseDto::new).toList();
    }

    @Override
    public List<GameUserDetailResponseDto> findDetailGameUsersByGameCode(String gameCode) {
        List<GameUser> gameUsers = gameUserRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameUserNotFoundException("Entities Not Found with GameCode: " + gameCode));
        return gameUsers.stream().map(gameUser -> new GameUserDetailResponseDto(
                userRepository.findById(gameUser.getUserId()).orElseThrow(
                        () -> new UserNotFoundException(gameUser.getUserId())), gameUser
        )).toList();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUserTeam(String team) {
        Long userId = userService.loadUser().getId();
        GameUser gameUser = gameUserRepository.findByUserId(userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with UserId: " + userId));
        gameUser = gameUserRepository.save(gameUser.updateTeam(team));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUserScore(Integer score) {
        Long userId = userService.loadUser().getId();
        GameUser gameUser = gameUserRepository.findByUserId(userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with UserId: " + userId));
        gameUser = gameUserRepository.save(gameUser.updateScore(score));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUserIsHost(Boolean isHost) {
        Long userId = userService.loadUser().getId();
        GameUser gameUser = gameUserRepository.findByUserId(userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with UserId: " + userId));
        gameUser = gameUserRepository.save(gameUser.updateIsHost(isHost));
        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public GameUserSimpleResponseDto updateGameUserScore(Long userId, Integer score) {
        GameUser gameUser = gameUserRepository.findByUserId(userId).orElseThrow(
                () -> new GameUserNotFoundException("Entity Not Found with UserId: " + userId));
        gameUser = gameUserRepository.save(gameUser.updateScore(score));

        return new GameUserSimpleResponseDto(gameUser);
    }

    @Override
    @Transactional
    public List<GameUserSimpleResponseDto> resetGameUserScore(String gameCode) {
        List<GameUser> gameUserList = gameUserRepository.findAllByGameCode(gameCode).orElseThrow(
                () -> new GameUserNotFoundException("Entities Not Found with GameCode: " + gameCode));
        for (GameUser gameUser : gameUserList) gameUser.initScore(0);
        gameUserRepository.saveAll(gameUserList);

        return gameUserList.stream()
                .map(GameUserSimpleResponseDto::new)
                .toList();
    }

}
