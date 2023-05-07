package com.EGG.Noticias.Servicios;

import com.EGG.Noticias.Entidades.Noticia;
import com.EGG.Noticias.Entidades.Periodista;
import com.EGG.Noticias.MiExcepcion.miExcepcion;
import com.EGG.Noticias.Repositorios.NoticiaRepositorio;
import com.EGG.Noticias.Repositorios.PeriodistaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class noticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    @Autowired
    private PeriodistaRepositorio periodistaRep;

    @Transactional
    public void crearNoticia(String titulo, String cuerpo, String id) throws miExcepcion {
        validar(titulo, cuerpo);

        Optional<Periodista> resp = periodistaRep.findById(id);

        if (resp != null) {
            Periodista periodista = resp.get();
            if (periodista.getBaja() == null) {
                Noticia noticia = new Noticia();
                noticia.setCuerpo(cuerpo);
                noticia.setTitulo(titulo);
                noticia.setPeriodista(periodista);
                noticiaRepositorio.save(noticia);
            } else {
                throw new miExcepcion("error, no tiene los permisos para realizar esta acción.");
            }

        }
    }

    public void validar(String titulo, String cuerpo) throws miExcepcion {
        if (titulo == null || titulo.isEmpty() || titulo.trim().isEmpty()) {
            throw new miExcepcion("Error, el título no puede ser nulo o vacio.");
        }
        if (cuerpo == null || cuerpo.isEmpty() || cuerpo.length() > 6000 || cuerpo.trim().isEmpty()) {
            throw new miExcepcion("Error, el cuerpo de la noticia no puede ser nulo, estar vacio o superar el límite");
        }
    }

    public List<Noticia> listarNoticias() {
        List<Noticia> noticias = new ArrayList<>();
        noticias = noticiaRepositorio.findAll();
        return noticias;
    }

    @Transactional
    public void modificarNoticia(String titulo, String cuerpo, String id) throws miExcepcion {

        validar(titulo, cuerpo);

        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);

        Noticia noticia = new Noticia();
        if (respuesta != null) {
            noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticiaRepositorio.save(noticia);
        }
    }

    @Transactional(readOnly = true)
    public Noticia getOne(String id) {
        return noticiaRepositorio.getOne(id);
    }

    @Transactional
    public void eliminarNoticia(String id) throws miExcepcion {

        Optional<Noticia> respuesta = noticiaRepositorio.findById(id);

        Noticia noticia = new Noticia();

        if (respuesta != null) {
            noticia = respuesta.get();
            noticia.setBaja(new Date());
            noticiaRepositorio.save(noticia);
        }
    }

}
