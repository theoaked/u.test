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

import br.senai.sp.informatica.tcc.dao.InterfaceDao;
import br.senai.sp.informatica.tcc.model.Instituicao;

@Transactional
@Controller
public class InstituicaoController implements ServletContextAware {
	private ServletContext context;
	@Autowired
	@Qualifier("instituicaoDao")
	private InterfaceDao<Instituicao> dao;

	@RequestMapping("/form_instituicao")
	public String form(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			return "instituicao/form";
		}
	}

	@RequestMapping("/salvar_instituicao")
	public String salvar(@Valid Instituicao instituicao, BindingResult result, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			if (result.hasFieldErrors("nome")) {
				return "instituicao/form";
			}
			if (instituicao.getId() == null) {
				dao.inserir(instituicao);
				return "instituicao/sucesso";
			} else {
				dao.alterar(instituicao);
				return "instituicao/alterado_sucesso";

			}
		}
	}

	@RequestMapping("/lista_instituicao")
	public String listar(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			List<Instituicao> instituicoes = dao.getLista();
			Gson gson = new Gson();
			String instituicoesJson = gson.toJson(instituicoes);
			model.addAttribute("instituicoes", instituicoesJson);
			return "instituicao/lista";
		}
	}

	@RequestMapping("/excluir_instituicao")
	public void excluir(long id, HttpServletResponse response) {
		dao.excluir(id);
		response.setStatus(200);
	}

	@RequestMapping("/alterar_instituicao")
	public String alterar(long id, Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			Instituicao i = dao.buscar(id);
			if (i != null) {
				model.addAttribute("instituicao", i);
			}
			return "forward:form_instituicao";
		}
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
