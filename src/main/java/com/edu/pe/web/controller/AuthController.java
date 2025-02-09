package com.edu.pe.web.controller;

import com.edu.pe.application.service.UsuarioService;
import com.edu.pe.domain.Usuario;
import com.edu.pe.security.JwtRequest;
import com.edu.pe.security.JwtResponse;
import com.edu.pe.security.JwtTokenUtil;
import com.edu.pe.web.response.ApiResponse;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtUtils;

	@PostMapping("/registro")
	public ResponseEntity<?> crearUsuario(@RequestBody @Validated Usuario usuario) {
		ApiResponse<Usuario> response = usuarioService.crearUsuario(usuario);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	 @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody JwtRequest obj) throws Exception {
	        try {
	        	System.out.println("Param Login: "+ obj.getUsername()+" - " + obj.getPassword());
	            autenticar(obj.getUsername(), obj.getPassword());
	            UserDetails userDetails = usuarioService.loadUserByUsername(obj.getUsername());

	            String token = this.jwtUtils.generateToken(userDetails);
	            Date expiracion = this.jwtUtils.fechaCreateToken();

	            Usuario objUsu= usuarioService.BuscarPorCorreo(obj.getUsername());
	            objUsu.setPassword("");
	            
	            return ResponseEntity.ok(new JwtResponse(objUsu,
	                    token, expiracion));

	        } catch (BadCredentialsException e) {
	            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas", e);
	        }
	    }
	 
	  private void autenticar(String username, String password) throws Exception {
	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	        } catch (DisabledException ex) {
	        	throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Usuario Deshabilitado: " + ex.getMessage());
	        } 
	    }
}
