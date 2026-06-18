package br.senai.sp.informatica.tcc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.sp.informatica.tcc.dao.AreaDao;
import br.senai.sp.informatica.tcc.dao.InstituicaoDao;

@Controller
public class IndexController {
	
	@Autowired
	private AreaDao daoA;
	
	@Autowired
	private InstituicaoDao daoI;
	
	@RequestMapping("/index")
	public String index(Model model) {
		return "/index";
	}
	
	@RequestMapping("/login")
	public String logar(Model model, HttpSession session) {
		model.addAttribute("area", daoA.getLista());
		model.addAttribute("instituicao", daoI.getListaEscola());
		return "login";
	}
	
	@RequestMapping("/logou")
	public String logou() {
		return "logou";
	}
	
	@RequestMapping("/termos")
	public String termos() {
		return "termos";
	}
}
