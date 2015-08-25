package com.framework.macros.odontograma;

import healthmanager.controller.ZKWindow;
import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Diente;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Auxhead;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.odontograma.DienteMacro.Estado;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.selectores.Ausente;
import com.framework.macros.odontograma.selectores.Caries;
import com.framework.macros.odontograma.selectores.CoronaBuena;
import com.framework.macros.odontograma.selectores.DienteErupcion;
import com.framework.macros.odontograma.selectores.DienteSano;
import com.framework.macros.odontograma.selectores.DienteSellado;
import com.framework.macros.odontograma.selectores.DientesSanosChuloSelector;
import com.framework.macros.odontograma.selectores.Endodoncia;
import com.framework.macros.odontograma.selectores.ExodonciaSimple;
import com.framework.macros.odontograma.selectores.Extraido;
import com.framework.macros.odontograma.selectores.Ionomeros;
import com.framework.macros.odontograma.selectores.NucleoPostes;
import com.framework.macros.odontograma.selectores.OpturacionTemporalConvencion;
import com.framework.macros.odontograma.selectores.ProtesisFija;
import com.framework.macros.odontograma.selectores.ProtesisRemovible;
import com.framework.macros.odontograma.selectores.RestauracionDesadaptada;
import com.framework.macros.odontograma.selectores.SuperficieAbrasion;
import com.framework.macros.odontograma.selectores.SuperficieEnAmalgama;
import com.framework.macros.odontograma.selectores.SuperficieManchada;
import com.framework.macros.odontograma.selectores.SuperficiePorSellar;
import com.framework.macros.odontograma.selectores.SuperficieSectorRecita;
import com.framework.macros.odontograma.util.ISectorDiente;
import com.framework.macros.odontograma.util.ISectorDiente.TypeSector;
import com.framework.macros.odontograma.util.ISelector;
import com.framework.macros.odontograma.util.ISuperficieTotal;
import com.framework.macros.odontograma.util.OnClickDiente;
import com.framework.res.CargardorDeDatos;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesComponentes;

/**
 * Esta clase simula un odontograma
 * 
 * @author Luis Miguel
 * */
public class OdontogramaMacro extends Groupbox implements AfterCompose {

	// private static Logger log = Logger.getLogger(OdontogramaMacro.class);

	/**
	 * @author Luis Miguel Hernandez <br/>
	 *         <b>Con Esta anotacion, en el metodo loadEvent(). sabe cuales son
	 *         los dientes, para asi hacer la inyeccion del evento. </b>
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface DienteEnum {

		/**
		 * Esto me permite saber si el diente es de O'leary o no
		 * */
		boolean leary() default false;

		/**
		 * Esto me permite saber si el diente es permanente o superior
		 * */
		boolean isAulto();

		/**
		 * Esto me permite, identificar el diente si es en la parte superior o
		 * inferior del diente
		 * 
		 * @author Luis Miguel
		 * */
		boolean isParteSuperior() default false;

