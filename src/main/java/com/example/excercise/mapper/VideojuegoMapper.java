package com.example.excercise.mapper;

import com.example.excercise.dto.VideojuegoDto;
import com.example.excercise.entities.Videojuego;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VideojuegoMapper {

    VideojuegoMapper INSTANCIA= Mappers.getMapper(VideojuegoMapper.class);

    VideojuegoDto toDto(Videojuego target);

    Videojuego toEntity(VideojuegoDto source);

}

