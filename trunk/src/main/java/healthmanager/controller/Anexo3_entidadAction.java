/*
 * anexo3_entidadAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.controller.NegacionAnexo3ActionPopUp.Procedimeintos;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Anexo4_entidad;
import healthmanager.modelo.bean.Cama;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Frecuencia_ordenamiento;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IClasificacionProcedimiento;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.CargardorDeDatos;
import com.framework.res.Main;
import com.framework.res.RadioGroupL2H;
import com.framework.res.VerificacionOnlyPyp;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Anexo3_entidadAction extends ZKWindow {

    private static Logger log = Logger.getLogger(Anexo3_entidadAction.class);
    private static final long serialVersionUID = -9145887024839938515L;

    private Window form;

    // Componentes //
    @View
    private Textbox tbxNumero_solicitud;
    @View
    private Datebox dtbxFecha;
    @View(classField = Anexo3_entidad.class, field = "codigo_ips")
    private Bandbox tbxCodigo_prestador;
    @View
    private Textbox tbxNomPrestador;
    // @View private Textbox tbxCodigo;
    @View
    private Textbox tbxId;
    @View
    private Textbox tbxdirecion;
    @View
    private Textbox tbxDepartamento;
    @View
    private Textbox tbxMunicipio;
    @View(classField = Anexo3_entidad.class, field = "codigo_paciente")
    private Textbox tbxIdentificacionPaciente;
    @View
    private Textbox tbxTipoId;
    @View
    private Textbox tbxapellido1Paciente;
    @View
    private Textbox tbxapellido2paciente;
    @View
    private Textbox tbxnombre1Paciente;
    @View
    private Textbox tbxnombre2paciente;
    @View
    private Textbox tbxdirPaciente;
    @View
    private Textbox tbxtelpaciente;
    @View
    private Datebox tbxFechNacpaciente;

    @View(classField = Anexo3_entidad.class, field = "cobertura")
    private Listbox lbxCobertura;
    @View(classField = Anexo3_entidad.class, field = "origen_general", isStringCheck = true)
    private Checkbox chbOrigen_general;
    @View(classField = Anexo3_entidad.class, field = "origen_trabajo", isStringCheck = true)
    private Checkbox chbOrigen_trabajo;
    @View(classField = Anexo3_entidad.class, field = "origen_evento", isStringCheck = true)
    private Checkbox chbOrigen_evento;
    @View(classField = Anexo3_entidad.class, field = "origen_profesional", isStringCheck = true)
    private Checkbox chbOrigen_profesional;
    @View(classField = Anexo3_entidad.class, field = "origen_transito", isStringCheck = true)
    private Checkbox chbOrigen_transito;
    @View(classField = Anexo3_entidad.class, field = "tipo_servicio", isStringCheck = true)
    private Listbox lbxTipo_servicio;
    @View(classField = Anexo3_entidad.class, field = "prioridad")
    private Listbox lbxPrioridad;
    @View(classField = Anexo3_entidad.class, field = "ubicacion")
    private Listbox lbxUbicacion;
    @View(classField = Anexo3_entidad.class, field = "servicio")
    private Textbox tbxServicio;
    @View(classField = Anexo3_entidad.class, field = "cama")
    private Listbox lbxCama;
    @View(classField = Anexo3_entidad.class, field = "guia_atencion")
    private Textbox tbxGuia_atencion;

    /* procedimeintos clasificados */
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento1;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento2;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento3;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento4;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento5;
    ;
		@ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento6;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento7;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento8;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento9;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento10;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento11;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento12;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento13;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento14;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento15;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento16;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento17;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento18;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento19;
    @ValidateNoCodigoIguales
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxprocedimiento20;

    @View(classField = Anexo3_entidad.class, field = "justificacion")
    private Textbox txt_justificacion;
    @View
    private Textbox tbxDx;
    @View
    private Textbox tbxRelacionado_1;
    @View
    private Textbox tbxRelacionado_2;
    @View(classField = Anexo3_entidad.class, field = "nombre_reporta")
    private Textbox tbxNombre_solicita;
    @View(classField = Anexo3_entidad.class, field = "tel_reporta")
    private Textbox tbxTelefono;
    @View(classField = Anexo3_entidad.class, field = "cargo_reporta")
    private Textbox tbxCargo;
    @View(classField = Anexo3_entidad.class, field = "cel_reporta")
    private Textbox tbxCel;
