package br.senai.sp.informatica.tcc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sp.informatica.tcc.dao.AdministradorDao;
import br.senai.sp.informatica.tcc.dao.AlunoDao;
import br.senai.sp.informatica.tcc.dao.AreaDao;
import br.senai.sp.informatica.tcc.dao.InstituicaoDao;
import br.senai.sp.informatica.tcc.dao.ProfessorDao;
import br.senai.sp.informatica.tcc.dao.UsuarioDao;
import br.senai.sp.informatica.tcc.model.Administrador;
import br.senai.sp.informatica.tcc.model.Aluno;
import br.senai.sp.informatica.tcc.model.Area;
import br.senai.sp.informatica.tcc.model.Instituicao;
import br.senai.sp.informatica.tcc.model.Professor;

/**
 * Popula dados iniciais (areas, instituicoes e usuarios) apenas quando o banco
 * esta vazio. Usa o {@link PasswordEncoder} real para gravar senhas em BCrypt,
 * permitindo subir e testar a aplicacao sem um dump pre-existente.
 *
 * <p>Credenciais de demonstracao (senha: 12345): admin / prof / aluno.
 */
@Component
public class DataSeeder implements CommandLineRunner {

	private final AreaDao areaDao;
	private final InstituicaoDao instituicaoDao;
	private final UsuarioDao usuarioDao;
	private final AdministradorDao administradorDao;
	private final ProfessorDao professorDao;
	private final AlunoDao alunoDao;
	private final PasswordEncoder passwordEncoder;

	public DataSeeder(AreaDao areaDao, InstituicaoDao instituicaoDao, UsuarioDao usuarioDao,
			AdministradorDao administradorDao, ProfessorDao professorDao, AlunoDao alunoDao,
			PasswordEncoder passwordEncoder) {
		this.areaDao = areaDao;
		this.instituicaoDao = instituicaoDao;
		this.usuarioDao = usuarioDao;
		this.administradorDao = administradorDao;
		this.professorDao = professorDao;
		this.alunoDao = alunoDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void run(String... args) {
		if (!usuarioDao.getLista().isEmpty()) {
			return; // ja existe dado: nao semeia novamente
		}

		Area exatas = new Area();
		exatas.setNome("Ciências Exatas");
		areaDao.inserir(exatas);

		Area humanas = new Area();
		humanas.setNome("Ciências Humanas");
		areaDao.inserir(humanas);

		Instituicao escola = new Instituicao();
		escola.setNome("SENAI Informática");
		escola.setEndereco("Alameda Barão de Limeira, 539 - São Paulo");
		escola.setTelefone("(11) 0000-0000");
		escola.setTipo(false); // escola (nao avaliativa)
		instituicaoDao.inserir(escola);

		Administrador admin = new Administrador();
		admin.setNome("Administrador");
		admin.setEmail("admin@u.test");
		admin.setTelefone("(11) 0000-0000");
		admin.setLogin("admin");
		admin.setSenha(passwordEncoder.encode("12345"));
		admin.setTipoUsuario(0);
		administradorDao.inserir(admin);

		Professor prof = new Professor();
		prof.setNome("Professor Demo");
		prof.setEmail("prof@u.test");
		prof.setTelefone("(11) 0000-0000");
		prof.setCpf("000.000.000-00");
		prof.setFormacao("Licenciatura");
		prof.setArea(exatas);
		prof.setInstituicao(escola);
		prof.setLogin("prof");
		prof.setSenha(passwordEncoder.encode("12345"));
		prof.setTipoUsuario(1);
		professorDao.inserir(prof);

		Aluno aluno = new Aluno();
		aluno.setNome("Aluno Demo");
		aluno.setEmail("aluno@u.test");
		aluno.setTelefone("(11) 0000-0000");
		aluno.setFormacao("Ensino Médio");
		aluno.setArea(exatas);
		aluno.setInstituicao(escola);
		aluno.setLogin("aluno");
		aluno.setSenha(passwordEncoder.encode("12345"));
		aluno.setTipoUsuario(2);
		alunoDao.inserir(aluno);
	}
}
