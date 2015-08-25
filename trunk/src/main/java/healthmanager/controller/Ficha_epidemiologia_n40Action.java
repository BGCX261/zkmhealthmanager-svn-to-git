/*
 * ficha_epidemiologia_n40Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n40;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n40Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n40>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n40Action.class);
	
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	
	@View private Textbox tbxCodigo_ficha;
	@View private Datebox dtbxFecha_ficha;
	@View private Textbox tbx_nro_identificacion;
	@View private Textbox tbx_tipo_identificacion;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View private Textbox tbx_nombres;
	@View private Datebox dtbxFecha_de_visita_de_campo;
	@View private Radiogroup rdbNumero_de_dosis_aplicadas_de_dpt_o_toxoide_tetanico;
	@View private Datebox dtbxFecha_ultima_dosis;
	@View private Radiogroup rdbInformacion_de_la_vacuna_fue_obtenida_por;
	@View private Radiogroup rdbAntecedentes_medicos_importantes;
	@View private Textbox tbxOtro_antecedente_medico;
	@View private Radiogroup rdbDolor_de_cuello;
	@View private Radiogroup rdbDolor_de_garganta;
	@View private Radiogroup rdbImposibilidad_para_abrir_la_boca;
	@View private Radiogroup rdbDisfagia;
	@View private Radiogroup rdbConvulsiones;
	@View private Radiogroup rdbContracciones_musculares;
	@View private Radiogroup rdbRigidez_en_musculos_adbominales;
	@View private Radiogroup rdbEspasmos_generalizados;
	@View private Radiogroup rdbRigidez_de_nuca;
	@View private Radiogroup rdbAfectacion_de_nervios_craneales;
	@View private Radiogroup rdbTrismus;
	@View private Radiogroup rdbOpistotonos;
	@View private Radiogroup rdbFiebre;
	@View private Textbox tbxOtro_cual;
	@View private Radiogroup rdbPrueba_de_entrada;
	@View private Textbox tbxOtra_prueba;
	@View private Datebox dtbxFecha_de_la_herida;
	@View private Radiogroup rdbLocalizacion_anatomica_de_la_herida;
	@View private Radiogroup rdbManejo_inicial_en;
	@View private Textbox tbxOtro_manejo;
	@View private Radiogroup rdbSe_administro_antitoxina_tetanica;
	@View private Datebox dtbxFecha_de_administracion_1;
	@View private Radiogroup rdbSe_administro_toxoide_tetanico;
	@View private Datebox dtbxFecha_de_administracion_2;
	@View private Radiogroup rdbEstado_final_del_paciente;
	@View private Row row_1;
	@View private Row row_2;
	@View private Row row_3;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception{
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n40 ficha = new Ficha_epidemiologia_n40();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n40) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 40-------> "+ficha);
			
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
	
	public void listarCombos(){
		listarParameter();
	}
	
	//mostrar filas de "otros datos"
	public void mostrarFila_1(Radiogroup rg){
		if(rg.getSelectedItem().getValue().equals("6")){
		row_1.setVisible(true);
	}else{
		
		row_1.setVisible(false);
		tbxOtro_antecedente_medico.setText("");
	}
	}
	public void mostrarFila_2(Radiogroup rg){
		if(rg.getSelectedItem().getValue().equals("6")){
		row_2.setVisible(true);
	}else{
		
		row_2.setVisible(false);
		tbxOtro_antecedente_medico.setText("");
	}
	}
	public void mostrarFila_3(Radiogroup rg){
		if(rg.getSelectedItem().getValue().equals("3")){
		row_3.setVisible(true);
	}else{
		
		row_3.setVisible(false);
		tbxOtro_antecedente_medico.setText("");
	}
	}
	
	
	

	
	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	//Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw,String accion)throws Exception{
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		
		if(!accion.equalsIgnoreCase("registrar")){
			buscarDatos();
		}else{
			//buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos()throws Exception{
		FormularioUtil.limpiarComponentes(groupboxEditar,idsExcluyentes);
	}
	
	// Metodo para validar campos del formulario //
		public boolean validarFichaEpidemiologia() {

			dtbxFecha_ficha.setStyle("background-color:white");
			// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:white");
			tbx_nro_identificacion.setStyle("background-color:white");

			boolean valida = true;

			if (dtbxFecha_ficha.getText().trim().equals("")) {
				dtbxFecha_ficha.setStyle("background-color:#F6BBBE");
				valida = false;
			}
			
			if (tbx_nro_identificacion.getText().trim().equals("")) {
				tbx_nro_identificacion.setStyle("background-color:#F6BBBE");
				valida = false;
			}

			if (!valida) {
				MensajesUtil.mensajeAlerta(usuarios.getNombres()
						+ " recuerde que...",
						"Los campos marcados con (*) son obligatorios");
			}

			return valida;
		}

	//Metodo para buscar //
	public void buscarDatos()throws Exception{
		try {
			String parameter = lbxParameter.getSelectedItem().getValue().toString();
			String value = tbxValue.getValue().toUpperCase().trim();
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%"+value+"%");
			
			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
					"limit 25 offset 0");

			List<Ficha_epidemiologia_n40> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n40.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n40 ficha_epidemiologia_n40 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n40,
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
	
	public Row crearFilas(Object objeto, Component componente)throws Exception{
		Row fila = new Row();
		
		final Ficha_epidemiologia_n40 ficha_epidemiologia_n40 = (Ficha_epidemiologia_n40)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n40.getCodigo_ficha()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n40);
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
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
					"Eliminar Registro", Messagebox.YES + Messagebox.NO,
					Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener<Event>() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								// do the thing
								eliminarDatos(ficha_epidemiologia_n40);
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
	
	//Metodo para guardar la informacion //
	public Ficha_epidemiologia_n40 obtenerFichaEpidemiologia() {
	
				Ficha_epidemiologia_n40 ficha_epidemiologia_n40 = new Ficha_epidemiologia_n40();
				ficha_epidemiologia_n40.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n40.setFecha_ficha(new Timestamp(dtbxFecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n40.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n40.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n40.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n40.setNro_identificacion(admision != null ? admision
						.getNro_identificacion() : null);
				ficha_epidemiologia_n40.setFecha_de_visita_de_campo(new Timestamp(dtbxFecha_de_visita_de_campo.getValue().getTime()));
				ficha_epidemiologia_n40.setNumero_de_dosis_aplicadas_de_dpt_o_toxoide_tetanico(rdbNumero_de_dosis_aplicadas_de_dpt_o_toxoide_tetanico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setFecha_ultima_dosis(new Timestamp(dtbxFecha_ultima_dosis.getValue().getTime()));
				ficha_epidemiologia_n40.setInformacion_de_la_vacuna_fue_obtenida_por(rdbInformacion_de_la_vacuna_fue_obtenida_por.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setAntecedentes_medicos_importantes(rdbAntecedentes_medicos_importantes.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setOtro_antecedente_medico(tbxOtro_antecedente_medico.getValue());
				ficha_epidemiologia_n40.setDolor_de_cuello(rdbDolor_de_cuello.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setDolor_de_garganta(rdbDolor_de_garganta.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setImposibilidad_para_abrir_la_boca(rdbImposibilidad_para_abrir_la_boca.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setDisfagia(rdbDisfagia.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setConvulsiones(rdbConvulsiones.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setContracciones_musculares(rdbContracciones_musculares.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setRigidez_en_musculos_adbominales(rdbRigidez_en_musculos_adbominales.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setEspasmos_generalizados(rdbEspasmos_generalizados.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setRigidez_de_nuca(rdbRigidez_de_nuca.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setAfectacion_de_nervios_craneales(rdbAfectacion_de_nervios_craneales.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setTrismus(rdbTrismus.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setOpistotonos(rdbOpistotonos.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setFiebre(rdbFiebre.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setOtro_cual(tbxOtro_cual.getValue());
				ficha_epidemiologia_n40.setPrueba_de_entrada(rdbPrueba_de_entrada.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setOtra_prueba(tbxOtra_prueba.getValue());
				ficha_epidemiologia_n40.setFecha_de_la_herida(new Timestamp(dtbxFecha_de_la_herida.getValue().getTime()));
				ficha_epidemiologia_n40.setLocalizacion_anatomica_de_la_herida(rdbLocalizacion_anatomica_de_la_herida.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setManejo_inicial_en(rdbManejo_inicial_en.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setOtro_manejo(tbxOtro_manejo.getValue());
				ficha_epidemiologia_n40.setSe_administro_antitoxina_tetanica(rdbSe_administro_antitoxina_tetanica.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setFecha_de_administracion_1(new Timestamp(dtbxFecha_de_administracion_1.getValue().getTime()));
				ficha_epidemiologia_n40.setSe_administro_toxoide_tetanico(rdbSe_administro_toxoide_tetanico.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setFecha_de_administracion_2(new Timestamp(dtbxFecha_de_administracion_2.getValue().getTime()));
				ficha_epidemiologia_n40.setEstado_final_del_paciente(rdbEstado_final_del_paciente.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n40.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n40.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n40.setDelete_date(null);
				ficha_epidemiologia_n40.setUltimo_user(usuarios.getCodigo().toString());
				ficha_epidemiologia_n40.setDelete_use(null);
				ficha_epidemiologia_n40.setCreacion_user(usuarios.getCodigo().toString());
			
				return ficha_epidemiologia_n40;
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n40 obj)throws Exception{
		Ficha_epidemiologia_n40 ficha_epidemiologia_n40 = (Ficha_epidemiologia_n40)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n40.getCodigo_ficha());
			dtbxFecha_ficha.setValue(ficha_epidemiologia_n40.getFecha_ficha());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			dtbxFecha_de_visita_de_campo.setValue(ficha_epidemiologia_n40.getFecha_de_visita_de_campo());
			Utilidades.seleccionarRadio(rdbNumero_de_dosis_aplicadas_de_dpt_o_toxoide_tetanico, ficha_epidemiologia_n40.getNumero_de_dosis_aplicadas_de_dpt_o_toxoide_tetanico());
			dtbxFecha_ultima_dosis.setValue(ficha_epidemiologia_n40.getFecha_ultima_dosis());
			Utilidades.seleccionarRadio(rdbInformacion_de_la_vacuna_fue_obtenida_por, ficha_epidemiologia_n40.getInformacion_de_la_vacuna_fue_obtenida_por());
			Utilidades.seleccionarRadio(rdbAntecedentes_medicos_importantes, ficha_epidemiologia_n40.getAntecedentes_medicos_importantes());
			tbxOtro_antecedente_medico.setValue(ficha_epidemiologia_n40.getOtro_antecedente_medico());
			Utilidades.seleccionarRadio(rdbDolor_de_cuello, ficha_epidemiologia_n40.getDolor_de_cuello());
			Utilidades.seleccionarRadio(rdbDolor_de_garganta, ficha_epidemiologia_n40.getDolor_de_garganta());
			Utilidades.seleccionarRadio(rdbImposibilidad_para_abrir_la_boca, ficha_epidemiologia_n40.getImposibilidad_para_abrir_la_boca());
			Utilidades.seleccionarRadio(rdbDisfagia, ficha_epidemiologia_n40.getDisfagia());
			Utilidades.seleccionarRadio(rdbConvulsiones, ficha_epidemiologia_n40.getConvulsiones());
			Utilidades.seleccionarRadio(rdbContracciones_musculares, ficha_epidemiologia_n40.getContracciones_musculares());
			Utilidades.seleccionarRadio(rdbRigidez_en_musculos_adbominales, ficha_epidemiologia_n40.getRigidez_en_musculos_adbominales());
			Utilidades.seleccionarRadio(rdbEspasmos_generalizados, ficha_epidemiologia_n40.getEspasmos_generalizados());
			Utilidades.seleccionarRadio(rdbRigidez_de_nuca, ficha_epidemiologia_n40.getRigidez_de_nuca());
			Utilidades.seleccionarRadio(rdbAfectacion_de_nervios_craneales, ficha_epidemiologia_n40.getAfectacion_de_nervios_craneales());
			Utilidades.seleccionarRadio(rdbTrismus, ficha_epidemiologia_n40.getTrismus());
			Utilidades.seleccionarRadio(rdbOpistotonos, ficha_epidemiologia_n40.getOpistotonos());
			Utilidades.seleccionarRadio(rdbFiebre, ficha_epidemiologia_n40.getFiebre());
			tbxOtro_cual.setValue(ficha_epidemiologia_n40.getOtro_cual());
			Utilidades.seleccionarRadio(rdbPrueba_de_entrada, ficha_epidemiologia_n40.getPrueba_de_entrada());
			tbxOtra_prueba.setValue(ficha_epidemiologia_n40.getOtra_prueba());
			dtbxFecha_de_la_herida.setValue(ficha_epidemiologia_n40.getFecha_de_la_herida());
			Utilidades.seleccionarRadio(rdbLocalizacion_anatomica_de_la_herida, ficha_epidemiologia_n40.getLocalizacion_anatomica_de_la_herida());
			Utilidades.seleccionarRadio(rdbManejo_inicial_en, ficha_epidemiologia_n40.getManejo_inicial_en());
			tbxOtro_manejo.setValue(ficha_epidemiologia_n40.getOtro_manejo());
			Utilidades.seleccionarRadio(rdbSe_administro_antitoxina_tetanica, ficha_epidemiologia_n40.getSe_administro_antitoxina_tetanica());
			dtbxFecha_de_administracion_1.setValue(ficha_epidemiologia_n40.getFecha_de_administracion_1());
			Utilidades.seleccionarRadio(rdbSe_administro_toxoide_tetanico, ficha_epidemiologia_n40.getSe_administro_toxoide_tetanico());
			dtbxFecha_de_administracion_2.setValue(ficha_epidemiologia_n40.getFecha_de_administracion_2());
			Utilidades.seleccionarRadio(rdbEstado_final_del_paciente, ficha_epidemiologia_n40.getEstado_final_del_paciente());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n40 ficha_epidemiologia_n40 = (Ficha_epidemiologia_n40)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n40);
			if(result==0){
				throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}
			
			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
				"Information", Messagebox.OK, Messagebox.INFORMATION);
		}catch (HealthmanagerException e) {
			MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
		}catch(RuntimeException r){
			MensajesUtil.mensajeError(r, "", this);
		}
	}
	public void obtenerAdmision(Admision admision){
		Paciente paciente = admision.getPaciente();
		tbx_nro_identificacion.setValue(admision.getNro_identificacion());
		tbx_nombres.setValue(paciente.getNombreCompleto());
		tbx_tipo_identificacion.setValue(paciente.getTipo_identificacion());
		
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
	public Ficha_epidemiologia_n40 consultarDatos(Map<String, Object> map,
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
				parameters.put("nro_identificacion", admision.getNro_identificacion());
				
				if(impresion_diagnostica != null){
				parameters.put("codigo_historia", impresion_diagnostica.getCodigo_historia());
				}else{
					return null;
				}
				
				if(cie_epidemiologia != null){
					parameters.put("codigo", cie_epidemiologia.getCodigo_cie());			
				}else{
					return null;
				}
				
				getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
				
				List<Ficha_epidemiologia_n40> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n40.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n40 ficha_n40 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n40;
				}else{

					return null;
				}
	}
	

}