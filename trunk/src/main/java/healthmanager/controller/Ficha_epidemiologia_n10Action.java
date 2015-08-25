/*
 * ficha_epidemiologia_n10Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Ficha_epidemiologia_n10;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n10Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n10> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n10Action.class);

	// Componentes //
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad_p;
	@View
	private Textbox tbxIdentificacion_p;
	@View 
	private Textbox tbxAseguradora;
	@View 
	private Textbox tbxNombre_aseguradora;
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Textbox tbxCodigo_ficha;
	@View
	private Listbox lbxUnidad_medida;
	@View
	private Datebox dtbxFecha_inicio_sintomas;
	@View
	private Listbox lbxDepartamento;
	@View
	private Listbox lbxMunicipio;
	@View
	private Listbox lbxTipo_regimen_salud;
	@View
	private Textbox tbxNombre_administradora_salud;
	@View
	private Textbox tbxCodigo_administradora;
	@View
	private Textbox tbxExamen_solicitado;
	@View
	private Textbox tbxMuestra_enviada;
	@View
	private Textbox tbxHallazgo_y_observaciones;
	@View
	private Datebox dtbxFecha_de_toma1;
	@View
	private Datebox dtbxFecha_de_recepcion1;
	@View
	private Datebox dtbxFecha_de_resultado1;
	@View
	private Checkbox chbMuestra1;
	@View
	private Checkbox chbPrueba1;
	@View
	private Textbox tbxAgente1;
	@View
	private Textbox tbxResultado1;
	@View
	private Textbox tbxValor_del_registro1;
	@View
	private Datebox dtbxFecha_de_toma2;
	@View
	private Datebox dtbxFecha_de_recepcion2;
	@View
	private Datebox dtbxFecha_de_resultado2;
	@View
	private Textbox tbxValor_del_registro2;
	@View
	private Checkbox chbMuestra2;
	@View
	private Checkbox chbPrueba2;
	@View
	private Textbox tbxAgente2;
	@View
	private Textbox tbxResultado2;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;

	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);
		
		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n10 ficha = new Ficha_epidemiologia_n10();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n10) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 10-------> "+ficha);
			
			mostrarDatos(ficha);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
		}
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxUnidad_medida, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxDepartamento, true,
				getServiceLocator());
		Utilidades.listarDepartamentos(lbxDepartamento, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_regimen_salud, true,
				getServiceLocator());
	}

	// manuel
	public void listarMunicipios(Listbox lbxMunicipio, Listbox lbxDepartamento) {
		Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("fecha_inicial");
		listitem.setLabel("Fecha_inicial");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		valida = false;
	}

	boolean valida = true;

	// Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia() {
		boolean valida = true;
		if (lbxUnidad_medida.getSelectedItem().getValue().toString().equals("")) {
			lbxUnidad_medida.setStyle("background-color:#F6BBBE");
			valida = false;

		}

		if (lbxDepartamento.getSelectedItem().getValue().toString().equals("")) {
			lbxDepartamento.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxMunicipio.getSelectedItem().getValue().toString().equals("")) {
			lbxMunicipio.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (lbxTipo_regimen_salud.getSelectedItem().getValue().toString()
				.equals("")) {
			lbxTipo_regimen_salud.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
					"limit 25 offset 0");

			List<Ficha_epidemiologia_n10> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(Ficha_epidemiologia_n10.class, parameters);
			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n10 ficha_epidemiologia_n10 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n10,
						this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Ficha_epidemiologia_n10 ficha_epidemiologia_n10 = (Ficha_epidemiologia_n10) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n10);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									// eliminarDatos(ficha_epidemiologia_n10);
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
	public Ficha_epidemiologia_n10 obtenerFichaEpidemiologia() {
		// Cargamos los componentes //

		Ficha_epidemiologia_n10 ficha_epidemiologia_n10 = new Ficha_epidemiologia_n10();
		ficha_epidemiologia_n10.setFecha_inicial(new Timestamp(
				dtbxFecha_inicial.getValue().getTime()));
		ficha_epidemiologia_n10.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n10.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		ficha_epidemiologia_n10.setCodigo_diagnostico("A001");
		ficha_epidemiologia_n10.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n10.setIdentificacion(tbxIdentificacion_p.getValue());
		/*
		 * ficha_epidemiologia_n10.setPrimer_nombre_paciente("");
		 * ficha_epidemiologia_n10.setSegundo_nombre_paciente("");
		 * ficha_epidemiologia_n10.setPrimer_apellido_paciente("");
		 * ficha_epidemiologia_n10.setSegundo_apellido_paciente("");
		 * ficha_epidemiologia_n10.setTipo_identidad("");
		 * ficha_epidemiologia_n10.setNumero_identidad("");
		 * ficha_epidemiologia_n10.setEdad("");
		 */
		ficha_epidemiologia_n10.setUnidad_medida(lbxUnidad_medida
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n10.setFecha_inicio_sintomas(new Timestamp(
				dtbxFecha_inicio_sintomas.getValue().getTime()));
		ficha_epidemiologia_n10.setDepartamento(lbxDepartamento
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n10.setMunicipio(lbxMunicipio.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n10.setTipo_regimen_salud(lbxTipo_regimen_salud
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n10
				.setNombre_administradora_salud(tbxNombre_administradora_salud
						.getValue());
		ficha_epidemiologia_n10
				.setCodigo_administradora(tbxCodigo_administradora.getValue());
		ficha_epidemiologia_n10.setExamen_solicitado(tbxExamen_solicitado
				.getValue());
		ficha_epidemiologia_n10.setMuestra_enviada(tbxMuestra_enviada
				.getValue());
		ficha_epidemiologia_n10
				.setHallazgo_y_observaciones(tbxHallazgo_y_observaciones
						.getValue());
		ficha_epidemiologia_n10.setFecha_de_toma1(new Timestamp(
				dtbxFecha_de_toma1.getValue().getTime()));
		ficha_epidemiologia_n10.setFecha_de_recepcion1(new Timestamp(
				dtbxFecha_de_recepcion1.getValue().getTime()));
		ficha_epidemiologia_n10.setFecha_de_resultado1(new Timestamp(
				dtbxFecha_de_resultado1.getValue().getTime()));
		ficha_epidemiologia_n10
				.setMuestra1(chbMuestra1.isChecked() ? "S" : "N");
		ficha_epidemiologia_n10.setPrueba1(chbPrueba1.isChecked() ? "S" : "N");
		ficha_epidemiologia_n10.setAgente1(tbxAgente1.getValue());
		ficha_epidemiologia_n10.setResultado1(tbxResultado1.getValue());
		ficha_epidemiologia_n10.setValor_del_registro1(tbxValor_del_registro1
				.getValue());
		ficha_epidemiologia_n10.setFecha_de_toma2(new Timestamp(
				dtbxFecha_de_toma2.getValue().getTime()));
		ficha_epidemiologia_n10.setFecha_de_recepcion2(new Timestamp(
				dtbxFecha_de_recepcion2.getValue().getTime()));
		ficha_epidemiologia_n10.setFecha_de_resultado2(new Timestamp(
				dtbxFecha_de_resultado2.getValue().getTime()));
		ficha_epidemiologia_n10.setValor_del_registro2(tbxValor_del_registro2
				.getValue());
		ficha_epidemiologia_n10
				.setMuestra2(chbMuestra2.isChecked() ? "S" : "N");
		ficha_epidemiologia_n10.setPrueba2(chbPrueba2.isChecked() ? "S" : "N");
		ficha_epidemiologia_n10.setAgente2(tbxAgente2.getValue());
		ficha_epidemiologia_n10.setResultado2(tbxResultado2.getValue());
		ficha_epidemiologia_n10.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n10.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n10.setCreacion_user(usuarios.getCodigo()
				.toString());
		ficha_epidemiologia_n10.setDelete_date(null);
		ficha_epidemiologia_n10.setUltimo_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n10.setDelete_user(null);

		return ficha_epidemiologia_n10;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n10 obj) throws Exception {
		Ficha_epidemiologia_n10 ficha_epidemiologia_n10 = (Ficha_epidemiologia_n10) obj;
		try {
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n10
					.getFecha_inicial());
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n10.getCodigo_ficha());

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			for (int i = 0; i < lbxUnidad_medida.getItemCount(); i++) {
				Listitem listitem = lbxUnidad_medida.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n10.getUnidad_medida())) {
					listitem.setSelected(true);
					i = lbxUnidad_medida.getItemCount();
				}
			}
			dtbxFecha_inicio_sintomas.setValue(ficha_epidemiologia_n10
					.getFecha_inicio_sintomas());

			for (int i = 0; i < lbxDepartamento.getItemCount(); i++) {
				Listitem listitem = lbxDepartamento.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n10.getDepartamento())) {
					listitem.setSelected(true);
					i = lbxDepartamento.getItemCount();
				}
			}

			Utilidades.listarMunicipios(lbxMunicipio, lbxDepartamento,
					getServiceLocator());
			for (int i = 0; i < lbxMunicipio.getItemCount(); i++) {
				Listitem listitem = lbxMunicipio.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n10.getMunicipio())) {
					listitem.setSelected(true);
					i = lbxMunicipio.getItemCount();
				}
			}
			for (int i = 0; i < lbxTipo_regimen_salud.getItemCount(); i++) {
				Listitem listitem = lbxTipo_regimen_salud.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n10.getTipo_regimen_salud())) {
					listitem.setSelected(true);
					i = lbxTipo_regimen_salud.getItemCount();
				}
			}
			tbxNombre_administradora_salud.setValue(ficha_epidemiologia_n10
					.getNombre_administradora_salud());
			tbxCodigo_administradora.setValue(ficha_epidemiologia_n10
					.getCodigo_administradora());
			tbxExamen_solicitado.setValue(ficha_epidemiologia_n10
					.getExamen_solicitado());
			tbxMuestra_enviada.setValue(ficha_epidemiologia_n10
					.getMuestra_enviada());
			tbxHallazgo_y_observaciones.setValue(ficha_epidemiologia_n10
					.getHallazgo_y_observaciones());
			dtbxFecha_de_toma1.setValue(ficha_epidemiologia_n10
					.getFecha_de_toma1());
			dtbxFecha_de_recepcion1.setValue(ficha_epidemiologia_n10
					.getFecha_de_recepcion1());
			dtbxFecha_de_resultado1.setValue(ficha_epidemiologia_n10
					.getFecha_de_resultado1());
			chbMuestra1.setChecked(ficha_epidemiologia_n10.getMuestra1()
					.equals("S") ? true : false);
			chbPrueba1.setChecked(ficha_epidemiologia_n10.getPrueba1().equals(
					"S") ? true : false);
			tbxAgente1.setValue(ficha_epidemiologia_n10.getAgente1());
			tbxResultado1.setValue(ficha_epidemiologia_n10.getResultado1());
			tbxValor_del_registro1.setValue(ficha_epidemiologia_n10
					.getValor_del_registro1());
			dtbxFecha_de_toma2.setValue(ficha_epidemiologia_n10
					.getFecha_de_toma2());
			dtbxFecha_de_recepcion2.setValue(ficha_epidemiologia_n10
					.getFecha_de_recepcion2());
			dtbxFecha_de_resultado2.setValue(ficha_epidemiologia_n10
					.getFecha_de_resultado2());
			tbxValor_del_registro2.setValue(ficha_epidemiologia_n10
					.getValor_del_registro2());
			chbMuestra2.setChecked(ficha_epidemiologia_n10.getMuestra2()
					.equals("S") ? true : false);
			chbPrueba2.setChecked(ficha_epidemiologia_n10.getPrueba2().equals(
					"S") ? true : false);
			tbxAgente2.setValue(ficha_epidemiologia_n10.getAgente2());
			tbxResultado2.setValue(ficha_epidemiologia_n10.getResultado2());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n10 ficha_epidemiologia_n10 = (Ficha_epidemiologia_n10) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n10);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public void obtenerAdmision(Admision admision) {
		Paciente paciente = admision.getPaciente();
		tbxIdentificacion_p.setValue(admision.getNro_identificacion());
		tbxNombres_y_apellidos.setValue(paciente.getNombreCompleto());
		tbxTipo_identidad_p.setValue(paciente.getTipo_identificacion());

		
		tbxAseguradora.setValue(admision.getCodigo_administradora());
		//log.info("PACIENTE"+paciente);
		//log.info("ADMINISTRADORA"+admision.getCodigo_empresa()+" "+admision.getCodigo_sucursal()+" "+paciente.getCodigo_administradora());
		
		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(admision.getCodigo_empresa());
		administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
		administradora.setCodigo(admision.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService().consultar(administradora);
		//log.info("administradora"+administradora);
		
		tbxNombre_aseguradora.setValue(administradora.getNombre());
	}

	@Override
	public Ficha_epidemiologia_n10 consultarDatos(Map<String, Object> map,
			Ficha_epidemiologia ficha_epidemiologia) throws Exception {
		
//				Ficha_epidemiologia ficha = (Ficha_epidemiologia)ficha_epidemiologia;
				
				//log.info("-----------------");
				
				//log.info("map"+map);
				//log.info("ficha"+ficha);
				
				Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) map.get("impresion_diagnostica");
				Cie_epidemiologia cie_epidemiologia = (Cie_epidemiologia) map.get("cie_epidemiologia");
				Admision admision = (Admision) map.get("admision");
				
				Map<String,Object> parameters = new HashMap<String,Object>();
				parameters.put("codigo_empresa", admision.getCodigo_empresa());
				parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
				parameters.put("identificacion", admision.getNro_identificacion());
				
				if(impresion_diagnostica != null){
				parameters.put("codigo_historia", impresion_diagnostica.getCodigo_historia());
				}else{
					return null;
				}
				
				if(cie_epidemiologia != null){
					parameters.put("codigo_diagnostico", cie_epidemiologia.getCodigo_cie());			
				}else{
					return null;
				}
				
				getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
				
				List<Ficha_epidemiologia_n10> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n10.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n10 ficha_n10 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n10;
				}else{

					return null;
				}
	}

}