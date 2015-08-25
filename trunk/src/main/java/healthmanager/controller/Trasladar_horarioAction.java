package healthmanager.controller;

import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Horarios_medico;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Horarios_medicoService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listgroup;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.BandboxRegistrosIMG.ISeleccionarItem;
import com.framework.res.ZKSimpleCalendarEvent;
import com.framework.util.MensajesUtil;

public class Trasladar_horarioAction extends ZKWindow {

	private HorariosAction horariosAction;
	private Horarios_medico horarios_seleccionado;

	private Horarios_medicoService horarios_medicoService;

	private Map<String, List<Detalles_horarios>> mapa_detalles = new HashMap<String, List<Detalles_horarios>>();

	private Map<String, Elemento> mapa_roles;

	private SimpleDateFormat formatoFecha = new SimpleDateFormat(
			"EEEEE dd MMMMM yyyy", IConstantes.locale);

	private List<Detalles_horarios> listado_detalles;
	private Prestadores prestadores;

	@View
	private Textbox tbxMedico_actual;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;
	@View
	private Listbox lbxServicios;
	@View
	private Listbox listboxDetalles;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_centro;
	@View
	private Toolbarbutton btnLimpiarCentro;
	
	
	private Map<String, Consultorio> mapa_consultorios = new HashMap<String, Consultorio>();

	@Override
	public void init() {
		parametrizarBandbox();
		inicializar();
	}

	private void inicializar() {
		if(prestadores!=null){
			tbxMedico_actual.setValue(prestadores.getNro_identificacion() + " - "
					+ prestadores.getNombres() + " " + prestadores.getApellidos());
			mapa_detalles.clear();
			lbxServicios.getChildren().clear();
			lbxServicios.appendItem("-- Todos --", "");
			for (Detalles_horarios detalles_horarios : listado_detalles) {
				if (mapa_detalles.containsKey(detalles_horarios.getCodigo_rol())) {
					List<Detalles_horarios> lista_aux = mapa_detalles
							.get(detalles_horarios.getCodigo_rol());
					lista_aux.add(detalles_horarios);
				} else {
					List<Detalles_horarios> lista_aux = new ArrayList<Detalles_horarios>();
					lista_aux.add(detalles_horarios);
					mapa_detalles.put(detalles_horarios.getCodigo_rol(), lista_aux);
					Elemento elemento = new Elemento();
					elemento.setCodigo(detalles_horarios.getCodigo_rol());
					elemento.setTipo("rol");
					elemento = getServiceLocator().getElementoService().consultar(
							elemento);
					lbxServicios.appendItem((elemento != null ? elemento.getDescripcion() : ""),
							elemento.getCodigo());
				}
			}
			lbxServicios.setSelectedIndex(0);
			seleccionarServicio();
		}
	}

