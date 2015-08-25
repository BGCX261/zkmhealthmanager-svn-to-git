package com.framework.util;

import java.sql.Timestamp;

import healthmanager.modelo.bean.Empresa;
import sios.modelo.service.Hisc_historialSiosService;

import com.framework.constantes.IVias_ingreso;

public class UtilidadesSios {

    public static final String REGISTRO_CLINICO = "01";
    public static final String EVOLUCION_CLINICA = "04";
    public static final String REGISTRO_DE_TRAUMA = "03";
    public static final String ONCOLOGICO_1 = "05";
    public static final String URGENCIA = "07";
    public static final String ODONTOLOGICO = "08";
    public static final String NOTA_URGENCIA = "14";
    public static final String OFTALMOLOGICO = "17";
    public static final String ONCOLOGICO_2 = "27";
    public static final String OPTOMETRIA = "32";
    public static final String ADULTO_MAYOR = "35";
    public static final String RECIEN_NACIDO = "36";
    public static final String ALTERACIONES_MENOR = "38";
    public static final String ALTERACIONES_MENOR_CONTROL = "39";
    public static final String PLANIFICACION_FAMILIAR = "40";
    public static final String ALTERACIONES_DEL_JOVEN = "41";
    public static final String ALTERACIONES_DEL_EMBARAZO = "42";
    public static final String CANCER_CUELLO_UTERINO = "43";
    public static final String HIPERTENSO_DIABETICO = "44";
    public static final String HIPERTENSO_DIABETICO_CONTROL = "45";
    public static final String CONTROL_PRENATAL = "63";

    public static Boolean pacienteTieneRegistro(Hisc_historialSiosService hisc_historialSiosService, Empresa empresa, String id_paciente, String via_ingreso, boolean primera_vez) {
        Integer res = 0;
        if (id_paciente != null && !id_paciente.isEmpty() && empresa != null && empresa.getUtiliza_info_sio()) {
            res = hisc_historialSiosService.cantidadHistoriasId(id_paciente, parserVia(via_ingreso, primera_vez));
        }
        return (res > 0);
    }

    public static Boolean pacienteTieneRegistroDespuesDe(Hisc_historialSiosService hisc_historialSiosService, Empresa empresa, String id_paciente, String via_ingreso, boolean primera_vez, Timestamp fecha) {
        Integer res = 0;
        if (id_paciente != null && !id_paciente.isEmpty() && empresa != null && empresa.getUtiliza_info_sio()) {
            res = hisc_historialSiosService.cantidadHistoriasIdFecha(id_paciente, parserVia(via_ingreso, primera_vez), fecha);
        }
        return (res > 0);
    }

    private static String parserVia(String via_ingreso, boolean primera_vez) {
        String via = "";
        if (via_ingreso.equalsIgnoreCase(IVias_ingreso.CONSULTA_EXTERNA)) {
            if (primera_vez) {
                via = REGISTRO_CLINICO;
            } else {
                via = EVOLUCION_CLINICA;
            }
        } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.URGENCIA)) {
            if (primera_vez) {
                via = URGENCIA;
            } else {
                via = NOTA_URGENCIA;
            }
        } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_MESES)
                || via_ingreso.equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)
                || via_ingreso.equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)
                || via_ingreso.equalsIgnoreCase(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
            if (primera_vez) {
                via = ALTERACIONES_MENOR;
            } else {
                via = ALTERACIONES_MENOR_CONTROL;
            }

        } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.HIPERTENSO_DIABETICO)) {
            if (primera_vez) {
                via = HIPERTENSO_DIABETICO;
            } else {
                via = HIPERTENSO_DIABETICO_CONTROL;
            }
        } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
            via = ALTERACIONES_DEL_EMBARAZO;
        } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.ALTERACION_JOVEN)) {
            via = ALTERACIONES_DEL_JOVEN;
        } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.ADULTO_MAYOR)) {
            via = ADULTO_MAYOR;
        }
        return via;
    }
}
