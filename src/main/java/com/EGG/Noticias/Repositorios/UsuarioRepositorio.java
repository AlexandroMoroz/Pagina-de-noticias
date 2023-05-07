
package com.EGG.Noticias.Repositorios;

import com.EGG.Noticias.Entidades.Periodista;
import com.EGG.Noticias.Entidades.Usuario;
import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
    
    @Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario buscarPorEmail(@Param("email") String email);
    
    @Query("SELECT p FROM Periodista p WHERE p.baja IS NULL")
    public ArrayList<Periodista> listarPeriodistas();
}
