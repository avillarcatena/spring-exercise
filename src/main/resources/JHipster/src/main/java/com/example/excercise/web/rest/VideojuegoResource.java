package com.example.excercise.web.rest;

import com.example.excercise.domain.Videojuego;
import com.example.excercise.repository.VideojuegoRepository;
import com.example.excercise.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.example.excercise.domain.Videojuego}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VideojuegoResource {

  private final Logger log = LoggerFactory.getLogger(VideojuegoResource.class);

  private static final String ENTITY_NAME = "springexcerciseVideojuego";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final VideojuegoRepository videojuegoRepository;

  public VideojuegoResource(VideojuegoRepository videojuegoRepository) {
    this.videojuegoRepository = videojuegoRepository;
  }

  /**
   * {@code POST  /videojuegos} : Create a new videojuego.
   *
   * @param videojuego the videojuego to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videojuego, or with status {@code 400 (Bad Request)} if the videojuego has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/videojuegos")
  public ResponseEntity<Videojuego> createVideojuego(
    @RequestBody Videojuego videojuego
  ) throws URISyntaxException {
    log.debug("REST request to save Videojuego : {}", videojuego);
    if (videojuego.getId() != null) {
      throw new BadRequestAlertException(
        "A new videojuego cannot already have an ID",
        ENTITY_NAME,
        "idexists"
      );
    }
    Videojuego result = videojuegoRepository.save(videojuego);
    return ResponseEntity
      .created(new URI("/api/videojuegos/" + result.getId()))
      .headers(
        HeaderUtil.createEntityCreationAlert(
          applicationName,
          false,
          ENTITY_NAME,
          result.getId().toString()
        )
      )
      .body(result);
  }

  /**
   * {@code PUT  /videojuegos/:id} : Updates an existing videojuego.
   *
   * @param id the id of the videojuego to save.
   * @param videojuego the videojuego to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videojuego,
   * or with status {@code 400 (Bad Request)} if the videojuego is not valid,
   * or with status {@code 500 (Internal Server Error)} if the videojuego couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/videojuegos/{id}")
  public ResponseEntity<Videojuego> updateVideojuego(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody Videojuego videojuego
  ) throws URISyntaxException {
    log.debug("REST request to update Videojuego : {}, {}", id, videojuego);
    if (videojuego.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, videojuego.getId())) {
      throw new BadRequestAlertException(
        "Invalid ID",
        ENTITY_NAME,
        "idinvalid"
      );
    }

    if (!videojuegoRepository.existsById(id)) {
      throw new BadRequestAlertException(
        "Entity not found",
        ENTITY_NAME,
        "idnotfound"
      );
    }

    Videojuego result = videojuegoRepository.save(videojuego);
    return ResponseEntity
      .ok()
      .headers(
        HeaderUtil.createEntityUpdateAlert(
          applicationName,
          false,
          ENTITY_NAME,
          videojuego.getId().toString()
        )
      )
      .body(result);
  }

  /**
   * {@code PATCH  /videojuegos/:id} : Partial updates given fields of an existing videojuego, field will ignore if it is null
   *
   * @param id the id of the videojuego to save.
   * @param videojuego the videojuego to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videojuego,
   * or with status {@code 400 (Bad Request)} if the videojuego is not valid,
   * or with status {@code 404 (Not Found)} if the videojuego is not found,
   * or with status {@code 500 (Internal Server Error)} if the videojuego couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PatchMapping(
    value = "/videojuegos/{id}",
    consumes = { "application/json", "application/merge-patch+json" }
  )
  public ResponseEntity<Videojuego> partialUpdateVideojuego(
    @PathVariable(value = "id", required = false) final Long id,
    @RequestBody Videojuego videojuego
  ) throws URISyntaxException {
    log.debug(
      "REST request to partial update Videojuego partially : {}, {}",
      id,
      videojuego
    );
    if (videojuego.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, videojuego.getId())) {
      throw new BadRequestAlertException(
        "Invalid ID",
        ENTITY_NAME,
        "idinvalid"
      );
    }

    if (!videojuegoRepository.existsById(id)) {
      throw new BadRequestAlertException(
        "Entity not found",
        ENTITY_NAME,
        "idnotfound"
      );
    }

    Optional<Videojuego> result = videojuegoRepository
      .findById(videojuego.getId())
      .map(existingVideojuego -> {
        if (videojuego.getNombre() != null) {
          existingVideojuego.setNombre(videojuego.getNombre());
        }
        if (videojuego.getImagenes() != null) {
          existingVideojuego.setImagenes(videojuego.getImagenes());
        }

        return existingVideojuego;
      })
      .map(videojuegoRepository::save);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(
        applicationName,
        false,
        ENTITY_NAME,
        videojuego.getId().toString()
      )
    );
  }

  /**
   * {@code GET  /videojuegos} : get all the videojuegos.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videojuegos in body.
   */
  @GetMapping("/videojuegos")
  public List<Videojuego> getAllVideojuegos() {
    log.debug("REST request to get all Videojuegos");
    return videojuegoRepository.findAll();
  }

  /**
   * {@code GET  /videojuegos/:id} : get the "id" videojuego.
   *
   * @param id the id of the videojuego to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videojuego, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/videojuegos/{id}")
  public ResponseEntity<Videojuego> getVideojuego(@PathVariable Long id) {
    log.debug("REST request to get Videojuego : {}", id);
    Optional<Videojuego> videojuego = videojuegoRepository.findById(id);
    return ResponseUtil.wrapOrNotFound(videojuego);
  }

  /**
   * {@code DELETE  /videojuegos/:id} : delete the "id" videojuego.
   *
   * @param id the id of the videojuego to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/videojuegos/{id}")
  public ResponseEntity<Void> deleteVideojuego(@PathVariable Long id) {
    log.debug("REST request to delete Videojuego : {}", id);
    videojuegoRepository.deleteById(id);
    return ResponseEntity
      .noContent()
      .headers(
        HeaderUtil.createEntityDeletionAlert(
          applicationName,
          false,
          ENTITY_NAME,
          id.toString()
        )
      )
      .build();
  }
}
