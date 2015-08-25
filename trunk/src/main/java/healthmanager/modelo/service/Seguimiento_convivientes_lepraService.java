/*
 * Seguimiento_convivientes_lepraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Seguimiento_convivientes_lepra;
import healthmanager.modelo.dao.Seguimiento_convivientes_lepraDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

import contaweb.modelo.service.ConsecutivoService;

@Service("seguimiento_convivientes_lepraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Seguimiento_convivientes_lepraService implements Serializable{

	@Autowired
	private Seguimiento_convivientes_lepraDao seguimiento_convivientes_lepraDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;

	public Seguimiento_convivientes_lepra guardarDatos(Map<String, Object> datos) {
		try {

			Seguimiento_convivientes_lepra seguimiento_convivientes_lepra = (Seguimiento_convivientes_lepra) datos
					.get("seguimiento_convivientes_lepra");
			List<Seguimiento_convivientes_lepra> lista_detalle = (List<Seguimiento_convivientes_lepra>) datos
					.get("lista_detalle");
			// String accion = (String) datos.get("accion");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa",
					seguimiento_convivientes_lepra.getCodigo_empresa());
			parameters.put("codigo_sucursal",
					seguimiento_convivientes_lepra.getCodigo_sucursal());
			parameters.put("codigo_ficha",
					seguimiento_convivientes_lepra.getCodigo_ficha());
			seguimiento_convivientes_lepraDao.eliminar(parameters);

			if (lista_detalle != null) {

				// log.info("2 >>>> " + lista_detalle);
				int i = 1;
				for (Seguimiento_convivientes_lepra lepra : lista_detalle) {

					String codigo_conviviente = i + "";

					lepra.setCodigo_empresa(seguimiento_convivientes_lepra
							.getCodigo_empresa());
					lepra.setCodigo_sucursal(seguimiento_convivientes_lepra
							.getCodigo_sucursal());
					lepra.setCodigo_ficha(seguimiento_convivientes_lepra
							.getCodigo_ficha());
					lepra.setCodigo_conviviente(codigo_conviviente);

					// log.info("LEPRA >>>> " + lepra);
					seguimiento_convivientes_lepraDao.crear(lepra);

					consecutivoService.actualizarConsecutivo(
							lepra.getCodigo_empresa(),
							lepra.getCodigo_sucursal(),
							IConstantes.CONS_CONTROL_CONVIVIENTES,
							lepra.getCodigo_conviviente());
					i++;
					if (i > 4) {
						i = 1;
					}
				}

			}

			seguimiento_convivientes_lepra = consultar(seguimiento_convivientes_lepra);

			return seguimiento_convivientes_lepra;

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(
			Seguimiento_convivientes_lepra seguimiento_convivientes_lepra) {
		try {
			if (consultar(seguimiento_convivientes_lepra) != null) {
				throw new HealthmanagerException(
						"Seguimiento_convivientes_lepra ya se encuentra en la base de datos");
			}
			seguimiento_convivientes_lepraDao
					.crear(seguimiento_convivientes_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Seguimiento_convivientes_lepra seguimiento_convivientes_lepra) {
		try {
			return seguimiento_convivientes_lepraDao
					.actualizar(seguimiento_convivientes_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Seguimiento_convivientes_lepra consultar(
			Seguimiento_convivientes_lepra seguimiento_convivientes_lepra) {
		try {
			return seguimiento_convivientes_lepraDao
					.consultar(seguimiento_convivientes_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Map<String, Object> parameters) {
		try {
			return seguimiento_convivientes_lepraDao.eliminar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Seguimiento_convivientes_lepra> listar(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return seguimiento_convivientes_lepraDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return seguimiento_convivientes_lepraDao.existe(parameters);
	}

}