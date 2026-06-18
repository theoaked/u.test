package br.senai.sp.informatica.tcc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Resposta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String resposta;
	private String consideracao;
	@ManyToOne
	@JoinColumn(name = "questaod_id")
	@JsonProperty(access = Access.WRITE_ONLY)
	private QuestaoD questao;
	private int correta;
		
	
	
	public QuestaoD getQuestao() {
		return questao;
	}
	public void setQuestao(QuestaoD questao) {		
		this.questao = questao;
	}
	public int getCorreta() {
		return correta;
	}
	public void setCorreta(int correta) {
		this.correta = correta;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getResposta() {
		return resposta;
	}
	public void setResposta(String resposta) {
		this.resposta = resposta;
	}
	public String getConsideracao() {
		return consideracao;
	}
	public void setConsideracao(String consideracao) {
		this.consideracao = consideracao;
	}
	
	

}
