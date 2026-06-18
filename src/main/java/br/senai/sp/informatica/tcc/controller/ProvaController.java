package br.senai.sp.informatica.tcc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.senai.sp.informatica.tcc.dao.AreaDao;
import br.senai.sp.informatica.tcc.dao.InterfaceDao;
import br.senai.sp.informatica.tcc.dao.ProfessorDao;
import br.senai.sp.informatica.tcc.dao.ProvaDao;
import br.senai.sp.informatica.tcc.dao.QuestaoDDao;
import br.senai.sp.informatica.tcc.dao.QuestaoDao;
import br.senai.sp.informatica.tcc.model.Area;
import br.senai.sp.informatica.tcc.model.Professor;
import br.senai.sp.informatica.tcc.model.Prova;
import br.senai.sp.informatica.tcc.model.Questao;
import br.senai.sp.informatica.tcc.model.Usuario;

@Transactional
@Controller
public class ProvaController implements ServletContextAware {
	private ServletContext context;
	@Autowired
	@Qualifier("provaDao")
	private InterfaceDao<Prova> dao;

	@Autowired
	private QuestaoDao daoQ;

	@Autowired
	private ProvaDao daoProva;

	@Autowired
	private AreaDao daoA;

	@Autowired
	private ProfessorDao daoProf;

	@Autowired
	private QuestaoDDao daoQD;

	// Formulario inicial da prova
	@RequestMapping("/form_prova1")
	public String form10(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			return "prova/form1";
		}
	}

	// Formulario da prova normal
	@RequestMapping("/form_prova")
	public String form1(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("questao", daoQ.getQuestoesAtivas());
			return "prova/form";
		}
	}

	// Formulario da prova ALEATORIA
	@RequestMapping("/form_prova_aleatoria")
	public String form(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("areas", daoA.getLista());
			return "prova/form_aleatoria";
		}
	}

	@RequestMapping("/salvar_prova")
	public String salvar(@Valid Prova prova, BindingResult result, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			if (prova.getId() == null) {
				Usuario usuario = (Usuario) session.getAttribute("profLogado");
				Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());
				prova.setProfessor(prof);
				List<Questao> questoes = prova.getQuestoes();
				for (int i = 0; i < questoes.size(); i++) {
					if (questoes.get(i).getId() == null) {
						questoes.remove(i);
						i--;
					}
				}
				prova.setQuestoes(questoes);

				List<Area> areas = new ArrayList<Area>();
				for (int i = 0; i < questoes.size(); i++) {
					Area area;
					area = questoes.get(i).getArea();
					if (!areas.contains(area)) {
						areas.add(area);
					}
				}
				prova.setAreas(areas);

				int contD = 0;
				int contF = 0;
				int contM = 0;

				for (int i = 0; i < prova.getQuestoes().size(); i++) {
					Questao q = daoQ.getQuestao(prova.getQuestoes().get(i).getId());
					if (q.getDificuldade() == 2) {
						contD++;
					} else if (q.getDificuldade() == 1) {
						contM++;
					} else {
						contF++;
					}
				}
				if (contF > contM && contF > contD) {
					prova.setDificuldade(0);
				} else if (contM > contF && contM > contD) {
					prova.setDificuldade(1);
				} else if (contD > contF && contD > contM) {
					prova.setDificuldade(2);
				}
				prova.setAreas(areas);
				dao.inserir(prova);
				return "prova/sucesso";
			} else {
				dao.alterar(prova);
				return "prova/alterado_sucesso";
			}
		}
	}

	@RequestMapping("/salvar_prova_aleatoria")
	public String salvar1(@Valid Prova prova, BindingResult result, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			if (prova.getId() == null) {
				Usuario usuario = (Usuario) session.getAttribute("profLogado");
				Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());
				prova.setProfessor(prof);

				List<Area> areas = prova.getAreas();
				for (int i = 0; i < areas.size(); i++) {
					if (areas.get(i).getId() == null) {
						areas.remove(i);
						i--;
					}
				}
				prova.setAreas(areas);

				int limite = 0;
				if (prova.getQtd_ques() < prova.getAreas().size()) {
					prova.setQtd_ques(prova.getAreas().size());
				}
				limite = prova.getQtd_ques() / prova.getAreas().size();

				List<Questao> questoes = prova.getQuestoes();
				List<Questao> questoes2 = new ArrayList<Questao>();
				for (int i = 0; i < prova.getAreas().size(); i++) {
					questoes = daoQ.getQuestaoAleatoria(prova.getAreas().get(i).getId(), limite);
					for (int j = 0; j < questoes.size(); j++) {
						
						questoes2.add(questoes.get(j));
					}
				}

				prova.setQuestoes(questoes2);

				int contD = 0;
				int contF = 0;
				int contM = 0;

				for (int i = 0; i < prova.getQuestoes().size(); i++) {

					Questao q = daoQ.getQuestao(prova.getQuestoes().get(i).getId());

					if (q.getDificuldade() == 2) {
						contD++;
					} else if (q.getDificuldade() == 1) {
						contM++;
					} else {
						contF++;
					}
				}
				if (contF > contM && contF > contD) {
					prova.setDificuldade(0);
				} else if (contM > contF && contM > contD) {
					prova.setDificuldade(1);
				} else if (contD > contF && contD > contM) {
					prova.setDificuldade(2);
				}

				dao.inserir(prova);
				return "prova/sucesso";
			} else {
				dao.alterar(prova);
				return "prova/alterado_sucesso";
			}
		}
	}

	// listagem de provas pelo professor que as formulou
	@RequestMapping("/lista_prova1")
	public String listar(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			Usuario usuario = (Usuario) session.getAttribute("profLogado");
			Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());

			ObjectMapper mapper = new ObjectMapper();
			List<Prova> provas = daoProva.getListaProf(prof.getId());
			String provasJson = null;
			try {
				provasJson = mapper.writeValueAsString(provas);
			} catch (JsonProcessingException e) {
				System.out.println("ERRO NO MAPPER ---- " + e);
			}
			model.addAttribute("provas", provasJson);
			return "prova/lista1";
		}
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}