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
import br.senai.sp.informatica.tcc.model.Aluno;

@Transactional
@Controller
public class AlunoController implements ServletContextAware {
	private ServletContext context;
	@Autowired
	@Qualifier("alunoDao")
	private InterfaceDao<Aluno> dao;

	@Autowired
	private AreaDao daoA;
	
	@Autowired
	private InstituicaoDao daoI;

	@RequestMapping("/form_aluno")
	public String form(Model model, HttpSession session) {
		model.addAttribute("area", daoA.getLista());
		model.addAttribute("instituicao", daoI.getListaEscola());
		return "aluno/form";
	}

	@RequestMapping("/salvar_aluno")
	public String salvar(@Valid Aluno aluno, BindingResult result, HttpSession session) {
		if (result.hasFieldErrors("descricao")) {
			return "aluno/form";
		}
		if (aluno.getId() == null) {
			dao.inserir(aluno);
			return "aluno/sucesso";
		} else {
			dao.alterar(aluno);
			return "aluno/alterado_sucesso";

		}
	}

	@RequestMapping("/lista_aluno")
	public String listar(Model model, HttpSession session) {
		List<Aluno> alunos = dao.getLista();
		Gson gson = new Gson();
		String alunosJson = gson.toJson(alunos);
		model.addAttribute("alunos", alunosJson);
		return "aluno/lista";
	}

	@RequestMapping("/excluir_aluno")
	public void excluir(long id, HttpServletResponse response) {
		dao.excluir(id);
		response.setStatus(200);
	}

	@RequestMapping("/alterar_aluno")
	public String alterar(long id, Model model, HttpSession session) {
		Aluno a = dao.buscar(id);
		if (a != null) {
			model.addAttribute("aluno", a);
		}
		return "forward:form_problema";
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
