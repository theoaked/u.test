package br.senai.sp.informatica.tcc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.sp.informatica.tcc.model.Questao;

@Repository
public class QuestaoDao implements InterfaceDao<Questao> {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Questao objeto) {
		manager.persist(objeto);
	}

	@Override
	public void alterar(Questao objeto) {
		manager.merge(objeto);
	}

	@Override
	public void excluir(long id) {
		Questao q = manager.find(Questao.class, id);
		manager.remove(q);

	}

	@Override
	public Questao buscar(long id) {
		return manager.find(Questao.class, id);
	}

	@Override
	public List<Questao> getLista() {
		TypedQuery<Questao> query = manager.createQuery("select q from Questao q", Questao.class);
		return query.getResultList();
	}
	
	public List<Questao> getQuestoesById(long id) {
		TypedQuery<Questao> query = manager.createQuery("select q from Questao q where q.professor.id=" + id, Questao.class);
		return query.getResultList();
	}
	
	public List<Questao> getQuestoesAtivas() {
		TypedQuery<Questao> query = manager.createQuery("select q from Questao q where q.ativa=TRUE", Questao.class);
		return query.getResultList();
	}
	
	public Questao getQuestao(long id) {
		TypedQuery<Questao> query = manager.createQuery("select q from Questao q where q.id=" + id, Questao.class);
		return query.getSingleResult();
	}
	
	public List<Questao> getQuestaoAleatoria(long id, int limite) {
		TypedQuery<Questao> query = manager.createQuery("select q from Questao q where q.area.id = :id and q.ativa=TRUE order by rand()", Questao.class).setMaxResults(limite);
		query.setParameter("id", id);
		return query.getResultList();
	}

}
