package com.curso.curso.controllers;

import com.curso.curso.dao.UsuarioDAO;
import com.curso.curso.models.Usuario;
import com.curso.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired//
    private JWTUtil jwtUtil;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario) {
        Usuario usuarioLogueado = usuarioDAO.obtenerUsuarioPorCredenciales(usuario);
        if (usuarioLogueado != null) {
            //CREACION DEL JWT
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getId()), usuarioLogueado.getEmail());
            return tokenJwt;
        }
        return "FAIL";
    }
}
