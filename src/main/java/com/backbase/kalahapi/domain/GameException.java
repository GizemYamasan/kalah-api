package com.backbase.kalahapi.domain;

public class GameException extends RuntimeException {

  private int code;

  public GameException(ExceptionCode exceptionCode) {
    super(exceptionCode.getMessage());
    this.code = exceptionCode.getCode();
  }

  public int getCode() {
    return code;
  }

  @Override
  public String toString() {
    return "GameException[code:" + code + " message:" + getMessage() + "]";
  }
}