	public void seleccionarServicio() {
		listboxDetalles.getItems().clear();
		String codigo_servicio = lbxServicios.getSelectedItem().getValue().toString();
		tbxCodigo_prestador.setValue(null);
		if (codigo_servicio.isEmpty()) {
			String fecha_inicial_aux = "";
			for (Detalles_horarios valor : listado_detalles) {

				String fecha_inicial = formatoFecha.format(
						valor.getFecha_inicial()).toUpperCase();

				if (fecha_inicial_aux.isEmpty()
						|| !fecha_inicial.equals(fecha_inicial_aux)) {
					// //log.info("fecha_inicial_aux ==> "
					// + fecha_inicial_aux
					// + " || fecha_inicial ==> " + fecha_inicial);
					Listgroup listgroup = new Listgroup();
					listgroup.setVisible(true);
					listgroup.setLabel(fecha_inicial);
					listboxDetalles.appendChild(listgroup);
				}

				fecha_inicial_aux = fecha_inicial;

				Listitem listitem = new Listitem();
				listitem.setValue(valor);

				Listcell listcell = new Listcell(
						ZKSimpleCalendarEvent.simpleDateFormat.format(valor
								.getFecha_inicial()));
				listitem.appendChild(listcell);

				listcell = (new Listcell(
						ZKSimpleCalendarEvent.simpleDateFormat.format(valor
								.getFecha_final())));
				listitem.appendChild(listcell);

				Consultorio consultorio = null;

				if (mapa_consultorios
						.containsKey(valor.getCodigo_consultorio())) {
					consultorio = mapa_consultorios.get(valor
							.getCodigo_consultorio());
				} else {
					consultorio = new Consultorio();
					consultorio.setCodigo_empresa(codigo_empresa);
					consultorio.setCodigo_sucursal(codigo_sucursal);
					consultorio.setCodigo_consultorio(valor
							.getCodigo_consultorio());
					consultorio = getServiceLocator().getConsultorioService()
							.consultar(consultorio);
					mapa_consultorios.put(valor.getCodigo_consultorio(),
							consultorio);
				}

				Textbox textbox = new Textbox(
						consultorio != null ? consultorio.getNombre() : "");
				textbox.setHflex("1");
				textbox.setReadonly(true);

				listcell = new Listcell();
				listcell.appendChild(textbox);
				listitem.appendChild(listcell);

				if (mapa_roles.containsKey(valor.getCodigo_rol())) {
					Elemento elemento = mapa_roles.get(valor.getCodigo_rol());
					textbox = new Textbox("("
							+ valor.getCodigo_rol()
							+ ")"
							+ (elemento != null ? elemento.getDescripcion()
									: ""));
				} else {
					Elemento elemento = new Elemento();
					elemento.setCodigo(valor.getCodigo_rol());
					elemento.setTipo("rol");
					elemento = getServiceLocator().getElementoService()
							.consultar(elemento);
					textbox = new Textbox("("
							+ valor.getCodigo_rol()
							+ ")"
							+ (elemento != null ? elemento.getDescripcion()
									: ""));
					mapa_roles.put(valor.getCodigo_rol(), elemento);
				}

				textbox.setHflex("1");
				textbox.setReadonly(true);

				listcell = new Listcell();
				listcell.appendChild(textbox);
				listitem.appendChild(listcell);

				listboxDetalles.appendChild(listitem);
			}
		} else {
			String fecha_inicial_aux = "";
			for (Detalles_horarios valor : mapa_detalles.get(codigo_servicio)) {

				String fecha_inicial = formatoFecha.format(
						valor.getFecha_inicial()).toUpperCase();

				if (fecha_inicial_aux.isEmpty()
						|| !fecha_inicial.equals(fecha_inicial_aux)) {
					// //log.info("fecha_inicial_aux ==> "
					// + fecha_inicial_aux
					// + " || fecha_inicial ==> " + fecha_inicial);
					Listgroup listgroup = new Listgroup();
					listgroup.setVisible(true);
					listgroup.setLabel(fecha_inicial);
					listboxDetalles.appendChild(listgroup);
				}

				fecha_inicial_aux = fecha_inicial;

				Listitem listitem = new Listitem();
				listitem.setValue(valor);

				Listcell listcell = new Listcell(
						ZKSimpleCalendarEvent.simpleDateFormat.format(valor
								.getFecha_inicial()));
				listitem.appendChild(listcell);

				listcell = (new Listcell(
						ZKSimpleCalendarEvent.simpleDateFormat.format(valor
								.getFecha_final())));
				listitem.appendChild(listcell);

				Consultorio consultorio = null;

				if (mapa_consultorios
						.containsKey(valor.getCodigo_consultorio())) {
					consultorio = mapa_consultorios.get(valor
							.getCodigo_consultorio());
				} else {
					consultorio = new Consultorio();
					consultorio.setCodigo_empresa(codigo_empresa);
					consultorio.setCodigo_sucursal(codigo_sucursal);
					consultorio.setCodigo_consultorio(valor
							.getCodigo_consultorio());
					consultorio = getServiceLocator().getConsultorioService()
							.consultar(consultorio);
					mapa_consultorios.put(valor.getCodigo_consultorio(),
							consultorio);
				}

				Textbox textbox = new Textbox(
						consultorio != null ? consultorio.getNombre() : "");
				textbox.setHflex("1");
				textbox.setReadonly(true);

				listcell = new Listcell();
				listcell.appendChild(textbox);
				listitem.appendChild(listcell);

				if (mapa_roles.containsKey(valor.getCodigo_rol())) {
					Elemento elemento = mapa_roles.get(valor.getCodigo_rol());
					textbox = new Textbox("("
							+ valor.getCodigo_rol()
							+ ")"
							+ (elemento != null ? elemento.getDescripcion()
									: ""));
				} else {
					Elemento elemento = new Elemento();
					elemento.setCodigo(valor.getCodigo_rol());
					elemento.setTipo("rol");
					elemento = getServiceLocator().getElementoService()
							.consultar(elemento);
					textbox = new Textbox("("
							+ valor.getCodigo_rol()
							+ ")"
							+ (elemento != null ? elemento.getDescripcion()
									: ""));
					mapa_roles.put(valor.getCodigo_rol(), elemento);
				}

				textbox.setHflex("1");
				textbox.setReadonly(true);

				listcell = new Listcell();
				listcell.appendChild(textbox);
				listitem.appendChild(listcell);

				listboxDetalles.appendChild(listitem);

			}
		}

	}

