package com.edu.pe.application.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.edu.pe.domain.Usuario;
import com.edu.pe.infrastructure.repository.IUsuarioRepository;
import com.edu.pe.web.response.ApiResponse;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;  

    @Autowired
    private PasswordEncoder  passwordEncoder; // Para encriptar la contraseña

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username.trim());

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(usuario.getRol()));  

        return new User(usuario.getCorreo(), usuario.getPassword(), authorities);
    }

    public ApiResponse<Usuario> crearUsuario(Usuario obj) {
        if (usuarioRepository.findByCorreo(obj.getCorreo().trim()) != null) {
            return new ApiResponse<>("El correo electrónico ya está registrado", null, false);  
        } 
        
        String passwordEncriptada = passwordEncoder.encode(obj.getPassword());
        String rolDefault = "ROLE_USER";
        
        obj.setPassword(passwordEncriptada);
        obj.setRol(rolDefault); 
        obj.setEstado(true);

        try {
	        Usuario usuarioGuardado = usuarioRepository.save(obj);
	        
	        if (usuarioGuardado != null) {
	            return new ApiResponse<>("Usuario creado exitosamente", usuarioGuardado); 
	        }
	        return new ApiResponse<>("El usuario no se pudo crear", null, false);  
	    } catch (Exception e) {
	        return new ApiResponse<>("Error al crear usuario", null, false);  
	    }
    }
    
    public Usuario BuscarPorCorreo(String correo) {
    	 Usuario usuario = usuarioRepository.findByCorreo(correo.trim());
    	 return usuario;
    }
}