		/**
		 * Este me permite identificar de que lado esta el diente
		 * 
		 * @author Luis Miguel
		 * */
		boolean isLadoIzquierdo() default true;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface IndiceDean {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface noClear {
	}

	private final String NOMBRE_DIENTE = "dientediente";

	/* odontograma */
	// odontontograma
	// dientes superiores
	@DienteEnum(isAulto = true, isParteSuperior = true)
	@View
	private DienteMacro dientediente18;
	@DienteEnum(isAulto = true, isParteSuperior = true)
	@View
	private DienteMacro dientediente17;
	@DienteEnum(isAulto = true, isParteSuperior = true)
	@View
	private DienteMacro dientediente16;
	@DienteEnum(isAulto = true, isParteSuperior = true)
	@View
	private DienteMacro dientediente15;
	@DienteEnum(isAulto = true, isParteSuperior = true)
	@View
	private DienteMacro dientediente14;
	@DienteEnum(isAulto = true, isParteSuperior = true)
	@View
	private DienteMacro dientediente13;
	@DienteEnum(isAulto = true, isParteSuperior = true)
	@View
	private DienteMacro dientediente12;
	@DienteEnum(isAulto = true, isParteSuperior = true)
	@View
	private DienteMacro dientediente11;

	@DienteEnum(isAulto = true, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente21;
	@DienteEnum(isAulto = true, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente22;
	@DienteEnum(isAulto = true, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente23;
	@DienteEnum(isAulto = true, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente24;
	@DienteEnum(isAulto = true, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente25;
	@DienteEnum(isAulto = true, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente26;
	@DienteEnum(isAulto = true, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente27;
	@DienteEnum(isAulto = true, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente28;

	// dientes superiores niño
	@DienteEnum(isAulto = false, isParteSuperior = true)
	@View
	private DienteMacro dientediente55;
	@DienteEnum(isAulto = false, isParteSuperior = true)
	@View
	private DienteMacro dientediente54;
	@DienteEnum(isAulto = false, isParteSuperior = true)
	@View
	private DienteMacro dientediente53;
	@DienteEnum(isAulto = false, isParteSuperior = true)
	@View
	private DienteMacro dientediente52;
	@DienteEnum(isAulto = false, isParteSuperior = true)
	@View
	private DienteMacro dientediente51;

	@DienteEnum(isAulto = false, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente61;
	@DienteEnum(isAulto = false, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente62;
	@DienteEnum(isAulto = false, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente63;
	@DienteEnum(isAulto = false, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente64;
	@DienteEnum(isAulto = false, isParteSuperior = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente65;

	// dientes inferiores adulto
	@DienteEnum(isAulto = false)
	@View
	private DienteMacro dientediente85;
	@DienteEnum(isAulto = false)
	@View
	private DienteMacro dientediente84;
	@DienteEnum(isAulto = false)
	@View
	private DienteMacro dientediente83;
	@DienteEnum(isAulto = false)
	@View
	private DienteMacro dientediente82;
	@DienteEnum(isAulto = false)
	@View
	private DienteMacro dientediente81;

	@DienteEnum(isAulto = false, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente71;
	@DienteEnum(isAulto = false, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente72;
	@DienteEnum(isAulto = false, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente73;
	@DienteEnum(isAulto = false, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente74;
	@DienteEnum(isAulto = false, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente75;

	// dientes inferiores
	@DienteEnum(isAulto = true)
	@View
	private DienteMacro dientediente48;
	@DienteEnum(isAulto = true)
	@View
	private DienteMacro dientediente47;
	@DienteEnum(isAulto = true)
	@View
	private DienteMacro dientediente46;
	@DienteEnum(isAulto = true)
	@View
	private DienteMacro dientediente45;
	@DienteEnum(isAulto = true)
	@View
	private DienteMacro dientediente44;
	@DienteEnum(isAulto = true)
	@View
	private DienteMacro dientediente43;
	@DienteEnum(isAulto = true)
	@View
	private DienteMacro dientediente42;
	@DienteEnum(isAulto = true)
	@View
	private DienteMacro dientediente41;

	@DienteEnum(isAulto = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente31;
	@DienteEnum(isAulto = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente32;
	@DienteEnum(isAulto = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente33;
	@DienteEnum(isAulto = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente34;
	@DienteEnum(isAulto = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente35;
	@DienteEnum(isAulto = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente36;
	@DienteEnum(isAulto = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente37;
	@DienteEnum(isAulto = true, isLadoIzquierdo = false)
	@View
	private DienteMacro dientediente38;

	// indice de leary
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary18;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary17;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary16;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary15;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary14;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary13;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary12;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary11;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary21;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary22;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary23;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary24;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary25;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary26;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary27;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary28;

	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary55;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary54;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary53;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary52;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary51;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary61;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary62;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary63;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary64;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary65;

	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary85;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary84;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary83;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary82;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary81;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary71;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary72;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary73;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary74;
	@DienteEnum(isAulto = false, leary = true)
	@View
	private DienteMacro dienteleary75;

	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary48;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary47;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary46;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary45;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary44;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary43;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary42;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary41;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary31;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary32;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary33;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary34;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary35;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary36;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary37;
	@DienteEnum(isAulto = true, leary = true)
	@View
	private DienteMacro dienteleary38;

	@View
	Groupbox indicedean;
	@View
	Groupbox indiceDental;
	@View
	Grid accion_odontograma;
	@View
	Auxhead tipo_denticion;
	@View
	Groupbox grpOdonotograma;
	@View
	Caption captionOdontograma;

	@View
	Checkbox chcMarcarDientesSanos;

	// selector
	private ISelector iSelector;
	private OnClickDiente onClickDiente;
	private OnClickDiente onClickDienteLeary;
	private ISelector iSelectorLeary;

	// Esta variable lleva la cantidad de cuantos dientes leary estan.
	private final int lengthLearyLeche = 5 * 4;
	private final int lengthLearyPermanentes = 8 * 4;

	@View
	private Listbox lbxModoOdontograma;

	public enum DienteOdontograma {
		ALL, ODONOTOGRAMA, OLEARY
	};

	/* variables de indice de caries */
	private int cariadosAdulto;
	private int opturadosAdulto;
	private int perdidosAdulto;
	private int copAdulto;

	// dientes de leche
	private int cariadosLeche;
	private int opturadoLeche;
	private int extraidosLeche;
	private int ceoLeche;

	// contador diente ausentes
	private int ausente_permanente;
	private int ausente_temporal;

	/* indice de caries */
	@View
	private Textbox tbxCariados;
	@View
	private Textbox tbxOpturados;
	@View
	private Textbox tbxPerdidos;
	@View
	private Textbox tbxCOP;

	@View
	private Textbox tbxCariadosNinio;
	@View
	private Textbox tbxOpturadosNinio;
	@View
	private Textbox tbxEstraidos;
	@View
	private Textbox tbxCEO;

	/* porcentaje de dientes manchados */
	@View
	private Textbox tbxPorcentajeManchado;

	/* supercies manchadas seleccionadas */
	private int superficiesManchadas;
	protected int select;

	private ZKWindow windowParent;

	@Override
	public void afterCompose() {
		init();
	}

	public void init() {
		try {
			CargardorDeDatos.initComponents(this);
			loadComponents();
			cargarEventosDiente();

		} catch (Exception e) {
			MensajesUtil.mensajeAlerta("Alerta", "Ha ocurrido un error");
			e.printStackTrace();
		}
	}

	/**
	 * 0 - cariado 1 - opturado
	 * */
	private int contieneCariado(DienteMacro dienteMacro, String tipo) {
		ISectorDiente[] iSectorDientes = {
				dienteMacro.getiSectorDienteBottom(),
				dienteMacro.getiSectorDienteCenter(),
				dienteMacro.getiSectorDienteLeft(),
				dienteMacro.getiSectorDienteRigth(),
				dienteMacro.getiSectorDienteTop(),
				dienteMacro.getiSectorDienteSuperficieSuperior() };
		int contador_ = 0;
		for (ISectorDiente iSectorDiente : iSectorDientes) {
			if (iSectorDiente != null) {
				if (tipo.equals("0")) {
					if (isCariado(iSectorDiente.getTypeSector())) {
						contador_++;
					}
				} else if (tipo.equals("1")) {
					if (isOpturado(iSectorDiente.getTypeSector())) {
						contador_++;
					}
				}

			}
		}
		return contador_;
	}

	/* odontograma acciones */
	/**
	 * En este medetodo incializamos todos los eventos del odontograma
	 * */
	private void cargarEventosDiente() {
		try {
			/* creamos metodo generico */
			onClickDiente = new OnClickDiente() {
				@Override
				public void onClick(DienteMacro dienteMacro) {
					dienteMacro.setiSelector(iSelector);
				}

				@Override
				public void onDespuesDelClick(DienteMacro dienteMacro) {
					boolean extraido = iSelector instanceof Extraido;
					// boolean diente_sano = iSelector instanceof DienteSano;
					boolean ausente = iSelector instanceof Ausente;
					if (extraido || ausente) {
						if (ausente) {
							marcarDienteComoAusenteOLeary(dienteMacro);
						} else {
							verificarAusencia(dienteMacro, false);
						}
					} else {
						verificarDienteTocayoMarcadoAusente(dienteMacro);
					}
				}

				@Override
				public void onChangeStateDiente(TypeSector antes,
						TypeSector ahora, boolean isAdulto,
						DienteMacro dienteMacro, SECTOR sector) {
					// Realizamos una validacion cuando sea cariado
					// int cariados = contieneCariado(dienteMacro);
					// //log.info("Cantidad dientes cariados: " + cariados);
					// log.info("Accion cambio odontograma");
					calcularIndiceCariesDental(antes, ahora, isAdulto,
							dienteMacro, false);
					// updateValoresLeary();
				}
			};
			iSelectorLeary = new SuperficieManchada();
			onClickDienteLeary = new OnClickDiente() {
				@Override
				public void onClick(DienteMacro dienteMacro) {
					dienteMacro.setiSelector(iSelectorLeary);
				}

				@Override
				public void onDespuesDelClick(DienteMacro dienteMacro) {

				}

				@Override
				public void onChangeStateDiente(TypeSector antes,
						TypeSector ahora, boolean isAdulto,
						DienteMacro dienteMacro, SECTOR sector) {
					// log.info("Evento manchado: " + antes + " / " + ahora);
					calcularSuperficieManchada(antes, ahora, isAdulto,
							dienteMacro, true);
				}
			};

			lbxModoOdontograma.addEventListener("onSelect",
					new EventListener<SelectEvent>() {
						@Override
						public void onEvent(SelectEvent event) throws Exception {
							Messagebox.show(
									"Estas seguro que deseas cambiar de modo?",
									"Informacion", Messagebox.YES
											+ Messagebox.NO,
									Messagebox.INFORMATION,
									new EventListener<Event>() {
										@Override
										public void onEvent(Event event)
												throws Exception {
											if ("onYes".equals(event.getName())) {
												cambioModoOdontograma();
												String modo = lbxModoOdontograma
														.getSelectedItem()
														.getValue().toString();
												if (modo.equals("03")) {
													MensajesUtil
															.mensajeInformacion(
																	"informacion",
																	"Ha seleccionado la denticion mixta,"
																			+ " para ello debe seleccionar los dientes ausentes en el odonotograma");
												}
												select = lbxModoOdontograma
														.getSelectedIndex();
												if (!"03"
														.equals(lbxModoOdontograma
																.getSelectedItem()
																.getValue()
																.toString())) {
													tbxPorcentajeManchado
															.setValue("");
													superficiesManchadas = 0;
												}
												if (chcMarcarDientesSanos
														.isChecked()) {
													marcarDientesSanos(true);
												}
											} else {
												lbxModoOdontograma
														.setSelectedIndex(select);
											}
										}
									});
						}
					});
			cambioModoOdontograma();

			/* inyectamos metodo a todos los dientes */
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				DienteEnum view = field.getAnnotation(DienteEnum.class);
				if (view != null) {
					DienteMacro dienteMacro = (DienteMacro) field.get(this);
					if (view.leary()) {
						dienteMacro.setOnClickDiente(onClickDienteLeary);
						dienteMacro.setMoldDiente(DienteMacro.MOLD_4SECTORES);
					} else {
						dienteMacro.setOnClickDiente(onClickDiente);
					}

					/* *
					 * De esta manera sabemos el numero del diente si es adulto
					 * si esta en la parte superior del odontograma
					 */
					dienteMacro.setNumber(field.getName().replaceAll(
							"[a-zA-Z]", ""));
					dienteMacro.setAdulto(view.isAulto());
					dienteMacro.setParteSuperior(view.isParteSuperior());

					inyectarNodosComunicacion(dienteMacro,
							view.isLadoIzquierdo());
				}
			}

			chcMarcarDientesSanos.addEventListener(Events.ON_CHECK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							final boolean marcar = (Boolean) ((Checkbox) event
									.getTarget()).isChecked();
							marcarDientesSanos(marcar);
						}
					});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, windowParent);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	protected void verificarDienteTocayoMarcadoAusente(DienteMacro dienteMacro) {
		try {
			String number = dienteMacro.getNumber();
			Field field = this.getClass().getDeclaredField(
					"dienteleary" + number);
			if (field != null) {
				field.setAccessible(true);
				DienteMacro dienteMacroLeary = (DienteMacro) field.get(this);

				if (dienteMacroLeary.getiSectorDienteSuperficieSuperior() != null) {
					if (dienteMacroLeary.getiSectorDienteSuperficieSuperior()
							.getTypeSector() == TypeSector.AUSENTE
							|| dienteMacroLeary
									.getiSectorDienteSuperficieSuperior()
									.getTypeSector() == TypeSector.EXTRAIDO) {
						dienteMacroLeary.reset();
						dienteMacroLeary.apllyAll();
						dienteMacroLeary.setOnClickDiente(onClickDienteLeary);
						updateValoresLeary();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este mapa me devuelve las imagen del diente de cada diente (diente + nro)
	 * y si es un diente de oleary (oleary + nro)
	 * 
	 * @author Luis Miguel
	 * @return Map<String, InputStream>
	 * */
	public Map<String, Object> getMap(DienteOdontograma dienteOdontograma,
			boolean solo_utilizado) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			DienteEnum view = field.getAnnotation(DienteEnum.class);
			if (view != null) {
				boolean addDiente = false;
				if (dienteOdontograma != DienteOdontograma.ALL) {
					if (dienteOdontograma == DienteOdontograma.OLEARY) {
						addDiente = view.leary();
					} else {
						addDiente = !view.leary();
					}
				} else {
					addDiente = true;
				}
				if (addDiente) {
					DienteMacro dienteMacro = (DienteMacro) field.get(this);
					if (dienteMacro.isUtilizado() || !solo_utilizado) {
						String nombre = view.leary() ? "oleary" : "diente";
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageIO.write(dienteMacro.getBufferedImage_(), "png",
								baos);
						InputStream inputStream = new ByteArrayInputStream(
								baos.toByteArray());
						map.put(nombre + dienteMacro.getNumber(), inputStream);
					}
				}
			}
		}
		return map;
	}

	protected void marcarDienteComoAusenteOLeary(DienteMacro dienteMacro) {
		try {
			String number = dienteMacro.getNumber();
			Field field = this.getClass().getDeclaredField(
					"dienteleary" + number);
			if (field != null) {
				field.setAccessible(true);
				DienteMacro dienteMacroLeary = (DienteMacro) field.get(this);
				dienteMacroLeary.setiSelector(new Ausente());
				dienteMacroLeary.aplySuper();
				dienteMacroLeary.setOnClickDiente(new OnClickDiente() {
					@Override
					public void onClick(DienteMacro dienteMacro) {
						dienteMacro.setiSelector(new Ausente());
					}

					@Override
					public void onChangeStateDiente(TypeSector antes,
							TypeSector ahora, boolean isAdulto,
							DienteMacro dienteMacro, SECTOR sector) {
					}

					@Override
					public void onDespuesDelClick(DienteMacro dienteMacro) {

					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo me permite, intercomunicar los dientes por medios de nodos
	 * 
	 * @author Luis Miguel
	 * */
	private void inyectarNodosComunicacion(DienteMacro dienteMacro,
			boolean lado_izquierdo) {
		int numero_diente = Integer.parseInt(dienteMacro.getNumber());
		int numero_diente_derecho = 0;
		int numero_diente_izquierdo = 0;
		if (!lado_izquierdo) {
			numero_diente_derecho = numero_diente - 1;
			numero_diente_izquierdo = numero_diente + 1;
		} else {
			numero_diente_derecho = numero_diente + 1;
			numero_diente_izquierdo = numero_diente - 1;
		}

		/* En esta parte agregamos los nodos corespondientes si existen */
		dienteMacro.setDienteMacroNodoDer(getDienteMacroPorNombre(NOMBRE_DIENTE
				+ numero_diente_derecho));
		dienteMacro.setDienteMacroNodoIzq(getDienteMacroPorNombre(NOMBRE_DIENTE
				+ numero_diente_izquierdo));
	}

	private DienteMacro getDienteMacroPorNombre(String nombre_diente) {
		try {
			Field field = OdontogramaMacro.class
					.getDeclaredField(nombre_diente);
			field.setAccessible(true);
			DienteMacro dienteMacro = (DienteMacro) field.get(this);
			return dienteMacro;
		} catch (Exception e) {
			return null;
		}
	}

	protected void marcarDientesSanos(boolean marcar) throws Exception {
		Field[] fieldDientes = this.getClass().getDeclaredFields();
		String modo_denticion = getTipoDenticion();
		for (Field field : fieldDientes) {
			DienteEnum dienteEnum = field.getAnnotation(DienteEnum.class);
			if (dienteEnum != null) {
				if (!dienteEnum.leary()) {
					DienteMacro dienteMacro = (DienteMacro) field.get(this);

					if (marcar) {
						iSelector = getSelectorDientesSanos();
					} else {
						iSelector = new DienteSano();
					}
					boolean aplicar_accion_diente = false;
					ISuperficieTotal iSelectorTotal = dienteMacro
							.getiSectorDienteSuperficieSuperior();
					boolean marcado_ausente = false;
					if (iSelectorTotal != null) {
						// log.info("clase:" + iSelectorTotal.getClass());
						if (iSelectorTotal
								.getClass()
								.toString()
								.contains(
										"com.framework.macros.odontograma.selectores.Ausente")) {
							marcado_ausente = true;
							// log.info("instancia");
						}
					}

					if (modo_denticion.equals("01") && !dienteEnum.isAulto()) { // permanente
						aplicar_accion_diente = true;
					} else if (modo_denticion.equals("02")
							&& dienteEnum.isAulto()) {
						aplicar_accion_diente = true;
					} else if (modo_denticion.equals("03")) {
						aplicar_accion_diente = true;
					}
					if (iSelector != null && aplicar_accion_diente
							&& !marcado_ausente) {
						dienteMacro.setiSelector(iSelector);
						dienteMacro.setSectorFromIselector(SECTOR.NONE);
						dienteMacro.apllyAll();
					}
					dienteMacro.setDisabled(marcar);
				}
			}
		}
	}

	/**
	 * Este metodo me devuelbe el selector de diente marcado como sano
	 * 
	 * @author Luis Miguel
	 * */
	private ISelector getSelectorDientesSanos() {
		return new DientesSanosChuloSelector();
	}

	/**
	 * En este metodo realizamos el cambio de modo
	 * */
	public void cambioModoOdontograma() {
		try {
			String modo = lbxModoOdontograma.getSelectedItem().getValue()
					.toString();
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				DienteEnum dienteEnum = field.getAnnotation(DienteEnum.class);
				if (dienteEnum != null) {
					field.setAccessible(true);
					DienteMacro diente = (DienteMacro) field.get(this);
					if (modo.equals("01")) {
						if (!dienteEnum.isAulto()) {
							diente.setDisabled(false);
						} else {
							diente.resetAndDisabled();
						}
					} else if (modo.equals("02")) {
						if (dienteEnum.isAulto()) {
							diente.setDisabled(false);
						} else {
							diente.resetAndDisabled();
						}
					} else {
						diente.setDisabled(false);
					}
				}
			}
			aplicarValor(modo);
		} catch (Exception e) {
			throw new ValidacionRunTimeException(
					"Error al cargar tipo denticion");
		}
	}

	public void setDisabledDientesOLeary(boolean disable) throws Exception {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			DienteEnum dienteEnum = field.getAnnotation(DienteEnum.class);
			if (dienteEnum != null) {
				field.setAccessible(true);
				if (dienteEnum.leary()) {
					DienteMacro diente = (DienteMacro) field.get(this);
					diente.setDisabled(disable);
				}
			}
		}
	}

	public void setDisabledDientesOdontograma(boolean disable) throws Exception {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			DienteEnum dienteEnum = field.getAnnotation(DienteEnum.class);
			if (dienteEnum != null) {
				field.setAccessible(true);
				if (!dienteEnum.leary()) {
					DienteMacro diente = (DienteMacro) field.get(this);
					diente.setDisabled(disable);
				}
			}
		}
	}

	public void setDisabledDientes(boolean disable) throws Exception {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			DienteEnum dienteEnum = field.getAnnotation(DienteEnum.class);
			if (dienteEnum != null) {
				field.setAccessible(true);
				DienteMacro diente = (DienteMacro) field.get(this);
				diente.setDisabled(disable);
			}
		}
	}

	private void aplicarValor(String modo) {
		if (!modo.equals("03")) {
			if (!modo.equals("01")) {
				cariadosLeche = 0;
				opturadoLeche = 0;
				extraidosLeche = 0;
				ceoLeche = 0;
				ausente_permanente = 0;
			} else if (!modo.equals("02")) {
				cariadosAdulto = 0;
				opturadosAdulto = 0;
				perdidosAdulto = 0;
				copAdulto = 0;
				ausente_temporal = 0;
			}
		}
		updateValores();
	}

	/* este es para cargar la informacion de los dientes */
	public void setDientes(List<Diente> dientes) throws Exception {
		if (dientes != null)
			for (Diente diente : dientes) {
				Field field = this.getClass().getDeclaredField(
						(diente.getLeary().equals("S") ? "dienteleary"
								: "dientediente") + diente.getNro_diente());
				field.setAccessible(true);
				DienteMacro dienteMacro = (DienteMacro) field.get(this);
				if (dienteMacro != null) {

					iSelector = getSelectorFromType(diente.getSector_bottom());
					if (iSelector != null) {
						dienteMacro.setiSelector(iSelector);
						dienteMacro.setSectorFromIselector(SECTOR.BOTTOM);
					}

					iSelector = getSelectorFromType(diente.getSector_top());
					if (iSelector != null) {
						dienteMacro.setiSelector(iSelector);
						dienteMacro.setSectorFromIselector(SECTOR.TOP);
					}

					iSelector = getSelectorFromType(diente.getSector_left());
					if (iSelector != null) {
						dienteMacro.setiSelector(iSelector);
						dienteMacro.setSectorFromIselector(SECTOR.LEFT);
					}

					iSelector = getSelectorFromType(diente.getSector_rigth());
					if (iSelector != null) {
						dienteMacro.setiSelector(iSelector);
						dienteMacro.setSectorFromIselector(SECTOR.RIGTH);
					}

					iSelector = getSelectorFromType(diente.getSector_center());
					if (iSelector != null) {
						dienteMacro.setiSelector(iSelector);
						dienteMacro.setSectorFromIselector(SECTOR.CENTER);
					}

					iSelector = getSelectorFromType(diente.getSector_superior());
					if (iSelector != null) {
						dienteMacro.setiSelector(iSelector);
						dienteMacro.setSectorFromIselector(SECTOR.NONE);
					}

					dienteMacro.apllyAll();
				}
			}

		/* actualizamos los valores reales */
		// cariadosAdulto = odontologia.getOdont_grama_cariados_adulto();
		// cariadosLeche = odontologia.getOdont_grama_cariados_leche();
		// ceoLeche = odontologia.getOdont_grama_ceo_leche();
		// copAdulto = odontologia.getOdont_grama_cop_adulto();
		// estraidosLeche = odontologia.getOdont_grama_estraidos_leche();
		// opturadosAdulto = odontologia.getOdont_grama_opturados_adulto();
		// opturadoLeche = odontologia.getOdont_grama_opturados_leche();
		// perdidosAdulto = odontologia.getOdont_grama_perdidos_adulto();
		// superficiesManchadas =
		// odontologia.getOdont_grama_porcentaje_manchado();
		updateValores();
		updateValoresLeary();
	}

	public static ISelector getSelectorFromType(String typeSelector) {
		try {
			if (typeSelector != null) {
				if (!typeSelector.isEmpty()) {
					switch (TypeSector.valueOf(typeSelector)) {
					case EXTRAIDO:
						return new Extraido();
					case CARIES_OPTURACION:
						return new Caries();
					case CORONA_DESADAPTADA:
						return new CoronaBuena(Estado.DESADAPTADA);
					case CORONABUENA:
						return new CoronaBuena(Estado.BUENA);
					case SUPERFICIE_MANCHADA:
						return new SuperficieManchada();
					case SUPERFICIE_RECINA:
						return new SuperficieSectorRecita();
					case AUSENTE:
						return new Ausente();
					case DIENTE_SANO:
						return new DienteSano();
					case DIENTE_SELLADO:
						return new DienteSellado();
					case DIENTEERUPCION:
						return new DienteErupcion();
					case ENDODONCIA:
						return new Endodoncia(Estado.RELLENADO);
					case ENDODONCIA_POR_RELLENAR:
						return new Endodoncia(Estado.POR_RELLNAR);
					case EXODONCIA_SIMPLE:
						return new ExodonciaSimple();
					case IONOMEROS:
						return new Ionomeros();
					case NUCLEO_POSTES:
						return new NucleoPostes();
					case PRETESIS_REMOVIBLE:
						return new ProtesisRemovible();
					case PROTESIS_FIJA:
						return new ProtesisFija();
					case RESTAURACION_DESADAPTADA:
						return new RestauracionDesadaptada();
					case SUPERFICIE_AMALGAMA:
						return new SuperficieEnAmalgama();
					case SUPERFICIE_POR_SELLAR:
						return new SuperficiePorSellar();
					case DIENTES_SANOS_CHULO:
						return new DientesSanosChuloSelector();
					case ABRASION:
						return new SuperficieAbrasion();
					default:
						return null;
					}
				} else
					return null;
			} else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

	private void loadComponents() {
		UtilidadesComponentes.listarElementosListbox(false, false,
				getServiceLocator(), lbxModoOdontograma);
	}

	protected void verificarAusencia(DienteMacro dienteMacro, boolean sano) {
		try {
			String number = dienteMacro.getNumber();
			Field field = this.getClass().getDeclaredField(
					"dienteleary" + number);
			if (field != null) {
				field.setAccessible(true);
				DienteMacro dienteMacroLeary = (DienteMacro) field.get(this);
				dienteMacroLeary.setiSelector(sano ? new DienteSano()
						: new Extraido());
				if (sano) {
					dienteMacroLeary.aplySector();
				} else
					dienteMacroLeary.aplySuper();
				dienteMacroLeary.setOnClickDiente(sano ? onClickDienteLeary
						: new OnClickDiente() {

							@Override
							public void onClick(DienteMacro dienteMacro) {
								dienteMacro.setiSelector(new Extraido());
							}

							@Override
							public void onChangeStateDiente(TypeSector antes,
									TypeSector ahora, boolean isAdulto,
									DienteMacro dienteMacro, SECTOR sector) {
							}

							@Override
							public void onDespuesDelClick(
									DienteMacro dienteMacro) {

							}
						});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void calcularIndiceCariesDental(TypeSector antes,
			TypeSector ahora, boolean isAdulto, DienteMacro dienteMacro,
			boolean isLeary) {
		// log.info(":::: ESTA ACTUALIZANDO LOS VALORES DEL ODONTOGRAMA :::: " +
		// antes.toString() + " ahora " + ahora);
		decrementar(antes, isAdulto, dienteMacro, isLeary);
		incrementar(ahora, isAdulto, dienteMacro, isLeary);
		calcularCOPYCeo();
		updateValores();
	}

	private void calcularCOPYCeo() {
		copAdulto = cariadosAdulto + opturadosAdulto + perdidosAdulto;
		ceoLeche = cariadosLeche + opturadoLeche + extraidosLeche;
	}

	/* Cargamos los valores en vista */
	private void updateValores() {
		tbxCariados.setValue("" + cariadosAdulto);
		tbxOpturados.setValue("" + opturadosAdulto);
		tbxPerdidos.setValue("" + perdidosAdulto);
		tbxCOP.setValue("" + copAdulto);

		/* niños */
		tbxCariadosNinio.setValue("" + cariadosLeche);
		tbxOpturadosNinio.setValue("" + opturadoLeche);
		tbxEstraidos.setValue("" + extraidosLeche);
		tbxCEO.setValue("" + ceoLeche);
	}

	public void updateValoresLeary() {
		Listitem listitem = lbxModoOdontograma.getSelectedItem();
		int lengthLeary = lengthLearyLeche
				+ lengthLearyPermanentes
				- ((perdidosAdulto + ausente_permanente) + (extraidosLeche + ausente_temporal));
		if (listitem != null) {
			if ("01".equals(listitem.getValue().toString())) {
				lengthLeary = lengthLearyLeche
						- (extraidosLeche + ausente_temporal);
				// log.info("lgth: " + lengthLearyLeche);
				// log.info("Nro ausentes: " + ausente_temporal);
				// log.info("extraidos: " + extraidosLeche);
			} else if ("02".equals(listitem.getValue().toString())) {
				lengthLeary = lengthLearyPermanentes
						- (perdidosAdulto + ausente_permanente);
				// log.info("lgth: " + lengthLearyPermanentes);
				// log.info("Nro ausentes: " + ausente_permanente);
				// log.info("Perdidos: " + perdidosAdulto);
			}
			// log.info("Dientes activos para leary: " + lengthLeary);
			// log.info("Porc(%):> " + superficiesManchadas + " * " + 100d +
			// " / " + (lengthLeary * 4d));
			tbxPorcentajeManchado
					.setValue(""
							+ new DecimalFormat("###.##")
									.format((superficiesManchadas * 100d / (lengthLeary * 4d)))
							+ " %");
		}
	}

	/**
	 * Este metodo me permite validar, la cantidad ya que ahi variaciones por la
	 * cantidades
	 * 
	 * @param isLeary
	 * */

	private void incrementar(TypeSector ahora, boolean isAdulto,
			DienteMacro dienteMacro, boolean isLeary) {
		if (isLeary) {
			if (isManchado(ahora)) {
				superficiesManchadas++;
			}
		} else {
			if (isAdulto) {
				if (isCariado(ahora)) {
					int cariados = contieneCariado(dienteMacro, "0");
					if (cariados == 0) {
						cariadosAdulto++;
					}
				}
				if (isOpturado(ahora)) {
					int opturado = contieneCariado(dienteMacro, "1");
					if (opturado == 0) {
						opturadosAdulto++;
					}
				}

				if (isExtraidos(ahora)) {
					perdidosAdulto++;
				}

				if (isAusente(ahora)) {
					ausente_permanente++;
				}
			} else {
				/* niños */
				if (isCariado(ahora)) {
					int cariados = contieneCariado(dienteMacro, "0");
					if (cariados == 0) {
						cariadosLeche++;
					}
				}
				if (isOpturado(ahora)) {
					int opturado = contieneCariado(dienteMacro, "1");
					if (opturado == 0) {
						opturadoLeche++;
					}
				}
				if (isExtraidos(ahora)) {
					extraidosLeche++;
				}
				if (isAusente(ahora)) {
					ausente_temporal++;
				}
			}
		}
	}

	private void decrementar(TypeSector antes, boolean isAdulto,
			DienteMacro dienteMacro, boolean isLeary) {
		if (isLeary) {
			if (isManchado(antes)) {
				superficiesManchadas--;
			}
		} else {
			if (isAdulto) {
				if (isCariado(antes)) {
					int cariados = contieneCariado(dienteMacro, "0");
					if (cariados == 1) {
						cariadosAdulto--;
					}
				}

				if (isOpturado(antes)) {
					int opturado = contieneCariado(dienteMacro, "1");
					if (opturado == 1) {
						opturadosAdulto--;
					}
				}

				if (isExtraidos(antes)) {
					perdidosAdulto--;
				}

				if (isAusente(antes)) {
					ausente_permanente--;
				}
			} else {
				if (isCariado(antes)) {
					int cariados = contieneCariado(dienteMacro, "0");
					if (cariados == 1) {
						cariadosLeche--;
					}
				}

				if (isOpturado(antes)) {
					int opturado = contieneCariado(dienteMacro, "1");
					if (opturado == 1) {
						opturadoLeche--;
					}
				}

				if (isExtraidos(antes)) {
					extraidosLeche--;
				}

				if (isAusente(antes)) {
					ausente_temporal--;
				}
			}
		}
	}

	private boolean isCariado(TypeSector antes) {
		boolean isCariado = false;
		if (antes == TypeSector.EXODONCIA_SIMPLE
				|| antes == TypeSector.CARIES_OPTURACION
				|| antes == TypeSector.RESTAURACION_DESADAPTADA
				|| antes == TypeSector.ENDODONCIA_POR_RELLENAR
				|| antes == TypeSector.OPTURACION_TEMPORAL) {
			isCariado = true;
		}
		return isCariado;
	}

	private boolean isManchado(TypeSector antes) {
		boolean isCariado = false;
		if (antes == TypeSector.SUPERFICIE_MANCHADA) {
			isCariado = true;
		}
		return isCariado;
	}

	private boolean isOpturado(TypeSector antes) {
		boolean isOpturado = false;
		if (antes == TypeSector.SUPERFICIE_RECINA
				|| antes == TypeSector.SUPERFICIE_AMALGAMA
				|| antes == TypeSector.DIENTE_SELLADO
				|| antes == TypeSector.IONOMEROS) {
			isOpturado = true;
		}
		return isOpturado;
	}

	private boolean isExtraidos(TypeSector antes) {
		boolean isExtraidos = false;
		if (antes == TypeSector.EXTRAIDO) {
			isExtraidos = true;
		}
		return isExtraidos;
	}

	private boolean isAusente(TypeSector antes) {
		boolean isAusente = false;
		if (antes == TypeSector.AUSENTE) {
			isAusente = true;
		}
		return isAusente;
	}

	protected void calcularSuperficieManchada(TypeSector antes,
			TypeSector ahora, boolean isAdulto, DienteMacro dienteMacro,
			boolean isLeary) {
		decrementar(antes, isAdulto, dienteMacro, isLeary);
		incrementar(ahora, isAdulto, dienteMacro, isLeary);
		updateValoresLeary();
	}

	/* eventos de botones odontograma */
	public void clickSuperficieRecina() {
		iSelector = new SuperficieSectorRecita();
	}

	public void clickDienteSano() {
		iSelector = new DienteSano();
	}

	public void clickSuperficieAmalgama() {
		iSelector = new SuperficieEnAmalgama();
	}

	public void clickCariesOpturacionTemporal() {
		iSelector = new Caries();
	}

	public void clickDienteSellado() {
		iSelector = new DienteSellado();
	}

	public void clickSuperficiePorSellar() {
		iSelector = new SuperficiePorSellar();
	}

	public void clickDienteErupcion() {
		iSelector = new DienteErupcion();
	}

	public void clickEndodonciaSimple() {
		iSelector = new ExodonciaSimple();
	}

	public void clickDienteIncluidoSemiIncluido() {
		iSelector = new Ausente();
	}

	public void clickEndodoncia(Estado estado) {
		iSelector = new Endodoncia(estado);
	}

	public void clickAusente() {
		iSelector = new Extraido();
	}

	public void clickCorona(Estado estado) {
		iSelector = new CoronaBuena(estado);
	}

	public void clickProtesisRemovible() {
		iSelector = new ProtesisRemovible();
	}

	public void clickRestauracionDesadaptada() {
		iSelector = new RestauracionDesadaptada();
	}

	public void clickProtesisFija() {
		iSelector = new ProtesisFija();
	}

	public void clickNucleoPoste() {
		iSelector = new NucleoPostes();
	}

	public void clickIonomeros() {
		iSelector = new Ionomeros();
	}

	public void clickAbrasion() {
		iSelector = new SuperficieAbrasion();
	}

	public void clickOpturacionTemporal() {
		iSelector = new OpturacionTemporalConvencion();
	}

	/* opciones leary */
	public void clickDienteSanoLeary() {
		iSelectorLeary = new DienteSano();
	}

	public void clickSuperficieManchada() {
		iSelectorLeary = new SuperficieManchada();
	}

	/* fin odontograma acciones */

	/**
	 * getter and setter
	 * */

	public ZKWindow getWindowParent() {
		return windowParent;
	}

	public void setWindowParent(ZKWindow windowParent) {
		this.windowParent = windowParent;
	}

	public void showIndicedean(boolean indicedean) {
		this.indicedean.setVisible(indicedean);
	}

	public void showIndiceDental(boolean indiceDental) {
		this.indiceDental.setVisible(indiceDental);
	}

	public void showAccion_odontograma(boolean accion_odontograma) {
		this.accion_odontograma.setVisible(accion_odontograma);
	}

	public void showTipo_denticion(boolean tipo_denticion) {
		this.tipo_denticion.setVisible(tipo_denticion);
	}

	public void showOdonotograma(boolean grpOdonotograma) {
		this.grpOdonotograma.setVisible(grpOdonotograma);
	}

	public void setTitleOdontograma(String title) {
		this.captionOdontograma.setLabel(title);
	}

	public List<Diente> getDientes() throws Exception {
		/* inyectamos metodo a todos los dientes */
		return getDientes(DienteOdontograma.ALL);
	}

	public List<Diente> getDientes(DienteOdontograma dienteOdontograma)
			throws Exception {
		/* inyectamos metodo a todos los dientes */
		Field[] fields = this.getClass().getDeclaredFields();
		List<Diente> dientes = new ArrayList<Diente>();
		for (Field field : fields) {
			field.setAccessible(true);
			DienteEnum view = field.getAnnotation(DienteEnum.class);
			if (view != null) {
				boolean addDiente = false;
				if (dienteOdontograma != DienteOdontograma.ALL) {
					if (dienteOdontograma == DienteOdontograma.OLEARY) {
						addDiente = view.leary();
					} else {
						addDiente = !view.leary();
					}
				} else {
					addDiente = true;
				}
				if (addDiente) {
					DienteMacro dienteMacro = (DienteMacro) field.get(this);
					if (dienteMacro.isUtilizado()) {
						Diente diente = new Diente();
						diente.setLeary(view.leary() ? "S" : "N");
						diente.setNro_diente(dienteMacro.getNumber());
						diente.setSector_bottom(getTypeSector(dienteMacro
								.getiSectorDienteBottom()));
						diente.setSector_center(getTypeSector(dienteMacro
								.getiSectorDienteCenter()));
						diente.setSector_top(getTypeSector(dienteMacro
								.getiSectorDienteTop()));
						diente.setSector_left(getTypeSector(dienteMacro
								.getiSectorDienteLeft()));
						diente.setSector_rigth(getTypeSector(dienteMacro
								.getiSectorDienteRigth()));
						diente.setSector_superior(getTypeSector(dienteMacro
								.getiSectorDienteSuperficieSuperior()));
						dientes.add(diente);
					}
				}
			}
		}
		return dientes;
	}

	public void habilitarSoloLosMarcadoPorHacer() throws Exception {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			DienteEnum view = field.getAnnotation(DienteEnum.class);
			if (view != null) {
				DienteMacro dienteMacro = (DienteMacro) field.get(this);
				if (!dienteMacro.isMarcadoPorHacer()) {
					dienteMacro.setDisabled(true);
				}
			}
		}
	}

	public void resetOdontograma() throws Exception {
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			DienteEnum view = field.getAnnotation(DienteEnum.class);
			if (view != null) {
				DienteMacro dienteMacro = (DienteMacro) field.get(this);
				dienteMacro.reset();
			}
		}
	}

	private String getTypeSector(ISectorDiente iSectorDiente) {
		if (iSectorDiente != null) {
			return iSectorDiente.getTypeSector().toString();
		}
		return TypeSector.NONE.toString();
	}

	public int getEstraidosLeche() {
		return extraidosLeche;
	}

	public void setEstraidosLeche(int estraidosLeche) {
		this.extraidosLeche = estraidosLeche;
	}

	public int getSuperficiesManchadas() {
		return superficiesManchadas;
	}

	public void setSuperficiesManchadas(int superficiesManchadas) {
		this.superficiesManchadas = superficiesManchadas;

	}

	public int getCariadosAdulto() {
		return cariadosAdulto;
	}

	public void setCariadosAdulto(int cariadosAdulto) {
		this.cariadosAdulto = cariadosAdulto;
	}

	public int getOpturadosAdulto() {
		return opturadosAdulto;
	}

	public void setOpturadosAdulto(int opturadosAdulto) {
		this.opturadosAdulto = opturadosAdulto;
	}

	public int getPerdidosAdulto() {
		return perdidosAdulto;
	}

	public void setPerdidosAdulto(int perdidosAdulto) {
		this.perdidosAdulto = perdidosAdulto;
	}

	public int getCopAdulto() {
		return copAdulto;
	}

	public void setCopAdulto(int copAdulto) {
		this.copAdulto = copAdulto;
	}

	public int getCariadosLeche() {
		return cariadosLeche;
	}

	public void setCariadosLeche(int cariadosLeche) {
		this.cariadosLeche = cariadosLeche;
	}

	public int getOpturadoLeche() {
		return opturadoLeche;
	}

	public void setOpturadoLeche(int opturadoLeche) {
		this.opturadoLeche = opturadoLeche;
	}

	public int getCeoLeche() {
		return ceoLeche;
	}

	public void setCeoLeche(int ceoLeche) {
		this.ceoLeche = ceoLeche;
	}

	public void setTipoDenticion(String tipo_denticion) {
		Utilidades.setValueFrom(lbxModoOdontograma, tipo_denticion);
		cambioModoOdontograma();

	}

	public String getTipoDenticion() {
		return lbxModoOdontograma.getSelectedItem().getValue().toString();
	}

	/* Para obtener el service locator */
	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

	public Textbox getTbxPorcentajeManchado() {
		return tbxPorcentajeManchado;
	}

}
