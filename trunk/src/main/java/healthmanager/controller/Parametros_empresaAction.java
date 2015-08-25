/*
 * parametros_empresaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Resolucion;

import java.io.File;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.res.Res;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.softcomputo.composer.constantes.IParametrosSesion;

import healthmanager.modelo.service.GeneralExtraService;

public class Parametros_empresaAction extends ZKWindow {

	// private static Logger log =
    // Logger.getLogger(Parametros_empresaAction.class);
    // Componentes //
    // @View private Listbox lbxParameter;
    // @View private Textbox tbxValue;
    // @View private Textbox tbxAccion;
    @View
    private Borderlayout groupboxEditar;
    // @View private Groupbox groupboxConsulta;
    // @View private Rows rowsResultado;
    // @View private Grid gridResultado;

    @View
    private Listbox lbxTipo_historia_clinica;
    @View
    private Listbox lbxCobrar_cuota_moderadora_cita;
    @View
    private Listbox lbxSeleccionar_cita_medica_admision;
    @View
    private Listbox lbxTrabaja_autorizacion;
    @View
    private Listbox lbxTrabaja_ordenes;
    @View
    private Listbox lbxSolo_afiliados;
    @View
    private Textbox tbxCodigo_ministerio;
    @View
    private Textbox tbxCodigo_super_salud;
    @View
    private Checkbox chbPrint_receta_caja;
    @View
    private Checkbox chbPrint_receta_consulta_ext;
    // @View private Listbox lbxModo_contrato;
    @View
    private Listbox lbxPagar_copago_procedimientos;
    @View
    private Listbox lbxPagar_copago_medicamentos;
    @View
    private Listbox lbxPagar_copago_remisiones;
    @View
    private Listbox lbxPagar_cuota_moderadora_medicamentos;
    @View
    private Listbox lbxPagar_cuota_moderadora_procedimientos;
    @View
    private Listbox lbxEntrega_r_caja;
    @View
    private Listbox lbxTrabaja_hopt;
    @View
    private Listbox lbxTrabaja_uci;
    @View
    private Listbox lbxTrabaja_consltaext;
    @View
    private Listbox lbxTrabaja_urg;
    @View
    private Listbox lbxSelect_prestador_medico_adutorizacion;
    @View
    private Listbox lbxSelect_prestador_medico_remisiones;
    @View
    private Listbox lbxTipo_reporte_autorizaciones;
    @View
    private Listbox lbxTipo_reporte_remisiones;
    @View
    private Intbox ibxVigencia_recetas;
    @View
    private Listbox lbxTipo_reporte_receta;
    @View
    private Listbox lbxTrabaja_centro_atencion;
    @View
    private Listbox lbxTipo_solicitud_tecnica;
    @View
    private Checkbox chbFacturar_auto_asistencial;
    @View
    private Checkbox chbPermitir_cambiar_result_lab;
    @View
    private Checkbox chbAfectar_hoja_gato_automatica;
    @View
    private Checkbox chbHabilitar_ordenes_sin_admision;
    @View
    private Checkbox chbApartar_cita_toma_laboratorios;
    @View
    private Checkbox chbPermitir_cambiar_diagnosticos_datos_consulta;
    @View
    private Listbox lbxModelo_recibo_caja;
    @View
    private Listbox lbxTipo_reporte_facturacion;
    
    @View
    private Checkbox chbPermitir_facturar_distfecha;
    
    private final String[] idsExcluyentes = {};
    
    @View
    private Checkbox chbSignos_enfermera;

    /* nuevos componentes agregados */
    @View
    private Toolbarbutton btGuardar;
    @View
    private Toolbarbutton btNew;
    @View
    private Toolbarbutton btCancel;
    @View
    private Toolbarbutton btImprimir;
    @View
    private Image imageUsuario;
    @View
    private Button upLoad;
    @View
    private Button quitar;
    @View
    private Listbox lbxAnio;
    @View
    private Listbox lbxMes;
    @View
    private Listbox lbxRangoEdadNoRecetada;
    @View
    private Listbox lbxCobrarProcedimientosDespuesTerceraCitugia;
    @View
    private Listbox lbxCobrarProcedimientosDespuesTerceraSegunda;
    @View
    private Listbox lbxCobrarMaterialesGrupo20;
    @View
    private Listbox lbxModuloEntregaMedicamentos;
    @View
    private Listbox lbxAfectarInventarioFactura;
    
    @View
    private Row rowReporteAutorizaciones;
    
    @View
    private Listbox lbxHabilitar_triage4;
    
    @View
    private Listbox lbxMaximo_lab_fuera_rango_unidad;
    
    @View
    private Intbox ibxMaximo_lab_fuera_rango_valor;
    @View
    private Checkbox chbPermitir_apartar_citas_medico;
    @View
    private Checkbox chbSalud_oral_recuperacion;
    @View
    private Checkbox chbPermitir_vacunar_sin_contratacion;
    @View
    private Checkbox chbFiltrar_actividades_ordenamiento_programas;
    @View
    private Checkbox chbAtender_enfermeras_gefe_primeravez;
    
    @View
    private Listbox lbxGestion_informacion;
    
    @View
    private Checkbox chbCargar_signos_vitales_triage;
    @View
    private Checkbox chbhabilitar_asignacion_cont_cap_facturador;
    @View
    private Checkbox chbTrabajar_por_paises;
    
    @View
    private Checkbox chbhabilitar_consulta_especializada;
    @View
    private Checkbox chbhabilitar_terapia_fisica;
    @View
    private Checkbox chbhabilitar_terapia_respiratoria;
    @View
    private Checkbox chbHabilitar_bodega_centro;
    @View
    private Checkbox chbHabilitar_prefijo_venta;
    @View
    private Checkbox chbHabilitar_edicion_fac_particulares;
    @View
    private Checkbox chbEditar_ordenamiento_prestador;
    @View
    private Checkbox chbPermitir_facturador_evento;
    @View
    private Checkbox chbHabilitar_restriccion_autorizacion;
    @View
    private Intbox ibxNumero_mascara_factura;
    @View
    private Checkbox chbMostrar_historias_pag;
    
    private boolean imagenLoad;
    private Resolucion resolucion;
    
    @Override
    public void init() throws Exception {
        listarCombos();
        initYears();
        if (parametros_empresa != null) {
            mostrarDatos(parametros_empresa);
        }
        ibxMaximo_lab_fuera_rango_valor.setValue(30);
    }
    
    private void initYears() {
        String[] anios = Res.getAnnos(7);
        
        Listitem listitem = new Listitem();
        listitem.setValue("PR");
        listitem.setLabel("año del sistema");
        lbxAnio.appendChild(listitem);
        
        for (String anio : anios) {
            listitem = new Listitem();
            listitem.setValue(anio);
            listitem.setLabel("" + anio);
            lbxAnio.appendChild(listitem);
        }
        
        if (lbxAnio.getItemCount() > 0) {
            lbxAnio.setSelectedIndex(0);
        }
        
        if (lbxMes.getItemCount() > 0) {
            lbxMes.setSelectedIndex(0);
        }
    }
    
    public void listarCombos() {
        listarParameter();
        listarTipoHistoria();
        
        listarSN_Box(lbxCobrar_cuota_moderadora_cita);
        listarSN_Box(lbxSeleccionar_cita_medica_admision);
        listarSN_Box(lbxTrabaja_autorizacion);
        listarSN_Box(lbxTrabaja_ordenes);
        listarSN_Box(lbxSolo_afiliados);
        listarSN_Box(lbxPagar_copago_procedimientos);
        listarSN_Box(lbxPagar_copago_medicamentos);
        listarSN_Box(lbxPagar_copago_remisiones);
        listarSN_Box(lbxPagar_cuota_moderadora_medicamentos);
        listarSN_Box(lbxPagar_cuota_moderadora_procedimientos);
        listarSN_Box(lbxSelect_prestador_medico_adutorizacion);
        listarSN_Box(lbxSelect_prestador_medico_remisiones);
        
        listarSN_Box(lbxTrabaja_hopt);
        listarSN_Box(lbxTrabaja_uci);
        listarSN_Box(lbxTrabaja_consltaext);
        listarSN_Box(lbxTrabaja_urg);
        listarSN_Box(lbxHabilitar_triage4);
        Utilidades.listarElementoListbox(lbxMaximo_lab_fuera_rango_unidad,
                false, getServiceLocator());
        Utilidades.listarElementoListbox(lbxModelo_recibo_caja, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxTipo_solicitud_tecnica, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxTipo_reporte_facturacion, false,
                getServiceLocator());
        // listarSN_Box(lbxMaximo_lab_fuera_rango_unidad);

        listarTiposEntregaReciboCaja();
        listarTiposReporteAutorizacion();
        listarTipoReporteRemision();
        listarTipoReceta();

        /* Campos resolucion */
        listarRangoEdaddes();
        listarSN_Box(lbxCobrarProcedimientosDespuesTerceraCitugia);
        listarSN_Box(lbxCobrarProcedimientosDespuesTerceraSegunda);
        listarSN_Box(lbxCobrarMaterialesGrupo20);
        listarSN_Box(lbxModuloEntregaMedicamentos);
        listarSN_Box(lbxAfectarInventarioFactura);
        
        listarSN_Box(lbxTrabaja_centro_atencion);
        listarSN_Box(lbxGestion_informacion);
    }
    
    private void listarTipoReceta() {
        String[][] listado = {{"01", "Reporte tipo 1"},
        {"02", "Reporte tipo 2"}, {"03", "Reporte tipo 3"}};
        for (String[] estados : listado) {
            lbxTipo_reporte_receta.appendChild(new Listitem(estados[1],
                    estados[0]));
        }
        if (lbxTipo_reporte_receta.getItemCount() > 0) {
            lbxTipo_reporte_receta.setSelectedIndex(0);
        }
    }
    
    private void listarTipoReporteRemision() {
        String[][] listado = {{"01", "Reporte de la Resolucion Anexo 9"},
        {"02", "Reporte de la Resolucion Anexo 9 sin cuadros"}};
        for (String[] estados : listado) {
            lbxTipo_reporte_remisiones.appendChild(new Listitem(estados[1],
                    estados[0]));
        }
        if (lbxTipo_reporte_remisiones.getItemCount() > 0) {
            lbxTipo_reporte_remisiones.setSelectedIndex(0);
        }
    }
    
    private void listarTiposReporteAutorizacion() {
        String[][] listado = {{"01", "Reporte de la Resolucion Anexo 4"},
        {"02", "Reporte sencillo Nro 1"}};
        for (String[] estados : listado) {
            lbxTipo_reporte_autorizaciones.appendChild(new Listitem(estados[1],
                    estados[0]));
        }
        if (lbxTipo_reporte_autorizaciones.getItemCount() > 0) {
            lbxTipo_reporte_autorizaciones.setSelectedIndex(0);
        }
    }
    
    private void listarRangoEdaddes() {
        String[][] listado = {{"", " -- -- "}, {"1", "12-17 años"}};
        for (String[] estados : listado) {
            lbxRangoEdadNoRecetada.appendChild(new Listitem(estados[1],
                    estados[0]));
        }
        if (lbxRangoEdadNoRecetada.getItemCount() > 0) {
            lbxRangoEdadNoRecetada.setSelectedIndex(0);
        }
    }
    
    private void listarTiposEntregaReciboCaja() {
        String[][] listado = {{"01", "Imprimir desde recepcion"},
        {"02", "Imprimir desde farmacia"}};
        for (String[] estados : listado) {
            lbxEntrega_r_caja.appendChild(new Listitem(estados[1], estados[0]));
        }
        if (lbxEntrega_r_caja.getItemCount() > 0) {
            lbxEntrega_r_caja.setSelectedIndex(0);
        }
    }

    /* Cada vez que se valla creando una historia, se va incrementando */
    private void listarTipoHistoria() {
        for (int i = 1; i <= 3; i++) {
            Listitem listitem = new Listitem();
            listitem.setValue("" + (i < 10 ? "0" + i : i));
            listitem.setLabel("Tipo " + i);
            lbxTipo_historia_clinica.appendChild(listitem);
        }
        if (lbxTipo_historia_clinica.getItemCount() > 0) {
            lbxTipo_historia_clinica.setSelectedIndex(0);
        }
    }
    
    private void listarSN_Box(Listbox listbox) {
        String[][] listado = {{"N", "No"}, {"S", "Si"}};
        for (String[] estados : listado) {
            listbox.appendChild(new Listitem(estados[1], estados[0]));
        }
        if (listbox.getItemCount() > 0) {
            listbox.setSelectedIndex(0);
        }
    }
    
    public void listarParameter() {
        
    }
    
    public void verificartrabajaAutorizaciones() {
        Listitem listitem = lbxTrabaja_autorizacion.getSelectedItem();
        if (listitem != null) {
            String estado = listitem.getValue();
            rowReporteAutorizaciones.setVisible(estado.equalsIgnoreCase("S"));
        }
    }

    // Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
        ibxVigencia_recetas.setValue(72);
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        boolean valida = true;
        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres()
                    + " recuerde que...",
                    "Los campos marcados con (*) son obligatorios");
        }
        
        return valida;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                // Cargamos los componentes //
                Parametros_empresa parametros_empresa = this.parametros_empresa;
                if (parametros_empresa == null) {
                    parametros_empresa = new Parametros_empresa();
                }
                
                parametros_empresa.setCodigo_empresa(empresa
                        .getCodigo_empresa());
                parametros_empresa
                        .setTipo_historia_clinica(lbxTipo_historia_clinica
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setCobrar_cuota_moderadora_cita(lbxCobrar_cuota_moderadora_cita
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setSeleccionar_cita_medica_admision(lbxSeleccionar_cita_medica_admision
                                .getSelectedItem().getValue().toString());
                parametros_empresa.setCreacion_date(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                parametros_empresa.setUltimo_update(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                parametros_empresa.setCreacion_user(usuarios.getCodigo()
                        .toString());
                parametros_empresa.setUltimo_user(usuarios.getCodigo()
                        .toString());
                parametros_empresa
                        .setTrabaja_autorizacion(lbxTrabaja_autorizacion
                                .getSelectedItem().getValue().toString()
                                .equalsIgnoreCase("S"));
                parametros_empresa.setTrabaja_ordenes(lbxTrabaja_ordenes
                        .getSelectedItem().getValue().toString().equals("S"));
                parametros_empresa.setSolo_afiliados(lbxSolo_afiliados
                        .getSelectedItem().getValue().toString().equals("S"));
                parametros_empresa.setCodigo_ministerio(tbxCodigo_ministerio
                        .getValue());
                parametros_empresa.setCodigo_super_salud(tbxCodigo_super_salud
                        .getValue());
                parametros_empresa.setPrint_receta_caja(chbPrint_receta_caja
                        .isChecked() ? "S" : "N");
                parametros_empresa
                        .setPrint_receta_consulta_ext(chbPrint_receta_consulta_ext
                                .isChecked() ? "S" : "N");
                parametros_empresa.setModo_contrato("");
                parametros_empresa
                        .setPagar_copago_procedimientos(lbxPagar_copago_procedimientos
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setPagar_copago_medicamentos(lbxPagar_copago_medicamentos
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setPagar_copago_remisiones(lbxPagar_copago_remisiones
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setPagar_cuota_moderadora_medicamentos(lbxPagar_cuota_moderadora_medicamentos
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setPagar_cuota_moderadora_procedimientos(lbxPagar_cuota_moderadora_procedimientos
                                .getSelectedItem().getValue().toString());
                parametros_empresa.setEntrega_r_caja(lbxEntrega_r_caja
                        .getSelectedItem().getValue().toString());
                parametros_empresa.setTrabaja_hopt(lbxTrabaja_hopt
                        .getSelectedItem().getValue().toString());
                parametros_empresa.setTrabaja_uci(lbxTrabaja_uci
                        .getSelectedItem().getValue().toString());
                parametros_empresa.setTrabaja_consltaext(lbxTrabaja_consltaext
                        .getSelectedItem().getValue().toString());
                parametros_empresa.setTrabaja_urg(lbxTrabaja_urg
                        .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setSelect_prestador_medico_adutorizacion(lbxSelect_prestador_medico_adutorizacion
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setSelect_prestador_medico_remisiones(lbxSelect_prestador_medico_remisiones
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setTipo_reporte_autorizaciones(lbxTipo_reporte_autorizaciones
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setTipo_reporte_remisiones(lbxTipo_reporte_remisiones
                                .getSelectedItem().getValue().toString());
                parametros_empresa.setVigencia_recetas((ibxVigencia_recetas
                        .getValue() != null ? ibxVigencia_recetas.getValue()
                        : 72));
                parametros_empresa
                        .setTipo_reporte_receta(lbxTipo_reporte_receta
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setTrabaja_centro_atencion(lbxTrabaja_centro_atencion
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setTipo_solicitud_tecnica(lbxTipo_solicitud_tecnica
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setFacturar_auto_asistencial(chbFacturar_auto_asistencial
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setPermitir_cambiar_result_lab(chbPermitir_cambiar_result_lab
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setAfectar_hoja_gato_automatica(chbAfectar_hoja_gato_automatica
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setHabilitar_ordenes_sin_admision(chbHabilitar_ordenes_sin_admision
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setApartar_cita_toma_laboratorios(chbApartar_cita_toma_laboratorios
                                .isChecked() ? "S" : "N");
                
                parametros_empresa.setHabilitar_triage4(lbxHabilitar_triage4
                        .getSelectedItem().getValue().toString());
                
                parametros_empresa
                        .setMaximo_lab_fuera_rango_unidad(lbxMaximo_lab_fuera_rango_unidad
                                .getSelectedItem().getValue().toString());
                parametros_empresa.setMaximo_lab_fuera_rango_valor(""
                        + ibxMaximo_lab_fuera_rango_valor.getValue());
                parametros_empresa
                        .setPermitir_apartar_citas_medico(chbPermitir_apartar_citas_medico
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setPermitir_vacunar_sin_contratacion(chbPermitir_vacunar_sin_contratacion
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setFiltrar_actividades_ordenamiento_programas(chbFiltrar_actividades_ordenamiento_programas
                                .isChecked() ? "S" : "N");
                
                parametros_empresa
                        .setSalud_oral_recuperacion(chbSalud_oral_recuperacion
                                .isChecked() ? "S" : "N");
                
                parametros_empresa
                        .setGestion_informacion(lbxGestion_informacion
                                .getSelectedItem().getValue().toString());
                
                parametros_empresa.setSignos_enfermera(chbSignos_enfermera
                        .isChecked() ? "S" : "N");
                parametros_empresa
                        .setPermitir_cambiar_diagnosticos_datos_consulta(chbPermitir_cambiar_diagnosticos_datos_consulta
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setAtender_enfermeras_gefe_primeravez(chbAtender_enfermeras_gefe_primeravez
                                .isChecked() ? "S" : "N");
                parametros_empresa.setModelo_recibo_caja(lbxModelo_recibo_caja
                        .getSelectedItem().getValue().toString());
                
                parametros_empresa
                        .setPermitir_facturar_distfecha(chbPermitir_facturar_distfecha
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setTipo_reporte_facturacion(lbxTipo_reporte_facturacion
                                .getSelectedItem().getValue().toString());
                parametros_empresa
                        .setCargar_signos_vitales_triage(chbCargar_signos_vitales_triage
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setHabilitar_asignacion_cont_cap_facturador(chbhabilitar_asignacion_cont_cap_facturador
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setTrabajar_por_paises(chbTrabajar_por_paises
                                .isChecked() ? "S" : "N");
                parametros_empresa
                        .setHabilitar_terapia_fisica(chbhabilitar_terapia_fisica
                                .isChecked());
                parametros_empresa
                        .setHabilitar_terapia_respiratoria(chbhabilitar_terapia_respiratoria
                                .isChecked());
                parametros_empresa
                        .setHabilitar_consulta_especializada(chbhabilitar_consulta_especializada
                                .isChecked());
                parametros_empresa
                        .setHabilitar_bodega_centro(chbHabilitar_bodega_centro
                                .isChecked());
                parametros_empresa
                        .setHabilitar_prefijo_venta(chbHabilitar_prefijo_venta
                                .isChecked());
                parametros_empresa
                        .setHabilitar_editar_fac_particular(chbHabilitar_edicion_fac_particulares
                                .isChecked());
                parametros_empresa
                        .setEditar_ordenamiento_prestador(chbEditar_ordenamiento_prestador
                                .isChecked());
                parametros_empresa
                        .setPermitir_facturador_evento(chbPermitir_facturador_evento
                                .isChecked());
                parametros_empresa
                        .setHabilitar_restriccion_autorizacion(chbHabilitar_restriccion_autorizacion
                                .isChecked());
                parametros_empresa.setNumero_mascara_factura(ibxNumero_mascara_factura.getValue());
                parametros_empresa.setMostrar_historias_pag(chbMostrar_historias_pag.isChecked());
                
                getServiceLocator().getServicio(GeneralExtraService.class).actualizar(
                        parametros_empresa);

                /* Guardamos resolucion */
                if (resolucion == null) {
                    resolucion = new Resolucion();
                    resolucion.setCodigo_empresa(codigo_empresa);
                    /* Para que no se valla ningun valor nulo */
                    Res.insertNull(resolucion);
                }
                resolucion.setLogo(imagenLoad ? imageUsuario.getContent()
                        .getByteData() : null);
                resolucion.setExtension(imagenLoad ? imageUsuario.getContent()
                        .getFormat() : "");
                resolucion.setCreacion_date(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                resolucion.setUltimo_update(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                resolucion.setCreacion_user(usuarios.getCodigo().toString());
                resolucion.setUltimo_user(usuarios.getCodigo().toString());
                resolucion.setAnio(lbxAnio.getSelectedItem().getValue()
                        .toString());
                resolucion.setMes(lbxMes.getSelectedIndex() == 0 ? "PR"
                        : ("" + lbxMes.getSelectedIndex()));
                resolucion.setTipo_costo("01");
                resolucion.setTipo_cont("01");
                resolucion
                        .setCobrar_cirugia(lbxCobrarProcedimientosDespuesTerceraSegunda
                                .getSelectedItem().getValue().toString());
                resolucion.setCobrar_materiales(lbxCobrarMaterialesGrupo20
                        .getSelectedItem().getValue().toString());
                resolucion
                        .setCobrar_cirugia_soat(lbxCobrarProcedimientosDespuesTerceraCitugia
                                .getSelectedItem().getValue().toString());
                resolucion.setRango_edad_noreceta(lbxRangoEdadNoRecetada
                        .getSelectedItem().getValue().toString());
                resolucion.setUsar_fact_med(lbxModuloEntregaMedicamentos
                        .getSelectedItem().getValue().toString().equals("S"));
                resolucion.setAfectar_kardex_fact(lbxAfectarInventarioFactura
                        .getSelectedItem().getValue().toString().equals("S"));
                
                getServiceLocator().getResolucionService().actualizar(
                        resolucion);
                actualizarParametroEnSesion(IParametrosSesion.PARAM_PARAMETROS_EMPRESA,
                        parametros_empresa);
                actualizarParametroEnSesion(IParametrosSesion.PARAM_RESOLUCION, resolucion);
                HttpServletRequest request = (HttpServletRequest) Executions
                        .getCurrent().getNativeRequest();
                loadPeriodo(request, resolucion);
                Messagebox
                        .show("Los datos se guardaron satisfactoriamente, se actualizara para que tome los cambios",
                                "Informacion ..", Messagebox.OK,
                                Messagebox.INFORMATION,
                                new EventListener<Event>() {
                                    @Override
                                    public void onEvent(Event event)
                                    throws Exception {
                                        _recargar();
                                    }
                                });
            }
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
        
    }
    
    private void loadPeriodo(HttpServletRequest request, Resolucion resolucion) {
        /* cargamos periodo */
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        
        if (resolucion != null) {
            if (!resolucion.getAnio().equalsIgnoreCase("PR")
                    && resolucion.getAnio().matches("[0-9]*$")) {
                year = Integer.parseInt(resolucion.getAnio());
            }
            
            if (!resolucion.getMes().equalsIgnoreCase("PR")
                    && resolucion.getMes().matches("[0-9]*$")) {
                month = Integer.parseInt(resolucion.getMes()) - 1;
            }
        }
        // log.info("@loadPeriodo Anio: " + year + " mes: " + month);

        request.getSession().setAttribute("anio", year);
        request.getSession().setAttribute("mes", month);
        /* fin envio de periodo */
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Parametros_empresa parametros_empresa = (Parametros_empresa) obj;
        try {
            
            Utilidades.seleccionarListitem(lbxTipo_historia_clinica,
                    parametros_empresa.getTipo_historia_clinica());
            
            Utilidades.seleccionarListitem(lbxCobrar_cuota_moderadora_cita,
                    parametros_empresa.getCobrar_cuota_moderadora_cita());
            
            Utilidades.seleccionarListitem(lbxSeleccionar_cita_medica_admision,
                    parametros_empresa.getSeleccionar_cita_medica_admision());
            
            for (int i = 0; i < lbxTrabaja_autorizacion.getItemCount(); i++) {
                Listitem listitem = lbxTrabaja_autorizacion.getItemAtIndex(i);
                if (listitem
                        .getValue()
                        .toString()
                        .equals(parametros_empresa.getTrabaja_autorizacion() ? "S"
                                : "N")) {
                    listitem.setSelected(true);
                    i = lbxTrabaja_autorizacion.getItemCount();
                }
            }
            for (int i = 0; i < lbxTrabaja_ordenes.getItemCount(); i++) {
                Listitem listitem = lbxTrabaja_ordenes.getItemAtIndex(i);
                if (listitem
                        .getValue()
                        .toString()
                        .equals(parametros_empresa.getTrabaja_ordenes() ? "S"
                                : "N")) {
                    listitem.setSelected(true);
                    i = lbxTrabaja_ordenes.getItemCount();
                }
            }
            for (int i = 0; i < lbxSolo_afiliados.getItemCount(); i++) {
                Listitem listitem = lbxSolo_afiliados.getItemAtIndex(i);
                if (listitem
                        .getValue()
                        .toString()
                        .equals(parametros_empresa.getSolo_afiliados() ? "S"
                                : "N")) {
                    listitem.setSelected(true);
                    i = lbxSolo_afiliados.getItemCount();
                }
            }
            tbxCodigo_ministerio.setValue(parametros_empresa
                    .getCodigo_ministerio());
            tbxCodigo_super_salud.setValue(parametros_empresa
                    .getCodigo_super_salud());
            chbPrint_receta_caja.setChecked(parametros_empresa
                    .getPrint_receta_caja().equalsIgnoreCase("S"));
            chbPrint_receta_consulta_ext.setChecked(parametros_empresa
                    .getPrint_receta_consulta_ext().equalsIgnoreCase("S"));
            chbFacturar_auto_asistencial.setChecked(parametros_empresa
                    .getFacturar_auto_asistencial().equals("S"));
            Utilidades.setValueFrom(lbxTipo_solicitud_tecnica,
                    parametros_empresa.getTipo_solicitud_tecnica());
            
            Utilidades.seleccionarListitem(lbxPagar_copago_procedimientos,
                    parametros_empresa.getPagar_copago_procedimientos());
            
            Utilidades.seleccionarListitem(lbxPagar_copago_medicamentos,
                    parametros_empresa.getPagar_copago_medicamentos());
            
            Utilidades.seleccionarListitem(lbxPagar_copago_remisiones,
                    parametros_empresa.getPagar_copago_remisiones());
            
            Utilidades
                    .seleccionarListitem(
                            lbxPagar_cuota_moderadora_medicamentos,
                            parametros_empresa
                            .getPagar_cuota_moderadora_medicamentos());
            
            Utilidades.seleccionarListitem(
                    lbxPagar_cuota_moderadora_procedimientos,
                    parametros_empresa
                    .getPagar_cuota_moderadora_procedimientos());
            
            Utilidades.seleccionarListitem(lbxEntrega_r_caja,
                    parametros_empresa.getEntrega_r_caja());
            
            Utilidades.seleccionarListitem(lbxTrabaja_hopt,
                    parametros_empresa.getTrabaja_hopt());
            
            Utilidades.seleccionarListitem(lbxTrabaja_uci,
                    parametros_empresa.getTrabaja_uci());
            
            Utilidades.seleccionarListitem(lbxTrabaja_consltaext,
                    parametros_empresa.getTrabaja_consltaext());
            
            Utilidades.seleccionarListitem(lbxTrabaja_urg,
                    parametros_empresa.getTrabaja_urg());
            
            Utilidades.seleccionarListitem(
                    lbxSelect_prestador_medico_adutorizacion,
                    parametros_empresa
                    .getSelect_prestador_medico_adutorizacion());
            
            Utilidades.seleccionarListitem(
                    lbxSelect_prestador_medico_remisiones,
                    parametros_empresa.getSelect_prestador_medico_remisiones());
            
            Utilidades.seleccionarListitem(lbxTrabaja_centro_atencion,
                    parametros_empresa.getTrabaja_centro_atencion());
            
            chbPermitir_cambiar_result_lab.setChecked(parametros_empresa
                    .getPermitir_cambiar_result_lab().equals("S") ? true
                    : false);
            chbAfectar_hoja_gato_automatica.setChecked(parametros_empresa
                    .getAfectar_hoja_gato_automatica().equals("S") ? true
                    : false);
            Utilidades.setValueFrom(lbxTipo_reporte_autorizaciones,
                    parametros_empresa.getTipo_reporte_autorizaciones());
            Utilidades.setValueFrom(lbxTipo_reporte_remisiones,
                    parametros_empresa.getTipo_reporte_remisiones());
            ibxVigencia_recetas.setValue(parametros_empresa
                    .getVigencia_recetas());
            Utilidades.setValueFrom(lbxTipo_reporte_receta,
                    parametros_empresa.getTipo_reporte_receta());
            chbHabilitar_ordenes_sin_admision.setChecked(parametros_empresa
                    .getHabilitar_ordenes_sin_admision().equals("S") ? true
                    : false);
            verificartrabajaAutorizaciones();
            chbApartar_cita_toma_laboratorios.setChecked(parametros_empresa
                    .getApartar_cita_toma_laboratorios().equals("S") ? true
                    : false);
            chbPermitir_apartar_citas_medico.setChecked(parametros_empresa
                    .getPermitir_apartar_citas_medico().equals("S") ? true
                    : false);
            chbPermitir_vacunar_sin_contratacion.setChecked(parametros_empresa
                    .getPermitir_vacunar_sin_contratacion().equals("S") ? true
                    : false);
            chbFiltrar_actividades_ordenamiento_programas
                    .setChecked(parametros_empresa
                            .getFiltrar_actividades_ordenamiento_programas()
                            .equals("S") ? true : false);
            chbSalud_oral_recuperacion.setChecked(parametros_empresa
                    .getSalud_oral_recuperacion().equals("S"));
            
            chbSignos_enfermera.setChecked(parametros_empresa
                    .getSignos_enfermera().equals("S"));
            chbPermitir_cambiar_diagnosticos_datos_consulta
                    .setChecked(parametros_empresa
                            .getPermitir_cambiar_diagnosticos_datos_consulta()
                            .equals("S"));
            chbTrabajar_por_paises.setChecked(parametros_empresa
                    .getTrabajar_por_paises().equals("S"));
            Utilidades.seleccionarListitem(lbxGestion_informacion,
                    parametros_empresa.getGestion_informacion());
            chbAtender_enfermeras_gefe_primeravez.setChecked(parametros_empresa
                    .getAtender_enfermeras_gefe_primeravez().equals("S"));
            Utilidades.setValueFrom(lbxModelo_recibo_caja,
                    parametros_empresa.getModelo_recibo_caja());
            Utilidades.setValueFrom(lbxTipo_reporte_facturacion,
                    parametros_empresa.getTipo_reporte_facturacion());
            
            chbCargar_signos_vitales_triage.setChecked(parametros_empresa
                    .getCargar_signos_vitales_triage().equals("S"));
            chbhabilitar_asignacion_cont_cap_facturador
                    .setChecked(parametros_empresa
                            .getHabilitar_asignacion_cont_cap_facturador()
                            .equals("S"));
            
            chbhabilitar_terapia_fisica.setChecked(parametros_empresa
                    .isHabilitar_terapia_fisica());
            chbhabilitar_terapia_respiratoria.setChecked(parametros_empresa
                    .isHabilitar_terapia_respiratoria());
            chbhabilitar_consulta_especializada.setChecked(parametros_empresa
                    .isHabilitar_consulta_especializada());
            chbHabilitar_bodega_centro.setChecked(parametros_empresa
                    .getHabilitar_bodega_centro());
            chbHabilitar_prefijo_venta.setChecked(parametros_empresa
                    .getHabilitar_prefijo_venta());
            chbHabilitar_edicion_fac_particulares.setChecked(parametros_empresa
                    .getHabilitar_editar_fac_particular());
            chbEditar_ordenamiento_prestador.setChecked(parametros_empresa
                    .getEditar_ordenamiento_prestador());
            chbPermitir_facturador_evento.setChecked(parametros_empresa
                    .getPermitir_facturador_evento());
            chbHabilitar_restriccion_autorizacion.setChecked(parametros_empresa
                    .getHabilitar_restriccion_autorizacion());
            ibxNumero_mascara_factura.setValue(parametros_empresa.getNumero_mascara_factura());
            chbMostrar_historias_pag.setChecked(parametros_empresa.getMostrar_historias_pag());

            /* Mostramos datos resolucion */
            resolucion = new Resolucion();
            resolucion.setCodigo_empresa(codigo_empresa);
            resolucion = getServiceLocator().getResolucionService().consultar(
                    resolucion);
            if (resolucion != null) {
                Utilidades.setValueFrom(lbxAnio, resolucion.getAnio());
                if (resolucion.getMes().equalsIgnoreCase("PR")) {
                    lbxMes.setSelectedIndex(0);
                } else if (resolucion.getMes().matches("[0-9]*$")) {
                    lbxMes.setSelectedIndex(Integer.parseInt(resolucion
                            .getMes()));
                }
                Utilidades.setValueFrom(lbxRangoEdadNoRecetada,
                        resolucion.getRango_edad_noreceta());
                Utilidades.setValueFrom(
                        lbxCobrarProcedimientosDespuesTerceraCitugia,
                        resolucion.getCobrar_cirugia_soat());
                Utilidades.setValueFrom(
                        lbxCobrarProcedimientosDespuesTerceraSegunda,
                        resolucion.getCobrar_cirugia());
                Utilidades.setValueFrom(lbxCobrarMaterialesGrupo20,
                        resolucion.getCobrar_materiales());
                Utilidades.setValueFrom(lbxModuloEntregaMedicamentos,
                        resolucion.getUsar_fact_med() ? "S" : "N");
                Utilidades.setValueFrom(lbxAfectarInventarioFactura,
                        resolucion.getAfectar_kardex_fact() ? "S" : "N");
                if (resolucion.getLogo() != null) {
                    AImage aImage = new AImage("logo", resolucion.getLogo());
                    imageUsuario.setContent(aImage);
                    imagenLoad = true;
                }
            }
            
            Utilidades.seleccionarListitem(lbxHabilitar_triage4,
                    parametros_empresa.getHabilitar_triage4());
            
            Utilidades.seleccionarListitem(lbxMaximo_lab_fuera_rango_unidad,
                    parametros_empresa.getMaximo_lab_fuera_rango_unidad());
            ibxMaximo_lab_fuera_rango_valor.setValue(ConvertidorDatosUtil
                    .convertirDatot(parametros_empresa
                            .getMaximo_lab_fuera_rango_valor()));
            
            chbPermitir_facturar_distfecha.setChecked(parametros_empresa
                    .getPermitir_facturar_distfecha().equals("S"));
            
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }
    
    public void borrarImagen() throws Exception {
        ServletContext servletContext = (ServletContext) Executions
                .getCurrent().getDesktop().getWebApp().getServletContext();
        AImage aImage = new AImage(new File(
                servletContext.getRealPath("/images/empresa.gif")));
        imageUsuario.setContent(aImage);
        imagenLoad = false;
    }
    
    public void guardarImagen(Media media) throws Exception {
        try {
            if (media instanceof org.zkoss.image.Image) {
                AImage aImage = new AImage("logo", media.getByteData());
                imageUsuario.setContent(aImage);
                imagenLoad = true;
            } else {
                Messagebox.show("No es una foto: " + media, "Error",
                        Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Error cargando foto!!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    public void eliminarDatos(Object obj) throws Exception {
        Parametros_empresa parametros_empresa = (Parametros_empresa) obj;
        try {
            int result = getServiceLocator().getServicio(GeneralExtraService.class)
                    .eliminar(parametros_empresa);
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
}
