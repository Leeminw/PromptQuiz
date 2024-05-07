package com.ssafy.apm.user.service;

import com.ssafy.apm.common.domain.JwtProvider;
import com.ssafy.apm.user.domain.RefreshToken;
import com.ssafy.apm.user.domain.User;
import com.ssafy.apm.user.dto.*;
import com.ssafy.apm.user.exceptions.UserNotFoundException;
import com.ssafy.apm.user.repository.RefreshTokenRepository;
import com.ssafy.apm.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public User loadUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            throw new UserNotFoundException("forbidden");
        }
        return (User) authentication.getPrincipal();
    }

    @Override
    @Transactional
    public UserDetailResponseDto createUser(UserCreateRequestDto requestDto) {
        User user = requestDto.toEntity();
        user.encodePassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new UserDetailResponseDto(user);
    }

    @Override
    public UserDetailResponseDto readUser() {
        User user = this.loadUser();
        return new UserDetailResponseDto(user);
    }

//    @Override
//    public UserDetailResponseDto updateUser(UserUpdateRequestDto requestDto) {
//        User user = userRepository.findById(requestDto.getUserId())
//                .orElseThrow(() -> new UserNotFoundException(requestDto.getUserId()));
//        userRepository.save(user);
//        return new UserDetailResponseDto(user);
//    }
//
//    @Override
//    public UserDetailResponseDto deleteUser(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(userId));
//        userRepository.delete(user);
//        return new UserDetailResponseDto(user);
//    }

    @Override
    @Transactional
    public UserLoginResponseDto loginUser(UserLoginRequestDto requestDto) {
        log.debug("service : {}", requestDto.toString());
        User user = userRepository.findByUserName(requestDto.getUserName())
                .orElseThrow(() -> new UserNotFoundException("not found user"));
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new UserNotFoundException(user.getId());
        }
        String accessToken = jwtProvider.createAccessToken(user.getId(),user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getId(),user.getRole());
        refreshTokenRepository.save(new RefreshToken(refreshToken,user.getId()));
        return new UserLoginResponseDto(user.getId(),accessToken,refreshToken);
    }

    @Override
    public Boolean isExistUserName(String userName) {
        return userRepository.existsUserByUserName(userName);
    }

    @Override
    @Transactional
    public UserDetailResponseDto updateProfile(String profileUrl) {
        User user = this.loadUser();
        user.updateProfile(profileUrl);
        userRepository.save(user);
        return new UserDetailResponseDto(user);
    }

    @Override
    @Transactional
    public UserDetailResponseDto updateStatusMessage(String message) {
        User user = this.loadUser();
        user.updateStatusMessage(message);
        userRepository.save(user);
        return new UserDetailResponseDto(user);
    }

    @Override
    @Transactional
    public UserDetailResponseDto updateUserScore(UserScoreUpdateRequestDto requestDto) {
        User user = this.loadUser();
        user.updateScore(requestDto);
        userRepository.save(user);
        return new UserDetailResponseDto(user);
    }

    @Override
    @Transactional
    public void logoutUser(String header) {
        String token = header.replace("Bearer ","");
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(token);
        refreshToken.ifPresent(refreshTokenRepository::delete);
    }

    @Override
    public UserRankingResponseDto getUserRanking() {
        List<UserDetailResponseDto> soloRanking = getUserRanking(userRepository::findTop10ByOrderBySoloScoreDesc);
        List<UserDetailResponseDto> teamRanking = getUserRanking(userRepository::findTop10ByOrderByTeamScoreDesc);
        List<UserDetailResponseDto> totalRanking = getUserRanking(userRepository::findTop10ByOrderByTotalScoreDesc);

        return new UserRankingResponseDto(teamRanking,soloRanking,totalRanking);
    }

    private List<UserDetailResponseDto> getUserRanking(Supplier<Optional<List<User>>> rankingSupplier) {
        return rankingSupplier.get()
                .orElseThrow(() -> new NoSuchElementException("No entities"))
                .stream()
                .map(UserDetailResponseDto::new)
                .toList();
    }
}
