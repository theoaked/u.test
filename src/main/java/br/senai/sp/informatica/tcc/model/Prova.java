package br.senai.sp.informatica.tcc.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Prova {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	@ManyToMany(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private List<Questao> questoes;	
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Area> areas;	
	@ManyToOne
	private Professor professor;
	private double tempo;
	private int qtd_ques;
	private int dificuldade = 6;
	private int nivel;
	private int aplicacoesA = 0;
	
	
	
	
	public int getQtd_ques() {
		return qtd_ques;
	}
	public void setQtd_ques(int qtd_ques) {
		this.qtd_ques = qtd_ques;
	}
	public double getTempo() {
		return tempo;
	}
	public void setTempo(double tempo) {
		this.tempo = tempo;
	}
	public int getAplicacoesA() {
		return aplicacoesA;
	}
	public void setAplicacoesA(int aplicacoesA) {
		this.aplicacoesA = aplicacoesA;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}  
	public List<Questao> getQuestoes() {
		return questoes;
	}
	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}
	public List<Area> getAreas() {
		return areas;
	}
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public int getDificuldade() {
		return dificuldade;
	}
	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}
	
	
	
}
