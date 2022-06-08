package com.example.excercise.service;


import com.example.excercise.dto.VideojuegoDto;
import com.example.excercise.entities.Videojuego;
import com.example.excercise.repository.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//mapper quita set, inyeccion por constructor.

@Service
public class VideojuegoService implements IVideojuegoService {

    @Autowired
    VideojuegoRepository videojuegoRepository;

    @Override
    public List<VideojuegoDto> getVideojuegos() {
        List<VideojuegoDto> videojuegoDtoList = new ArrayList<>();
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        for (Videojuego vj : videojuegoList) {
           VideojuegoDto videoAdded = new VideojuegoDto();
            videoAdded.setId((vj.getId()));
            videoAdded.setNombre(vj.getNombre());
            videoAdded.setImagenes(vj.getImagenes());
            videojuegoDtoList.add(videoAdded);

        }
        return videojuegoDtoList;
    }

    @Override
    public VideojuegoDto addVideojuego(VideojuegoDto body) {

        Videojuego vjAdded = new Videojuego();
        vjAdded.setId(body.getId());
        vjAdded.setNombre(body.getNombre());
        vjAdded.setImagenes(body.getImagenes());

        VideojuegoDto videojuegoDto = new VideojuegoDto();
        videojuegoDto.setId(vjAdded.getId());
        videojuegoDto.setNombre(vjAdded.getNombre());
        videojuegoDto.setImagenes(vjAdded.getImagenes());
        videojuegoRepository.save(vjAdded);

        return videojuegoDto;
    }

    @Override
    public VideojuegoDto getVideojuegoById(Long id) {
        VideojuegoDto vjRequested = new VideojuegoDto();
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        for (Videojuego vj : videojuegoList) {
            if (vj.getId().equals(id)) {
                vjRequested.setId(vj.getId());
                vjRequested.setNombre(vj.getNombre());
                vjRequested.setImagenes(vj.getImagenes());
            }
        }
        return vjRequested;
    }

    @Override
    public VideojuegoDto updateVideojuegoWithForm(Long videojuegoId, String nombre) {
        VideojuegoDto vjGotten= new VideojuegoDto();
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        for (Videojuego vj : videojuegoList) {
            if (vj.getId().equals(videojuegoId)) {
                vjGotten.setId(vj.getId());
                vjGotten.setNombre(nombre);
                vjGotten.setImagenes(vj.getImagenes());

                Videojuego videoAdded = new Videojuego();
                videoAdded.setId(vjGotten.getId());
                videoAdded.setNombre(vjGotten.getNombre());
                videoAdded.setImagenes(vjGotten.getImagenes());

                videojuegoRepository.save(videoAdded);
            }
        }
        return vjGotten;
    }

    @Override
    public VideojuegoDto deleteVideojuego(Long id, String apiKey) {
        VideojuegoDto vjDeleted = new VideojuegoDto();
        List<Videojuego> videojuegoList = videojuegoRepository.findAll();
        for (Videojuego vj : videojuegoList) {
            if (vj.getId().equals(id)) {
                vjDeleted.setId(vj.getId());
                vjDeleted.setNombre(vj.getNombre());
                vjDeleted.setImagenes(vj.getImagenes());
                videojuegoRepository.delete(vj);
            }
        }
        return vjDeleted;
    }
}
