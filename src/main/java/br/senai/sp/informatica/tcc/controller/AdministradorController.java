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

import br.senai.sp.informatica.tcc.dao.AdministradorDao;
import br.senai.sp.informatica.tcc.dao.InterfaceDao;
import br.senai.sp.informatica.tcc.model.Administrador;
import br.senai.sp.informatica.tcc.model.Usuario;

@Transactional
@Controller
public class AdministradorController implements ServletContextAware {
	private ServletContext context;
	@Autowired
	@Qualifier("administradorDao")
	private InterfaceDao<Administrador> dao;

	@Autowired
	private AdministradorDao administradorDao;

	@RequestMapping("/form_administrador")
	public String form(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			return "administrador/form";
		}
	}
	
	@RequestMapping("/altera_adm")
	public String form1(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			Usuario usuario = (Usuario) session.getAttribute("admLogado");
			Administrador adm = (Administrador) administradorDao.buscaPorUsuario(usuario.getId());
			model.addAttribute("adm", adm);
			return "administrador/alterar";
		}
	}
	
	@RequestMapping("/logou/altera_adm")
	public String form2(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			Usuario usuario = (Usuario) session.getAttribute("admLogado");
			Administrador adm = (Administrador) administradorDao.buscaPorUsuario(usuario.getId());
			model.addAttribute("adm", adm);
			return "administrador/alterar";
		}
	}

	@RequestMapping("/salvar_administrador")
	public String salvar(@Valid Administrador administrador, BindingResult result, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			if (administrador.getId() == null) {
				dao.inserir(administrador);
				return "administrador/sucesso";
			} else {
				dao.alterar(administrador);
				return "logou";

			}
		}
	}
	
	@RequestMapping("/logou/salvar_administrador")
	public String salvar1(@Valid Administrador administrador, BindingResult result, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			if (administrador.getId() == null) {
				dao.inserir(administrador);
				return "administrador/sucesso";
			} else {
				dao.alterar(administrador);
				return "logou";

			}
		}
	}

	@RequestMapping("/lista_administrador")
	public String listar(Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			List<Administrador> administradores = dao.getLista();
			Gson gson = new Gson();
			String administradoresJson = gson.toJson(administradores);
			model.addAttribute("administradores", administradoresJson);
			return "administrador/lista";
		}
	}

	@RequestMapping("/excluir_administrador")
	public void excluir(long id, HttpServletResponse response, HttpSession session) {
		dao.excluir(id);
		response.setStatus(200);
	}

	@RequestMapping("/alterar_administrador")
	public String alterar(long id, Model model, HttpSession session) {
		if (session.getAttribute("admLogado") == null) {
			return "sem_acesso";
		} else {
			Administrador a = dao.buscar(id);
			if (a != null) {
				model.addAttribute("administrador", a);
			}
			return "forward:form_administrador";
		}
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
