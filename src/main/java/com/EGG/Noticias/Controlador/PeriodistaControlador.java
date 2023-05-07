package com.EGG.Noticias.Controlador;

import com.EGG.Noticias.MiExcepcion.miExcepcion;
import com.EGG.Noticias.Servicios.periodistaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/periodista")
public class PeriodistaControlador {

    @Autowired
    private periodistaServicio periodistaService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{id}")
    public String eliminarPeriodista(@PathVariable String id) {
        periodistaService.bajaPeriodista(id);
        return "redirect:/admin/periodistas";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modificar/{id}")
    public String modificarSalario(@PathVariable String id, Integer sueldoMensual, ModelMap modelo) throws miExcepcion {
        try {
            periodistaService.ingresarSalario(sueldoMensual, id);
            return "redirect:/admin/periodistas";
        } catch (miExcepcion e) {
            modelo.put("error", e.getMessage());
            return "redirect:/admin/periodistas";
        }

    }
}
