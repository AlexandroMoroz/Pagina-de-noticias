
package com.EGG.Noticias.Repositorios;

import com.EGG.Noticias.Entidades.Noticia;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia, String>{
           
    @Query("SELECT n.baja FROM Noticia n WHERE n.id = :id")
    public Date buscarBaja(@Param("id") String id);
       
    @Query("SELECT n FROM Noticia n WHERE n.baja IS NULL")
    public List<Noticia> buscarNoticiasDisponibles();
    
    @Query("SELECT n FROM Noticia n WHERE n.id = :id")
    public List<Noticia> buscarNoticia(@Param("id") String id);
    
    @Query("SELECT n FROM Noticia n WHERE n.baja IS NULL AND n.periodista.id= :id")
    public List<Noticia> listarNoticias(@Param("id")String id);
    

}
