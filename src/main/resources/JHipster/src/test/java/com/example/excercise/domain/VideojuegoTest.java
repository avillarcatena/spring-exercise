package com.example.excercise.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.excercise.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VideojuegoTest {

  @Test
  void equalsVerifier() throws Exception {
    TestUtil.equalsVerifier(Videojuego.class);
    Videojuego videojuego1 = new Videojuego();
    videojuego1.setId(1L);
    Videojuego videojuego2 = new Videojuego();
    videojuego2.setId(videojuego1.getId());
    assertThat(videojuego1).isEqualTo(videojuego2);
    videojuego2.setId(2L);
    assertThat(videojuego1).isNotEqualTo(videojuego2);
    videojuego1.setId(null);
    assertThat(videojuego1).isNotEqualTo(videojuego2);
  }
}
