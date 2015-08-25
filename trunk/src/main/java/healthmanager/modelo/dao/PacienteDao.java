/*
 * PacienteDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Paciente;
import java.sql.Timestamp;

public interface PacienteDao {

    /**
     * El metodo de crear pacientes a la hora de realizar el registro ejecuta
     * trigger el cual registra que se creo el paciente <br/>
     * Informacion de base de datos:<br/>
     *     * trigger -> trazabilidad_actualizar<br/>
     *     * funtiones -> crearActualizacionTrazabilidad y trazabilidad_actualizar
     *
     * @author Luis Miguel
	 *
     */
    void crear(Paciente paciente);

    /**
     * El metodo de actualizar pacientes a la hora de realizar la actualizacion
     * ejecuta un trigger compara si ahi un cambio en los campos del paciente y
     * realiza un registro dependiendo los cambios encontrados<br/>
     * Informacion de base de datos:<br/>
     *     * trigger -> trazabilidad_actualizar<br/>
     *     * funtiones -> crearActualizacionTrazabilidad y trazabilidad_actualizar
     *
     * @author Luis Miguel
	 *
     */
    int actualizar(Paciente paciente);

    Paciente consultar(Paciente paciente);

    Paciente consultarPorLoginPassword(Paciente paciente);

    Paciente consultarPorDocumento(Paciente paciente);

    int eliminar(Paciente paciente);

    List<Paciente> listar(Map<String, Object> parameters);

    List<Paciente> listar_por_centro(Map<String, Object> parameters);

    List<Paciente> listar_conTuberculosis_lepra(Map<String, Object> parameters);

    List<Paciente> listarPacienteSaludMental(Map<String, Object> parameters);

    List<Paciente> listarGraficos(Map<String, Object> parameters);

    void setLimit(String limit);

    boolean existe(Map<String, Object> param);

    List<Paciente> consultarPorInformacion(Paciente paciente);

    Integer totalResultados(Map<String, Object> parametros);

    Timestamp getFechaAfiliacion(Map<String, Object> map);
    
    int actualizarFechaNacimiento(Paciente paciente);

}