	@Override
	public void params(Map<String, Object> map) {
		listado_detalles = (List<Detalles_horarios>) map
				.get("listado_detalles");
		prestadores = (Prestadores) map.get("prestadores");
		mapa_roles = (Map<String, Elemento>) map.get("mapa_roles");
		horariosAction = (HorariosAction) map.get("horariosAction");
		horarios_seleccionado = (Horarios_medico) map
				.get("horarios_seleccionado");
	}

	private void parametrizarBandbox() {
		inicializarCentro();
		parametrizarBandboxPrestador();
		parametrizarBandboxCentro();
	}
	
	private void parametrizarBandboxCentro() {
		tbxCodigo_centro.inicializar(null, btnLimpiarCentro);
		tbxCodigo_centro
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {
						listitem.appendChild(new Listcell(registro
								.getCodigo_centro()));
						listitem.appendChild(new Listcell(registro
								.getNombre_centro()));
					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						return getCentro_atencions(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {
						bandbox.setValue(registro.getCodigo_centro() + "-"
								+ registro.getNombre_centro());
						// textboxInformacion.setValue(registro.getNombre_centro());
						((BandboxRegistrosMacro) bandbox).setObject(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						((BandboxRegistrosMacro) bandbox).setObject(null);
						tbxCodigo_prestador.setObject(null);
					}
				});
	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_prestador.inicializar(new Textbox(), btnLimpiarPrestador);
		tbxCodigo_prestador
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

					@Override
					public void renderListitem(Listitem listitem,
							Prestadores registro) {

						Especialidad especialidad = new Especialidad();
						especialidad.setCodigo(registro.getCodigo_especialidad());
						especialidad = getServiceLocator()
								.getEspecialidadService().consultar(especialidad);

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
								.getTipo_prestador().equals("01") ? "MÉDICO"
								: "ENFERMERA"));
						listitem.appendChild(listcell);

						listitem.appendChild(listcell);
					}

