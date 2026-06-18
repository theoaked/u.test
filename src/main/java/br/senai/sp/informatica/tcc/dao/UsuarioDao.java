
package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Usuario;

@Repository
public class UsuarioDao implements InterfaceDao<Usuario> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Usuario objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Usuario objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Usuario u = manager.find(Usuario.class, id);
		manager.remove(u);

	}

	@Override
	public Usuario buscar(long id) {
		return manager.find(Usuario.class, id);
	}

	@Override
	public List<Usuario> getLista() {
		TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u", Usuario.class);
		return query.getResultList();
	}
	
	public Usuario logar(String login, String senha, int tipo) {
		TypedQuery<Usuario> query = manager.createQuery(
				"select u from Usuario u where u.login = :login and u.senha = :senha and u.tipoUsuario = :tipoUsuario",
				Usuario.class);
		query.setParameter("login", login);
		query.setParameter("senha", senha);
		query.setParameter("tipoUsuario", tipo);
		if (query.getResultList().size() != 0) {
			return query.getResultList().get(0);
		} else {
			return null;
		}
	}

}
