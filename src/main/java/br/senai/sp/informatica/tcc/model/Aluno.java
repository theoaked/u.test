package br.senai.sp.informatica.tcc.model;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Aluno extends Usuario{
	
	private String nome;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Calendar dataNasc;
	private String email;
	private String telefone;
	@ManyToOne
	private Instituicao instituicao;
	private String formacao;
	private double level;
	@ManyToOne
	private Area area;
	private String imagem;
	private int questoes;
	private int provas;
	private boolean ensinoF;
	private boolean ensinoM;
	private boolean ensinoS;
	private boolean ensinoT;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public Instituicao getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}
	public String getFormacao() {
		return formacao;
	}
	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}
	public double getLevel() {
		return level;
	}
	public void setLevel(double level) {
		this.level = level;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public String getImagem() {
		return imagem;
	}
	public void setImagem(String imagem) {
		this.imagem = imagem;
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
	public boolean isEnsinoF() {
		return ensinoF;
	}
	public void setEnsinoF(boolean ensinoF) {
		this.ensinoF = ensinoF;
	}
	public boolean isEnsinoM() {
		return ensinoM;
	}
	public void setEnsinoM(boolean ensinoM) {
		this.ensinoM = ensinoM;
	}
	public boolean isEnsinoS() {
		return ensinoS;
	}
	public void setEnsinoS(boolean ensinoS) {
		this.ensinoS = ensinoS;
	}
	public boolean isEnsinoT() {
		return ensinoT;
	}
	public void setEnsinoT(boolean ensinoT) {
		this.ensinoT = ensinoT;
	}
	
	
	

}
