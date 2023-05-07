package com.EGG.Noticias.Servicios;

import com.EGG.Noticias.Entidades.Periodista;
import com.EGG.Noticias.Entidades.Usuario;
import com.EGG.Noticias.MiExcepcion.miExcepcion;
import com.EGG.Noticias.Repositorios.UsuarioRepositorio;
import com.EGG.Noticias.enumeraciones.Rol;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRep;

    @Transactional
    public void registrar(String nombre, String email, String pass, String pass2) throws miExcepcion {
        
        validacion(nombre, email, pass, pass2);
        
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPass(new BCryptPasswordEncoder().encode(pass));
        usuario.setRol(Rol.USER);

        usuarioRep.save(usuario);
    }

    @Transactional
    public void registrarPeriodista(String nombre, String email, String pass, String pass2) throws miExcepcion {
        
        validacion(nombre, email, pass, pass2);
        
        Periodista periodista = new Periodista();

        periodista.setNombre(nombre);
        periodista.setEmail(email);
        periodista.setPass(new BCryptPasswordEncoder().encode(pass));
        periodista.setRol(Rol.PERIODISTA);

        usuarioRep.save(periodista);
    }
    
    public void validacion(String nombre, String email, String pass, String pass2) throws miExcepcion {
        if (nombre == null || nombre.isEmpty() || nombre.trim().isEmpty()) {
            throw new miExcepcion("El nombre no puede estar vacio.");
        }
        if (email == null || email.isEmpty() || email.trim().isEmpty()) {
            throw new miExcepcion("El email no puede estar vacio.");
        }
        if (pass == null || pass.isEmpty() || pass.trim().isEmpty() || pass.length() <= 5) {
            throw new miExcepcion("La contraseña no puede estar vacia y debe tener más de 5 digitos");
        }      
        if (!pass.equals(pass2)) {
            throw new miExcepcion("Las contraseñas no coinciden.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      
        Usuario usuario = usuarioRep.buscarPorEmail(email);
        
        if (usuario != null) {
        
            List<GrantedAuthority> permisos = new ArrayList();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            
            permisos.add(p);
            
         ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession sesion = attr.getRequest().getSession(true);
            
            sesion.setAttribute("usuariosesion", usuario);
                    
            return new User(usuario.getEmail(), usuario.getPass(), permisos);
        } else {
            return null;
        }
        
    }

}
