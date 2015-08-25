/*
 * ArticuloServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Copago_estrato;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.dao.Copago_estratoDao;
import healthmanager.modelo.dao.Manuales_tarifariosDao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.bean.Tarifa_medicamento;
import contaweb.modelo.dao.ArticuloDao;
import contaweb.modelo.dao.Bodega_articuloDao;
import contaweb.modelo.dao.Tarifa_medicamentoDao;

@Service("articuloService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ArticuloService implements Serializable{

	private String limit;

	@Autowired
	private ArticuloDao articuloDao;
	@Autowired
	private Bodega_articuloDao bodega_articuloDao;
	@Autowired
	private Manuales_tarifariosDao manuales_tarifariosDao;
	@Autowired
	private Copago_estratoDao copago_estratoDao;
	@Autowired
	private Tarifa_medicamentoDao tarifa_medicamentoDao;

	public void crear(Articulo articulo) {
		try {
			if (consultar(articulo) != null) {
				throw new HealthmanagerException(
						"Articulo ya se encuentra en la base de datos");
			}
			articuloDao.crear(articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Articulo articulo) {
		try {
			return articuloDao.actualizar(articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Articulo consultar(Articulo articulo) {
		try {
			return articuloDao.consultar(articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Articulo articulo) {
		try {
			return articuloDao.eliminar(articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Articulo> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return articuloDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public void crearExistencias(Articulo articulo, boolean afecta_kardex) {
		try {
			crear(articulo);
			actualizarExistencias(afecta_kardex, articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizarExistencias(Articulo articulo, boolean afecta_kardex) {
		try {
			int resultado = actualizar(articulo);
			actualizarExistencias(afecta_kardex, articulo);
			return resultado;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me va ha permitir actualizar la cantidad siempre y cuando no
	 * trabaje con kardexew
	 * */
	private void actualizarExistencias(boolean afecta_kardex, Articulo articulo) {
		if (!afecta_kardex) {
			Map<String, Object> map_param = new HashMap<String, Object>();
			map_param.put("codigo_empresa", articulo.getCodigo_empresa());
			map_param.put("codigo_sucursal", articulo.getCodigo_sucursal());
			map_param.put("codigo_articulo", articulo.getCodigo_articulo());
			map_param.put("codigo_bodega", "01");
			if (!bodega_articuloDao.existe(map_param)) {
				Bodega_articulo bodega_articulo = new Bodega_articulo();
				bodega_articulo.setCodigo_bodega("01");
				bodega_articulo.setCodigo_empresa(articulo.getCodigo_empresa());
				bodega_articulo.setCodigo_sucursal(articulo
						.getCodigo_sucursal());
				bodega_articulo.setCodigo_articulo(articulo
						.getCodigo_articulo());
				bodega_articulo.setCantidad(1000);
				bodega_articulo.setCosto(0d);
				bodega_articulo.setCreacion_date(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				bodega_articulo.setUltimo_update(new Timestamp(Calendar
						.getInstance().getTimeInMillis()));
				bodega_articulo.setCreacion_user(articulo.getUltimo_user());
				bodega_articulo.setUltimo_user(articulo.getUltimo_user());
				bodega_articuloDao.crear(bodega_articulo);
			}
		}
	}

	public int actualizarPorDmanda(Articulo articulo) {
		try {
			return articuloDao.actualizarPorDmanda(articulo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Map<String, Object> getMedicamentos(Map<String, Object> params) {
		// parametros
		Via_ingreso_contratadas via_ingreso_contratadas = (Via_ingreso_contratadas) params
				.get("via_ingreso_contratadas");
		String codigo_articulo = (String) params.get("codigo_articulo");
		String estrato = (String) params.get("estrato"); // no es obligatorio
															// enviarlo sino se
															// utiliza va null
		String grupo = (String) params.get("grupo");

		Manuales_tarifarios manuales_tarifarios = new Manuales_tarifarios();
		manuales_tarifarios.setConsecutivo(via_ingreso_contratadas
				.getConsecutivo_manual());
		manuales_tarifarios.setCodigo_empresa(via_ingreso_contratadas
				.getCodigo_empresa());
		manuales_tarifarios.setCodigo_sucursal(via_ingreso_contratadas
				.getCodigo_sucursal());
		manuales_tarifarios.setCodigo_administradora(via_ingreso_contratadas
				.getCodigo_administradora());
		manuales_tarifarios
				.setId_contrato(via_ingreso_contratadas.getId_plan());
		manuales_tarifarios = manuales_tarifariosDao
				.consultar(manuales_tarifarios);

		Articulo art = new Articulo();
		art.setCodigo_empresa(manuales_tarifarios.getCodigo_empresa());
		art.setCodigo_sucursal(manuales_tarifarios.getCodigo_sucursal());
		art.setCodigo_articulo(codigo_articulo);
		art = articuloDao.consultar(art);

		if (art != null) {
			// Grupo1 grupo1 = new Grupo1();
			// grupo1.setCodigo_empresa(art.getCodigo_empresa());
			// grupo1.setCodigo_sucursal(art.getCodigo_sucursal());
			// grupo1.setCodigo(art.getGrupo1());
			// grupo1 = grupo1Service.consultar(grupo1);

			if (grupo == null) {
				grupo = art.getGrupo1();
			}

			double valor = (int) art.getPrecio1();
			double valor_real = valor;
			double copago = 0;

			if (estrato != null) {
				Copago_estrato cop = new Copago_estrato();
				cop.setCodigo_empresa(manuales_tarifarios.getCodigo_empresa());
				cop.setCodigo_sucursal(manuales_tarifarios.getCodigo_sucursal());
				cop.setEstrato(estrato);
				cop = copago_estratoDao.consultar(cop);
				copago = (cop != null ? (int) (valor) : 0);
			}

			double descuento = 0, incremento = 0;
			if (manuales_tarifarios != null) {
				if (grupo.equalsIgnoreCase("01")
						|| art.getGrupo1().equalsIgnoreCase("01")) {
					if (manuales_tarifarios.getTipo_medicamento()
							.equalsIgnoreCase("01")) {
						descuento = (int) (valor * (manuales_tarifarios
								.getMedicamentos() / 100));
						valor -= descuento;
					} else {
						incremento = (int) (valor * (manuales_tarifarios
								.getMedicamentos() / 100));
						valor += incremento;
					}
				} else if (grupo.equalsIgnoreCase("02")
						|| art.getGrupo1().equalsIgnoreCase("02")) {
					if (manuales_tarifarios.getTipo_servicio()
							.equalsIgnoreCase("01")) {
						descuento = (int) (valor * (manuales_tarifarios
								.getServicios() / 100));
						valor -= descuento;
					} else {
						incremento = (int) (valor * (manuales_tarifarios
								.getServicios() / 100));
						valor += incremento;
					}
				}

				if (manuales_tarifarios.getTarifa_especial().equals("S")) {
					Tarifa_medicamento tarifa = new Tarifa_medicamento();
					tarifa.setCodigo_empresa(manuales_tarifarios
							.getCodigo_empresa());
					tarifa.setCodigo_sucursal(manuales_tarifarios
							.getCodigo_sucursal());
					tarifa.setCodigo_administradora(manuales_tarifarios
							.getCodigo_administradora());
					tarifa.setId_plan(manuales_tarifarios.getId_contrato());
					tarifa.setCodigo_pcd(art.getCodigo_articulo());
					tarifa = tarifa_medicamentoDao.consultar(tarifa);
					if (tarifa != null) {
						valor = tarifa.getValor();	
						valor_real = valor;
					}
				}
			}

			/* verificamos autorizacion de medicamento */
			boolean autorizado = (((art.getPos() + "").equalsIgnoreCase("S") || (art
					.getPyp() + "").equalsIgnoreCase("S")) && (art
					.getAlto_costo() + "").equalsIgnoreCase("N"));

			Map<String, Object> medicamento = new HashMap<String, Object>();
			medicamento.put("codigo_empresa",
					manuales_tarifarios.getCodigo_empresa());
			medicamento.put("codigo_sucursal",
					manuales_tarifarios.getCodigo_sucursal());
			medicamento.put("codigo_articulo", art.getCodigo_articulo());
			medicamento.put("valor_unitario", valor);
			medicamento.put("valor_total", valor);
			medicamento.put("descuento", descuento);
			medicamento.put("incremento", incremento);
			medicamento.put("valor_real", valor_real);
			medicamento.put("copago", copago);
			medicamento.put("autorizado", autorizado);
			medicamento.put("tipo", art.getGrupo1());
			medicamento.put("bodega", "01");
			medicamento.put("pyp", art.getPyp() + "");
			medicamento.put("nombre1", art.getNombre1());
			medicamento.put("referencia", art.getReferencia());
			medicamento.put("unidad_medida", art.getUnidad_medida());
			medicamento.put("concentracion", art.getUnidad_concentracion());
			medicamento.put("facturable", art.getFacturable());
			medicamento.put("pos", art.getPos());
			medicamento.put("cantidad_maxima", art.getCantidad_maxima());
			return medicamento;
		} else {
			return null;
		}
	}

}
