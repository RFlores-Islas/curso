package com.curso.curso.dao;

import com.curso.curso.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository//Indica que va a tner la funcionalidad de acceder al repositorio de la base de datos
@Transactional//Hace referencia a la forma en que va tratar las consultas SQL
public class UsuarioDAOImp implements UsuarioDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuario> getUsuario() {
        String query = "FROM Usuario";//usuario: nombre de la clase
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = entityManager.find(Usuario.class,id);
        entityManager.remove(usuario);
    }

    @Override
    public void registrar(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";//EVITA UNA INYECCION SQL
        List<Usuario> lista = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (lista.isEmpty()) {
            return null;
        }

        String passwordHash = lista.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
       // return argon2.verify(passwordHash, usuario.getPassword());//SE INGRESA LA CONTRASEÑA QUE VALIDARA // SE OPTIENE LA CONTRASEÑA INGRESADA
        if (argon2.verify(passwordHash, usuario.getPassword())) {
            return lista.get(0);
        }
        return null;
    }
}
