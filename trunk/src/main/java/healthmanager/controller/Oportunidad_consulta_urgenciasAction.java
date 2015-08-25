/*
 * admisionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.His_triageService;
import healthmanager.modelo.service.Hisc_urgenciaService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.XulElement;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.MensajesUtil;
import com.softcomputo.composer.constantes.IParametrosSesion;

public class Oportunidad_consulta_urgenciasAction extends ZKWindow  {

	private Admision admision_seleccionada;

	private CitasService citasService;
	
	// Componentes //

	@View
	private Textbox tbxValue;
	@View
	private Listbox listboxResultado;
	@View
	private Datebox dtbxFecha_final;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Label lbTotal_oportunidad;
	
	private long oportunidad_consulta; 

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_medico;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_centro;
	@View
	private Textbox tbxNomCentro;
	@View
	private Toolbarbutton btnLimpiarCentro;

	@View
	private Listbox lbxVias_ingreso;

	@View
	private Groupbox groupboxConsultar;

	@View
	private Toolbarbutton btnFiltro_ingreso;

	private Boolean filtrar_centro = false;
	
	private Prestadores prestador;
	private Centro_atencion centro_atencion;

	@View
	private Div divPopups_traige;
	
	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();


	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizarBandboxPrestador();
		parametrizarBandboxCentro();
		parametrizarResultadoPaginado();
	}

	public void listarCombos() {
		cargarVias(lbxVias_ingreso);
	}

	private void cargarVias(Listbox listbox) {

		Listitem listitem;
		String tipo = listbox.getName();
		
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);
		parametros.put("orden_descripcion", "orden_descripcion");

		List<Elemento> elementos =  getServiceLocator().getElementoService().listar(
				parametros);
		
		for (Elemento elemento : elementos) {
			if(!elemento.getCodigo().equals("23") && !elemento.getCodigo().equals("24")){
				listitem = new Listitem();
				listitem.setValue(elemento.getCodigo());
				listitem.setLabel(elemento.getDescripcion());
				listbox.appendChild(listitem);
			}
		}
	}
	
	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			divPopups_traige.getChildren().clear();
			String value = tbxValue.getValue().toUpperCase().trim();
			lbTotal_oportunidad.setValue("");
			oportunidad_consulta = 0;
			long oportunidad = 0;

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (filtrar_centro) {
				parameters.put("codigo_centro",centro_atencion_session.getCodigo_centro());
			}


			parameters.put("fecha_apertura", "fecha_apertura");
			
			parameters.put("atendida", true);
			//parameters.put("estado", "2");
			parameters.put("paramTodo", "paramTodo");
			parameters.put("value", value);
	
			if (dtbxFecha_inicial.getValue() != null && dtbxFecha_final.getValue() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String fecha1 = dateFormat.format(dtbxFecha_inicial.getValue());
					String fecha2 = dateFormat.format(dtbxFecha_final.getValue());
					
					if (dateFormat.parse(fecha1).getTime() > dateFormat.parse(fecha2).getTime()) {
						throw new ValidacionRunTimeException("La fecha inicial en la busqueda no puede ser mayor a la fecha final");
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
				parameters.put("codigo_medico_mod", prest);
			}

			String centro = null;
			if (centro_atencion != null) {
				centro = centro_atencion.getCodigo_centro();
				parameters.put("codigo_centro", centro);
			}
			
			//Via de Ingreso Urgencias
			parameters.put("via_ingreso", '1');
			
			resultadoPaginadoMacro.buscarDatos(parameters);
			
			if(resultadoPaginadoMacro.totalRegistros() != 0){
				oportunidad = oportunidad_consulta/resultadoPaginadoMacro.totalRegistros();
			}else{
				oportunidad = 0;
			}
	
			lbTotal_oportunidad.setValue(oportunidad + "");

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFila(Object objeto, Component componente)
			throws Exception {
		final Listitem fila = new Listitem();

		final Admision admision = (Admision) objeto;
		Hbox hbox = new Hbox();
		
		double resultado = 0;
		Date fecha_inicio_urgencia = new Date();
		

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		
		Prestadores p = admision.getPrestadores();
		
		String prest = "";
		if (p != null) {
			prest = p.getNombres() + " " + p.getApellidos();
		}
		
		Paciente paciente = admision.getPaciente();
		
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
		
		
		Listcell listcell_traige = new Listcell();
		His_triage his_triage = new His_triage();
		his_triage.setCodigo_empresa(codigo_empresa);
		his_triage.setCodigo_sucursal(codigo_sucursal);
		his_triage.setIdentificacion(admision.getNro_identificacion());
		his_triage.setNro_ingreso(admision.getNro_ingreso());
		his_triage = getServiceLocator().getServicio(His_triageService.class).consultar(his_triage);

				
		Popup popup_traige = new Popup();
		Html html = new Html();
		
		if (his_triage != null) {
			popup_traige.appendChild(html);
			divPopups_traige.appendChild(popup_traige);

			String style = his_triage.getNivel_triage().equals("1") ? "red"
						: his_triage.getNivel_triage().equals("2") ? "orange"
								: his_triage.getNivel_triage().equals("3") ? "yellow"
										: "green";
				listcell_traige.setStyle("text-align:center;background-color:"
						+ style
						+ ";font-weight:bold;border-style:solid;cursor : help");
				listcell_traige
						.setLabel(his_triage.getNivel_triage().equals("1") ? "I"
								: his_triage.getNivel_triage().equals("2") ? "II"
										: his_triage.getNivel_triage().equals(
												"3") ? "III" : "IV");
				html.setContent("Paciente clasificado con Triage Nivel "
						+ listcell_traige.getLabel()
						+ ". <br/>"
						+ (his_triage.getAdmitido_si().equals("1") ? "Urgencia General"
								: his_triage.getAdmitido_si().equals("2") ? "Urgencia Obstétrica"
										: ""));
				
				
				if(his_triage.getNivel_triage().equals("4") && his_triage.getAdmitido().equals("N")){

					resultado = restarFecha(admision.getFecha_ingreso(),
							his_triage.getFecha_inicial());
					
				}else{
					

					Hisc_urgencia hisc_urgencia = new Hisc_urgencia();
					hisc_urgencia.setCodigo_empresa(codigo_empresa);
					hisc_urgencia.setCodigo_sucursal(codigo_sucursal);
					hisc_urgencia.setNro_identificacion(admision.getNro_identificacion());
					hisc_urgencia.setNro_ingreso(admision.getNro_ingreso());
					hisc_urgencia = getServiceLocator().getServicio(Hisc_urgenciaService.class).consultar(hisc_urgencia);
					
					if(hisc_urgencia !=null){
					fecha_inicio_urgencia = hisc_urgencia.getFecha_ingreso();
					
					resultado = restarFecha(his_triage.getCreacion_date(),
							fecha_inicio_urgencia);
					}
					
				}
			} else {
				listcell_traige
						.setStyle("text-align:center;font-weight:bold;border-style:solid;cursor : help");
				listcell_traige.setImage("/images/interrogracion.png");
				html.setContent("Paciente admitido omitiendo clasificacion de triage");
				
				resultado = restarFecha(admision.getFecha_ingreso(),
						admision.getFecha_apertura());
				
			}
		
		listcell_traige.setTooltip(popup_traige);
				
		
		fila.appendChild(new Listcell(admision.getNro_identificacion() + ""));
		
		fila.appendChild(new Listcell(nombre_paciente+ ""));

		fila.appendChild(new Listcell(prest+" "));
		
		fila.appendChild(listcell_traige);
		
		if (his_triage != null) {
			if(his_triage.getNivel_triage().equals("4") && his_triage.getAdmitido().equals("N")){
				
				fila.appendChild(new Listcell(dateFormat.format(admision.getFecha_ingreso()) + ""));	
				fila.appendChild(new Listcell(dateFormat.format(his_triage.getFecha_inicial()) + ""));					
			}else{
				
				fila.appendChild(new Listcell(dateFormat.format(his_triage.getCreacion_date()) + ""));	
				fila.appendChild(new Listcell(dateFormat.format(fecha_inicio_urgencia) + ""));					
			}		
		}else{
			
				fila.appendChild(new Listcell(dateFormat.format(admision.getFecha_ingreso()) + ""));
				fila.appendChild(new Listcell(dateFormat.format(admision.getFecha_apertura()) + ""));					
		}
						
			
		/*
		if (his_triage != null) {
				resultado = restarFecha(his_triage.getFecha_inicial(),
					admision.getFecha_apertura());
		}else{
				resultado = restarFecha(admision.getFecha_ingreso(),
					admision.getFecha_apertura());
			} 
		*/
		long hora_entero = (long)(resultado / 3600000);
		//Double min = (double)((horas-hora_entero)*60000);
		long min_entero = (long)((resultado % 3600000) / 60000);
