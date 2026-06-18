package br.senai.sp.informatica.tcc.relatorio;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/professor/RelatorioServlet/")
public class RelatorioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
	
		try {
 
			String id1 = request.getParameter("id");
			int id = Integer.parseInt(id1);
			
			// Pega o caminho completo de onde a aplicação está rodando
			ServletContext servletContext = request.getServletContext();
			
			String diretorio = servletContext.getRealPath("/") + "resources\\relatorios\\";
		
 
			// Instaciar a classe que possui os métodos de geração de relatório
			GeraRelatorio geraRelatorio = new GeraRelatorio();
 
			// Chama o método que gera um array de bytes com o
			// conteúdo do arquivo PDF
			String relatorioGabarito = null;
			
			byte[] pdf = geraRelatorio.gerarPDF(diretorio, id);
			
		
			OutputStream outStream = response.getOutputStream();
			response.setHeader("Content-Disposition", "inline; filename="+relatorioGabarito+".pdf");
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			outStream.write(pdf, 0, pdf.length);
			outStream.flush();
			outStream.close();
			
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}