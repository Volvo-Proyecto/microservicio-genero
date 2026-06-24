package com.genero.genero;

import com.genero.genero.DTO.GeneroPedidoDTO;
import com.genero.genero.DTO.GeneroRespuestaDTO;
import com.genero.genero.Model.Genero;
import com.genero.genero.Repository.GeneroRepository;
import com.genero.genero.Service.GeneroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) 
class GeneroServiceTest {

    @Mock
    private GeneroRepository repositorio; 

    @InjectMocks
    private GeneroService servicio; 

    private Genero genero;
    private GeneroPedidoDTO pedido;

    @BeforeEach 
    void setUp() {
        genero = new Genero();
        genero.setId(1L);
        genero.setNombre("RPG");
        genero.setDescripcion("Juegos de rol");

        pedido = new GeneroPedidoDTO();
        pedido.setNombre("RPG");
        pedido.setDescripcion("Juegos de rol");
    }

    @Test
    void deberiaObtenerTodosLosGeneros() {
        
        when(repositorio.findAll()).thenReturn(List.of(genero));

       
        List<GeneroRespuestaDTO> resultado = servicio.obtenerTodos();

        
        assertEquals(1, resultado.size());
        assertEquals("RPG", resultado.get(0).getNombre());
        verify(repositorio, times(1)).findAll(); 
    }

    @Test
    void deberiaObtenerGeneroPorId() {
        
        when(repositorio.findById(1L)).thenReturn(Optional.of(genero));

        
        GeneroRespuestaDTO resultado = servicio.obtenerPorId(1L);

        
        assertNotNull(resultado);
        assertEquals("RPG", resultado.getNombre());
        assertEquals(1L, resultado.getId());
    }

    @Test
    void deberiaLanzarExcepcionSiGeneroNoExiste() {
        
        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        
        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> servicio.obtenerPorId(99L)
        );
        assertTrue(ex.getMessage().contains("no encontrado"));
    }

    @Test
    void deberiaCrearGeneroCorrectamente() {
        // Given
        when(repositorio.findByNombre("RPG")).thenReturn(Optional.empty()); 
        when(repositorio.save(any(Genero.class))).thenReturn(genero);

        
        GeneroRespuestaDTO resultado = servicio.crear(pedido);

        
        assertNotNull(resultado);
        assertEquals("RPG", resultado.getNombre());
        verify(repositorio, times(1)).save(any(Genero.class));
    }

    @Test
    void noDeberiaCrearGeneroDuplicado() {
        
        when(repositorio.findByNombre("RPG")).thenReturn(Optional.of(genero));

        
        RuntimeException ex = assertThrows(
            RuntimeException.class,
            () -> servicio.crear(pedido)
        );
        assertTrue(ex.getMessage().contains("Ya existe"));
        verify(repositorio, never()).save(any()); 
    }

    @Test
    void deberiaActualizarGeneroExistente() {
        // Given
        GeneroPedidoDTO pedidoActualizado = new GeneroPedidoDTO();
        pedidoActualizado.setNombre("RPG Táctico");
        pedidoActualizado.setDescripcion("Subgénero del RPG");

        when(repositorio.findById(1L)).thenReturn(Optional.of(genero));
        when(repositorio.save(any(Genero.class))).thenReturn(genero);

        // When
        GeneroRespuestaDTO resultado = servicio.actualizar(1L, pedidoActualizado);

        // Then
        assertNotNull(resultado);
        verify(repositorio, times(1)).save(genero);
    }

    @Test
    void deberiaEliminarGeneroExistente() {

        when(repositorio.existsById(1L)).thenReturn(true);

        servicio.eliminar(1L);

        verify(repositorio, times(1)).deleteById(1L);
    }

    @Test
    void noDeberiaEliminarGeneroInexistente() {
        when(repositorio.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> servicio.eliminar(99L));
        verify(repositorio, never()).deleteById(any()); 
    }
}