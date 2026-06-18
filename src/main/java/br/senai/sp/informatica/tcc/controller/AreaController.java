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
import br.senai.sp.informatica.tcc.model.Area;

@Transactional
@Controller
public class AreaController implements ServletContextAware {
	private ServletContext context;
	@Autowired
	@Qualifier("areaDao")
	private InterfaceDao<Area> dao;

	@RequestMapping("/form_area")
	public String form(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			return "area/form";
		}
	}

	@RequestMapping("/salvar_area")
	public String salvar(@Valid Area area, BindingResult result, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			if (result.hasFieldErrors("nome")) {
				return "area/form";
			}
			if (area.getId() == null) {
				dao.inserir(area);
				return "area/sucesso";
			} else {
				dao.alterar(area);
				return "area/alterado_sucesso";

			}
		}
	}

	@RequestMapping("/lista_area")
	public String listar(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			List<Area> areas = dao.getLista();
			Gson gson = new Gson();
			String areasJson = gson.toJson(areas);
			model.addAttribute("areas", areasJson);
			return "area/lista";
		}
	}

	@RequestMapping("/excluir_area")
	public void excluir(long id, HttpServletResponse response) {
		dao.excluir(id);
		response.setStatus(200);
	}

	@RequestMapping("/alterar_area")
	public String alterar(long id, Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			Area a = dao.buscar(id);
			if (a != null) {
				model.addAttribute("area", a);
			}
			return "forward:form_area";
		}
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
