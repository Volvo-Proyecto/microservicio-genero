package com.genero.genero.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.genero.genero.DTO.GeneroRespuestaDTO;
import com.genero.genero.Model.Genero;
import com.genero.genero.Repository.GeneroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GeneroService {
    private final GeneroRepository repositorio;

    public List<GeneroRespuestaDTO> obtenerTodos(){
        return repositorio.findAll()
        .stream()
        .map(this::mapearRespuesta)
        .collect(Collectors.toList());
    }
    public GeneroRespuestaDTO obtenerPorId(Long id){
        Genero genero = repositorio.findById(id)
        .orElseThrow(()->new RuntimeException("Genero no encontrado con Id"+ id));
        return mapearRespuesta(genero);
    }
}
