/*
 * Nota_enfermeriaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_nota_enf;
import healthmanager.modelo.bean.Nota_enfermeria;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Detalle_nota_enfDao;
import healthmanager.modelo.dao.Nota_enfermeriaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("nota_enfermeriaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Nota_enfermeriaService implements Serializable{

	@Autowired
	private Nota_enfermeriaDao nota_enfermeriaDao;
	@Autowired
	private Detalle_nota_enfDao detalle_nota_enfDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Nota_enfermeria guardar(Map<String, Object> datos) {
		try {
			Nota_enfermeria nota_enfermeria = (Nota_enfermeria) datos
					.get("nota_enfermeria");
			List<Map<String, Object>> lista_detalle_map = (List<Map<String, Object>>) datos
					.get("lista_detalle");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_nota = consecutivoService
						.crearConsecutivo(
								nota_enfermeria.getCodigo_empresa(),
								nota_enfermeria.getCodigo_sucursal(),
								"NOTA_ENFERMERIA");
				nota_enfermeria.setCodigo_nota(codigo_nota);
				if (consultar(nota_enfermeria) != null) {
					throw new HealthmanagerException(
							"Nota "
									+ (nota_enfermeria.getTipo().equals("1") ? "enfermería"
											: "aclaratoria")
									+ " nro "
									+ codigo_nota
									+ " ya se encuentra en la base de datos actualize el consecutivo de la nota");
				}
				nota_enfermeriaDao.crear(nota_enfermeria);
				consecutivoService.actualizarConsecutivo(
						nota_enfermeria.getCodigo_empresa(),
						nota_enfermeria.getCodigo_sucursal(),
						"NOTA_ENFERMERIA", nota_enfermeria.getCodigo_nota());
			} else {
				nota_enfermeriaDao.actualizar(nota_enfermeria);
			}

			// Registramos las notas //
			Detalle_nota_enf detalle_evolucionAux = new Detalle_nota_enf();
			detalle_evolucionAux.setCodigo_empresa(nota_enfermeria
					.getCodigo_empresa());
			detalle_evolucionAux.setCodigo_sucursal(nota_enfermeria
					.getCodigo_sucursal());
			detalle_evolucionAux.setCodigo_nota(nota_enfermeria
					.getCodigo_nota());
			detalle_nota_enfDao.eliminar(detalle_evolucionAux);
			int i = 0;
			List<Detalle_nota_enf> lista_detalle_ = new LinkedList<Detalle_nota_enf>();
			for (Map<String, Object> bean : lista_detalle_map) {
				String consecutivo = i + "";
				Timestamp fecha = (Timestamp) bean.get("fecha");
				String nota = (String) bean.get("nota");
				String asunto = (String) bean.get("asunto");

				Detalle_nota_enf detalle_nota_enf = new Detalle_nota_enf();
				detalle_nota_enf.setCodigo_empresa(nota_enfermeria
						.getCodigo_empresa());
				detalle_nota_enf.setCodigo_sucursal(nota_enfermeria
						.getCodigo_sucursal());
				detalle_nota_enf.setCodigo_nota("");
				detalle_nota_enf.setConsecutivo(consecutivo);
				detalle_nota_enf.setFecha(fecha);
				detalle_nota_enf.setNota(nota.toUpperCase());
				detalle_nota_enf.setAsunto(asunto != null ? asunto
						.toUpperCase() : "");
				detalle_nota_enf.setCodigo_nota(nota_enfermeria
						.getCodigo_nota());
				lista_detalle_.add(detalle_nota_enf);
				if (detalle_nota_enfDao.consultar(detalle_nota_enf) != null) {
					throw new HealthmanagerException(
							"Nota de "
									+ (nota_enfermeria.getTipo().equals("1") ? "enfermería"
											: "aclaratoria") + "  de la fila "
									+ (i + 1) + " se encuentra repetido");
				}
				detalle_nota_enfDao.crear(detalle_nota_enf);
				i++;
			}
			nota_enfermeria.setLista_detalle(lista_detalle_);
			return nota_enfermeria;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Nota_enfermeria nota_enfermeria) {
		try {
			if (consultar(nota_enfermeria) != null) {
				throw new HealthmanagerException(
						""
								+ (nota_enfermeria.getTipo().equals("1") ? "Nota enfermería"
										: "Nota aclaratoria")
								+ " ya se encuentra en la base de datos");
			}
			nota_enfermeriaDao.crear(nota_enfermeria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Nota_enfermeria nota_enfermeria) {
		try {
			return nota_enfermeriaDao.actualizar(nota_enfermeria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Nota_enfermeria consultar(Nota_enfermeria nota_enfermeria) {
		try {
			return nota_enfermeriaDao.consultar(nota_enfermeria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Nota_enfermeria nota_enfermeria) {
		try {
			return nota_enfermeriaDao.eliminar(nota_enfermeria);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Nota_enfermeria> listar(Map<String, Object> parameter) {
		try {
			return nota_enfermeriaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
