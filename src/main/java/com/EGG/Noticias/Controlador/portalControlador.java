package com.EGG.Noticias.Controlador;

import com.EGG.Noticias.Entidades.Noticia;
import com.EGG.Noticias.Entidades.Usuario;
import com.EGG.Noticias.MiExcepcion.miExcepcion;
import com.EGG.Noticias.Repositorios.NoticiaRepositorio;
import com.EGG.Noticias.Servicios.UsuarioServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class portalControlador {

    @Autowired
    private UsuarioServicio usuarioService;
    @Autowired
    private NoticiaRepositorio noticiaRepo;

    @GetMapping("/login")
    public String ingresar(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario o contraseña invalidos");
        }
        return "login.html";
    }

    @GetMapping("/")
    public String inicio() {
        return "inicio.html";
    }

    
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_PERIODISTA')")
    @GetMapping("/index")
    public String paginaPrincipal(Model model, HttpSession sesion) {
        Usuario logued = (Usuario) sesion.getAttribute("usuariosesion");
        if (logued.getRol().toString().equals("ADMIN") || logued.getRol().toString().equals("PERIODISTA")) {
        return "redirect:/admin/dashboard";
        } else {
        List<Noticia> listaNoticias = new ArrayList();
        listaNoticias = noticiaRepo.buscarNoticiasDisponibles();
        model.addAttribute("noticias", listaNoticias);
        return "index.html";
        }
        
       
    }

    @GetMapping("/registrar")
    public String registrarUsuario() {
        return "registro.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String pass,
            String pass2, ModelMap modelo) throws miExcepcion {

        try {

            usuarioService.registrar(nombre, email, pass, pass2);

            modelo.put("exito", "Usuario registrado correctamente!");

            return "inicio.html";
        } catch (miExcepcion e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }

    }

     @GetMapping("/registrar_periodista")
    public String registrarPeriodista() {
        return "registro_periodista.html";
    }

    @PostMapping("/registro_periodista")
    public String registroPeriodista(@RequestParam String nombre, @RequestParam String email, @RequestParam String pass,
            String pass2, ModelMap modelo) throws miExcepcion {

        try {

            usuarioService.registrarPeriodista(nombre, email, pass, pass2);

            modelo.put("exito", "Usuario registrado correctamente!");

            return "inicio.html";
        } catch (miExcepcion e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "registro.html";
        }

    }
}
