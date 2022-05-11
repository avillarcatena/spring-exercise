package com.example.excercise.repository;

import com.example.excercise.entities.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideojuegoRepository extends JpaRepository<Videojuego,Long> {
}
