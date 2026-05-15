package com.genero.genero.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genero.genero.Model.Genero;

public interface GeneroRepository extends JpaRepository<Genero, Long>{
    Optional<Genero> findfindByNombre(String nombre);

    java.util.List<Genero> findfindByNombreContainingIgnoreCase(String nombre);
}
