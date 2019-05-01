package com.backbase.kalahapi.api;

import com.backbase.kalahapi.domain.GameException;
import com.backbase.kalahapi.domain.KalahGame;
import com.backbase.kalahapi.service.KalahGameService;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/games", produces = "application/json")
public class KalahGameApi {

  private static final Logger LOGGER = LoggerFactory.getLogger(KalahGameApi.class);

  private final KalahGameService gameService;

  @Autowired
  public KalahGameApi(KalahGameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping
  public ResponseEntity<GameDTO> createGame(HttpServletRequest request) {
    KalahGame kalahGame = gameService.createGame();
    int id = kalahGame.getId();
    GameDTO game = new GameDTO(id, getUrl(request, id));
    return new ResponseEntity<>(game, HttpStatus.CREATED);
  }

  @PutMapping(value = "/{gameId}/pits/{pitId}")
  public ResponseEntity<GameDTO> move(HttpServletRequest request,
      @Positive @PathVariable("gameId") Integer gameId,
      @Min(1) @Max(14) @PathVariable("pitId") Integer pitId) {

    KalahGame kalahGame = gameService.move(gameId, pitId);
    GameDTO gameDTO = getGameDTO(request, kalahGame);
    return ResponseEntity.ok(gameDTO);
  }

  @ExceptionHandler
  public ResponseEntity<Map<String, String>> handleGameExceptions(GameException e) {
    LOGGER.error(e.toString());
    int code = e.getCode();
    String message = e.getMessage();
    Map<String, String> errorMessage = new HashMap<>();
    errorMessage.put("error_code", String.valueOf(code));
    errorMessage.put("message", message);
    return ResponseEntity.badRequest().body(errorMessage);
  }

  @ExceptionHandler
  public ResponseEntity<Map<String, String>> handleExceptions(Exception e) {
    LOGGER.error("unexpected internal server error", e);
    Map<String, String> errorMessage = new HashMap<>();
    errorMessage.put("error_code", "0");
    errorMessage.put("message", "Unexpected Internal Exception");
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
  }

  private GameDTO getGameDTO(HttpServletRequest request, KalahGame kalahGame) {
    int id = kalahGame.getId();
    Map<String, String> status = getStatus(kalahGame);
    String url = getUrl(request, id);
    return new GameDTO(id, url, status);
  }

  private Map<String, String> getStatus(KalahGame kalahGame) {
    Map<String, String> status = new TreeMap<>();
    int[] pits = kalahGame.getPits();
    for (int i = 0; i < pits.length; i++) {
      status.put(String.valueOf(i), String.valueOf(pits[i]));
    }
    return status;
  }

  private String getUrl(HttpServletRequest request, int id) {
    String requestURL = request.getRequestURL().toString();
    String requestURI = request.getRequestURI();
    String url = requestURL.replace(requestURI, "");
    return url + "/games/" + id;
  }

}
