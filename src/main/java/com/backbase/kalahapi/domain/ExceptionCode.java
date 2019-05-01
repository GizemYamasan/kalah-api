package com.backbase.kalahapi.domain;

public enum ExceptionCode {

  INVALID_PIT_ID(1, "you can select pits index between 1 and 14"),
  EMPTY_PIT(2, "you cannot pick from empty pit"),
  NOT_YOUR_TURN (3, "it is not your turn"),
  GAME_NOT_FOUND(4, "game not found"),
  GAME_OVER(5, "game is over");

  int code;
  String message;

  ExceptionCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
