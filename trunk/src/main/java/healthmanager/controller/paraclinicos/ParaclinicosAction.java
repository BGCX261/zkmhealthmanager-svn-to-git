/*
 * phistorias_clinicasAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller.paraclinicos;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Pexamenes_paraclinicos;
import healthmanager.modelo.bean.Phistorias_clinicas;
import healthmanager.modelo.bean.Phistorias_paraclinicos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class ParaclinicosAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(ParaclinicosAction.class);

	@View
	private Listbox lbxTipo_examen;
	@View
	private Listbox listboxHistorias;
	@View
	private Listbox listboxExamenes;
	@View
	private Listbox listboxAsignaciones;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxHistorias;
	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxExamenes;

	@View
	private Textbox tbxNombre_historia;
	@View
	private Textbox tbxNombre_examen;
	@View
	private Toolbarbutton btnLimpiar_historia;
	@View
	private Toolbarbutton btnLimpiar_examen;
	@View
	private Popup popupAgregarAsociacion;
	@View
	private Listbox lbxHistorias_clinicas;

	@Override
	public void init() {
		consultarHistoriasClinicas();
		consultarExamenesParaclinicos();
		consultarAsignaciones();
		parametrizarBandboxHistorias();
		parametrizarBandboxExamenes();
	}

	public void consultarHistoriasClinicas() {
		listboxHistorias.getItems().clear();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);

		List<Phistorias_clinicas> listado = getServiceLocator()
				.getPhistorias_clinicasService().listar(parametros);

		String codigo_seleccionado = "";
		boolean seleccionado = false;

		if (lbxHistorias_clinicas.getItemCount() > 0) {
			codigo_seleccionado = lbxHistorias_clinicas.getSelectedItem()
					.getValue();
		}

		lbxHistorias_clinicas.getChildren().clear();

		for (Phistorias_clinicas phistorias_clinicas : listado) {
			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell());
			listitem.appendChild(Utilidades.obtenerListcell(
					phistorias_clinicas.getCodigo(), Textbox.class, true, false));
			listitem.appendChild(Utilidades.obtenerListcell(
					phistorias_clinicas.getNombre(), Textbox.class, true, false));
			listitem.appendChild(Utilidades.obtenerListcell(
					phistorias_clinicas.getDescripcion(), Textbox.class, true,
					false));
			listitem.setValue(phistorias_clinicas);
			listboxHistorias.appendChild(listitem);

			Listitem listitem2 = new Listitem(phistorias_clinicas.getCodigo()
					+ " " + phistorias_clinicas.getNombre(),
					phistorias_clinicas.getCodigo());
			if (codigo_seleccionado.equals(phistorias_clinicas.getCodigo())) {
				listitem2.setSelected(true);
				seleccionado = true;
			}
			lbxHistorias_clinicas.appendChild(listitem2);

		}

		if (!seleccionado && lbxHistorias_clinicas.getItemCount() > 0) {
			lbxHistorias_clinicas.setSelectedIndex(0);
		}

	}

	public void consultarExamenesParaclinicos() {
		// log.info("ejecutando metodo ===> consultarExamenesParaclinicos()");
		listboxExamenes.getItems().clear();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("tipo_examen", lbxTipo_examen.getSelectedItem()
				.getValue());

		List<Pexamenes_paraclinicos> listado = getServiceLocator()
				.getPexamenes_paraclinicosService().listar(parametros);

		for (final Pexamenes_paraclinicos pexamenes_paraclinicos : listado) {
			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell());
			listitem.appendChild(Utilidades.obtenerListcell(
					pexamenes_paraclinicos.getCodigo(), Textbox.class, true,
					false));
			listitem.appendChild(Utilidades.obtenerListcell(
					pexamenes_paraclinicos.getNombre(), Textbox.class, true,
					false));
			Checkbox checkbox = new Checkbox();
			checkbox.setChecked(pexamenes_paraclinicos.getNormal_anormal()
					.equals("S"));
			checkbox.setDisabled(true);
			Listcell listcell = new Listcell();
			listcell.appendChild(checkbox);
			listitem.appendChild(listcell);
			listitem.appendChild(Utilidades.obtenerListcell(
					pexamenes_paraclinicos.getDescripcion(), Textbox.class,
					true, false));
			listboxExamenes.appendChild(listitem);

			listitem.addEventListener(Events.ON_RIGHT_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							editarHistoriaClinica(pexamenes_paraclinicos);
						}

					});
		}

		listboxExamenes.getPagingChild().setActivePage(
				listboxExamenes.getPageCount() - 1);

	}

	public void consultarAsignaciones() {
		listboxAsignaciones.getItems().clear();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		if (lbxHistorias_clinicas.getSelectedItem() != null) {
			parametros.put("codigo_historia", lbxHistorias_clinicas
					.getSelectedItem().getValue());
		}

		List<Phistorias_paraclinicos> listado = getServiceLocator()
				.getPhistorias_paraclinicosService().listar(parametros);

		for (Phistorias_paraclinicos phistorias_paraclinicos : listado) {
			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell());
			listitem.appendChild(Utilidades.obtenerListcell(
					phistorias_paraclinicos.getCodigo_historia()
							+ " "
							+ phistorias_paraclinicos.getPhistorias_clinicas()
									.getNombre(), Textbox.class, true, false));
			listitem.appendChild(Utilidades.obtenerListcell(
					phistorias_paraclinicos.getCodigo_examen()
							+ " "
							+ phistorias_paraclinicos
									.getPexamenes_paraclinicos().getNombre(),
					Textbox.class, true, false));
			listboxAsignaciones.appendChild(listitem);
		}

		listboxAsignaciones.getPagingChild().setActivePage(
				listboxAsignaciones.getPageCount() - 1);

	}

	public void eliminarHistoriaClinica() {
		if (listboxHistorias.getSelectedItem() != null) {
			Messagebox.show(
					"¿Esta seguro que desea eliminar la historia clinica?",
					"Eliminar Historia Clinica", Messagebox.OK | Messagebox.NO,
					Messagebox.QUESTION, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								Listitem listitem = listboxHistorias
										.getSelectedItem();
								Phistorias_clinicas phistorias_clinicas = (Phistorias_clinicas) listitem
										.getValue();
								try {
									getServiceLocator()
											.getPhistorias_clinicasService()
											.eliminar(phistorias_clinicas);
									listitem.detach();
									consultarHistoriasClinicas();
								} catch (Exception e) {
									MensajesUtil.mensajeError(e, "",
											ParaclinicosAction.this);
								}
							}
						}
					});
		}
	}

	public void editarHistoriaClinica(
			final Pexamenes_paraclinicos pexamenes_paraclinicos) {
		final Window window = (Window) Executions.createComponents(
				"/pages/paraclinicos/pdatos.zul", this, null);
		final Textbox tbxNombre = (Textbox) window.getFellow("tbxNombre");
		tbxNombre.setValue(pexamenes_paraclinicos.getNombre());
		final Textbox tbxDescripcion = (Textbox) window
				.getFellow("tbxDescripcion");
		tbxDescripcion.setValue(pexamenes_paraclinicos.getDescripcion());
		final Checkbox chkNormal_anormal = (Checkbox) window
				.getFellow("chkNormal_anormal");
		chkNormal_anormal.setChecked(pexamenes_paraclinicos.getNormal_anormal()
				.equals("S"));
		Toolbarbutton toolbarbutton = (Toolbarbutton) window
				.getFellow("btnGuardar");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						Pexamenes_paraclinicos pexamenes_aux = new Pexamenes_paraclinicos();
						pexamenes_aux.setCodigo(pexamenes_paraclinicos
								.getCodigo());
						pexamenes_aux.setCodigo_empresa(codigo_empresa);
						pexamenes_aux.setCodigo_sucursal(codigo_sucursal);
						pexamenes_aux.setNombre(tbxNombre.getValue()
								.toUpperCase());
						pexamenes_aux.setDescripcion(tbxDescripcion.getValue()
								.toUpperCase());
						pexamenes_aux.setTipo_examen(pexamenes_paraclinicos
								.getTipo_examen());

						pexamenes_aux.setNormal_anormal(chkNormal_anormal
								.isChecked() ? "S" : "N");

						getServiceLocator().getPexamenes_paraclinicosService()
								.actualizar(pexamenes_aux);

						consultarExamenesParaclinicos();

						window.onClose();
					}
				});
		window.setTitle("Actualizar Historia Clinica");
		window.setMode("modal");
	}

	public void agregarHistoriaClinica() {
		final Window window = (Window) Executions.createComponents(
				"/pages/paraclinicos/pdatos.zul", this, null);
		final Textbox tbxNombre = (Textbox) window.getFellow("tbxNombre");
		final Textbox tbxDescripcion = (Textbox) window
				.getFellow("tbxDescripcion");
		Toolbarbutton toolbarbutton = (Toolbarbutton) window
				.getFellow("btnGuardar");

		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Phistorias_clinicas phistorias_clinicas = new Phistorias_clinicas();
						phistorias_clinicas.setCodigo_empresa(codigo_empresa);
						phistorias_clinicas.setCodigo_sucursal(codigo_sucursal);
						phistorias_clinicas.setNombre(tbxNombre.getValue()
								.toUpperCase());
						phistorias_clinicas.setDescripcion(tbxDescripcion
								.getValue().toUpperCase());

						getServiceLocator().getPhistorias_clinicasService()
								.crear(phistorias_clinicas);

						consultarHistoriasClinicas();

						window.detach();

					}

				});

		window.setTitle("Registrar Historia Clinica");
		window.setMode("modal");
	}

	public void eliminarExamenParaclinico() {
		if (listboxExamenes.getSelectedItem() != null) {
			Messagebox.show(
					"¿Esta seguro que desea eliminar el examen paraclinico?",
					"Eliminar Examen paraclinico", Messagebox.OK
							| Messagebox.NO, Messagebox.QUESTION,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								Listitem listitem = listboxExamenes
										.getSelectedItem();
								Pexamenes_paraclinicos pexamenes_paraclinicos = (Pexamenes_paraclinicos) listitem
										.getValue();
								try {
									getServiceLocator()
											.getPexamenes_paraclinicosService()
											.eliminar(pexamenes_paraclinicos);
									listitem.detach();
									consultarExamenesParaclinicos();
								} catch (Exception e) {
									MensajesUtil.mensajeError(e, "",
											ParaclinicosAction.this);
								}
							}
						}
					});
		}
	}

	public void agregarExamenParaclinico() {
		final Window window = (Window) Executions.createComponents(
				"/pages/paraclinicos/pdatos.zul", this, null);
		final Textbox tbxNombre = (Textbox) window.getFellow("tbxNombre");
		final Textbox tbxDescripcion = (Textbox) window
				.getFellow("tbxDescripcion");
		final Checkbox chkNormal_anormal = (Checkbox) window
				.getFellow("chkNormal_anormal");
		Toolbarbutton toolbarbutton = (Toolbarbutton) window
				.getFellow("btnGuardar");

		final Popup popupConsultas = (Popup) window.getFellow("popupConsultas");
		final Listbox listboxConsultas = (Listbox) window
				.getFellow("listboxConsultas");

		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						Pexamenes_paraclinicos pexamenes_paraclinicos = new Pexamenes_paraclinicos();
						pexamenes_paraclinicos
								.setCodigo_empresa(codigo_empresa);
						pexamenes_paraclinicos
								.setCodigo_sucursal(codigo_sucursal);
						pexamenes_paraclinicos.setNombre(tbxNombre.getValue()
								.toUpperCase());
						pexamenes_paraclinicos.setDescripcion(tbxDescripcion
								.getValue().toUpperCase());
						pexamenes_paraclinicos.setTipo_examen(lbxTipo_examen
								.getSelectedItem().getValue().toString());

						pexamenes_paraclinicos
								.setNormal_anormal(chkNormal_anormal
										.isChecked() ? "S" : "N");

						getServiceLocator().getPexamenes_paraclinicosService()
								.crear(pexamenes_paraclinicos);

						consultarExamenesParaclinicos();

						tbxNombre.setValue("");
						tbxDescripcion.setValue("");

					}

				});

		tbxNombre.addEventListener(Events.ON_OK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				listboxConsultas.getItems().clear();
				List<Pexamenes_paraclinicos> listado = ((BandboxRegistrosIMG<Pexamenes_paraclinicos>) bandboxExamenes
						.getBandboxRegistrosIMG()).listarRegistros(tbxNombre
						.getValue().trim().toLowerCase(),
						new HashMap<String, Object>());
				if (!listado.isEmpty()) {
					popupConsultas.open(tbxNombre);
				}
				for (Pexamenes_paraclinicos pexamenes_paraclinicos : listado) {
					Listitem listitem = new Listitem();
					((BandboxRegistrosIMG<Pexamenes_paraclinicos>) bandboxExamenes
							.getBandboxRegistrosIMG()).renderListitem(listitem,
							pexamenes_paraclinicos);
					listboxConsultas.appendChild(listitem);
				}
			}

		});

		window.setTitle("Registrar Examen Paraclinico");
		window.setMode("modal");
	}

	private void parametrizarBandboxHistorias() {
		bandboxHistorias.inicializar(tbxNombre_historia, btnLimpiar_historia);
		bandboxHistorias
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Phistorias_clinicas>() {

					@Override
					public void renderListitem(Listitem listitem,
							Phistorias_clinicas registro) {
						listitem.appendChild(Utilidades.obtenerListcell(
								registro.getCodigo(), Label.class, true, true));
						listitem.appendChild(Utilidades.obtenerListcell(
								registro.getNombre(), Textbox.class, true,
								false));
					}

					@Override
					public List<Phistorias_clinicas> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("paramTodo", "");
						parametros.put("value", "%" + valorBusqueda + "%");
						getServiceLocator().getPhistorias_clinicasService()
								.setLimit("limit 25 offset 0");
						return getServiceLocator()
								.getPhistorias_clinicasService().listar(
										parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion,
							Phistorias_clinicas registro) {
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNombre());
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
					}
				});
	}

	private void parametrizarBandboxExamenes() {
		bandboxExamenes.inicializar(tbxNombre_examen, btnLimpiar_examen);
		bandboxExamenes
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Pexamenes_paraclinicos>() {

					@Override
					public void renderListitem(Listitem listitem,
							Pexamenes_paraclinicos registro) {
						listitem.appendChild(Utilidades.obtenerListcell(
								registro.getCodigo(), Label.class, true, true));
						listitem.appendChild(Utilidades.obtenerListcell(
								registro.getNombre(), Textbox.class, true,
								false));
					}

					@Override
					public List<Pexamenes_paraclinicos> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa", codigo_empresa);
						parametros.put("codigo_sucursal", codigo_sucursal);
						parametros.put("paramTodo", "");
						parametros.put("value", "%" + valorBusqueda + "%");
						parametros.put("limite_paginado", "limit 25 offset 0");

						return getServiceLocator()
								.getPexamenes_paraclinicosService().listar(
										parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion,
							Pexamenes_paraclinicos registro) {
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNombre());
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
					}
				});
	}

	public void guardarAsociacion() {
		try {
			if (bandboxHistorias.getValue().isEmpty()
					|| bandboxExamenes.getValue().isEmpty()) {
				MensajesUtil
						.mensajeAlerta("Campos Obligatorios",
								"Debe seleccionar una historia y examen para registrar la asociacion");
			} else {
				Phistorias_paraclinicos phistorias_paraclinicos = new Phistorias_paraclinicos();
				phistorias_paraclinicos.setCodigo_empresa(codigo_empresa);
				phistorias_paraclinicos.setCodigo_sucursal(codigo_sucursal);
				phistorias_paraclinicos.setCodigo_historia(bandboxHistorias
						.getValue());
				phistorias_paraclinicos.setCodigo_examen(bandboxExamenes
						.getValue());

				getServiceLocator().getPhistorias_paraclinicosService().crear(
						phistorias_paraclinicos);

				consultarAsignaciones();

				bandboxExamenes.limpiarSeleccion(true);

				// popupAgregarAsociacion.close();

			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void abrirPopupAgregarAsociacion() {
		bandboxExamenes.limpiarSeleccion(true);
		bandboxHistorias.limpiarSeleccion(true);
	}

}