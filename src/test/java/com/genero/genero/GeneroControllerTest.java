package com.genero.genero;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genero.genero.Controller.GeneroController;
import com.genero.genero.DTO.GeneroPedidoDTO;
import com.genero.genero.DTO.GeneroRespuestaDTO;
import com.genero.genero.Service.GeneroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GeneroController.class)
class GeneroControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private GeneroService generoService; 

    @Autowired
    private ObjectMapper objectMapper; 

    private GeneroRespuestaDTO generoRespuesta;
    private GeneroPedidoDTO generoPedido;

    
    @BeforeEach
    void setUp() {
        
        generoRespuesta = new GeneroRespuestaDTO();
        generoRespuesta.setId(1L);
        generoRespuesta.setNombre("RPG");
        generoRespuesta.setDescripcion("Juegos de rol"); 
        
        
        generoPedido = new GeneroPedidoDTO();
        generoPedido.setNombre("RPG");
        generoPedido.setDescripcion("Juegos de rol"); 
    }

    @Test
    void deberiaObtenerTodosLosGeneros() throws Exception {
        List<GeneroRespuestaDTO> listaGeneros = Arrays.asList(generoRespuesta);
        when(generoService.obtenerTodos()).thenReturn(listaGeneros);

        mockMvc.perform(get("/api/v1/generos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("RPG"));
    }

    @Test
    void deberiaObtenerGeneroPorId() throws Exception {
        when(generoService.obtenerPorId(1L)).thenReturn(generoRespuesta);

        mockMvc.perform(get("/api/v1/generos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("RPG"));
    }

    @Test
    void deberiaCrearGeneroCorrectamente() throws Exception {
        when(generoService.crear(any(GeneroPedidoDTO.class))).thenReturn(generoRespuesta);

        mockMvc.perform(post("/api/v1/generos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generoPedido)))
                .andExpect(status().isCreated()) 
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("RPG"));
    }

    @Test
    void deberiaActualizarGeneroExistente() throws Exception {
        when(generoService.actualizar(eq(1L), any(GeneroPedidoDTO.class))).thenReturn(generoRespuesta);

        mockMvc.perform(put("/api/v1/generos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(generoPedido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("RPG"));
    }

    @Test
    void deberiaEliminarGeneroExistente() throws Exception {
        doNothing().when(generoService).eliminar(1L);

        mockMvc.perform(delete("/api/v1/generos/{id}", 1L))
                .andExpect(status().isNoContent()); 
    }
}