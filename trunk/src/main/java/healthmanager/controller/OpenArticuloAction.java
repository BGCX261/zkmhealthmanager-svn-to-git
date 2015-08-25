/**
 * 
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Copago_estrato;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.Copago_estratoService;
import healthmanager.modelo.service.Plantilla_pypService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.interfaces.ISeleccionarComponente;
import com.framework.res.VerificacionOnlyPyp;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Articulo;
import contaweb.modelo.bean.Bodega_articulo;
import contaweb.modelo.bean.Grupo1;
import contaweb.modelo.bean.Tarifa_medicamento;
import contaweb.modelo.service.ArticuloService;
import contaweb.modelo.service.Bodega_articuloService;
import contaweb.modelo.service.Grupo1Service;
import contaweb.modelo.service.Tarifa_medicamentoService;

/**
 * @author ferney
 * 
 */
public class OpenArticuloAction extends GeneralComposer {

	private static Logger log = Logger.getLogger(OpenArticuloAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@WireVariable
	private Configuracion_admision_ingresoService configuracion_admision_ingresoService;
	@WireVariable
	private ArticuloService articuloService;
	@WireVariable
	private Bodega_articuloService bodega_articuloService;
	@WireVariable
	private Plantilla_pypService plantilla_pypService;
	@WireVariable
	private Grupo1Service grupo1Service;
	@WireVariable
	private Copago_estratoService copago_estratoService;
	@WireVariable
	private Tarifa_medicamentoService tarifa_medicamentoService;
	@WireVariable
	private CitasService citasService;

	// Componentes //
	@View
	private Textbox tbxValue;
	@View
	private Listbox listboxResultado;
	@View
	private Listheader colValor;
	@View
	private Listheader colExist;

	private List<Articulo> lista_articulos;
	private Map<String, Object> parametrosAction;

	private Window action;
	private Admision admision;

	@View
	private Auxheader auxheaderResultado;

	private Map<String, String> seleccionados = new HashMap<String, String>();

	private ISeleccionarComponente seleccionar_componente;
	private Manuales_tarifarios manual_tarifario;

	@Override
	public void init() throws Exception {
		initOpenArticulo();
		listboxResultado.setAttribute("org.zkoss.zul.listbox.rod", true);
	}

	@SuppressWarnings("unchecked")
	public void initOpenArticulo() throws Exception {
		try {
			// lbxDetalle.getItems().clear();
			action = (Window) this.getParent();
			parametrosAction = (Map<String, Object>)Executions.getCurrent().getArg();
			if (parametrosAction == null) {
				parametrosAction = new HashMap<String, Object>();
			}
			admision = (Admision) parametrosAction.get("admision");
			if (admision != null) {
				manual_tarifario = ServiciosDisponiblesUtils
						.getManuales_tarifarios(admision);
			}

			if (parametrosAction.get("manuales_tarifarios") != null) {
				manual_tarifario = (Manuales_tarifarios) parametrosAction
						.get("manuales_tarifarios");
			}

			if (parametrosAction.get("ocultaExist") != null) {
				colExist.setVisible(false);
				auxheaderResultado
						.setColspan(auxheaderResultado.getColspan() - 1);
			}
			if (parametrosAction.get("ocultaValor") != null) {
				colValor.setVisible(false);
				auxheaderResultado
						.setColspan(auxheaderResultado.getColspan() - 1);
			}
			seleccionados.clear();
			if (parametrosAction.get("seleccionados") != null) {
				List<String> lista_seleccionado = (List<String>) parametrosAction
						.get("seleccionados");
				for (String seleccion : lista_seleccionado) {
					seleccionados.put(seleccion, seleccion);
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String value = tbxValue.getValue().toUpperCase().trim();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", value);

			if (parametrosAction.containsKey("codigos_actividades")) {
				parameters.put("actividades",
						parametrosAction.get("codigos_actividades"));
			}

			// Esto es para filtro de Pyp
			if (parametrosAction.containsKey("pyp")) {
				parameters.put("pyp", parametrosAction.get("pyp"));
			}

			if (admision != null) {
				if (VerificacionOnlyPyp.onlyPyp(admision, citasService)) {
					parameters.put("pyp", "S");
				}

				Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
				configuracion_admision_ingreso
						.setCodigo_empresa(codigo_empresa);
				configuracion_admision_ingreso
						.setCodigo_sucursal(codigo_sucursal);
				configuracion_admision_ingreso.setVia_ingreso(admision
						.getVia_ingreso());

				configuracion_admision_ingreso = configuracion_admision_ingresoService
						.consultar(configuracion_admision_ingreso);

				if (configuracion_admision_ingreso == null) {
					throw new ValidacionRunTimeException(
							"La via de ingreso "
									+ admision.getVia_ingreso()
									+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
				}

				if (parametros_empresa != null)
					if (parametros_empresa
							.getFiltrar_actividades_ordenamiento_programas()
							.equals("S")
							&& configuracion_admision_ingreso.getEs_pyp()) {
						String servicios[] = ServiciosDisponiblesUtils
								.getServicios(admision);
						if (servicios != null) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("area_intervencion_in", servicios);
							List<Plantilla_pyp> plantilla_pyps = plantilla_pypService
									.listar(map);
							List<String> codigos_actividades = new ArrayList<String>();
							for (Plantilla_pyp plantilla_pyp : plantilla_pyps) {
								List<String> codigos = Utilidades
										.validarCodigo(plantilla_pyp
												.getCodigo_pdc());
								for (String codig : codigos) {
									codigos_actividades.add(codig);
								}
							}
							parameters.put("actividades", codigos_actividades);
						}
					}
			}

			if (parametrosAction.containsKey("grupo")) {
				parameters
						.put("grupo1", (String) parametrosAction.get("grupo"));
			}

			if (parametrosAction.containsKey("grupo2")) {
				parameters.put("grupo2",
						(String) parametrosAction.get("grupo2"));
			}

			if (parametrosAction.containsKey("grupo1_in")) {
				parameters.put("grupo1_in", parametrosAction.get("grupo1_in"));
			}

			articuloService.setLimit("limit 25 offset 0");
			
			lista_articulos = articuloService.listar(parameters);

			listboxResultado.getItems().clear();

			int i = 0;
			for (Articulo articulo : lista_articulos) {
				listboxResultado.appendChild(crearFilas(articulo, this, i));
				i++;
			}

			listboxResultado.setVisible(true);
			listboxResultado.setMold("paging");
			listboxResultado.setPageSize(20);

			listboxResultado.applyProperties();
			listboxResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente, int index)
			throws Exception {
		final Listitem listitem = new Listitem();

		final Articulo art = (Articulo) objeto;

		Bodega_articulo bodega_articulo = new Bodega_articulo();
		bodega_articulo.setCodigo_empresa(art.getCodigo_empresa());
		bodega_articulo.setCodigo_sucursal(art.getCodigo_sucursal());
		bodega_articulo.setCodigo_bodega("01");
		bodega_articulo.setCodigo_articulo(art.getCodigo_articulo());
		bodega_articulo = bodega_articuloService.consultar(bodega_articulo);

		String existencia = (bodega_articulo != null ? bodega_articulo
				.getCantidad() + "" : "0.0");

		// Unidad_funcional uf = new Unidad_funcional();
		// uf.setCodigo(art.getCodigo_unidad_funcional());
		// uf = getServiceLocator().getUnidad_funcionalService().consultar(uf);

		// Tercero ter = new Tercero();
		// ter.setCodigo_empresa(art.getCodigo_empresa());
		// ter.setCodigo_sucursal(art.getCodigo_sucursal());
		// ter.setNro_identificacion(art.getFabricante());
		// ter = getServiceLocator().getTerceroService().consultar(ter);
		// String nombre_fabricante = "*";
		// if (ter != null) {
		// nombre_fabricante = ter.getNombre1() + " " + ter.getApellido1()
		// + " " + ter.getApellido2();
		// }
		// log.info("nombre_fabricante : " + nombre_fabricante);

		Grupo1 grupo1 = new Grupo1();
		grupo1.setCodigo_empresa(art.getCodigo_empresa());
		grupo1.setCodigo_sucursal(art.getCodigo_sucursal());
		grupo1.setCodigo(art.getGrupo1());
		grupo1 = grupo1Service.consultar(grupo1);

		double valor = (int) art.getPrecio1();
		double valor_real = valor;
		double copago = 0;

		if ((String) parametrosAction.get("estrato") != null) {
			Copago_estrato cop = new Copago_estrato();
			cop.setCodigo_empresa(empresa.getCodigo_empresa());
			cop.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			cop.setEstrato((String) parametrosAction.get("estrato"));
			cop = copago_estratoService.consultar(cop);
			copago = (cop != null ? (int) (valor) : 0);
		}

		double descuento = 0, incremento = 0;
		if (manual_tarifario != null) {
			if (manual_tarifario.getTarifa_especial().equals("S")) {
				Tarifa_medicamento tarifa = new Tarifa_medicamento();
				tarifa.setCodigo_empresa(manual_tarifario.getCodigo_empresa());
				tarifa.setCodigo_sucursal(manual_tarifario.getCodigo_sucursal());
				tarifa.setCodigo_administradora(manual_tarifario
						.getCodigo_administradora());
				tarifa.setId_plan(manual_tarifario.getId_contrato());
				tarifa.setCodigo_pcd(art.getCodigo_articulo());
				tarifa = tarifa_medicamentoService.consultar(tarifa);
				if (tarifa != null) {
					valor = tarifa.getValor();
					valor_real = valor;
				}
			}
		}

		if (manual_tarifario != null) {
			if (((String) parametrosAction.get("grupo")).equals("01")
					|| art.getGrupo1().equals("01")) {
				if (manual_tarifario.getTipo_medicamento().equals("01")) {
					descuento = (int) (valor * (manual_tarifario
							.getMedicamentos() / 100));
					valor -= descuento;
				} else {
					incremento = (int) (valor * (manual_tarifario
							.getMedicamentos() / 100));
					valor += incremento;
				}
			} else if (((String) parametrosAction.get("grupo")).equals("02")
					|| art.getGrupo1().equals("02")) {
				if (manual_tarifario.getTipo_servicio().equals("01")) {
					descuento = (int) (valor * (manual_tarifario.getServicios() / 100));
					valor -= descuento;
				} else {
					incremento = (int) (valor * (manual_tarifario
							.getServicios() / 100));
					valor += incremento;
				}
			}
		}

		/*
		 * verificamos autorizacion de medicamento 1. Si es POS o si es PyP Si
		 * no es Alto costo
		 * 
		 * 2. Si esta en el Vademecum internacional
		 */
		boolean autorizado = ((((art.getPos() + "").equalsIgnoreCase("S") || (art
				.getPyp() + "").equalsIgnoreCase("S")) && (art.getAlto_costo() + "")
				.equalsIgnoreCase("N")) || art.getVademecum_institucional()
				.equalsIgnoreCase("S"));

		final Map<String, Object> pcd = new HashMap<String, Object>();
		pcd.put("codigo_empresa", empresa.getCodigo_empresa());
		pcd.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		pcd.put("codigo_articulo", art.getCodigo_articulo());
		pcd.put("valor_unitario", valor);
		pcd.put("valor_total", valor);
		pcd.put("descuento", descuento);
		pcd.put("incremento", incremento);
		pcd.put("valor_real", valor_real);
		pcd.put("copago", copago);
		pcd.put("autorizado", autorizado);
		pcd.put("tipo", art.getGrupo1());
		pcd.put("bodega", "01");
		pcd.put("pyp", art.getPyp() + "");
		pcd.put("nombre1", art.getNombre1());
		pcd.put("facturable", art.getFacturable());
		pcd.put("cantidad_maxima", art.getCantidad_maxima());
		pcd.put("grupo1", grupo1 != null ? grupo1.getNombre() : "");

		listitem.setStyle("text-align: justify;nowrap:nowrap");
		listitem.appendChild(new Listcell(art.getCodigo_articulo()));
		listitem.appendChild(new Listcell(art.getNombre1()));
		// listitem.appendChild(new Listcell(new DecimalFormat("#,##0.##")
		// .format(Double.parseDouble(exist))));
		listitem.appendChild(Utilidades.obtenerListcell("", Textbox.class,
				true, false));
		listitem.appendChild(new Listcell((grupo1 != null ? grupo1.getNombre()
				: "")));
		listitem.appendChild(new Listcell(art.getPrecio1() + ""));
		listitem.appendChild(new Listcell(existencia));

		listitem.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					if (!listitem.isDisabled()) {
						if (action != null) {
							if (action instanceof Action) {
								((Action) action).adicionarPcd(pcd);
							} else {
								((ZKWindow) action).adicionarPcd(pcd);
							}
						} else {
							seleccionar_componente.onSeleccionar(pcd);
						}
						listitem.setDisabled(true);
						listitem.setTooltiptext("Este articulo ya se encuentra seleccionado");
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					throw new Exception(e);
				}
			}
		});

		if (seleccionados.containsKey(art.getCodigo_articulo())) {
			listitem.setDisabled(true);
			listitem.setSelected(true);
			listitem.setTooltiptext("Este articulo ya se encuentra seleccionado");
		}

		return listitem;
	}

	public ISeleccionarComponente getSeleccionar_componente() {
		return seleccionar_componente;
	}

	public void setSeleccionar_componente(
			ISeleccionarComponente seleccionar_componente) {
		this.seleccionar_componente = seleccionar_componente;
	}

}
