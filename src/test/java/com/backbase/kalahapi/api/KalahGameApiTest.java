package com.backbase.kalahapi.api;


import com.backbase.kalahapi.domain.ExceptionCode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KalahGameApi.class)
@ComponentScan("com.backbase.kalahapi")
public class KalahGameApiTest {

  @Autowired
  private MockMvc mvc;

  @Test
  public void test_mvcTestSetup_success() {
    Assert.assertNotNull(mvc);
  }

  @Test
  public void test_createGame_success() throws Exception {
    mvc.perform(post("/games")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(is(notNullValue())))
        .andExpect(jsonPath("$.url").value(startsWith("http://localhost/games/")));
  }

  @Test
  public void test_makeMove_success() throws Exception {
    mvc.perform(post("/games")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    mvc.perform(put("/games/1/pits/2")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1"))
        .andExpect(jsonPath("$.url").value(startsWith("http://localhost/games/")));
  }

  @Test
  public void test_makeMoveOnInvalidPitId_failWithHttp4xx() throws Exception {
    mvc.perform(post("/games")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    mvc.perform(put("/games/1/pits/15")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.error_code").value(ExceptionCode.INVALID_PIT_ID.getCode()));
  }

}
