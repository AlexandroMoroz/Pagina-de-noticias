 
package com.EGG.Noticias;

import com.EGG.Noticias.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter{
    
    @Autowired
    private UsuarioServicio usuarioService;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(usuarioService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests()                 
                    .antMatchers("/css/*","/js/*","/img/*", "/**")
                    .permitAll()
                    .antMatchers("/periodista/*").hasRole("PERIODISTA")
                    .antMatchers("/admin/*").hasRole("ADMIN")              
                .and()
                   .formLogin()
                   .loginPage("/login")
                   .loginProcessingUrl("/login")
                   .usernameParameter("email")
                   .passwordParameter("pass")
                   .defaultSuccessUrl("/index")
                   .permitAll()
                .and()
                   .logout()
                   .logoutUrl("/logout")
                   .logoutSuccessUrl("/")
                   .permitAll()
                .and()
                   .csrf()
                   .disable();
                        
        
    }
    
    
}
