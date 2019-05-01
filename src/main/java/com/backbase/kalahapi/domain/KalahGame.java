package com.backbase.kalahapi.domain;

import java.util.Arrays;

public class KalahGame {

  static final int STONE_COUNT = 6;
  static final int PLAYER_ONE_HOUSE = 6;
  static final int PLAYER_TWO_HOUSE = 13;

  static final int LAST_STONE_TO_PLAYER_HOUSE = 0;
  static final int LAST_STONE_TO_ANY_PIT = 1;

  // GAME STATUS LIST
  public static final int GAME_OVER = Integer.MIN_VALUE;
  public static final int GAME_STARTED = Integer.MAX_VALUE;
  public static final int PLAYER_ONE = 1;
  public static final int PLAYER_TWO = 2;

  private int id;
  private int[] pits;
  private int currentPlayer;

  /**
   * creates new kalah game <br/> Each of the two players has **​six pits** ​in front of him/her. To
   * the right of the six pits, each player has a larger pit, his Kalah or house. At the start of
   * the game, six stones are put in each pit.
   */
  public KalahGame(int id) {
    this.id = id;
    this.currentPlayer = GAME_STARTED;
    this.pits = new int[14];
    Arrays.fill(pits, STONE_COUNT); // fill pits with 6 stones
    pits[getHouseId(PLAYER_ONE)] = 0; // player one house is initially empty
    pits[getHouseId(PLAYER_TWO)] = 0; // player two house is initially empty
  }

  public int getId() {
    return id;
  }

  public int[] getPits() {
    return this.pits;
  }

  public int whoseTurn() {
    return currentPlayer;
  }

  public void pickPit(int pitId) {
    pitId--;
    validateMove(pitId);

    int player = getPlayerFromPitId(pitId);
    int stones = collectStones(pitId);
    int status = placeStones(player, pitId, stones);
    switch (status) {
      case LAST_STONE_TO_ANY_PIT:
        // now this is opponents' s turn
        currentPlayer = getOpponent(player);
        break;
      case LAST_STONE_TO_PLAYER_HOUSE:
        // one more turn for current player
        currentPlayer = player;
        break;
      case GAME_OVER:
        currentPlayer = GAME_OVER;
        break;
    }
  }

  int collectStones(int pitId) {
    int stones = pits[pitId];
    pits[pitId] = 0;
    return stones;
  }

  int placeStones(int player, int pitId, int stones) {
    int opponent = getOpponent(player);
    int opponentHouseId = getHouseId(opponent);
    int playerHouseId = getHouseId(player);
    int lastPitId = 0;

    for (int i = pitId + 1; stones > 0; i++) {
      i = i % pits.length;
      if (i != opponentHouseId) {
        pits[i]++;
        stones--;
        lastPitId = i;
      }
    }

    if (isPlayerPitsEmpty(player)) {
      collectPitStonesToHouse(opponent);
      return GAME_OVER;
    }
    else if (lastPitId == playerHouseId) {
      return LAST_STONE_TO_PLAYER_HOUSE;
    }
    else {
      return LAST_STONE_TO_ANY_PIT;
    }
  }

  int getHouseId(int player) {
    if (player == PLAYER_ONE) {
      return PLAYER_ONE_HOUSE;
    } else {
      return PLAYER_TWO_HOUSE;
    }
  }

  int getOpponent(int player) {
    if (player == PLAYER_ONE) {
      return PLAYER_TWO;
    } else {
      return PLAYER_ONE;
    }
  }

  private void collectPitStonesToHouse(int player) {
        if (player == PLAYER_ONE) {
      for (int i = 0; i < PLAYER_ONE_HOUSE; i++) {
        pits[PLAYER_ONE_HOUSE] += pits[i];
        pits[i] = 0;
      }
    } else {
      for (int i = 7; i < PLAYER_TWO_HOUSE; i++) {
        pits[PLAYER_TWO_HOUSE] += pits[i];
        pits[i] = 0;
      }
    }
  }

  private boolean isPlayerPitsEmpty(int player) {
    if (player == PLAYER_ONE) {
      for (int i = 0; i < PLAYER_ONE_HOUSE; i++) {
        if (pits[i] > 0) {
          return false;
        }
      }
    } else {
      for (int i = 7; i < PLAYER_TWO_HOUSE; i++) {
        if (pits[i] > 0) {
          return false;
        }
      }
    }
    return true;
  }

  void validateMove(int pitId) {
    if (currentPlayer == GAME_OVER) {
      throw new GameException(ExceptionCode.GAME_OVER);
    }
    if (currentPlayer != GAME_STARTED) {
      int player = getPlayerFromPitId(pitId);
      if (player != currentPlayer) {
        throw new GameException(ExceptionCode.NOT_YOUR_TURN);
      }
      if (pits[pitId] == 0) {
        throw new GameException(ExceptionCode.EMPTY_PIT);
      }
    }
  }

  int getPlayerFromPitId(int pitId) {
    if (0 <= pitId && pitId < PLAYER_ONE_HOUSE) {
      return PLAYER_ONE;
    } else if (7 <= pitId && pitId < PLAYER_TWO_HOUSE) {
      return PLAYER_TWO;
    } else {
      throw new GameException(ExceptionCode.INVALID_PIT_ID);
    }
  }

}
