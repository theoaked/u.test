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

import com.google.gson.Gson;

import br.senai.sp.informatica.tcc.dao.AreaDao;
import br.senai.sp.informatica.tcc.dao.InstituicaoDao;
import br.senai.sp.informatica.tcc.dao.InterfaceDao;
import br.senai.sp.informatica.tcc.dao.ProfessorDao;
import br.senai.sp.informatica.tcc.model.Professor;
import br.senai.sp.informatica.tcc.model.Usuario;

@Transactional
@Controller
public class ProfessorController implements ServletContextAware {
	private ServletContext context;
	@Autowired
	@Qualifier("professorDao")
	private InterfaceDao<Professor> dao;

	@Autowired
	private AreaDao daoA;

	@Autowired
	private InstituicaoDao daoI;
	
	@Autowired
	private ProfessorDao daoProf;

	@RequestMapping("/form_professor")
	public String form(Model model, HttpSession session) {
		model.addAttribute("area", daoA.getLista());
		model.addAttribute("instituicao", daoI.getLista());
		return "professor/form";
	}

	@RequestMapping("/altera_professor")
	public String form2(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("profLogado");
		Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("professor", prof);
			return "professor/alterar";
		}
	}
	
	@RequestMapping("/logou/altera_professor")
	public String form3(Model model, HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("profLogado");
		Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("professor", prof);
			return "professor/alterar";
		}
	}

	@RequestMapping("/salvar_professor")
	public String salvar(@Valid Professor professor, BindingResult result, HttpSession session) {
		if (professor.getId() == null) {
			dao.inserir(professor);
			return "professor/sucesso";
		} else {
			dao.alterar(professor);
			return "logou";

		}
	}
	
	@RequestMapping("/logou/salvar_professor")
	public String salvar1(@Valid Professor professor, BindingResult result, HttpSession session) {
		if (professor.getId() == null) {
			dao.inserir(professor);
			return "professor/sucesso";
		} else {
			dao.alterar(professor);
			return "logou";

		}
	}

	@RequestMapping("/lista_professor")
	public String listar(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			List<Professor> professores = dao.getLista();
			Gson gson = new Gson();
			String professoresJson = gson.toJson(professores);
			model.addAttribute("professores", professoresJson);
			return "professor/lista";
		}
	}

	@RequestMapping("/excluir_professor")
	public void excluir(long id, HttpServletResponse response) {
		dao.excluir(id);
		response.setStatus(200);
	}

	@RequestMapping("/alterar_professor")
	public String alterar(long id, Model model, HttpSession session) {
		Professor p = dao.buscar(id);
		if (p != null) {
			model.addAttribute("professor", p);
		}
		return "forward:form_professor";
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
