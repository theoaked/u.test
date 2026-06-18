package br.senai.sp.informatica.tcc.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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
		// O original usava "order by rand()" (sintaxe MySQL, invalida em JPQL/PostgreSQL).
		// Mantemos o mesmo comportamento - N questoes ativas aleatorias da area -
		// embaralhando em memoria, de forma independente do dialeto do banco.
		TypedQuery<Questao> query = manager.createQuery(
				"select q from Questao q where q.area.id = :id and q.ativa=TRUE", Questao.class);
		query.setParameter("id", id);
		List<Questao> questoes = new ArrayList<>(query.getResultList());
		Collections.shuffle(questoes);
		if (questoes.size() > limite) {
			return new ArrayList<>(questoes.subList(0, limite));
		}
		return questoes;
	}

}
