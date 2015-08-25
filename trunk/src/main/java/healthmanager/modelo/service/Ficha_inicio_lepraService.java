/*
 * Ficha_inicio_lepraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Control_ficha_inicio_lepra;
import healthmanager.modelo.bean.Ficha_inicio_lepra;
import healthmanager.modelo.dao.Control_ficha_inicio_lepraDao;
import healthmanager.modelo.dao.Ficha_inicio_lepraDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("ficha_inicio_lepraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Ficha_inicio_lepraService implements Serializable{

	@Autowired
	private Ficha_inicio_lepraDao ficha_inicio_lepraDao;
	@Autowired
	private Control_ficha_inicio_lepraDao control_ficha_inicio_lepraDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;

	public void crear(Ficha_inicio_lepra ficha_inicio_lepra) {
		try {
			String codigo_ficha = consecutivoService.crearConsecutivo(
					ficha_inicio_lepra.getCodigo_empresa(),
					ficha_inicio_lepra.getCodigo_sucursal(),
					IConstantes.CONS_FICHA_INICIO_LEPRA);
			ficha_inicio_lepra.setCodigo_ficha(consecutivoService.getZeroFill(
					codigo_ficha, 10));
			if (consultar(ficha_inicio_lepra) != null) {
				throw new HealthmanagerException(
						"Ficha_inicio_lepra ya se encuentra en la base de datos");
			}
			ficha_inicio_lepraDao.crear(ficha_inicio_lepra);
			consecutivoService.actualizarConsecutivo(
					ficha_inicio_lepra.getCodigo_empresa(),
					ficha_inicio_lepra.getCodigo_sucursal(),
					IConstantes.CONS_FICHA_INICIO_LEPRA,
					ficha_inicio_lepra.getCodigo_ficha());
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Ficha_inicio_lepra guardarDatos(Map<String, Object> datos) {
		try {
			Ficha_inicio_lepra ficha_inicio_lepra = (Ficha_inicio_lepra) datos
					.get("ficha_inicio_lepra");
			List<Control_ficha_inicio_lepra> lista_detalle = (List<Control_ficha_inicio_lepra>) datos
					.get("lista_detalle");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_ficha = consecutivoService.crearConsecutivo(
						ficha_inicio_lepra.getCodigo_empresa(),
						ficha_inicio_lepra.getCodigo_sucursal(),
						IConstantes.CONS_FICHA_INICIO_LEPRA);
				ficha_inicio_lepra.setCodigo_ficha(consecutivoService
						.getZeroFill(codigo_ficha, 10));

				if (consultar(ficha_inicio_lepra) != null) {
					throw new HealthmanagerException(
							"Codigo de ficha ya existe actualizar consecutivo "
									+ ficha_inicio_lepra);
				}

				ficha_inicio_lepraDao.crear(ficha_inicio_lepra);

				consecutivoService.actualizarConsecutivo(
						ficha_inicio_lepra.getCodigo_empresa(),
						ficha_inicio_lepra.getCodigo_sucursal(),
						IConstantes.CONS_FICHA_INICIO_LEPRA,
						ficha_inicio_lepra.getCodigo_ficha());
			} else {
				ficha_inicio_lepraDao.actualizar(ficha_inicio_lepra);

				Control_ficha_inicio_lepra control_ficha_inicio_lepraAux = new Control_ficha_inicio_lepra();
				control_ficha_inicio_lepraAux
						.setCodigo_empresa(ficha_inicio_lepra
								.getCodigo_empresa());
				control_ficha_inicio_lepraAux
						.setCodigo_sucursal(ficha_inicio_lepra
								.getCodigo_sucursal());
				control_ficha_inicio_lepraAux
						.setCodigo_ficha(ficha_inicio_lepra.getCodigo_ficha());
				control_ficha_inicio_lepraDao
						.eliminar(control_ficha_inicio_lepraAux);
			}

			for (Control_ficha_inicio_lepra control_ficha_inicio_lepra : lista_detalle) {
				control_ficha_inicio_lepra.setCodigo_ficha(ficha_inicio_lepra
						.getCodigo_ficha());
				if (control_ficha_inicio_lepraDao
						.consultar(control_ficha_inicio_lepra) != null) {
					throw new HealthmanagerException("Control "
							+ new SimpleDateFormat("dd/MM/yyyy")
							+ " ya se encuentra registrado");
				}
				control_ficha_inicio_lepraDao.crear(control_ficha_inicio_lepra);
			}

			return ficha_inicio_lepra;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Ficha_inicio_lepra ficha_inicio_lepra) {
		try {
			return ficha_inicio_lepraDao.actualizar(ficha_inicio_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Ficha_inicio_lepra consultar(Ficha_inicio_lepra ficha_inicio_lepra) {
		try {
			return ficha_inicio_lepraDao.consultar(ficha_inicio_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Ficha_inicio_lepra ficha_inicio_lepra) {
		try {
			return ficha_inicio_lepraDao.eliminar(ficha_inicio_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Ficha_inicio_lepra> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return ficha_inicio_lepraDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return ficha_inicio_lepraDao.existe(parameters);
	}

	public boolean existe_paciente_lepra(Map<String, Object> parameters) {
		return ficha_inicio_lepraDao.existe_paciente_lepra(parameters);
	}

}