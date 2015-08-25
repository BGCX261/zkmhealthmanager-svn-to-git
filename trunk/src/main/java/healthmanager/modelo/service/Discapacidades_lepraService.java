/*
 * Discapacidades_lepraServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Discapacidades_lepra;
import healthmanager.modelo.dao.Discapacidades_lepraDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("discapacidades_lepraService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Discapacidades_lepraService implements Serializable{
	
	private static final Logger log = Logger.getLogger(Discapacidades_lepraService.class);

	@Autowired
	private Discapacidades_lepraDao discapacidades_lepraDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	private String limit;

	public Discapacidades_lepra guardar(Map<String, Object> datos) {
		try {

			Discapacidades_lepra discapacidades_lepra = (Discapacidades_lepra) datos
					.get("discapacidades_lepra");

			List<Discapacidades_lepra> listado_discapacidades = (List<Discapacidades_lepra>) datos
					.get("listado_discapacidades");

			// log.info("discapacidades_lepra"+discapacidades_lepra);
			log.debug("listado_discapacidades"+listado_discapacidades);

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa",
					discapacidades_lepra.getCodigo_empresa());
			parameters.put("codigo_sucursal",
					discapacidades_lepra.getCodigo_sucursal());
			parameters.put("identificacion",
					discapacidades_lepra.getIdentificacion());

			// log.info("1"+discapacidades_lepra.getCodigo_empresa() + "-" +
			// discapacidades_lepra.getCodigo_sucursal() + "-" +
			// discapacidades_lepra.getIdentificacion() );
			discapacidades_lepraDao.eliminar(parameters);

			if (listado_discapacidades != null) {

				// log.info("2 >>>> "+listado_discapacidades);

				for (Discapacidades_lepra lepra : listado_discapacidades) {

					String codigo_historia = consecutivoService
							.crearConsecutivo(
									discapacidades_lepra.getCodigo_empresa(),
									discapacidades_lepra.getCodigo_sucursal(),
									IConstantes.CONS_DISCAPACIDADES_LEPRA);

					codigo_historia = consecutivoService.getZeroFill(
							codigo_historia, 10);

					// log.info("codigo_historia >>>"+codigo_historia);

					lepra.setCodigo_empresa(discapacidades_lepra
							.getCodigo_empresa());
					lepra.setCodigo_sucursal(discapacidades_lepra
							.getCodigo_sucursal());
					lepra.setCodigo_historia(codigo_historia);
					lepra.setIdentificacion(discapacidades_lepra
							.getIdentificacion());

					// log.info("LEPRA >>>> "+lepra);

					discapacidades_lepraDao.crear(lepra);

					consecutivoService.actualizarConsecutivo(
							lepra.getCodigo_empresa(),
							lepra.getCodigo_sucursal(),
							IConstantes.CONS_DISCAPACIDADES_LEPRA,
							lepra.getCodigo_historia());

				}
			}

			return discapacidades_lepra;

		} catch (Exception e) {

			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Discapacidades_lepra discapacidades_lepra) {
		try {
			if (consultar(discapacidades_lepra) != null) {
				throw new HealthmanagerException(
						"Discapacidades_lepra ya se encuentra en la base de datos");
			}
			discapacidades_lepraDao.crear(discapacidades_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Discapacidades_lepra discapacidades_lepra) {
		try {
			return discapacidades_lepraDao.actualizar(discapacidades_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Discapacidades_lepra consultar(
			Discapacidades_lepra discapacidades_lepra) {
		try {
			return discapacidades_lepraDao.consultar(discapacidades_lepra);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Map<String, Object> parameters) {
		try {
			return discapacidades_lepraDao.eliminar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Discapacidades_lepra> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return discapacidades_lepraDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Discapacidades_lepra> listar_pacientes_lepra(
			Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return discapacidades_lepraDao.listar_pacientes_lepra(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return discapacidades_lepraDao.existe(parameters);
	}

}