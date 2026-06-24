package com.genero.genero.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.genero.genero.DTO.GeneroPedidoDTO;
import com.genero.genero.DTO.GeneroRespuestaDTO;
import com.genero.genero.Service.GeneroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService servicio;

    @Operation(
        summary = "Listar todos los géneros",
        description = "Devuelve la lista completa de géneros registrados en el catálogo"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista obtenida correctamente",
        content = @Content(
            mediaType = "application/json",
            examples = @ExampleObject(value = """
                [
                  { "id": 1, "nombre": "RPG", "descripcion": "Juegos de rol" },
                  { "id": 2, "nombre": "Acción", "descripcion": "Juegos de combate" }
                ]
                """)
        )
    )
    @GetMapping
    public ResponseEntity<List<GeneroRespuestaDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicio.obtenerTodos());
    }

    @Operation(
        summary = "Buscar género por ID",
        description = "Devuelve un género específico según su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Género encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "id": 1, "nombre": "RPG", "descripcion": "Juegos de rol" }
                    """)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GeneroRespuestaDTO> obtenerPorId(
            @Parameter(description = "ID del género a buscar", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @Operation(
        summary = "Crear un nuevo género",
        description = "Registra un género nuevo en el catálogo. El nombre debe ser único."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Género creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "id": 3, "nombre": "Aventura", "descripcion": "Juegos narrativos" }
                    """)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en la solicitud"),
        @ApiResponse(responseCode = "409", description = "Ya existe un género con ese nombre")
    })
    @PostMapping
    public ResponseEntity<GeneroRespuestaDTO> crear(@Valid @RequestBody GeneroPedidoDTO pedido) {
        GeneroRespuestaDTO creado = servicio.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
        summary = "Actualizar un género existente",
        description = "Modifica el nombre y/o descripción de un género ya registrado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Género actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GeneroRespuestaDTO> actualizar(
            @Parameter(description = "ID del género a actualizar", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody GeneroPedidoDTO pedido) {
        return ResponseEntity.ok(servicio.actualizar(id, pedido));
    }

    @Operation(
        summary = "Eliminar un género",
        description = "Elimina un género del catálogo de forma permanente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Género eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Género no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del género a eliminar", example = "1")
            @PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
