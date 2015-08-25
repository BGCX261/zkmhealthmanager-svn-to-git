/*
 * ficha_epidemiologia_n3Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n3;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.North;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IFichas_epidemiologicas;
import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n3Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n3>{

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n3Action.class);

	// Componentes //
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
	private Textbox tbxCodigo_ficha;
	@View 
	private Datebox dtbxFecha_ficha;
	@View 
	private Textbox tbxNro_identificacion;
	@View 
	private Textbox tbx_tipo_identificacion;
	@View 
	private Textbox tbxNombres;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View
	private Radiogroup rdbConoce_y_o_ha_sido_picado_por_pito;
	@View
	private Radiogroup rdbTransfuciones_sanguineas;
	@View
	private Radiogroup rdbSometido_transplante;
	@View
	private Radiogroup rdbHijo_de_madre_cero_positiva;
	@View
	private Radiogroup rdbEmbarazo_actual;
	@View
	private Radiogroup rdbHa_sido_donante;
	@View
	private Radiogroup rdbTipo_techo;
	@View
	private Radiogroup rdbTipo_paredes;
	@View
	private Radiogroup rdbEstrato_socio_economico;
	@View
	private Radiogroup rdbEstado_clinico;
	@View
	private Radiogroup rdbClasificacion_de_caso;
	@View
	private Checkbox chbFiebre;
	@View
	private Checkbox chbDolor_toracico_cronico;
	@View
	private Checkbox chbDisnea;
	@View
	private Checkbox chbPalpitaciones;
	@View
	private Checkbox chbMialgias;
	@View
	private Checkbox chbArtralgias;
	@View
	private Checkbox chbEdema_facial;
	@View
	private Checkbox chbEdema_miembros_inferiores;
	@View
	private Checkbox chbDerrame_pericardico;
	@View
	private Checkbox chbHepatoesplenomegalia;
	@View
	private Checkbox chbAdenopatias;
	@View
	private Checkbox chbChagoma;
	@View
	private Checkbox chbFalla_cardiaca;
	@View
	private Checkbox chbPalpitacion_taquicardia;
	@View
	private Checkbox chbDolor_toracico_agudo;
	@View
	private Checkbox chbBrandicardia;
	@View
	private Checkbox chbSincope_o_presincope;
	@View
	private Checkbox chbHipotension;
	@View
	private Checkbox chbDisfagia;
	@View
	private Radiogroup rdbGota_gruesa_frotis_de_sangre_periferica;
	@View
	private Radiogroup rdbMicrohematocrito_examen_fresco;
	@View
	private Radiogroup rdbStrout;
	@View
	private Radiogroup rdbElisa_igg_chagas;
	@View
	private Radiogroup rdbIfi_igg_chagas;
	@View
	private Radiogroup rdbHai_chagas;
	@View
	private Radiogroup rdbElectrocardiograma;
	@View
	private Radiogroup rdbEcocardiograma;
	@View
	private Radiogroup rdbRayos_x_de_torax_indice_toracico;
	@View
	private Radiogroup rdbHolter;
	@View
	private Radiogroup rdbTratamiento_etiologico;
	@View
	private Radiogroup rdbTratamiento_sintomatico;
	@View
	private Radiogroup rdbPosible_via_transmision;
	@View
	private Checkbox chbRomana;
	// @View private Textbox tbxCodigo;
	@View
	private Textbox tbxResultado1;
	@View
	private Textbox tbxResultado2;
	@View
	private Intbox ibxSemanas_de_embarazo;
	@View
	private Intbox ibxNumero_familiares_con_changa;
	@View
	private Checkbox chbConstipacion_cronica;
	@View
	private Row row1;
    @View
	private Toolbarbutton btGuardar;

	@View private Textbox tbxotro_tipo_techo;
	@View
	private Row row2;
	@View
	private Row row3;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;

	@View private Toolbarbutton btImprimir;
	@View private North north_ficha;
	
	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);
		FormularioUtil.inicializarRadiogroupsDefecto(this);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n3 ficha = new Ficha_epidemiologia_n3();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n3) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 3-------> "+ficha);
			
			mostrarDatos(ficha);

			north_ficha.setVisible(true);
			btImprimir.setVisible(true);
		}else {

			north_ficha.setVisible(false);
			btImprimir.setVisible(false);
			
		}
	}

	public void listarCombos() {
		listarParameter();
		
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
		}
	}

	public void seleccionarRadio(Radiogroup radio1) {
		if (radio1.getSelectedItem().getValue().equals("1")) {
			row1.setVisible(true);

		} else {
			row1.setVisible(false);
			

		}

	}
	public void selccionarRadio2(Radiogroup radio2){
		if (radio2.getSelectedItem().getValue().equals("1")){
			row2.setVisible(true);			
		}else{
			row2.setVisible(false);
		}
		
	}
	public void selccionarRadio3(Radiogroup radio3){
		if (radio3.getSelectedItem().getValue().equals("2")){
			row3.setVisible(true);			
		}else{
			row3.setVisible(false);
			 tbxotro_tipo_techo.setText("");
		}
		
	}
	
	

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Nro_identificacion");
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
			btGuardar.setVisible(false);
		} else {
			btGuardar.setVisible(true);
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}
	
	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}
	// Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia() {

		dtbxFecha_ficha.setStyle("background-color:white");
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:white");
		tbxNro_identificacion.setStyle("background-color:white");

		boolean valida = true;

		if (dtbxFecha_ficha.getText().trim().equals("")) {
			dtbxFecha_ficha.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		// if(tbxNombres_y_apellidos.getText().trim().equals("")){
		// tbxNombres_y_apellidos.setStyle("text-transform:uppercase;background-color:#F6BBBE");
		// valida = false;
		// }
		if (tbxNro_identificacion.getText().trim().equals("")) {
			tbxNro_identificacion.setStyle("background-color:#F6BBBE");
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
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (parameter.equalsIgnoreCase("fecha_inicial")) {
				parameters.put("fecha_string", value);
			} else {
				parameters.put("parameter", parameter);
				parameters.put("value", "%" + value + "%");
			}
			if (admision != null) {
				parameters.put("identificacion",
						admision.getNro_identificacion());
			}

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
					"limit 25 offset 0");

			List<Ficha_epidemiologia_n3> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(
							Ficha_epidemiologia_n3.class, parameters);
			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n3 ficha_epidemiologia_n3 : lista_datos) {
				rowsResultado.appendChild(crearFilas(
						ficha_epidemiologia_n3, this));
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

		final Ficha_epidemiologia_n3 ficha_epidemiologia_n3 = (Ficha_epidemiologia_n3) objeto;

//		Paciente registro = new Paciente();
//		registro.setCodigo_empresa(empresa.getCodigo_empresa());
//		registro.setCodigo_sucursal(sucursal.getCodigo_sucursal());
//		registro.setNro_identificacion(ficha_epidemiologia_n3
//				.getCodigo_ficha());
//		registro = getServiceLocator().getPacienteService().consultar(registro);
//
		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(ficha_epidemiologia_n3.getCodigo_ficha() + ""));
	
		
//		fila.appendChild(new Label(registro != null ? registro
//				.getNombreCompleto() : ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n3);
				
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
									eliminarDatos(ficha_epidemiologia_n3);
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
	// Metodo para guardar la informacion //
		public Ficha_epidemiologia_n3 obtenerFichaEpidemiologia() {
		
				Ficha_epidemiologia_n3 ficha_epidemiologia_n3 = new Ficha_epidemiologia_n3();
				ficha_epidemiologia_n3.setCodigo_empresa(empresa.getCodigo_empresa());
				ficha_epidemiologia_n3.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				ficha_epidemiologia_n3.setCodigo("Z000");
				ficha_epidemiologia_n3.setCodigo_ficha(tbxCodigo_ficha
						.getValue());
				ficha_epidemiologia_n3.setFecha_ficha(new Timestamp(dtbxFecha_ficha.getValue().getTime()));
				ficha_epidemiologia_n3.setNro_identificacion(tbxNro_identificacion.getValue());
			
				//ficha_epidemiologia_n3
					//	.setNro_identificacion(tbxNro_identificacion.getValue());
				ficha_epidemiologia_n3
						.setConoce_y_o_ha_sido_picado_por_pito(rdbConoce_y_o_ha_sido_picado_por_pito
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setTransfuciones_sanguineas(rdbTransfuciones_sanguineas
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setSometido_transplante(rdbSometido_transplante
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setHijo_de_madre_cero_positiva(rdbHijo_de_madre_cero_positiva
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setEmbarazo_actual(rdbEmbarazo_actual
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setHa_sido_donante(rdbHa_sido_donante
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setTipo_techo(rdbTipo_techo
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setTipo_paredes(rdbTipo_paredes
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setEstrato_socio_economico(rdbEstrato_socio_economico
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setEstado_clinico(rdbEstado_clinico
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setClasificacion_de_caso(rdbClasificacion_de_caso
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setFiebre(chbFiebre.isChecked() ? "S"
						: "N");
				ficha_epidemiologia_n3
						.setDolor_toracico_cronico(chbDolor_toracico_cronico
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3.setDisnea(chbDisnea.isChecked() ? "S"
						: "N");
				ficha_epidemiologia_n3.setPalpitaciones(chbPalpitaciones
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setMialgias(chbMialgias.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setArtralgias(chbArtralgias.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3.setEdema_facial(chbEdema_facial
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setEdema_miembros_inferiores(chbEdema_miembros_inferiores
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setDerrame_pericardico(chbDerrame_pericardico
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setHepatoesplenomegalia(chbHepatoesplenomegalia
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3.setAdenopatias(chbAdenopatias
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3.setChagoma(chbChagoma.isChecked() ? "S"
						: "N");
				ficha_epidemiologia_n3.setFalla_cardiaca(chbFalla_cardiaca
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setPalpitacion_taquicardia(chbPalpitacion_taquicardia
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setDolor_toracico_agudo(chbDolor_toracico_agudo
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3.setBrandicardia(chbBrandicardia
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setSincope_o_presincope(chbSincope_o_presincope
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3.setHipotension(chbHipotension
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setDisfagia(chbDisfagia.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3
						.setGota_gruesa_frotis_de_sangre_periferica(rdbGota_gruesa_frotis_de_sangre_periferica
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setMicrohematocrito_examen_fresco(rdbMicrohematocrito_examen_fresco
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setStrout(rdbStrout.getSelectedItem()
						.getValue().toString());
				ficha_epidemiologia_n3.setElisa_igg_chagas(rdbElisa_igg_chagas
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setIfi_igg_chagas(rdbIfi_igg_chagas
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setHai_chagas(rdbHai_chagas
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setElectrocardiograma(rdbElectrocardiograma
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setEcocardiograma(rdbEcocardiograma
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setRayos_x_de_torax_indice_toracico(rdbRayos_x_de_torax_indice_toracico
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setHolter(rdbHolter.getSelectedItem()
						.getValue().toString());
				ficha_epidemiologia_n3
						.setTratamiento_etiologico(rdbTratamiento_etiologico
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setTratamiento_sintomatico(rdbTratamiento_sintomatico
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3
						.setPosible_via_transmision(rdbPosible_via_transmision
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n3.setRomana(chbRomana.isChecked() ? "S"
						: "N");
				
				ficha_epidemiologia_n3.setCodigo_empresa(empresa
						.getCodigo_empresa());
				
				ficha_epidemiologia_n3.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				// ficha_epidemiologia_n3.setCodigo(tbxCodigo.getValue());
				ficha_epidemiologia_n3.setResultado1(tbxResultado1.getValue());
				ficha_epidemiologia_n3.setResultado2(tbxResultado2.getValue());
				ficha_epidemiologia_n3
						.setSemanas_de_embarazo((ibxSemanas_de_embarazo
								.getValue() != null ? ibxSemanas_de_embarazo
								.getValue() : 0));
				ficha_epidemiologia_n3
						.setNumero_familiares_con_changa((ibxNumero_familiares_con_changa
								.getValue() != null ? ibxNumero_familiares_con_changa
								.getValue() : 0));
				ficha_epidemiologia_n3
						.setConstipacion_cronica(chbConstipacion_cronica
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n3.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n3.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n3.setCreacion_user(usuarios.getCodigo()
						.toString());
				ficha_epidemiologia_n3.setDelete_date(null);
				ficha_epidemiologia_n3.setUltimo_user(usuarios.getCodigo()
						.toString());
				ficha_epidemiologia_n3.setDelete_user(null);
				ficha_epidemiologia_n3.setOtro_tipo_techo(tbxotro_tipo_techo.getValue());

				
		return ficha_epidemiologia_n3;
		}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n3 obj) throws Exception {
		Ficha_epidemiologia_n3 ficha_epidemiologia_n3 = (Ficha_epidemiologia_n3) obj;
		try {
			
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n3.getCodigo_ficha());
			dtbxFecha_ficha.setValue(ficha_epidemiologia_n3.getFecha_ficha());
 
  			tbxNro_identificacion.setValue(ficha_epidemiologia_n3.getNro_identificacion());

			 obtenerAdmision(admision);

				FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
				
				
			Utilidades.seleccionarRadio(rdbConoce_y_o_ha_sido_picado_por_pito,
					ficha_epidemiologia_n3
							.getConoce_y_o_ha_sido_picado_por_pito());
			Utilidades.seleccionarRadio(rdbTransfuciones_sanguineas,
					ficha_epidemiologia_n3.getTransfuciones_sanguineas());
			Utilidades.seleccionarRadio(rdbSometido_transplante,
					ficha_epidemiologia_n3.getSometido_transplante());
			Utilidades.seleccionarRadio(rdbHijo_de_madre_cero_positiva,
					ficha_epidemiologia_n3.getHijo_de_madre_cero_positiva());
			Utilidades.seleccionarRadio(rdbEmbarazo_actual,
					ficha_epidemiologia_n3.getEmbarazo_actual());
			Utilidades.seleccionarRadio(rdbHa_sido_donante,
					ficha_epidemiologia_n3.getHa_sido_donante());
			Utilidades.seleccionarRadio(rdbTipo_techo,
					ficha_epidemiologia_n3.getTipo_techo());
			Utilidades.seleccionarRadio(rdbTipo_paredes,
					ficha_epidemiologia_n3.getTipo_paredes());
			Utilidades.seleccionarRadio(rdbEstrato_socio_economico,
					ficha_epidemiologia_n3.getEstrato_socio_economico());
			Utilidades.seleccionarRadio(rdbEstado_clinico,
					ficha_epidemiologia_n3.getEstado_clinico());
			Utilidades.seleccionarRadio(rdbClasificacion_de_caso,
					ficha_epidemiologia_n3.getClasificacion_de_caso());
			chbFiebre
					.setChecked(ficha_epidemiologia_n3.getFiebre().equals("S") ? true
							: false);
			chbDolor_toracico_cronico.setChecked(ficha_epidemiologia_n3
					.getDolor_toracico_cronico().equals("S") ? true : false);
			chbDisnea
					.setChecked(ficha_epidemiologia_n3.getDisnea().equals("S") ? true
							: false);
			chbPalpitaciones.setChecked(ficha_epidemiologia_n3
					.getPalpitaciones().equals("S") ? true : false);
			chbMialgias.setChecked(ficha_epidemiologia_n3.getMialgias().equals(
					"S") ? true : false);
			chbArtralgias.setChecked(ficha_epidemiologia_n3.getArtralgias()
					.equals("S") ? true : false);
			chbEdema_facial.setChecked(ficha_epidemiologia_n3.getEdema_facial()
					.equals("S") ? true : false);
			chbEdema_miembros_inferiores.setChecked(ficha_epidemiologia_n3
					.getEdema_miembros_inferiores().equals("S") ? true : false);
			chbDerrame_pericardico.setChecked(ficha_epidemiologia_n3
					.getDerrame_pericardico().equals("S") ? true : false);
			chbHepatoesplenomegalia.setChecked(ficha_epidemiologia_n3
					.getHepatoesplenomegalia().equals("S") ? true : false);
			chbAdenopatias.setChecked(ficha_epidemiologia_n3.getAdenopatias()
					.equals("S") ? true : false);
			chbChagoma.setChecked(ficha_epidemiologia_n3.getChagoma().equals(
					"S") ? true : false);
			chbFalla_cardiaca.setChecked(ficha_epidemiologia_n3
					.getFalla_cardiaca().equals("S") ? true : false);
			chbPalpitacion_taquicardia.setChecked(ficha_epidemiologia_n3
					.getPalpitacion_taquicardia().equals("S") ? true : false);
			chbDolor_toracico_agudo.setChecked(ficha_epidemiologia_n3
					.getDolor_toracico_agudo().equals("S") ? true : false);
			chbBrandicardia.setChecked(ficha_epidemiologia_n3.getBrandicardia()
					.equals("S") ? true : false);
			chbSincope_o_presincope.setChecked(ficha_epidemiologia_n3
					.getSincope_o_presincope().equals("S") ? true : false);
			chbHipotension.setChecked(ficha_epidemiologia_n3.getHipotension()
					.equals("S") ? true : false);
			chbDisfagia.setChecked(ficha_epidemiologia_n3.getDisfagia().equals(
					"S") ? true : false);
		
			Utilidades.seleccionarRadio(rdbMicrohematocrito_examen_fresco,
					ficha_epidemiologia_n3.getMicrohematocrito_examen_fresco());
			Utilidades.seleccionarRadio(rdbStrout,
					ficha_epidemiologia_n3.getStrout());
			Utilidades.seleccionarRadio(rdbElisa_igg_chagas,
					ficha_epidemiologia_n3.getElisa_igg_chagas());
			Utilidades.seleccionarRadio(rdbIfi_igg_chagas,
					ficha_epidemiologia_n3.getIfi_igg_chagas());
			Utilidades.seleccionarRadio(rdbHai_chagas,
					ficha_epidemiologia_n3.getHai_chagas());
			Utilidades.seleccionarRadio(rdbElectrocardiograma,
					ficha_epidemiologia_n3.getElectrocardiograma());
			Utilidades.seleccionarRadio(rdbEcocardiograma,
					ficha_epidemiologia_n3.getEcocardiograma());
			Utilidades.seleccionarRadio(rdbRayos_x_de_torax_indice_toracico,
					ficha_epidemiologia_n3
							.getRayos_x_de_torax_indice_toracico());
			Utilidades.seleccionarRadio(rdbHolter,
					ficha_epidemiologia_n3.getHolter());
			Utilidades.seleccionarRadio(rdbTratamiento_etiologico,
					ficha_epidemiologia_n3.getTratamiento_etiologico());
			Utilidades.seleccionarRadio(rdbTratamiento_sintomatico,
					ficha_epidemiologia_n3.getTratamiento_sintomatico());
			Utilidades.seleccionarRadio(rdbPosible_via_transmision,
					ficha_epidemiologia_n3.getPosible_via_transmision());
			chbRomana
					.setChecked(ficha_epidemiologia_n3.getRomana().equals("S") ? true
							: false);
			// tbxCodigo.setValue(ficha_epidemiologia_n3.getCodigo());
			tbxResultado1.setValue(ficha_epidemiologia_n3.getResultado1());
			tbxResultado2.setValue(ficha_epidemiologia_n3.getResultado2());
			ibxSemanas_de_embarazo.setValue(ficha_epidemiologia_n3
					.getSemanas_de_embarazo());
			ibxNumero_familiares_con_changa.setValue(ficha_epidemiologia_n3
					.getNumero_familiares_con_changa());
			chbConstipacion_cronica.setChecked(ficha_epidemiologia_n3
					.getConstipacion_cronica().equals("S") ? true : false);

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n3 ficha_epidemiologia_n3 = (Ficha_epidemiologia_n3) obj;
		try {
			int result = getServiceLocator().getFicha_epidemiologia_nnService()
					.eliminar(ficha_epidemiologia_n3);
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
	public void obtenerAdmision(Admision admision){
		//log.info("admision"+admision);
		Paciente paciente = admision.getPaciente();
		tbxNro_identificacion.setValue(admision.getNro_identificacion());
		tbxNombres.setValue(paciente.getNombreCompleto());
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
	public Ficha_epidemiologia_n3 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n3> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n3.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n3 ficha_n3 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n3;
				}else{

					return null;
				}
	}
	

	public void imprimir() throws Exception {
		
		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Ficha_epidemiologia");
		paramRequest.put("empresa", empresa.getCodigo_empresa());
		paramRequest.put("sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("codigo_ficha", tbxCodigo_ficha.getValue());
		paramRequest.put("ficha_epidemiologia", IFichas_epidemiologicas.ENFERMEDAD_CHAGAS);

		//log.info("codigo_ficha"+tbxCodigo_ficha.getValue());
		
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}
	
	
}