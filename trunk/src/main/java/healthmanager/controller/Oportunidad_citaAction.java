/*
 * admisionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.PrestadoresService;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class Oportunidad_citaAction extends ZKWindow  {

//	private static Logger log = Logger
//			.getLogger(Auditoria_admisionAction.class);

	private CitasService citasService;
//	private Citas cita_seleccionada;

//	private Historia_clinicaService historia_clinicaService;
	// Componentes //
	
	@View
	private Listbox listboxResultado;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;
	@View
	private Label lbTotal_historias;
	@View
	private Label lbTotal_oportunidad;
	
	private long cantidad_fecha; 
	private long cantidad_citas; 
	private double cantidad_oportunidad; 

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
//	private Paciente paciente;


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
			lbTotal_oportunidad.setValue("");
			cantidad_fecha = 0;
			cantidad_citas = 0;
			cantidad_oportunidad = 0;

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (filtrar_centro) {
				parameters.put("codigo_centro",centro_atencion_session.getCodigo_centro());
			}

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

			//log.info("parametros--->"+parameters);
			
			List<Map<String, Object>> lista_datos = citasService.listar_oportunidad(parameters);
			listboxResultado.getItems().clear();

			for (Map<String, Object> citas : lista_datos) {
				listboxResultado.appendChild(crearFilas(citas, this));
			}

			if(cantidad_citas != 0){
			cantidad_oportunidad = cantidad_fecha/cantidad_citas;
			}
			lbTotal_historias.setValue(cantidad_citas + "");
			
			//log.info("1--->"+cantidad_oportunidad);
			DecimalFormat formateador = new DecimalFormat("####.####");
			//log.info("2--->"+formateador.format(cantidad_oportunidad));
			lbTotal_oportunidad.setValue(formateador.format(cantidad_oportunidad) + "");
			
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

	public Listitem crearFilas(Map<String, Object> objeto, Component componente)
			throws Exception {
		final Listitem fila = new Listitem();

		//final Citas citas = (Citas) objeto;
		Hbox hbox = new Hbox();
		String via_ingreso = (String) objeto.get("via_ingreso");
		Long diferencia_fecha = (Long) objeto.get("diferencia_fecha");
		Long total_citas = (Long) objeto.get("total_citas");
		Double oportunidad = (Double) objeto.get("oportunidad");
		
		
		fila.appendChild(new Listcell(via_ingreso+ ""));
		
		fila.appendChild(new Listcell(diferencia_fecha + ""));
		
				
		fila.appendChild(new Listcell(total_citas + ""));
			
		DecimalFormat formateador = new DecimalFormat("####.####");
		fila.appendChild(new Listcell(formateador.format (oportunidad)+""));
		
		Listcell listcell = new Listcell();
		listcell.appendChild(hbox);

		fila.appendChild(listcell);

		//fila.setValue(citas);
		
		cantidad_fecha = cantidad_fecha+diferencia_fecha;
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
						parametros.put("value", "%"+ valorBusqueda.toUpperCase().trim() + "%");
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
						
						//log.info("parametros"+parametros);
						
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
	
	
	public long restarFecha(Date fechaIni, Date fechaFin) {
		  long diferencia, fin, inicio;
		  final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
		  
		  fin = fechaFin.getTime();
		  inicio = fechaIni.getTime();
		  diferencia = (fin - inicio)/MILLSECS_PER_DAY;
		  //log.info("diferencia"+diferencia);
		  // diferencia = (fechaFin.getTime() - fechaIni.getTime()) / 100;

		  return diferencia;

		 }
 
	public void mostrarDatos() throws Exception {
		
	}


}