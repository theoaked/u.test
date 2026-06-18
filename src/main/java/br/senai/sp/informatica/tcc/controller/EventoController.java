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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.senai.sp.informatica.tcc.dao.AlunoDao;
import br.senai.sp.informatica.tcc.dao.EventoDao;
import br.senai.sp.informatica.tcc.dao.EventoDaoJDBC;
import br.senai.sp.informatica.tcc.dao.GabaritoDao;
import br.senai.sp.informatica.tcc.dao.InterfaceDao;
import br.senai.sp.informatica.tcc.dao.ProfessorDao;
import br.senai.sp.informatica.tcc.dao.ProvaDao;
import br.senai.sp.informatica.tcc.dao.QuestaoDao;
import br.senai.sp.informatica.tcc.dao.QuestaoDaoJDBC;
import br.senai.sp.informatica.tcc.dao.RespostaDaoJDBC;
import br.senai.sp.informatica.tcc.dao.UsuarioDaoJDBC;
import br.senai.sp.informatica.tcc.model.Aluno;
import br.senai.sp.informatica.tcc.model.Evento;
import br.senai.sp.informatica.tcc.model.Gabarito;
import br.senai.sp.informatica.tcc.model.ListaGabaritos;
import br.senai.sp.informatica.tcc.model.Professor;
import br.senai.sp.informatica.tcc.model.Prova;
import br.senai.sp.informatica.tcc.model.Questao;
import br.senai.sp.informatica.tcc.model.Resposta;
import br.senai.sp.informatica.tcc.model.Usuario;

@Controller
public class EventoController implements ServletContextAware {
	private ServletContext context;
	@Autowired
	@Qualifier("eventoDao")
	private InterfaceDao<Evento> dao;

	@Autowired
	private ProvaDao provaDao;

	@Autowired
	private AlunoDao aluDao;

	@Autowired
	private QuestaoDao qDao;

	@Autowired
	private GabaritoDao gabDao;

	@Autowired
	private ProfessorDao daoProf;

	@Autowired
	private EventoDao daoEvent;

	@Autowired
	private EventoDaoJDBC daoEvento;

	@Autowired
	private UsuarioDaoJDBC daoUser;

	@Autowired
	private QuestaoDaoJDBC daoQuest;

	@Autowired
	private RespostaDaoJDBC daoResp;

