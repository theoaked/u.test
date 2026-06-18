package br.senai.sp.informatica.tcc.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
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
import br.senai.sp.informatica.tcc.dao.QuestaoADao;
import br.senai.sp.informatica.tcc.dao.QuestaoDDao;
import br.senai.sp.informatica.tcc.dao.QuestaoDao;
import br.senai.sp.informatica.tcc.dao.QuestaoDaoJDBC;
import br.senai.sp.informatica.tcc.model.Professor;
import br.senai.sp.informatica.tcc.model.Questao;
import br.senai.sp.informatica.tcc.model.QuestaoA;
import br.senai.sp.informatica.tcc.model.QuestaoD;
import br.senai.sp.informatica.tcc.model.Usuario;

@Transactional
@Controller
public class QuestaoController implements ServletContextAware {
	private ServletContext context;
	@Qualifier("questaoDao")
	private InterfaceDao<Questao> dao;

	@Autowired
	private AreaDao daoA;

	@Autowired
	private QuestaoDDao daoQD;

	@Autowired
	private QuestaoDao daoQ;

	@Autowired
	private QuestaoADao daoQA;

	@Autowired
	private ProfessorDao daoProf;

	@Autowired
	private QuestaoDaoJDBC daoJDBC;

	// Formulario inicial da questao
	@RequestMapping("/form_questao")
	public String form(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("area", daoA.getLista());
			return "questao/form1";
		}
	}

	// decisão entre questão alternativa ou dissertativa
	@RequestMapping("/criar_questao1")
	public String salvar(@Valid Questao questao, BindingResult result, HttpSession session, Model model) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			if (questao.getTipo() == 0) {
				questao = new QuestaoD();

				Usuario usuario = (Usuario) session.getAttribute("profLogado");
				Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());

				questao.setProfessor(prof);
				questao.setAtiva(true);

				daoQD.inserir((QuestaoD) questao);
				model.addAttribute("questaoD", questao);
				return "forward:form1_0";
			} else {
				questao = new QuestaoA();

				Usuario usuario = (Usuario) session.getAttribute("profLogado");
				Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());

				questao.setProfessor(prof);
				questao.setAtiva(true);

				daoQA.inserir((QuestaoA) questao);
				model.addAttribute("questaoA", questao);
				return "forward:form1_1";
			}
		}
	}

	// formulario questao dissertativa
	@RequestMapping("/form1_0")
	public String form1(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("area", daoA.getLista());
			return "questao/form1_0";
		}
	}

	// formulario questao alternativa
	@RequestMapping("/form1_1")
	public String form2(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("area", daoA.getLista());
			return "questao/form1_1";
		}
	}

	// salvar questao dissertativa
	@RequestMapping("/criar_questao1D")
	public String salvar1(@Valid QuestaoD questao, BindingResult result, HttpSession session) {

		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {

			Usuario usuario = (Usuario) session.getAttribute("profLogado");
			Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());

			questao.setProfessor(prof);

			daoJDBC.alterarQuestaoD1(questao);
			return "questao/sucesso";
		}
	}

	@RequestMapping("/criar_questao1A")
	public String salvar2(@Valid QuestaoA questao, BindingResult result, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			for (int i = 0; i < questao.getAlternativas().size(); i++) {
				System.out.println(
						"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + questao.getAlternativas().get(i).getDescricao());
				if (questao.getAlternativas().get(i).getDescricao() == "") {
					questao.getAlternativas().remove(i);
					i--;
				}
			}

			Usuario usuario = (Usuario) session.getAttribute("profLogado");
			Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());

			questao.setProfessor(prof);

			daoQA.alterar(questao);
			return "questao/sucesso";
		}
	}

	@RequestMapping("/lista_questao")
	public String listar(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			ObjectMapper mapper = new ObjectMapper();

			Usuario usuario = (Usuario) session.getAttribute("profLogado");
			Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());

			List<Questao> questoes = daoQ.getQuestoesById(prof.getId());
			String questoesJson = null;
			try {
				questoesJson = mapper.writeValueAsString(questoes);
			} catch (JsonProcessingException e) {
				System.out.println("ERRO NO MAPPER ---- " + e);
			}
			model.addAttribute("questoes", questoesJson);
			return "questao/lista";
		}
	}
	
	@RequestMapping("/lista_questaoAdm")
	public String listar1(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			ObjectMapper mapper = new ObjectMapper();

			List<Questao> questoes = daoQ.getLista();
			String questoesJson = null;
			try {
				questoesJson = mapper.writeValueAsString(questoes);
			} catch (JsonProcessingException e) {
				System.out.println("ERRO NO MAPPER ---- " + e);
			}
			model.addAttribute("questoes", questoesJson);
			return "questao/listaAdm";
		}
	}

	@RequestMapping("/excluir_questao")
	public void excluir(long id, HttpServletResponse response) {
		dao.excluir(id);
		response.setStatus(200);
	}
	
	@RequestMapping("/ativar_questao")
	public void ativar(long id, HttpServletResponse response) {		
		daoJDBC.tirarDaGeladeira(daoQ.getQuestao(id));
		response.setStatus(200);
	}

	@RequestMapping("/alterar_questao")
	public String alterar(long id, Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			Questao a = dao.buscar(id);
			if (a != null) {
				model.addAttribute("questao", a);
			}
			return "forward:form_questao";
		}
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
