package com.backbase.kalahapi.service;

import com.backbase.kalahapi.domain.ExceptionCode;
import com.backbase.kalahapi.domain.GameException;
import com.backbase.kalahapi.domain.KalahGame;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class KalahGameService {

  private Map<Integer, KalahGame> games;
  private AtomicInteger index;

  public KalahGameService() {
    games = new ConcurrentHashMap<>();
    index = new AtomicInteger();
  }

  public KalahGame createGame() {
    int id = index.incrementAndGet();
    KalahGame kalahGame = new KalahGame(id);
    games.put(id, kalahGame);
    return kalahGame;
  }

  public KalahGame move(Integer gameId, Integer pitId) {
    if (!games.containsKey(gameId)) {
      throw new GameException(ExceptionCode.GAME_NOT_FOUND);
    }
    KalahGame kalahGame = games.get(gameId);
    kalahGame.pickPit(pitId);
    return kalahGame;
  }
}
