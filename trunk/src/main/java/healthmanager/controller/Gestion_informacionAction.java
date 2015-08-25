package healthmanager.controller;

import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Localidad;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.BarrioService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.Nivel_educativoService;
import healthmanager.modelo.service.OcupacionesService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pertenencia_etnicaService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Gestion_informacionAction extends GeneralComposer {

    @WireVariable
    private ElementoService elementoService;
    @WireVariable
    private Pertenencia_etnicaService pertenencia_etnicaService;
    @WireVariable
    private Nivel_educativoService nivel_educativoService;
    @WireVariable
    private OcupacionesService ocupacionesService;
    @WireVariable
    private DepartamentosService departamentosService;
    @WireVariable
    private MunicipiosService municipiosService;
    @WireVariable
    private GeneralExtraService generalExtraService;
    @WireVariable
    private BarrioService barrioService;
    @WireVariable
    private PacienteService pacienteService;

    @View
    private Textbox mcTbxPaciente;
    @View
    private Textbox mcTbxInfoPaciente;
    @View
    private Textbox mcTbxSexo;
    @View
    private Textbox mcTbxDireccion;
    @View
    private Doublebox mcDbxTelefono;
    @View
    private Datebox mcDtbxFecha_ncto;
    @View
    private Textbox mcTbxEdad;
    @View
    private Textbox mcTbxLugar_ncto;
    @View
    private Textbox mcTbxRegimen;
    @View
    private Listbox mcLbxRaza;
    @View(isMacro = true)
    private BandboxRegistrosMacro mcTbxCodigo_ocupacion;
    @View
    private Textbox mcTbxInfoOcupacion;
    @View
    private Toolbarbutton mcBtnLimpiarOcupacion;

    @View
    private Listbox mcLbxEstado_civil;
    @View
    private Textbox mcTbxAseguradora;
    @View
    private Listbox mcLbxEducativo;
    @View
    private Textbox mcTbxBarrio;
    @View
    private Textbox mcTbxLocalidad;
    @View
    private Textbox mcTbxTipo_identificacion;
    @View
    private Toolbarbutton btnGuardar;
    @View
    private Toolbarbutton btnCerrar;

    private Paciente paciente;

    private Usuarios usuarios;

    @View
    private Checkbox chkAplica_tel;

    @Override
    public void init() throws Exception {
        listarCombos();
        if (paciente != null) {
            cargarInformacion(paciente);
            chkAplica_tel.setChecked(true);
        }
    }

    @Override
    public void params(Map<String, Object> map) {
        paciente = (Paciente) map.get("paciente");
        usuarios = (Usuarios) map.get("usuarios");
    }

    public void listarCombos() {
        Utilidades.listarPertenencia_etnica(mcLbxRaza, true,
                pertenencia_etnicaService);
        parametrizarBandboxOcupacion();
        Utilidades.listarNivel_educativo(mcLbxEducativo, true,
                nivel_educativoService);
        Utilidades.listarElementoListbox(mcLbxEstado_civil, true,
                elementoService);
    }

    private void parametrizarBandboxOcupacion() {
        mcTbxCodigo_ocupacion.inicializar(mcTbxInfoOcupacion,
                mcBtnLimpiarOcupacion);
        mcTbxCodigo_ocupacion
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Ocupaciones>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Ocupaciones registro) {
                        listitem.appendChild(new Listcell(registro.getId()));
                        listitem.appendChild(new Listcell(registro.getNombre()));
                    }

                    @Override
                    public List<Ocupaciones> listarRegistros(
                            String valorBusqueda, Map<String, Object> parametros) {
                                parametros.put("paramTodo", valorBusqueda.toLowerCase());
                                return ocupacionesService.listar(parametros);
                            }

                            @Override
                            public boolean seleccionarRegistro(Bandbox bandbox,
                                    Textbox textboxInformacion, Ocupaciones registro) {
                                bandbox.setValue(registro.getId());
                                textboxInformacion.setValue(registro.getNombre());

                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {

                            }
                });

    }

    public void cargarInformacion(Paciente registro) {
        try {
            // this.zkWindow = zkWindow;
            FormularioUtil.limpiarComponentes(this,
                    new String[]{"mcDtbxFecha_inicial"});

            mcTbxPaciente.setValue(registro.getNro_identificacion());
            mcTbxInfoPaciente.setValue(registro.getNombreCompleto());
            mcTbxDireccion.setValue(registro.getDireccion());
            mcTbxTipo_identificacion.setValue(Utilidades.getNombreElemento(
                    registro.getTipo_identificacion(), "tipo_id",
                    elementoService));

            String telefono_res = (registro.getTel_res() != null
                    && !registro.getTel_res().isEmpty() ? registro.getTel_res()
                    : "");

            chkAplica_tel.setChecked(registro.getTel_res() != null
                    && !registro.getTel_res().isEmpty());

            mcDbxTelefono.setValue(ConvertidorDatosUtil
                    .convertirDato(telefono_res));
            mcDtbxFecha_ncto.setValue(registro.getFecha_nacimiento());
            Departamentos departamentos = new Departamentos(
                    registro.getCodigo_dpto(), "");
            departamentos = departamentosService.consultar(departamentos);
            Municipios municipios = new Municipios(
                    registro.getCodigo_municipio(), registro.getCodigo_dpto(),
                    "");
            municipios = municipiosService.consultar(municipios);
            mcTbxLugar_ncto.setValue((municipios != null ? municipios
                    .getNombre() : "")
                    + " - "
                    + (departamentos != null ? departamentos.getNombre() : ""));

            Utilidades.seleccionarListitem(mcLbxRaza,
                    registro.getPertenencia_etnica());

            Utilidades.seleccionarListitem(mcLbxEstado_civil,
                    registro.getEstado_civil());

            mcTbxAseguradora.setValue(registro.getCodigo_administradora());

            Ocupaciones ocupaciones = new Ocupaciones();
            ocupaciones.setId(registro.getCodigo_ocupacion());
            ocupaciones = ocupacionesService.consultar(ocupaciones);

            mcTbxCodigo_ocupacion.setValue(registro.getCodigo_ocupacion());
            mcTbxInfoOcupacion.setValue((ocupaciones != null ? ocupaciones
                    .getNombre() : ""));

            Utilidades.seleccionarListitem(mcLbxEducativo,
                    registro.getCodigo_educativo());

            mcTbxSexo.setValue(Utilidades.getNombreElemento(registro.getSexo(),
                    "sexo", elementoService));

            mcTbxRegimen
                    .setValue(Utilidades.getNombreElemento(
                                    registro.getTipo_usuario(), "tipo_usuario",
                                    elementoService));

            onSeleccionarFecha(registro.getFecha_nacimiento());

            Adicional_paciente adicional_paciente = new Adicional_paciente();
            adicional_paciente.setCodigo_empresa(codigo_empresa);
            adicional_paciente.setCodigo_sucursal(codigo_sucursal);
            adicional_paciente.setNro_identificacion(registro
                    .getNro_identificacion());
            adicional_paciente = generalExtraService
                    .consultar(adicional_paciente);

            Barrio barrio = new Barrio();
            barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente
                    .getCodigo_barrio() : "");
            barrio = barrioService.consultar(barrio);
            mcTbxBarrio.setValue(barrio != null ? ("("
                    + barrio.getCodigo_barrio() + ") " + barrio.getBarrio())
                    : "");

            if (barrio != null) {
                Localidad localidad = new Localidad();
                localidad.setCodigo_localidad(barrio.getCodigo_localidad());
                localidad = generalExtraService.consultar(localidad);

                mcTbxLocalidad.setValue(localidad != null ? ("("
                        + localidad.getCodigo_localidad() + ") " + localidad
                        .getLocalidad()) : barrio.getCodigo_localidad());
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void onSeleccionarFecha(Date fecha_nacimiento) {

        String edadstr = Util.getEdadPrsentacionSimple(new Timestamp(
                fecha_nacimiento.getTime()),
                new Timestamp(new Date().getTime()));
        mcTbxEdad.setValue(edadstr);

        int edad = Integer.parseInt(Util.getEdad(
                new java.text.SimpleDateFormat("dd/MM/yyyy")
                .format(fecha_nacimiento.getTime()), "1", false));

        if (edad <= 14) {
            if (edad < 5) {
                Ocupaciones ocupaciones = new Ocupaciones();
                ocupaciones.setId("9998");
                ocupaciones = ocupacionesService
                        .consultar(ocupaciones);
                if (mcTbxCodigo_ocupacion.getText().isEmpty()) {
                    mcTbxCodigo_ocupacion
                            .setValue((ocupaciones != null ? ocupaciones
                                    .getId() : ""));
                    mcTbxInfoOcupacion
                            .setValue((ocupaciones != null ? ocupaciones
                                    .getNombre() : ""));
                }
                if (mcLbxEducativo.getSelectedIndex() == 0) {
                    Utilidades.setValueFrom(mcLbxEducativo, "13");
                }
            } else {
                Ocupaciones ocupaciones = new Ocupaciones();
                ocupaciones.setId("9997");
                ocupaciones = ocupacionesService
                        .consultar(ocupaciones);

                if (mcTbxCodigo_ocupacion.getText().isEmpty()) {
                    mcTbxCodigo_ocupacion
                            .setValue((ocupaciones != null ? ocupaciones
                                    .getId() : ""));
                    mcTbxInfoOcupacion
                            .setValue((ocupaciones != null ? ocupaciones
                                    .getNombre() : ""));
                }

                if (mcLbxEducativo.getSelectedIndex() == 0) {
                    if (edad == 5) {
                        Utilidades.setValueFrom(mcLbxEducativo, "1");
                    } else if (edad >= 6 && edad < 11) {
                        Utilidades.setValueFrom(mcLbxEducativo, "2");
                    } else {
                        Utilidades.setValueFrom(mcLbxEducativo, "3");
                    }
                }
            }
        }
        if (edad >= 100) {
            MensajesUtil.notificarAlerta(
                    "Observar la edad de este paciente...", mcTbxEdad);
        }
    }

    public boolean guardarDatos() throws Exception {
        try {
            if (paciente != null) {
                validarCerrado(true);
                paciente.setPertenencia_etnica(mcLbxRaza.getSelectedItem()
                        .getValue().toString());
                paciente.setEstado_civil(mcLbxEstado_civil.getSelectedItem()
                        .getValue().toString());
                paciente.setDireccion(mcTbxDireccion.getValue());
                paciente.setTel_res((chkAplica_tel.isChecked() ? mcDbxTelefono
                        .getText() : ""));
                paciente.setCodigo_ocupacion(mcTbxCodigo_ocupacion.getValue());
                paciente.setCodigo_educativo(mcLbxEducativo.getSelectedItem()
                        .getValue().toString());
                paciente.setFecha_nacimiento(new Timestamp(mcDtbxFecha_ncto
                        .getValue().getTime()));
                paciente.setUltimo_update(new Timestamp(new Date().getTime()));
                paciente.setUltimo_user(usuarios.getCodigo());
                pacienteService.actualizar(paciente,
                        true);
                if (getParent() instanceof Admision_nuevaAction) {
                    ((Admision_nuevaAction) getParent())
                            .cargarDatosPaciente(paciente);
                }
                MensajesUtil.mensajeInformacion("Informacion",
                        "Paciente actualizado satisfactoriamente");
                return true;
            }
        } catch (WrongValueException e) {
            log.error(e);
            return false;
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
            return false;
        }
        return false;
    }

    public void validarCerrado(boolean lanzar) throws WrongValueException {
        this.setClosable(false);
        try {
            if (chkAplica_tel.isChecked()) {
                FormularioUtil.validarCamposObligatorios(mcTbxDireccion,
                        mcDbxTelefono, mcLbxEducativo);
            } else {
                FormularioUtil.validarCamposObligatorios(mcTbxDireccion,
                        mcLbxEducativo);
            }
            if (mcTbxCodigo_ocupacion.getValue().isEmpty()) {
                MensajesUtil
                        .mensajeAlerta(
                                "Campos vacios no diligenciados",
                                "Los campos demarcados con rojo son obligatorios. Para continuar con el proceso estos campos deben estar diligenciados",
                                new EventListener<Event>() {

                                    @Override
                                    public void onEvent(Event event)
                                    throws Exception {
                                        mcTbxCodigo_ocupacion.setFocus(true);
                                    }
                                });
                throw new WrongValueException(mcTbxCodigo_ocupacion,
                        "Debe especificar la ocupacion");
            }
            btnCerrar.setVisible(true);
        } catch (WrongValueException wve) {
            btnCerrar.setVisible(false);
            if (lanzar) {
                throw new WrongValueException(wve);
            }
        }
    }

    public void onCheckAplicaTel(Doublebox dbxTel) {
        if (!chkAplica_tel.isChecked()) {
            dbxTel.setDisabled(true);
            dbxTel.setText("");
        } else {
            dbxTel.setDisabled(false);
        }

    }

    public Toolbarbutton getBotonCerrar() {
        return btnCerrar;
    }

    public Toolbarbutton getBotonGuardar() {
        return btnGuardar;
    }

}
