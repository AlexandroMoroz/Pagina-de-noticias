package com.EGG.Noticias.Servicios;


import com.EGG.Noticias.Entidades.Noticia;
import com.EGG.Noticias.Entidades.Periodista;
import com.EGG.Noticias.MiExcepcion.miExcepcion;
import com.EGG.Noticias.Repositorios.PeriodistaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class periodistaServicio{

    @Autowired
    private PeriodistaRepositorio periodistaRep;

    @Transactional
    public void ingresarNoticia(@RequestParam ArrayList<Noticia> noticia, @RequestParam String id) {
        Periodista periodista = new Periodista();
        Optional<Periodista> result = periodistaRep.findById(id);
        if (result != null) {
            periodista = result.get();
            periodista.setNoticia(noticia);
            periodistaRep.save(periodista);
        }

    }

    @Transactional
    public void ingresarSalario(@RequestParam Integer sueldoMensual, @RequestParam String id) throws miExcepcion {
        
            validar(sueldoMensual);
            
            Periodista periodista = new Periodista();
            Optional<Periodista> result = periodistaRep.findById(id);
            if (result != null) {
                periodista = result.get();
                periodista.setSueldoMensual(sueldoMensual);
                periodistaRep.save(periodista);
            }       
    }

    public void validar(Integer sueldoMensual) throws miExcepcion{
        if (sueldoMensual.equals(null) || sueldoMensual.toString().isEmpty() || sueldoMensual.toString().trim().isEmpty()) {
            throw new miExcepcion("Error, el sueldo no puede estar vacio");
        }       
    }
    
    @Transactional
    public void bajaPeriodista(@RequestParam String id){
        Optional<Periodista> resp = periodistaRep.findById(id);
        Periodista periodista = new Periodista();
        if (resp != null) {
            periodista = resp.get();
            periodista.setBaja(new Date());
            periodistaRep.save(periodista);
        }
    }  
}
