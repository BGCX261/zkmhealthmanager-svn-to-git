/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Estancias_iss;
import healthmanager.modelo.bean.Estancias_soat;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.Estancias_issService;
import healthmanager.modelo.service.Estancias_soatService;
import healthmanager.modelo.service.ProcedimientosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.XulElement;

import com.framework.macros.macro_row.BandBoxRowMacro;
import com.framework.macros.macro_row.BandBoxRowMacro.IConfiguracionBandbox;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

/**
 * @author ferney
 * 
 */
public class Procedimientos_estanciasAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Evolucion_medicaAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View
	private Listbox lbxManual_tarifario;

	@View
	private Listbox lbxNivel_procedimiento;

	@View
	private Textbox tbxValue;
	@View
	private Listbox listboxResultado;

	@View
	private Listheader listheaderValor;

	private IConfiguracionBandbox<Procedimientos> iconfiguracionbandbox_procedimientos;

	private List<Object> listado_eliminados = new ArrayList<Object>();

	public void seleccionarManual() {
		listado_eliminados.clear();
		listboxResultado.getItems().clear();
		String manual = lbxManual_tarifario.getSelectedItem().getValue()
				.toString();

		if (manual.equals("SOAT")) {
			listheaderValor.setLabel("Porcentaje");
		} else {
			listheaderValor.setLabel("Valor");
		}
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String manual = lbxManual_tarifario.getSelectedItem().getValue()
					.toString();
			Map<String, Object> parameters = new HashMap<String, Object>();
			String value = tbxValue.getValue().toUpperCase().trim();

			if (!lbxNivel_procedimiento.getSelectedItem().getValue().toString()
					.isEmpty())
				parameters.put("nivel_orden", new Integer(
						lbxNivel_procedimiento.getSelectedItem().getValue()
								.toString()));
			parameters.put("paramTodo", "");
			parameters.put("value", value);

			//log.info("parametros de busqueda ===> " + parameters);

			listboxResultado.getItems().clear();

			if (manual.equalsIgnoreCase("SOAT")) {
				List<Estancias_soat> listado_estancias = getServiceLocator()
						.getServicio(Estancias_soatService.class).listar(
								parameters);

				for (Estancias_soat estancias_soat : listado_estancias) {
					listboxResultado.appendChild(crearFilas(estancias_soat));
				}

			} else {
				List<Estancias_iss> listado_estancias = getServiceLocator()
						.getServicio(Estancias_issService.class).listar(
								parameters);

				for (Estancias_iss estancias_iss : listado_estancias) {
					listboxResultado.appendChild(crearFilas(estancias_iss));
				}
			}

			listboxResultado.invalidate();

			listado_eliminados.clear();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void inicializamosEvento() {
		iconfiguracionbandbox_procedimientos = new IConfiguracionBandbox<Procedimientos>() {

			@Override
			public void onSeleccionar(Procedimientos t, Bandbox bandbox) {

			}

			@Override
			public String getCabecera(Bandbox bandbox) {
				return "Codigo cups#70px|Nombre";
			}

			@Override
			public String getWidthListBox() {
				return "350px";
			}

			@Override
			public String getHeightListBox() {
				return "200px";
			}

			@Override
			public void renderListitem(Listitem listitem,
					Procedimientos registro, Bandbox bandbox) {
				listitem.appendChild(new Listcell(registro.getCodigo_cups()));
				listitem.appendChild(new Listcell(""
						+ registro.getDescripcion()));
			}

			@Override
			public List<?> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros, Bandbox bandbox) {
				parametros.put("paramTodo", "paramTodo");
				parametros.put("value", valorBusqueda.toUpperCase());
				return getServiceLocator().getServicio(
						ProcedimientosService.class).listar(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Procedimientos registro) {
				bandbox.setValue(registro.getCodigo_cups() + " "
						+ registro.getDescripcion());
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}

		};
	}

	public void registrarNuevo() {
		Listitem listitem = crearFilas(new Object());
		listboxResultado.appendChild(listitem);
		Clients.scrollIntoView(listitem);
	}

	public void eliminarSeleccionados() {
		Set<Listitem> listado_seleccionados = listboxResultado
				.getSelectedItems();
		for (Listitem listitem : listado_seleccionados) {
			if (listitem.getValue() != null) {
				listado_eliminados.add(listitem.getValue());
			}
		}
		listboxResultado.getItems().removeAll(listado_seleccionados);
	}

	public Listitem crearFilas(Object objeto) {
		final Listitem fila = new Listitem();
		fila.appendChild(new Listcell());

		if (objeto instanceof Estancias_soat) {
			fila.setValue(objeto);
			Estancias_soat estancias_soat = (Estancias_soat) objeto;
			Listbox listbox_tipo = new Listbox();
			listbox_tipo.setHflex("1");
			listbox_tipo.setName("tipo_estancia");
			listbox_tipo.setMold("select");
			Utilidades.listarElementoListbox(listbox_tipo, true,
					getServiceLocator());
			Utilidades.seleccionarListitem(listbox_tipo,
					estancias_soat.getEstancia());
			Listcell listcell = new Listcell();
			listcell.appendChild(listbox_tipo);
			fila.appendChild(listcell);

			Listbox listbox_niveles = new Listbox();
			listbox_niveles.setHflex("1");
			listbox_niveles.setName("nivel_complejidad");
			listbox_niveles.setMold("select");
			Utilidades.listarElementoListbox(listbox_niveles, true,
					getServiceLocator());
			Utilidades.seleccionarListitem(listbox_niveles,
					estancias_soat.getNivel() + "");
			listcell = new Listcell();
			listcell.appendChild(listbox_niveles);
			fila.appendChild(listcell);

			Intbox intbox_camas = new Intbox();
			intbox_camas.setHflex("1");
			intbox_camas.setValue(estancias_soat.getNro_camas());
			listcell = new Listcell();
			listcell.appendChild(intbox_camas);
			fila.appendChild(listcell);

			final BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro();

			bandBoxRowMacro.setHflex("1");

			bandBoxRowMacro.configurar(iconfiguracionbandbox_procedimientos);

			Procedimientos procedimientos = new Procedimientos();

			try {
				procedimientos.setId_procedimiento(new Long(estancias_soat
						.getCodigo()));
				procedimientos = getServiceLocator().getProcedimientosService()
						.consultar(procedimientos);
				bandBoxRowMacro.seleccionarRegistro(
						procedimientos,
						procedimientos != null ? procedimientos
								.getCodigo_cups()
								+ " "
								+ procedimientos.getDescripcion()
								: estancias_soat.getCodigo());
			} catch (NumberFormatException numberFormatException) {
				bandBoxRowMacro.seleccionarRegistro(null,
						estancias_soat.getCodigo());
			}

			listcell = new Listcell();
			listcell.appendChild(bandBoxRowMacro);
			fila.appendChild(listcell);

			Doublebox doublebox_valor = new Doublebox();
			doublebox_valor.setHflex("1");
			doublebox_valor.setValue(estancias_soat.getPorcentaje());
			listcell = new Listcell();
			listcell.appendChild(doublebox_valor);
			fila.appendChild(listcell);

		} else if (objeto instanceof Estancias_iss) {
			fila.setValue(objeto);
			Estancias_iss estancias_iss = (Estancias_iss) objeto;
			Listbox listbox_tipo = new Listbox();
			listbox_tipo.setHflex("1");
			listbox_tipo.setName("tipo_estancia");
			listbox_tipo.setMold("select");
			Utilidades.listarElementoListbox(listbox_tipo, true,
					getServiceLocator());
			Utilidades.seleccionarListitem(listbox_tipo,
					estancias_iss.getEstancia());
			Listcell listcell = new Listcell();
			listcell.appendChild(listbox_tipo);
			fila.appendChild(listcell);

			Listbox listbox_niveles = new Listbox();
			listbox_niveles.setHflex("1");
			listbox_niveles.setName("nivel_complejidad");
			listbox_niveles.setMold("select");
			Utilidades.listarElementoListbox(listbox_niveles, true,
					getServiceLocator());
			Utilidades.seleccionarListitem(listbox_niveles,
					estancias_iss.getNivel() + "");
			listcell = new Listcell();
			listcell.appendChild(listbox_niveles);
			fila.appendChild(listcell);

			Intbox intbox_camas = new Intbox();
			intbox_camas.setHflex("1");
			intbox_camas.setValue(estancias_iss.getNro_camas());
			listcell = new Listcell();
			listcell.appendChild(intbox_camas);
			fila.appendChild(listcell);

			final BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro();

			bandBoxRowMacro.setHflex("1");

			bandBoxRowMacro.configurar(iconfiguracionbandbox_procedimientos);

			Procedimientos procedimientos = new Procedimientos();

			try {
				procedimientos.setId_procedimiento(new Long(estancias_iss
						.getCodigo()));
				procedimientos = getServiceLocator().getProcedimientosService()
						.consultar(procedimientos);
				bandBoxRowMacro.seleccionarRegistro(
						procedimientos,
						procedimientos != null ? procedimientos
								.getCodigo_cups()
								+ " "
								+ procedimientos.getDescripcion()
								: estancias_iss.getCodigo());
			} catch (NumberFormatException numberFormatException) {
				bandBoxRowMacro.seleccionarRegistro(null,
						estancias_iss.getCodigo());
			}

			listcell = new Listcell();
			listcell.appendChild(bandBoxRowMacro);
			fila.appendChild(listcell);

			Doublebox doublebox_valor = new Doublebox();
			doublebox_valor.setHflex("1");
			doublebox_valor.setValue(estancias_iss.getValor());
			listcell = new Listcell();
			listcell.appendChild(doublebox_valor);
			fila.appendChild(listcell);
		} else {
			Listbox listbox_tipo = new Listbox();
			listbox_tipo.setHflex("1");
			listbox_tipo.setName("tipo_estancia");
			listbox_tipo.setMold("select");
			Utilidades.listarElementoListbox(listbox_tipo, true,
					getServiceLocator());
			Listcell listcell = new Listcell();
			listcell.appendChild(listbox_tipo);
			fila.appendChild(listcell);

			Listbox listbox_niveles = new Listbox();
			listbox_niveles.setHflex("1");
			listbox_niveles.setName("nivel_complejidad");
			listbox_niveles.setMold("select");
			Utilidades.listarElementoListbox(listbox_niveles, true,
					getServiceLocator());
			listcell = new Listcell();
			listcell.appendChild(listbox_niveles);
			fila.appendChild(listcell);

			Intbox intbox_camas = new Intbox();
			intbox_camas.setHflex("1");
			listcell = new Listcell();
			listcell.appendChild(intbox_camas);
			fila.appendChild(listcell);

			final BandBoxRowMacro bandBoxRowMacro = new BandBoxRowMacro();

			bandBoxRowMacro.setHflex("1");

			bandBoxRowMacro.configurar(iconfiguracionbandbox_procedimientos);
			listcell = new Listcell();
			listcell.appendChild(bandBoxRowMacro);
			fila.appendChild(listcell);

			Doublebox doublebox_valor = new Doublebox();
			doublebox_valor.setHflex("1");
			listcell = new Listcell();
			listcell.appendChild(doublebox_valor);
			fila.appendChild(listcell);
		}

		return fila;
	}

	public boolean validarInformacion() {
		boolean validar = true;
		String mensaje = "";
		List<Listitem> listado_items = listboxResultado.getItems();

		XulElement xulElement = null;

		for (Listitem listitem : listado_items) {
			Listbox listbox_tipo = (Listbox) listitem.getChildren().get(1)
					.getFirstChild();
			if (listbox_tipo.getSelectedItem().getValue().toString().isEmpty()) {
				validar = false;
				xulElement = listbox_tipo;
				mensaje = "Debe seleccionar un tipo de estancia para la estancia que desea guardar";
				break;
			}

			Listbox listbox_niveles = (Listbox) listitem.getChildren().get(2)
					.getFirstChild();
			if (listbox_niveles.getSelectedItem().getValue().toString()
					.isEmpty()) {
				validar = false;
				xulElement = listbox_niveles;
				mensaje = "Debe seleccionar un nivel de complejidad para la estancia que desea guardar";
				break;
			}

			Intbox intbox_camas = (Intbox) listitem.getChildren().get(3)
					.getFirstChild();
			if (intbox_camas.getValue() == null) {
				validar = false;
				xulElement = intbox_camas;
				mensaje = "Debe ingresar el numero de camas para la estancia que desea guardar";
				break;
			}

			BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
					.getChildren().get(4).getFirstChild();
			if (bandBoxRowMacro.getValue().isEmpty()) {
				validar = false;
				xulElement = bandBoxRowMacro;
				mensaje = "Debe ingresar el codigo del procedimiento para la estancia que desea guardar";
				break;
			}

			Doublebox doublebox_valor = (Doublebox) listitem.getChildren()
					.get(5).getFirstChild();
			if (doublebox_valor.getValue() == null) {
				validar = false;
				xulElement = doublebox_valor;
				mensaje = "Debe ingresar el Valor o porcentaje para la estancia que desea guardar";
				break;
			}
		}

		if (!validar) {
			final XulElement xulElement_aux = xulElement;
			MensajesUtil.mensajeAlerta("Error al validar la informacion",
					mensaje, new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							Clients.scrollIntoView(xulElement_aux);
							xulElement_aux.focus();
							MensajesUtil.notificarAlerta("validar",
									xulElement_aux);
						}
					});
		}

		return validar;
	}

	public void guardarDatos() {
		try {
			if (validarInformacion()) {
				List<Listitem> listado_items = listboxResultado.getItems();
				List<Object> listado_datos = new ArrayList<Object>();

				if (!listado_items.isEmpty()) {
					if (lbxManual_tarifario.getSelectedItem().getValue()
							.toString().equals("SOAT")) {
						for (Listitem listitem : listado_items) {
							Estancias_soat estancias_soat = new Estancias_soat();
							Listbox listbox_tipo = (Listbox) listitem
									.getChildren().get(1).getFirstChild();
							estancias_soat.setEstancia(listbox_tipo
									.getSelectedItem().getValue().toString());

							Listbox listbox_niveles = (Listbox) listitem
									.getChildren().get(2).getFirstChild();
							estancias_soat.setNivel(new Integer(listbox_niveles
									.getSelectedItem().getValue().toString()));

							Intbox intbox_camas = (Intbox) listitem
									.getChildren().get(3).getFirstChild();
							estancias_soat
									.setNro_camas(intbox_camas.getValue());

							BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
									.getChildren().get(4).getFirstChild();

							if (bandBoxRowMacro.getRegistroSeleccionado() != null) {
								Procedimientos procedimientos = (Procedimientos) bandBoxRowMacro
										.getRegistroSeleccionado();
								estancias_soat.setCodigo(procedimientos
										.getId_procedimiento() + "");
								estancias_soat.setDescripcion(procedimientos
										.getDescripcion());
							} else {
								estancias_soat.setCodigo(bandBoxRowMacro
										.getValue());
								estancias_soat.setDescripcion(bandBoxRowMacro
										.getValue());
							}

							Doublebox doublebox_valor = (Doublebox) listitem
									.getChildren().get(5).getFirstChild();
							estancias_soat.setPorcentaje(doublebox_valor
									.getValue());

							listado_datos.add(estancias_soat);
						}

						Map<String, Object> mapa_guardar = new HashMap<String, Object>();
						mapa_guardar.put("listado_datos", listado_datos);
						mapa_guardar
								.put("listado_eliminar", listado_eliminados);

						getServiceLocator().getServicio(
								Estancias_soatService.class)
								.actualizarEstancias(mapa_guardar);
						MensajesUtil.mensajeInformacion("Informacion",
								"Los datos se guardaron satisfactoriamente");
						buscarDatos();
					} else {
						for (Listitem listitem : listado_items) {
							Estancias_iss estancias_iss = new Estancias_iss();
							Listbox listbox_tipo = (Listbox) listitem
									.getChildren().get(1).getFirstChild();
							estancias_iss.setEstancia(listbox_tipo
									.getSelectedItem().getValue().toString());

							Listbox listbox_niveles = (Listbox) listitem
									.getChildren().get(2).getFirstChild();
							estancias_iss.setNivel(new Integer(listbox_niveles
									.getSelectedItem().getValue().toString()));

							Intbox intbox_camas = (Intbox) listitem
									.getChildren().get(3).getFirstChild();
							estancias_iss.setNro_camas(intbox_camas.getValue());

							BandBoxRowMacro bandBoxRowMacro = (BandBoxRowMacro) listitem
									.getChildren().get(4).getFirstChild();

							if (bandBoxRowMacro.getRegistroSeleccionado() != null) {
								Procedimientos procedimientos = (Procedimientos) bandBoxRowMacro
										.getRegistroSeleccionado();
								estancias_iss.setCodigo(procedimientos
										.getId_procedimiento() + "");
								estancias_iss.setDescripcion(procedimientos
										.getDescripcion());
							} else {
								estancias_iss.setCodigo(bandBoxRowMacro
										.getValue());
								estancias_iss.setDescripcion(bandBoxRowMacro
										.getValue());
							}

							Doublebox doublebox_valor = (Doublebox) listitem
									.getChildren().get(5).getFirstChild();
							estancias_iss.setValor(doublebox_valor.getValue());

							listado_datos.add(estancias_iss);
						}

						Map<String, Object> mapa_guardar = new HashMap<String, Object>();
						mapa_guardar.put("listado_datos", listado_datos);
						mapa_guardar
								.put("listado_eliminar", listado_eliminados);

						getServiceLocator().getServicio(
								Estancias_issService.class)
								.actualizarEstancias(mapa_guardar);
						MensajesUtil.mensajeInformacion("Informacion",
								"Los datos se guardaron satisfactoriamente");
						buscarDatos();
					}
				} else {
					MensajesUtil
							.mensajeAlerta("No hay items seleccionados",
									"Debe seleccionar procedimientos que desea actualizar");
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void init() throws Exception {
		inicializamosEvento();
		seleccionarManual();
	}

	public class BeanMapUtil extends BeanMap {

		public BeanMapUtil(Object object) {
			super(object);
		}

		@Override
		public Object get(Object name) {
			return super.get(name) != null ? super.get(name) : "";
		}

	}

}
