package br.senai.sp.informatica.tcc.relatorio;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Substitui o antigo RelatorioServlet (@WebServlet). Mantem a mesma URL e
 * o mesmo contrato (parametro "id", PDF inline), porem usando injecao de
 * dependencias do Spring e o DataSource gerenciado.
 */
@Controller
public class RelatorioController {

	private final GeraRelatorio geraRelatorio;

	public RelatorioController(GeraRelatorio geraRelatorio) {
		this.geraRelatorio = geraRelatorio;
	}

	@GetMapping("/professor/RelatorioServlet/")
	public ResponseEntity<byte[]> gerar(@RequestParam("id") int id) {
		byte[] pdf = geraRelatorio.gerarPDF(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=relatorio.pdf");
		return new ResponseEntity<>(pdf, headers, org.springframework.http.HttpStatus.OK);
	}
}
