package com.curso.curso.dao;

import com.curso.curso.models.Usuario;

import java.util.List;

public interface UsuarioDAO {
    List<Usuario> getUsuario();

    void eliminar(Long id);

    void registrar(Usuario usuario);

    Usuario obtenerUsuarioPorCredenciales(Usuario usuario);
}
