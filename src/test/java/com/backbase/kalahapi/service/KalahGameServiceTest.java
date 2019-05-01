package com.backbase.kalahapi.service;

import com.backbase.kalahapi.domain.ExceptionCode;
import com.backbase.kalahapi.domain.GameException;
import com.backbase.kalahapi.domain.KalahGame;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KalahGameServiceTest {

  @Autowired
  KalahGameService kalahGameService;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void test_setupServiceObject_success() {
    assertNotNull(kalahGameService);
  }

  @Test
  public void test_createGame_success() {
    KalahGame game = kalahGameService.createGame();
    assertNotNull(game);
    assertEquals(KalahGame.GAME_STARTED, game.whoseTurn());
  }

  @Test
  public void test_createGameAndThenMakeValidMove_success() {
    KalahGame game = kalahGameService.createGame();
    kalahGameService.move(game.getId(), 2);
    assertEquals(KalahGame.PLAYER_TWO, game.whoseTurn());
  }

  @Test
  public void test_createGameAndThenMakeMoveOnNotExistGame_fail() {
    thrown.expect(GameException.class);
    thrown.expectMessage(ExceptionCode.GAME_NOT_FOUND.getMessage());

    kalahGameService.createGame();
    kalahGameService.move(-1, 2);
  }
}
