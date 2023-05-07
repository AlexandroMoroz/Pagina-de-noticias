package com.EGG.Noticias.Repositorios;

import com.EGG.Noticias.Entidades.Periodista;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface PeriodistaRepositorio extends JpaRepository<Periodista, String> {

    @Query("SELECT p FROM Periodista p WHERE p.email= :email")
    public Periodista buscarPorEmail(@Param("email")String email);
            
            
            
    
}
