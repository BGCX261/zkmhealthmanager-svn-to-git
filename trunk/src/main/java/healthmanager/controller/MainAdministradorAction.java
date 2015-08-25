package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Registros_entradas;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.Detalles_horariosService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.Registros_entradasService;
import healthmanager.modelo.service.Roles_usuariosService;
import healthmanager.modelo.service.Roles_usuarios_capsService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Footer;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.LabelImageElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.Res;
import com.framework.res.TabsValidator;
import com.softcomputo.composer.constantes.IParametrosSesion;

public class MainAdministradorAction extends GeneralComposer {

	// private static Logger log =
	// Logger.getLogger(MainAdministradorAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@WireVariable
	private Centro_atencionService centro_atencionService;
	@WireVariable
	private Roles_usuarios_capsService roles_usuarios_capsService;
	@WireVariable
	private Detalles_horariosService detalles_horariosService;
	@WireVariable
	private PrestadoresService prestadoresService;
	@WireVariable
	private Registros_entradasService registros_entradasService;
	@WireVariable
	private Roles_usuariosService roles_usuariosService;

	private List<Roles_usuarios> listado_roles;

	@View
	private Label lbUsuario;
	@View
	private Label lbEmpresa;
	@View
	private Label lbSucursal;
	@View
	private Label lbRoles;
	@View
	private Listbox lbxRoles;
	@View
	private Listbox lbxCaps_relacionado;

	private HttpSession httpSession;
	@View
	private Toolbarbutton cerrar_sesion;

	@View
	private Listbox lbxAnios;
	@View
	private Listbox lbxPeriodo;

	@View
	private Tabs tab_contenedora;
	@View
	private Tabpanels tabpanelcontenedora;

	private static final String TIPO_ROL = "1";
	private static final String TIPO_CAPS = "2";

