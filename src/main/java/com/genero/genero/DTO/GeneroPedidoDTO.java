package com.genero.genero.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GeneroPedidoDTO {
    @NotBlank(message="el nombre del genero no puede estar vacio")
    @Size(max =100, message = "el nombre no puede superar los 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "la descripcion no puede superar los 500 caracteres")
    private String descripcion;

}
