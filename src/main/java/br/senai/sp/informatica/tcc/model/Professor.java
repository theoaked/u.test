package br.senai.sp.informatica.tcc.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Professor extends Usuario{
	
	@ManyToOne
	private Area area;
	@ManyToOne
	private Instituicao instituicao;
	private String nome;
	private String formacao;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Calendar dataNasc;
	private String email;
	private String telefone;
	private double level;
	private int questoes;
	private int provas;
	private String cpf;
	
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFormacao() {
		return formacao;
	}
	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}
	public Calendar getDataNasc() {
		return dataNasc;
	}
	public void setDataNasc(Calendar dataNasc) {
		this.dataNasc = dataNasc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public double getLevel() {
		return level;
	}
	public void setLevel(double level) {
		this.level = level;
	}
	public int getQuestoes() {
		return questoes;
	}
	public void setQuestoes(int questoes) {
		this.questoes = questoes;
	}
	public int getProvas() {
		return provas;
	}
	public void setProvas(int provas) {
		this.provas = provas;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	
	
	

}
