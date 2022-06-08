package com.example.excercise.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.excercise.IntegrationTest;
import com.example.excercise.domain.Videojuego;
import com.example.excercise.repository.VideojuegoRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link VideojuegoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VideojuegoResourceIT {

  private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
  private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

  private static final String DEFAULT_IMAGENES = "AAAAAAAAAA";
  private static final String UPDATED_IMAGENES = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/videojuegos";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(
    random.nextInt() + (2 * Integer.MAX_VALUE)
  );

  @Autowired
  private VideojuegoRepository videojuegoRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restVideojuegoMockMvc;

  private Videojuego videojuego;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Videojuego createEntity(EntityManager em) {
    Videojuego videojuego = new Videojuego()
      .nombre(DEFAULT_NOMBRE)
      .imagenes(DEFAULT_IMAGENES);
    return videojuego;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Videojuego createUpdatedEntity(EntityManager em) {
    Videojuego videojuego = new Videojuego()
      .nombre(UPDATED_NOMBRE)
      .imagenes(UPDATED_IMAGENES);
    return videojuego;
  }

  @BeforeEach
  public void initTest() {
    videojuego = createEntity(em);
  }

  @Test
  @Transactional
  void createVideojuego() throws Exception {
    int databaseSizeBeforeCreate = videojuegoRepository.findAll().size();
    // Create the Videojuego
    restVideojuegoMockMvc
      .perform(
        post(ENTITY_API_URL)
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(videojuego))
      )
      .andExpect(status().isCreated());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeCreate + 1);
    Videojuego testVideojuego = videojuegoList.get(videojuegoList.size() - 1);
    assertThat(testVideojuego.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testVideojuego.getImagenes()).isEqualTo(DEFAULT_IMAGENES);
  }

  @Test
  @Transactional
  void createVideojuegoWithExistingId() throws Exception {
    // Create the Videojuego with an existing ID
    videojuego.setId(1L);

    int databaseSizeBeforeCreate = videojuegoRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restVideojuegoMockMvc
      .perform(
        post(ENTITY_API_URL)
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(videojuego))
      )
      .andExpect(status().isBadRequest());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllVideojuegos() throws Exception {
    // Initialize the database
    videojuegoRepository.saveAndFlush(videojuego);

    // Get all the videojuegoList
    restVideojuegoMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(
        jsonPath("$.[*].id").value(hasItem(videojuego.getId().intValue()))
      )
      .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
      .andExpect(jsonPath("$.[*].imagenes").value(hasItem(DEFAULT_IMAGENES)));
  }

  @Test
  @Transactional
  void getVideojuego() throws Exception {
    // Initialize the database
    videojuegoRepository.saveAndFlush(videojuego);

    // Get the videojuego
    restVideojuegoMockMvc
      .perform(get(ENTITY_API_URL_ID, videojuego.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(videojuego.getId().intValue()))
      .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
      .andExpect(jsonPath("$.imagenes").value(DEFAULT_IMAGENES));
  }

  @Test
  @Transactional
  void getNonExistingVideojuego() throws Exception {
    // Get the videojuego
    restVideojuegoMockMvc
      .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
      .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewVideojuego() throws Exception {
    // Initialize the database
    videojuegoRepository.saveAndFlush(videojuego);

    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();

    // Update the videojuego
    Videojuego updatedVideojuego = videojuegoRepository
      .findById(videojuego.getId())
      .get();
    // Disconnect from session so that the updates on updatedVideojuego are not directly saved in db
    em.detach(updatedVideojuego);
    updatedVideojuego.nombre(UPDATED_NOMBRE).imagenes(UPDATED_IMAGENES);

    restVideojuegoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedVideojuego.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedVideojuego))
      )
      .andExpect(status().isOk());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
    Videojuego testVideojuego = videojuegoList.get(videojuegoList.size() - 1);
    assertThat(testVideojuego.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testVideojuego.getImagenes()).isEqualTo(UPDATED_IMAGENES);
  }

  @Test
  @Transactional
  void putNonExistingVideojuego() throws Exception {
    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();
    videojuego.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restVideojuegoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, videojuego.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(videojuego))
      )
      .andExpect(status().isBadRequest());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchVideojuego() throws Exception {
    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();
    videojuego.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restVideojuegoMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(videojuego))
      )
      .andExpect(status().isBadRequest());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamVideojuego() throws Exception {
    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();
    videojuego.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restVideojuegoMockMvc
      .perform(
        put(ENTITY_API_URL)
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(videojuego))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateVideojuegoWithPatch() throws Exception {
    // Initialize the database
    videojuegoRepository.saveAndFlush(videojuego);

    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();

    // Update the videojuego using partial update
    Videojuego partialUpdatedVideojuego = new Videojuego();
    partialUpdatedVideojuego.setId(videojuego.getId());

    partialUpdatedVideojuego.imagenes(UPDATED_IMAGENES);

    restVideojuegoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedVideojuego.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideojuego))
      )
      .andExpect(status().isOk());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
    Videojuego testVideojuego = videojuegoList.get(videojuegoList.size() - 1);
    assertThat(testVideojuego.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    assertThat(testVideojuego.getImagenes()).isEqualTo(UPDATED_IMAGENES);
  }

  @Test
  @Transactional
  void fullUpdateVideojuegoWithPatch() throws Exception {
    // Initialize the database
    videojuegoRepository.saveAndFlush(videojuego);

    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();

    // Update the videojuego using partial update
    Videojuego partialUpdatedVideojuego = new Videojuego();
    partialUpdatedVideojuego.setId(videojuego.getId());

    partialUpdatedVideojuego.nombre(UPDATED_NOMBRE).imagenes(UPDATED_IMAGENES);

    restVideojuegoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedVideojuego.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVideojuego))
      )
      .andExpect(status().isOk());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
    Videojuego testVideojuego = videojuegoList.get(videojuegoList.size() - 1);
    assertThat(testVideojuego.getNombre()).isEqualTo(UPDATED_NOMBRE);
    assertThat(testVideojuego.getImagenes()).isEqualTo(UPDATED_IMAGENES);
  }

  @Test
  @Transactional
  void patchNonExistingVideojuego() throws Exception {
    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();
    videojuego.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restVideojuegoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, videojuego.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(videojuego))
      )
      .andExpect(status().isBadRequest());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchVideojuego() throws Exception {
    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();
    videojuego.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restVideojuegoMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(videojuego))
      )
      .andExpect(status().isBadRequest());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamVideojuego() throws Exception {
    int databaseSizeBeforeUpdate = videojuegoRepository.findAll().size();
    videojuego.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restVideojuegoMockMvc
      .perform(
        patch(ENTITY_API_URL)
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(videojuego))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the Videojuego in the database
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteVideojuego() throws Exception {
    // Initialize the database
    videojuegoRepository.saveAndFlush(videojuego);

    int databaseSizeBeforeDelete = videojuegoRepository.findAll().size();

    // Delete the videojuego
    restVideojuegoMockMvc
      .perform(
        delete(ENTITY_API_URL_ID, videojuego.getId())
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Videojuego> videojuegoList = videojuegoRepository.findAll();
    assertThat(videojuegoList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
