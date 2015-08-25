/*
 * admisionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.Ficha_epidemiologiaService;
import healthmanager.modelo.service.Ficha_epidemiologia_historialService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IFichas_epidemiologicas;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ContenedorPaginasMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ModuloConsultaIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import com.softcomputo.composer.constantes.IParametrosSesion;

public class Ficha_epidemiologia_historialAction extends ZKWindow implements
		ModuloConsultaIMG {

	private static Logger log = Logger
			.getLogger(Auditoria_admisionAction.class);

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	private Ficha_epidemiologiaService ficha_epidemiologiaService;
	private Ficha_epidemiologia_historialService ficha_epidemiologia_historialService;

	// Componentes //
	@View
	private Textbox tbxValue;
	@View
	private Listbox listboxResultado;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;
	@View
	private Checkbox chkFiltro_atendidas;
	@View
	private Label lbTotal_historias;
	@View
	private ContenedorPaginasMacro tabboxContendor;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_medico;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;

	@View
	private Listbox lbxFichas_epidemiologicas;

	@View
	private Groupbox groupboxConsultar;

	@View
	private Borderlayout borderlayoutEditar;
	@View
	private Toolbarbutton toolbarbuttonInformacion_paciente;
	@View
	private Textbox tbxIngreso;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxTipo_identificacion;
	@View
	private Textbox tbxFecha_ingreso;
	@View
	private Textbox tbxAseguradora;
	@View
	private Textbox tbxNivel_educativo;
	@View
	private Textbox tbxPertencia_etnica;
	@View
	private Textbox tbxEdad;
	@View
	private Textbox tbxSexo;
	@View
	private Toolbarbutton btnFiltro_ingreso;

	private Boolean filtrar_centro = false;
	private Integer paginacion = 20;
	private Prestadores prestador;
	private Paciente paciente;


	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizarBandboxPrestador();
	}

	public void listarCombos() {
		cargarFichas(lbxFichas_epidemiologicas);
	}

	private void cargarFichas(Listbox listbox) {

		Listitem listitem;
		//String tipo = listbox.getName();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);

		
		List<Ficha_epidemiologia> fichas = ficha_epidemiologiaService.listar(parameters);

		for (Ficha_epidemiologia elemento : fichas) {
			listitem = new Listitem();
			if(!(elemento.getCodigo().equals("34") || elemento.getCodigo().equals("35"))){
				listitem.setValue(elemento.getCodigo());
				listitem.setLabel(elemento.getTitulo());
				listbox.appendChild(listitem);
			}
		}
	}
	
	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {

			String value = tbxValue.getValue().toUpperCase().trim();
			lbTotal_historias.setValue("");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (filtrar_centro) {
				parameters.put("codigo_centro",centro_atencion_session.getCodigo_centro());
			}

			parameters.put("paramTodo", "paramTodo");
			parameters.put("value", value);
			// parameters.put("atendida", chkFiltro_atendidas.isChecked());
			// parameters.put("estado", "1");

			if (dtbxFecha_inicial.getValue() != null
					&& dtbxFecha_final.getValue() != null) {
				if (dtbxFecha_inicial.getValue().compareTo(
						dtbxFecha_final.getValue()) > 0) {
					throw new ValidacionRunTimeException(
							"La fecha inicial en la busqueda no puede ser mayor a la fecha final");
				}
				parameters.put("fecha_inicial_p", new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				parameters.put("fecha_final_p", new Timestamp(dtbxFecha_final
						.getValue().getTime()));
			} else if (dtbxFecha_inicial.getValue() != null) {
				parameters.put("fecha_inicial_p", new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
			} else if (dtbxFecha_final.getValue() != null) {
				parameters.put("fecha_final_p", new Timestamp(dtbxFecha_final
						.getValue().getTime()));
			}

			String prest = null;
			if (prestador != null) {
				prest = prestador.getNro_identificacion();
				parameters.put("codigo_medico", prest);
			}

			if (lbxFichas_epidemiologicas.getSelectedItems().size() > 0) {
				List<String> listado_vias = new ArrayList<String>();
				for (Listitem listitem : lbxFichas_epidemiologicas.getSelectedItems()) {
					listado_vias.add((String) listitem.getValue());
				}
				btnFiltro_ingreso.setImage("/images/filtro1.png");
				//log.info("listado_vias"+listado_vias);
				parameters.put("vias_ingreso", listado_vias);
			} else {
				btnFiltro_ingreso.setImage("/images/filtro.png");
			}

			log.debug("parametros--->"+parameters);
			
			List<Ficha_epidemiologia_historial> lista_datos = ficha_epidemiologia_historialService.listar(parameters);
			listboxResultado.getItems().clear();

			for (Ficha_epidemiologia_historial ficha : lista_datos) {
				listboxResultado.appendChild(crearFilas(ficha, this));
			}

			lbTotal_historias.setValue(lista_datos.size() + "");
			listboxResultado.setVisible(true);

			if (paginacion > 0) {
				listboxResultado.setMold("paging");
				listboxResultado.setPageSize(paginacion);
			}

			listboxResultado.applyProperties();
			listboxResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		final Listitem fila = new Listitem();

		final Ficha_epidemiologia_historial ficha = (Ficha_epidemiologia_historial) objeto;
		Hbox hbox = new Hbox();

		Prestadores p = new Prestadores();
		p.setCodigo_empresa(ficha.getCodigo_empresa());
		p.setCodigo_sucursal(ficha.getCodigo_sucursal());
		p.setNro_identificacion(ficha.getCodigo_medico());
		p = getServiceLocator().getServicio(PrestadoresService.class).consultar(p);
		//log.info("p"+p);
		
		String prest = "";
		if (p != null) {
			prest = p.getNombres() + " " + p.getApellidos();
		}

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(ficha.getCodigo_empresa());
		paciente.setCodigo_sucursal(ficha.getCodigo_sucursal());
		paciente.setNro_identificacion(ficha.getIdentificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		//log.info("paciente"+paciente);
		
		
		String nombre_paciente = "";
		if (paciente != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(paciente.getNombre1());
			sb.append(!paciente.getNombre2().isEmpty() ? " "
					+ paciente.getNombre2() : "");
			sb.append(!paciente.getApellido1().isEmpty() ? " "
					+ paciente.getApellido1() : "");
			sb.append(!paciente.getApellido2().isEmpty() ? " "
					+ paciente.getApellido2() : "");
			nombre_paciente = sb.toString().toUpperCase();
		}

		fila.appendChild(new Listcell());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		fila.appendChild(new Listcell(dateFormat.format(ficha
				.getFecha_creacion()) + ""));
		fila.appendChild(new Listcell(ficha.getCodigo_diagnostico() + ""));
		fila.appendChild(new Listcell(paciente.getNro_identificacion() + ""));
		fila.appendChild(new Listcell(nombre_paciente));

		Elemento elemento = new Elemento();
		elemento.setCodigo(ficha.getVia_ingreso());
		elemento.setTipo("via_ingreso");
		elemento = getServiceLocator().getElementoService().consultar(elemento);

		//admision.setElemento_via_ingreso(elemento);

		fila.appendChild(new Listcell(prest));
		fila.appendChild(new Listcell(Utilidades.getNombreElemento(
				ficha.getVia_ingreso(), "via_ingreso", getServiceLocator())
				+ ""));

		Listcell listcell = new Listcell();
		listcell.appendChild(hbox);

		fila.appendChild(listcell);

		fila.setValue(ficha);

		fila.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				fila.setSelected(true);
				
				onSeleccionarFicha(fila);
			}
		});

		return fila;
	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_medico.inicializar(tbxNomPrestador, btnLimpiarPrestador);
		tbxCodigo_medico
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

					@Override
					public void renderListitem(Listitem listitem,
							Prestadores registro) {

						// Extraemos valores
						String nro_identificacion = (String) registro
								.getNro_identificacion();
						String nombres = (String) registro.getNombres();
						String apellidos = (String) registro.getApellidos();

						// Mostramos valores en vista
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(nro_identificacion));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(nombres));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(apellidos));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Prestadores> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("codigo_empresa",
								sucursal.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());

						if (filtrar_centro) {
							parametros.put(
									"codigo_centro_dh",
									centro_atencion_session != null ? centro_atencion_session
											.getCodigo_centro() : "110");
						}
						parametros.put("limite_paginado", "limit 25 offset 0");

						return getServiceLocator().getServicio(
								PrestadoresService.class).listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Prestadores registro) {

						String nro_identificacion = (String) registro
								.getNro_identificacion();
						String nombre = (String) registro.getNombres() + " "
								+ (String) registro.getApellidos();

						bandbox.setValue(nro_identificacion);
						textboxInformacion.setValue(nombre);
						prestador = registro;
						actualizarDatosHistoria();
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						prestador = null;
						actualizarDatosHistoria();
					}

				});
	}

	private void actualizarDatosHistoria() {
		try {
			buscarDatos();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void accionForm2(final String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			
			groupboxConsultar.setVisible(true);
			borderlayoutEditar.setVisible(false);
		} else {
			groupboxConsultar.setVisible(false);
			borderlayoutEditar.setVisible(true);
		}
	}

	public void accionForm(String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			
			groupboxConsultar.setVisible(true);
			borderlayoutEditar.setVisible(false);
		} else {
			groupboxConsultar.setVisible(false);
			borderlayoutEditar.setVisible(true);
		}
	}

	public void onSeleccionarFicha(Listitem listitem) throws Exception {
		try {
			//log.info("Item de la ficha: " + listitem.getValue());
			Ficha_epidemiologia_historial ficha_epidemiologia_historial = (Ficha_epidemiologia_historial) listitem.getValue();
			ficha_seleccionada = ficha_epidemiologia_historial;
			accionForm("registrar");
			mostrarDatos();
			inicializarContenedorPaginas();
			   // cargarHistorialHistorias();
			borderlayoutEditar.invalidate();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil
					.mensajeError(e, "Ficha no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil.mensajeError(e, "Ficha no valida Exception", this);
		}
	}
 
	public void mostrarDatos() throws Exception {
		if (ficha_seleccionada != null) {
			tbxIngreso.setValue("");
			tbxNro_identificacion.setValue(ficha_seleccionada.getIdentificacion());

			paciente = new Paciente();
			paciente.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			paciente.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			paciente.setNro_identificacion(ficha_seleccionada.getIdentificacion());
			paciente = getServiceLocator().getPacienteService().consultar(paciente);
			//log.info("paciente"+paciente);
			
			toolbarbuttonInformacion_paciente.setLabel(paciente.getNombreCompleto());
			
			// Agregado por jhonny
			Map<String, Integer> mapa_edades = Util
					.getEdadYYYYMMDD(paciente.getFecha_nacimiento());

			Integer anios = mapa_edades.get("anios");
			Integer meses = mapa_edades.get("meses");
			Integer dias = mapa_edades.get("dias");

			if (anios.intValue() == 0 && meses.intValue() == 0) {
				tbxEdad.setValue(dias + (dias == 1 ? " día" : " días"));
			} else if (anios.intValue() == 0) {
				tbxEdad.setValue(meses + (meses == 1 ? " mes (" : " meses (")
						+ (dias + (dias == 1 ? " día" : " días")) + ")");
			} else {
				int current_meses = meses.intValue() - (anios.intValue() * 12);
				tbxEdad.setValue(anios
						+ (anios == 1 ? " año" : " años")
						+ (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ? " mes "
								: " meses"))
								: ""));
			}

			if (paciente.getSexo() != null) {
				if (paciente.getSexo().equals("M")) {
					tbxSexo.setValue("MASCULINO");
				} else if (paciente.getSexo()
						.equals("F")) {
					tbxSexo.setValue("FEMENINO");
				}
			}

			// Fin Jhonny
			tbxTipo_identificacion.setValue(Utilidades.getNombreElemento(
					paciente.getTipo_identificacion(), "tipo_id", this));
			tbxFecha_ingreso.setValue(new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss").format(ficha_seleccionada.getFecha_creacion()));
			
			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			administradora.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			administradora.setCodigo(paciente.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService().consultar(administradora);
			
			tbxAseguradora
					.setValue(paciente.getCodigo_administradora() != null ? 
							paciente.getCodigo_administradora()
							+ " "
							+ administradora.getNombre(): paciente
									.getCodigo_administradora());
						
			Nivel_educativo educacion = new Nivel_educativo();
			educacion.setId(paciente.getCodigo_educativo());
			educacion = getServiceLocator().getNivel_educativoService()
					.consultar(educacion);

			Pertenencia_etnica etnica = new Pertenencia_etnica();
			etnica.setId(paciente.getPertenencia_etnica());
			etnica = getServiceLocator().getPertenencia_etnicaService()
					.consultar(etnica);

			tbxNivel_educativo.setValue(educacion != null ? educacion
					.getNombre() : "");
			tbxPertencia_etnica.setValue("("
					+ paciente.getPertenencia_etnica() + ") "
					+ (etnica != null ? etnica.getNombre() : ""));
		} else {
			throw new Exception("No hay una admision seleccionada");
		}
	}

	public void inicializarContenedorPaginas() throws Exception {
		tabboxContendor.cerrarTabs();
		String ficha_epidemiologia = ficha_seleccionada.getCodigo_ficha();
		//log.info("ejecutando metodo @inicializarContenedorPaginas() ===> FICHA_EPIDEMIOLOGIA="
				//+ ficha_epidemiologia);

		// CAMBIAR AQUI
		if (ficha_seleccionada != null) {
			
			
			tabboxContendor.cerrarTabs();

			Map<String, Object> parametros = new HashMap<String, Object>();
			
			Admision admisionAux = new Admision();
			admisionAux.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			admisionAux.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			admisionAux.setNro_identificacion(ficha_seleccionada.getIdentificacion());
			
			admisionAux.setCodigo_administradora(paciente.getCodigo_administradora());
			admisionAux.setVia_ingreso(ficha_seleccionada.getVia_ingreso());
			admisionAux.setEstado("1");
			admisionAux.setCodigo_medico(ficha_seleccionada.getCodigo_medico());
			admisionAux.setNro_autorizacion("");
			admisionAux.setTipo_atencion("");
			admisionAux.setCodigo_especialidad("");
			admisionAux.setIngreso_programa("N");
			admisionAux.setPrimera_vez("N");
			admisionAux.setCondicion_usuaria("");
			admisionAux.setCausa_externa("13");
			admisionAux.setTipo_diagnostico("1");
			admisionAux.setDiagnostico_ingreso(ficha_seleccionada.getCodigo_diagnostico());
			admisionAux.setTipo_discapacidad("");
			admisionAux.setGrado_discapacidad("");
			admisionAux.setFecha_ingreso(ficha_seleccionada.getFecha_creacion());
			admisionAux.setCreacion_date(ficha_seleccionada.getFecha_creacion());
			admisionAux.setUltimo_update(ficha_seleccionada.getFecha_creacion());
			admisionAux.setCreacion_user(usuarios.getCodigo()
					.toString());
			admisionAux.setUltimo_user(usuarios.getCodigo()
					.toString());
			admisionAux.setUrgencias("N");
			admisionAux.setHospitalizacion("N");
			admisionAux.setRecien_nacido("N");
			admisionAux.setAtendida(false);
			admisionAux.setCodigo_cita("");
			admisionAux.setCama("");
			admisionAux.setPaciente(paciente);

			admisionAux.setAplica_triage(false);
			admisionAux.setRealizo_triage(false);
			admisionAux.setAplica_tuberculosis(false);
			admisionAux.setAplica_lepra(false);
			admisionAux.setTipo_consulta("1");
			admisionAux.setTipo_adicional_via_ingreso("");
			admisionAux.setFecha_atencion(ficha_seleccionada.getFecha_creacion());
			admisionAux.setAdmision_parto("N");
			admisionAux.setCodigo_centro(centro_atencion_session
					.getCodigo_centro());

			//log.info("admisionAux" + admisionAux);
			
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,	admisionAux);
			parametros.put("ficha_seleccionada", ficha_seleccionada);
			parametros.put(IVias_ingreso.VIA_INGRESO, ficha_seleccionada.getVia_ingreso());
			
			tabboxContendor.getTabs().setStyle("background-image:url(../images/bar01.gif)");

			if (ficha_epidemiologia.equals(IFichas_epidemiologicas.ACCIDENTE_OFIDICO)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_ACCIDENTE_OFIDICO);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_ACCIDENTE_OFIDICO);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.BAJO_PESO_AL_NACER)) {
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_BAJO_PESO_AL_NACER);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_BAJO_PESO_AL_NACER);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.ENFERMEDAD_CHAGAS)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_ENFERMEDAD_CHAGAS);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_ENFERMEDAD_CHAGAS);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.DENGUE)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_DENGUE);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_DENGUE);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.ESAVI)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_ESAVI);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_ESAVI);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.FIEBRE_AMARILLA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_FIEBRE_AMARILLA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_FIEBRE_AMARILLA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.HEPATITIS_B)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_HEPATITIS_B);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_HEPATITIS_B);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.ENFERMEDAD_POR_ALIMENTOS)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_ENFERMEDAD_POR_ALIMENTOS);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_ENFERMEDAD_POR_ALIMENTOS);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.INTOXICACION)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_INTOXICACION);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_INTOXICACION);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.SALUD_PUBLICA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_SALUD_PUBLICA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_SALUD_PUBLICA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.LEISHMANIASIS_CUTANEA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_LEISHMANIASIS_CUTANEA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_LEISHMANIASIS_CUTANEA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.LEPRA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_LEPRA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_LEPRA);
				
			}else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.LEPTOSPIROSIS)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_LEPTOSPIROSIS);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_LEPTOSPIROSIS);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.MALARIA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_MALARIA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_MALARIA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.MALARIA_COMPLICADA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_MALARIA_COMPLICADA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_MALARIA_COMPLICADA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.MENINGITIS)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_MENINGITIS);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_MENINGITIS);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.PARAISIS_FLACIDA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_PARAISIS_FLACIDA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_PARAISIS_FLACIDA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.TOSFERINA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_TOSFERINA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_TOSFERINA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.LEUCEMIA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_LEUCEMIA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LEUCEMIA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.DIARREICA_AGUDA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_DIARREICA_AGUDA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_DIARREICA_AGUDA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.AGRESIONES_POR_ANIMALES)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_AGRESIONES_POR_ANIMALES);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_AGRESIONES_POR_ANIMALES);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.VIGILANCIA_CENTINELA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_VIGILANCIA_CENTINELA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_VIGILANCIA_CENTINELA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.RABIA_HUMANA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_RABIA_HUMANA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_RABIA_HUMANA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.RABIA_ANIMAL)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_RABIA_ANIMAL);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_RABIA_ANIMAL);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.TUBERCULOSIS_PULMONAR)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_TUBERCULOSIS_PULMONAR);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_TUBERCULOSIS_PULMONAR);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.MORTALIDAD_MATERNA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_MORTALIDAD_MATERNA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_MORTALIDAD_MATERNA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.LESIONES_POR_POLVORA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_LESIONES_POR_POLVORA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_LESIONES_POR_POLVORA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.ANOMALIAS_CONGENITAS)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_ANOMALIAS_CONGENITAS);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_ANOMALIAS_CONGENITAS);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.EXPOSICION_FLUOR)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_EXPOSICION_FLUOR);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_EXPOSICION_FLUOR);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.MORBILIDAD_MATERNA_EXTREMA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_MORBILIDAD_MATERNA_EXTREMA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_MORBILIDAD_MATERNA_EXTREMA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.MORTALIDAD_DESNUTRICION)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_MORTALIDAD_DESNUTRICION);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_MORTALIDAD_DESNUTRICION);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.TUBERCULOSIS_FARMACORRESISTENTE)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_TUBERCULOSIS_FARMACORRESISTENTE);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_TUBERCULOSIS_FARMACORRESISTENTE);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.VIOLENCIA_INTRAFAMILIAR)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_VIOLENCIA_INTRAFAMILIAR);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_VIOLENCIA_INTRAFAMILIAR);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.RUBEOLA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_RUBEOLA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_RUBEOLA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.SARAMPION)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_SARAMPION);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_SARAMPION);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.SIFILIS_CONGENITA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_SIFILIS_CONGENITA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_SIFILIS_CONGENITA);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.SIFILIS_GESTIONAL)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_SIFILIS_GESTIONAL);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_SIFILIS_GESTIONAL);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.TETANOS_ACCIDENTAL)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_TETANOS_ACCIDENTAL);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_TETANOS_ACCIDENTAL);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.VIH)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_VIH);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_VIH);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.NOTIFICACION_SALUD_MENTAL)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_NOTIFICACION_SALUD_MENTAL);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_NOTIFICACION_SALUD_MENTAL);
				
			} else if (ficha_epidemiologia.equals(IFichas_epidemiologicas.NOTIFICACION_CONSUMO_SPA)) {
				
				tabboxContendor.setUrlPaginaInicio(IFichas_epidemiologicas.PAGINA_NOTIFICACION_CONSUMO_SPA);
				tabboxContendor.setLabelTabInicio(IFichas_epidemiologicas.LABEL_NOTIFICACION_CONSUMO_SPA);
				
			} 
			
			
			tabboxContendor.inicializarInicio(false, parametros);

			
		} else {
			throw new Exception("No hay una ficha seleccionada");
		}
	}


//	@Override
//	public void cargarRegClinicoHigiene(Odontologia odontologia,
//			boolean desde_historia, OpcionesFormulario opcion_formulario) {
//		
//		
//	}

	@Override
	public void cargarEpicrisis() {
		
		
	}

@Override
public void cargarEvolucionOdonotologia(Odontologia odontologia,
		OpcionesFormulario opcion_formulario,
		List<Map<String, Object>> lista_codigos_fac, boolean primera_vez) {
	
	
}

@Override
public OpcionesFormulario opcion_formulario(List<String> lista_codigos_fac,
		boolean primera_vez) {
	
	return null;
}


	
	}

