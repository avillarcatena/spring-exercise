package com.example.excercise.service;


import com.example.excercise.dto.VideojuegoDto;

import java.util.List;

public interface IVideojuegoService {

    List<VideojuegoDto> getVideojuegos();
    VideojuegoDto addVideojuego(VideojuegoDto body);
    VideojuegoDto getVideojuegoById(Long id);
    VideojuegoDto updateVideojuegoWithForm(Long videojuegoId, String nombre);
    VideojuegoDto deleteVideojuego(Long id,  String apiKey);


}
