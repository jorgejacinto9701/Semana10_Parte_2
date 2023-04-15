package controlador;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ClienteDAO;
import entity.Categoria;
import entity.Cliente;
import entity.Respuesta;
import fabricas.Fabrica;

@WebServlet("/registraCliente")
public class ClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			String vnombre = req.getParameter("nombre");
			String vdni = req.getParameter("dni");
			String vcategoria = req.getParameter("categoria");

			Categoria objCategoria = new Categoria();
			objCategoria.setIdCategoria(Integer.parseInt(vcategoria));
			
			Cliente objCliente = new Cliente();
			objCliente.setNombre(vnombre);
			objCliente.setDni(vdni);
			objCliente.setCategoria(objCategoria);
			
			Fabrica fabrica = Fabrica.getFabrica(Fabrica.MYSQL);
			ClienteDAO dao = fabrica.getCliente();
		
			int insertados = dao.insertaCliente(objCliente);
			
			
			Respuesta objRespuesta = new Respuesta();
			if (insertados > 0) {
				objRespuesta.setMensaje("Registro exitoso");
			}else {
				objRespuesta.setMensaje("Error en el registro");
			}
			
			Gson gson = new Gson();
			String json = gson.toJson(objRespuesta);
			
			resp.setContentType("application/json;charset=UTF-8");
			
			PrintWriter out = resp.getWriter();
			out.println(json);
	}

}