//		long segundos = (long)(((resultado % 3600000) % 60000) / 1000);
		

		
		Long suma_minutos = (hora_entero*60) + min_entero;
		
		//log.info("hora_entero "+hora_entero);
		//log.info("min_entero "+min_entero);
		//log.info("segundos "+segundos);
		
		fila.appendChild(new Listcell(suma_minutos+""));
		
		//fila.appendChild(new Listcell(hora_entero + ":" + min_entero + ":" + segundos));
			


		//DecimalFormat formateador = new DecimalFormat("####.####");
		//fila.appendChild(new Listcell(oportunidad+""));
		
		
		Listcell listcell = new Listcell();
		listcell.appendChild(hbox);

		fila.appendChild(listcell);

		fila.setValue(admision);
		oportunidad_consulta = oportunidad_consulta+suma_minutos;
		
				
		
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

						if (centro_atencion != null) {
							
							parametros.put(
									"codigo_centro_dh",
									centro_atencion != null ? centro_atencion
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
	
	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Admision> listarResultados(
					Map<String, Object> parametros) {
				List<Admision> listado = getServiceLocator()
						.getAdmisionService().listarResultados(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getAdmisionService()
						.totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFila(dato, listboxResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				listboxResultado, 7);
	}

	private void parametrizarBandboxCentro() {
		tbxCodigo_centro.inicializar(tbxNomCentro, btnLimpiarCentro);
		tbxCodigo_centro
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {

						// Extraemos valores
						String nro_identificacion = (String) registro.getCodigo_centro();
						String nombres = (String) registro.getNombre_centro();

						// Mostramos valores en vista
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(nro_identificacion));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(nombres));
						listitem.appendChild(listcell);

					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramOportunidad", "");
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
								Centro_atencionService.class).listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {

						String nro_identificacion = (String) registro.getCodigo_centro();
						String nombres = (String) registro.getNombre_centro();


						bandbox.setValue(nro_identificacion);
						textboxInformacion.setValue(nombres);
						centro_atencion = registro;
						actualizarDatosHistoria();
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						centro_atencion = null;
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

	
	public void accionForm(String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			
			groupboxConsultar.setVisible(true);
		} else {
			groupboxConsultar.setVisible(false);
		}
	}
	
	public Citas onSeleccionarCita(Admision admision){
	
			admision_seleccionada = admision;
			
			//log.info("-"+admision_seleccionada);
			
			Citas cita = new Citas();
			cita.setCodigo_empresa(admision_seleccionada.getCodigo_empresa());
			cita.setCodigo_sucursal(admision_seleccionada.getCodigo_sucursal());
			cita.setCodigo_cita(admision_seleccionada.getCodigo_cita());
			
		    cita = citasService.consultar(cita);
			
			if(cita != null){

				return cita; 
			}else {
				
				return null;
			}
			
		
	}
	
	public long restarFecha(Date fechaIni, Date fechaFin) {
		  long diferencia, fin, inicio;
		  //log.info("----->" + fechaIni.getTime() + " - " + fechaFin.getTime());
		  
		  if (fechaIni != null && fechaFin != null){
			  fin = fechaFin.getTime();
			  inicio = fechaIni.getTime();
			  diferencia = fin - inicio;
			  //log.info("diferencia"+diferencia);
			  
			  	return diferencia;
		  	
		  }else{
			
			  	return 0;
		  }
		 }
 
	public void mostrarDatos() throws Exception {
		/*
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
		*/
	}


}