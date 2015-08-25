/*
 * discapacidades_lepraAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Discapacidades_lepra;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.service.Discapacidades_lepraService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.GridGradoDiscapacidadMacro;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Discapacidades_lepraAction extends ZKWindow {

    private static Logger log = Logger.getLogger(Discapacidades_lepraAction.class);

    private Discapacidades_lepraService discapacidades_lepraService;

    private Admision admision;

    //Componentes //
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
    private Textbox tbxCodigo_historia;

    @View
    private Bandbox tbxNro_identificacion;
    @View
    private Textbox tbxNomPaciente;
    @View
    private Toolbarbutton btnLimpiarPaciente;
    @View
    private Textbox tbxTipo_id;
    @View
    private Textbox tbxEdad;
    @View
    private Textbox tbxOcupacion;
    @View
    private Textbox tbxSexo;
    @View
    private Textbox tbxPais;
    @View
    private Textbox tbxDpto;
    @View
    private Textbox tbxMun;
    @View
    private Textbox tbxAdmin;
    @View
    private Textbox tbxTipo_usuario;
    @View
    private Textbox tbxDireccion;
    @View
    private Textbox tbxBarrio;
    @View
    private Textbox tbxArea_paciente;
    @View
    private Textbox tbxPertenencia_etnica;
    @View
    private Textbox tbxGrupo_poblacional;
    @View
    private Textbox tbxNro_ingreso;

    @View
    private Datebox dtbxFecha_diligenciamiento;
    @View
    private Groupbox gbxValoracion;

    private final String[] idsExcluyentes = {"tbxNro_identificacion",
        "btnLimpiarPaciente", "tbxTipo", "btCancel", "btGuardar", "tbxAccion",
        "btNew", "lbxFormato", "btImprimir", "lbxAnio", "tbxTipo_id", "tbxEdad", "tbxOcupacion",
        "tbxSexo", "tbxPais", "tbxDpto", "tbxMun", "tbxAdmin", "tbxTipo_usuario", "tbxDireccion", "tbxBarrio",
        "tbxArea_paciente", "tbxPertenencia_etnica", "tbxGrupo_poblacional", "tbxNomPaciente", "lbxParameter", "tbxValue", "gridGradoDiscapacidadMacro"};

    @Override
    public void init() throws Exception {
        listarCombos();
        buscarRegistros();
    }

    /**
     * Sobreescritura del metodo params(Map<String, Object> map) para obtener
     * los parametros iniciales con los que trabajara la historia clinica
     */
    @Override
    public void params(Map<String, Object> map) {
        if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
            admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			//log.info("admision"+admision);

        }
    }

    public void buscarRegistros() {

        try {
			//log.info("admision"+admision);
            //log.info("empresa"+codigo_empresa+" SUCURSAL "+codigo_sucursal+" id"+admision.getNro_identificacion());

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
            parameters.put("identificacion", admision.getNro_identificacion());

            List<Discapacidades_lepra> lista_datos = discapacidades_lepraService.listar(parameters);
			//log.info("lista_datos>>>>"+lista_datos);

            if (lista_datos == null) {
                tbxNro_identificacion.setValue(admision.getNro_identificacion());
                tbxNomPaciente.setValue(admision.getPaciente().getNombreCompleto());

                cargarInfoPaciente(admision.getNro_identificacion(), admision);
                FormularioUtil.deshabilitarComponentes(Discapacidades_lepraAction.this, false, idsExcluyentes);
            } else {
                mostrarDatos(lista_datos, admision);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private void cargarInfoPaciente(String nro_identificacion, Admision admision) {
        try {
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(codigo_empresa);
            paciente.setCodigo_sucursal(codigo_sucursal);
            paciente.setNro_identificacion(nro_identificacion);
            paciente = getServiceLocator().getPacienteService().consultar(paciente);

            if (paciente != null) {

                Adicional_paciente adicional_paciente = new Adicional_paciente();
                adicional_paciente.setCodigo_empresa(codigo_empresa);
                adicional_paciente.setCodigo_sucursal(codigo_sucursal);
                adicional_paciente.setNro_identificacion(paciente
                        .getNro_identificacion());
                adicional_paciente = getServiceLocator().getServicio(
                        GeneralExtraService.class).consultar(adicional_paciente);

                tbxNro_ingreso.setValue(admision.getNro_ingreso());

                Elemento tipo_id = new Elemento();
                tipo_id.setTipo("tipo_id");
                tipo_id.setCodigo(paciente.getTipo_identificacion());
                tipo_id = getServiceLocator().getElementoService().consultar(tipo_id);
                tbxTipo_id.setValue((tipo_id != null ? tipo_id.getDescripcion() : ""));

                Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(paciente
                        .getFecha_nacimiento());
                Integer anios = mapa_edades.get("anios");
                tbxEdad.setValue(anios + (anios == 1 ? " año " : " años "));

                Ocupaciones ocupaciones = new Ocupaciones();
                ocupaciones.setId(paciente.getCodigo_ocupacion());
                ocupaciones = getServiceLocator().getOcupacionesService()
                        .consultar(ocupaciones);
                tbxOcupacion.setValue(ocupaciones != null ? ocupaciones
                        .getNombre() : "");

                tbxSexo.setValue(Utilidades.getNombreElemento(paciente.getSexo(),
                        "sexo", Discapacidades_lepraAction.this));

                tbxPais.setValue("Colombia");

                Departamentos departamentos = new Departamentos(
                        paciente.getCodigo_dpto(), "");
                departamentos = getServiceLocator()
                        .getDepartamentosService().consultar(departamentos);

                tbxDpto.setValue((departamentos != null ? departamentos.getNombre() : ""));

                Municipios municipios = new Municipios(
                        paciente.getCodigo_municipio(), paciente.getCodigo_dpto(),
                        "");
                municipios = getServiceLocator().getMunicipiosService()
                        .consultar(municipios);
                tbxMun.setValue((municipios != null ? municipios.getNombre() : ""));

                tbxAdmin
                        .setValue(admision.getAdministradora() != null ? (admision
                                .getAdministradora().getCodigo() + "-" + admision
                                .getAdministradora().getNombre()) : "");

                tbxTipo_usuario.setValue(Utilidades.getNombreElemento(
                        paciente.getTipo_usuario(), "tipo_usuario", Discapacidades_lepraAction.this));

                tbxDireccion.setValue((paciente != null ? paciente.getDireccion() : ""));

                Barrio barrio = new Barrio();
                barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
                barrio = getServiceLocator().getBarrioService()
                        .consultar(barrio);
                tbxBarrio.setValue(barrio != null ? ("("
                        + barrio.getCodigo_barrio() + ") " + barrio.getBarrio())
                        : "");

                tbxArea_paciente.setValue(Utilidades.getNombreElemento(paciente.getArea_paciente(),
                        "area_paciente", Discapacidades_lepraAction.this));

                Pertenencia_etnica etnica = new Pertenencia_etnica();
                etnica.setId(admision.getPaciente().getPertenencia_etnica());
                etnica = getServiceLocator()
                        .getPertenencia_etnicaService().consultar(etnica);

                tbxPertenencia_etnica.setValue("("
                        + admision.getPaciente().getPertenencia_etnica() + ") "
                        + (etnica != null ? etnica.getNombre() : ""));

                tbxGrupo_poblacional.setValue("");

            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MensajesUtil.mensajeAlerta("Alerta !!!", e.getMessage());
        }
    }

    public void listarCombos() {
        listarParameter();
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("fil.nro_identificacion");
        listitem.setLabel("Identificacion");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("t2.nombre1||' '||t2.nombre2");
        listitem.setLabel("Nombres");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("t2.apellido1||' '||t2.apellido2");
        listitem.setLabel("Apellidos");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    //Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        groupboxConsulta.setVisible(!sw);
        groupboxEditar.setVisible(sw);

        if (!accion.equalsIgnoreCase("registrar")) {
            //buscarDatos();
        } else {
            //buscarDatos();
            limpiarDatos();
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);

        tbxNro_identificacion.setValue("");
        tbxNro_identificacion.setDisabled(false);
        btnLimpiarPaciente.setVisible(false);
        FormularioUtil.deshabilitarComponentes(this, true, idsExcluyentes);

        tbxNomPaciente.setValue("");
        tbxNro_ingreso.setValue("");
        tbxTipo_id.setValue("");
        tbxEdad.setValue("");
        tbxOcupacion.setValue("");
        tbxSexo.setValue("");
        tbxPais.setValue("");
        tbxDpto.setValue("");
        tbxMun.setValue("");
        tbxAdmin.setValue("");
        tbxTipo_usuario.setValue("");
        tbxDireccion.setValue("");
        tbxBarrio.setValue("");
        tbxArea_paciente.setValue("");
        tbxPertenencia_etnica.setValue("");
        tbxGrupo_poblacional.setValue("");

        for (int i = 1; i <= 10; i++) {

            GridGradoDiscapacidadMacro gridGradoDiscapacidadMacro = (GridGradoDiscapacidadMacro) getFellow("gridGradoDiscapacidadMacro" + i).getFirstChild();
            gridGradoDiscapacidadMacro.limpiar();
        }

    }

    //Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        boolean valida = true;

        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres() + " recuerde que...", "Los campos marcados con (*) son obligatorios");
        }

        return valida;
    }
    /*
     //Metodo para buscar //
     public void buscarDatos()throws Exception{
     try {
     String parameter = lbxParameter.getSelectedItem().getValue().toString();
     String value = tbxValue.getValue().toUpperCase().trim();
			
     Map<String,Object> parameters = new HashMap<String,Object>();
     parameters.put("parameter", parameter);
     parameters.put("value", "%"+value+"%");
			
     discapacidades_lepraService.setLimit("limit 25 offset 0");
			
     List<Discapacidades_lepra> lista_datos = discapacidades_lepraService.listar_pacientes_lepra(parameters);
     rowsResultado.getChildren().clear();
			
     for (Discapacidades_lepra discapacidades_lepra : lista_datos) {
     rowsResultado.appendChild(crearFilas(discapacidades_lepra, this));
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
		
     final Discapacidades_lepra discapacidades_lepra = (Discapacidades_lepra)objeto;
		
     Paciente paciente = new Paciente();
     paciente.setCodigo_empresa(discapacidades_lepra.getCodigo_empresa());
     paciente.setCodigo_sucursal(discapacidades_lepra.getCodigo_sucursal());
     paciente.setNro_identificacion(discapacidades_lepra.getIdentificacion());
     paciente = getServiceLocator().getPacienteService().consultar(paciente);
		
     Hbox hbox = new Hbox();
     Space space = new Space();
		
     fila.setStyle("text-align: justify;nowrap:nowrap");
     fila.appendChild(new Label(discapacidades_lepra.getCodigo_historia()+""));
     fila.appendChild(new Label(discapacidades_lepra.getIdentificacion()+""));
     fila.appendChild(new Label((paciente!=null?paciente.getApellido1()+" "+paciente.getApellido2():"")));
     fila.appendChild(new Label((paciente!=null?paciente.getNombre1()+" "+paciente.getNombre2():"")));
		
		
     hbox.appendChild(space);
		
     Image img = new Image();
     img.setSrc("/images/editar.gif");
     img.setTooltiptext("Editar");
     img.setStyle("cursor: pointer");
     img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
     @Override
     public void onEvent(Event arg0) throws Exception {
     //mostrarDatos(discapacidades_lepra);
     }
     });
     hbox.appendChild(img);
		
		
     fila.appendChild(hbox);
		
     return fila;
     }
     */

    //Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
				//Cargamos los componentes //

                Discapacidades_lepra discapacidades_lepra = new Discapacidades_lepra();

                discapacidades_lepra.setCodigo_empresa(empresa.getCodigo_empresa());
                discapacidades_lepra.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                discapacidades_lepra.setCodigo_historia(tbxCodigo_historia.getValue());
                discapacidades_lepra.setIdentificacion(tbxNro_identificacion.getValue());
                discapacidades_lepra.setNro_ingreso(tbxNro_ingreso.getValue());

				//log.info("empresa"+empresa);
                Map<String, Object> datos = new HashMap<String, Object>();
                datos.put("discapacidades_lepra", discapacidades_lepra);
                datos.put("accion", tbxAccion.getText());
                datos.put("listado_discapacidades", obtenerListado());

                discapacidades_lepraService.guardar(datos);

                tbxAccion.setValue("modificar");
                //infoPacientes.setCodigo_historia(his_tarjeta_tuberculosis.getCodigo_historia());

                MensajesUtil.mensajeInformacion("Informacion ..",
                        "Los datos se guardaron satisfactoriamente");

                MensajesUtil.mensajeInformacion("Informacion ..", "Los datos se guardaron satisfactoriamente");
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    //Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(List<Discapacidades_lepra> lista_datos, Admision registro) throws Exception {
        try {
			//log.info("2 >>>>"+lista_datos);

            tbxNro_identificacion.setValue(registro.getNro_identificacion());
            tbxNomPaciente.setValue(registro.getPaciente().getNombreCompleto());

            cargarInfoPaciente(registro.getNro_identificacion(), registro);

            mostrarInformacionMacro(lista_datos);

            tbxNro_identificacion.setDisabled(true);
            btnLimpiarPaciente.setVisible(false);

            for (int i = 1; i <= 10; i++) {

                GridGradoDiscapacidadMacro gridGradoDiscapacidadMacro = (GridGradoDiscapacidadMacro) getFellow("gridGradoDiscapacidadMacro" + i).getFirstChild();
                if (gridGradoDiscapacidadMacro.getDtbFechaMc().getValue() != null) {
                    //log.info("ok"+i);
                    FormularioUtil.deshabilitarComponentes(gridGradoDiscapacidadMacro, true, idsExcluyentes);
                } else {
                    FormularioUtil.deshabilitarComponentes(gridGradoDiscapacidadMacro, false, idsExcluyentes);
                    gridGradoDiscapacidadMacro.limpiar();
                }
            }

            //Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public List<Discapacidades_lepra> obtenerListado() {

        List<Discapacidades_lepra> listado_discapacidades = new ArrayList<Discapacidades_lepra>();

        for (int i = 1; i <= 10; i++) {

            GridGradoDiscapacidadMacro gridGradoDiscapacidadMacro = (GridGradoDiscapacidadMacro) getFellow("gridGradoDiscapacidadMacro" + i).getFirstChild();

            // int i = 0;
            Discapacidades_lepra discapacidades_lepra = new Discapacidades_lepra();

            if (gridGradoDiscapacidadMacro.getDtbFechaMc().getValue() != null) {
                discapacidades_lepra.setFecha(new Timestamp(gridGradoDiscapacidadMacro.getDtbFechaMc().getValue().getTime()));

            } else {
                discapacidades_lepra.setFecha(null);

            }

            discapacidades_lepra.setTipo("2");
            discapacidades_lepra.setAnio(i + "");

            if (gridGradoDiscapacidadMacro.getRdbRadioOjosDerechoMc().getSelectedItem() != null) {
                discapacidades_lepra.setOjo_derecha(gridGradoDiscapacidadMacro.getRdbRadioOjosDerechoMc().getSelectedItem().getValue().toString());

            } else {
                discapacidades_lepra.setOjo_derecha("");
            }

            if (gridGradoDiscapacidadMacro.getRdbRadioOjosIzquierdoMc().getSelectedItem() != null) {
                discapacidades_lepra.setOjo_izquierda(gridGradoDiscapacidadMacro.getRdbRadioOjosIzquierdoMc().getSelectedItem().getValue().toString());

            } else {
                discapacidades_lepra.setOjo_izquierda("");
            }

            if (gridGradoDiscapacidadMacro.getRdbRadioManosDerechoMc().getSelectedItem() != null) {
                discapacidades_lepra.setMano_derecha(gridGradoDiscapacidadMacro.getRdbRadioManosDerechoMc().getSelectedItem().getValue().toString());

            } else {
                discapacidades_lepra.setMano_derecha("");
            }

            if (gridGradoDiscapacidadMacro.getRdbRadioManosIzquierdoMc().getSelectedItem() != null) {
                discapacidades_lepra.setMano_izquierda(gridGradoDiscapacidadMacro.getRdbRadioManosIzquierdoMc().getSelectedItem().getValue().toString());

            } else {
                discapacidades_lepra.setMano_izquierda("");
            }

            if (gridGradoDiscapacidadMacro.getRdbRadioPiesDerechoMc().getSelectedItem() != null) {
                discapacidades_lepra.setPie_derecha(gridGradoDiscapacidadMacro.getRdbRadioPiesDerechoMc().getSelectedItem().getValue().toString());

            } else {
                discapacidades_lepra.setPie_derecha("");
            }

            if (gridGradoDiscapacidadMacro.getRdbRadioPiesIzquierdoMc().getSelectedItem() != null) {
                discapacidades_lepra.setPie_izquierda(gridGradoDiscapacidadMacro.getRdbRadioPiesIzquierdoMc().getSelectedItem().getValue().toString());

            } else {
                discapacidades_lepra.setPie_izquierda("");
            }

            if (gridGradoDiscapacidadMacro.getRdbRadioGradoMaximoMc().getSelectedItem() != null) {
                discapacidades_lepra.setGrado_maximo(gridGradoDiscapacidadMacro.getRdbRadioGradoMaximoMc().getSelectedItem().getValue().toString());

            } else {
                discapacidades_lepra.setGrado_maximo("");
            }

            listado_discapacidades.add(discapacidades_lepra);

        }

        return listado_discapacidades;

    }

    public void mostrarInformacionMacro(List<Discapacidades_lepra> listado_discapacidades) {

		//log.info("3 >>>>"+listado_discapacidades);
        if (listado_discapacidades != null) {
            int i = 1;
            for (Discapacidades_lepra discapacidades_lepra : listado_discapacidades) {

                GridGradoDiscapacidadMacro gridGradoDiscapacidadMacro = (GridGradoDiscapacidadMacro) getFellow("gridGradoDiscapacidadMacro" + i).getFirstChild();

                if (discapacidades_lepra.getFecha() != null) {
                    gridGradoDiscapacidadMacro.getDtbFechaMc().setValue(new Date(discapacidades_lepra.getFecha().getTime()));
                } else {
                    gridGradoDiscapacidadMacro.getDtbFechaMc().setValue(null);
                }

                if (discapacidades_lepra.getOjo_derecha() != null) {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioOjosDerechoMc(), discapacidades_lepra.getOjo_derecha());
                } else {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioOjosDerechoMc(), "");
                }

                if (discapacidades_lepra.getPie_derecha() != null) {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioPiesDerechoMc(), discapacidades_lepra.getPie_derecha());
                } else {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioPiesDerechoMc(), "");
                }
                if (discapacidades_lepra.getMano_derecha() != null) {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioManosDerechoMc(), discapacidades_lepra.getMano_derecha());
                } else {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioManosDerechoMc(), "");
                }

                if (discapacidades_lepra.getOjo_izquierda() != null) {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioOjosIzquierdoMc(), discapacidades_lepra.getOjo_izquierda());
                } else {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioOjosIzquierdoMc(), "");
                }

                if (discapacidades_lepra.getPie_izquierda() != null) {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioPiesIzquierdoMc(), discapacidades_lepra.getPie_izquierda());
                } else {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioPiesIzquierdoMc(), "");
                }
                if (discapacidades_lepra.getMano_izquierda() != null) {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioManosIzquierdoMc(), discapacidades_lepra.getMano_izquierda());
                } else {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioManosIzquierdoMc(), "");
                }

                if (discapacidades_lepra.getGrado_maximo() != null) {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioGradoMaximoMc(), discapacidades_lepra.getGrado_maximo());
                } else {
                    Utilidades.seleccionarRadio(gridGradoDiscapacidadMacro.getRdbRadioGradoMaximoMc(), "");
                }

                i++;

            }
        }

    }

}
