package com.EGG.Noticias.Controlador;

import com.EGG.Noticias.Entidades.Noticia;
import com.EGG.Noticias.Entidades.Periodista;
import com.EGG.Noticias.Entidades.Usuario;
import com.EGG.Noticias.Repositorios.NoticiaRepositorio;
import com.EGG.Noticias.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private NoticiaRepositorio noticiaRepo;
    
    @Autowired
    private UsuarioRepositorio usuarioRep;    
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/dashboard")
    public String panelAdministrativo(Model model) {
        List<Noticia> listaNoticias = new ArrayList();
        listaNoticias = noticiaRepo.buscarNoticiasDisponibles();
        model.addAttribute("noticias", listaNoticias);
        return "index_admin.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/periodistas")
    public String listaPeriodistas(Model model){
        List<Periodista> periodistas = new ArrayList();
        periodistas = usuarioRep.listarPeriodistas();
        model.addAttribute("periodistas", periodistas);
        return "tabla_periodistas.html";
    }
    
}