	// Formulario inicial do evento
	@RequestMapping("/form_evento")
	public String form(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("prova", provaDao.getLista());
			return "evento/form";
		}
	}

	@RequestMapping("/prova")
	public String prova(Model model, int id, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			model.addAttribute("prova", provaDao.getProvaById(id));
			return "prova/prova";
		}
	}

	@Transactional
	@RequestMapping("/criar_evento1")
	public String criarEvento(Model model, Prova prova, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			Evento evento = new Evento();
			Usuario usuario = (Usuario) session.getAttribute("profLogado");
			Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());
			evento.setProfessor(prof);
			evento.setInstituicao(prof.getInstituicao());
			evento.setProva(prova);

			for (int i = 0; i < provaDao.getProvaById(prova.getId()).getQuestoes().size(); i++) {
				daoQuest.colocarNaGeladeira(provaDao.getProvaById(prova.getId()).getQuestoes().get(i));
			}
			dao.inserir(evento);
			model.addAttribute("evento", evento);
			return "forward:form_evento1";
		}
	}

	// Segundo formulario do evento
	@RequestMapping("/form_evento1")
	public String form1(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			Usuario usuario = (Usuario) session.getAttribute("profLogado");
			Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());
			if (prof.getInstituicao().isTipo()) {
				model.addAttribute("aluno", aluDao.getLista());
			} else {
				model.addAttribute("aluno", aluDao.getListaByProf(prof.getInstituicao().getId()));
			}
			return "evento/form1";
		}
	}

	@Transactional
	@RequestMapping("/criar_evento2")
	public String criarEvento2(@Valid Evento evento, Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {

			Evento evento1 = daoEvent.getEventoById(evento.getId());

			evento1.setNome(evento.getNome());
			evento1.setData(evento.getData());
			evento1.setHora(evento.getHora());
			evento1.setTipo(evento.getTipo());
			
			

			List<Aluno> alunos = evento.getAlunosParticipantes();
			for (int i = 0; i < alunos.size(); i++) {
				if (alunos.get(i).getId() == null) {
					alunos.remove(i);
					i--;
				}
			}
			evento1.setAlunosParticipantes(alunos);

			dao.alterar(evento1);

			model.addAttribute("evento", evento1);

			return "evento/sucesso2";
		}
	}

	// listar eventos por professor
	@RequestMapping("/lista_evento1")
	public String listar(Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			Usuario usuario = (Usuario) session.getAttribute("profLogado");
			Professor prof = (Professor) daoProf.buscaPorUsuario(usuario.getId());

			ObjectMapper mapper = new ObjectMapper();

			List<Evento> eventos = daoEvent.getListaProf(prof.getId());
			String eventosJson = null;
			try {
				eventosJson = mapper.writeValueAsString(eventos);
			} catch (JsonProcessingException e) {
				System.out.println("ERRO NO MAPPER ---- " + e);
			}
			model.addAttribute("eventos", eventosJson);
			return "evento/lista1";
		}
	}

	@RequestMapping("/corrigir_evento")
	public String corrigir(long id, Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			List<Gabarito> g = gabDao.getListaByEvento(id);
			model.addAttribute("gabaritos", g);
			return "forward:corrigir_gabaritos";
		}
	}

	@RequestMapping("/corrigir_gabaritos")
	public String corrigir2(Model model, HttpSession session) {
		return "evento/gabaritos";
	}

	@RequestMapping("/corrigir_gabaritos1")
	public String corrigir3(ListaGabaritos listaGabaritos, Model model, HttpSession session) {
		if (session.getAttribute("profLogado") == null) {
			return "sem_acesso";
		} else {
			List<Gabarito> gabaritos = listaGabaritos.getLista();
			
			

			for (int i = 0; i < gabaritos.size(); i++) {
				Gabarito gabarito = gabaritos.get(i);
				for (int j = 0; j < gabarito.getRespostas().size(); j++) {
					Resposta resposta = gabarito.getRespostas().get(j);
					Questao q = qDao.getQuestao(resposta.getQuestao().getId());
					daoResp.alterarResposta1(resposta);
					Aluno a = new Aluno();

					a = aluDao.getAlunoById(gabarito.getAluno().getId());
					switch (resposta.getCorreta()) {
					case 0:
						daoQuest.maisErro(q);
						break;
					case 1:
						gabarito.setPontos(gabarito.getPontos() + 0.5);

						daoUser.aumentarLevel(a, 0.5);
						daoResp.alterarGabarito1(gabarito);
						daoQuest.maisAcerto(q);
						break;
					case 2:
						gabarito.setPontos(gabarito.getPontos() + 1);
						daoResp.alterarGabarito1(gabarito);
						daoUser.aumentarLevel(a, 1);
						daoQuest.maisAcerto(q);
						break;
					default:
						break;
					}
				}
			}
			List<Gabarito> lista = new ArrayList<Gabarito>();
			for (int i = 0; i < gabaritos.size(); i++) {
				Gabarito g = gabDao.getListaById(gabaritos.get(i).getId());
				Aluno a = new Aluno();
				daoEvento.alterarProva(g.getEvento().getProva());
				a = aluDao.getAlunoById(g.getAluno().getId());
				for (int j = 0; j < g.getAlternativas().size(); j++) {
					if (g.getAlternativas().get(j).isCorreta()) {
						g.setPontos(g.getPontos() + 1);
						daoUser.aumentarLevel(a, 1);
						daoQuest.maisAcerto(g.getAlternativas().get(j).getQuestao());
					} else {
						daoQuest.maisErro(g.getAlternativas().get(j).getQuestao());
					}
				}
				daoResp.alterarGabarito1(g);
				lista.add(g);
			}

			for (int i = 0; i < lista.size(); i++) {
				Gabarito gabarito = lista.get(i);
				gabarito.setNota((gabarito.getPontos() * 10) / gabarito.getQuestoes());
				daoResp.alterarGabarito2(gabarito);
				lista.get(i).setNota(gabarito.getNota());
			}

			listaGabaritos.setLista(lista);

			Evento evento = listaGabaritos.getLista().get(0).getEvento();
			daoEvento.alterarEvento2(evento);

			for (int i = 0; i < evento.getProva().getQuestoes().size(); i++) {
				daoQuest.maisAplicacao(evento.getProva().getQuestoes().get(i));
				Questao q = qDao.getQuestao(evento.getProva().getQuestoes().get(i).getId());
				if (q.getAplicacoes() == 10) {
					if (q.getAcertos() <= 3) {
						daoQuest.aumentaDificuldade(q);
					} else if (q.getAcertos() <= 7) {
					} else {
						daoQuest.diminuiDificuldade(evento.getProva().getQuestoes().get(i));
					}
				}
			}
			model.addAttribute("evento", evento);
			model.addAttribute("gabaritos", listaGabaritos.getLista());
			return "evento/sucesso";
		}
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}
}
