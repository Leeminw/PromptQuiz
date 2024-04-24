//package com.ssafy.apm.game;
//
//import com.ssafy.apm.game.domain.GameEntity;
//import com.ssafy.apm.game.dto.request.GameCreateRequestDto;
//import com.ssafy.apm.game.dto.response.GameGetResponseDto;
//import com.ssafy.apm.game.repository.GameRepository;
//import com.ssafy.apm.game.service.GameServiceImpl;
//import com.ssafy.apm.gameuser.domain.GameUserEntity;
//import com.ssafy.apm.gameuser.repository.GameUserRepository;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.verify;
//
////@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class GameServiceTest {
//    @InjectMocks
//    GameServiceImpl gameService;
//    @Mock
//    GameRepository gameRepository;
//    @Mock
//    GameUserRepository gameUserRepository;
//
//    @Test
//    @DisplayName("방 등록 테스트")
////    @Disabled
//    public void createGameTest() throws Exception {
//        // Given
//        GameCreateRequestDto dto = new GameCreateRequestDto(1L, 1L, 0, 0, "들어와", null, true, false, 0, 0, 1, 8);
//        GameEntity gameEntity = dto.toEntity();
//        gameEntity.updateId(1L);
//        GameUserEntity gameUserEntity = GameUserEntity.builder()
//                .gameId(gameEntity.getId())
//                .userId(dto.getUserId())
//                .isHost(true)
//                .isReady(true)
//                .score(0)
//                .team("RED")
//                .ranking(0)
//                .build();
//
//        given(gameRepository.save(any(GameEntity.class))).willReturn(gameEntity);
//        given(gameUserRepository.save(any(GameUserEntity.class))).willReturn(gameUserEntity);
//
//        // When
//        gameService.createGame(dto);
//
//        // Then
//        verify(gameRepository).save(any(GameEntity.class));
//        verify(gameUserRepository).save(any(GameUserEntity.class));
//    }
//    @Test
//    @DisplayName("방 정보 리스트 조회")
////    @Disabled
//    void findBoardsServiceTest() {
//        Long channelId = 1L;
//        // given
//        List<GameEntity> games = new ArrayList<>();
//        GameEntity gameEntity = GameEntity.builder()
//                .id(1L)
//                .channelId(1L)
//                .type(0)
//                .style(0)
//                .code("12132")
//                .title("들어와")
//                .status(true)
//                .isTeam(false)
//                .curPlayers(0)
//                .curRound(0)
//                .rounds(8)
//                .maxPlayers(8)
//                .build();
//        GameEntity gameEntity2 = GameEntity.builder()
//                .id(2L)
//                .channelId(1L)
//                .type(0)
//                .style(0)
//                .code("31212")
//                .title("들어와2")
//                .status(true)
//                .isTeam(false)
//                .curPlayers(0)
//                .curRound(0)
//                .rounds(8)
//                .maxPlayers(8)
//                .build();
//        games.add(gameEntity);
//        games.add(gameEntity2);
//
//        given(gameRepository.findAllByChannelId(channelId)).willReturn(games);
//
//        // when
//        List<GameGetResponseDto> result = gameService.getGameList(channelId);
//
//        // then
//        assertThat(result.size()).isEqualTo(2);
//    }
//}
