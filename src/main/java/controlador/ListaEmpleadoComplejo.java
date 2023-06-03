package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.EmpleadoDAO;
import entity.Empleado;
import fabricas.Fabrica;

@WebServlet("/listaEmpleadoComplejo")
public class ListaEmpleadoComplejo extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String nombre = req.getParameter("nombre");
			String pais = req.getParameter("pais");
			String estado = req.getParameter("estado");
			String vfechaInicio = req.getParameter("fechaInicio");
			String vfechaFin = req.getParameter("fechaFin");
					
			//Si la fecha de Inicio es vacio
			if (vfechaInicio.equals("")) { vfechaInicio = "1900-01-01"; }
			//Si la fecha de Fin es vacio
			if (vfechaFin.equals("")) { vfechaFin = "2900-12-31"; }
			
			Fabrica fabrica = Fabrica.getFabrica(Fabrica.MYSQL);
			EmpleadoDAO  dao = fabrica.getEmpleado();
			
			List<Empleado> lstEmpleado = dao.listaCompleja(
							nombre+"%", 
							Integer.parseInt(pais), 
							Integer.parseInt(estado),
							Date.valueOf(vfechaInicio), 
							Date.valueOf(vfechaFin));
			
			Gson gson = new Gson();
			String json = gson.toJson(lstEmpleado);

			resp.setContentType("application/json;charset=UTF-8");
			
			PrintWriter out = resp.getWriter();
			out.println(json);
	}
	
}








