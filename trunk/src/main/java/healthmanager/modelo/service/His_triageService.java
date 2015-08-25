/*
 * His_triageServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo2;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Sigvitales;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.His_triageDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("his_triageService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_triageService implements Serializable{

	@Autowired 
	private His_triageDao his_triageDao;
	@Autowired 
	private ConsecutivoService consecutivoService;
	@Autowired
	private AdmisionDao admisionDao;
	@Autowired
	private GeneralExtraService generalExtraService;
	
	private String limit;

	public His_triage guardar(Map<String, Object> datos){
		try {
			His_triage his_triage = (His_triage) datos.get("his_triage");
			String accion = (String) datos.get("accion");
			Admision admision = (Admision)datos.get("admision");
			Sigvitales sigvitales = (Sigvitales) datos.get("sigvitales");
			
			// copiamos signos
			copiarSignos(his_triage, sigvitales); 
			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_historia = consecutivoService.crearConsecutivo(
						his_triage.getCodigo_empresa(), his_triage.getCodigo_sucursal(),
						IConstantes.CONS_HIS_TRIAGE);
				
				codigo_historia = consecutivoService.getZeroFill(codigo_historia, 10);
				his_triage.setCodigo_historia(codigo_historia);
				
				if (consultar(his_triage) != null) {
					throw new HealthmanagerException(" Nro "+ codigo_historia
									+ " ya se encuentra en la base de datos actualize el consecutivo del registro");
				}
				
				his_triageDao.crear(his_triage);
				consecutivoService.actualizarConsecutivo(his_triage.getCodigo_empresa(),
								his_triage.getCodigo_sucursal(),
								IConstantes.CONS_HIS_TRIAGE,
								his_triage.getCodigo_historia());
				// //log.info("5: "+codigo_historia);
	
			} else {
				his_triageDao.actualizar(his_triage);
			}
			
			if (admision == null) {
				throw new Exception("No hay admision que actualizar");
			}
			
			admision.setRealizo_triage(true);
			if(his_triage.getAdmitido().equalsIgnoreCase("N")){
				admision.setCodigo_medico(his_triage.getCodigo_prestador());
			}
			admisionDao.actualizar(admision);
			
			//diligenciamiento del anexo 2
			Anexo2 anexo2 = new Anexo2();
			anexo2.setCodigo_empresa(admision.getCodigo_empresa());
			anexo2.setCodigo_sucursal(admision.getCodigo_sucursal());
			anexo2.setNro_ingreso(admision.getNro_ingreso());
			anexo2.setNro_documento(admision.getNro_identificacion());
			
			anexo2 = generalExtraService.consultar(anexo2);
			
			if(anexo2 != null){
				anexo2.setTriage(his_triage.getNivel_triage());
				//colocas todos los datos que necesitas de la admision
				Paciente paciente = admision.getPaciente();	
				
				Administradora administradora = new Administradora();
				administradora.setCodigo_empresa(paciente
						.getCodigo_empresa());
				administradora.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				administradora.setCodigo(paciente
						.getCodigo_administradora());	
				
				anexo2.setCodigo_paciente(paciente.getNro_identificacion());
				anexo2.setOrigen_general(admision.getCausa_externa());
				anexo2.setCodigo_administradora(paciente.getCodigo_administradora());
				anexo2.setAseguradora(administradora != null? administradora.getNombre():"");
				anexo2.setCobertura(paciente.getTipo_usuario());
				anexo2.setRemitido(admision.getPaciente_remitido());				
				
				//colocar todos los datos que necesitas de la historia clinica
				generalExtraService.actualizar(anexo2);
			}else{
				anexo2 = new Anexo2();
				anexo2.setCodigo_empresa(admision.getCodigo_empresa());
				anexo2.setCodigo_sucursal(admision.getCodigo_sucursal());
				anexo2.setNro_ingreso(admision.getNro_ingreso());
				anexo2.setNro_documento(admision.getNro_identificacion());
				anexo2.setTriage(his_triage.getNivel_triage());
				
				
				//colocas todos los datos que necesitas de la admision		
				Paciente paciente = admision.getPaciente();	
				
				Administradora administradora = new Administradora();
				administradora.setCodigo_empresa(paciente
						.getCodigo_empresa());
				administradora.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				administradora.setCodigo(paciente
						.getCodigo_administradora());	
				
				anexo2.setCodigo_paciente(paciente.getNro_identificacion());
				anexo2.setOrigen_general(admision.getCausa_externa());
				anexo2.setCodigo_administradora(paciente.getCodigo_administradora());
				anexo2.setAseguradora(administradora != null? administradora.getNombre():"");
				anexo2.setCobertura(paciente.getTipo_usuario());
				anexo2.setRemitido(admision.getPaciente_remitido());
				
				//colocar todos los datos que necesitas de la historia clinica
				
				generalExtraService.crear(anexo2);
			}
			
			return his_triage;
			
		} catch (Exception e) {
			
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	
	/**
	 * Este metodo me permite realizar una copia de los valores de los signos vitales 
	 * a al triage, para el registro de los signos vitales
	 * @author Luis Miguel Hernandez 
	 * */
	private void copiarSignos(His_triage his_triage, Sigvitales sigvitales) {
		if(sigvitales != null){
			his_triage.setCreatinina_cerica(sigvitales
					.getCreatinina_cerica());
			his_triage.setFrecuencia_cardiaca(sigvitales
					.getFrecuencia_cardiaca());
			his_triage.setFrecuencia_respiratoria(sigvitales
					.getFrecuencia_respiratoria());
			his_triage.setImc(sigvitales.getImc());
			his_triage.setPerimetro_cefalico(sigvitales
					.getPerimetro_cefalico());
			his_triage.setPerimetro_toraxico(sigvitales
					.getPerimetro_toraxico());
			his_triage.setPeso(sigvitales.getPeso());
			his_triage.setSuperficie_corporal(sigvitales
					.getSuperficie_corporal());
			his_triage.setTa_diastolica(sigvitales.getTa_diastolica());
			his_triage.setTa_sistolica(sigvitales.getTa_sistolica());
			his_triage.setTa_media(sigvitales.getTa_media());
			his_triage.setTalla(sigvitales.getTalla());
			his_triage.setTemparatura(sigvitales.getTemparatura());
			his_triage.setTfg(sigvitales.getTfg());
		}
	}
	
	public void crear(His_triage his_triage){ 
		try{
			if(consultar(his_triage)!=null){
				throw new HealthmanagerException("His_triage ya se encuentra en la base de datos");
			}
			his_triageDao.crear(his_triage);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(His_triage his_triage){ 
		try{
			return his_triageDao.actualizar(his_triage);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public His_triage consultar(His_triage his_triage){ 
		try{
			return his_triageDao.consultar(his_triage);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public His_triage consultar_historia(His_triage his_triage){ 
		try{
			return his_triageDao.consultar_historia(his_triage);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}
	
	public int eliminar(His_triage his_triage){ 
		try{
			return his_triageDao.eliminar(his_triage);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<His_triage> listar(Map<String,Object> parameters){ 
		try{
			parameters.put("limit",limit);
			return his_triageDao.listar(parameters);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public boolean existe(Map<String,Object> parameters){ 
		return his_triageDao.existe(parameters);
	}
	
}