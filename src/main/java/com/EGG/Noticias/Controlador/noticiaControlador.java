package com.EGG.Noticias.Controlador;

import com.EGG.Noticias.Entidades.Noticia;
import com.EGG.Noticias.Entidades.Periodista;
import com.EGG.Noticias.MiExcepcion.miExcepcion;
import com.EGG.Noticias.Repositorios.NoticiaRepositorio;
import com.EGG.Noticias.Servicios.noticiaServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noticia")
public class noticiaControlador {

    @Autowired
    private noticiaServicio noticiaService;
    @Autowired
    private NoticiaRepositorio noticiaRep;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/registrar")
    public String formularioNoticia(HttpSession sesion, ModelMap model) {
        Periodista logued = (Periodista) sesion.getAttribute("usuariosesion");
        model.addAttribute("periodistas", logued);
        return "noticia_form.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PERIODISTA')")
    @PostMapping("/registro")
    public String crearNoticia(@RequestParam String titulo, String cuerpo, String id, ModelMap modelo) {
        try {
            noticiaService.crearNoticia(titulo, cuerpo, id);

            return "redirect:/index";
        } catch (miExcepcion e) {
            modelo.put("error", e.getMessage());
            return "noticia_form.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/modificar/{id}")
    public String modificarNoticia(@PathVariable String id, ModelMap modelo) {

        modelo.put("noticia", noticiaService.getOne(id));
        return "noticia_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PERIODISTA')")
    @PostMapping("/modificado/{id}")
    public String modificadoNoticia(@PathVariable String id, String titulo, String cuerpo, ModelMap modelo) {
        try {
            noticiaService.modificarNoticia(titulo, cuerpo, id);
            return "redirect:/index";

        } catch (miExcepcion e) {

            modelo.put("error", e.getMessage());
            modelo.put("noticia", noticiaService.getOne(id));
            return "noticia_form.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarNoticia(@PathVariable String id, ModelMap modelo) {
        try {
            noticiaService.eliminarNoticia(id);
            return "redirect:/index";
        } catch (miExcepcion e) {
            modelo.put("error", e.getMessage());
            return "redirect:/index";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/mostrar/{id}")
    public String mostrarNoticia(@PathVariable String id, Model modelo) {
        List<Noticia> noticia = new ArrayList();
        noticia = noticiaRep.buscarNoticia(id);
        modelo.addAttribute("noticia", noticia);
        return "noticia_mostrar.html";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/noticias_periodista/{id}")
    public String listarNoticias(@PathVariable String id, ModelMap model) {
        List<Noticia> listaNoticias = new ArrayList();
        listaNoticias = noticiaRep.listarNoticias(id);
        model.addAttribute("noticias", listaNoticias);
        return "list_noticias.html";
    }
}