//		private Historia_clinica_uci historia_clinica;

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
//		private String nro_identificacion;

    /* agregamos las tabs */
    @View
    private Tab tabLAB;
    @View
    private Tab tabRAD;
    @View
    private Tab tabECO;
    @View
    private Tab tabEND;
    @View
    private Tab tabTAC;
    @View
    private Tab tabRES;
    @View
    private Tab tabCitologias;
    @View
    private Tab tabElectrocardiograma;
    private Admision admision;

    @View
    private Row rowPrestadorServicio;
    private String causas_externas;
    private String diagnostico_principal;
    private String diagnostico_relacionado1;
    private String diagnostico_relacionado2;
    private Paciente paciente;

    /* fin */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ValidateNoCodigoIguales {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface addToOther {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface cloneThis {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface noAdded {
    }

    private void cargamosDatosDeMedico() {

        tbxNombre_solicita.setText("" + usuarios.getNombres() + " "
                + usuarios.getApellidos());
        tbxTelefono.setText("" + usuarios.getTel_res());
        tbxCargo.setText(usuarios.getTipo_med() == null ? "MEDICO" : (usuarios
                .getTipo_med().equals("01") ? "MEDICO GENERAL"
                : "MEDICO ESPECIALISTA"));
        tbxCel.setText("" + usuarios.getCelular());
    }

    private void verificamosSiYaHaSidoAtendido() throws Exception {
        Map parametros = Executions.getCurrent().getArg();
//		nro_identificacion = (String) parametros.get("nro_identificacion");
//		String nro_ingreso = (String) parametros.get("nro_ingreso");
//		String tipo_hc = (String) parametros.get("tipo_hc");
        admision = (Admision) parametros.get(IVias_ingreso.ADMISION_PACIENTE);
        causas_externas = (String) parametros.get(IVias_ingreso.CAUSAS_EXTERNAS);
        diagnostico_principal = (String) parametros.get(IVias_ingreso.DIAGNOSTICO_PRINCIPAL);
        diagnostico_relacionado1 = (String) parametros.get(IVias_ingreso.DIAGNOSTICO_RELACIONADO1);
        diagnostico_relacionado2 = (String) parametros.get(IVias_ingreso.DIAGNOSTICO_RELACIONADO2);

        cargamosDatosDelPaciente();

		//log.info("Admision: " + admision);

        /* Verificamos si la admision es de pyp */
        if (admision != null) {
            listarEstadoPago(!VerificacionOnlyPyp.onlyPyp(admision,
                    getServiceLocator()));
        }

        if (admision != null) {
            verificamosSiEsPacienteOPacienta();
        } else //log.info("::: @verificamosSiYaTieneHistoriaClinica Admision nula"); 
        if (admision.getAtendida()) {
            cargarDatosOrigenAtencion();
            cargarDatosDiagnosticos(diagnostico_principal, diagnostico_relacionado1, diagnostico_relacionado2);
        }
    }

    private void verificamosSiEsPacienteOPacienta() {
        paciente = new Paciente();
        paciente.setCodigo_empresa(admision.getCodigo_empresa());
        paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
        paciente.setNro_identificacion(admision.getNro_identificacion());
        paciente = getServiceLocator().getPacienteService().consultar(paciente);
        if (paciente != null) {
            if (paciente.getSexo().equals("M")) {
                tabCitologias.setVisible(false);
            }
        }
    }

    private void cargarCoberturaSalud(Paciente paciente) {
        if (paciente != null) {
            String cobertura = "";
            String posIdCobertura[] = {"1", "5", "6", "4", "8"};
            if (paciente.getTipo_usuario() != null) {
                if (paciente.getTipo_usuario().matches("[0-9]$*")) {
                    int pos = Integer.parseInt(paciente.getTipo_usuario());
                    //log.info("pos: " + pos);
                    cobertura = posIdCobertura[pos - 1];
                    //log.info("cobertura: " + cobertura);
                }
            }
            for (int i = 0; i < lbxCobertura.getItemCount(); i++) {
                Listitem listitem = lbxCobertura.getItemAtIndex(i);
				//log.info("::::::::::cobertura lista: "
                //+ listitem.getValue().toString());
                if (listitem.getValue().toString().equals(cobertura)) {
                    listitem.setSelected(true);
                    break;
                }
            }
        }
    }

    /**
     * Estas causas externas, salen de la historias
	 *
     */
    private void cargarDatosOrigenAtencion() {
        if (causas_externas != null) {
            chbOrigen_evento.setChecked(causas_externas.equals("06"));
            chbOrigen_general.setChecked(causas_externas.equals("13"));
            chbOrigen_profesional.setChecked(causas_externas.equals("14"));
            chbOrigen_trabajo.setChecked(causas_externas.equals("01"));
            chbOrigen_transito.setChecked(causas_externas.equals("02"));
        }
    }

    private void cargarDatosDiagnosticos(String diagnostico_principal, String diagnostico_relacionado1, String diagnostico_relacionado2) {
        if (diagnostico_principal != null) {
            Cie cie = new Cie();
            cie.setCodigo(diagnostico_principal);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            tbxDx.setValue(diagnostico_principal + " "
                    + (cie != null ? cie.getNombre() : ""));
        }

        if (diagnostico_relacionado1 != null) {
            /* relacionado 1 */
            Cie cie = new Cie();
            cie.setCodigo(diagnostico_relacionado1);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            tbxRelacionado_1.setValue(diagnostico_relacionado1 + " "
                    + (cie != null ? cie.getNombre() : ""));
        }

        if (diagnostico_relacionado2 != null) {
            /* relacionado 2 */
            Cie cie = new Cie();
            cie.setCodigo(diagnostico_relacionado2);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            tbxRelacionado_2.setValue(diagnostico_relacionado2 + " "
                    + (cie != null ? cie.getNombre() : ""));
        }
    }

    private void cargamosDatosDelPaciente() {
        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(admision.getCodigo_empresa());
        paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
        paciente.setNro_identificacion(admision.getNro_identificacion());
        paciente = getServiceLocator().getPacienteService().consultar(paciente);
        if (paciente != null) {
            tbxIdentificacionPaciente.setText(""
                    + paciente.getNro_identificacion());
            tbxTipoId.setText("" + paciente.getTipo_identificacion());
            tbxapellido1Paciente.setText("" + paciente.getApellido1());
            tbxapellido2paciente.setText("" + paciente.getApellido2());
            tbxnombre1Paciente.setText("" + paciente.getNombre1());
            tbxnombre2paciente.setText("" + paciente.getNombre2());
            tbxdirPaciente.setText("" + paciente.getDireccion());
            tbxtelpaciente.setText("" + paciente.getTel_res());
            tbxFechNacpaciente.setValue(paciente.getFecha_nacimiento());
            cargarCoberturaSalud(paciente);
        }
    }

    private void deshabilitarButtom(boolean sw) throws Exception {
        ((Button) getFellow("btn_guardar")).setDisabled(true);
    }

    private void listarCombos() {
        listamosElementos();
        listarCamas();
    }

    /**
     * Este metodo nos permite listar los estados de pago. si entra por pyp, o
     * si entra por medicina general
     *
     * @author Luis Miguel
	 *
     */
    private void listarEstadoPago(boolean disabled) {
        List<Elemento> elementos = this.getServiceLocator()
                .getElementoService().listar("estado_pago");
        for (int i = 1; i <= 20; i++) {
            String endNumber = "" + i;
            Listbox lbxestado_pago = (Listbox) getFellowIfAny("lbxestado"
                    + endNumber);
            if (lbxestado_pago != null) {
                listarElementoListboxFromType(lbxestado_pago, false, elementos,
                        false);
                lbxestado_pago.setDisabled(disabled);
            }
        }
    }

    /* datos del prestador */
    public void selectedPrestador(Listitem listitem) {
        if (listitem.getValue() == null) {
            tbxCodigo_prestador.setValue("");
            tbxNomPrestador.setValue("");
            tbxDepartamento.setText("");
            tbxMunicipio.setText("");
            tbxdirecion.setText("");
            tbxId.setText("");
            desabilitarLoadProcedimientos(true);
        } else {
            Map dato = (HashMap) listitem.getValue();

            String codigo = (String) dato.get("codigo");
//			String nit = (String) dato.get("nit");
            String nombre = (String) dato.get("nombre");
            String codigo_dpto = (String) dato.get("codigo_dpto");
            String direccion = (String) dato.get("direccion");
            String codigo_municipio = (String) dato.get("codigo_municipio");

            tbxCodigo_prestador.setValue(codigo);
            tbxNomPrestador.setValue(nombre);
            tbxDepartamento.setText(getNombreDepartamento(codigo_dpto));
            tbxMunicipio.setText(getNombreMunicipio(codigo_dpto,
                    codigo_municipio));
            tbxdirecion.setText("" + direccion);
            tbxId.setText("" + codigo);
            desabilitarLoadProcedimientos(false);
        }
        tbxCodigo_prestador.close();
    }

    private void cargarPrestadores(String codigo_prestador) {
        Administradora administradora = new Administradora();
        administradora.setCodigo(codigo_prestador);
        administradora = getServiceLocator().getAdministradoraService()
                .consultar(administradora);

        if (administradora != null) {
            tbxCodigo_prestador.setValue(administradora.getCodigo());
            tbxNomPrestador.setValue(administradora.getNombre());
            tbxDepartamento.setText(getNombreDepartamento(administradora
                    .getCodigo_dpto()));
            tbxMunicipio.setText(getNombreMunicipio(
                    administradora.getCodigo_dpto(),
                    administradora.getCodigo_municipio()));
            tbxdirecion.setText("" + administradora.getDireccion());
            tbxId.setText("" + administradora.getCodigo());
        }
    }

    private String getNombreDepartamento(String codigo_dpto) {
        Departamentos departamentos = new Departamentos();
        departamentos.setCodigo(codigo_dpto);
        departamentos = getServiceLocator().getDepartamentosService()
                .consultar(departamentos);
        return departamentos != null ? departamentos.getNombre() : "";
    }

    private String getNombreMunicipio(String codigo_dpto, String codigo_mun) {
        Municipios municipios = new Municipios();
        municipios.setCoddep(codigo_dpto);
        municipios.setCodigo(codigo_mun);
        municipios = getServiceLocator().getMunicipiosService().consultar(
                municipios);
        return municipios != null ? municipios.getNombre() : "";
    }

    private void listarCamas() {
        Map map = new HashMap();
        map.put("codigo_empresa", usuarios.getCodigo_empresa());
        map.put("codigo_sucursal", usuarios.getCodigo_sucursal());
        map.put("codigo_centro", centro_atencion_session.getCodigo_centro());

        List<Cama> camas = getServiceLocator().getCamaService().listar(map);

        lbxCama.getChildren().clear();
        Listitem listitem;
        listitem = new Listitem();
        listitem.setValue("");
        listitem.setLabel("-- seleccione --");
        lbxCama.appendChild(listitem);

        for (Cama elemento : camas) {
            listitem = new Listitem();
            listitem.setValue(elemento.getCodigo());
            listitem.setLabel(elemento.getNombre());
            lbxCama.appendChild(listitem);
        }
        if (lbxCama.getItemCount() > 0) {
            lbxCama.setSelectedIndex(0);
        }
    }

    public void listarElementoListboxFromType(Listbox listbox, boolean select,
            List<Elemento> elementos, boolean selectEnd) {
        listbox.getChildren().clear();
        Listitem listitem;
        if (select) {
            listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel("-- seleccione --");
            listbox.appendChild(listitem);
        }

        for (Elemento elemento : elementos) {
            listitem = new Listitem();
            listitem.setValue(elemento.getCodigo());
            listitem.setLabel(elemento.getDescripcion());
            listbox.appendChild(listitem);
        }
        if (listbox.getItemCount() > 0) {
            if (!selectEnd) {
                listbox.setSelectedIndex(0);
            } else {
                listbox.setSelectedIndex(listbox.getChildren().size() - 1);
            }
        }
    }

    private void listamosElementos() {
        List<Elemento> elementos = this.getServiceLocator()
                .getElementoService().listar("cobertura_anexo3");
        listarElementoListboxFromType(lbxCobertura, false, elementos, false);

        elementos = this.getServiceLocator().getElementoService()
                .listar("tipo_servicio_anexo3");
        listarElementoListboxFromType(lbxTipo_servicio, false, elementos, false);

        elementos = this.getServiceLocator().getElementoService()
                .listar("proridad_anexo3");
        listarElementoListboxFromType(lbxPrioridad, false, elementos, false);

        elementos = this.getServiceLocator().getElementoService()
                .listar("ubicacion_anexo3");
        listarElementoListboxFromType(lbxUbicacion, false, elementos, false);
    }

    public void loadComponents() {
        form = (Window) this.getFellow("formAnexo3_entidad");
        listarParameter();
    }

    public void listarElementoListbox(Listbox listbox, boolean select) {
        listbox.getChildren().clear();
        Listitem listitem;
        if (select) {
            listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel("-- seleccione --");
            listbox.appendChild(listitem);
        }

        String tipo = listbox.getName();
        List<Elemento> elementos = this.getServiceLocator()
                .getElementoService().listar(tipo);

        for (Elemento elemento : elementos) {
            listitem = new Listitem();
            listitem.setValue(elemento.getCodigo());
            listitem.setLabel(elemento.getDescripcion());
            listbox.appendChild(listitem);
        }
        if (listbox.getItemCount() > 0) {
            listbox.setSelectedIndex(0);
        }
    }

    // Convertimos todos las valores de textbox a mayusculas
    public void setUpperCase() {
        Collection<Component> collection = this.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                Textbox textbox = (Textbox) abstractComponent;
                if (!(textbox instanceof Combobox)) {
                    ((Textbox) abstractComponent)
                            .setText(((Textbox) abstractComponent).getText()
                                    .trim().toUpperCase());
                }
            }
        }
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        Collection<Component> collection = this.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
                    ((Textbox) abstractComponent).setValue("");
                }
            }
            if (abstractComponent instanceof Listbox) {
                if (!((Listbox) abstractComponent).getId().equals(
                        "lbxParameter")) {
                    if (((Listbox) abstractComponent).getItemCount() > 0) {
                        ((Listbox) abstractComponent).setSelectedIndex(0);
                    }
                }
            }
            if (abstractComponent instanceof Doublebox) {
                ((Doublebox) abstractComponent).setText("0.00");
            }
            if (abstractComponent instanceof Intbox) {
                if (((Intbox) abstractComponent).getId().startsWith(
                        "ibx_cantidad")) {
                    ((Intbox) abstractComponent).setText("0");
                } else {
                    ((Intbox) abstractComponent).setText("");
                }
            }
            if (abstractComponent instanceof Datebox) {
                ((Datebox) abstractComponent).setValue(new Date());
            }
            if (abstractComponent instanceof Checkbox) {
                ((Checkbox) abstractComponent).setChecked(false);
            }
            if (abstractComponent instanceof Radiogroup) {
                ((Radio) ((Radiogroup) abstractComponent).getFirstChild())
                        .setChecked(true);
            }
        }
        limpiarProcedimientos();
        chbOrigen_general.setChecked(true);
        cargamosDatosDelPaciente();
        if (admision.getAtendida()) {
            cargarDatosDiagnosticos(diagnostico_principal, diagnostico_relacionado1, diagnostico_relacionado2);
            cargamosDatosDeMedico();
            cargarDatosOrigenAtencion();
            deshabilitarButtom(false);
            ((Button) getFellow("btn_guardar")).setDisabled(false);
            deshabilitarCampos(false);
        } else {
            deshabilitarCampos(true);
            deshabilitarButtom(true);
        }
    }

    /**
     * Este metodo ayuda a limpiar los banbox registros de comonentes
     *
     * @author Luis Miguel
	 *
     */
    private void limpiarProcedimientos() {
        try {
            Field[] fields = getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(ValidateNoCodigoIguales.class) != null) {
                    field.setAccessible(true);
                    String endNumber = field.getName().replaceAll("[^0-9]", "");
                    Textbox tbxnombre = (Textbox) getFellow("tbxNomProcemiento"
                            + endNumber);
                    BandboxRegistrosMacro bandboxRegistros = (BandboxRegistrosMacro) field.get(this);
                    bandboxRegistros.limpiarSeleccion(true);
                    bandboxRegistros.setDisabled(false);
                    tbxnombre.setReadonly(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newAutorizacion() throws Exception {
        limpiarDatos();
        groupboxConsulta.setVisible(false);
        groupboxEditar.setVisible(true);
    }

    public void cancel() {
        groupboxConsulta.setVisible(true);
        groupboxEditar.setVisible(false);
    }

    public void imprimir() throws Exception {
        Map paramRequest = new HashMap();
        paramRequest.put("name_report", "Negacion");
        // paramRequest.put("codigo", "6");

        Component componente = Executions.createComponents(
                "/pages/printInformes.zul", form, paramRequest);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);
    }

    private void desabilitarLoadProcedimientos(boolean estado) {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().startsWith("tbxprocedimiento")) {
                    ((Bandbox) field.get(this)).setDisabled(estado);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarPrestador(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");
            parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());

            getServiceLocator().getAdministradoraService().setLimit(
                    "limit 25 offset 0");

            List<Map> list = getServiceLocator().getAdministradoraService()
                    .listarDesdeContratos(parameters);

            lbx.getItems().clear();

            for (Map dato : list) {

                String codigo = (String) dato.get("codigo");
                String nit = (String) dato.get("nit");
                String nombre = (String) dato.get("nombre");

                Listitem listitem = new Listitem();
                listitem.setValue(dato);

                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(codigo + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(nit + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(nombre + ""));
                listitem.appendChild(listcell);

                lbx.appendChild(listitem);
            }

            lbx.setMold("paging");
            lbx.setPageSize(8);
            lbx.applyProperties();

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Mensaje de Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        tbxCodigo_prestador.setStyle("background-color:white");
        tbxNomPrestador.setStyle("background-color:white");

        chbOrigen_general.setStyle("background-color:white");
        chbOrigen_trabajo.setStyle("background-color:white");
        chbOrigen_evento.setStyle("background-color:white");
        chbOrigen_profesional.setStyle("background-color:white");
        chbOrigen_transito.setStyle("background-color:white");

        boolean valida = true;
        String msj = "Los campos marcados con (*) son obligatorios";

        if (tbxCodigo_prestador.getText().trim().equals("") && !Utilidades.noUsarPrestador(parametros_empresa, "01")) {
            tbxCodigo_prestador.setStyle("background-color:#F6BBBE");
            tbxNomPrestador.setStyle("background-color:#F6BBBE");
            valida = false;
        }

        boolean thereCheck = chbOrigen_general.isChecked()
                || chbOrigen_trabajo.isChecked()
                || chbOrigen_evento.isChecked()
                || chbOrigen_profesional.isChecked()
                || chbOrigen_transito.isChecked();

        if (!thereCheck) {
            valida = false;
            msj = "Almenos debe seleccionar un Origen de atencion";
            chbOrigen_general.setStyle("background-color:#F6BBBE");
            chbOrigen_trabajo.setStyle("background-color:#F6BBBE");
            chbOrigen_evento.setStyle("background-color:#F6BBBE");
            chbOrigen_profesional.setStyle("background-color:#F6BBBE");
            chbOrigen_transito.setStyle("background-color:#F6BBBE");
        }

        /* validamos que este marcado almenos un oriden general */
        if (!valida) {
            Messagebox
                    .show("" + msj, usuarios.getNombres() + " recuerde que...",
                            Messagebox.OK, Messagebox.EXCLAMATION);
        }

        return valida;
    }

	// Metodo para buscar //
    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        Messagebox.show("Estas seguro que deseas hacer esta autorizacion? ",
                "Informacion", Messagebox.YES + Messagebox.NO,
                Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event event) throws Exception {
                        if ("onYes".equals(event.getName())) {
                            // do the thing
                            try {
                                setUpperCase();
                                if (validarForm() && validateNoProcedimeintosIguales()) {

									// nuevos proceso
                                    // Cargamos los componentes //
                                    Anexo3_entidad anexo3_entidad = new Anexo3_entidad();
                                    anexo3_entidad.setCodigo_empresa(admision
                                            .getCodigo_empresa());
                                    anexo3_entidad.setCodigo_sucursal(admision
                                            .getCodigo_sucursal());
                                    String codigo = Main.obtenerConsecutivo(
                                            getServiceLocator(),
                                            admision.getCodigo_empresa(),
                                            admision.getCodigo_sucursal(),
                                            "ANEXO_3");
                                    String numero_solicitud = Main
                                    .obtenerNro_solicitud(
                                            getServiceLocator(),
                                            admision.getCodigo_empresa(),
                                            admision.getCodigo_sucursal(),
                                            "NRO_ANEXO_3");

                                    DecimalFormat decimalFormat = new DecimalFormat(
                                            "0000000000");
                                    numero_solicitud = decimalFormat.format(Double
                                            .parseDouble(numero_solicitud));

                                    anexo3_entidad.setCodigo(codigo);
                                    anexo3_entidad
                                    .setCodigo_administradora(admision
                                            .getCodigo_empresa());
                                    anexo3_entidad
                                    .setNumero_solicitud(numero_solicitud);
                                    anexo3_entidad.setFecha(new Timestamp(
                                                    dtbxFecha.getValue().getTime()));
                                    CargardorDeDatos.cargarDatosViewEnBean(
                                            Anexo3_entidadAction.this,
                                            Anexo3_entidad.class,
                                            anexo3_entidad);

                                    anexo3_entidad
                                    .setCreacion_date(new Timestamp(
                                                    Calendar.getInstance()
                                                    .getTimeInMillis()));
                                    anexo3_entidad
                                    .setUltimo_update(new Timestamp(
                                                    Calendar.getInstance()
                                                    .getTimeInMillis()));
                                    anexo3_entidad.setCreacion_user(usuarios
                                            .getCodigo().toString());
                                    anexo3_entidad.setUltimo_user(usuarios
                                            .getCodigo().toString());
                                    anexo3_entidad.setAutorizado("");
                                    // anexo3_entidad.setCodigo_ips("");
                                    anexo3_entidad.setCons_ips("");
                                    anexo3_entidad.setEntidad("EPS");
                                    anexo3_entidad.setLeido("N");
                                    anexo3_entidad.setCie_p(diagnostico_principal != null ? diagnostico_principal : "Z000");
                                    anexo3_entidad.setCie_1(diagnostico_relacionado1 != null ? diagnostico_relacionado1 : "");
                                    anexo3_entidad.setCie_2(diagnostico_relacionado2 != null ? diagnostico_relacionado2 : "");
                                    anexo3_entidad
                                    .setNro_historia(null);
                                    anexo3_entidad
                                    .setNeed_autorizacion("N");
                                    anexo3_entidad.setTipo_anexo("PRO");
                                    anexo3_entidad.setCodigo_receta("");

                                    String anioo = new SimpleDateFormat("yyyy")
                                    .format(new Date());

                                    /* cargamos los procedimientos autorizados */
                                    List<Frecuencia_ordenamiento> listFrecuencia = new ArrayList<Frecuencia_ordenamiento>();
                                    for (int i = 1; i <= 20; i++) {
                                        String endNumber = "" + i;
                                        Field fieldProcedimiento = Anexo3_entidadAction.class.getDeclaredField("tbxprocedimiento" + endNumber);
                                        fieldProcedimiento.setAccessible(true);
                                        BandboxRegistrosMacro bandboxRegistros = (BandboxRegistrosMacro) fieldProcedimiento.get(Anexo3_entidadAction.this);
                                        Map<String, Object> map_procedimiento = (Map<String, Object>) bandboxRegistros.getObject();

                                        /* obtenemos valores */
                                        Field fieldValue = Anexo3_entidad.class
                                        .getDeclaredField("valor"
                                                + endNumber);
                                        Field fieldTipo = Anexo3_entidad.class
                                        .getDeclaredField("tipo_pcd"
                                                + endNumber);
                                        Field fieldCantidad = Anexo3_entidad.class
                                        .getDeclaredField("cantidad"
                                                + endNumber);
                                        Field fieldNombre = Anexo3_entidad.class
                                        .getDeclaredField("nombre"
                                                + endNumber);
                                        Field fieldCodigo_cups = Anexo3_entidad.class
                                        .getDeclaredField("codigo_cups"
                                                + endNumber);
                                        Field fieldAuto = Anexo3_entidad.class
                                        .getDeclaredField("autorizado_"
                                                + endNumber);
                                        Field fieldestado_pago = Anexo3_entidad.class
                                        .getDeclaredField("estado_cobro"
                                                + endNumber);

                                        /* colocamos de forma accecible */
                                        fieldValue.setAccessible(true);
                                        fieldTipo.setAccessible(true);
                                        fieldCantidad.setAccessible(true);
                                        fieldNombre.setAccessible(true);
                                        fieldCodigo_cups.setAccessible(true);
                                        fieldAuto.setAccessible(true);
                                        fieldestado_pago.setAccessible(true);

                                        if (map_procedimiento != null) {
                                            String codigo_pro = "" + map_procedimiento.get(IClasificacionProcedimiento.CODIGO);
                                            Double valor_pcd = ((BigDecimal) map_procedimiento.get(IClasificacionProcedimiento.VALOR)).doubleValue();
                                            valor_pcd = valor_pcd != null ? valor_pcd : 0d;
                                            String tipo_procedimiento = "" + map_procedimiento.get(IClasificacionProcedimiento.MANUAL);
                                            String nombre_procedimiento = "" + map_procedimiento.get(IClasificacionProcedimiento.DESCRIPCION);

                                            Intbox ibx_cantidad = (Intbox) getFellow("ibx_cantidad"
                                                    + endNumber);

                                            Listbox lbxestado_pago = (Listbox) getFellowIfAny("lbxestado"
                                                    + endNumber);

										//log.info("Estado de pago para procedimiento: " + codigo + " _ " + lbxestado_pago.getSelectedItem().getValue().toString()); 

                                            /* obtenemos valores */
                                            Integer cantidad = ibx_cantidad
                                            .getValue();

                                            Frecuencia_ordenamiento frecuencia_ordenamiento = new Frecuencia_ordenamiento();
                                            frecuencia_ordenamiento
                                            .setCodigo_empresa(codigo_empresa);
                                            frecuencia_ordenamiento
                                            .setCodigo_sucursal(codigo_sucursal);
                                            frecuencia_ordenamiento
                                            .setNro_identificacion(tbxIdentificacionPaciente
                                                    .getValue());
                                            frecuencia_ordenamiento
                                            .setCodigo(codigo_pro);
                                            frecuencia_ordenamiento
                                            .setCreacion_date(new Timestamp(
                                                            Calendar.getInstance()
                                                            .getTimeInMillis()));
                                            frecuencia_ordenamiento
                                            .setUltimo_update(new Timestamp(
                                                            Calendar.getInstance()
                                                            .getTimeInMillis()));
                                            frecuencia_ordenamiento
                                            .setCreacion_user(usuarios
                                                    .getCodigo()
                                                    .toString());
                                            frecuencia_ordenamiento
                                            .setUltimo_user(usuarios
                                                    .getCodigo()
                                                    .toString());
                                            frecuencia_ordenamiento.setCodigo_med(usuarios.getCodigo());
                                            frecuencia_ordenamiento.setManual_tarifa(tipo_procedimiento);
                                            frecuencia_ordenamiento.setCantidad(cantidad);
                                            listFrecuencia
                                            .add(frecuencia_ordenamiento);

                                            fieldCantidad.set(anexo3_entidad,
                                                    cantidad);
                                            fieldTipo.set(anexo3_entidad, tipo_procedimiento);
                                            fieldValue.set(anexo3_entidad,
                                                    valor_pcd);
                                            fieldNombre.set(anexo3_entidad,
                                                    nombre_procedimiento);
                                            fieldCodigo_cups.set(
                                                    anexo3_entidad, codigo_pro);
                                            fieldAuto.set(anexo3_entidad,
                                                    true);
                                            fieldestado_pago
                                            .set(anexo3_entidad,
                                                    lbxestado_pago != null ? lbxestado_pago
                                                    .getSelectedItem()
                                                    .getValue()
                                                    .toString()
                                                    : "01");
                                        } else {
                                            fieldCantidad
                                            .set(anexo3_entidad, 0);
                                            fieldTipo.set(anexo3_entidad, "");
                                            fieldValue.set(anexo3_entidad, 0d);
                                            fieldNombre.set(anexo3_entidad, "");
                                            fieldCodigo_cups.set(
                                                    anexo3_entidad, "");
                                            fieldAuto.set(anexo3_entidad, true);
                                            fieldestado_pago.set(
                                                    anexo3_entidad, "01");
                                        }
                                    }
//									anexo3_entidad
//											.setListFrecuencia(listFrecuencia);
                                    getServiceLocator()
                                    .getAnexo3EntidadService().crear(
                                            anexo3_entidad);

                                    Main.actualizarConsecutivo(
                                            getServiceLocator(),
                                            sucursal.getCodigo_empresa(),
                                            sucursal.getCodigo_sucursal(),
                                            "ANEXO_3", codigo);
                                    Main.actualizarConsecutivo(
                                            getServiceLocator(),
                                            sucursal.getCodigo_empresa(),
                                            sucursal.getCodigo_sucursal(),
                                            "NRO_ANEXO_3", numero_solicitud);

                                    if (!Utilidades.noUsarPrestador(parametros_empresa, "01")) {
                                        generarAnexo4Para(anexo3_entidad, "01");
                                        generarAnexo4Para(anexo3_entidad, "02");
                                        generarAnexo4Para(anexo3_entidad, "03");
                                    }

                                    limpiarDatos();
                                    Messagebox
                                    .show("Los datos se guardaron satisfactoriamente",
                                            "Informacion ..",
                                            Messagebox.OK,
                                            Messagebox.INFORMATION);
                                }

                            } catch (Exception e) {
                                MensajesUtil.mensajeError(e, "", Anexo3_entidadAction.this);
                            }
                        }
                    }
                });
    }

    private void generarAnexo4Para(Anexo3_entidad anexo3_entidad,
            String marcado_como) throws Exception {
        Anexo4_entidad anexo4EntidadMedicinaGeneral = getServiciosAutorizados(
                anexo3_entidad, marcado_como);
        if (anexo4EntidadMedicinaGeneral != null) {
            getServiceLocator().getAnexo4EntidadService().crear(
                    anexo4EntidadMedicinaGeneral);

            Main.actualizarConsecutivo(getServiceLocator(),
                    admision.getCodigo_empresa(),
                    admision.getCodigo_sucursal(), "ANEXO_4",
                    anexo4EntidadMedicinaGeneral.getCodigo());
//			Main.actualizarConsecutivo(getServiceLocator(),
//					admision.getCodigo_empresa(),
//					admision.getCodigo_sucursal(), "NRO_ANEXO_4",
//					anexo4EntidadMedicinaGeneral.getNumero_autorizacion());
        }
    }

    protected Anexo4_entidad getServiciosAutorizados(Anexo3_entidad anexo3,
            String marcado_como) throws Exception {

        if (hayAutorizados(true, marcado_como)) {
            String codigoAnexo4 = Main.obtenerConsecutivo(getServiceLocator(),
                    admision.getCodigo_empresa(),
                    admision.getCodigo_sucursal(), "ANEXO_4");
            String numero_solicitudAnexo4 = Main.obtenerNro_solicitud(
                    getServiceLocator(), admision.getCodigo_empresa(),
                    admision.getCodigo_sucursal(), "NRO_ANEXO_4");

            DecimalFormat decimalFormat = new DecimalFormat("0000000000");
            numero_solicitudAnexo4 = decimalFormat.format(Double
                    .parseDouble(numero_solicitudAnexo4));
            Anexo4_entidad anexo4 = new Anexo4_entidad();
            anexo4.setCodigo_empresa(empresa.getCodigo_empresa());
            anexo4.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            anexo4.setCodigo(codigoAnexo4);
//			anexo4.setNumero_autorizacion(numero_solicitudAnexo4);
//			anexo4.setFecha(new Timestamp(Calendar.getInstance()
//					.getTimeInMillis()));
//			anexo4.setCodigo_administradora(empresa.getCodigo_empresa());
//			anexo4.setCodigo_solicitud(anexo3.getCodigo());
//			anexo4.setNumero_solicitud(anexo3.getNumero_solicitud());
            anexo4.setUbicacion(anexo3.getUbicacion());
            anexo4.setServicio(anexo3.getServicio());
            anexo4.setCama(anexo3.getCama());
            anexo4.setGuia_atencion(anexo3.getGuia_atencion());
            anexo4.setEstado_cobro(marcado_como);
            /* cargamos los procedimientos autorizados */
            int c_autorizados = 1;
            for (int i = 1; i <= 20; i++) {
                String endNumber = "" + i;
                Checkbox checkboxValue = (Checkbox) getFellow("autorizado_"
                        + endNumber);
                Bandbox tbxCodigo = (Bandbox) getFellow("tbxprocedimiento"
                        + endNumber);
                String codigo = tbxCodigo.getValue();
                Listbox lbxestado_pago = (Listbox) getFellowIfAny("lbxestado"
                        + i);
                boolean marca_aceptada = false;
                if (lbxestado_pago != null) {
                    marca_aceptada = lbxestado_pago.getSelectedItem()
                            .getValue().toString().equals(marcado_como);
                } else if (marcado_como.equals("01")) {
                    marca_aceptada = true;
                }
                if (checkboxValue.isChecked() && !codigo.isEmpty()
                        && marca_aceptada) {
                    String endNumberOrden = "" + c_autorizados;

                    Doublebox doubleboxValue = (Doublebox) getFellow("valor_pcd"
                            + endNumber);
                    Label labelValue = (Label) getFellow("tipo_pcd" + endNumber);
                    Textbox tbxnombre = (Textbox) getFellow("tbxNomProcemiento"
                            + endNumber);
                    Intbox ibx_cantidad = (Intbox) getFellow("ibx_cantidad"
                            + endNumber);

                    /* obtenemos valores */
                    Field fieldValue = Anexo4_entidad.class
                            .getDeclaredField("valor" + endNumberOrden);
                    Field fieldTipo = Anexo4_entidad.class
                            .getDeclaredField("tipo_pcd" + endNumberOrden);
                    Field fieldCantidad = Anexo4_entidad.class
                            .getDeclaredField("cantidad" + endNumberOrden);
                    Field fieldNombre = Anexo4_entidad.class
                            .getDeclaredField("nombre" + endNumberOrden);
                    Field fieldCodigo_cups = Anexo4_entidad.class
                            .getDeclaredField("codigo_cups" + endNumberOrden);

                    /* colocamos de forma accecible */
                    fieldValue.setAccessible(true);
                    fieldTipo.setAccessible(true);
                    fieldCantidad.setAccessible(true);
                    fieldNombre.setAccessible(true);
                    fieldCodigo_cups.setAccessible(true);

                    /* obtenemos valores */
                    Double valor = doubleboxValue.getValue();
                    String tipo = labelValue.getValue();
                    String nombre = tbxnombre.getValue();
                    Integer cantidad = ibx_cantidad.getValue();

                    fieldCantidad.set(anexo4, cantidad);
                    fieldTipo.set(anexo4, tipo);
                    fieldValue.set(anexo4, valor);
                    fieldNombre.set(anexo4, nombre);
                    fieldCodigo_cups.set(anexo4, codigo);
                    c_autorizados++;
                }
            }

            /* rectificamos que no se vallan valores nulos */
            for (int i = c_autorizados; i <= 20; i++) {
                String endNumber = "" + i;

                Field fieldValue = Anexo4_entidad.class
                        .getDeclaredField("valor" + endNumber);
                Field fieldTipo = Anexo4_entidad.class
                        .getDeclaredField("tipo_pcd" + endNumber);
                Field fieldCantidad = Anexo4_entidad.class
                        .getDeclaredField("cantidad" + endNumber);
                Field fieldNombre = Anexo4_entidad.class
                        .getDeclaredField("nombre" + endNumber);
                Field fieldCodigo_cups = Anexo4_entidad.class
                        .getDeclaredField("codigo_cups" + endNumber);

                /* colocamos de forma accecible */
                fieldValue.setAccessible(true);
                fieldTipo.setAccessible(true);
                fieldCantidad.setAccessible(true);
                fieldNombre.setAccessible(true);
                fieldCodigo_cups.setAccessible(true);

                /* cargamos los siuientes valores */
                fieldCantidad.set(anexo4, 0);
                fieldTipo.set(anexo4, "");
                fieldValue.set(anexo4, 0d);
                fieldNombre.set(anexo4, "");
                fieldCodigo_cups.set(anexo4, "");
            }
            /* fin proceso de carga de procedimeintos */

            anexo4.setPorcentaje_valor(0);
            anexo4.setSemanas_afiliacion(0);
            anexo4.setReclamo_bono("N");
            anexo4.setConcepto_moderadora("N");
            anexo4.setValor_moderadora(0);
            anexo4.setPorcentaje_moderadora(0);
            anexo4.setValor_max_moderadora(0);
            anexo4.setConcepto_copago("N");
            anexo4.setValor_copago(0);
            anexo4.setPorcentaje_copago(0);
            anexo4.setValor_max_copago(0);
            anexo4.setConcepto_recuperacion("N");
            anexo4.setValor_recuperacion(0);
            anexo4.setPorcentaje_recuperacion(0);
            anexo4.setValor_max_recuperacion(0);
            anexo4.setConcepto_otro("N");
            anexo4.setValor_otro(0);
            anexo4.setPorcentaje_otro(0);
            anexo4.setValor_max_otro(0);
            anexo4.setNombre_reporta(usuarios.getNombres() + " "
                    + usuarios.getApellidos());
            anexo4.setCargo_reporta("");
            anexo4.setTel_reporta("");
            anexo4.setCel_reporta("");
            anexo4.setCreacion_date(new Timestamp(Calendar.getInstance()
                    .getTimeInMillis()));
            anexo4.setUltimo_update(new Timestamp(Calendar.getInstance()
                    .getTimeInMillis()));
            anexo4.setCreacion_user(usuarios.getCodigo());
            anexo4.setUltimo_user(usuarios.getCodigo());
//			anexo4.setCodigo_ips(anexo3.getCodigo_ips());
//			anexo4.setCons_ips(anexo3.getCons_ips());
            anexo4.setEntidad(anexo3.getEntidad());
            anexo4.setLeido("N");
            return anexo4;
        }
        return null;
    }

