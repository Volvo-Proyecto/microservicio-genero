package com.genero.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genero.genero.DTO.GeneroPedidoDTO;
import com.genero.genero.DTO.GeneroRespuestaDTO;
import com.genero.genero.Service.GeneroService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService servicio;

    @GetMapping
    public ResponseEntity<?> obtenerTodos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GeneroRespuestaDTO> obtenerporId(Long id){
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }
    @PostMapping("/crear")
    public ResponseEntity<GeneroRespuestaDTO> crear(@RequestBody GeneroPedidoDTO pedido) {
        GeneroRespuestaDTO creado = servicio.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GeneroRespuestaDTO> actualizar(@PathVariable Long id, @RequestBody GeneroPedidoDTO pedido) {
        return ResponseEntity.ok(servicio.actualizar(id, pedido));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    servicio.eliminar(id);
    return ResponseEntity.noContent().build();
    }
}
