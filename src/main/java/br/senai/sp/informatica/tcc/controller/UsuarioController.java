package br.senai.sp.informatica.tcc.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping("logar")
	public String logarSuper(String login, String senha, HttpSession session, Model model) {
		model.addAttribute("area", daoA.getLista());
		model.addAttribute("instituicao", daoI.getListaEscola());

		Usuario usuario = daoU.buscarPorLogin(login);
		if (usuario != null && passwordEncoder.matches(senha, usuario.getSenha())) {
			switch (usuario.getTipoUsuario()) {
			case 0:
				session.setAttribute("admLogado", usuario);
				return "forward:/logou";
			case 1:
				session.setAttribute("profLogado", usuario);
				return "logou";
			case 2:
				session.setAttribute("userLogado", usuario);
				return "logou";
			default:
				break;
			}
		}
		model.addAttribute("mensagem", "Login ou senha invalidos");
		return "login";
	}

	@RequestMapping("usuario/logout")
	public String sair(HttpSession session, Usuario usuario) {
		session.invalidate();
		return "index";
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

}
