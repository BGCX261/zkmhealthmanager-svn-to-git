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
import healthmanager.modelo.bean.Oportunidad_citas;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.softcomputo.composer.constantes.IParametrosSesion;

public class Oportunidad_consultaAction extends ZKWindow  {

	private static Logger log = Logger
			.getLogger(Oportunidad_consultaAction.class);

	private Admision admision_seleccionada;

	private CitasService citasService;
	
	// Componentes //
	
	@View
	private Listbox listboxResultado;
	@View
	private Datebox dtbxFecha_final;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Label lbTotal_historias;
	
	private long cantidad_citas; 

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
	private Integer paginacion = 20;
	private Prestadores prestador;
	private Centro_atencion centro_atencion;


	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizarBandboxPrestador();
		parametrizarBandboxCentro();
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

			//String value = tbxValue.getValue().toUpperCase().trim();
			lbTotal_historias.setValue("");
			cantidad_citas = 0;

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (filtrar_centro) {
				parameters.put("codigo_centro",centro_atencion_session.getCodigo_centro());
			}


			parameters.put("fecha_apertura", "fecha_apertura");
			
			parameters.put("atendida", true);
			parameters.put("estado", "2");
			parameters.put("paramTodo", "paramTodo");
			//parameters.put("value", value);
	
			if (dtbxFecha_inicial.getValue() != null && dtbxFecha_final.getValue() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String fecha1 = dateFormat.format(dtbxFecha_inicial.getValue());
				String fecha2 = dateFormat.format(dtbxFecha_final.getValue());
				
				if (dateFormat.parse(fecha1).getTime() > dateFormat.parse(fecha2).getTime()) {
					throw new ValidacionRunTimeException("La fecha inicial en la busqueda no puede ser mayor a la fecha final");
				}
				parameters.put("rango_fecha", "X");
				parameters.put("fecha_inicio", new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				parameters.put("fecha_final", new Timestamp(dtbxFecha_final
						.getValue().getTime()));
			} else if (dtbxFecha_inicial.getValue() != null) {
				parameters.put("fecha_inicial_p", new Timestamp(dtbxFecha_inicial.getValue().getTime()));
			} else if (dtbxFecha_final.getValue() != null) {
				parameters.put("fecha_final_p", new Timestamp(dtbxFecha_final.getValue().getTime()));
			}
			
			
			String prest = null;
			if (prestador != null) {
				prest = prestador.getNro_identificacion();
				parameters.put("codigo_medico", prest);
			}

			String centro = null;
			if (centro_atencion != null) {
				centro = centro_atencion.getCodigo_centro();
				parameters.put("codigo_centro", centro);
			}
			
			if (lbxVias_ingreso.getSelectedItems().size() > 0) {
				List<String> listado_vias = new ArrayList<String>();
				for (Listitem listitem : lbxVias_ingreso.getSelectedItems()) {
					listado_vias.add((String) listitem.getValue());
				}
				btnFiltro_ingreso.setImage("/images/filtro1.png");
				//log.info("listado_vias"+listado_vias);
				parameters.put("vias_ingreso", listado_vias);
			} else {
				btnFiltro_ingreso.setImage("/images/filtro.png");
			}

			log.debug("parametros--->"+parameters);
			
			List<Oportunidad_citas> lista_datos = citasService.listar_oportunidad_consulta(parameters);
			listboxResultado.getItems().clear();

			for (Oportunidad_citas oportunidad_citas : lista_datos) {

				//log.info("citas--->"+oportunidad_citas);
				listboxResultado.appendChild(crearFilas(oportunidad_citas, this));
			}

			lbTotal_historias.setValue(cantidad_citas + "");
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

	public Listitem crearFilas(Oportunidad_citas objeto, Component componente)
			throws Exception {
		final Listitem fila = new Listitem();

		//final Citas admision = (Admision) objeto;
		Hbox hbox = new Hbox();

		String via_ingreso = objeto.getVia_ingreso();
		Long total_citas = objeto.getTotal_citas();
		Double valor_en_minutos = objeto.getMinutos() != null ? objeto.getMinutos().doubleValue() : 0D;
		
		//log.info("valor_en_minutos "+valor_en_minutos);
		
		fila.appendChild(new Listcell(via_ingreso+""));
		
		fila.appendChild(new Listcell(total_citas+""));
		
		Double horas = (double) (valor_en_minutos / 60);
		Long hora_entero = (long) (valor_en_minutos / 60);
		Double min = (double)((horas-hora_entero)*60);
		Long min_entero = (long)((horas-hora_entero)*60);
		Long segundos = (long) ((min-min_entero)*60);
		
		//log.info("horas "+horas);
		//log.info("hora_entero "+hora_entero);
		//log.info("min "+min);
		//log.info("min_entero "+min_entero);
		//log.info("segundos "+segundos);
		
		
		fila.appendChild(new Listcell(hora_entero + ":" + min_entero + ":" + segundos));
			


		//DecimalFormat formateador = new DecimalFormat("####.####");
		//fila.appendChild(new Listcell(oportunidad+""));
		
		
		Listcell listcell = new Listcell();
		listcell.appendChild(hbox);

		fila.appendChild(listcell);

		//fila.setValue(admision);
		cantidad_citas = cantidad_citas+total_citas;
		
				
		
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