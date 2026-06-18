package br.senai.sp.informatica.tcc.relatorio;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

@Component
public class GeraRelatorio {

	private final DataSource dataSource;

	public GeraRelatorio(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public byte[] gerarPDF(long id) {
		// O template (.jrxml) agora e lido do classpath e a conexao vem do pool
		// gerenciado pelo Spring (antes era DriverManager com credenciais fixas).
		try (Connection conexao = dataSource.getConnection();
				InputStream jrxml = new ClassPathResource("reports/Cherry.jrxml").getInputStream()) {

			JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("idEvento", id);

			return JasperRunManager.runReportToPdf(jasperReport, parametros, conexao);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
