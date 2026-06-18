package br.senai.sp.informatica.tcc.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Gabarito {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Aluno aluno;
	@ManyToOne
	private Evento evento;
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Alternativa> alternativas;
	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private List<Resposta> respostas;
	private double pontos;
	private int questoes;
	private double nota;
	private boolean corrigida;
	
	
	public boolean isCorrigida() {
		return corrigida;
	}
	public void setCorrigida(boolean corrigida) {
		this.corrigida = corrigida;
	}
	public double getPontos() {
		return pontos;
	}
	public void setPontos(double pontos) {
		this.pontos = pontos;
	}
	public int getQuestoes() {
		return questoes;
	}
	public void setQuestoes(int questoes) {
		this.questoes = questoes;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
	
	public List<Resposta> getRespostas() {
		return respostas;
	}
	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}
	public void setAlternativas(List<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Alternativa> getAlternativas() {
		return alternativas;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	
	
	

}
