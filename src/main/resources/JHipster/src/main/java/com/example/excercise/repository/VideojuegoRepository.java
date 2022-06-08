package com.example.excercise.repository;

import com.example.excercise.domain.Videojuego;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Videojuego entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {}
