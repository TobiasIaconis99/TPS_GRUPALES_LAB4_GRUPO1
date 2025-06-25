package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import entidad.Localidad;
import negocio.LocalidadNegocio;
import negocioImpl.LocalidadNegocioImpl;

@WebServlet("/ServletLocalidad")
public class ServletLocalidad extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private LocalidadNegocio localidadNegocio = new LocalidadNegocioImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String idProvinciaParam = request.getParameter("idProvincia");

		if (idProvinciaParam == null || idProvinciaParam.trim().isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"error\":\"El ID de la provincia es necesario.\"}");
			return;
		}

		try {
			int idProvincia = Integer.parseInt(idProvinciaParam);
			List<Localidad> listaLocalidades = localidadNegocio.listarPorProvincia(idProvincia);

			StringBuilder jsonBuilder = new StringBuilder("[");
			for (int i = 0; i < listaLocalidades.size(); i++) {
				Localidad loc = listaLocalidades.get(i);
				String nombreEscapado = loc.getNombreLocalidad().replace("\"", "\\\"");

				jsonBuilder.append("{")
					.append("\"idLocalidad\":").append(loc.getIdLocalidad()).append(",")
					.append("\"nombreLocalidad\":\"").append(nombreEscapado).append("\"")
					.append("}");

				if (i < listaLocalidades.size() - 1) {
					jsonBuilder.append(",");
				}
			}
			jsonBuilder.append("]");

			response.getWriter().write(jsonBuilder.toString());

		} catch (NumberFormatException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"error\":\"El ID no es valido.\"}");
		}
	}
}