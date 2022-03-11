package com.curso.curso.controllers;

import com.curso.curso.dao.UsuarioDAO;
import com.curso.curso.models.Usuario;
import com.curso.curso.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;//INYECCION DE DEPENDENCIAS

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value="Authorization") String token) {
        if (!validarToken(token)) {
            return null;
        }

        return usuarioDAO.getUsuario();//INTERFAZ CREADA
    }

    private boolean validarToken(String token) {
        //VERIFICACION DEL TOKEN
        String usuarioId = jwtUtil.getKey(token); //DEVUELVE EL ID DE USUARIO
        return usuarioId != null;
    }


    @RequestMapping(value = "usuarios", method = RequestMethod.POST)
    public void registrarUsuarios(@RequestBody Usuario usuario) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());//1: Indica el numero de ciclos que se da a la encriptacion
        //1024: indica el tamaño de memoria que se almacena para el proceso de enciptacion
        //1: Indica el numero de hilos que tendra
        //getPassword() obtiene la contraseña
        usuario.setPassword(hash);
        usuarioDAO.registrar(usuario);
    }

    @RequestMapping(value = "usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value="Authorization") String token, @PathVariable Long id) {
        if (!validarToken(token)) {
            return;
        }
        usuarioDAO.eliminar(id);
    }


}
