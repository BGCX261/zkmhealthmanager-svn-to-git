/*
 * Revision_comiteServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_revision_comite;
import healthmanager.modelo.bean.Revision_comite;
import healthmanager.modelo.dao.Detalle_receta_ripsDao;
import healthmanager.modelo.dao.Detalle_revision_comiteDao;
import healthmanager.modelo.dao.Revision_comiteDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.locator.ServiceLocatorWeb;

@Service("revision_comiteService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Revision_comiteService implements Serializable{

	private String limit;

	@Autowired
	private Revision_comiteDao revision_comiteDao;
	@Autowired
	private Detalle_revision_comiteDao detalleRevisionComiteDao;
	@Autowired
	private Detalle_receta_ripsDao detalle_receta_ripsDao;

	public Revision_comite guardar(Map<String, Object> datos,
			ServiceLocatorWeb serviceLocator) {
		try {
			Revision_comite comite = (Revision_comite) datos.get("comite");
			List<Detalle_revision_comite> lista_detalle = (List<Detalle_revision_comite>) datos
					.get("lista_detalle");
			String accion = (String) datos.get("accion");
			ConsecutivoService consecutivoService = serviceLocator
					.getConsecutivoService();

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_receta = consecutivoService.crearConsecutivo(
						comite.getCodigo_empresa(),
						comite.getCodigo_sucursal(), "REVISION_COMITE");
				comite.setConsecutivo(codigo_receta);
				if (consultar(comite) != null) {
					throw new HealthmanagerException(
							"La revision nro "
									+ codigo_receta
									+ " ya se encuentra en la base de datos actualize el consecutivo REVISION_COMITE");
				}
				revision_comiteDao.crear(comite);
				consecutivoService.actualizarConsecutivo(
						comite.getCodigo_empresa(),
						comite.getCodigo_sucursal(), "REVISION_COMITE",
						comite.getConsecutivo());
			} else {
				revision_comiteDao.actualizar(comite);
			}

			Detalle_revision_comite detalleRevisionComiteAux = new Detalle_revision_comite();
			detalleRevisionComiteAux.setCodigo_empresa(comite
					.getCodigo_empresa());
			detalleRevisionComiteAux.setCodigo_sucursal(comite
					.getCodigo_sucursal());
			detalleRevisionComiteAux
					.setCodigo_revision(comite.getConsecutivo());
			detalleRevisionComiteDao.eliminar(detalleRevisionComiteAux);
			int i = 0;

			// Listado de medicamentos autorizados
			List<String> list_codigo_medicamento = new ArrayList<String>();
			for (Detalle_revision_comite detalleRevisionComite : lista_detalle) {
				detalleRevisionComite.setCodigo_revision(comite
						.getConsecutivo());
				detalleRevisionComite.setConsecutivo(++i + "");
				if (detalleRevisionComiteDao.consultar(detalleRevisionComite) != null) {
					throw new HealthmanagerException("Medicamento  de la fila "
							+ (i + 1) + " se encuentra repetido");
				}
				detalleRevisionComiteDao.crear(detalleRevisionComite);

				if (detalleRevisionComite.getAutorizado().equals("S")) {
					list_codigo_medicamento.add(detalleRevisionComite
							.getCodigo_medicamento());
				}
			}

			// Verificar si ahi medicamento autorizados
			if (!list_codigo_medicamento.isEmpty()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", comite.getCodigo_empresa());
				map.put("codigo_sucursal", comite.getCodigo_sucursal());
				map.put("nro_identificacion", comite.getNro_identificacion());
				map.put("nro_ingreso", comite.getNro_ingreso());
				map.put("medicamentos", list_codigo_medicamento);
				detalle_receta_ripsDao
						.autorizarEntrega(map);
				// log.info("Nro medicamentos autorizados: " +
				// nro_medicamentos_autorizados);
			}
			comite.setLista_detalle(lista_detalle);
			return comite;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Revision_comite revision_comite) {
		try {
			if (consultar(revision_comite) != null) {
				throw new HealthmanagerException(
						"Revision_comite ya se encuentra en la base de datos");
			}
			revision_comiteDao.crear(revision_comite);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Revision_comite revision_comite) {
		try {
			return revision_comiteDao.actualizar(revision_comite);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Revision_comite consultar(Revision_comite revision_comite) {
		try {
			return revision_comiteDao.consultar(revision_comite);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Revision_comite revision_comite) {
		try {
			return revision_comiteDao.eliminar(revision_comite);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Revision_comite> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return revision_comiteDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
