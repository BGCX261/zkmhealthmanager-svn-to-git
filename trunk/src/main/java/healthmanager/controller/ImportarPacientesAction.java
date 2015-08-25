package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.service.Pacientes_contratosService;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vlayout;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;

public class ImportarPacientesAction extends ZKWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9150460526394236350L;

	@View
	private Listbox listboxModulo;

	@View
	private Vlayout result;

	private Media file;

	@View
	private Button buttonEliminar;
	@View
	private Button buttonAdjuntar;
	@View
	private Label labelArchivo_adjunto;
	@View
	private Button buttonImportar;
	
	@View
	private Listbox listboxEntidades;

	@View
	private Row rowContratos;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxAdministradora;
	@View
	private Textbox tbxAdministradora;
	@View
	private Toolbarbutton btnLimpiar_administradora;

	@View
	private Listbox listboxContratos;

	@View
	private Listbox lbxTipo_importacion;

	@View
	private Listbox lbxDelimitador;
	@View
	private Button buttonDeshabilitar;

	private String FORMATO_CSV = "csv";
	private String FORMATO_XLS = "xls";
	private String FORMATO_XLSX = "xlsx";

	@Override
	public void init() throws Exception {
		listarModulos();
		parametrizarBandboxAdministradora();
	}

	public void listarModulos() {
		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("-- seleccione --");
		listboxModulo.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("pacientes");
		listitem.setLabel("BD de usuarios");
		listboxModulo.appendChild(listitem);

		if (listboxModulo.getItemCount() > 0) {
			listboxModulo.setSelectedIndex(0);
		}

	}

	public void adjuntarArchivo(Media media) throws Exception {
		try {
			if (!(media.getFormat().toLowerCase().endsWith(FORMATO_CSV)
					|| media.getFormat().endsWith(FORMATO_XLS) || media
					.getFormat().endsWith(FORMATO_XLSX))) {
				throw new ValidacionRunTimeException(
						"El importador de pacientes solo soporta formatos csv, no "
								+ media.getFormat());
			}

			if (file != null) {
				throw new ValidacionRunTimeException(
						"El archivo ya fue cargado");
			}

			buttonAdjuntar.setVisible(false);
			buttonEliminar.setVisible(true);
			buttonImportar.setDisabled(false);

			labelArchivo_adjunto.setValue(media.getName());

			file = media;

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarArchivo() {
		labelArchivo_adjunto.setValue("");
		buttonAdjuntar.setVisible(true);
		buttonEliminar.setVisible(false);
		buttonImportar.setDisabled(true);
		result.getChildren().clear();
		file = null;
		lbxTipo_importacion.setSelectedIndex(0);
	}

	@SuppressWarnings("unchecked")
	public void importarDatos() throws Exception {
		try {
			if (file == null) {
				throw new Exception(
						"Debe cargar un archivo de excel para la importacion");
			} else if (listboxModulo.getSelectedItem().getValue().toString()
					.equals("")) {
				throw new Exception("Seleccione modulo a importar");
			}

			if (lbxTipo_importacion.getSelectedItem().getValue().toString()
					.isEmpty()) {
				throw new Exception(
						"Debe Seleccionar el tipo de importacion que desea realizar");
			}

			String tipo_importacion = lbxTipo_importacion.getSelectedItem()
					.getValue().toString();

			String entidad = listboxEntidades.getSelectedItem().getValue()
					.toString();

			Set<Listitem> items_seleccionados = listboxContratos
					.getSelectedItems();

			if (!tipo_importacion.equals("03")
					&& entidad.equalsIgnoreCase("ESE_CARTAGENA")) {
				if (items_seleccionados.isEmpty()) {
					throw new Exception(
							"Seleccionar contratos con los que va a trabajar la poblacion actual");
				}
			}

			List<Contratos> listado_contratos = new ArrayList<Contratos>();

			for (Listitem listitem : items_seleccionados) {
				listado_contratos.add((Contratos) listitem.getValue());
			}

			result.getChildren().clear();

			String modulo = listboxModulo.getSelectedItem().getValue()
					.toString();
			String mensaje = "";

			if (modulo.equals("pacientes")) {
				Map<String, Object> mapa_parametros = new HashMap<String, Object>();
				mapa_parametros
						.put("file_contenido", file.getFormat().toLowerCase()
								.endsWith(FORMATO_CSV) ? file.getReaderData()
								: new InputStreamReader(file.getStreamData()));
				mapa_parametros.put("entidad", entidad);
				mapa_parametros.put("listado_contratos", listado_contratos);
				mapa_parametros.put("codigo_empresa", codigo_empresa);
				mapa_parametros.put("codigo_sucursal", codigo_sucursal);
				mapa_parametros.put("codigo_usuario", usuarios.getCodigo());
				mapa_parametros.put("tipo_importacion", tipo_importacion);
				mapa_parametros.put("codigo_administradora",
						bandboxAdministradora.getValue());
				mapa_parametros.put("delimitador", lbxDelimitador
						.getSelectedItem().getValue().toString());
				Map<String, Object> respuestaMap = getServiceLocator()
						.getPacienteService().guardarImportacionExcel(
								mapa_parametros);
				mensaje = (String) respuestaMap.get(IConstantes.IMPORTADOR_MSJ);
				
				List<Map<String, Object>> pacientes_homologados = (List<Map<String, Object>>) respuestaMap
						.get(IConstantes.IMPORTADOR_POSIBLES_PACIENTES_REPETIDOS);
				if (pacientes_homologados != null
						&& !pacientes_homologados.isEmpty()) {
					// Mostar pacientes que contienen repetidos
					// //log.info("" + pacientes_homologados.size());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put(ListadoPoblacionAction.PACIENTES,
							pacientes_homologados);
					map.put(IConstantes.IMPORTADOR_INTERNO_LISTADO_CONTRATOS,
							listado_contratos);
					map.put(IConstantes.IMPORTADOR_INTERNO_INFORMACION,
							"Importacion terminada satisfactoriamente. "
									+ mensaje);
					map.put(ListadoPoblacionAction.TITULO,
							"HOMOLOGACION DE PACIENTES - TOTAL: "
									+ pacientes_homologados.size());
					ListadoPoblacionAction listadoPoblacionAction = (ListadoPoblacionAction) Executions
							.createComponents("/pages/listado_poblacion.zul",
									this, map);
					listadoPoblacionAction.setWidth("100%");
					listadoPoblacionAction.setHeight("100%");
					listadoPoblacionAction.doModal();
				}
				if(respuestaMap.containsKey(IConstantes.IMPORTADOR_NACIDOS_VIVOS)){
					Integer cantidad_nacidos = (Integer)respuestaMap.get(IConstantes.IMPORTADOR_NACIDOS_VIVOS);
					mensaje = mensaje+". Se actualizaron "+cantidad_nacidos+" nacidos vivos que heredaron contratos";
				}
			}

			mensaje("Importacion terminada satisfactoriamente. " + mensaje, 1);

		} catch (Exception e) {
			mensaje(e.getMessage(), 2);
			e.printStackTrace();
		}
	}

	public void mensaje(String mensaje, int tipo) {
		String color = "";
		if (tipo == 1) {
			color = "blue";
		} else if (tipo == 2) {
			color = "red";
		} else if (tipo == 3) {
			color = "orange";
		}
		Hlayout hlayout = new Hlayout();
		Textbox textbox = new Textbox(mensaje);
		textbox.setWidth("700px");
		textbox.setRows(6);
		textbox.setReadonly(true);
		textbox.setStyle("background-color:transparent;color:red;border-color:transparent");
		hlayout.setStyle("color:" + color);

		// Label label = new Label(mensaje);
		if (tipo == 1 || tipo == 3) {
			Html html = new Html("<p>" + mensaje + "</p>");
			hlayout.appendChild(html);
		} else {
			hlayout.appendChild(textbox);
		}

		result.appendChild(hlayout);
	}

	public void onSeleccionarEntidad() {
		String entidad = listboxEntidades.getSelectedItem().getValue()
				.toString();
		if (entidad.equalsIgnoreCase("ESE_CARTAGENA")) {
			rowContratos.setVisible(true);
		} else {
			rowContratos.setVisible(false);
		}
	}

	private void parametrizarBandboxAdministradora() {
		bandboxAdministradora.inicializar(tbxAdministradora,
				btnLimpiar_administradora);
		BandboxRegistrosIMG<Administradora> bandboxRegistrosIMG = new BandboxRegistrosIMG<Administradora>() {

			@Override
			public void renderListitem(Listitem listitem,
					Administradora registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre()));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Administradora> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toLowerCase().trim() + "%");

				ImportarPacientesAction.this.getServiceLocator()
						.getAdministradoraService()
						.setLimit("limit 25 offset 0");

				return ImportarPacientesAction.this.getServiceLocator()
						.getAdministradoraService().listar(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Administradora registro) {
				boolean retorno = true;
				bandbox.setValue(registro.getCodigo());
				textboxInformacion.setValue(registro.getNombre());
				listarContratos(listboxContratos, registro.getCodigo(), true,
						true);
				buttonDeshabilitar.setDisabled(false);
				return retorno;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				listboxContratos.getItems().clear();
				buttonDeshabilitar.setDisabled(true);
			}
		};
		bandboxAdministradora.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	public void listarContratos(Listbox listbox, String codigo_admin,
			boolean select, boolean solo_abiertos) {

		listbox.getItems().clear();

		Listitem listitem;

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("codigo_administradora", codigo_admin);
		if (solo_abiertos) {
			parameters.put("cerrado", false);
		}
		List<Contratos> lista_contratos = getServiceLocator()
				.getContratosService().listar(parameters);

		for (Contratos contratos : lista_contratos) {
			listitem = new Listitem();
			listitem.setValue(contratos);
			listitem.appendChild(new Listcell());
			listitem.appendChild(new Listcell(contratos.getId_plan() + " - "
					+ contratos.getNro_contrato() + " - "
					+ contratos.getNombre()));
			listbox.appendChild(listitem);
		}
	}

	public void onSeleccionarTipoImportacion() {
		String tipo = lbxTipo_importacion.getSelectedItem().getValue()
				.toString();
		if (!tipo.isEmpty()) {
			if (tipo.equals("01")) {
				listboxContratos.setVisible(true);
			} else if (tipo.equals("02")) {
				listboxContratos.setVisible(true);
			} else if (tipo.equals("03")) {
				listboxContratos.setVisible(false);
			}
		}
	}

	public void descargarDemoArchivo() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < IConstantes.COLUMNAS_ARCHIVO.length; i++) {
			String name_col = IConstantes.COLUMNAS_ARCHIVO[i];
			stringBuilder.append(name_col).append(",");
		}
		Filedownload.save(stringBuilder.toString(), "text/plain",
				"demo_archivo.csv");
	}

	/**
	 * Este metodo me permite deshabilitar los pacientes que de la aseguradora
	 * que esta seleccionada
	 * 
	 * @author Luis Miguel Hernandez
	 * */
	public void onDeshabilitarPacientes() {
		Messagebox.show("Esta seguro que deseas deshabilitar los pacientes? ",
				"Deshabilitar Pacientes", Messagebox.YES + Messagebox.NO,
				Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						if ("onYes".equals(event.getName())) {
							// --------------------------
							try {
								Administradora administradora = bandboxAdministradora
										.getRegistroSeleccionado();
								if (administradora != null) {
									Map<String, Object> mapa_contratos = new HashMap<String, Object>();
									mapa_contratos.put("codigo_empresa",
											codigo_empresa);
									mapa_contratos.put("codigo_sucursal",
											codigo_sucursal);
									mapa_contratos.put("codigo_administradora",
											administradora.getCodigo());
									int eliminados = getServiceLocator()
											.getServicio(
													Pacientes_contratosService.class)
											.eliminar_contratos_varios(
													mapa_contratos);
									if (eliminados > 0) {
										MensajesUtil
												.mensajeInformacion(
														"Informacion",
														"Se han deshabilitado los pacientes satisfactoriamente!");
									} else {
										MensajesUtil
												.mensajeAlerta(
														"Advertencia",
														"El sistema no encontro ningún paciente relacionado con los contratos de esta aseguradora");
									}
								} else {
									MensajesUtil
											.mensajeAlerta("Advertencia",
													"Para realizar esta accion debe seleccionar la administradora");
								}
							} catch (Exception e) {
								MensajesUtil.mensajeError(e, null,
										ImportarPacientesAction.this);
							}
							// --------------------------
						}
					}
				});
	}
}
