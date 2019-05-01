package com.backbase.kalahapi.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class KalahGameTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  KalahGame kalahGame;

  @Before
  public void setup() {
    kalahGame = new KalahGame(1001);
  }

  @Test
  public void test_nextPlayer_success() {
    int player = kalahGame.whoseTurn();
    assertEquals(player, KalahGame.GAME_STARTED);
  }

  @Test
  public void test_getOpponent_success() {
    int opponent = kalahGame.getOpponent(KalahGame.PLAYER_ONE);
    assertEquals(KalahGame.PLAYER_TWO, opponent);
  }

  @Test
  public void test_validateMove_failNotYourTurn() {
    thrown.expect(GameException.class);
    thrown.expectMessage(ExceptionCode.NOT_YOUR_TURN.getMessage());

    // player one picked and last stone did not go to house
    kalahGame.pickPit(3);
    // player one picked again
    kalahGame.pickPit(2);
  }

  @Test
  public void test_validateMove_failEmptyPit() {
    thrown.expect(GameException.class);
    thrown.expectMessage(ExceptionCode.EMPTY_PIT.getMessage());

    // player one picked and last stone went to house, so player one's turn again
    kalahGame.pickPit(1);
    kalahGame.pickPit(1);
  }

  @Test
  public void test_validateMove3_failInvalidPitIndex() {
    thrown.expect(GameException.class);
    thrown.expectMessage(ExceptionCode.INVALID_PIT_ID.getMessage());

    // house cannot be picked
    kalahGame.pickPit(7);
  }

  @Test
  public void test_collectStones_success() {
    int stones = kalahGame.collectStones(0);
    assertEquals(6, stones);
    int[] pits = kalahGame.getPits();
    assertEquals(0, pits[0]);
  }

  @Test
  public void test_placePits_success() {
    int pitId = 1;
    int player = kalahGame.getPlayerFromPitId(pitId);

    int stones = kalahGame.collectStones(pitId - 1);
    int lastStone = kalahGame.placeStones(player, pitId - 1, stones);

    assertEquals(KalahGame.LAST_STONE_TO_PLAYER_HOUSE, lastStone);
    int[] playerPits = kalahGame.getPits();
    assertEquals(7, playerPits[1]);
    assertEquals(7, playerPits[2]);
    assertEquals(7, playerPits[3]);
    assertEquals(7, playerPits[4]);
    assertEquals(7, playerPits[5]);
  }

  @Test
  public void test_pickPitsLeaveLastStoneToHouseAndPlayerNotChanges_success() {
    // player one picks first pit, and leave last stone to house.
    // so player wont change
    kalahGame.pickPit(1);
    assertEquals(KalahGame.PLAYER_ONE, kalahGame.whoseTurn());
  }

  @Test
  public void test_pickPitsLeaveLastStoneToAnyPitAndPlayerChanges_success() {
    // player one picks first pit, and leave last stone to house.
    // so player wont change
    kalahGame.pickPit(2);
    assertEquals(KalahGame.PLAYER_TWO, kalahGame.whoseTurn());
  }

  @Test
  public void test_pickPitsConsequently_success() {
    // player one and then player two
    kalahGame.pickPit(6);
    kalahGame.pickPit(12);
    // player one and then player two
    kalahGame.pickPit(5);
    kalahGame.pickPit(11);
    // player one and then player two
    kalahGame.pickPit(4);
    kalahGame.pickPit(10);
    // player one and then player two
    kalahGame.pickPit(3);
    kalahGame.pickPit(9);
    // player one and then player two
    kalahGame.pickPit(2);
    kalahGame.pickPit(8);
  }
}
