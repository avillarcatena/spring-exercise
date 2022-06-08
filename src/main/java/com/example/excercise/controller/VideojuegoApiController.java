package com.example.excercise.controller;
//juanjo
import com.example.excercise.api.VideojuegoApi_;
import com.example.excercise.dto.VideojuegoDto;
import com.example.excercise.service.VideojuegoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VideojuegoApiController implements VideojuegoApi_ {

    VideojuegoService videojuegoService;

    @Override
    public ResponseEntity<VideojuegoDto> addVideojuego(VideojuegoDto body) {

        return new ResponseEntity<VideojuegoDto>(videojuegoService.addVideojuego(body), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteVideojuego(Long videojuegoId, String apiKey) {
        return null;
    }

    @Override
    public ResponseEntity<VideojuegoDto> getVideojuegoById(Long videojuegoId) {
        return null;
    }

    @Override
    @GetMapping("/videojuego")
    public ResponseEntity<List<VideojuegoDto>> getVideojuegos() {

        return new ResponseEntity<List<VideojuegoDto>>(videojuegoService.getVideojuegos(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateVideojuegoWithForm(Long videojuegoId, String nombre) {
        return null;
    }
}
