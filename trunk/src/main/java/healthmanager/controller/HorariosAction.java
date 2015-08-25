/*
 * horariosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Horarios_medico;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.Horarios_medicoService;
import healthmanager.modelo.service.Roles_usuarios_capsService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.calendar.api.CalendarEvent;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.interfaces.EventosHorarios;
import com.framework.interfaces.ISeleccionarMedico;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.BandboxRegistrosIMG.ISeleccionarItem;
import com.framework.res.L2HContraintDate;
import com.framework.res.Res;
import com.framework.res.ZKSimpleCalendarEvent;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.framework.util.secuencia_hora.DetallesHorariosModel;

public class HorariosAction extends ZKWindow implements EventosHorarios,
		ISeleccionarMedico {

	private static Logger log = Logger.getLogger(HorariosAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Button btGuardar;

	// Componentes //
	@View
	private Textbox tbxAccion;
	@View
	private Groupbox groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador;
	@View
	private Textbox tbxNombrePrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;
	@View
	private Toolbarbutton btTransladarHorario;
	@View
	private Listbox listboxMeses;
	@View
	private Listbox lbxAnios;

	@View
	private Listbox lbxRoles;

//	@View
//	private Spinner spinnerTiempo_cita;

	@View
	private Center centerCalendars;

	private Horarios_medicoService horarios_medicoService;

	private DetallesHorariosModel<Detalles_horarios> detallesHorariosModel = new DetallesHorariosModel<Detalles_horarios>();

	private SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"EEEEE dd MMMMM yyyy", IConstantes.locale);

	@View
	private Checkbox chkSeleccionar_todos;

	@View
	private Listbox listboxCalendar;

	@View
	private Textbox tbxUltimaSecuencia;
	
	private Horarios_medico horarios_seleccionado;

	private Map<String, Elemento> mapa_roles = new HashMap<String, Elemento>();
	private Map<String, String> centros;

	private Map<String, Consultorio> mapa_consultorios = new HashMap<String, Consultorio>();

	private Boolean filtrado_centro = true;
	private Horarios_medico horarios;
	private Usuarios usurio_medico;
	private List<String> roles_Seleccionados;
	
	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("u.codigo");
		listitem.setLabel("Nro identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("u.nombres");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void changeDate(Date date) {

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (accion.equalsIgnoreCase("modificar")
				|| accion.equalsIgnoreCase("registrar")) {

			agregarEventoCalendar();
			if (!accion.equalsIgnoreCase("registrar")) {
				buscarDatos();
				lbxRoles.setDisabled(true);
			} else {
				limpiarDatos();
				lbxRoles.setDisabled(false);
			}
		} else {
			buscarDatos();
			lbxRoles.setDisabled(true);
		}

		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() {
		tbxCodigo_prestador.setValue("");
		tbxNombrePrestador.setValue("");
		tbxUltimaSecuencia.setValue("");
		if (detallesHorariosModel != null)
			detallesHorariosModel.clear();
		tbxCodigo_prestador.setDisabled(false);
		//lbxRoles.getItems().clear();
		listboxCalendar.getItems().clear();
		horarios_seleccionado = null;
	}

	@Override
	public void eventoSeleccionarMedico(boolean selectd,
			Map<String, Object> dato) {
		// getFellow("btTransladarHorario").setVisible(selectd);

		Horarios_medico horariosMedico = new Horarios_medico();
		horariosMedico.setCodigo_empresa(sucursal.getCodigo_empresa());
		horariosMedico.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		horariosMedico.setCodigo_medico(""
				+ dato.get(Utilidades.CODIGO_USUARIO));
		horariosMedico = horarios_medicoService.consultar(horariosMedico);

		try {
			if (horariosMedico != null) {
				Usuarios usuariosMedico = new Usuarios();
				usuariosMedico.setCodigo_empresa(sucursal.getCodigo_empresa());
				usuariosMedico
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				usuariosMedico.setCodigo(horariosMedico.getCodigo_medico());
				usuariosMedico = getServiceLocator().getUsuariosService()
						.consultar(usuariosMedico);

				mostrarDatos(horariosMedico, usuariosMedico, true, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;
		String mensaje = "";
		int anio_final = (Integer) lbxAnios.getItemAtIndex(
				lbxAnios.getItemCount() - 1).getValue();
		if (detallesHorariosModel.getSublist(getTime(0, 1, anio),
				getTime(11, 31, anio_final)).isEmpty()) {
			valida = false;
			mensaje = "No hay horario de citas que guardar";
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
			parameters.put("cons_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			horarios_medicoService.setLimit("limit 25 offset 0");

			List<Horarios_medico> lista_datos = getServiceLocator()
					.getHorariosMedicoService().listar(parameters);
			rowsResultado.getChildren().clear();

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);

			for (Horarios_medico horarios : lista_datos) {
				parametros.put("codigo_usuario", horarios.getCodigo_medico());
				parametros.put("codigo_centro",	centro_atencion_session.getCodigo_centro());
				List<Roles_usuarios_caps> listado_caps = getServiceLocator()
						.getServicio(Roles_usuarios_capsService.class).listar(parametros);
				boolean esMedico = false;
				for (Roles_usuarios_caps roles_usuarios_caps : listado_caps) {
					if (Utilidades.isMedico(roles_usuarios_caps.getRol())
							|| Utilidades.isEnfermeraJefe(roles_usuarios_caps
									.getRol())) {
						esMedico = true;
						break;
					}
				}
				if (esMedico)
					rowsResultado.appendChild(crearFilas(horarios, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();
		final Horarios_medico horarios = (Horarios_medico) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		Usuarios usuariosMedico = new Usuarios();
		usuariosMedico.setCodigo_empresa(sucursal.getCodigo_empresa());
		usuariosMedico.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		usuariosMedico.setCodigo(horarios.getCodigo_medico());
		final Usuarios usuariosMedicoFinal = getServiceLocator()
				.getUsuariosService().consultar(usuariosMedico);

		fila.setStyle("nowrap:nowrap");
		fila.appendChild(new Label(horarios.getCodigo_medico()));
		fila.appendChild(new Label(
				usuariosMedicoFinal != null ? usuariosMedicoFinal.getNombres()
						+ " " + usuariosMedicoFinal.getApellidos() : ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(horarios, usuariosMedicoFinal, true, true);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/eliminar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este Horario...?",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(horarios);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar, new String[] {});
			if (validarForm()) {
				// Cargamos los componentes //
				Horarios_medico horarios = new Horarios_medico();
				horarios.setCodigo_empresa(sucursal.getCodigo_empresa());
				horarios.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				horarios.setCreacion_date(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				horarios.setCreacion_user(usuarios.getCodigo());
				horarios.setCodigo_medico(tbxCodigo_prestador.getValue());

				horarios.setCodigo_rol("");

				int anio_final = (Integer) lbxAnios.getItemAtIndex(
						lbxAnios.getItemCount() - 1).getValue();

				List<Detalles_horarios> listado_items = detallesHorariosModel
						.getSublist(getTime(0, 1, anio),
								getTime(11, 31, anio_final));

				Map datos = new HashMap();
				datos.put("horarios", horarios);
				datos.put("accion_form", tbxAccion.getText());
				datos.put("detalles", listado_items);
				// //
				horarios_medicoService.guardarHorarioCitas(datos);

				Usuarios usuariosMedico = new Usuarios();
				usuariosMedico.setCodigo_empresa(sucursal.getCodigo_empresa());
				usuariosMedico
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				usuariosMedico.setCodigo(horarios.getCodigo_medico());
				usuariosMedico = getServiceLocator().getUsuariosService()
						.consultar(usuariosMedico);

				mostrarDatos(horarios, usuariosMedico, false, false);

				btnLimpiarPrestador.setVisible(false);

				Messagebox
						.show("Los datos se guardaron satisfactoriamente",
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj, Usuarios usuariosMedicoFinal,
			boolean limpiar, boolean actualizarRoles) throws Exception {
		tbxAccion.setText("modificar");
		detallesHorariosModel.clear();
		accionForm(true, tbxAccion.getText());
		horarios = (Horarios_medico) obj;
		usurio_medico = usuariosMedicoFinal;
		if (limpiar){
			limpiarDatos();
		}
		if(actualizarRoles){
			mostrarRolesDisponibles(usuariosMedicoFinal);
		}
		try {
			tbxCodigo_prestador.setDisabled(true);

			Integer anio_busqueda = lbxAnios.getSelectedItem().getValue();
			Integer mes_busqueda = listboxMeses.getSelectedIndex() + 1;

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", horarios.getCodigo_empresa());
			parameters.put("codigo_sucursal", horarios.getCodigo_sucursal());
			parameters.put("codigo_medico", horarios.getCodigo_medico());
			parameters.put("anio", anio_busqueda);
			parameters.put("mes", mes_busqueda);
			parameters.put("codigo_rols", roles_Seleccionados);
			if(filtrado_centro){
				parameters.put("codigo_centro",	centro_atencion_session.getCodigo_centro());
			}
			//log.info(">>>>>>>>"+parameters);
			List<Detalles_horarios> detallesHorarios = getServiceLocator()
					.getDetallesHorariosService().listar_normal(parameters);

			for (Detalles_horarios detallesHorariosTemp : detallesHorarios) {
				detallesHorariosModel.agregar(detallesHorariosTemp
						.getFecha_inicial().getTime(), detallesHorariosTemp);
			}

			renderizarHorarioPrestador(detallesHorariosModel);

			//log.info("detallesHorarios ===> " + detallesHorarios.size());

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(codigo_empresa);
			prestadores.setCodigo_sucursal(codigo_sucursal);
			prestadores.setNro_identificacion(horarios.getCodigo_medico());
			prestadores = getServiceLocator().getPrestadoresService().consultar(prestadores);
			
			tbxCodigo_prestador.seleccionarRegistro(prestadores, usuariosMedicoFinal.getCodigo(), "");
			tbxNombrePrestador.setValue(usuariosMedicoFinal != null ? 
							usuariosMedicoFinal.getNombres()
							+ " "
							+ usuariosMedicoFinal.getApellidos() : "");

			horarios_seleccionado = horarios;
			
			parameters = new HashMap();
			parameters.put("codigo_empresa", horarios.getCodigo_empresa());
			parameters.put("codigo_sucursal", horarios.getCodigo_sucursal());
			parameters.put("codigo_medico", horarios.getCodigo_medico());
			parameters.put("codigos_roles", roles_Seleccionados);
			if(filtrado_centro){
				parameters.put("codigo_centro",	centro_atencion_session.getCodigo_centro());
			}
			Detalles_horarios ultimo = getServiceLocator().getDetallesHorariosService().consultarUltimoDisponible(parameters);
			
			if(ultimo!=null){
				tbxUltimaSecuencia.setValue(formatoFecha.format(ultimo.getFecha_inicial()).toUpperCase());
			}
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "Este dato no se puede editar", this);
		}
	}

	public void renderizarHorarioPrestador(Map<Long, Detalles_horarios> mapa_horarios) {
		mapa_consultorios.clear();
		if (mapa_horarios == null){
			mapa_horarios = detallesHorariosModel;
		}
		listboxCalendar.getItems().clear();
		chkSeleccionar_todos.setChecked(false);
		String anio = lbxAnios.getSelectedItem().getValue().toString();
		int mes = listboxMeses.getSelectedIndex() + 1;
		String fecha_string = anio + "-" + (mes > 9 ? ("" + mes) : ("0" + mes));
		String fecha_inicial_aux = "";
		boolean existe = false;
		int pagina = 0;
		int registro = 0;
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		
		for (Entry<Long, Detalles_horarios> entryDetalle : mapa_horarios.entrySet()) {
			Detalles_horarios valor = entryDetalle.getValue();
			String fecha_inicial = formatoFecha.format(valor.getFecha_inicial()).toUpperCase();
			String fecha_imprimir = ZKSimpleCalendarEvent.simpleDateFormat.format(valor.getFecha_inicial());
			if (!existe) {
				if (fecha_imprimir.startsWith(fecha_string)) {
					existe = true;
				}
			}

			boolean hay_citas = false;
			if (valor.getConsecutivo() != null) {
				parametros.put("codigo_detalle_horario", valor.getConsecutivo());
				parametros.put("estado", '1');
				hay_citas = getServiceLocator().getCitasService().existe(parametros);
			}
			
			if (fecha_inicial_aux.isEmpty()	|| !fecha_inicial.equals(fecha_inicial_aux)) {
				Listgroup listgroup = new Listgroup();
				listgroup.setVisible(true);
				listgroup.setLabel(fecha_inicial);
				listgroup.setOpen(false);
				String hoy = formatoFecha.format(new Date()).toUpperCase();
				if(fecha_inicial.equalsIgnoreCase(hoy)){
					listgroup.setOpen(true);
				}
				listboxCalendar.appendChild(listgroup);				
				if (!existe){
					registro++;
				}
			}

			fecha_inicial_aux = fecha_inicial;


			Listitem listitem = new Listitem();
			listitem.setValue(valor);

			listitem.setAttribute("HAY_CITAS", hay_citas);
			Listcell listcell = new Listcell();
			listitem.appendChild(listcell);

			listcell = new Listcell(hay_citas ? "ASIGNADO" : "LIBRE");
			listitem.appendChild(listcell);
			listcell = new Listcell(ZKSimpleCalendarEvent.simpleDateFormat.format(valor.getFecha_inicial()));
			listitem.appendChild(listcell);

			listcell = (new Listcell(ZKSimpleCalendarEvent.simpleDateFormat.format(valor.getFecha_final())));
			listitem.appendChild(listcell);

			Consultorio consultorio = null;

			if (mapa_consultorios.containsKey(valor.getCodigo_consultorio())) {
				consultorio = mapa_consultorios.get(valor.getCodigo_consultorio());
			} else {
				consultorio = new Consultorio();
				consultorio.setCodigo_empresa(codigo_empresa);
				consultorio.setCodigo_sucursal(codigo_sucursal);
				consultorio.setCodigo_consultorio(valor.getCodigo_consultorio());
				consultorio = getServiceLocator().getConsultorioService().consultar(consultorio);
				mapa_consultorios.put(valor.getCodigo_consultorio(),consultorio);
			}

			listcell = new Listcell(consultorio != null ? consultorio.getNombre() : "");
			listitem.appendChild(listcell);

			if (mapa_roles.containsKey(valor.getCodigo_rol())) {
				Elemento elemento = mapa_roles.get(valor.getCodigo_rol());
				listcell = new Listcell(elemento != null ? elemento.getDescripcion() : "");
			} else {
				Elemento elemento = new Elemento();
				elemento.setCodigo(valor.getCodigo_rol());
				elemento.setTipo("rol");
				elemento = getServiceLocator().getElementoService().consultar(elemento);
				listcell = new Listcell(elemento != null ? elemento.getDescripcion() : "");
				mapa_roles.put(valor.getCodigo_rol(), elemento);
			}
			listitem.appendChild(listcell);

			listcell = new Listcell(valor != null ? centros.get(valor.getCodigo_centro()) : "");
			listitem.appendChild(listcell);
			
			List<Component> listItems = listitem.getChildren();
			for (Component componente : listItems) {
				if(componente instanceof Listcell){
					Listcell lst = (Listcell) componente; 
					if (hay_citas){
						lst.setStyle("color:red;font-weight:bold");
					}else{
						lst.setStyle("color:blue;font-weight:bold");
					}
				}
			}
			
			listboxCalendar.appendChild(listitem);

			if (!existe)
				registro++;

			if (registro == 25) {
				pagina++;
				registro = 0;
			}
		}
		if (existe) {
			listboxCalendar.setActivePage(pagina);
		} else {
			listboxCalendar.setActivePage(0);
			MensajesUtil.notificarAlerta(
					"No se encontraron registros en este mes", listboxMeses);
		}

	}

	private void parametrizarBandbox() {
		parametrizarBandboxPrestador();
	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_prestador
				.inicializar(tbxNombrePrestador, btnLimpiarPrestador);
		tbxCodigo_prestador
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

					@Override
					public void renderListitem(Listitem listitem,
							Prestadores registro) {

						Especialidad especialidad = new Especialidad();
						especialidad.setCodigo(registro
								.getCodigo_especialidad());
						especialidad = getServiceLocator()
								.getEspecialidadService().consultar(
										especialidad);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombres()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellidos()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_prestador().equals("01") ? "Médico"
								: "Enfermera"));
						listitem.appendChild(listcell);

						listitem.appendChild(listcell);
					}

					@Override
					public List<Prestadores> listarRegistros(String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", "");
						parametros.put("value", "%"	+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("codigo_empresa", sucursal.getCodigo_empresa());
						parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
						parametros.put("codigo_centro", centro_atencion_session.getCodigo_centro());
						parametros.put("limite_paginado", "limit 25 offset 0");
						return getServiceLocator().getPrestadoresService().listarPorCentro(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox, Textbox textboxInformacion, Prestadores registro) {
						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro.getNombres() + " "
								+ registro.getApellidos());
						Especialidad especialidad = new Especialidad();
						especialidad.setCodigo(registro
								.getCodigo_especialidad());
						especialidad = getServiceLocator()
								.getEspecialidadService().consultar(
										especialidad);

						Horarios_medico horarios_medico = new Horarios_medico();
						horarios_medico.setCodigo_empresa(codigo_empresa);
						horarios_medico.setCodigo_sucursal(codigo_sucursal);
						horarios_medico.setCodigo_medico(registro
								.getNro_identificacion());

						horarios_medico = getServiceLocator()
								.getHorariosMedicoService().consultar(
										horarios_medico);

						if (horarios_medico != null) {
							Usuarios usuarios = new Usuarios();
							usuarios.setCodigo_empresa(codigo_empresa);
							usuarios.setCodigo_sucursal(codigo_sucursal);
							usuarios.setCodigo(registro.getNro_identificacion());

							usuarios = getServiceLocator().getUsuariosService()
									.consultar(usuarios);
							if (usuarios != null)
								try {
									mostrarDatos(horarios_medico, usuarios,
											true,true);
								} catch (Exception e) {
									log.error(e);
								}
						} else {
							Usuarios usuarios = new Usuarios();
							usuarios.setCodigo_empresa(codigo_empresa);
							usuarios.setCodigo_sucursal(codigo_sucursal);
							usuarios.setCodigo(registro.getNro_identificacion());
							usuarios = getServiceLocator().getUsuariosService()
									.consultar(usuarios);
							if (usuarios != null)
								mostrarRolesDisponibles(usuarios);
						}

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						lbxRoles.getItems().clear();
						limpiarDatos();
					}

				});

		tbxCodigo_prestador.setSeleccionarItem(new ISeleccionarItem() {

			@Override
			public void accion(Object registro) {
				btnLimpiarPrestador.setVisible(false);
			}

		});
	}

	private void mostrarRolesDisponibles(Usuarios prestadores) {

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_usuario", prestadores.getCodigo());

		lbxRoles.getItems().clear();
		List<Roles_usuarios> listado_roles = getServiceLocator()
				.getRoles_usuariosService().listar(parametros);
		roles_Seleccionados = new ArrayList<String>();
		for (Roles_usuarios roles_usuarios : listado_roles) {
			if (Utilidades.isMedico(roles_usuarios.getRol())
					|| Utilidades.isEnfermeraJefe(roles_usuarios.getRol())) {
				Listitem listitem = new Listitem();
				listitem.setValue(roles_usuarios);
				listitem.setLabel(roles_usuarios.getElementoRol()
						.getDescripcion()
						+ " ("
						+ roles_usuarios.getHoras_medico() + " horas)");
				lbxRoles.appendChild(listitem);
				roles_Seleccionados.add(roles_usuarios.getRol());
			}
		}

		if (lbxRoles.getItemCount() > 0) {
			lbxRoles.selectAll();
		}

	}

	public void onSeleccionarRolMedico() throws Exception {
		if (lbxRoles.getSelectedItems().size()>0) {
			Set<Listitem> roles = lbxRoles.getSelectedItems();
			roles_Seleccionados = new ArrayList<String>();
			for(Listitem listitem : roles){
				Roles_usuarios rol = (Roles_usuarios)listitem.getValue();
				roles_Seleccionados.add(rol.getRol());
			}			
			
			if(horarios!=null && usurio_medico!=null){
				mostrarDatos(horarios,usurio_medico,true,false);
			}
			
//			if (roles_usuarios.getRol().equals("13")) {
//				spinnerTiempo_cita.setValue(20);
//			} else {
//				spinnerTiempo_cita.setValue(15);
//			}
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Horarios_medico horarios_medico = (Horarios_medico) obj;
		try {
			int result = horarios_medicoService.eliminar(horarios_medico);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
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

	@Override
	public void init() {
		inicializarMapas();
		parametrizarBandbox();
		listarCombos();
		initDatos();
		mapa_roles.clear();
	}

	private void inicializarMapas(){
		Map<String, Object> parameters = new HashMap();
		parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
		parameters.put("cons_sucursal", sucursal.getCodigo_sucursal());
		
		List<Centro_atencion> listado_centros = getServiceLocator().getServicio(Centro_atencionService.class).listar(parameters);
		centros = new HashMap<String, String>();
		
		for (Centro_atencion centro : listado_centros) {
			centros.put(centro.getCodigo_centro(), centro.getNombre_centro());
		}
	}
	
	public void trasladarHorario() {
		try {

			final Set<Listitem> listado_seleccionados = listboxCalendar
					.getSelectedItems();

			if (!listado_seleccionados.isEmpty()) {
				Messagebox
						.show("¿Esta seguro que desea trasladar los detalles seleccionados de este horario...?",
								"Trasladar Horarios", Messagebox.YES
										+ Messagebox.NO, Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											List<Detalles_horarios> listado_detalles = new ArrayList<Detalles_horarios>();
											for (Listitem listitem : listado_seleccionados) {
												Boolean hay_citas = (Boolean) listitem
														.getAttribute("HAY_CITAS");
												if (hay_citas) {
													Detalles_horarios detalles_horarios = (Detalles_horarios) listitem
															.getValue();
													if (detalles_horarios
															.getConsecutivo() != null) {
														listado_detalles
																.add(detalles_horarios);
													}
												}
											}

											if (!listado_detalles.isEmpty()) {
												crearTrasladarHorarios(listado_detalles);
											} else {
												MensajesUtil
														.mensajeAlerta(
																"No hay detalles con citas",
																"No se puede hacer el traslado porque los detalles seleccionados no presentan citas");
											}

										}
									}
								});

			} else {
				MensajesUtil
						.mensajeAlerta("No hay items seleccionados",
								"Se deben seleccionar los items que se desean eliminar");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e,
					"Error al eliminar la secuencia de horarios", this);
		}
	}

	public Date getTime(int mes, int day, int anio) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, mes);
		calendar.set(Calendar.DAY_OF_MONTH, day);
		calendar.set(Calendar.YEAR, anio);
		return calendar.getTime();
	}

	public Date getTimeHoras(boolean inicio) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR, inicio ? 0 : 23);
		calendar.set(Calendar.MINUTE, inicio ? 0 : 59);
		calendar.set(Calendar.SECOND, inicio ? 1 : 59);
		return calendar.getTime();
	}

	private void agregarEventoCalendar() {

	}

	protected void agregarNuevoEvento(Date date, String hora_final,
			String evento) throws Exception {
		Map map = new HashMap();
		map.put("date_fecha", date);
		map.put("hora_current", hora_final);
		//map.put("tiempo_cita", spinnerTiempo_cita.getValue());
		map.put("codigo_medico", tbxCodigo_prestador.getValue().toString());
		map.put("evento", evento);
		Roles_usuarios roles_usuarios = (lbxRoles.getSelectedItem() != null ? (Roles_usuarios) lbxRoles
				.getSelectedItem().getValue() : null);
		map.put("rol_seleccionado",
				roles_usuarios != null ? roles_usuarios.getRol() : "");
		Component componente = Executions.createComponents(
				"/pages/createEvent.zul", this, map);
		final Window ventana = (Window) componente;
		ventana.doModal();
	}

	protected void editarEventoAgregado(CalendarEvent datos_evento,
			String evento) throws Exception {
		Map map = new HashMap();
		map.put("evento", evento);
		map.put("datos_evento", datos_evento);
		map.put("codigo_medico", tbxCodigo_prestador.getValue().toString());
		//map.put("tiempo_cita", spinnerTiempo_cita.getValue());
		Component componente = Executions.createComponents(
				"/pages/createEvent.zul", this, map);
		final Window ventana = (Window) componente;
		ventana.doModal();
	}

	@Override
	public void addEvent(ZKSimpleCalendarEvent zkSimpleCalendarEvent)
			throws Exception {
		Calendar calendar_inicio_dia = Calendar.getInstance();
		calendar_inicio_dia.setTime(zkSimpleCalendarEvent.getBeginDate());
		calendar_inicio_dia.set(Calendar.HOUR, 0);
		calendar_inicio_dia.set(Calendar.MINUTE, 1);
		calendar_inicio_dia.set(Calendar.SECOND, 1);

		Calendar calendar_fin_dia = Calendar.getInstance();
		calendar_fin_dia.setTime(zkSimpleCalendarEvent.getBeginDate());
		calendar_fin_dia.set(Calendar.HOUR, 23);
		calendar_fin_dia.set(Calendar.MINUTE, 59);
		calendar_fin_dia.set(Calendar.SECOND, 59);

		List<Detalles_horarios> listado_eventos = detallesHorariosModel
				.getSublist(calendar_inicio_dia.getTime(),
						calendar_fin_dia.getTime());

		boolean agregar_evento = true;

		for (Detalles_horarios detalles_horarios : listado_eventos) {
			if (L2HContraintDate.estaDentroRango(
					zkSimpleCalendarEvent.getBeginDate(),
					detalles_horarios.getFecha_inicial(),
					detalles_horarios.getFecha_final())) {
				agregar_evento = false;
				break;
			}
		}

		Detalles_horarios detalles_horarios = Res
				.converTo(zkSimpleCalendarEvent);

		if (agregar_evento) {
			if (!tbxAccion.getValue().equalsIgnoreCase("registrar")) {
				detalles_horarios.setCodigo_medico(tbxCodigo_prestador
						.getValue());
				detalles_horarios.setCodigo_empresa(codigo_empresa);
				detalles_horarios.setCodigo_sucursal(codigo_sucursal);
				detalles_horarios.setCreacion_user(usuarios.getCodigo());
				detalles_horarios.setMotivo_consulta("1");
				detalles_horarios.setCodigo_centro(zkSimpleCalendarEvent
						.getCodigo_centro_salud());
				detalles_horarios.setCodigo_consultorio(zkSimpleCalendarEvent
						.getCodigo_consulorio());
				// detalles_horarios.setCodigo_rol(roles_usuarios.getRol());
				if (detalles_horarios.getConsecutivo() != null) {
							getServiceLocator().getDetallesHorariosService()
							.actualizar(detalles_horarios);
				} else {
					getServiceLocator().getDetallesHorariosService().crear(
							detalles_horarios);
				}
				zkSimpleCalendarEvent.setConsecutivo(detalles_horarios
						.getConsecutivo());
			}

			detallesHorariosModel.agregar(detalles_horarios.getFecha_inicial()
					.getTime(), detalles_horarios);

			if (zkSimpleCalendarEvent.getEvento_calendar().equals(
					CalendarsEvent.ON_EVENT_CREATE)) {
			}
		} else {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
					"yyyy-MM-dd hh:mm a");
			throw new ValidacionRunTimeException(
					"No se puede agregar el evento "
							+ sdf.format(zkSimpleCalendarEvent.getBeginDate())
							+ " "
							+ sdf.format(zkSimpleCalendarEvent.getEndDate())
							+ " porque ese rango no esta disponible ");
		}
	}

	@Override
	public void eliminarEvent(ZKSimpleCalendarEvent zkSimpleCalendarEvent) {
		if (!tbxAccion.getValue().equalsIgnoreCase("registrar")) {
			if (zkSimpleCalendarEvent.getConsecutivo() != null) {
				Detalles_horarios detalles_horarios = new Detalles_horarios();
				detalles_horarios.setCodigo_empresa(codigo_empresa);
				detalles_horarios.setCodigo_sucursal(codigo_sucursal);
				detalles_horarios.setCodigo_medico(tbxCodigo_prestador
						.getValue());
				detalles_horarios.setConsecutivo(zkSimpleCalendarEvent
						.getConsecutivo());
				getServiceLocator().getDetallesHorariosService().eliminar(
						detalles_horarios);
				detallesHorariosModel.remove(zkSimpleCalendarEvent
						.getBeginDate().getTime());
			}
		} else {
			detallesHorariosModel.remove(zkSimpleCalendarEvent.getBeginDate()
					.getTime());
		}

	}

	@Override
	public void renderizarHorario() {
		filtrarCalendario();
	}

	public void crearSecuenciaHorario() throws Exception {
		try {
			Map map = new HashMap();
			map.put("accion_formulario", tbxAccion.getValue());
			//map.put("tiempo_cita", spinnerTiempo_cita.getValue());
			map.put("codigo_medico", tbxCodigo_prestador.getValue().toString());
			map.put("date", new Date());
			Roles_usuarios roles_usuarios = (lbxRoles.getSelectedItem() != null ? (Roles_usuarios) lbxRoles
					.getSelectedItem().getValue() : null);
			map.put("rol_seleccionado",
					roles_usuarios != null ? roles_usuarios.getRol() : "");

			Component componente = Executions.createComponents(
					"/pages/secuencia_horario.zul", this, map);

			final Window ventana = (Window) componente;
			ventana.doModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eliminarDetallesSeleccionados() throws Exception {
		try {
			final Set<Listitem> listado_seleccionados = listboxCalendar
					.getSelectedItems();
			if (!listado_seleccionados.isEmpty()) {
				Messagebox
						.show("¿Esta seguro que desea eliminar los detalles seleccionados de este horario...?",
								"Eliminar Registro", Messagebox.YES
										+ Messagebox.NO, Messagebox.QUESTION,
								new org.zkoss.zk.ui.event.EventListener() {
									public void onEvent(Event event)
											throws Exception {
										if ("onYes".equals(event.getName())) {
											List<Detalles_horarios> listado_detalles = new ArrayList<Detalles_horarios>();
											int no_eliminados = 0;
											for (Listitem listitem : listado_seleccionados) {
												Boolean hay_citas = (Boolean) listitem
														.getAttribute("HAY_CITAS");
												if (hay_citas != null) {
													if (!hay_citas) {
														Detalles_horarios detalles_horarios = (Detalles_horarios) listitem
																.getValue();
														if (detalles_horarios
																.getConsecutivo() != null) {
															listado_detalles
																	.add(detalles_horarios);
														}
													} else {
														no_eliminados++;
													}
												}
											}
											int cantidad = horarios_medicoService
													.eliminarSecuenciaHoarios(
															listado_detalles,
															detallesHorariosModel);
											MensajesUtil
													.mensajeInformacion(
															"Eliminacion de detalles seleccionados",
															"Se han eliminado "
																	+ cantidad
																	+ " detalles del horario del medico. "
																	+ (no_eliminados != 0 ? "No se han eliminado "
																			+ no_eliminados
																			+ " porque tienen citas asignadas"
																			: ""));
											renderizarHorario();
										}
									}
								});

			} else {
				MensajesUtil
						.mensajeAlerta("No hay items seleccionados",
								"Se deben seleccionar los items que se desean eliminar");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e,
					"Error al eliminar la secuencia de horarios", this);
		}
	}

	public void crearTrasladarHorarios(List<Detalles_horarios> listado_detalles)
			throws Exception {
		try {
			Map map = new HashMap();
			map.put("listado_detalles", listado_detalles);
			map.put("prestadores", tbxCodigo_prestador.getRegistroSeleccionado());
			map.put("mapa_roles", mapa_roles);
			map.put("horariosAction", this);
			map.put("horarios_seleccionado", horarios_seleccionado);
			Component componente = Executions.createComponents(
					"/pages/trasladar_horario.zul", null, map);

			final Window ventana = (Window) componente;
			ventana.doModal();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void initDatos() {
		Calendar fecha_sistema = Calendar.getInstance();
		int anio_sistema = anio != null ? anio : fecha_sistema.get(Calendar.YEAR);

		for (int i = (anio_sistema - 5); i < (anio_sistema + 5); i++) {
			Listitem listitem = new Listitem();
			listitem.setValue(i);
			listitem.setLabel("" + i);
			if (i == anio_sistema) {
				listitem.setSelected(true);
			}
			lbxAnios.appendChild(listitem);
		}

		listarMeses();

		listboxMeses.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
	}

	public void onSeleccionarAnio() throws Exception {
		if (horarios_seleccionado != null) {
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(horarios_seleccionado.getCodigo_medico());

			usuarios = getServiceLocator().getUsuariosService().consultar(
					usuarios);
			mostrarDatos(horarios_seleccionado, usuarios, true,false);
		} else {
			renderizarHorario();
		}
	}

	public void onSeleccionarMes() throws Exception {
		if (horarios_seleccionado != null) {
			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(horarios_seleccionado.getCodigo_medico());

			usuarios = getServiceLocator().getUsuariosService().consultar(
					usuarios);
			mostrarDatos(horarios_seleccionado, usuarios, true,false);
		} else {
			renderizarHorario();
		}
	}

	public void listarMeses() {
		listboxMeses.getItems().clear();
		Listitem listitem = new Listitem();
		listitem.setValue(0);
		listitem.setLabel("Enero");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(1);
		listitem.setLabel("Febrero");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(2);
		listitem.setLabel("Marzo");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(3);
		listitem.setLabel("Abril");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(4);
		listitem.setLabel("Mayo");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(5);
		listitem.setLabel("Junio");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(6);
		listitem.setLabel("Julio");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(7);
		listitem.setLabel("Agosto");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(8);
		listitem.setLabel("Septiembre");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(9);
		listitem.setLabel("Octubre");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(10);
		listitem.setLabel("Noviembre");
		listboxMeses.appendChild(listitem);
		listitem = new Listitem();
		listitem.setValue(11);
		listitem.setLabel("Dicimebre");
		listboxMeses.appendChild(listitem);
	}
	
	public Date incrementarDia(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) + 1);
		return calendar.getTime();
	}

	public void filtrarCalendario() {
		Integer anio_busqueda = lbxAnios.getSelectedItem().getValue();
		Integer mes_busqueda = listboxMeses.getSelectedIndex() + 1;
		Date fecha_inicial = getTime(mes_busqueda - 1, 1, anio_busqueda);
		Date fecha_final = getTime(mes_busqueda - 1, 31, anio_busqueda);
		if (fecha_inicial != null && fecha_final != null) {
			if (fecha_inicial.compareTo(fecha_final) <= 0) {
				//log.info("renderizarHorarioPrestador Con fecha de inicio y con fecha final");
				Map<Long, Detalles_horarios> mapa_sub = detallesHorariosModel
						.getSubmap(fecha_inicial, fecha_final);
				renderizarHorarioPrestador(mapa_sub);
			} else {
				MensajesUtil
						.mensajeAlerta("Datos de fecha !!!",
								"La fecha inicial no puede ser mayor que la fecha final");
			}
		}
	}

	public void recargarHorarioPrestador() throws Exception {
		//log.info("Ejecutando metodo @recargarHorarioPrestador()");
		Usuarios usuarios = new Usuarios();
		usuarios.setCodigo_empresa(codigo_empresa);
		usuarios.setCodigo_sucursal(codigo_sucursal);
		usuarios.setCodigo(horarios_seleccionado.getCodigo_medico());

		usuarios = getServiceLocator().getUsuariosService().consultar(usuarios);
		mostrarDatos(horarios_seleccionado, usuarios, true,true);
	}

	public void quitarFiltroCalendario() {
		renderizarHorarioPrestador(null);

	}

	// public Comparador getComparador() {
	// Comparador comparador = new Comparador() {
	// @Override
	// public boolean comparacion(Long fecha_long) {
	// Date hora_inicio = tmbxFecha_inicio.getValue() != null ? tmbxFecha_inicio
	// .getValue() : getTimeHoras(true);
	// Date hora_final = tmbxFecha_final.getValue() != null ? tmbxFecha_final
	// .getValue() : getTimeHoras(false);
	// Calendar calendar_aux = Calendar.getInstance();
	// calendar_aux.setTimeInMillis(fecha_long);
	//
	// if (validarDiaSemana(calendar_aux)) {
	// if (hora_inicio != null && hora_final != null) {
	// Calendar calendar_fecha = Calendar.getInstance();
	// calendar_fecha.setTime(hora_inicio);
	// calendar_fecha.set(Calendar.YEAR,
	// calendar_aux.get(Calendar.YEAR));
	// calendar_fecha.set(Calendar.MONTH,
	// calendar_aux.get(Calendar.MONTH));
	// calendar_fecha.set(Calendar.DAY_OF_MONTH,
	// calendar_aux.get(Calendar.DAY_OF_MONTH));
	// hora_inicio = calendar_fecha.getTime();
	//
	// calendar_fecha.setTime(hora_final);
	// calendar_fecha.set(Calendar.YEAR,
	// calendar_aux.get(Calendar.YEAR));
	// calendar_fecha.set(Calendar.MONTH,
	// calendar_aux.get(Calendar.MONTH));
	// calendar_fecha.set(Calendar.DAY_OF_MONTH,
	// calendar_aux.get(Calendar.DAY_OF_MONTH));
	// hora_final = calendar_fecha.getTime();
	//
	// if (fecha_long >= hora_inicio.getTime()
	// && fecha_long <= hora_final.getTime()) {
	// return true;
	// } else {
	// return false;
	// }
	// } else {
	// return true;
	// }
	// } else {
	// return false;
	// }
	// }
	//
	// };
	//
	// return comparador;
	// }

	public void verificarSeleccion() {
		List<Listitem> listado = listboxCalendar.getItems();
		if (!listado.isEmpty()) {
			chkSeleccionar_todos.setChecked(true);
			for (Listitem listitem : listado) {
				if (!listitem.isSelected()) {
					chkSeleccionar_todos.setChecked(false);
					break;
				}
			}
		} else {
			chkSeleccionar_todos.setChecked(false);
		}
	}

	public void seleccionarTodos(boolean seleccion) {
		List<Listitem> listado = listboxCalendar.getItems();
		if (!listado.isEmpty()) {
			for (Listitem listitem : listado) {
				listitem.setSelected(seleccion);
			}
		}
	}
	
	public void filtrarCentros(Boolean filtrar) throws Exception{
		filtrado_centro = filtrar;
		btTransladarHorario.setDisabled(!filtrar);
		if(horarios!=null && usurio_medico!=null){
			mostrarDatos(horarios,usurio_medico,true,false);
		}
	}
}