//	private boolean hayAutorizados(boolean estado) {
//		for (int i = 1; i <= 20; i++) {
//			Checkbox checkbox = (Checkbox) getFellow("autorizado_" + i);
//			Bandbox tbxCodigo = (Bandbox) getFellow("tbxprocedimiento" + i);
//			if (checkbox.isChecked() == estado
//					&& !tbxCodigo.getValue().isEmpty())
//				return true;
//		}
//		return false;
//	}
    private boolean hayAutorizados(boolean estado, String marcados_como) {
        for (int i = 1; i <= 20; i++) {
            Checkbox checkbox = (Checkbox) getFellow("autorizado_" + i);
            Bandbox tbxCodigo = (Bandbox) getFellow("tbxprocedimiento" + i);
            Listbox lbxestado_pago = (Listbox) getFellowIfAny("lbxestado" + i);

            boolean marca_aceptada = false;
            if (lbxestado_pago != null) {
                marca_aceptada = lbxestado_pago.getSelectedItem().getValue()
                        .toString().equals(marcados_como);
            } else if (marcados_como.equals("01")) {
                marca_aceptada = true;
            }

            if (checkbox.isChecked() == estado
                    && !tbxCodigo.getValue().isEmpty() && marca_aceptada) {
                return true;
            }
        }
        return false;
    }

    private boolean validateNoProcedimeintosIguales() throws Exception {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            Map map_contentProcess = new HashMap();
            int i = 0;
            boolean noHay = true;
            for (Field field : fields) {
                field.setAccessible(true);
                ValidateNoCodigoIguales view = field
                        .getAnnotation(ValidateNoCodigoIguales.class);
//				//log.info("Procedimiento " + field.getName() + " ValidateNoCodigoIguales: " + view);
                if (view != null) {
                    Object object = field.get(this);
                    i++;
                    String codigo = ((BandboxRegistrosMacro) object).getValue();
                    if (codigo != null) {
                        if (!codigo.isEmpty()) {
                            /* verificamos cantidad */
                            Intbox intbox = (Intbox) this
                                    .getFellow("ibx_cantidad" + i);
                            if (intbox.getValue() == null) {
                                throw new Exception(
                                        "procedimiento nro ? la cantidad se encuentra vacia."
                                        .replace("?", "" + (i)));
                            }

                            if (intbox.getValue().intValue() == 0) {
                                throw new Exception(
                                        "procedimiento nro ? la cantidad no puede ser igual a cero."
                                        .replace("?", "" + (i)));
                            }
                            noHay = false;
                            if (map_contentProcess.containsKey(codigo)) {
                                throw new Exception(
                                        "procedimiento nro ? se encuentra repido."
                                        .replace("?", "" + (i)));
                            } else {
                                map_contentProcess.put(codigo, "*");
                            }
                        }
                    }
                }
            }
            if (noHay) {
                throw new Exception("debe agregar almenos un procedimiento");
            }
            return true;
        } catch (Exception e) {
            MensajesUtil.mensajeAlerta("Adverntencia", e.getMessage());
            return false;
        }
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("numero_solicitud");
        listitem.setLabel("Nro solicitud");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("(fecha::VARCHAR)");
        listitem.setLabel("Fecha");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    public void buscarDatos() throws Exception {
        try {
//			String parameter = lbxParameter.getSelectedItem().getValue()
//					.toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("value", "%" + value + "%");
            parameters.put("codigo_empresa", admision.getCodigo_empresa());
            parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
            parameters.put("codigo_paciente", admision.getNro_identificacion() + "");
//			parameters.put("nro_historia",historia_clinica != null ? historia_clinica.getNro_historia() : "-1");

            getServiceLocator().getAnexo3EntidadService().setLimit(
                    "limit 25 offset 0");

            List<Anexo3_entidad> lista_datos = getServiceLocator()
                    .getAnexo3EntidadService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Anexo3_entidad anexo3Entidad : lista_datos) {
                rowsResultado.appendChild(crearFilas(anexo3Entidad, this));
            }

            gridResultado.setVisible(true);
            gridResultado.setMold("paging");
            gridResultado.setPageSize(20);

            gridResultado.applyProperties();
            gridResultado.invalidate();
            gridResultado.setVisible(true);
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Mensaje de Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    public Row crearFilas(Object objeto, Component componente) throws Exception {
        Row fila = new Row();

        final Anexo3_entidad anexo3Entidad = (Anexo3_entidad) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        Administradora administradora = new Administradora();
        administradora.setCodigo(anexo3Entidad.getCodigo_ips());
        administradora.setCodigo_empresa(admision.getCodigo_empresa());
        administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
        administradora = getServiceLocator().getAdministradoraService()
                .consultar(administradora);

        String direccionPres = "";
        if (administradora != null) {
            direccionPres += administradora.getDireccion();

            Departamentos departamentos = new Departamentos();
            departamentos.setCodigo(administradora.getCodigo_dpto());
            departamentos = getServiceLocator().getDepartamentosService()
                    .consultar(departamentos);

            Municipios municipios = new Municipios();
            municipios.setCoddep(administradora.getCodigo_dpto());
            municipios.setCodigo(administradora.getCodigo_municipio());
            municipios = getServiceLocator().getMunicipiosService().consultar(
                    municipios);

            if (municipios != null) {
                direccionPres += " - " + municipios.getNombre();
            }
            if (departamentos != null) {
                direccionPres += " - " + departamentos.getNombre();
            }
        }

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(anexo3Entidad.getNumero_solicitud() + ""));
        fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd hh:mm")
                .format(anexo3Entidad.getFecha()) + ""));
        fila.appendChild(new Label(anexo3Entidad.getCodigo_ips() + ""));
        fila.appendChild(new Label(administradora != null ? administradora
                .getNombre() : " -- "));
        fila.appendChild(new Label(direccionPres + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                validamosPestanasDependiendoAnexo3(anexo3Entidad
                        .getTipo_anexo());
                mostrarDatos(anexo3Entidad);
            }
        });
        hbox.appendChild(img);

        img = new Image();
        img.setSrc("/images/borrar.gif");
        img.setTooltiptext("Eliminar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                Messagebox.show(
                        "Esta seguro que desea eliminar este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    eliminarDatos(anexo3Entidad);
                                    buscarDatos();
                                }
                            }
                        });
            }
        });
		// hbox.appendChild(space);
        // hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    protected void validamosPestanasDependiendoAnexo3(String tipoAnexo) {

        boolean visible = tipoAnexo.equals("PRO");

        tabLAB.setLabel(visible ? "LABORATORIOS" : "MEDICAMENTOS");
        tabRAD.setVisible(visible);
        tabECO.setVisible(visible);
        tabEND.setVisible(visible);
        tabTAC.setVisible(visible);
        tabRES.setVisible(visible);
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        newAutorizacion();
        Anexo3_entidad anexo3_entidad = (Anexo3_entidad) obj;
        try {
            CargardorDeDatos.mostrarEnVista(this, Anexo3_entidad.class,
                    anexo3_entidad);
            desabilitarLoadProcedimientos(true);
            for (int i = 0; i < lbxCobertura.getItemCount(); i++) {
                Listitem listitem = lbxCobertura.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(anexo3_entidad.getCobertura())) {
                    listitem.setSelected(true);
                    break;
                }
            }
            chbOrigen_general.setChecked(anexo3_entidad.getOrigen_general()
                    .equalsIgnoreCase("S"));
            chbOrigen_profesional.setChecked(anexo3_entidad
                    .getOrigen_profesional().equalsIgnoreCase("S"));
            chbOrigen_trabajo.setChecked(anexo3_entidad.getOrigen_trabajo()
                    .equalsIgnoreCase("S"));
            chbOrigen_transito.setChecked(anexo3_entidad.getOrigen_transito()
                    .equalsIgnoreCase("S"));
            chbOrigen_evento.setChecked(anexo3_entidad.getOrigen_evento()
                    .equalsIgnoreCase("S"));
            cargarPrestadores(anexo3_entidad.getCodigo_ips());
            deshabilitarButtom(true);
            cargaMosProcedimientos(anexo3_entidad);

            for (int i = 0; i < lbxTipo_servicio.getItemCount(); i++) {
                Listitem listitem = lbxTipo_servicio.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(anexo3_entidad.getTipo_servicio())) {
                    listitem.setSelected(true);
                    break;
                }
            }
            for (int i = 0; i < lbxPrioridad.getItemCount(); i++) {
                Listitem listitem = lbxPrioridad.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(anexo3_entidad.getPrioridad())) {
                    listitem.setSelected(true);
                    break;
                }
            }
            for (int i = 0; i < lbxUbicacion.getItemCount(); i++) {
                Listitem listitem = lbxUbicacion.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(anexo3_entidad.getUbicacion())) {
                    listitem.setSelected(true);
                    break;
                }
            }
            tbxServicio.setValue(anexo3_entidad.getServicio());
            for (int i = 0; i < lbxCama.getItemCount(); i++) {
                Listitem listitem = lbxCama.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(anexo3_entidad.getCama())) {
                    listitem.setSelected(true);
                    break;
                }
            }
            tbxGuia_atencion.setValue(anexo3_entidad.getGuia_atencion());
            txt_justificacion.setValue(anexo3_entidad.getJustificacion());
            deshabilitarCampos(true);
            /* cargamos los procedimeintos */
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show("Este dato no se puede editar", "Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    private void cargaMosProcedimientos(Anexo3_entidad anexo3Entidad) {

        try {
            Field[] fields = Anexo3_entidad.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Procedimeintos view = field.getAnnotation(Procedimeintos.class);
                if (view != null) {
                    String codigo_cups = "" + field.get(anexo3Entidad);
                    if (!codigo_cups.isEmpty()) {
                        String endNumber = field.getName().replaceAll("[^0-9]",
                                "");
//						tbxprocedimiento1
                        Field fieldProcedimientoBandBoxRegistros = getClass().getDeclaredField("tbxprocedimiento" + endNumber);
                        Textbox tbxnombre = (Textbox) getFellow("tbxNomProcemiento"
                                + endNumber);
                        Listbox lbxestado = (Listbox) getFellow("lbxestado"
                                + endNumber);
                        Intbox intboxCantidad = (Intbox) getFellow("ibx_cantidad" + endNumber);

                        /* obtenemos valores */
                        Field fieldNombre = Anexo3_entidad.class
                                .getDeclaredField("nombre" + endNumber);
                        Field fieldEstadoCopago = Anexo3_entidad.class
                                .getDeclaredField("estado_cobro" + endNumber);
                        Field fieldCantidad = Anexo3_entidad.class
                                .getDeclaredField("cantidad" + endNumber);

                        /* colocamos de forma accecible */
                        fieldNombre.setAccessible(true);
                        fieldEstadoCopago.setAccessible(true);
                        fieldProcedimientoBandBoxRegistros.setAccessible(true);
                        fieldCantidad.setAccessible(true);

                        /* obtenemos valores */
                        String nombre = (String) fieldNombre.get(anexo3Entidad);
                        String estado_cobro = (String) fieldEstadoCopago
                                .get(anexo3Entidad);
                        Integer cantidad = (Integer) fieldCantidad.get(anexo3Entidad);
                        BandboxRegistrosMacro bandboxRegistros = (BandboxRegistrosMacro) fieldProcedimientoBandBoxRegistros.get(this);

                        /* cargamos valores en vista */
                        intboxCantidad.setValue(cantidad);
                        bandboxRegistros.setValue(codigo_cups);
                        tbxnombre.setValue(nombre);
                        for (int i = 0; i < lbxestado.getItemCount(); i++) {
                            Listitem listitem = lbxestado.getItemAtIndex(i);
                            if (listitem.getValue().toString()
                                    .equals(estado_cobro)) {
                                listitem.setSelected(true);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deshabilitarCampos(boolean sw) throws Exception {
        Collection<Component> collection = form.getFellows();
        for (Component abstractComponent : collection) {

            if (abstractComponent instanceof Listbox) {
                if (!((Listbox) abstractComponent).getId().equals("lbxFormato") && !((Listbox) abstractComponent).getId().startsWith("lbxestado")) {
                    if (((Listbox) abstractComponent).getItemCount() > 0) {
                        ((Listbox) abstractComponent).setDisabled(sw);
                    }
                }
            }
            if (abstractComponent instanceof Doublebox) {
                ((Doublebox) abstractComponent).setReadonly(sw);
            }

            if (abstractComponent instanceof Bandbox) {
                ((Bandbox) abstractComponent).setDisabled(sw);
            } else if (abstractComponent instanceof Textbox) {
                if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
                    ((Textbox) abstractComponent).setReadonly(sw);
                }
            }

            if (abstractComponent instanceof Intbox) {
                ((Intbox) abstractComponent).setReadonly(sw);
            }
            if (abstractComponent instanceof Datebox) {
                ((Datebox) abstractComponent).setReadonly(sw);
            }
            if (abstractComponent instanceof Checkbox) {
                ((Checkbox) abstractComponent).setDisabled(sw);
            }
            if (abstractComponent instanceof Radio) {
                ((Radio) abstractComponent).setDisabled(sw);
            }

            if (abstractComponent instanceof RadioGroupL2H) {
                ((RadioGroupL2H) abstractComponent).setDisabled(sw);
            }

            if (abstractComponent instanceof Radiogroup) {
                for (Object abstractComponentTemp : ((Radiogroup) abstractComponent)
                        .getChildren()) {
                    if (abstractComponentTemp instanceof Radio) {
                        ((Radio) abstractComponentTemp).setDisabled(sw);
                    }
                }
            }
        }
        tbxNomPrestador.setReadonly(true);
        tbxNombre_solicita.setReadonly(true);
        tbxTelefono.setReadonly(true);
        tbxCargo.setReadonly(true);
        tbxCel.setReadonly(true);
        tbxIdentificacionPaciente.setReadonly(true);
        tbxTipoId.setReadonly(true);
        tbxapellido1Paciente.setReadonly(true);
        tbxapellido2paciente.setReadonly(true);
        tbxnombre1Paciente.setReadonly(true);
        tbxnombre2paciente.setReadonly(true);
        tbxdirPaciente.setReadonly(true);
        tbxtelpaciente.setReadonly(true);
        tbxFechNacpaciente.setReadonly(true);
        tbxDepartamento.setReadonly(true);
        tbxMunicipio.setReadonly(true);
        tbxdirecion.setReadonly(true);
        tbxId.setReadonly(true);
        tbxDx.setReadonly(true);
        tbxRelacionado_1.setReadonly(true);
        tbxRelacionado_2.setReadonly(true);
    }

    public void eliminarDatos(Object obj) throws Exception {
        Anexo3_entidad anexo3_entidad = (Anexo3_entidad) obj;
        try {
            int result = getServiceLocator().getAnexo3EntidadService()
                    .eliminar(anexo3_entidad);
            if (result == 0) {
                throw new Exception(
                        "Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
            }

            Messagebox.show("Informacion se eliminó satisfactoriamente !!",
                    "Information", Messagebox.OK, Messagebox.INFORMATION);
        } catch (HealthmanagerException e) {

            log.error(e.getMessage(), e);
            Messagebox
                    .show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
                            "Error !!", Messagebox.OK, Messagebox.ERROR);
        } catch (RuntimeException r) {
            log.error(r.getMessage(), r);
            Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
                    Messagebox.ERROR);
        }
    }

    @Override
    public void afterCargarDatosSession(HttpServletRequest request) {
        rowPrestadorServicio.setVisible(!Utilidades.noUsarPrestador(parametros_empresa, "01"));
        desabilitarLoadProcedimientos(rowPrestadorServicio.isVisible());
    }

    @Override
    public void init() throws Exception {
        loadComponents();
        listarCombos();
        verificamosSiYaHaSidoAtendido();
        cargamosDatosDeMedico();
        parametrizarBandbox();
    }

    private void parametrizarBandbox() {
        inicializarBandboxProcedimientos();
    }

    /**
     * getBRIMG - Este metodo esta abrebiado el cual devuel el
     * BandboxRegistrosIMG<Map> de Utilidades
     *
     * @param clasficacion
	 *
     */
    private BandboxRegistrosIMG<Map<String, Object>> getBRIMG(String clasficacion) {
        String nro_identificacion_prestador = tbxCodigo_prestador
                .getValue();
        Map<String, Object> parametersContratos = new HashMap<String, Object>();
        parametersContratos.put("codigo_empresa",
                sucursal.getCodigo_empresa());
        parametersContratos.put("codigo_sucursal",
                sucursal.getCodigo_sucursal());
        if (!Utilidades.noUsarPrestador(parametros_empresa, "01")) {
            parametersContratos.put("codigo_administradora",
                    nro_identificacion_prestador);
        }
        parametersContratos.put("tipo_procedimiento", clasficacion);
        parametersContratos.put("autorizado", true);
        parametersContratos.put("sexo", paciente != null ? paciente.getSexo() : "_No");
        if (VerificacionOnlyPyp.onlyPyp(admision, getServiceLocator())) {
            parametersContratos.put("pyp", "S");
        } else {
            parametersContratos.put("pyp", "N");
        }
        return Utilidades.inicializarBanboxRegistrosProcedimiento(parametersContratos, this, paciente);
    }

    private void inicializarBandboxProcedimientos() {
        /* Incializamos los procedimientos de tipo laboratorio */
        BandboxRegistrosIMG<Map<String, Object>> bandboxRegistrosIMGLaboratorios = getBRIMG(IClasificacionProcedimiento.LABORATORIOS);
        tbxprocedimiento1.inicializar((Textbox) getFellow("tbxNomProcemiento1"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento1"));
        tbxprocedimiento1.setBandboxRegistrosIMG(bandboxRegistrosIMGLaboratorios);
        tbxprocedimiento2.inicializar((Textbox) getFellow("tbxNomProcemiento2"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento2"));
        tbxprocedimiento2.setBandboxRegistrosIMG(bandboxRegistrosIMGLaboratorios);
        tbxprocedimiento3.inicializar((Textbox) getFellow("tbxNomProcemiento3"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento3"));
        tbxprocedimiento3.setBandboxRegistrosIMG(bandboxRegistrosIMGLaboratorios);
        tbxprocedimiento4.inicializar((Textbox) getFellow("tbxNomProcemiento4"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento4"));
        tbxprocedimiento4.setBandboxRegistrosIMG(bandboxRegistrosIMGLaboratorios);

        /* Incializamos los procedimientos de tipo radiagrafias */
        BandboxRegistrosIMG<Map<String, Object>> bandboxRegistrosIMGRadiografia = getBRIMG(IClasificacionProcedimiento.RADIOGRAFIA);
        tbxprocedimiento5.inicializar((Textbox) getFellow("tbxNomProcemiento5"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento5"));
        tbxprocedimiento5.setBandboxRegistrosIMG(bandboxRegistrosIMGRadiografia);
        tbxprocedimiento6.inicializar((Textbox) getFellow("tbxNomProcemiento6"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento6"));
        tbxprocedimiento6.setBandboxRegistrosIMG(bandboxRegistrosIMGRadiografia);
        tbxprocedimiento7.inicializar((Textbox) getFellow("tbxNomProcemiento7"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento7"));
        tbxprocedimiento7.setBandboxRegistrosIMG(bandboxRegistrosIMGRadiografia);
        tbxprocedimiento8.inicializar((Textbox) getFellow("tbxNomProcemiento8"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento8"));
        tbxprocedimiento8.setBandboxRegistrosIMG(bandboxRegistrosIMGRadiografia);

        /* Incializamos los procedimientos de tipo ecografias */
        BandboxRegistrosIMG<Map<String, Object>> bandboxRegistrosIMGEcografias = getBRIMG(IClasificacionProcedimiento.ECOGRAFIA);
        tbxprocedimiento9.inicializar((Textbox) getFellow("tbxNomProcemiento9"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento9"));
        tbxprocedimiento9.setBandboxRegistrosIMG(bandboxRegistrosIMGEcografias);
        tbxprocedimiento10.inicializar((Textbox) getFellow("tbxNomProcemiento10"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento10"));
        tbxprocedimiento10.setBandboxRegistrosIMG(bandboxRegistrosIMGEcografias);
        tbxprocedimiento11.inicializar((Textbox) getFellow("tbxNomProcemiento11"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento11"));
        tbxprocedimiento11.setBandboxRegistrosIMG(bandboxRegistrosIMGEcografias);
        tbxprocedimiento12.inicializar((Textbox) getFellow("tbxNomProcemiento12"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento12"));
        tbxprocedimiento12.setBandboxRegistrosIMG(bandboxRegistrosIMGEcografias);

        /* Incializamos los procedimientos de tipo endoscopia */
        BandboxRegistrosIMG<Map<String, Object>> bandboxRegistrosIMGEndoscopia = getBRIMG(IClasificacionProcedimiento.ENDOSCOPIA);
        tbxprocedimiento13.inicializar((Textbox) getFellow("tbxNomProcemiento13"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento13"));
        tbxprocedimiento13.setBandboxRegistrosIMG(bandboxRegistrosIMGEndoscopia);
        tbxprocedimiento14.inicializar((Textbox) getFellow("tbxNomProcemiento14"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento14"));
        tbxprocedimiento14.setBandboxRegistrosIMG(bandboxRegistrosIMGEndoscopia);
        tbxprocedimiento15.inicializar((Textbox) getFellow("tbxNomProcemiento15"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento15"));
        tbxprocedimiento15.setBandboxRegistrosIMG(bandboxRegistrosIMGEndoscopia);

        /* Incializamos los procedimientos de tipo tac */
        BandboxRegistrosIMG<Map<String, Object>> bandboxRegistrosIMGTac = getBRIMG(IClasificacionProcedimiento.TAC);
        tbxprocedimiento16.inicializar((Textbox) getFellow("tbxNomProcemiento16"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento16"));
        tbxprocedimiento16.setBandboxRegistrosIMG(bandboxRegistrosIMGTac);

        /* Incializamos los procedimientos de tipo RESONACIA_MAGNETICA */
        BandboxRegistrosIMG<Map<String, Object>> bandboxRegistrosIMGResonacia_magnetica = getBRIMG(IClasificacionProcedimiento.RESONACIA_MAGNETICA);
        tbxprocedimiento17.inicializar((Textbox) getFellow("tbxNomProcemiento17"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento17"));
        tbxprocedimiento17.setBandboxRegistrosIMG(bandboxRegistrosIMGResonacia_magnetica);
        tbxprocedimiento18.inicializar((Textbox) getFellow("tbxNomProcemiento18"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento18"));
        tbxprocedimiento18.setBandboxRegistrosIMG(bandboxRegistrosIMGResonacia_magnetica);

        /* Incializamos los procedimientos de tipo CITOLOGIAS */
        BandboxRegistrosIMG<Map<String, Object>> bandboxRegistrosIMGCitologias = getBRIMG(IClasificacionProcedimiento.CITOLOGIAS);
        tbxprocedimiento19.inicializar((Textbox) getFellow("tbxNomProcemiento19"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento19"));
        tbxprocedimiento19.setBandboxRegistrosIMG(bandboxRegistrosIMGCitologias);

        /* Incializamos los procedimientos de tipo ELECTROCARDIOGRAMA */
        BandboxRegistrosIMG<Map<String, Object>> bandboxRegistrosIMGElectrocardiograma = getBRIMG(IClasificacionProcedimiento.ELECTROCARDIOGRAMA);
        tbxprocedimiento20.inicializar((Textbox) getFellow("tbxNomProcemiento20"), (Toolbarbutton) getFellow("btnLimpiarProcedimiento20"));
        tbxprocedimiento20.setBandboxRegistrosIMG(bandboxRegistrosIMGElectrocardiograma);
    }
}
