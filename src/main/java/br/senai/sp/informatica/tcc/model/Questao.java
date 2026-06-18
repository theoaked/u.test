package br.senai.sp.informatica.tcc.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Questao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String enunciado;
	private int utilizacoes;
	private int aplicacoes;
	private boolean ativa;
	private int acertos;
	private int erros;
	private int dificuldade = 1;
	@ManyToOne
	private Area area;
	private int acerto;
	private int nivel;
	private int tipo;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Calendar dataInativa;
	@ManyToOne
	private Professor professor;
	
	

	public Calendar getDataInativa() {
		return dataInativa;
	}
	public void setDataInativa(Calendar dataInativa) {
		this.dataInativa = dataInativa;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public int getUtilizacoes() {
		return utilizacoes;
	}
	public void setUtilizacoes(int utilizacoes) {
		this.utilizacoes = utilizacoes;
	}
	public int getAplicacoes() {
		return aplicacoes;
	}
	public void setAplicacoes(int aplicacoes) {
		this.aplicacoes = aplicacoes;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getAcerto() {
		return acerto;
	}
	public void setAcerto(int acerto) {
		this.acerto = acerto;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	public boolean isAtiva() {
		return ativa;
	}
	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	public int getAcertos() {
		return acertos;
	}
	public void setAcertos(int acertos) {
		this.acertos = acertos;
	}
	public int getErros() {
		return erros;
	}
	public void setErros(int erros) {
		this.erros = erros;
	}
	public int getDificuldade() {
		return dificuldade;
	}
	public void setDificuldade(int dificuldade) {
		this.dificuldade = dificuldade;
	}
	

}
