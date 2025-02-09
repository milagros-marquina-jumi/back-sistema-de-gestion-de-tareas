package com.edu.pe.security;

import java.util.Date;

public class JwtResponse  {

    private Object data;
    private String token;
    private Date expiracion;

    public JwtResponse(Object data, String token, Date expiracion) {
        this.data = data;
        this.token = token;
        this.expiracion = expiracion;
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(Date expiracion) {
        this.expiracion = expiracion;
    }

}
