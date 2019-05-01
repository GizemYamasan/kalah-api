package com.backbase.kalahapi.api;

import java.util.Map;

public class GameDTO {

  private String id;
  private String url;
  private Map<String,String> status;

  public GameDTO(int id, String url) {
    this(id, url, null);
  }

  public GameDTO(int id, String url, Map<String, String> status) {
    this.id = String.valueOf(id);
    this.url = url;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public Map<String,String> getStatus() {
    return status;
  }
}