					@Override
					public List<Prestadores> listarRegistros(String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", "");
						parametros.put("value", "%"+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("codigo_empresa",sucursal.getCodigo_empresa());
						parametros.put("codigo_sucursal",sucursal.getCodigo_sucursal());
						
						Centro_atencion centro = (Centro_atencion)tbxCodigo_centro.getRegistroSeleccionado();
						parametros.put("codigo_centro",	((centro==null||centro.getCodigo_centro().isEmpty())?centro_atencion_session.getCodigo_centro():centro.getCodigo_centro()));
						if(lbxServicios.getSelectedItem().getValue()!=null && lbxServicios.getSelectedIndex()>0){
							parametros.put("rol", lbxServicios.getSelectedItem().getValue().toString());
						}	
						//log.info(">>>>>>>"+parametros);
						parametros.put("limite_paginado", "limit 25 offset 0");
						List<Prestadores> lst = new ArrayList<Prestadores>();
						for (Prestadores prestador : getServiceLocator().getPrestadoresService().listarPorCentro(parametros)) {
							if(!prestador.getNro_identificacion().equalsIgnoreCase(prestadores.getNro_identificacion())){
								lst.add(prestador);
							}
						}
						return lst; 
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Prestadores registro) {
						bandbox.setValue(registro.getNro_identificacion()
								+ " - " + registro.getNombres() + " "
								+ registro.getApellidos());
						textboxInformacion.setValue(registro.getNombres() + " "
								+ registro.getApellidos());
						Especialidad especialidad = new Especialidad();
						especialidad.setCodigo(registro
								.getCodigo_especialidad());
						especialidad = getServiceLocator()
								.getEspecialidadService().consultar(
										especialidad);

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}

				});

		tbxCodigo_prestador.setSeleccionarItem(new ISeleccionarItem() {

			@Override
			public void accion(Object registro) {
				btnLimpiarPrestador.setVisible(false);
			}

		});
	}

	public void trasladarHorariosOtroMedico() {
		try {
			if (tbxCodigo_prestador.getRegistroSeleccionado() != null) {
				List<Detalles_horarios> listado_detalles_aux = null;
				String codigo_servicio = lbxServicios.getSelectedItem()
						.getValue().toString();
				if (codigo_servicio.isEmpty()) {
					listado_detalles_aux = listado_detalles;
				} else {
					listado_detalles_aux = mapa_detalles.get(codigo_servicio);
				}

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("prestadores", prestadores);
				parametros.put("prestadores_traslado", tbxCodigo_prestador.getRegistroSeleccionado());
				parametros.put("listado_detalles_aux", listado_detalles_aux);
				parametros.put("creacion_user", usuarios.getCodigo());
				
				horarios_medicoService.guardarTrasladoCitas(parametros);

				MensajesUtil.mensajeInformacion("Informacion",
						"Traslado hecho satisfactoriamente",
						new EventListener<Event>() {

							@Override
							public void onEvent(Event arg0) throws Exception {
								if (horarios_seleccionado != null) {
									Usuarios usuariosMedico = new Usuarios();
									usuariosMedico.setCodigo_empresa(sucursal.getCodigo_empresa());
									usuariosMedico.setCodigo_sucursal(sucursal.getCodigo_sucursal());
									usuariosMedico.setCodigo(horarios_seleccionado.getCodigo_medico());
									final Usuarios usuariosMedicoFinal = getServiceLocator().getUsuariosService().consultar(usuariosMedico);
									horariosAction.mostrarDatos(horarios_seleccionado,usuariosMedicoFinal, true,false);
									Trasladar_horarioAction.this.detach();
								}
							}
						});

			} else {
				MensajesUtil
						.mensajeAlerta(
								"Debe seleccionar el medico de traslado",
								"Para realizar el traslado de citas debe seleccionar el medico de traslado");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	public List<Centro_atencion> getCentro_atencions(
			Map<String, Object> parametros) {

		return getServiceLocator().getCentro_atencionService().listar(
				parametros);
	}

	public void onSeleccionarRolMedico() {

	}
	
	private void inicializarCentro() {
		List<Centro_atencion> centro_atencions = getCentro_atencions(new HashMap<String, Object>());
		if (centro_atencions.size() == 1) {
			Centro_atencion centro_atencion = centro_atencions.get(0);
			tbxCodigo_centro.seleccionarRegistro(
					centro_atencion,centro_atencion.getCodigo_centro() + "-" + centro_atencion.getNombre_centro(), "");
		}else{
			Centro_atencion centro_atencion = centro_atencion_session;
			tbxCodigo_centro.seleccionarRegistro(
					centro_atencion,centro_atencion.getCodigo_centro() + "-" + centro_atencion.getNombre_centro(), "");
		}
	}

}
