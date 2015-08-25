/*
 * ficha_epidemiologia_n24Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n24;
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
import org.zkoss.zul.Intbox;
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

public class Ficha_epidemiologia_n24Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n24>{

//	private static Logger log = Logger.getLogger(Ficha_epidemiologia_n24Action.class);
	
	//Componentes //
	@View private Listbox lbxParameter;
	@View private Textbox tbxValue;
	@View private Textbox tbxAccion;
	@View private Borderlayout groupboxEditar;
	@View private Groupbox groupboxConsulta;
	@View private Rows rowsResultado;
	@View private Grid gridResultado;
	@View private Textbox tbx_nro_identificacion;
	@View private Textbox tbx_tipo_identificacion;
	@View private Textbox tbx_nombres;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View private Textbox tbxCodigo_ficha;
	@View private Radiogroup rdbNombre_del_evento;
	@View private Datebox dtbxFecha_notificacion;
	@View private Textbox tbxRazon_social_de_la_upgd;
	@View private Textbox tbxDepartamento;
	@View private Textbox tbxMunicipio;
	@View private Textbox tbxCodigo_1;
	@View private Textbox tbxSubindice;
	@View private Radiogroup rdbClasificacion_inicial_del_caso;
	@View private Textbox tbxNombres_y_apellidos_del_propietario;
	@View private Textbox tbxDireccion_de_recidencia;
	@View private Intbox ibxTelefono;
	@View private Textbox tbxDepartamento_recidencia;
	@View private Textbox tbxCodigo_departamento;
	@View private Textbox tbxMunicipio_recidencia;
	@View private Textbox tbxCodigo_municipio;
	@View private Radiogroup rdbEspecie;
	@View private Textbox tbxRaza;
	@View private Textbox tbxColor_de_la_cabeza_del_animal;
	@View private Intbox ibxEdad;
	@View private Radiogroup rdbUnidad_de_medida_de_edad;
	@View private Radiogroup rdbAntecedente_de_vacunacion;
	@View private Datebox dtbxFecha_de_vacunacion;
	@View private Radiogroup rdbPresento_carnet_vigente;
	@View private Textbox tbxTipo_de_vacuna;
	@View private Textbox tbxLote_de_vacuna;
	@View private Radiogroup rdbArea_de_procedencia_del_animal;
	@View private Checkbox chbNinguno;
	@View private Checkbox chbAgrecividad;
	@View private Checkbox chbParalisis_miembros_posteriores;
	@View private Checkbox chbSalivacion;
	@View private Checkbox chbApetito_alterado;
	@View private Checkbox chbVoracidad;
	@View private Checkbox chbDeglucion_dificultosa;
	@View private Checkbox chbLadrido_ronco;
	@View private Checkbox chbMandibula_trabada;
	@View private Checkbox chbAnisocoria;
	@View private Checkbox chbOtro;
	@View private Textbox tbxCual_otro;
	@View private Datebox dtbxFecha_de_inicio_de_sintomas;
	@View private Radiogroup rdbTipo_de_muerte;
	@View private Datebox dtbxFecha_de_muerte;
	@View private Intbox ibxNumero_de_personas_expuestas;
	@View private Intbox ibxPerros_expuestos;
	@View private Intbox ibxGatos_expuestos;
	@View private Textbox tbxCuales_expuestos;
	@View private Intbox ibxOtros_expuestos;
	@View private Intbox ibxPerros_sintomatologia_nerviosa;
	@View private Intbox ibxGatos_sintomatologia_nerviosa;
	@View private Intbox ibxZorros_sintomatologia_nerviosa;
	@View private Intbox ibxMurcielagos_sintomatologia_nerviosa;
	@View private Textbox tbxOtros_sintomatologia_nerviosa;
	@View private Intbox ibxPerros_en_cuarentena;
	@View private Intbox ibxGatos_en_cuarentena;
	@View private Intbox ibxOtros_en_cuarentena;
	@View private Textbox tbxCuales_otros_en_cuarentena;
	@View private Intbox ibxPerros_eliminados;
	@View private Intbox ibxGatos_eliminados;
	@View private Intbox ibxZorros_eliminados;
	@View private Intbox ibxMurcielagos_eliminados;
	@View private Intbox ibxOtros_eliminados;
	@View private Textbox tbxCuales_otros_eliminados;
	@View private Intbox ibxPerros_vacunados;
	@View private Intbox ibxGatos_vacunados;
	@View private Intbox ibxOtros_vacunados;
	@View private Textbox tbxCuales_otros_vacunados;
	@View private Intbox ibxPerros_muestras_tomadas;
	@View private Intbox ibxGatos_muestras_tomadas;
	@View private Intbox ibxZorros_muestras_tomadas;
	@View private Intbox ibxMurcielagos_muestras_tomadas;
	@View private Intbox ibxOtros_muestras_tomadas;
	@View private Textbox tbxCuales_otros_muestras_tomadas;
	@View private Textbox tbxNomre_de_quien_remite_la_muestra;
	@View private Textbox tbxInstitucion;
	@View private Intbox ibxTelefono_1;
	@View private Textbox tbxCargo;
	@View private Datebox dtbxFecha_de_toma_de_muestra;
	@View private Datebox dtbxFecha_de_remision_de_muestra;
	@View private Radiogroup rdbDestino_de_muestra;
	@View private Textbox tbxOtro_destino;
	@View private Radiogroup rdbPrueba_diagnostica_confirmatoria;
	@View private Radiogroup rdbResultado;
	@View private Radiogroup rdbIdentificacion_variante;
	@View private Radiogroup rdbVariante_identificada;
	@View private Textbox tbxOtra_variante;
	@View private Datebox fecha_ficha;
	@View private Textbox tbxCuales_sintomatologia_nerviosa;
	@View private Row row_1;
	@View private Row row_2;
	@View private Row row_7;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	@Override
	public void init() throws Exception{
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n24 ficha = new Ficha_epidemiologia_n24();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n24) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 24-------> "+ficha);
			
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
	
	public void mostrarChec(){
		if (chbOtro.isChecked()){
			
			tbxCual_otro.setDisabled(false);
		}else{
		
			tbxCual_otro.setDisabled(true);
			tbxCual_otro.setText("");
			
		}
		
	}
	public void no_seleccionado(){
		
	}
	

	
	public void mostrar_fila_expuestos( ){	
		row_1.setVisible(!ibxOtros_expuestos.getText().equals(""));

		}
	
	public void mostrar_fila(Radiogroup r){
		if(r.getSelectedItem().getValue().equals("3")){
			row_2.setVisible(true);
		}else{
			row_2.setVisible(false);
			tbxOtro_destino.setText("");
		}
		
	}
	public void mostrar_fila_7(Radiogroup r){
		if(r.getSelectedItem().getValue().equals("9")){
			row_7.setVisible(true);
		}else{
			row_7.setVisible(false);
			tbxOtra_variante.setText("");
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

			fecha_ficha.setStyle("background-color:white");
			// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:white");
			tbx_nro_identificacion.setStyle("background-color:white");

			boolean valida = true;

			if (fecha_ficha.getText().trim().equals("")) {
				fecha_ficha.setStyle("background-color:#F6BBBE");
				valida = false;
			}
			// if(tbxNombres_y_apellidos.getText().trim().equals("")){
			// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			// valida = false;
			// }
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

			List<Ficha_epidemiologia_n24> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n24.class, parameters);

			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n24 ficha_epidemiologia_n24 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n24,
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
		
		final Ficha_epidemiologia_n24 ficha_epidemiologia_n24 = (Ficha_epidemiologia_n24)objeto;
		
		Hbox hbox = new Hbox();
		Space space = new Space();
		
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n24.getCodigo_ficha()+""));
	
		hbox.appendChild(space);
		
		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n24);
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
								eliminarDatos(ficha_epidemiologia_n24);
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
	
	//Metodo para guardar la informacion //// Metodo para guardar la informacion //
	public Ficha_epidemiologia_n24 obtenerFichaEpidemiologia() {

	
				Ficha_epidemiologia_n24 ficha_epidemiologia_n24 = new Ficha_epidemiologia_n24();
				ficha_epidemiologia_n24.setCodigo_ficha(tbxCodigo_ficha.getValue());
				ficha_epidemiologia_n24.setFecha_ficha(new Timestamp(fecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n24.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n24.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n24.setCodigo_diagnostico("Z000");
				ficha_epidemiologia_n24.setNro_identificacion(admision != null ? admision
						.getNro_identificacion() : null);
			
			//	ficha_epidemiologia_n24.setCodigo();
				//ficha_epidemiologia_n24.setNro_identificacion();
				ficha_epidemiologia_n24.setNombre_del_evento(rdbNombre_del_evento.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setFecha_notificacion(new Timestamp(dtbxFecha_notificacion.getValue().getTime()));
				ficha_epidemiologia_n24.setRazon_social_de_la_upgd(tbxRazon_social_de_la_upgd.getValue());
				ficha_epidemiologia_n24.setDepartamento(tbxDepartamento.getValue());
				ficha_epidemiologia_n24.setMunicipio(tbxMunicipio.getValue());
				ficha_epidemiologia_n24.setCodigo_1(tbxCodigo_1.getValue());
				ficha_epidemiologia_n24.setSubindice(tbxSubindice.getValue());
				ficha_epidemiologia_n24.setClasificacion_inicial_del_caso(rdbClasificacion_inicial_del_caso.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setNombres_y_apellidos_del_propietario(tbxNombres_y_apellidos_del_propietario.getValue());
				ficha_epidemiologia_n24.setDireccion_de_recidencia(tbxDireccion_de_recidencia.getValue());
				ficha_epidemiologia_n24.setTelefono((ibxTelefono.getValue()!=null?ibxTelefono.getValue():0));
				ficha_epidemiologia_n24.setDepartamento_recidencia(tbxDepartamento_recidencia.getValue());
				ficha_epidemiologia_n24.setCodigo_departamento(tbxCodigo_departamento.getValue());
				ficha_epidemiologia_n24.setMunicipio_recidencia(tbxMunicipio_recidencia.getValue());
				ficha_epidemiologia_n24.setCodigo_municipio(tbxCodigo_municipio.getValue());
				ficha_epidemiologia_n24.setEspecie(rdbEspecie.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setRaza(tbxRaza.getValue());
				ficha_epidemiologia_n24.setColor_de_la_cabeza_del_animal(tbxColor_de_la_cabeza_del_animal.getValue());
				ficha_epidemiologia_n24.setEdad((ibxEdad.getValue()!=null?ibxEdad.getValue():0));
				ficha_epidemiologia_n24.setUnidad_de_medida_de_edad(rdbUnidad_de_medida_de_edad.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setAntecedente_de_vacunacion(rdbAntecedente_de_vacunacion.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setFecha_de_vacunacion(new Timestamp(dtbxFecha_de_vacunacion.getValue().getTime()));
				ficha_epidemiologia_n24.setPresento_carnet_vigente(rdbPresento_carnet_vigente.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setTipo_de_vacuna(tbxTipo_de_vacuna.getValue());
				ficha_epidemiologia_n24.setLote_de_vacuna(tbxLote_de_vacuna.getValue());
				ficha_epidemiologia_n24.setArea_de_procedencia_del_animal(rdbArea_de_procedencia_del_animal.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setNinguno(chbNinguno.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setAgrecividad(chbAgrecividad.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setParalisis_miembros_posteriores(chbParalisis_miembros_posteriores.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setSalivacion(chbSalivacion.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setApetito_alterado(chbApetito_alterado.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setVoracidad(chbVoracidad.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setDeglucion_dificultosa(chbDeglucion_dificultosa.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setLadrido_ronco(chbLadrido_ronco.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setMandibula_trabada(chbMandibula_trabada.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setAnisocoria(chbAnisocoria.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setOtro(chbOtro.isChecked()?"S":"N");
				ficha_epidemiologia_n24.setCual_otro(tbxCual_otro.getValue());
				ficha_epidemiologia_n24.setFecha_de_inicio_de_sintomas(new Timestamp(dtbxFecha_de_inicio_de_sintomas.getValue().getTime()));
				ficha_epidemiologia_n24.setTipo_de_muerte(rdbTipo_de_muerte.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setFecha_de_muerte(new Timestamp(dtbxFecha_de_muerte.getValue().getTime()));
				ficha_epidemiologia_n24.setNumero_de_personas_expuestas((ibxNumero_de_personas_expuestas.getValue()!=null?ibxNumero_de_personas_expuestas.getValue():0));
				ficha_epidemiologia_n24.setPerros_expuestos((ibxPerros_expuestos.getValue()!=null?ibxPerros_expuestos.getValue():0));
				ficha_epidemiologia_n24.setGatos_expuestos((ibxGatos_expuestos.getValue()!=null?ibxGatos_expuestos.getValue():0));
				ficha_epidemiologia_n24.setCuales_expuestos(tbxCuales_expuestos.getValue());
				ficha_epidemiologia_n24.setOtros_expuestos((ibxOtros_expuestos.getValue()!=null?ibxOtros_expuestos.getValue():0));
				ficha_epidemiologia_n24.setPerros_sintomatologia_nerviosa((ibxPerros_sintomatologia_nerviosa.getValue()!=null?ibxPerros_sintomatologia_nerviosa.getValue():0));
				ficha_epidemiologia_n24.setGatos_sintomatologia_nerviosa((ibxGatos_sintomatologia_nerviosa.getValue()!=null?ibxGatos_sintomatologia_nerviosa.getValue():0));
				ficha_epidemiologia_n24.setZorros_sintomatologia_nerviosa((ibxZorros_sintomatologia_nerviosa.getValue()!=null?ibxZorros_sintomatologia_nerviosa.getValue():0));
				ficha_epidemiologia_n24.setMurcielagos_sintomatologia_nerviosa((ibxMurcielagos_sintomatologia_nerviosa.getValue()!=null?ibxMurcielagos_sintomatologia_nerviosa.getValue():0));
				ficha_epidemiologia_n24.setOtros_sintomatologia_nerviosa(tbxOtros_sintomatologia_nerviosa.getValue());
				ficha_epidemiologia_n24.setCuales_otros_sintomatologia_nerviosa(tbxOtros_sintomatologia_nerviosa.getValue());
				ficha_epidemiologia_n24.setPerros_en_cuarentena((ibxPerros_en_cuarentena.getValue()!=null?ibxPerros_en_cuarentena.getValue():0));
				ficha_epidemiologia_n24.setGatos_en_cuarentena((ibxGatos_en_cuarentena.getValue()!=null?ibxGatos_en_cuarentena.getValue():0));
				ficha_epidemiologia_n24.setOtros_en_cuarentena((ibxOtros_en_cuarentena.getValue()!=null?ibxOtros_en_cuarentena.getValue():0));
				ficha_epidemiologia_n24.setCuales_otros_en_cuarentena(tbxCuales_otros_en_cuarentena.getValue());
				ficha_epidemiologia_n24.setPerros_eliminados((ibxPerros_eliminados.getValue()!=null?ibxPerros_eliminados.getValue():0));
				ficha_epidemiologia_n24.setGatos_eliminados((ibxGatos_eliminados.getValue()!=null?ibxGatos_eliminados.getValue():0));
				ficha_epidemiologia_n24.setZorros_eliminados((ibxZorros_eliminados.getValue()!=null?ibxZorros_eliminados.getValue():0));
				ficha_epidemiologia_n24.setMurcielagos_eliminados((ibxMurcielagos_eliminados.getValue()!=null?ibxMurcielagos_eliminados.getValue():0));
				ficha_epidemiologia_n24.setOtros_eliminados((ibxOtros_eliminados.getValue()!=null?ibxOtros_eliminados.getValue():0));
				ficha_epidemiologia_n24.setCuales_otros_eliminados(tbxCuales_otros_eliminados.getValue());
				ficha_epidemiologia_n24.setPerros_vacunados((ibxPerros_vacunados.getValue()!=null?ibxPerros_vacunados.getValue():0));
				ficha_epidemiologia_n24.setGatos_vacunados((ibxGatos_vacunados.getValue()!=null?ibxGatos_vacunados.getValue():0));
				ficha_epidemiologia_n24.setOtros_vacunados((ibxOtros_vacunados.getValue()!=null?ibxOtros_vacunados.getValue():0));
				ficha_epidemiologia_n24.setCuales_otros_vacunados(tbxCuales_otros_vacunados.getValue());
				ficha_epidemiologia_n24.setPerros_muestras_tomadas((ibxPerros_muestras_tomadas.getValue()!=null?ibxPerros_muestras_tomadas.getValue():0));
				ficha_epidemiologia_n24.setGatos_muestras_tomadas((ibxGatos_muestras_tomadas.getValue()!=null?ibxGatos_muestras_tomadas.getValue():0));
				ficha_epidemiologia_n24.setZorros_muestras_tomadas((ibxZorros_muestras_tomadas.getValue()!=null?ibxZorros_muestras_tomadas.getValue():0));
				ficha_epidemiologia_n24.setMurcielagos_muestras_tomadas((ibxMurcielagos_muestras_tomadas.getValue()!=null?ibxMurcielagos_muestras_tomadas.getValue():0));
				ficha_epidemiologia_n24.setOtros_muestras_tomadas((ibxOtros_muestras_tomadas.getValue()!=null?ibxOtros_muestras_tomadas.getValue():0));
				ficha_epidemiologia_n24.setCuales_otros_muestras_tomadas(tbxCuales_otros_muestras_tomadas.getValue());
				ficha_epidemiologia_n24.setNomre_de_quien_remite_la_muestra(tbxNomre_de_quien_remite_la_muestra.getValue());
				ficha_epidemiologia_n24.setInstitucion(tbxInstitucion.getValue());
				ficha_epidemiologia_n24.setTelefono_1((ibxTelefono_1.getValue()!=null?ibxTelefono_1.getValue():0));
				ficha_epidemiologia_n24.setCargo(tbxCargo.getValue());
				ficha_epidemiologia_n24.setFecha_de_toma_de_muestra(new Timestamp(dtbxFecha_de_toma_de_muestra.getValue().getTime()));
				ficha_epidemiologia_n24.setFecha_de_remision_de_muestra(new Timestamp(dtbxFecha_de_remision_de_muestra.getValue().getTime()));
				ficha_epidemiologia_n24.setDestino_de_muestra(rdbDestino_de_muestra.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setOtro_destino(tbxOtro_destino.getValue());
				ficha_epidemiologia_n24.setPrueba_diagnostica_confirmatoria(rdbPrueba_diagnostica_confirmatoria.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setResultado(rdbResultado.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setIdentificacion_variante(rdbIdentificacion_variante.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setVariante_identificada(rdbVariante_identificada.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n24.setOtra_variante(tbxOtra_variante.getValue());
				ficha_epidemiologia_n24.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n24.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				//ficha_epidemiologia_n24.setDelete_date();
				ficha_epidemiologia_n24.setUltimo_user(usuarios.getCodigo().toString());
				//ficha_epidemiologia_n24.setDelete_use();
				ficha_epidemiologia_n24.setCreacion_user(usuarios.getCodigo().toString());
				
				return ficha_epidemiologia_n24;
				
	}
	
	//Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n24 obj)throws Exception{
		Ficha_epidemiologia_n24 ficha_epidemiologia_n24 = (Ficha_epidemiologia_n24)obj;
		try{
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n24.getCodigo_ficha());
			fecha_ficha.setValue(ficha_epidemiologia_n24.getFecha_ficha());
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			
			Utilidades.seleccionarRadio(rdbNombre_del_evento, ficha_epidemiologia_n24.getNombre_del_evento());
			dtbxFecha_notificacion.setValue(ficha_epidemiologia_n24.getFecha_notificacion());
			tbxRazon_social_de_la_upgd.setValue(ficha_epidemiologia_n24.getRazon_social_de_la_upgd());
			tbxDepartamento.setValue(ficha_epidemiologia_n24.getDepartamento());
			tbxMunicipio.setValue(ficha_epidemiologia_n24.getMunicipio());
			tbxCodigo_1.setValue(ficha_epidemiologia_n24.getCodigo_1());
			tbxSubindice.setValue(ficha_epidemiologia_n24.getSubindice());
			Utilidades.seleccionarRadio(rdbClasificacion_inicial_del_caso, ficha_epidemiologia_n24.getClasificacion_inicial_del_caso());
			tbxNombres_y_apellidos_del_propietario.setValue(ficha_epidemiologia_n24.getNombres_y_apellidos_del_propietario());
			tbxDireccion_de_recidencia.setValue(ficha_epidemiologia_n24.getDireccion_de_recidencia());
			ibxTelefono.setValue(ficha_epidemiologia_n24.getTelefono());
			tbxDepartamento_recidencia.setValue(ficha_epidemiologia_n24.getDepartamento_recidencia());
			tbxCodigo_departamento.setValue(ficha_epidemiologia_n24.getCodigo_departamento());
			tbxMunicipio_recidencia.setValue(ficha_epidemiologia_n24.getMunicipio_recidencia());
			tbxCodigo_municipio.setValue(ficha_epidemiologia_n24.getCodigo_municipio());
			Utilidades.seleccionarRadio(rdbEspecie, ficha_epidemiologia_n24.getEspecie());
			tbxRaza.setValue(ficha_epidemiologia_n24.getRaza());
			tbxColor_de_la_cabeza_del_animal.setValue(ficha_epidemiologia_n24.getColor_de_la_cabeza_del_animal());
			ibxEdad.setValue(ficha_epidemiologia_n24.getEdad());
			Utilidades.seleccionarRadio(rdbUnidad_de_medida_de_edad, ficha_epidemiologia_n24.getUnidad_de_medida_de_edad());
			Utilidades.seleccionarRadio(rdbAntecedente_de_vacunacion, ficha_epidemiologia_n24.getAntecedente_de_vacunacion());
			dtbxFecha_de_vacunacion.setValue(ficha_epidemiologia_n24.getFecha_de_vacunacion());
			Utilidades.seleccionarRadio(rdbPresento_carnet_vigente, ficha_epidemiologia_n24.getPresento_carnet_vigente());
			tbxTipo_de_vacuna.setValue(ficha_epidemiologia_n24.getTipo_de_vacuna());
			tbxLote_de_vacuna.setValue(ficha_epidemiologia_n24.getLote_de_vacuna());
			Utilidades.seleccionarRadio(rdbArea_de_procedencia_del_animal, ficha_epidemiologia_n24.getArea_de_procedencia_del_animal());
			chbNinguno.setChecked(ficha_epidemiologia_n24.getNinguno().equals("S")?true:false);
			chbAgrecividad.setChecked(ficha_epidemiologia_n24.getAgrecividad().equals("S")?true:false);
			chbParalisis_miembros_posteriores.setChecked(ficha_epidemiologia_n24.getParalisis_miembros_posteriores().equals("S")?true:false);
			chbSalivacion.setChecked(ficha_epidemiologia_n24.getSalivacion().equals("S")?true:false);
			chbApetito_alterado.setChecked(ficha_epidemiologia_n24.getApetito_alterado().equals("S")?true:false);
			chbVoracidad.setChecked(ficha_epidemiologia_n24.getVoracidad().equals("S")?true:false);
			chbDeglucion_dificultosa.setChecked(ficha_epidemiologia_n24.getDeglucion_dificultosa().equals("S")?true:false);
			chbLadrido_ronco.setChecked(ficha_epidemiologia_n24.getLadrido_ronco().equals("S")?true:false);
			chbMandibula_trabada.setChecked(ficha_epidemiologia_n24.getMandibula_trabada().equals("S")?true:false);
			chbAnisocoria.setChecked(ficha_epidemiologia_n24.getAnisocoria().equals("S")?true:false);
			chbOtro.setChecked(ficha_epidemiologia_n24.getOtro().equals("S")?true:false);
			tbxCual_otro.setValue(ficha_epidemiologia_n24.getCual_otro());
			dtbxFecha_de_inicio_de_sintomas.setValue(ficha_epidemiologia_n24.getFecha_de_inicio_de_sintomas());
			Utilidades.seleccionarRadio(rdbTipo_de_muerte, ficha_epidemiologia_n24.getTipo_de_muerte());
			dtbxFecha_de_muerte.setValue(ficha_epidemiologia_n24.getFecha_de_muerte());
			ibxNumero_de_personas_expuestas.setValue(ficha_epidemiologia_n24.getNumero_de_personas_expuestas());
			ibxPerros_expuestos.setValue(ficha_epidemiologia_n24.getPerros_expuestos());
			ibxGatos_expuestos.setValue(ficha_epidemiologia_n24.getGatos_expuestos());
			tbxCuales_expuestos.setValue(ficha_epidemiologia_n24.getCuales_expuestos());
			ibxOtros_expuestos.setValue(ficha_epidemiologia_n24.getOtros_expuestos());
			ibxPerros_sintomatologia_nerviosa.setValue(ficha_epidemiologia_n24.getPerros_sintomatologia_nerviosa());
			ibxGatos_sintomatologia_nerviosa.setValue(ficha_epidemiologia_n24.getGatos_sintomatologia_nerviosa());
			ibxZorros_sintomatologia_nerviosa.setValue(ficha_epidemiologia_n24.getZorros_sintomatologia_nerviosa());
			ibxMurcielagos_sintomatologia_nerviosa.setValue(ficha_epidemiologia_n24.getMurcielagos_sintomatologia_nerviosa());
			tbxOtros_sintomatologia_nerviosa.setValue(ficha_epidemiologia_n24.getOtros_sintomatologia_nerviosa());
			tbxCuales_sintomatologia_nerviosa.setValue(ficha_epidemiologia_n24.getCuales_otros_sintomatologia_nerviosa());
			ibxPerros_en_cuarentena.setValue(ficha_epidemiologia_n24.getPerros_en_cuarentena());
			ibxGatos_en_cuarentena.setValue(ficha_epidemiologia_n24.getGatos_en_cuarentena());
			ibxOtros_en_cuarentena.setValue(ficha_epidemiologia_n24.getOtros_en_cuarentena());
			tbxCuales_otros_en_cuarentena.setValue(ficha_epidemiologia_n24.getCuales_otros_en_cuarentena());
			ibxPerros_eliminados.setValue(ficha_epidemiologia_n24.getPerros_eliminados());
			ibxGatos_eliminados.setValue(ficha_epidemiologia_n24.getGatos_eliminados());
			ibxZorros_eliminados.setValue(ficha_epidemiologia_n24.getZorros_eliminados());
			ibxMurcielagos_eliminados.setValue(ficha_epidemiologia_n24.getMurcielagos_eliminados());
			ibxOtros_eliminados.setValue(ficha_epidemiologia_n24.getOtros_eliminados());
			tbxCuales_otros_eliminados.setValue(ficha_epidemiologia_n24.getCuales_otros_eliminados());
			ibxPerros_vacunados.setValue(ficha_epidemiologia_n24.getPerros_vacunados());
			ibxGatos_vacunados.setValue(ficha_epidemiologia_n24.getGatos_vacunados());
			ibxOtros_vacunados.setValue(ficha_epidemiologia_n24.getOtros_vacunados());
			tbxCuales_otros_vacunados.setValue(ficha_epidemiologia_n24.getCuales_otros_vacunados());
			ibxPerros_muestras_tomadas.setValue(ficha_epidemiologia_n24.getPerros_muestras_tomadas());
			ibxGatos_muestras_tomadas.setValue(ficha_epidemiologia_n24.getGatos_muestras_tomadas());
			ibxZorros_muestras_tomadas.setValue(ficha_epidemiologia_n24.getZorros_muestras_tomadas());
			ibxMurcielagos_muestras_tomadas.setValue(ficha_epidemiologia_n24.getMurcielagos_muestras_tomadas());
			ibxOtros_muestras_tomadas.setValue(ficha_epidemiologia_n24.getOtros_muestras_tomadas());
			tbxCuales_otros_muestras_tomadas.setValue(ficha_epidemiologia_n24.getCuales_otros_muestras_tomadas());
			tbxNomre_de_quien_remite_la_muestra.setValue(ficha_epidemiologia_n24.getNomre_de_quien_remite_la_muestra());
			tbxInstitucion.setValue(ficha_epidemiologia_n24.getInstitucion());
			ibxTelefono_1.setValue(ficha_epidemiologia_n24.getTelefono_1());
			tbxCargo.setValue(ficha_epidemiologia_n24.getCargo());
			dtbxFecha_de_toma_de_muestra.setValue(ficha_epidemiologia_n24.getFecha_de_toma_de_muestra());
			dtbxFecha_de_remision_de_muestra.setValue(ficha_epidemiologia_n24.getFecha_de_remision_de_muestra());
			Utilidades.seleccionarRadio(rdbDestino_de_muestra, ficha_epidemiologia_n24.getDestino_de_muestra());
			tbxOtro_destino.setValue(ficha_epidemiologia_n24.getOtro_destino());
			Utilidades.seleccionarRadio(rdbPrueba_diagnostica_confirmatoria, ficha_epidemiologia_n24.getPrueba_diagnostica_confirmatoria());
			Utilidades.seleccionarRadio(rdbResultado, ficha_epidemiologia_n24.getResultado());
			Utilidades.seleccionarRadio(rdbIdentificacion_variante, ficha_epidemiologia_n24.getIdentificacion_variante());
			Utilidades.seleccionarRadio(rdbVariante_identificada, ficha_epidemiologia_n24.getVariante_identificada());
			tbxOtra_variante.setValue(ficha_epidemiologia_n24.getOtra_variante());
			
			//Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void eliminarDatos(Object obj)throws Exception{
		Ficha_epidemiologia_n24 ficha_epidemiologia_n24 = (Ficha_epidemiologia_n24)obj;
		try{
			int result = getServiceLocator().getFicha_epidemiologia_nnService().eliminar(ficha_epidemiologia_n24);
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
	public Ficha_epidemiologia_n24 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n24> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n24.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n24 ficha_n24 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n24;
				}else{

					return null;
				}
	}
	
}