	/* Menus */
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AFILIACIONES })
	@View
	private LabelImageElement menuAfiliaciones;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemFestivos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemArticulos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemConfiguracionServiciosAutorizacion;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemGraficosDB;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES,
			IRoles.CALIDAD, IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuMaestros;

	@MenuEnum(rols = { IRoles.MEDICO_CONS_EXTERNA, IRoles.MEDICO_URGENCIAS,
			IRoles.ODONTOLOGO, IRoles.MEDICO_PYP,
			IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.AUXILIAR_LABORATORIOS, IRoles.GINECOLOGO,
			IRoles.MEDICO_DE_ADULTO_MAYOR,
			IRoles.MEDICO_DE_ALTERACION_EMBARAZO,
			IRoles.MEDICO_DE_ALTERACION_JOVEN,
			IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO,
			IRoles.MEDICO_DE_HIPERTENSO_DIABETICO,
			IRoles.MEDICO_DE_PLANIFICACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.FACTURADOR_CAPS, IRoles.FACTURADOR_UPAS,
			IRoles.ENFERMERA_JEFE, IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.FARMACIA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.FISIOTERAPEUTA,
			IRoles.FISIOTERAPEUTA_FISICA, IRoles.CALIDAD,
			IRoles.MEDICO_DE_CUALQUIER_SERVICIO })
	@View
	private LabelImageElement menuAsistencial;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FISIOTERAPEUTA,
			IRoles.FISIOTERAPEUTA_FISICA })
	@View
	private LabelImageElement menuTerapias;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FISIOTERAPEUTA,
			IRoles.FISIOTERAPEUTA_FISICA })
	@View
	private LabelImageElement menuItemTerapiaFisica;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FISIOTERAPEUTA })
	@View
	private LabelImageElement menuItemTerapiaRespiratoria;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemSucursales;
	// //////////////////////

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.GINECOLOGO,
			IRoles.COORDINADOR, IRoles.FARMACIA, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuGinecologia;
	// /////////////////////////

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.MEDICO_URGENCIAS,
			IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_CAPS,
			IRoles.FARMACIA })
	@View
	private LabelImageElement menuUrgencia;
	// ////////////////////////////

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.MEDICO_URGENCIAS,
			IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemHojaGatos;
	// /////////////////////////////

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.MEDICO_URGENCIAS,
			IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemHisc_Recien_nacido;
	// //////////////////7

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.MEDICO_PYP,
			IRoles.MEDICO_DE_ADULTO_MAYOR,
			IRoles.MEDICO_DE_ALTERACION_EMBARAZO,
			IRoles.MEDICO_DE_ALTERACION_JOVEN,
			IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO,
			IRoles.MEDICO_DE_HIPERTENSO_DIABETICO,
			IRoles.MEDICO_DE_PLANIFICACION, IRoles.COORDINADOR,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS, IRoles.FARMACIA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.MEDICO_DE_CUALQUIER_SERVICIO })
	@View
	private LabelImageElement menuItemAtencionPyp;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuOpciones;
	// //////////////////////////

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ASIGNADOR_CITAS,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.DIGITADOR, IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR,
			IRoles.COORDINADOR, IRoles.ENFERMERA_JEFE,
			IRoles.ASIGNADOR_CITAS_CAPS, IRoles.ASIGNADOR_CITAS_UPAS,
			IRoles.CITAS_MEDICAS_CAJA })
	@View
	private LabelImageElement menuCitas;
	// ///////////////////////

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ODONTOLOGO,
			IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuRegistrosOdontologia;
	// ////////////////////////////

	@MenuEnum(rols = { IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuPsicologia;
	// /////////////////////////////

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.VACUNACION, IRoles.AUXILIAR_ENFERMERIA,
			IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION })
	@View
	private LabelImageElement menuEnfermeria;
	// ///////////////////////
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE,
			IRoles.VACUNACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuItemVacunasPai;
	// /////////////////////

	@MenuEnum(rols = { IRoles.MEDICO_URGENCIAS, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuHospitalizacion;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FARMACIA,
			IRoles.RECEPCIONISTA, IRoles.AUXILIAR_DE_AUDITORIA, IRoles.AUDITOR,
			IRoles.AUXILIAR_LABORATORIOS, IRoles.AUTORIZACIONES_EXTERNAS,
			IRoles.CITAS_MEDICAS_CAJA, IRoles.COMITE_TECNICO_CIENTIFICO })
	@View
	private LabelImageElement menuRecepcion;

	// /* MenuItems */
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemAdministradoras;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ADMISIONISTA_URGENCIAS,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_UPAS, IRoles.ASIGNADOR_CITAS, IRoles.DIGITADOR,
			IRoles.FACTURADOR, IRoles.ASIGNADOR_CITAS_CAPS,
			IRoles.ASIGNADOR_CITAS_UPAS, IRoles.CITAS_MEDICAS_CAJA })
	@View
	private LabelImageElement menuItemCitas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ADMISIONISTA_URGENCIAS })
	@View
	private LabelImageElement menuItemEdadVencimiento;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemTurnosApolloDiagnostico;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FACTURADOR_UPAS,
			IRoles.ASIGNADOR_CITAS, IRoles.DIGITADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuItemHorarioCitas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FACTURADOR_UPAS,
			IRoles.ASIGNADOR_CITAS, IRoles.DIGITADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.CITAS_MEDICAS_CAJA })
	@View
	private Menuitem menuItemConsultasCitas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES })
	@View
	private LabelImageElement menuItemPlantillaMetasPyp;

	// @MenuEnum(rols = { IRoles.ADMINISTRADOR })
	// @View
	// private LabelImageElement menuItemRedUniversitaria;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuActividadEconomica;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemAportantes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AFILIACIONES })
	@View
	private LabelImageElement menuItemAfiliaciones;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemAportes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemSolicitudTranslado;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemRespuestasTranslados;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemDescargaArchivos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES })
	@View
	private LabelImageElement menuItemJustificacion_clinica;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.RECEPCIONISTA,
			IRoles.CITAS_MEDICAS_CAJA })
	@View
	private LabelImageElement menuItemPagoCuotaModeradora;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemManualesTarifarios;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemContratos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.RECEPCIONISTA,
			IRoles.CITAS_MEDICAS_CAJA })
	@View
	private LabelImageElement menuItemAutorizaciones;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUTORIZACIONES_EXTERNAS })
	@View
	private LabelImageElement menuItemAutorizacionesExternas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemRemisiones;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemRecetasPrestadorExterno;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemResultadoLaboratorio;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUXILIAR_LABORATORIOS })
	@View
	private LabelImageElement menuItemTomaMuestras;

	// /* MenuItems */
	// asistencial
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.RECEPCIONISTA,
			IRoles.AUDITOR })
	@View
	private LabelImageElement menuItemContra_referencia;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUDITOR,
			IRoles.COMITE_TECNICO_CIENTIFICO })
	@View
	private LabelImageElement menuItemAutorizacionesNegacion;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUDITOR,
			IRoles.COMITE_TECNICO_CIENTIFICO })
	@View
	private LabelImageElement menuItemAutorizaciones_recetas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUDITOR,
			IRoles.COMITE_TECNICO_CIENTIFICO })
	@View
	private LabelImageElement menuCtC;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUDITOR,
			IRoles.COMITE_TECNICO_CIENTIFICO })
	@View
	private LabelImageElement menuItemActas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemRecetasFarmacia;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.RECEPCIONISTA,
			IRoles.FACTURADOR, IRoles.ASISTENTE, IRoles.COORDINADOR,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.ASIGNADOR_CITAS_CAPS,
			IRoles.ASIGNADOR_CITAS_UPAS, IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemAnexo2;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.RECEPCIONISTA,
			IRoles.FACTURADOR, IRoles.ASISTENTE, IRoles.COORDINADOR,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.ASIGNADOR_CITAS_CAPS,
			IRoles.ASIGNADOR_CITAS_UPAS, IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemAnexo3;

	// sala de espera
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuSala_de_espera;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private Menuitem menuItemmModulo_usuario;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS })
	@View
	private Menuitem menuItemModulo_coordinador_caps;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_SERVICIOS })
	@View
	private Menuitem menuItemModulo_coordinador_servicios;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private Menuitem menuItemModulo_administrador;

	// Modulo Agenda
	@MenuEnum(rols = { IRoles.MEDICO_CONS_EXTERNA, IRoles.MEDICO_URGENCIAS,
			IRoles.ODONTOLOGO, IRoles.MEDICO_PYP,
			IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.ADMINISTRADOR,
			IRoles.GINECOLOGO, IRoles.MEDICO_DE_ADULTO_MAYOR,
			IRoles.MEDICO_DE_ALTERACION_EMBARAZO,
			IRoles.MEDICO_DE_ALTERACION_JOVEN,
			IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO,
			IRoles.MEDICO_DE_HIPERTENSO_DIABETICO,
			IRoles.MEDICO_DE_PLANIFICACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.MEDICO_DE_CUALQUIER_SERVICIO })
	@View
	private LabelImageElement menuItemAgenda_citas;

	// ENFERMERIA
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuItemcitologia;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuItemenfermera_jefe;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUDITOR,
			IRoles.AUDITOR_HISTORIAS_CLINICAS })
	@View
	private Menuitem menuItemVerificarPacientes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private Menu menuHistoriasManuales;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private Menuitem menuItemRegHistoriasManuales;

	// ASISTENCIAL
	@MenuEnum(rols = { IRoles.MEDICO_CONS_EXTERNA, IRoles.MEDICO_URGENCIAS,
			IRoles.ODONTOLOGO, IRoles.MEDICO_PYP,
			IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.AUXILIAR_LABORATORIOS, IRoles.GINECOLOGO,
			IRoles.MEDICO_DE_ADULTO_MAYOR,
			IRoles.MEDICO_DE_ALTERACION_EMBARAZO,
			IRoles.MEDICO_DE_ALTERACION_JOVEN,
			IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO,
			IRoles.MEDICO_DE_HIPERTENSO_DIABETICO,
			IRoles.MEDICO_DE_PLANIFICACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.ENFERMERA_JEFE, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS, IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.FARMACIA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.AUDITOR_HISTORIAS_CLINICAS, IRoles.ADMISIONISTA_URGENCIAS,
			IRoles.CALIDAD, IRoles.MEDICO_DE_CUALQUIER_SERVICIO })
	@View
	private LabelImageElement menuItemConsultaHistoria;

	@MenuEnum(rols = { IRoles.MEDICO_CONS_EXTERNA, IRoles.MEDICO_URGENCIAS,
			IRoles.ODONTOLOGO, IRoles.MEDICO_PYP,
			IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.AUXILIAR_LABORATORIOS, IRoles.GINECOLOGO,
			IRoles.MEDICO_DE_ADULTO_MAYOR,
			IRoles.MEDICO_DE_ALTERACION_EMBARAZO,
			IRoles.MEDICO_DE_ALTERACION_JOVEN,
			IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO,
			IRoles.MEDICO_DE_HIPERTENSO_DIABETICO,
			IRoles.MEDICO_DE_PLANIFICACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.ENFERMERA_JEFE, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS, IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.FARMACIA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.CALIDAD,
			IRoles.MEDICO_DE_CUALQUIER_SERVICIO })
	@View
	private LabelImageElement menuItemAgenda_cita_prestador;

	@MenuEnum(rols = { IRoles.MEDICO_CONS_EXTERNA, IRoles.MEDICO_URGENCIAS,
			IRoles.ODONTOLOGO, IRoles.MEDICO_PYP,
			IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.AUXILIAR_LABORATORIOS, IRoles.GINECOLOGO,
			IRoles.MEDICO_DE_ADULTO_MAYOR,
			IRoles.MEDICO_DE_ALTERACION_EMBARAZO,
			IRoles.MEDICO_DE_ALTERACION_JOVEN,
			IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO,
			IRoles.MEDICO_DE_HIPERTENSO_DIABETICO,
			IRoles.MEDICO_DE_PLANIFICACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.FACTURADOR_CAPS, IRoles.FACTURADOR_UPAS,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.FARMACIA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.MEDICO_DE_CUALQUIER_SERVICIO })
	@View
	private LabelImageElement menuItemConsultaFicha;
	// Urgencias

	@MenuEnum(rols = { IRoles.MEDICO_URGENCIAS, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.FARMACIA, IRoles.ENFERMERA_JEFE_HOSPITALIZACION })
	@View
	private LabelImageElement menuItemModulo_hcuci;
	@MenuEnum(rols = { IRoles.MEDICO_URGENCIAS, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemModulo_hosp;

	@MenuEnum(rols = { IRoles.MEDICO_CONS_EXTERNA, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemConsulta_externa;

	@MenuEnum(rols = { IRoles.MEDICO_ESPECIALISTA, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemEspecializada;

	@MenuEnum(rols = { IRoles.MEDICO_URGENCIAS, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemUrgencias;

	@MenuEnum(rols = { IRoles.MEDICO_URGENCIAS, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.FARMACIA, IRoles.FACTURADOR_CAPS })
	@View
	private LabelImageElement menuItemHis_triage;

	@MenuEnum(rols = { IRoles.MEDICO_CONS_EXTERNA, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.AUDITOR,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuConsultaExterna;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUDITOR,
			IRoles.MEDICO_CONS_EXTERNA, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.MEDICO_PYP,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemControlTuberculosis0;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUDITOR,
			IRoles.MEDICO_CONS_EXTERNA, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.MEDICO_PYP,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemLepra0;

	// odontologia
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ODONTOLOGO,
			IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemOdontologia2;

	@MenuEnum(rols = { IRoles.ODONTOLOGO, IRoles.ADMINISTRADOR,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemUrgencia_Odontologica;

	// salud mental
	@MenuEnum(rols = { IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.ADMINISTRADOR, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemPsicologia;
	@MenuEnum(rols = { IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.ADMINISTRADOR, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemVisita_domiciliaria;
	@MenuEnum(rols = { IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.ADMINISTRADOR, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemPsiquiatria;

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////***********************************
	// Servicios***********************************////////////////////////////
	// //
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS, IRoles.COORDINADOR,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.AUDITOR })
	@View
	private LabelImageElement menuServicios;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemDatos_consultas_lote;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemDatos_procedimiento_lote;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemDatos_procedimiento_multiple;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS })
	@View
	private LabelImageElement menuItemFacturacion_medicamentos;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS })
	@View
	private LabelImageElement menuItemFacturacion_materiales;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.COORDINADOR, IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemFacturacion_servicios;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS })
	@View
	private LabelImageElement menuItemHospitalizacion;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION })
	@View
	private LabelImageElement menuItemRecien_nacido;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION })
	@View
	private LabelImageElement menuItemRipsUrgencias;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION })
	@View
	private LabelImageElement menuEgresos;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	public LabelImageElement menuItemFacturacion;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.ADMISIONISTA_URGENCIAS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemAnularFacturas;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_CAPS })
	@View
	private LabelImageElement menuItemPrefacturacion;

	@MenuEnum(rols = {IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemFacturacion_radicada;

	@MenuEnum(rols = {IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemRecalcular_factura;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuArchivos_planos;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemRips;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuFacturacionAgrupadaCapitada;
	
	@MenuEnum(rols = { IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemConsultarFacturacionAgrupaCapitada;

	@MenuEnum(rols = {IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.AUDITOR })
	@View
	private LabelImageElement menuItemGlosaDevoluciones;

	@MenuEnum(rols = {IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.AUDITOR })
	@View
	private LabelImageElement menuFacturacionGlosasDevoluciones;

	@MenuEnum(rols = {IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.AUDITOR })
	@View
	private LabelImageElement menuItemRipsPrestadores;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS })
	@View
	private LabelImageElement menuAnexo1_fur_fut;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS })
	@View
	private LabelImageElement menuItemFurtran;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS })
	@View
	private LabelImageElement menuItemFurips;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_CAPS,
			IRoles.FACTURADOR_UPAS })
	@View
	private LabelImageElement menuItemAnexo1;

	// Maestros //
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemAnio_soat;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemCopago_estrato;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemLocalidades;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemBarrios;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemCentro_atencion;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FACTURADOR })
	@View
	private LabelImageElement menuItemCamas;

	// Adminisiones //
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.RECEPCIONISTA,
			IRoles.FACTURADOR_ADMINISTRATIVA, IRoles.FACTURADOR_UPAS,
			IRoles.FACTURADOR_CAPS, IRoles.ADMISIONISTA_URGENCIAS,
			IRoles.ASIGNADOR_CITAS, IRoles.DIGITADOR, IRoles.FACTURADOR,
			IRoles.ASISTENTE, IRoles.COORDINADOR, IRoles.ASIGNADOR_CITAS_CAPS,
			IRoles.ASIGNADOR_CITAS_UPAS, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.CITAS_MEDICAS_CAJA })
	@View
	private LabelImageElement menuAdmisiones;

	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.FACTURADOR_CAPS, IRoles.FACTURADOR_UPAS,
			IRoles.ADMINISTRADOR, IRoles.ADMISIONISTA_URGENCIAS })
	@View
	private LabelImageElement menuItemModulo_cons_fact_urgencias;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.RECEPCIONISTA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.FACTURADOR,
			IRoles.ASIGNADOR_CITAS, IRoles.ASISTENTE,
			IRoles.AUDITOR_HISTORIAS_CLINICAS, IRoles.ASIGNADOR_CITAS_UPAS,
			IRoles.CITAS_MEDICAS_CAJA, IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemAdmisionPacientes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.ASISTENTE })
	@View
	private LabelImageElement menuItemAditoriaAdmisiones;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.ASISTENTE })
	@View
	private LabelImageElement menuAuditorias_procesos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.ASISTENTE })
	@View
	private LabelImageElement menuItemFacturacion_faltantes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.ASISTENTE })
	@View
	private LabelImageElement menuItemAditoriaAdmisionesAtendidas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ASIGNADOR_CITAS,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.DIGITADOR, IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS,
			IRoles.RECEPCIONISTA, IRoles.FACTURADOR, IRoles.ASISTENTE,
			IRoles.ADMISIONISTA_URGENCIAS, IRoles.ASIGNADOR_CITAS_CAPS,
			IRoles.ASIGNADOR_CITAS_UPAS, IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemPaciente;
	@MenuEnum(rols = { IRoles.FACTURADOR, IRoles.ADMINISTRADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.RECEPCIONISTA, IRoles.ADMISIONISTA_URGENCIAS,
			IRoles.ASISTENTE })
	@View
	private LabelImageElement menuOrdenes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FACTURADOR_ADMINISTRATIVA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.FARMACIA })
	@View
	private LabelImageElement menuItemPorcentajeAdicional;

	// Administracion
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuAdministracion;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuItemUsuario;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemEmpresa;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemModuloFirma;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuIteParametros;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuIteConsecutivo;
	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemImportarDB;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemServiciosCentro_atencion;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemVacunas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CALIDAD })
	@View
	private LabelImageElement menuItemGruposEtareos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES })
	@View
	private LabelImageElement menuItemGruposProcedimientos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuIteParametros_signos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemPeriodos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemCuotasModeradoras;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuConfiguracionPyP;

	// Citas
	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemConsultorios;

	// Reportes
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AFILIACIONES,
			IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.ENFERMERA_JEFE,
			IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.AUDITOR,
			IRoles.ESTADISTICO })
	@View
	private LabelImageElement menuItemReportes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.AUXILIAR_LABORATORIOS,
			IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuItemImagenes_laboratorios;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ASIGNADOR_CITAS,
			IRoles.DIGITADOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private LabelImageElement menuItemOrdenes_medicas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemProcedimientos_act;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemActualizar_estancias;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuConfiguraciones_vias_ingreso;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemVias_ingreso;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemAlertas_epidemiologicas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FACTURADOR,
			IRoles.COORDINADOR, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.ENFERMERA_JEFE,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS,
			IRoles.CITAS_MEDICAS_CAJA })
	@View
	private LabelImageElement menuItemCitasPorCaps;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.CONTRATACIONES,
			IRoles.FACTURADOR_ADMINISTRATIVA })
	@View
	private LabelImageElement menuItemPaquetes_servicios;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS })
	@View
	private Menuitem menuItemSignos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.ENFERMERA_JEFE,
			IRoles.AUXILIAR_ENFERMERIA, IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private Menuitem menuItemControl_signos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.ENFERMERA_JEFE,
			IRoles.AUXILIAR_ENFERMERIA, IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private Menuitem menuItemServicios_ambulatorios;

	@MenuEnum(rols = { IRoles.MEDICO_CONS_EXTERNA, IRoles.MEDICO_URGENCIAS,
			IRoles.ODONTOLOGO, IRoles.MEDICO_PYP,
			IRoles.PROFESIONAL_SALUD_MENTAL, IRoles.ADMINISTRADOR,
			IRoles.AUDITOR, IRoles.AUXILIAR_LABORATORIOS, IRoles.GINECOLOGO,
			IRoles.MEDICO_DE_ADULTO_MAYOR,
			IRoles.MEDICO_DE_ALTERACION_EMBARAZO,
			IRoles.MEDICO_DE_ALTERACION_JOVEN,
			IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO,
			IRoles.MEDICO_DE_HIPERTENSO_DIABETICO,
			IRoles.MEDICO_DE_PLANIFICACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.ENFERMERA_JEFE,
			IRoles.AUXILIAR_ENFERMERIA, IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.FACTURADOR_CAPS, IRoles.FACTURADOR_UPAS, IRoles.FARMACIA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS })
	@View
	private Menuitem menuItemHistoriasSios;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION })
	@View
	private LabelImageElement menuItemControlTuberculosis;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION })
	@View
	private LabelImageElement menuItemLepra;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS, IRoles.ENFERMERA_JEFE_HOSPITALIZACION })
	@View
	private Menuitem menuItemNotas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR_CAPS,
			IRoles.COORDINADOR_SERVICIOS })
	@View
	private Menuitem menuItemRegMed;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.FACTURADOR })
	@View
	private Menuitem menuItemProcedimientosServicios;

	// Indicadores de Calidad
	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.MEDICO_URGENCIAS,
			IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION,
			IRoles.ENFERMERA_JEFE_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private LabelImageElement menuIndicadores_calidad;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.MEDICO_URGENCIAS,
			IRoles.ENFERMERA_JEFE_URGENCIAS,
			IRoles.AUXILIAR_ENFERMERIA_URGENCIA,
			IRoles.AUXILIAR_HOSPITALIZACION, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private Menuitem menuItemmIndicadores_calidad;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private Menuitem menuItemmOportunidad_cita;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private Menuitem menuItemmOportunidad_consulta;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private Menuitem menuItemmOportunidad_cita_pacientes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private Menuitem menuItemmOportunidad_consulta_pacientes;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private Menuitem menuItemmOportunidad_consulta_urgencias;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private Menuitem menuItemmConsolidado_consulta;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.CALIDAD })
	@View
	private Menuitem menuItemmConsolidado_procedimientos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private Menuitem menuItemServicios_via_ingreso;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private Menuitem menuItemVia_ingreso_consultas;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private Menuitem menuItemConfig_admision_ingreso;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR, IRoles.COORDINADOR,
			IRoles.FACTURADOR_UPAS, IRoles.FACTURADOR_CAPS, IRoles.FARMACIA,
			IRoles.COORDINADOR_CAPS, IRoles.COORDINADOR_SERVICIOS,
			IRoles.MEDICO_DE_CUALQUIER_SERVICIO })
	@View
	private LabelImageElement menuItemAtencionCualquier;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemArbol_servicios;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuArticulos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemMaestroArticulos;

	@MenuEnum(rols = { IRoles.ADMINISTRADOR })
	@View
	private LabelImageElement menuItemMaestroMedicamentos_pos;

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface MenuEnum {

		String[] rols();
	}

	public void inicializarRolesUsuarios() {
		lbUsuario.setValue((usuarios.getNombres() + " " + usuarios
				.getApellidos()).toUpperCase());
		lbEmpresa.setValue(getEmpresa().getNombre_empresa());
		lbSucursal.setValue(getSucursal().getNombre_sucursal());
		lbRoles.setValue("Rol usuario:");

		lbxRoles.getChildren().clear();

		boolean seleccionado = false;

		for (Roles_usuarios rol : listado_roles) {
			Elemento elementoRol = rol.getElementoRol();
			String nombre_rol = (elementoRol != null ? elementoRol
					.getDescripcion() : "");
			Listitem listitem = new Listitem(nombre_rol.toUpperCase(),
					rol.getRol());
			if (rol.getRol().equals(rol_usuario)) {
				listitem.setSelected(true);
				seleccionado = true;
			}
			lbxRoles.appendChild(listitem);
		}

		if (seleccionado) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("codigo_usuario", usuarios.getCodigo());
			parametros.put("rol", lbxRoles.getSelectedItem().getValue()
					.toString());

			List<Roles_usuarios_caps> listado_caps_asignados = roles_usuarios_capsService
					.listar(parametros);

			lbxCaps_relacionado.getChildren().clear();

			seleccionado = false;

			for (Roles_usuarios_caps roles_usuarios_caps : listado_caps_asignados) {
				Centro_atencion centro_atencion = new Centro_atencion();
				centro_atencion.setCodigo_empresa(roles_usuarios_caps
						.getCodigo_empresa());
				centro_atencion.setCodigo_sucursal(roles_usuarios_caps
						.getCodigo_sucursal());
				centro_atencion.setCodigo_centro(roles_usuarios_caps
						.getCodigo_centro());
				centro_atencion = centro_atencionService
						.consultar(centro_atencion);
				if (centro_atencion != null) {
					Listitem listitem = new Listitem(
							centro_atencion.getCodigo_centro() + "-"
									+ centro_atencion.getNombre_centro(),
							centro_atencion);
					lbxCaps_relacionado.appendChild(listitem);
					if (centro_atencion_session != null) {
						if (centro_atencion_session.getCodigo_empresa().equals(
								centro_atencion.getCodigo_empresa())
								&& centro_atencion_session.getCodigo_sucursal()
										.equals(centro_atencion
												.getCodigo_sucursal())
								&& centro_atencion_session.getCodigo_centro()
										.equals(centro_atencion
												.getCodigo_centro())) {
							listitem.setSelected(true);
							seleccionado = true;
						}
					}
				} else {
					// log.info("NO existe el centro ===> "
					// + roles_usuarios_caps.getCodigo_centro());
				}
			}

			if (!seleccionado) {
				if (lbxCaps_relacionado.getItemCount() > 0) {
					lbxCaps_relacionado.setSelectedIndex(0);
					centro_atencion_session = (Centro_atencion) lbxCaps_relacionado
							.getSelectedItem().getValue();
					actualizarParametroEnSesion(
							IParametrosSesion.PARAM_CENTRO_ATENCION,
							centro_atencion_session);
				} else {
					throw new ValidacionRunTimeException(
							"Para este  usuario no aplica el de "
									+ lbxRoles.getSelectedItem().getLabel()
									+ " en ninguno de los caps o centros de atencion registrados");
				}

			}

		} else {
			throw new ValidacionRunTimeException(
					"El rol del paciente no se encuentra regostrado en la base de datos");
		}

	}

	// private String validateString(String in, int maxSize) {
	// if (in.length() > maxSize) {
	// return in.substring(0, maxSize - 1) + "...";
	// } else
	// return in;
	// }
	private void cargarMenuDependiendoRol(String rol) {
		// Ocultamos todos los menus
		visibleMenu(false);
		/* cargamos menus requerido */
		loadMenus(getRolDependiendoValidaciones(rol));
		// Colocamos ocultamos los menus no disponibles por los
		// Parametros
		setVisibleMenuPorParametro();

		// mostramos el menu de triage para la enfermera jefe cuando el centro
		// de atencion lo tenga configurado
		if (rol.equals(IRoles.ENFERMERA_JEFE_URGENCIAS)
				|| rol.equals(IRoles.ENFERMERA_JEFE_HOSPITALIZACION)) {
			if (centro_atencion_session != null) {
				if (centro_atencion_session.getTriage_enfermera()
						.equalsIgnoreCase("S")) {
					if (!menuAsistencial.isVisible()) {
						menuAsistencial.setVisible(true);
					}
					if (!menuUrgencia.isVisible()) {
						menuUrgencia.setVisible(true);
					}
					if (!menuItemHis_triage.isVisible()) {
						menuItemHis_triage.setVisible(true);
					}
				} else {
					menuItemHis_triage.setVisible(false);
				}
			}
		}

	}

	/**
	 * Este metodo me permite saber con que rol va ha trabar el usuario
	 *
	 * @author Luis Miguel
	 *
	 */
	private String[] getRolDependiendoValidaciones(String rol) {
		// Verificamos roles
		// Esta opcion es para verificar cuando la enfermera gefe o el auxiliar,
		// tengan asignado los programas X de PyP, le aparesca que pueda atender
		// al paciente
		if (rol.equals("")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", codigo_empresa);
			map.put("codigo_sucursal", codigo_sucursal);
			map.put("codigo_medico", usuarios.getCodigo());
			map.put("codigo_rol", rol);
			// log.info("@getRolDependiendoValidaciones Map: " + map);
			List<Map<String, Object>> listViasIngreso = detalles_horariosService
					.getViasIngresoAsignadas(map);
			// log.info("@getRolDependiendoValidaciones listado via ingreso: "
			// + listViasIngreso.size());
			List<String> listRoles = new ArrayList<String>();
			listRoles.add(rol);
			for (Map<String, Object> mapViaIngreso : listViasIngreso) {
				String via_ingreso = (String) mapViaIngreso.get("via_ingreso");
				if (via_ingreso != null) {
					String[][] vias_ingresoRol = new String[][] {
							{ IVias_ingreso.HC_DETECCION_ALT_EMBARAZO,
									IRoles.MEDICO_DE_ALTERACION_EMBARAZO },
							{ IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS,
									IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO },
							{ IVias_ingreso.HC_MENOR_2_MESES_2_ANOS,
									IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO },
							{ IVias_ingreso.HC_MENOR_2_MESES,
									IRoles.MEDICO_DE_CRECIMIENTO_DESARROLLO },
							{ IVias_ingreso.ADULTO_MAYOR,
									IRoles.MEDICO_DE_ADULTO_MAYOR },
							{ IVias_ingreso.SALUD_ORAL, IRoles.ODONTOLOGO },
							{ IVias_ingreso.ALTERACION_JOVEN,
									IRoles.MEDICO_DE_ALTERACION_JOVEN },
							{ IVias_ingreso.HIPERTENSO_DIABETICO,
									IRoles.MEDICO_DE_HIPERTENSO_DIABETICO },
							{ IVias_ingreso.PLANIFICACION_FAMILIAR,
									IRoles.MEDICO_DE_PLANIFICACION } };
					for (String[] via_rol : vias_ingresoRol) {
						String via_ingreso_temp = via_rol[0];
						String rolTemp = via_rol[1];
						if (via_ingreso_temp.equals(via_ingreso)) {
							listRoles.add(rolTemp);
						}
					}
				} else {
					// log.info("@getRolDependiendoValidaciones Via de ingreso viane nula");
				}
			}
			return listRoles.toArray(new String[] {});
		} else {
			return new String[] { rol };
		}
	}

	private void loadMenus(String... rol) {
		try {
			String rolAux = convertirComparador(rol);
			// log.info("@loadMenus codigo rol: " + rolAux);
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				MenuEnum view = field.getAnnotation(MenuEnum.class);
				if (view != null) {
					Object object = this.getFellow("" + field.getName());
					boolean visible = isAceptMenu(rolAux, view.rols());
					if (object instanceof LabelImageElement) {
						((LabelImageElement) object).setVisible(visible);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private String convertirComparador(String[] roles) {
		String salida = "";
		for (String rol : roles) {
			salida += "[" + rol + "]";
		}
		return salida;
	}

	@SuppressWarnings("unused")
	private String getRolPorEspecialidad() {
		Prestadores prestadores = new Prestadores();
		prestadores.setCodigo_empresa(codigo_empresa);
		prestadores.setCodigo_sucursal(codigo_sucursal);
		prestadores.setNro_identificacion(usuarios.getCodigo());
		prestadores = prestadoresService.consultar(prestadores);
		if (prestadores != null) {
			// log.info("Prestador: " + prestadores);
			return "05" + prestadores.getCodigo_especialidad();
		}
		return "05";
	}

	private void setVisibleMenuPorParametro() {
		try {
			if (parametros_empresa != null) {
				if (!parametros_empresa.getEntrega_r_caja().equals("01")) {
					cambiarEstadoItem(menuItemPagoCuotaModeradora, false);
				}
				if (!parametros_empresa.getTrabaja_consltaext().equals("S")) {
					cambiarEstadoItem(menuItemConsulta_externa, false);
				}
				if (!parametros_empresa.getTrabaja_hopt().equals("S")) {
					cambiarEstadoItem(menuItemModulo_hosp, false);
				}
				if (!parametros_empresa.getTrabaja_uci().equals("S")) {
					cambiarEstadoItem(menuItemModulo_hcuci, false);
				}
				if (!parametros_empresa.getTrabaja_urg().equals("S")) {
					cambiarEstadoItem(menuItemUrgencias, false);
				}

				// if (!parametros_empresa.getTrabaja_autorizacion()) {
				// //log.info("6");
				// chageVisibleState(menuItemAutorizaciones, false);
				// }
				if (getSucursal().getTipo().equals(
						IConstantes.TIPOS_SUCURSAL_CAJA_PREV)) {
					menuItemAdministradoras.setLabel("Prestadores");
					if (rol_usuario.equals(IRoles.FARMACIA)) {
						menuAsistencial.setVisible(false);
					}
				} else {
					cambiarEstadoItem(
							menuItemConfiguracionServiciosAutorizacion, false);
					cambiarEstadoItem(menuItemAutorizaciones, false);
					cambiarEstadoItem(menuAfiliaciones, false);
					cambiarEstadoItem(menuItemRecetasPrestadorExterno, false);
					cambiarEstadoItem(menuItemPorcentajeAdicional, false);
				}

				if (!parametros_empresa.isHabilitar_terapia_fisica()
						&& !parametros_empresa.isHabilitar_terapia_fisica()) {
					cambiarEstadoItem(menuTerapias, false);
				} else {
					if (!parametros_empresa.isHabilitar_terapia_fisica()) {
						cambiarEstadoItem(menuItemTerapiaFisica, false);
					}
					if (!parametros_empresa.isHabilitar_terapia_fisica()) {
						cambiarEstadoItem(menuItemTerapiaRespiratoria, false);
					}
				}

				if (!parametros_empresa.isHabilitar_consulta_especializada()) {
					cambiarEstadoItem(menuItemEspecializada, false);
				}

				if (parametros_empresa.getHabilitar_ordenes_sin_admision()
						.equals("N")) {
					menuOrdenes.setVisible(false);
				}

				if (resolucion.getAfectar_kardex_fact()) {
					menuItemArticulos.setVisible(false);
				}
			}
		} catch (Exception e) {
			// httpSession.invalidate();
			// Executions.getCurrent().sendRedirect("../", "_top");
			e.printStackTrace();
		}
	}

	private void cambiarEstadoItem(LabelImageElement menuitem, boolean visible) {
		if (menuitem != null) {
			menuitem.setVisible(visible);
		} else {
			// log.info("El Menu Item " + menuitem + " esta nulo");
		}
	}

	private boolean isAceptMenu(String rol, String[] aceptRols) {
		for (String rolAcept : aceptRols) {
			if (rol.contains("[" + rolAcept + "]")) {
				return true;
			}
		}
		return false;
	}

	public void visibleMenu(boolean visible) {
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				MenuEnum view = field.getAnnotation(MenuEnum.class);
				if (view != null) {
					Object object = this.getFellow("" + field.getName());
					if (object instanceof LabelImageElement) {
						((LabelImageElement) object).setVisible(visible);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void cambiarRoles(final String tipo, String descriptor)
			throws Exception {
		Clients.confirmClose(null);
		final Window componente = (Window) Executions.createComponents(
				"/pages/cambio_rol.zul", null, null);

		// log.info("Centro atencion sesSIÓN: "
		// + (centro_atencion_session != null ? centro_atencion_session
		// .getCodigo_centro() : ""));
		Label labelDescripcion = (Label) componente.getFellow("label");
		final Listbox lbxCaps_relacionadoCambio = (Listbox) componente
				.getFellow("lbxCaps_relacionadoCambio");
		Hbox hbox = (Hbox) componente.getFellow("hboxContenedorCaps");
		if (tipo.equals(TIPO_CAPS)) {
			labelDescripcion
					.setValue("¿Esta seguro que desea cambiar al centro de atencion: "
							+ descriptor + "?");
			hbox.setVisible(false);
		} else {
			labelDescripcion.setValue("¿Esta seguro que desea cambiar al Rol: "
					+ descriptor + "?");
			hbox.setVisible(true);

			// Permitimos actualizar los Caps
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("codigo_usuario", usuarios.getCodigo());
			parametros.put("rol", lbxRoles.getSelectedItem().getValue()
					.toString());

			List<Roles_usuarios_caps> listado_caps_asignados = roles_usuarios_capsService
					.listar(parametros);

			lbxCaps_relacionadoCambio.getChildren().clear();
			boolean seleccionado = false;
			for (Roles_usuarios_caps roles_usuarios_caps : listado_caps_asignados) {
				Centro_atencion centro_atencion = new Centro_atencion();
				centro_atencion.setCodigo_empresa(roles_usuarios_caps
						.getCodigo_empresa());
				centro_atencion.setCodigo_sucursal(roles_usuarios_caps
						.getCodigo_sucursal());
				centro_atencion.setCodigo_centro(roles_usuarios_caps
						.getCodigo_centro());
				centro_atencion = centro_atencionService
						.consultar(centro_atencion);
				if (centro_atencion != null) {
					Listitem listitem = new Listitem(
							centro_atencion.getCodigo_centro() + "-"
									+ centro_atencion.getNombre_centro(),
							centro_atencion);
					lbxCaps_relacionadoCambio.appendChild(listitem);
					if (centro_atencion_session != null) {
						if (centro_atencion_session.getCodigo_empresa().equals(
								centro_atencion.getCodigo_empresa())
								&& centro_atencion_session.getCodigo_sucursal()
										.equals(centro_atencion
												.getCodigo_sucursal())
								&& centro_atencion_session.getCodigo_centro()
										.equals(centro_atencion
												.getCodigo_centro())) {
							listitem.setSelected(true);
							seleccionado = true;
						}
					}
				}
			}

			if (!seleccionado) {
				if (lbxCaps_relacionadoCambio.getItemCount() > 0) {
					lbxCaps_relacionadoCambio.setSelectedIndex(0);
				}
			}

		}

		componente.addEventListener(Events.ON_CLOSE,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						inicializarRolesUsuarios();
					}
				});

		Button btnAceptar = (Button) componente.getFellow("btnAceptar");
		if (!tipo.equals(TIPO_CAPS)) {
			btnAceptar.setDisabled(lbxCaps_relacionadoCambio.getItems()
					.isEmpty());
		}

		btnAceptar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						getSession();
						if (tipo.equals(TIPO_CAPS)) {
							Centro_atencion centro_atencion = (Centro_atencion) lbxCaps_relacionado
									.getSelectedItem().getValue();
							actualizarParametroEnSesion(
									IParametrosSesion.PARAM_CENTRO_ATENCION,
									centro_atencion);
						} else {
							String key_rol = (String) lbxRoles
									.getSelectedItem().getValue();
							actualizarParametroEnSesion(
									IParametrosSesion.PARAM_ROL_USUARIO,
									key_rol);
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("codigo_empresa", codigo_empresa);
							parametros.put("codigo_sucursal", codigo_sucursal);
							parametros.put("codigo_usuario",
									usuarios.getCodigo());
							parametros.put("rol", key_rol);
							parametros.put("tipo", tipo);
							List<Centro_atencion> listado_caps = centro_atencionService
									.listar(parametros);
							if (listado_caps.isEmpty()) {
								actualizarParametroEnSesion(
										IParametrosSesion.PARAM_CENTRO_ATENCION,
										null);
							} else {
								actualizarParametroEnSesion(
										IParametrosSesion.PARAM_CENTRO_ATENCION,
										lbxCaps_relacionadoCambio
												.getSelectedItem() != null ? (Centro_atencion) lbxCaps_relacionadoCambio
												.getSelectedItem().getValue()
												: null);
							}
						}
						Executions.getCurrent().sendRedirect(null);

					}
				});
		Button btnCancelar = (Button) componente.getFellow("btnCancelar");
		btnCancelar.addEventListener("onClick", new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				inicializarRolesUsuarios();
				componente.detach();
			}
		});
		componente.setMode("modal");
	}

	private void getSession() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		httpSession = request.getSession();
	}

	public void addTab(String name, String url) {
		TabsValidator.addTabParam(name, url, tab_contenedora,
				tabpanelcontenedora, this);
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		cargarMenuDependiendoRol(rol_usuario);
		if (parametros_empresa == null) {
			// log.info("La empresa " + codigo_empresa
			// + " no tiene parametros de empresa");
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"La empresa no posee parámetros de empresa",
					IConstantes.TIEMPO_NOTIFICACIONES_MEDIO);
		}
	}

	private void cerrarSesion() {
		try {
			Clients.confirmClose("");
			getSession();
			if (httpSession.getAttribute("registros_entradas") != null) {
				Registros_entradas registros_entradas = (Registros_entradas) httpSession
						.getAttribute("registros_entradas");
				registros_entradas.setFecha_egreso(new Timestamp(new Date()
						.getTime()));
				registros_entradas.setEstado("2");
				registros_entradasService.actualizar(registros_entradas);
				httpSession.setAttribute("registros_entradas",
						registros_entradas);
			}
			httpSession.invalidate();
			Executions.getCurrent().sendRedirect("../", "_top");
		} catch (Exception e) {
		}
	}

	@Override
	public void init() throws Exception {
		Clients.confirmClose(null);
		loadPeriodo();
		cerrar_sesion.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				final Window window = new Window("Estado de sesion", "normal",
						false);

				Grid grid = new Grid();
				grid.setWidth("100%");
				Columns columns = new Columns();
				columns.appendChild(new Column());
				grid.appendChild(columns);
				window.appendChild(grid);

				Rows rows = new Rows();
				grid.appendChild(rows);

				Row row = new Row();
				rows.appendChild(row);
				Cell cell = new Cell();
				cell.setAlign("center");
				cell.appendChild(new Label(
						"¿Estas seguro que deseas cerrar sesion?"));
				row.appendChild(cell);

				Foot foot = new Foot();
				grid.appendChild(foot);
				Footer footer = new Footer();
				footer.setAlign("center");
				foot.appendChild(footer);

				Toolbarbutton tbnAceptar = new Toolbarbutton("Cerrar sesion",
						"/images/quit.png");
				Toolbarbutton tbnCancelar = new Toolbarbutton(
						"Continuar en el Sistema", "/images/open.png");

				tbnAceptar.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								cerrarSesion();
							}
						});

				tbnCancelar.addEventListener(Events.ON_CLICK,
						new EventListener<Event>() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								window.detach();
							}
						});

				footer.appendChild(tbnAceptar);
				footer.appendChild(tbnCancelar);

				window.setWidth("300px");
				window.setHeight("100px");
				window.setPage(getPage());
				window.doModal();
			}
		});

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
		parameters.put("codigo_sucursal", usuarios.getCodigo_sucursal());
		parameters.put("codigo_usuario", usuarios.getCodigo());
		listado_roles = roles_usuariosService.listar(parameters);

		inicializarRolesUsuarios();
		lbxRoles.addEventListener("onSelect", new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				cambiarRoles(TIPO_ROL, lbxRoles.getSelectedItem().getLabel());
			}
		});

		lbxCaps_relacionado.addEventListener("onSelect",
				new EventListener<Event>() {
					@Override
					public void onEvent(Event event) throws Exception {
						cambiarRoles(TIPO_CAPS, lbxCaps_relacionado
								.getSelectedItem().getLabel());
					}
				});

		saludar();
		Clients.confirmClose("Advertencia");

		menuItemHistoriasSios.setVisible(getEmpresa().getUtiliza_info_sio());

		if (parametros_empresa != null) {
			if (parametros_empresa.getSignos_enfermera().equals("S")) {
				if (!menuItemSignos.isVisible()) {
					menuItemSignos.setVisible(true);
				}
			} else {
				menuItemSignos.setVisible(false);
			}
		}
		// inicializarEventoComunicado();
	}

	private void saludar() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();
		request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		if (request.getSession().getAttribute("saludo") != null) {
			Notificaciones.mostrarNotificacionInformacion("Bienvenido",
					getSaludo() + " " + (usuarios.getNombres()) + "!",
					IConstantes.TIEMPO_NOTIFICACIONES_MEDIO);
			request.getSession().removeAttribute("saludo");
		}
	}

	private String getSaludo() {
		Calendar calendar = Calendar.getInstance();
		int hora = calendar.get(Calendar.HOUR_OF_DAY);
		if (hora > 18) {
			return "Buenas noches";
		} else if (hora >= 12) {
			return "Buenas tardes";
		}
		return "Buenos días";
	}

	private void loadPeriodo() {
		/* cargamos anios */
		lbxAnios.getChildren().clear();
		for (String year : Res.getAnnos(5)) {
			Listitem listitem = new Listitem();
			listitem.setValue(year);
			listitem.setLabel(year);
			lbxAnios.appendChild(listitem);
		}
		if (lbxAnios.getItemCount() > 0) {
			lbxAnios.setSelectedIndex(0);
		}

		/* cargamos periodos */
		lbxPeriodo.getChildren().clear();
		for (int i = 0; i < 12; i++) {
			Listitem listitem = new Listitem();
			listitem.setValue(i);
			listitem.setLabel((Res.getNombreMes(i) + "").toUpperCase());
			lbxPeriodo.appendChild(listitem);
		}
		if (lbxPeriodo.getItemCount() > 0) {
			lbxPeriodo.setSelectedIndex(0);
		}
		initPeriodo();
		loadEvent();
	}

	private void loadEvent() {
		EventListener<Event> eventListener = new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				HttpServletRequest request = (HttpServletRequest) Executions
						.getCurrent().getNativeRequest();
				request.getSession().setAttribute(
						"anio",
						Integer.parseInt(lbxAnios.getSelectedItem().getValue()
								.toString()));
				request.getSession().setAttribute("mes",
						lbxPeriodo.getSelectedItem().getValue());
				Executions.getCurrent().sendRedirect("");
			}
		};
		lbxPeriodo.addEventListener("onSelect", eventListener);
		lbxAnios.addEventListener("onSelect", eventListener);
	}

	private void initPeriodo() {
		lbxPeriodo.setSelectedIndex(mes);
		for (int i = 0; i < lbxAnios.getItemCount(); i++) {
			Listitem listitem = lbxAnios.getItemAtIndex(i);
			if (listitem.getValue().toString().equals("" + anio)) {
				listitem.setSelected(true);
				break;
			}
		}
	}

	@Override
	public void onError(Throwable throwable) {
	}

	public void restringirBackspace() {
		this.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {

			}
		});
	}

}
