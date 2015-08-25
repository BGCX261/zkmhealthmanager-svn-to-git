/*
 * ficha_epidemiologia_n31Action.java
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
import healthmanager.modelo.bean.Ficha_epidemiologia_n31;
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
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
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

public class Ficha_epidemiologia_n31Action extends FichaEpidemiologiaModel<Ficha_epidemiologia_n31> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n31Action.class);

	// Componentes //
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad_p;
	@View
	private Textbox tbxIdentificacion_p;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
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
	private Textbox tbxPrimer_nombre_madre;
	@View
	private Textbox tbxSegundo_nombre_madre;
	@View
	private Textbox tbxPrimer_apellido_madre;
	@View
	private Textbox tbxSegundo_apellido_madre;
	@View
	private Listbox lbxTipo_identidad_madre;
	@View
	private Intbox ibxNumero_identidad_madre;
	@View
	private Listbox lbxNivel_educativo_madre;
	@View
	private Listbox lbxEstrato_socioeconomico;
	@View
	private Intbox ibxNum_men5_hogar;
	@View
	private Listbox lbxSitio_defuncion;
	@View
	private Doublebox dbxPeso_al_nacer;
	@View
	private Doublebox dbxTalla_al_nacer;
	@View
	private Intbox ibxTiempo_lactancia_materna;
	@View
	private Intbox ibxEdad_inici_alimentacion;
	@View
	private Radiogroup rdbInscrito_crecimiento;
	@View
	private Doublebox dbxPeso_actual;
	@View
	private Doublebox dbxTalla_longitud;
	@View
	private Radiogroup rdbEsquema_vacunacion;
	@View
	private Radiogroup rdbReferido_carnet;
	@View
	private Listbox lbxClasificacion_peso_edad;
	@View
	private Listbox lbxClasificacion_talla_edad;
	@View
	private Listbox lbxClasificacion_peso_talla;
	@View
	private Checkbox chbEdema;
	@View
	private Checkbox chbEmaciacion_delgades;
	@View
	private Checkbox chbPiel_reseca;
	@View
	private Checkbox chbHipo_hiperpigmentacion;
	@View
	private Checkbox chbLesiones_cabello;
	@View
	private Checkbox chbAnemia;
	@View
	private Textbox tbxCausas_directa_muerte;
	@View
	private Textbox tbxCodigo_causa_defuncion;
	@View
	private Radiogroup rdbCausa_muerte_determinada_por;
	@View
	private Textbox tbxCausas_antecententes_b;
	@View
	private Textbox tbxCausas_antecententes_c;
	@View
	private Textbox tbxCausas_antecententes_d;
	@View
	private Textbox tbxOtros_estados;
	@View
	private Radiogroup rdbDesnutricion_causa_basica;
	@View
	private Textbox tbxCodigo_des_cbas;
	@View
	private Radiogroup rdbDesnutricion_causa_asociada;
	@View
	private Textbox tbxCodigo_des_caso;
	@View
	private Radiogroup rdbDemora_1;
	@View
	private Radiogroup rdbDemora_2;
	@View
	private Radiogroup rdbDemora_3;
	@View
	private Radiogroup rdbDemora_4;
	private final String[] idsExcluyentes = {};
	private Admision admision;
	private Ficha_epidemiologia_historial ficha_seleccionada;

	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n31 ficha = new Ficha_epidemiologia_n31();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n31) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 31-------> "+ficha);
			
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
		Utilidades.listarElementoListbox(lbxTipo_identidad_madre, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxNivel_educativo_madre, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxEstrato_socioeconomico, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxSitio_defuncion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxClasificacion_peso_edad, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxClasificacion_talla_edad, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxClasificacion_peso_talla, true,
				getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
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
	}

	// Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia() {

		boolean valida = true;

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

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");

			List<Ficha_epidemiologia_n31> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(Ficha_epidemiologia_n31.class, parameters);
			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n31 ficha_epidemiologia_n31 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n31,
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

		final Ficha_epidemiologia_n31 ficha_epidemiologia_n31 = (Ficha_epidemiologia_n31) objeto;

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
				mostrarDatos(ficha_epidemiologia_n31);
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
									eliminarDatos(ficha_epidemiologia_n31);
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
	public Ficha_epidemiologia_n31 obtenerFichaEpidemiologia() {
	
				// Cargamos los componentes //

				Ficha_epidemiologia_n31 ficha_epidemiologia_n31 = new Ficha_epidemiologia_n31();
				ficha_epidemiologia_n31.setFecha_inicial(new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				ficha_epidemiologia_n31.setCodigo_empresa(empresa
						.getCodigo_empresa());
				ficha_epidemiologia_n31.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				ficha_epidemiologia_n31.setCodigo_ficha(tbxCodigo_ficha
						.getValue());
				ficha_epidemiologia_n31.setIdentificacion(tbxIdentificacion_p.getValue());
				ficha_epidemiologia_n31.setCodigo_diagnostico("A044");
				ficha_epidemiologia_n31.setPrimer_nombre_paciente("");
				ficha_epidemiologia_n31.setSegundo_nombre_paciente("");
				ficha_epidemiologia_n31.setPrimer_apellido_paciente("");
				ficha_epidemiologia_n31.setSegundo_apellido_paciente("");
				ficha_epidemiologia_n31.setTipo_identidad("");
				ficha_epidemiologia_n31.setNumero_identidad("");
				ficha_epidemiologia_n31
						.setPrimer_nombre_madre(tbxPrimer_nombre_madre
								.getValue());
				ficha_epidemiologia_n31
						.setSegundo_nombre_madre(tbxSegundo_nombre_madre
								.getValue());
				ficha_epidemiologia_n31
						.setPrimer_apellido_madre(tbxPrimer_apellido_madre
								.getValue());
				ficha_epidemiologia_n31
						.setSegundo_apellido_madre(tbxSegundo_apellido_madre
								.getValue());
				ficha_epidemiologia_n31
						.setTipo_identidad_madre(lbxTipo_identidad_madre
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31
						.setNumero_identidad_madre((ibxNumero_identidad_madre
								.getValue() != null ? ibxNumero_identidad_madre
								.getValue().toString() : "0"));
				ficha_epidemiologia_n31
						.setNivel_educativo_madre(lbxNivel_educativo_madre
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31
						.setEstrato_socioeconomico(lbxEstrato_socioeconomico
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setNum_men5_hogar((ibxNum_men5_hogar
						.getValue() != null ? ibxNum_men5_hogar.getValue()
						.toString() : "0"));
				ficha_epidemiologia_n31.setSitio_defuncion(lbxSitio_defuncion
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setPeso_al_nacer((dbxPeso_al_nacer
						.getValue() != null ? dbxPeso_al_nacer.getValue()
						.toString() : "0.00"));
				ficha_epidemiologia_n31.setTalla_al_nacer((dbxTalla_al_nacer
						.getValue() != null ? dbxTalla_al_nacer.getValue()
						.toString() : "0.00"));
				ficha_epidemiologia_n31
						.setTiempo_lactancia_materna((ibxTiempo_lactancia_materna
								.getValue() != null ? ibxTiempo_lactancia_materna
								.getValue().toString() : "0"));
				ficha_epidemiologia_n31
						.setEdad_inici_alimentacion((ibxEdad_inici_alimentacion
								.getValue() != null ? ibxEdad_inici_alimentacion
								.getValue().toString() : "0"));
				ficha_epidemiologia_n31
						.setInscrito_crecimiento(rdbInscrito_crecimiento
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setPeso_actual((dbxPeso_actual
						.getValue() != null ? dbxPeso_actual.getValue()
						.toString() : "0.00"));
				ficha_epidemiologia_n31.setTalla_longitud((dbxTalla_longitud
						.getValue() != null ? dbxTalla_longitud.getValue()
						.toString() : "0.00"));
				ficha_epidemiologia_n31
						.setEsquema_vacunacion(rdbEsquema_vacunacion
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setReferido_carnet(rdbReferido_carnet
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31
						.setClasificacion_peso_edad(lbxClasificacion_peso_edad
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31
						.setClasificacion_talla_edad(lbxClasificacion_talla_edad
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31
						.setClasificacion_peso_talla(lbxClasificacion_peso_talla
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setEdema(chbEdema.isChecked() ? "S"
						: "N");
				ficha_epidemiologia_n31
						.setEmaciacion_delgades(chbEmaciacion_delgades
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n31.setPiel_reseca(chbPiel_reseca
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n31
						.setHipo_hiperpigmentacion(chbHipo_hiperpigmentacion
								.isChecked() ? "S" : "N");
				ficha_epidemiologia_n31.setLesiones_cabello(chbLesiones_cabello
						.isChecked() ? "S" : "N");
				ficha_epidemiologia_n31.setAnemia(chbAnemia.isChecked() ? "S"
						: "N");
				ficha_epidemiologia_n31
						.setCausas_directa_muerte(tbxCausas_directa_muerte
								.getValue());
				ficha_epidemiologia_n31
						.setCodigo_causa_defuncion(tbxCodigo_causa_defuncion
								.getValue());
				ficha_epidemiologia_n31
						.setCausa_muerte_determinada_por(rdbCausa_muerte_determinada_por
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31
						.setCausas_antecententes_b(tbxCausas_antecententes_b
								.getValue());
				ficha_epidemiologia_n31
						.setCausas_antecententes_c(tbxCausas_antecententes_c
								.getValue());
				ficha_epidemiologia_n31
						.setCausas_antecententes_d(tbxCausas_antecententes_d
								.getValue());
				ficha_epidemiologia_n31.setOtros_estados(tbxOtros_estados
						.getValue());
				ficha_epidemiologia_n31
						.setDesnutricion_causa_basica(rdbDesnutricion_causa_basica
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setCodigo_des_cbas(tbxCodigo_des_cbas
						.getValue());
				ficha_epidemiologia_n31
						.setDesnutricion_causa_asociada(rdbDesnutricion_causa_asociada
								.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setCodigo_des_caso(tbxCodigo_des_caso
						.getValue());
				ficha_epidemiologia_n31.setDemora_1(rdbDemora_1
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setDemora_2(rdbDemora_2
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setDemora_3(rdbDemora_3
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setDemora_4(rdbDemora_4
						.getSelectedItem().getValue().toString());
				ficha_epidemiologia_n31.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n31.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				ficha_epidemiologia_n31.setCreacion_user(usuarios.getCodigo()
						.toString());
				ficha_epidemiologia_n31.setDelete_date(null);
				ficha_epidemiologia_n31.setUltimo_user(usuarios.getCodigo()
						.toString());
				ficha_epidemiologia_n31.setDelete_user(null);

				return ficha_epidemiologia_n31;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n31 obj) throws Exception {
		Ficha_epidemiologia_n31 ficha_epidemiologia_n31 = (Ficha_epidemiologia_n31) obj;
		try {
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n31.getCodigo_ficha());
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n31.getFecha_inicial());
			
			obtenerAdmision(admision);

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
				
			tbxPrimer_nombre_madre.setValue(ficha_epidemiologia_n31
					.getPrimer_nombre_madre());
			tbxSegundo_nombre_madre.setValue(ficha_epidemiologia_n31
					.getSegundo_nombre_madre());
			tbxPrimer_apellido_madre.setValue(ficha_epidemiologia_n31
					.getPrimer_apellido_madre());
			tbxSegundo_apellido_madre.setValue(ficha_epidemiologia_n31
					.getSegundo_apellido_madre());
			for (int i = 0; i < lbxTipo_identidad_madre.getItemCount(); i++) {
				Listitem listitem = lbxTipo_identidad_madre.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n31
								.getTipo_identidad_madre())) {
					listitem.setSelected(true);
					i = lbxTipo_identidad_madre.getItemCount();
				}
			}
			// ibxNumero_identidad_madre.setValue(ficha_epidemiologia_n31
			// .getNumero_identidad_madre());
			for (int i = 0; i < lbxNivel_educativo_madre.getItemCount(); i++) {
				Listitem listitem = lbxNivel_educativo_madre.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n31
								.getNivel_educativo_madre())) {
					listitem.setSelected(true);
					i = lbxNivel_educativo_madre.getItemCount();
				}
			}
			for (int i = 0; i < lbxEstrato_socioeconomico.getItemCount(); i++) {
				Listitem listitem = lbxEstrato_socioeconomico.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n31
								.getEstrato_socioeconomico())) {
					listitem.setSelected(true);
					i = lbxEstrato_socioeconomico.getItemCount();
				}
			}
			ibxNum_men5_hogar.setValue(Integer.valueOf(ficha_epidemiologia_n31
					.getNum_men5_hogar()));
			for (int i = 0; i < lbxSitio_defuncion.getItemCount(); i++) {
				Listitem listitem = lbxSitio_defuncion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(ficha_epidemiologia_n31.getSitio_defuncion())) {
					listitem.setSelected(true);
					i = lbxSitio_defuncion.getItemCount();
				}
			}
			dbxPeso_al_nacer.setValue(Double.valueOf(ficha_epidemiologia_n31
					.getPeso_al_nacer()));
			dbxTalla_al_nacer.setValue(Double.valueOf(ficha_epidemiologia_n31
					.getTalla_al_nacer()));
			ibxTiempo_lactancia_materna.setValue(Integer
					.valueOf(ficha_epidemiologia_n31
							.getTiempo_lactancia_materna()));
			ibxEdad_inici_alimentacion.setValue(Integer
					.valueOf(ficha_epidemiologia_n31
							.getEdad_inici_alimentacion()));
			Utilidades.seleccionarRadio(rdbInscrito_crecimiento,
					ficha_epidemiologia_n31.getInscrito_crecimiento());
			dbxPeso_actual.setValue(Double.valueOf(ficha_epidemiologia_n31
					.getPeso_actual()));
			dbxTalla_longitud.setValue(Double.valueOf(ficha_epidemiologia_n31
					.getTalla_longitud()));

			Utilidades.seleccionarRadio(rdbEsquema_vacunacion,
					ficha_epidemiologia_n31.getEsquema_vacunacion());
			Utilidades.seleccionarRadio(rdbDemora_1,
					ficha_epidemiologia_n31.getDemora_1());

			Utilidades.seleccionarRadio(rdbReferido_carnet,
					ficha_epidemiologia_n31.getReferido_carnet());

			for (int i = 0; i < lbxClasificacion_peso_edad.getItemCount(); i++) {
				Listitem listitem = lbxClasificacion_peso_edad
						.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n31
								.getClasificacion_peso_edad())) {
					listitem.setSelected(true);
					i = lbxClasificacion_peso_edad.getItemCount();
				}
			}
			for (int i = 0; i < lbxClasificacion_talla_edad.getItemCount(); i++) {
				Listitem listitem = lbxClasificacion_talla_edad
						.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n31
								.getClasificacion_talla_edad())) {
					listitem.setSelected(true);
					i = lbxClasificacion_talla_edad.getItemCount();
				}
			}
			for (int i = 0; i < lbxClasificacion_peso_talla.getItemCount(); i++) {
				Listitem listitem = lbxClasificacion_peso_talla
						.getItemAtIndex(i);
				if (listitem
						.getValue()
						.toString()
						.equals(ficha_epidemiologia_n31
								.getClasificacion_peso_talla())) {
					listitem.setSelected(true);
					i = lbxClasificacion_peso_talla.getItemCount();
				}
			}
			chbEdema.setChecked(ficha_epidemiologia_n31.getEdema().equals("S") ? true
					: false);
			chbEmaciacion_delgades.setChecked(ficha_epidemiologia_n31
					.getEmaciacion_delgades().equals("S") ? true : false);
			chbPiel_reseca.setChecked(ficha_epidemiologia_n31.getPiel_reseca()
					.equals("S") ? true : false);
			chbHipo_hiperpigmentacion.setChecked(ficha_epidemiologia_n31
					.getHipo_hiperpigmentacion().equals("S") ? true : false);
			chbLesiones_cabello.setChecked(ficha_epidemiologia_n31
					.getLesiones_cabello().equals("S") ? true : false);
			chbAnemia.setChecked(ficha_epidemiologia_n31.getAnemia()
					.equals("S") ? true : false);
			tbxCausas_directa_muerte.setValue(ficha_epidemiologia_n31
					.getCausas_directa_muerte());
			tbxCodigo_causa_defuncion.setValue(ficha_epidemiologia_n31
					.getCodigo_causa_defuncion());
			Utilidades.seleccionarRadio(rdbCausa_muerte_determinada_por,
					ficha_epidemiologia_n31.getCausa_muerte_determinada_por());
			tbxCausas_antecententes_b.setValue(ficha_epidemiologia_n31
					.getCausas_antecententes_b());
			tbxCausas_antecententes_c.setValue(ficha_epidemiologia_n31
					.getCausas_antecententes_c());
			tbxCausas_antecententes_d.setValue(ficha_epidemiologia_n31
					.getCausas_antecententes_d());
			tbxOtros_estados.setValue(ficha_epidemiologia_n31
					.getOtros_estados());
			Utilidades.seleccionarRadio(rdbDesnutricion_causa_basica,
					ficha_epidemiologia_n31.getDesnutricion_causa_basica());
			tbxCodigo_des_cbas.setValue(ficha_epidemiologia_n31
					.getCodigo_des_cbas());
			Utilidades.seleccionarRadio(rdbDesnutricion_causa_asociada,
					ficha_epidemiologia_n31.getDesnutricion_causa_asociada());
			tbxCodigo_des_caso.setValue(ficha_epidemiologia_n31
					.getCodigo_des_caso());
			Utilidades.seleccionarRadio(rdbDemora_1,
					ficha_epidemiologia_n31.getDemora_1());
			Utilidades.seleccionarRadio(rdbDemora_2,
					ficha_epidemiologia_n31.getDemora_2());
			Utilidades.seleccionarRadio(rdbDemora_3,
					ficha_epidemiologia_n31.getDemora_3());
			Utilidades.seleccionarRadio(rdbDemora_4,
					ficha_epidemiologia_n31.getDemora_4());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n31 ficha_epidemiologia_n31 = (Ficha_epidemiologia_n31) obj;
		try {
			int result = getServiceLocator()
					.getFicha_epidemiologia_nnService().eliminar(
							ficha_epidemiologia_n31);
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
	public Ficha_epidemiologia_n31 consultarDatos(Map<String, Object> map,
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
				
				List<Ficha_epidemiologia_n31> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n31.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n31 ficha_n31 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n31;
				}else{

					return null;
				}
	}

}