package br.senai.sp.informatica.tcc.restcontroller;

import java.util.List;

import javax.servlet.ServletContext;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;

import br.senai.sp.informatica.tcc.dao.AlunoDao;
import br.senai.sp.informatica.tcc.dao.EventoDao;
import br.senai.sp.informatica.tcc.dao.GabaritoDao;
import br.senai.sp.informatica.tcc.dao.InterfaceDao;
import br.senai.sp.informatica.tcc.dao.ProvaDao;
import br.senai.sp.informatica.tcc.model.Aluno;
import br.senai.sp.informatica.tcc.model.Evento;
import br.senai.sp.informatica.tcc.model.Gabarito;
import br.senai.sp.informatica.tcc.model.Prova;
import br.senai.sp.informatica.tcc.model.Usuario;

@RestController
@RequestMapping("/services/usuario")
@Transactional
public class AlunoRestController implements ServletContextAware {
	@Autowired
	@Qualifier("alunoDao")
	private InterfaceDao<Aluno> dao;
	private ServletContext context;

	@Autowired
	private AlunoDao alunoDao;

	@Autowired
	private ProvaDao provaDao;

	@Autowired
	private EventoDao eventoDao;

	@Autowired
	private GabaritoDao gabritoDao;

	// manda objeto ALUNO via id
	@RequestMapping(value = "/listaById/{idAluno}", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Aluno> listaById(@PathVariable(value = "idAluno") long idAluno) {
		return alunoDao.getAlunoById2(idAluno);
	}

	// manda lista de todos os alunos
	@RequestMapping(value = "/lista", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Aluno> lista() {
		return dao.getLista();
	}

	@RequestMapping(value = "/listaProvas", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Prova> listaProvas() {
		return provaDao.getLista();
	}

	// manda objeto USUARIO via login
	@RequestMapping(value = "/logar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public Usuario logar(@RequestBody String json) {
		System.out.println(json);
		AlunoDao daoAluno = (AlunoDao) dao;
		Aluno usuario;
		try {
			JSONObject jsonObject = new JSONObject(json);
			String login = jsonObject.getString("login");
			String senha = jsonObject.getString("senha");
			int tipoUsuario = jsonObject.getInt("tipoUsuario");
			usuario = daoAluno.logar(login, senha, tipoUsuario);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return usuario;
	}

	// tras eventos a serem feitos
	@RequestMapping(value = "/eventosById/{idAluno}", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Evento> eventosById(@PathVariable(value = "idAluno") long idAluno) {
		return eventoDao.getListaDeEventosPorId(idAluno);
	}

	// tras evento que aguardam correcao
	@RequestMapping(value = "/eventosById1/{idAluno}", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Evento> eventosById1(@PathVariable(value = "idAluno") long idAluno) {
		return eventoDao.getListaDeEventosPorId1(idAluno);
	}

	// tras eventos ja corrigidos
	@RequestMapping(value = "/eventosById2/{idAluno}", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Evento> eventosById2(@PathVariable(value = "idAluno") long idAluno) {
		return eventoDao.getListaDeEventosPorId2(idAluno);
	}

	// tras gabaritos de um aluno ja corrigidos
	@RequestMapping(value = "/gabaritos_by_id/{idAluno}", method = RequestMethod.GET, headers = "Accept=application/json;charset=UTF-8")
	public List<Gabarito> gabaritosById(@PathVariable(value = "idAluno") long idAluno) {
		return gabritoDao.getListaByAluno(idAluno);
	}

	@RequestMapping(value = "/finalizar", method = RequestMethod.POST, headers = "Accept=application/json;charset=UTF-8")
	public String fechar(@RequestBody Gabarito gabarito) {
		try {
			if (gabarito.getPontos() == 0) {
				for (int j = 0; j < gabarito.getAlternativas().size(); j++) {
					if (gabarito.getAlternativas().get(j).isCorreta()) {
						gabarito.setPontos(gabarito.getPontos() + 1);
					}
				}
			}

			gabritoDao.inserir(gabarito);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "OK";
	}

	@Override
	public void setServletContext(ServletContext context) {
		this.context = context;
	}

}
