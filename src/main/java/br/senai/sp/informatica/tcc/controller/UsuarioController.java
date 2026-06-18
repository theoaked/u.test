package br.senai.sp.informatica.tcc.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import br.senai.sp.informatica.tcc.dao.AreaDao;
import br.senai.sp.informatica.tcc.dao.InstituicaoDao;
import br.senai.sp.informatica.tcc.dao.UsuarioDao;
import br.senai.sp.informatica.tcc.model.Usuario;

@Transactional
@Controller
public class UsuarioController implements ServletContextAware {
	private ServletContext context;

	@Autowired
	private UsuarioDao daoU;
	
	@Autowired
	private AreaDao daoA;
	
	@Autowired
	private InstituicaoDao daoI;

	@RequestMapping("logar")
	public String logarSuper(String login, String senha, HttpSession session, Model model) {
		model.addAttribute("area", daoA.getLista());
		model.addAttribute("instituicao", daoI.getListaEscola());
		UsuarioDao daoUsuario = (UsuarioDao) daoU;
		Usuario usuarioS = daoUsuario.logar(login, senha, 0);
		Usuario usuarioP = daoUsuario.logar(login, senha, 1);
		Usuario usuarioA = daoUsuario.logar(login, senha, 2);

		if (usuarioS != null) {
			session.setAttribute("admLogado", usuarioS);
			return "forward:/logou";
		} else if (usuarioP != null) {
			session.setAttribute("profLogado", usuarioP);
			return "/logou";
		} else if (usuarioA != null) {
			session.setAttribute("userLogado", usuarioA);
			return "/logou";
		} else {
			model.addAttribute("mensagem", "Login ou senha invalidos");
			return "/login";
		}

	}

	@RequestMapping("usuario/logout")
	public String sair(HttpSession session, Usuario usuario) {
		session.invalidate();
		return "/index";
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

}
