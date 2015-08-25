/*
 * evolucion_medicaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cama;
import healthmanager.modelo.bean.Detalle_evolucion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Evolucion_medica;
import healthmanager.modelo.bean.Habitacion;
import healthmanager.modelo.bean.Pabellon;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Evolucion_medicaAction extends ZKWindow {

	private static Logger log = Logger.getLogger(Evolucion_medicaAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Window form;

	// Componentes //
	@View
	private Listbox lbxFormato;
	@View
	private Textbox tbxAccion;
	@View
	private Textbox tbxCodigo_evolucion;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxCodigo_administradora;
	@View
	private Textbox tbxNro_ingreso;
	@View
	private Textbox tbxTipo_hc;

	@View
	private Bandbox tbxCodigo_prestador;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Listbox lbxCodigo_cama;
	@View
	private Listbox lbxCodigo_habitacion;

	@View
	private Grid gridEvoluciones;
	@View
	private Rows rowsEvoluciones;

	private List<Map> lista_datos;

	@View
	private Row rowPrestador_evoluciones;
	@View
	private Row rowHabitacionCama;

	@View
	private Groupbox groupboxEditar;

	private final String[] idsExc = { "tbxValue", "tbxNro_identificacion",
			"tbxNro_ingreso", "tbxCodigo_administradora", "tbxTipo_hc" };

	public void loadComponents() {
		form = (Window) this.getFellow("formEvolucion_medica");

		lbxFormato = (Listbox) form.getFellow("lbxFormato");
		tbxCodigo_evolucion = (Textbox) form.getFellow("tbxCodigo_evolucion");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		tbxNro_identificacion = (Textbox) form
				.getFellow("tbxNro_identificacion");
		tbxCodigo_administradora = (Textbox) form
				.getFellow("tbxCodigo_administradora");
		tbxNro_ingreso = (Textbox) form.getFellow("tbxNro_ingreso");
		tbxTipo_hc = (Textbox) form.getFellow("tbxTipo_hc");

		tbxCodigo_prestador = (Bandbox) form.getFellow("tbxCodigo_prestador");
		tbxNomPrestador = (Textbox) form.getFellow("tbxNomPrestador");

		lbxCodigo_cama = (Listbox) form.getFellow("lbxCodigo_cama");
		lbxCodigo_habitacion = (Listbox) form.getFellow("lbxCodigo_habitacion");

		gridEvoluciones = (Grid) form.getFellow("gridEvoluciones");
		rowsEvoluciones = (Rows) form.getFellow("rowsEvoluciones");
		rowPrestador_evoluciones = (Row) form
				.getFellow("rowPrestador_evoluciones");

		rowHabitacionCama = (Row) form.getFellow("rowHabitacionCama");

	}

	public void initEvolucion_medica() throws Exception {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		try {
			String id_m = request.getParameter("id_menu");
			if (id_m != null) {
				// crearPermisos(new Integer(id_m));
			}
			Map parametros = Executions.getCurrent().getArg();
			String nro_identificacion = (String) parametros
					.get("nro_identificacion");
			String nro_ingreso = (String) parametros.get("nro_ingreso");
			String estado = (String) parametros.get("estado");
			String codigo_administradora = (String) parametros
					.get("codigo_administradora");
			String tipo_hc = (String) parametros.get("tipo_hc");
			String ocultar_prestador = (String) parametros
					.get("ocultar_prestador");

			if (ocultar_prestador != null) {
				rowPrestador_evoluciones.setVisible(false);
			}

			tbxNro_identificacion.setValue(nro_identificacion);
			tbxNro_ingreso.setValue(nro_ingreso);
			tbxCodigo_administradora.setValue(codigo_administradora);
			tbxTipo_hc.setValue(tipo_hc);

			if (nro_identificacion.equals("") && nro_ingreso.equals("")) {
				accionForm(true, "registrar");
				deshabilitarCampos(true);
			} else {
				Evolucion_medica evolucion_medica = new Evolucion_medica();
				evolucion_medica.setCodigo_empresa(empresa.getCodigo_empresa());
				evolucion_medica.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				evolucion_medica.setNro_identificacion(nro_identificacion);
				evolucion_medica.setNro_ingreso(nro_ingreso);
				evolucion_medica.setTipo_hc(tipo_hc);
				evolucion_medica = getServiceLocator()
						.getEvolucion_medicaService().consultar(
								evolucion_medica);
				if (evolucion_medica != null) {
					mostrarDatos(evolucion_medica);
				} else {
					accionForm(true, "registrar");
				}

				if (estado.equals("2")) {
					deshabilitarCampos(true);
				} else {
					deshabilitarCampos(false);
				}
			}

			OcultarHabitacionCama();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// metodo que oculta habitacion y cama cuando la via de ingreso
	// es de urgencia
	public void OcultarHabitacionCama() {
		Admision admision = new Admision();
		admision.setCodigo_empresa(empresa.getCodigo_empresa());
		admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		admision.setNro_identificacion(tbxNro_identificacion.getValue());
		admision.setNro_ingreso(tbxNro_ingreso.getValue());
		admision = getServiceLocator().getServicio(AdmisionService.class).consultar(admision);

		if (admision != null) {
			if (admision.getVia_ingreso().equals(
					IVias_ingreso.HOSPITALIZACIONES)) {
				rowHabitacionCama.setVisible(false);
			} else if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)) {
				rowHabitacionCama.setVisible(false);
			}
		}
	}

	public void cargarPrestador() throws Exception {
		if (rol_usuario.equals("05")) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
			prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			prestadores.setNro_identificacion(usuarios.getCodigo());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			if (prestadores == null) {
				throw new Exception(
						"Usuario no se encuentra registrado como un prestador");
			}
			tbxCodigo_prestador.setValue(prestadores.getNro_identificacion());
			tbxNomPrestador.setValue(prestadores.getNombres() + " "
					+ prestadores.getApellidos());

		} else {
			Admision admision = new Admision();
			admision.setCodigo_empresa(empresa.getCodigo_empresa());
			admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			admision.setNro_identificacion(tbxNro_identificacion.getValue());
			admision.setNro_ingreso(tbxNro_ingreso.getValue());
			admision = getServiceLocator().getServicio(AdmisionService.class).consultar(
					admision);

			if (admision != null) {
				Prestadores prestadores = new Prestadores();
				prestadores.setCodigo_empresa(admision.getCodigo_empresa());
				prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
				prestadores.setNro_identificacion(admision.getCodigo_medico());
				prestadores = getServiceLocator().getPrestadoresService()
						.consultar(prestadores);

				tbxCodigo_prestador.setValue((prestadores != null ? prestadores
						.getNro_identificacion() : "000000000"));
				tbxNomPrestador.setValue((prestadores != null ? prestadores
						.getNombres() + " " + prestadores.getApellidos()
						: "MEDICO POR DEFECTO"));

			} else {
				tbxCodigo_prestador.setValue("000000000");
				tbxNomPrestador.setValue("MEDICO POR DEFECTO");
			}
		}
	}

	public void listarCombos() {
		listarHabitacion(lbxCodigo_habitacion, true);
		listarCamas(lbxCodigo_habitacion, lbxCodigo_cama, true);
	}

	public void listarHabitacion(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> parameters = new HashMap();
		parameters.put("codigo_empresa", empresa.getCodigo_empresa());
		parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		List<Habitacion> lista_habitaciones = this.getServiceLocator()
				.getHabitacionService().listar(parameters);

		for (Habitacion habitacion : lista_habitaciones) {
			Pabellon pabellon = new Pabellon();
			pabellon.setCodigo_empresa(habitacion.getCodigo_empresa());
			pabellon.setCodigo_sucursal(habitacion.getCodigo_sucursal());
			pabellon.setCodigo(habitacion.getCodigo_pabellon());
			pabellon.setCodigo_centro(habitacion.getCodigo_centro());
			pabellon.setTipo_atencion(habitacion.getTipo_atencion());
			pabellon.setCodigo_centro(centro_atencion_session
					.getCodigo_centro());
			pabellon = getServiceLocator().getPabellonService().consultar(
					pabellon);

			Elemento e = new Elemento();
			e.setCodigo(habitacion.getTipo_atencion());
			e.setTipo("tipo_atencion");
			e = getServiceLocator().getElementoService().consultar(e);

			listitem = new Listitem();
			listitem.setValue(habitacion);
			listitem.setLabel((e != null ? e.getDescripcion() : "") + "-"
					+ (pabellon != null ? pabellon.getNombre() : "") + "-"
					+ habitacion.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarCamas(Listbox listboxHab, Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		Habitacion hab = (Habitacion) listboxHab.getSelectedItem().getValue();
		if (hab != null) {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", hab.getCodigo_empresa());
			parameters.put("codigo_sucursal", hab.getCodigo_sucursal());
			parameters.put("tipo_atencion", hab.getTipo_atencion());
			parameters.put("codigo_pabellon", hab.getCodigo_pabellon());
			parameters.put("codigo_habitacion", hab.getCodigo());

			List<Cama> lista_camas = this.getServiceLocator().getCamaService()
					.listar(parameters);

			for (Cama cama : lista_camas) {
				listitem = new Listitem();
				listitem.setValue(cama);
				listitem.setLabel(cama.getNombre());
				listbox.appendChild(listitem);
			}
		}

		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		if (accion.equalsIgnoreCase("registrar")) {
			limpiarDatos();
		}

		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExc);
		cargarPrestador();
		lista_datos.clear();
		crearFilas();
	}

	// Limpiamos los campos del formulario //
	public void deshabilitarCampos(boolean sw) throws Exception {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, true,
				new String[] { "lbxFormato", "tbxValue", "btGuardar","btImprimir" });

		for (int i = 0; i < lista_datos.size(); i++) {
			Row fila = (Row) rowsEvoluciones.getFellow("fila_" + i);

			// Datebox dtbxFecha = (Datebox) fila.getFellow("fecha_" + i);
			Textbox tbxEvoluciones = (Textbox) fila.getFellow("evoluciones_"
					+ i);
			Textbox tbxOrdenes = (Textbox) fila.getFellow("ordenes_" + i);
			Textbox tbxObjetivos = (Textbox) fila.getFellow("objetivos_" + i);
			Textbox tbxAnalisis = (Textbox) fila.getFellow("analisis_" + i);
			// Textbox tbxPrestador = (Textbox) fila.getFellow("prestador_" +
			// i);

			Image img = (Image) fila.getFellow("img_" + i);

			if (!sw) {
				// dtbxFecha.setDisabled(false);
				tbxEvoluciones.setDisabled(false);
				tbxOrdenes.setDisabled(false);
				tbxObjetivos.setDisabled(false);
				tbxAnalisis.setDisabled(false);
				img.setVisible(true);
			} else {
				// dtbxFecha.setDisabled(true);
				tbxEvoluciones.setDisabled(true);
				tbxOrdenes.setDisabled(true);
				tbxObjetivos.setDisabled(true);
				tbxAnalisis.setDisabled(true);
				img.setVisible(false);
			}
		}

		if (rol_usuario.equals("05")) {
			tbxCodigo_prestador.setDisabled(true);
		} else {
			tbxCodigo_prestador.setDisabled(sw);
		}

	}

	public void buscarPrestador(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Prestadores> list = getServiceLocator()
					.getPrestadoresService().listar(parameters);

			lbx.getItems().clear();

			for (Prestadores dato : list) {

				Especialidad especialidad = new Especialidad();
				especialidad.setCodigo(dato.getCodigo_especialidad());
				especialidad = getServiceLocator().getEspecialidadService()
						.consultar(especialidad);

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombres()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellidos()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(
						(especialidad != null ? especialidad.getNombre() : "")));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void selectedPrestador(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxCodigo_prestador.setValue("");
			tbxNomPrestador.setValue("");
		} else {
			Prestadores dato = (Prestadores) listitem.getValue();
			tbxCodigo_prestador.setValue(dato.getNro_identificacion());
			tbxNomPrestador.setValue(dato.getNombres() + " "
					+ dato.getApellidos());
		}
		tbxCodigo_prestador.close();
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxCodigo_prestador
				.setStyle("text-transform:uppercase;background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxCodigo_prestador.getText().trim().equals("")) {
			tbxCodigo_prestador
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida && lista_datos.isEmpty()
				&& tbxCodigo_evolucion.getValue().isEmpty()) {
			mensaje = "Debe crear al menos un registro de evolucion";
			valida = false;
		} else {
			actualizarPagina();
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			// setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //

				if (lista_datos.isEmpty()
						&& !tbxCodigo_evolucion.getValue().isEmpty()) {
					Evolucion_medica evolucion_medica = new Evolucion_medica();
					evolucion_medica.setCodigo_empresa(empresa
							.getCodigo_empresa());
					evolucion_medica.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					evolucion_medica.setCodigo_evolucion(tbxCodigo_evolucion
							.getValue());
					getServiceLocator().getEvolucion_medicaService().eliminar(
							evolucion_medica);
					tbxAccion.setValue("registrar");
					MensajesUtil.mensajeAlerta(
							"No hay registros en la evolucion",
							"Debe crear al menos un registro de evolucion");
				} else {

					Map datos = new HashMap();

					Habitacion habitacion = (Habitacion) lbxCodigo_habitacion
							.getSelectedItem().getValue();
					Cama cama = (Cama) lbxCodigo_cama.getSelectedItem()
							.getValue();

					Evolucion_medica evolucion_medica = new Evolucion_medica();
					evolucion_medica.setCodigo_empresa(empresa
							.getCodigo_empresa());
					evolucion_medica.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					evolucion_medica.setCodigo_evolucion(tbxCodigo_evolucion
							.getValue());
					evolucion_medica
							.setNro_identificacion(tbxNro_identificacion
									.getValue());
					evolucion_medica
							.setCodigo_administradora(tbxCodigo_administradora
									.getValue());
					evolucion_medica.setCodigo_prestador(tbxCodigo_prestador
							.getValue());
					evolucion_medica.setNro_ingreso(tbxNro_ingreso.getValue());
					evolucion_medica.setCodigo_cama((cama != null ? cama
							.getCodigo() : 0));
					evolucion_medica
							.setCodigo_habitacion((habitacion != null ? habitacion
									.getCodigo() : 0));
					evolucion_medica
							.setCodigo_pabellon((habitacion != null ? habitacion
									.getCodigo_pabellon() : 0));
					evolucion_medica.setCreacion_date(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					evolucion_medica.setUltimo_update(new Timestamp(
							new GregorianCalendar().getTimeInMillis()));
					evolucion_medica.setCreacion_user(usuarios.getCodigo());
					evolucion_medica.setUltimo_user(usuarios.getCodigo()
							.toString());
					evolucion_medica
							.setTipo_atencion((habitacion != null ? habitacion
									.getTipo_atencion() : ""));
					evolucion_medica.setTipo_hc(tbxTipo_hc.getValue());

					List<Detalle_evolucion> lista_detalle = new ArrayList<Detalle_evolucion>();
					for (int i = 0; i < lista_datos.size(); i++) {
						Map bean = lista_datos.get(i);

						String consecutivo = i + "";
						Timestamp fecha = (Timestamp) bean.get("fecha");
						String evoluciones = (String) bean.get("evoluciones");
						String ordenes = (String) bean.get("ordenes");
						String objetivos = (String) bean.get("objetivos");
						String analisis = (String) bean.get("analisis");
						String prestador = (String) bean.get("prestador");

						Detalle_evolucion detalle = new Detalle_evolucion();
						detalle.setCodigo_empresa(evolucion_medica
								.getCodigo_empresa());
						detalle.setCodigo_sucursal(evolucion_medica
								.getCodigo_sucursal());
						detalle.setCodigo_evolucion("");
						detalle.setConsecutivo(consecutivo);
						detalle.setFecha(fecha);
						detalle.setEvoluciones(evoluciones);
						detalle.setOrdenes(ordenes);
						detalle.setObjetivos(objetivos);
						detalle.setAnalisis(analisis);
						detalle.setPrestador(prestador);
						detalle.setCreacion_date(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						detalle.setUltimo_update(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						detalle.setCreacion_user(usuarios.getCodigo());
						detalle.setUltimo_user(usuarios.getCodigo());
						lista_detalle.add(detalle);
					}

					datos.put("evolucion_medica", evolucion_medica);
					datos.put("lista_detalle", lista_detalle);
					datos.put("accion", tbxAccion.getText());

					evolucion_medica = getServiceLocator()
							.getEvolucion_medicaService().guardar(datos);
					if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
						tbxAccion.setText("modificar");
					}
					mostrarDatos(evolucion_medica);

					final String codigo_evolucion = evolucion_medica
							.getCodigo_evolucion();

					Messagebox
							.show("Los datos se guardaron satisfactoriamente. Desea imprimir el documento? ",
									"Impresion", Messagebox.YES
											+ Messagebox.NO,
									Messagebox.QUESTION,
									new org.zkoss.zk.ui.event.EventListener() {
										public void onEvent(Event event)
												throws Exception {
											if ("onYes".equals(event.getName())) {
												// do the thing
												imprimir(codigo_evolucion);
											}
										}
									});

				}
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Evolucion_medica evolucion_medica = (Evolucion_medica) obj;
		try {
			tbxCodigo_evolucion
					.setValue(evolucion_medica.getCodigo_evolucion());
			tbxNro_identificacion.setValue(evolucion_medica
					.getNro_identificacion());
			tbxCodigo_administradora.setValue(evolucion_medica
					.getCodigo_administradora());

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(evolucion_medica.getCodigo_empresa());
			prestadores.setCodigo_sucursal(evolucion_medica
					.getCodigo_sucursal());
			prestadores.setNro_identificacion(evolucion_medica
					.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			tbxCodigo_prestador
					.setValue(evolucion_medica.getCodigo_prestador());
			tbxNomPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));
			tbxNro_ingreso.setValue(evolucion_medica.getNro_ingreso());

			for (int i = 0; i < lbxCodigo_habitacion.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_habitacion.getItemAtIndex(i);
				Habitacion habitacion = (Habitacion) listitem.getValue();
				if (habitacion != null) {
					if (habitacion.getCodigo() == evolucion_medica
							.getCodigo_habitacion()) {
						listitem.setSelected(true);
						i = lbxCodigo_habitacion.getItemCount();
					}
				}
			}

			listarCamas(lbxCodigo_habitacion, lbxCodigo_cama, true);

			for (int i = 0; i < lbxCodigo_cama.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_cama.getItemAtIndex(i);
				Cama cama = (Cama) listitem.getValue();
				if (cama != null) {
					if (cama.getCodigo() == evolucion_medica.getCodigo_cama()) {
						listitem.setSelected(true);
						i = lbxCodigo_cama.getItemCount();
					}
				}
			}

			lista_datos.clear();
			rowsEvoluciones.getChildren().clear();
			List<Detalle_evolucion> lista_detalle = evolucion_medica
					.getLista_detalle();
			for (Detalle_evolucion detalle : lista_detalle) {

				Map bean = llenarBeanDetalle(detalle);
				lista_datos.add(bean);
			}
			crearFilas();
			// adicionarBlanco();

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(String codigo_evolucion) throws Exception {
		final String cod_ev = codigo_evolucion;
		if (codigo_evolucion.equals("")) {
			Messagebox.show("La evolucion no se ha guardado aun",
					"Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		try {
			Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								Evolucion_medica evolucion_medica = new Evolucion_medica();
								evolucion_medica.setCodigo_empresa(empresa
										.getCodigo_empresa());
								evolucion_medica.setCodigo_sucursal(sucursal
										.getCodigo_sucursal());
								evolucion_medica.setCodigo_evolucion(cod_ev);
								int result = getServiceLocator()
										.getEvolucion_medicaService().eliminar(
												evolucion_medica);
								if (result == 0) {
									throw new Exception(
											"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
								}

								accionForm(true, "registrar");

								Messagebox
										.show("Informacion se elimino satisfactoriamente !!",
												"Information", Messagebox.OK,
												Messagebox.INFORMATION);
							}
						}
					});

		} catch (HealthmanagerException e) {
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void imprimir(String codigo_evolucion) throws Exception {
		if (codigo_evolucion.equals("")) {
			Messagebox.show("La evolucion no se ha guardado aun",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Evolucion_medica");
		paramRequest.put("codigo_evolucion", codigo_evolucion);
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", form, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	private Map llenarBeanDetalle(Detalle_evolucion detalle) throws Exception {
		Map bean = new HashMap();
		bean.put("fecha", detalle.getFecha());
		bean.put("evoluciones", detalle.getEvoluciones());
		bean.put("ordenes", detalle.getOrdenes());
		bean.put("objetivos", detalle.getObjetivos());
		bean.put("analisis", detalle.getAnalisis());
		bean.put("prestador", detalle.getPrestador());
		return bean;
	}

	public void adicionarNuevo() throws Exception {
		actualizarPagina();
		Detalle_evolucion detalle_evolucion = new Detalle_evolucion();
		detalle_evolucion.setFecha(new Timestamp(new Date().getTime()));
		detalle_evolucion.setEvoluciones("");
		detalle_evolucion.setOrdenes("");
		detalle_evolucion.setObjetivos("");
		detalle_evolucion.setAnalisis("");
		detalle_evolucion.setPrestador(usuarios.getCodigo());
		adicionarEvolucion(detalle_evolucion);
	}

	public void adicionarEvolucion(Detalle_evolucion detalle) throws Exception {
		try {
			Map bean = llenarBeanDetalle(detalle);
			lista_datos.add(bean);
			crearFilas();
			// repintarGridCuentas();
		} catch (Exception e) {
			Messagebox.show(e.getMessage(), "Error al adicionar cuenta",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void crearFilas() throws Exception {
		rowsEvoluciones.getChildren().clear();
		for (int j = 0; j < lista_datos.size(); j++) {
			Map bean = lista_datos.get(j);
			crearFilaEvolucion(bean, j);
		}
	}

	public void crearFilaEvolucion(Map bean, int j) throws Exception {
		final int index = j;
		Timestamp fecha = (Timestamp) bean.get("fecha");
		String evoluciones = (String) bean.get("evoluciones");
		String ordenes = (String) bean.get("ordenes");
		String objetivos = (String) bean.get("objetivos");
		String analisis = (String) bean.get("analisis");
		String codigo_prestador = (String) bean.get("prestador");

		Prestadores prestador = new Prestadores();
		prestador.setCodigo_empresa(empresa.getCodigo_empresa());
		prestador.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		prestador.setNro_identificacion(codigo_prestador);
		prestador = getServiceLocator().getServicio(PrestadoresService.class)
				.consultar(prestador);

		final Row fila = new Row();
		fila.setStyle("text-align: center;nowrap:nowrap");

		// // Columna fecha //
		// Cell cell = new Cell();
		// cell.setAlign("left");
		// cell.setValign("top");
		final Datebox dtbxFecha = new Datebox();
		if (fecha != null) {
			dtbxFecha.setValue(new Date(fecha.getTime()));
		}
		dtbxFecha.setFormat("yyyy-MM-dd HH:mm");
		// dtbxFecha.setInplace(true);
		dtbxFecha.setId("fecha_" + j);
		dtbxFecha.setVisible(false);

		final Textbox tbxPrestador = new Textbox();
		if (prestador == null) {
			tbxPrestador.setText(usuarios.getCodigo());
		} else {
			tbxPrestador.setText(prestador.getNro_identificacion());
		}
		tbxPrestador.setId("prestador_" + j);
		tbxPrestador.setVisible(false);

		// dtbxFecha.setHflex("1");
		// cell.appendChild(dtbxFecha);
		// fila.appendChild(cell);

		// Columna evoluciones //
		Cell cell = new Cell();
		cell.setAlign("left");
		cell.setHflex("1");
		Groupbox gbxEvolucion = new Groupbox();
		gbxEvolucion.setClosable(true);
		gbxEvolucion.setMold("3d");
		gbxEvolucion.setHflex("1");

		Caption cptEvolucion = new Caption();
		String lprestador = (prestador != null ? prestador.getNombres() + " "
				+ prestador.getApellidos() : "");
		String lfecha = new SimpleDateFormat("dd/MM/yyyy HH:mm")
				.format(new Date(fecha.getTime()));

		cptEvolucion.setLabel("PRESTADOR: " + lprestador + " [" + lfecha + "]");
		Image img = new Image("/images/borrar.gif");
		img.setId("img_" + j);
		img.setTooltiptext("Quitar registro");
		img.setStyle("cursor:pointer");

		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									actualizarPagina();
									lista_datos.remove(index);
									crearFilas();
								}
							}
						});
			}
		});

		cptEvolucion.appendChild(dtbxFecha);
		cptEvolucion.appendChild(tbxPrestador);
		cptEvolucion.appendChild(img);
		gbxEvolucion.appendChild(cptEvolucion);

		Hbox hbox2 = new Hbox();
		hbox2.setHflex("1");
		Vbox vbox1 = new Vbox();
		vbox1.setHflex("1");
		Label titulo_subjetivo = new Label("SUBJETIVO");
		titulo_subjetivo.setStyle("font-weight: bold");
		final Textbox tbxEvoluciones = new Textbox(evoluciones);
		// tbxEvoluciones.setInplace(true);
		tbxEvoluciones.setId("evoluciones_" + j);
		tbxEvoluciones.setHflex("1");
		tbxEvoluciones.setRows(7);
		vbox1.appendChild(titulo_subjetivo);
		vbox1.appendChild(tbxEvoluciones);

		// Columna analisis //
		Label titulo_analisis = new Label("ANALISIS");
		titulo_analisis.setStyle("font-weight: bold");
		final Textbox tbxAnalisis = new Textbox(analisis);
		// tbxAnalisis.setInplace(true);
		tbxAnalisis.setId("analisis_" + j);
		tbxAnalisis.setHflex("1");
		tbxAnalisis.setRows(7);
		vbox1.appendChild(titulo_analisis);
		vbox1.appendChild(tbxAnalisis);

		hbox2.appendChild(vbox1);

		// Columna objetivos //

		Vbox vbox2 = new Vbox();
		vbox2.setHflex("1");
		Label titulo_objetivo = new Label("OBJETIVO");
		titulo_objetivo.setStyle("font-weight: bold");
		final Textbox tbxObjetivos = new Textbox(objetivos);
		// tbxObjetivos.setInplace(true);
		tbxObjetivos.setId("objetivos_" + j);
		tbxObjetivos.setHflex("1");
		tbxObjetivos.setRows(7);
		vbox2.appendChild(titulo_objetivo);
		vbox2.appendChild(tbxObjetivos);

		// Columna evoluciones //
		Label titulo_orden = new Label("PLAN");
		titulo_orden.setStyle("font-weight: bold");
		final Textbox tbxOrdenes = new Textbox(ordenes);
		tbxOrdenes.setId("ordenes_" + j);
		tbxOrdenes.setHflex("1");
		tbxOrdenes.setRows(7);
		vbox2.appendChild(titulo_orden);
		vbox2.appendChild(tbxOrdenes);

		hbox2.appendChild(vbox2);
		gbxEvolucion.appendChild(hbox2);

		cell.appendChild(gbxEvolucion);
		fila.appendChild(cell);
		fila.setId("fila_" + j);

		rowsEvoluciones.appendChild(fila);

		gridEvoluciones.setVisible(true);
		gridEvoluciones.setMold("paging");
		gridEvoluciones.setPageSize(20);
		gridEvoluciones.applyProperties();
		gridEvoluciones.invalidate();

	}

	public void actualizarPagina() throws Exception {
		for (int i = 0; i < lista_datos.size(); i++) {
			Map bean = lista_datos.get(i);

			Row fila = (Row) rowsEvoluciones.getFellow("fila_" + i);
			Datebox dtbxFecha = (Datebox) fila.getFellow("fecha_" + i);
			Textbox tbxEvoluciones = (Textbox) fila.getFellow("evoluciones_"
					+ i);
			Textbox tbxOrdenes = (Textbox) fila.getFellow("ordenes_" + i);
			Textbox tbxObjetivos = (Textbox) fila.getFellow("objetivos_" + i);
			Textbox tbxAnalisis = (Textbox) fila.getFellow("analisis_" + i);
			Textbox tbxPrestador = (Textbox) fila.getFellow("prestador_" + i);

			bean.put("fecha", new Timestamp(dtbxFecha.getValue().getTime()));
			bean.put("evoluciones", tbxEvoluciones.getValue().trim());
			bean.put("ordenes", tbxOrdenes.getValue().trim());
			bean.put("objetivos", tbxObjetivos.getValue().trim());
			bean.put("analisis", tbxAnalisis.getValue().trim());
			bean.put("prestador", tbxPrestador.getValue().trim());

			lista_datos.set(i, bean);
		}
	}

	@Override
	public void init() throws Exception {
		lista_datos = new ArrayList<Map>();
		listarCombos();
		try {
			initEvolucion_medica();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
