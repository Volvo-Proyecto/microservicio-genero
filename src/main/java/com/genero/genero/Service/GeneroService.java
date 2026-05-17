package com.genero.genero.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.genero.genero.DTO.GeneroPedidoDTO;
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
    public GeneroRespuestaDTO crear(GeneroPedidoDTO pedido) {

        //***Verificar que no exista ya un género con ese nombre
        if (repositorio.findByNombre(pedido.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe un género con el nombre: " + pedido.getNombre());
        }

        Genero genero = new Genero();
        genero.setNombre(pedido.getNombre());
        genero.setDescripcion(pedido.getDescripcion());

        Genero guardado = repositorio.save(genero);
        return mapearRespuesta(guardado);
    }

    // ****Actualizar 
    public GeneroRespuestaDTO actualizar(Long id, GeneroPedidoDTO pedido) {
        Genero genero = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Género no encontrado con ID: " + id));

        genero.setNombre(pedido.getNombre());
        genero.setDescripcion(pedido.getDescripcion());

        Genero actualizado = repositorio.save(genero);
        return mapearRespuesta(actualizado);
    }

    // ****Eliminar 
    public void eliminar(Long id) {
        if (!repositorio.existsById(id)) {
            throw new RuntimeException("Género no encontrado con ID: " + id);
        }
        repositorio.deleteById(id);
    }

    // Código privado para mapear entre entidad y DTO
    private GeneroRespuestaDTO mapearRespuesta(Genero genero) {
        GeneroRespuestaDTO respuesta = new GeneroRespuestaDTO();
        respuesta.setId(genero.getId());
        respuesta.setNombre(genero.getNombre());
        respuesta.setDescripcion(genero.getDescripcion());
        return respuesta;
    }
}

