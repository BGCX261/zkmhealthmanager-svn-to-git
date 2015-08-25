package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Usuarios;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;
import com.framework.interfaces.IDatosFichaEpidemiologia;

@Service("ficha_epidemiologia_nnService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Ficha_epidemiologia_nnService implements Serializable{

	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;

	@Autowired
	private ConsecutivoService consecutivoService;

	@Autowired
	private Ficha_epidemiologia_historialService ficha_epidemiologia_historialService;

	private String limit;

	public Usuarios usuarios;

	public IDatosFichaEpidemiologia guardar(Map<String, Object> datos) {
		try {
			IDatosFichaEpidemiologia datos_ficha = (IDatosFichaEpidemiologia) datos
					.get("datos_ficha_epidemiologia");
			String accion = (String) datos.get("accion");
			String identificacion = (String) datos.get("identificacion");
			String codigo_medico = (String) datos.get("codigo_medico");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_ficha = consecutivoService.crearConsecutivo(
						datos_ficha.getCodigo_empresa(),
						datos_ficha.getCodigo_sucursal(),
						IConstantes.CONS_FICHA_EPIDEMIOLOGIA);
				codigo_ficha = consecutivoService.getZeroFill(codigo_ficha, 10);
				datos_ficha.setCodigo_ficha(codigo_ficha);
				if (consultar(datos_ficha) != null) {
					throw new HealthmanagerException(
							" Nro "
									+ codigo_ficha
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}

				// log.info("nombre de la ficha "+getNamespaceCompleto(datos_ficha.getClass()));
				// log.info("ficha "+datos_ficha.getClass());

				String numero_ficha = String.valueOf(datos_ficha.getClass());

				String numero_53 = numero_ficha.substring(53);
				// String numero_47 = numero_ficha.substring(47);
				// String numero = numero_53+numero_47;
				// log.info("numero ----> "+numero_53);

				/*
				 * String numero = numero_ficha.substring(46,47);
				 * //log.info("numero ----> "+numero);
				 */

				Ficha_epidemiologia_historial ficha_historial = new Ficha_epidemiologia_historial();
				ficha_historial.setCodigo_empresa(datos_ficha
						.getCodigo_empresa());
				ficha_historial.setCodigo_sucursal(datos_ficha
						.getCodigo_sucursal());
				ficha_historial.setConsecutivo(datos_ficha.getCodigo_ficha());
				ficha_historial.setCodigo_ficha(numero_53);
				ficha_historial.setCodigo_historia(datos_ficha
						.getCodigo_historia());
				ficha_historial.setIdentificacion(identificacion);
				ficha_historial.setCodigo_diagnostico(datos_ficha
						.getCodigo_diagnostico());
				ficha_historial.setCodigo_medico(codigo_medico);
				ficha_historial.setFecha_creacion(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				ficha_historial.setVia_ingreso(datos_ficha.getVia_ingreso());

				// log.info("---------------->"+ficha_historial);

				ficha_epidemiologia_historialService.crear(ficha_historial);

				sqlSession.insert(getNamespaceCompleto(datos_ficha.getClass())
						+ ".crear", datos_ficha);
				consecutivoService.actualizarConsecutivo(
						datos_ficha.getCodigo_empresa(),
						datos_ficha.getCodigo_sucursal(),
						IConstantes.CONS_FICHA_EPIDEMIOLOGIA,
						datos_ficha.getCodigo_ficha());

			} else {
				sqlSession.update(getNamespaceCompleto(datos_ficha.getClass())
						+ ".actualizar", datos_ficha);
			}

			return datos_ficha;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		} finally {
			// sqlSession.close();
		}
	}

	public void crear(IDatosFichaEpidemiologia datos_ficha) {
		try {
			if (consultar(datos_ficha) != null) {
				throw new HealthmanagerException(
						"Ficha_epidemiologia ya se encuentra en la base de datos");
			}
			sqlSession.insert(getNamespaceCompleto(datos_ficha.getClass())
					+ ".crear", datos_ficha);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		} finally {
			// sqlSession.close();
		}
	}

	public int actualizar(IDatosFichaEpidemiologia datos_ficha) {
		try {
			return sqlSession.update(
					getNamespaceCompleto(datos_ficha.getClass())
							+ ".actualizar", datos_ficha);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		} finally {
			// sqlSession.close();
		}
	}

	public IDatosFichaEpidemiologia consultar(
			IDatosFichaEpidemiologia datos_ficha) {
		try {
			return sqlSession
					.selectOne(getNamespaceCompleto(datos_ficha.getClass())
							+ ".consultar", datos_ficha);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		} finally {
			// sqlSession.close();
		}
	}

	public int eliminar(IDatosFichaEpidemiologia datos_ficha) {
		try {
			return sqlSession.delete(
					getNamespaceCompleto(datos_ficha.getClass()) + ".eliminar",
					datos_ficha);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		} finally {
			// sqlSession.close();
		}
	}

	public <T extends IDatosFichaEpidemiologia> List<T> listar(
			Class<T> tipo_ficha, Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return sqlSession.selectList(getNamespaceCompleto(tipo_ficha)
					+ ".listar", parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		} finally {
			// sqlSession.close();
		}
	}

	private String getNamespaceCompleto(Class<?> clase) {
		return "healthmanager.modelo.dao." + clase.getSimpleName() + "Dao";
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
