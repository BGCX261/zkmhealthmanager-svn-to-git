package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Aportantes_ma;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.Afiliaciones_meService;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.L2HContraintDate;
import com.framework.util.MensajesUtil;

public class ArchivosPlanosAfiliacionesAction extends ZKWindow {

	@View private Datebox dtx_init_me;
	@View private Datebox dtx_end_me;
	@View private Datebox dtx_init_ma;
	@View private Datebox dtx_end_ma;
	@View private Datebox dtx_init_ne;
	@View private Datebox dtx_end_ne;
	@View private Datebox dtx_init_e1;
	@View private Datebox dtx_end_e1;
	@View private Datebox dtx_init_e4;
	@View private Datebox dtx_end_e4;
	
	@View private Datebox dtx_init_054;
	@View private Datebox dtx_end_054;
	
	@View private Datebox dtx_init_198;
	@View private Datebox dtx_end_198;
	
	/* selectores */
	@View private Checkbox check_me;
	@View private Checkbox check_ne;
	@View private Checkbox check_ma;
	@View private Checkbox check_e1;
	@View private Checkbox check_e4;
	
	@View private Checkbox check_054;
	@View private Checkbox check_198;
	
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora;
	@View
	private Toolbarbutton btnLimpiarAseguradora;
	
	private final String format_time = "dd/MM/yyyy";
	private final int BUFFER = 2048;
	
	private final String formato_comprimido = ".zip";
	
	
	public void onCheck(boolean isCheck, Datebox dateboxInit, Datebox dateboxEnd){
		dateboxInit.setDisabled(!isCheck);
		dateboxEnd.setDisabled(!isCheck);
	}
	
	
	private boolean validar(){
		boolean validar = true;
		
	    String msj = "";	
		if (parametros_empresa.getCodigo_ministerio() == null
				|| parametros_empresa.getCodigo_ministerio().trim()
						.isEmpty()) {
			validar = false;
			msj = "Para realizar esta accion es requerido el código ministerio en los parametros de empresa (Administracion > parametros empresa)";
		}
		
		
		if (parametros_empresa.getCodigo_super_salud() == null
				|| parametros_empresa.getCodigo_super_salud().trim()
						.isEmpty()) {
			validar = false;
			msj = "Para realizar esta accion es requerido el código supersalud en los parametros de empresa (Administracion > parametros empresa)";
		}
		
		
		if(!validar){ 
			MensajesUtil.mensajeAlerta("Adverntencia", "" + msj); 
		}
		
		return validar;
	}
	
	public void generarArchivosPlanos() throws Exception{
		/* creamos directorios */
		File file_archivo_plano = null;
		try {
			if(validar()){
				 if(parametros_empresa != null){
					  if(parametros_empresa.getCodigo_ministerio() != null){
						    String tiempo = new SimpleDateFormat("ddMMyyyyhhmm").format(Calendar.getInstance().getTime());
						    File file = new File(Executions.getCurrent().getDesktop().getWebApp().getRealPath("") + "/Files");
					        if (!file.exists()) { 
					            file.mkdir();
					        }
					        
					        file = new File(file.getAbsolutePath() + "/Temp");
					        if (!file.exists()) { 
					            file.mkdir();
					        }
					        
							file = new File(file.getAbsolutePath()
									+ "/"
									+ empresa.getCodigo_empresa()
									+ ""
									+ sucursal.getCodigo_sucursal() 
									+ ""
									+ tiempo);
							
					        if (!file.exists()) { 
					            file.mkdir();
					        }
					        
					        File fileZip = new File(file.getAbsolutePath() + File.separator + "ZIP");
					        if (!fileZip.exists()) { 
					        	fileZip.mkdir();
					        }
					        
					        file_archivo_plano = new File(file.getAbsolutePath() + File.separator + "plain");
					        if (!file_archivo_plano.exists()) { 
					        	file_archivo_plano.mkdir();
					        }
					        
						/**/
					    //log.info("Direccion de archivos" + file_archivo_plano.getAbsolutePath());
					    crearME(file_archivo_plano);
					    crearMa(file_archivo_plano);
					    crearNE(file_archivo_plano);
					    crearE1(file_archivo_plano);
					    crearE4(file_archivo_plano);
					    crear054(file_archivo_plano);
					    crear198(file_archivo_plano);
					    
					    
					    String nameOfFileZip = fileZip.getAbsolutePath() + File.separator + "Archivos-" + empresa.getNombre_empresa().replaceAll("^[a-zA-Z 0-9]", "") + "-" +(tiempo)  + formato_comprimido;
					    
					    
					    BufferedInputStream origin = null;
				        FileOutputStream dest = new FileOutputStream(nameOfFileZip);
				        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
				        byte data[] = new byte[BUFFER];
				        String files[] = file_archivo_plano.list();
				        for (int i = 0; i < files.length; i++) {
				            FileInputStream fi = new FileInputStream(file_archivo_plano.getAbsolutePath() + File.separator + files[i]);
				            origin = new BufferedInputStream(fi, BUFFER);
				            // creamos una entrada zip
				            ZipEntry entry = new ZipEntry(files[i]);
				            // agregamos entradas zip al archivo
				            out.putNextEntry(entry);
				            int count;
				            while ((count = origin.read(data, 0, BUFFER)) != -1) {
				                out.write(data, 0, count);
				            }
				            origin.close();
				            File aux = new File(file_archivo_plano.getAbsolutePath() + File.separator + files[i]);
				            if (aux.exists()) {
				                aux.delete();
				            }
				        }
				        out.close();

				        FileInputStream archivo = new FileInputStream(nameOfFileZip);
				        int longitud = archivo.available();
				        byte[] datos = new byte[longitud];
				        archivo.read(datos);
				        archivo.close();
				        File del = new File(nameOfFileZip);
				        Filedownload.save(del, "application/zip");
				        limpiar();
					  }else{}
				   }else{throw new ValidacionRunTimeException("Para esta opcion debe registrar los parametros de la empresa");} 
			} 
		} catch (Exception e) { 
			MensajesUtil.mensajeError(e, null, this); 
			if(file_archivo_plano != null){
				file_archivo_plano.deleteOnExit();
			}
		}
	} 
	
	/** Este metodo es para resetear la vista 
	 *  @author Idadyou
	 *  @param ninguno
	 * 
	 * */
	private void limpiar(){
		check_me.setChecked(false);
		check_ne.setChecked(false);
		check_ma.setChecked(false);
		check_e1.setChecked(false);
		check_e4.setChecked(false);
		inicializarTiempo();
	}
	
	
	private void inicializarTiempo(){
		//log.info(" inicializamos fechas ");
		Date date = Calendar.getInstance().getTime();
		//log.info(" inicializamos fechas: " + date);
		L2HContraintDate contraintDate = new L2HContraintDate(date, date);
		Date init = contraintDate.getFecha1AsInitOfMonth();
		Date end = contraintDate.getFecha2AsEndOfMonth();
		
		//log.info(" inicializamos fechas: " + init);
		//log.info(" inicializamos fechas: " + end);
		
		/* inicio de fecha */
		dtx_init_e1.setValue(init);
		dtx_init_e4.setValue(init);
		dtx_init_ma.setValue(init);
		dtx_init_me.setValue(init);
		dtx_init_ne.setValue(init);
		
		/* fin de fecha*/
		dtx_end_e1.setValue(end);
		dtx_end_e4.setValue(end);
		dtx_end_ma.setValue(end);
		dtx_end_me.setValue(end);
		dtx_end_ne.setValue(end);
		
		// Deshabilitar
		dtx_init_e1.setDisabled(true); 
		dtx_init_e4.setDisabled(true);
		dtx_init_ma.setDisabled(true);
		dtx_init_me.setDisabled(true);
		dtx_init_ne.setDisabled(true);
		
		dtx_end_e1.setDisabled(true);
		dtx_end_e4.setDisabled(true);
		dtx_end_ma.setDisabled(true);
		dtx_end_me.setDisabled(true);
		dtx_end_ne.setDisabled(true);
	}
	
	
	
	/* creamos archivos */
	private String crearME(File dir) throws Exception{
		if(check_me.isChecked()){
			Map param = new HashMap();
			param.put("codigo_empresa", sucursal.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param.put("fecha_init", dtx_init_me.getValue());
			param.put("fecha_end", dtx_end_me.getValue());
			// Agregamos la administradora
			Administradora administradora = tbxCodigo_administradora.getRegistroSeleccionado();
			param.put("codigo_administradora", administradora != null ? administradora.getCodigo() : null);
			List<Afiliaciones_me> list_me = getServiceLocator().getAfiliacionesMeService().listar(param);
			//log.info("Size: " + list_me.size());
			StringBuilder stringBuilder = new StringBuilder();
			for (Afiliaciones_me afiliacionesMe : list_me) {
				 stringBuilder.append(parametros_empresa.getCodigo_ministerio() + ",");
				 
				 Paciente paciente = afiliacionesMe.getPaciente();
				 
				 Afiliaciones_me afiliacionesMeCotizante = new Afiliaciones_me();
				 afiliacionesMeCotizante.setCodigo_empresa(sucursal.getCodigo_empresa());
				 afiliacionesMeCotizante.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				 afiliacionesMeCotizante.setNro_identificacion_afiliado(afiliacionesMe.getNro_identificacion_cotizante());
			     afiliacionesMeCotizante = getServiceLocator().getAfiliacionesMeService().consultar(afiliacionesMeCotizante);
				 stringBuilder.append((afiliacionesMeCotizante != null ? afiliacionesMeCotizante.getPaciente().getTipo_identificacion() : "") + ","); 
				 stringBuilder.append(afiliacionesMe.getNro_identificacion_cotizante() + ",");
				 
				 stringBuilder.append(paciente.getNro_identificacion() + ",");
				 stringBuilder.append(paciente.getApellido1() + ",");
				 stringBuilder.append(paciente.getApellido2() + ",");
				 stringBuilder.append(paciente.getNombre1() + ",");
				 stringBuilder.append(paciente.getNombre2() + ",");
				 stringBuilder.append(new SimpleDateFormat(format_time).format(paciente.getFecha_nacimiento()) + ",");
				 stringBuilder.append(paciente.getSexo() + ",");
				 stringBuilder.append(afiliacionesMe.getParenteco_cotizante() + ",");
				 stringBuilder.append(afiliacionesMe.getDepartamento_afiliacion() + ",");
				 stringBuilder.append(afiliacionesMe.getMunicipio_afiliacion() + ",");
				 stringBuilder.append(afiliacionesMe.getZona_afiliacion() + ",");
				 stringBuilder.append(new SimpleDateFormat(format_time).format(afiliacionesMe.getFecha_afiliacion()) + ",");
				 
				 Aportantes_ma aportantesMa = new Aportantes_ma();
				 aportantesMa.setCodigo_empresa(sucursal.getCodigo_empresa());
				 aportantesMa.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				 aportantesMa.setNro_identificacon(afiliacionesMe.getNro_identificacion_aportante());
				 aportantesMa = getServiceLocator().getAportantesMaService().consultar(aportantesMa);
				 
				
				 stringBuilder.append((aportantesMa != null ? aportantesMa.getTipo_identificacion() : "") + ",");
				 stringBuilder.append(afiliacionesMe.getNro_identificacion_aportante());
				 stringBuilder.append("\r\n"); 
			}
			
			
//			int i = 0;
//			for (Map map : list_me) {
//				String in = "";
//				Iterator it = map.entrySet().iterator();
//				while (it.hasNext()) {
//				   Map.Entry e = (Map.Entry)it.next();
//				  
//				   if(e.getKey().toString().startsWith("fecha")){
//					   Timestamp timestamp = (Timestamp) e.getValue();
//					   in += new SimpleDateFormat(format_time).format(timestamp);
//				   }else{
//					   in += e.getValue()+""; 
//				   }
//				   in += ",";
//			    } 
//				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");  
//				stringBuilder.append(in + ((i++) < list_me.size() ? "\n" : ""));
//			}
			String fecha = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
			String nameFile = dir.getAbsolutePath() + File.separator + "ME" + parametros_empresa.getCodigo_ministerio().toUpperCase() + fecha + ".TXT";
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(nameFile));
            dos.writeBytes(stringBuilder.toString());
            dos.close();
            return nameFile;
		}
		return null;
	}
	
	
	
	private String crearMa(File dir) throws Exception{
		if(check_ma.isChecked()){
			
			Map param = new HashMap();
			param.put("codigo_empresa", sucursal.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param.put("fecha_init", dtx_init_ma.getValue());
			param.put("fecha_end", dtx_end_ma.getValue());
			List<Map<String, Object>> list_me = getServiceLocator().getAportantesMaService().listarMap(param);
			int i = 0;
			StringBuilder stringBuilder = new StringBuilder();
			for (Map map : list_me) {
				String in = "";
				Iterator it = map.entrySet().iterator();
				while (it.hasNext()) {
				   Map.Entry e = (Map.Entry)it.next();
				  
				   if(e.getValue() != null)
				   if(e.getKey().toString().startsWith("fecha")){
					   Timestamp timestamp = (Timestamp) e.getValue();
					   in += new SimpleDateFormat(format_time).format(timestamp);
				   }else{
					   in += e.getValue()+""; 
				   }
				   in += ",";
			    }
				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");
				stringBuilder.append(in);
				if((i++) < list_me.size())stringBuilder.append("\r\n");
			}
			String fecha = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
			String nameFile = dir.getAbsolutePath() + File.separator + "MA" + parametros_empresa.getCodigo_ministerio().toUpperCase() + fecha + ".TXT";
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(nameFile));
            dos.writeBytes(stringBuilder.toString());
            dos.close();
            return nameFile;
		}
		return null;
	}
	
	
	private String crearNE(File dir) throws Exception{
		if(check_ne.isChecked()){
			Map param = new HashMap();
			param.put("codigo_empresa", sucursal.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param.put("fecha_init", dtx_init_ne.getValue());
			param.put("fecha_end", dtx_end_ne.getValue());
			// Agregamos la administradora
			Administradora administradora = tbxCodigo_administradora.getRegistroSeleccionado();
			param.put("codigo_administradora", administradora != null ? administradora.getCodigo() : null);
			List<Map> list_me = getServiceLocator().getNovedadesNeService().listarMap(param);
			int i = 0;
			StringBuilder stringBuilder = new StringBuilder();
			String[] record = {"a", "b","c", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u"};
			for (Map map : list_me) {
				String in = "";
				for (String string : record) {
					 Object value = map.get(string);
					 if(value != null)
					 if(value instanceof Timestamp){
						 Timestamp timestamp = (Timestamp) value;
						 in += new SimpleDateFormat(format_time).format(timestamp);
					 }else  in += value+""; 
					 in += ",";
				}
				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");
				stringBuilder.append(in);
				if((i++) < list_me.size())stringBuilder.append("\r\n");
				
//				String in = "";
//				Iterator it = map.entrySet().iterator();
//				while (it.hasNext()) {
//				   Map.Entry e = (Map.Entry)it.next();
//				   if(e.getValue() != null)
//				   if(e.getKey().toString().startsWith("b5")){
//					   Timestamp timestamp = (Timestamp) e.getValue();
//					   in += new SimpleDateFormat(format_time).format(timestamp);
//				   }else{
//					   in += e.getValue()+""; 
//				   }
//				   in += ",";
//			    } 
//				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");
//				stringBuilder.append(in);
//				if((i++) < list_me.size())stringBuilder.append("\r\n");
			}
			String fecha = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
			String nameFile = dir.getAbsolutePath() + File.separator + "NE" + parametros_empresa.getCodigo_ministerio().toUpperCase() + fecha + ".TXT";
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(nameFile));
            dos.writeBytes(stringBuilder.toString());
            dos.close();
            return nameFile;
		}
		return null;
	}
	
	
	private String crear054(File dir) throws Exception{
		if(check_054.isChecked()){
			Map param = new HashMap();
			param.put("codigo_empresa", sucursal.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
//			param.put("fecha_init", dtx_init_054.getValue());
//			param.put("fecha_end", dtx_end_054.getValue());
			// Agregamos la administradora
			Administradora administradora = tbxCodigo_administradora.getRegistroSeleccionado();
			param.put("codigo_administradora", administradora != null ? administradora.getCodigo() : null);
			List<Map<String, Object>> list_me = getServiceLocator().getServicio(Afiliaciones_meService.class).listarMap054(param);
			int i = 0; 
			
			StringBuilder stringBuilder = new StringBuilder();
			String separador = ",";
			for (Map map : list_me) {
				
				String nro_identificacion = (String) map.get("nro_identificacion_afiliado");
				String tipo = (String) map.get("tipo_afiliado");
				
				Timestamp timestamp = (Timestamp) map.get("fecha_vinculacion");
				if(tipo.equals("C") && timestamp ==  null){
					throw new ValidacionRunTimeException("Fecha de vinculacion no puede ser nula para el cotizante con el nro identificacion " + nro_identificacion);
				}
				
				stringBuilder.append(parametros_empresa.getCodigo_super_salud() + separador);
				stringBuilder.append(validarNull(map.get("tipo_identificacion_cotizante")) + separador);
				stringBuilder.append(validarNull(map.get("nro_identificacion_cotizante")) + separador); 
				stringBuilder.append(validarNull(map.get("tipo_identificacion_afiliado")) + separador); 
				stringBuilder.append(nro_identificacion + separador); 
				stringBuilder.append(map.get("apellido1_afiliado") + separador); 
				stringBuilder.append(map.get("apellido2_afiliado") + separador); 
				stringBuilder.append(map.get("nombre1_afiliado") + separador); 
				stringBuilder.append(validarNull(map.get("nombre2_afiliado")) + separador); 
				stringBuilder.append(new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("fecha_nacimiento_afiliado")) + separador); 
				stringBuilder.append(map.get("sexo_afiliado") + separador);
				stringBuilder.append(tipo + separador); 
				stringBuilder.append(validarNull(map.get("parenteco_cotizante")) + separador); 
				stringBuilder.append(map.get("departamento_afiliacion") + separador); 
				stringBuilder.append(map.get("municipio_afiliacion") + separador); 
				stringBuilder.append(validarNull(map.get("zona_afiliacion")) + separador); 
				stringBuilder.append(new SimpleDateFormat("dd/MM/yyyy").format((Date)map.get("fecha_afiliacion")) + separador); 
				stringBuilder.append(validarNull(map.get("tipo_identificacion_aportante")) + separador); 
				stringBuilder.append(validarNull(map.get("nro_identificacon_aportante")) + separador); 
				stringBuilder.append((timestamp != null ? new SimpleDateFormat("dd/MM/yyyy").format((Date)timestamp) : "") + separador); 
				stringBuilder.append(validarNull(map.get("revision_ciiu"))); 
				
				if((i++) < list_me.size())stringBuilder.append("\r\n");

			}
			String fecha = new SimpleDateFormat("MMyyyy").format(Calendar.getInstance().getTime());
			String nameFile = dir.getAbsolutePath() + File.separator + parametros_empresa.getCodigo_super_salud().toUpperCase() + fecha + "054.TXT";
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(nameFile));
            dos.writeBytes(stringBuilder.toString());
            dos.close();
            return nameFile;
		}
		return null;
	}
	
	private String validarNull(Object valor){
		if(valor == null){
			return "";
		}
		return valor + "";
	}
	
	
	private String crear198(File dir) throws Exception{
		if(check_198.isChecked()){
			
			Map param = new HashMap();
			param.put("codigo_empresa", sucursal.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			// Agregamos la administradora
			Administradora administradora = tbxCodigo_administradora.getRegistroSeleccionado();
			param.put("codigo_administradora", administradora != null ? administradora.getCodigo() : null);
//			param.put("fecha_init", dtx_init_198.getValue());
//			param.put("fecha_end", dtx_end_198.getValue());
			Map<String, Object> map = getServiceLocator().getServicio(Afiliaciones_meService.class).getMap198(param);
			StringBuilder stringBuilder = new StringBuilder();
			String separador = ",";
				Long total_afiliados = (Long) map.get("afiliados_activos");
				
				Long total_activos_hombres_mayores_60 = (Long) map.get("afiliados_hombres_mayor60");
				Long total_activos_mujeres_entre_15_44 = (Long) map.get("afiliados_hombres_mujeres1544");
				
				
				int porcentaje_hombres = (int)(total_activos_hombres_mayores_60 * 100 / total_afiliados);
				int porcentaje_mujeres = (int)(total_activos_mujeres_entre_15_44 * 100 / total_afiliados);
				
				stringBuilder.append(empresa.getNro_identificacion().replaceAll("[-].+", "") + separador); 
				stringBuilder.append(empresa.getNro_identificacion().replaceAll(".+[-]", "") + separador);
				stringBuilder.append(parametros_empresa.getCodigo_super_salud() + separador);
				stringBuilder.append(new SimpleDateFormat("MM").format(Calendar.getInstance().getTime()) + separador);
				stringBuilder.append(new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime()) + separador);
				stringBuilder.append("198" + separador);
				stringBuilder.append(total_afiliados + separador);
				stringBuilder.append(map.get("afiliados_hombres") + separador); 
				stringBuilder.append(porcentaje_hombres + separador); 
				stringBuilder.append(map.get("afiliados_mujeres") + separador); 
				stringBuilder.append(porcentaje_mujeres); 
				
				
			String fecha = new SimpleDateFormat("MMyyyy").format(Calendar.getInstance().getTime());
			String nameFile = dir.getAbsolutePath() + File.separator + parametros_empresa.getCodigo_super_salud().toUpperCase() + fecha + "198.TXT";
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(nameFile));
            dos.writeBytes(stringBuilder.toString());
            dos.close();
            return nameFile;
		}
		return null;
	}
	
	
	private String crearE1(File dir) throws Exception{
		if(check_e1.isChecked()){
			Map param = new HashMap();
			param.put("codigo_empresa", sucursal.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param.put("fecha_init", dtx_init_e1.getValue());
			param.put("fecha_end", dtx_end_e1.getValue());
			List<Map> list_me = getServiceLocator().getSolicitudE1Service().listarMap(param);
			int i = 0;
			StringBuilder stringBuilder = new StringBuilder();
			for (Map map : list_me) {
				String in = "";
				String[] record = {"a", "b","c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y"};
				for (String string : record) {
					 Object value = map.get(string);
					 if(value != null)
					 if(value instanceof Timestamp){
						 Timestamp timestamp = (Timestamp) value;
						 in += new SimpleDateFormat(format_time).format(timestamp);
					 }else  in += value+""; 
					 in += ",";
				}
				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");
				stringBuilder.append(in);
				if((i++) < list_me.size())stringBuilder.append("\r\n");
				
//				Iterator it = map.entrySet().iterator();
//				while (it.hasNext()) {
//				   Map.Entry e = (Map.Entry)it.next();
//				   if(e.getValue() != null)
//				   if(e.getKey().toString().startsWith("fecha")){
//					   Timestamp timestamp = (Timestamp) e.getValue();
//					   in += new SimpleDateFormat(format_time).format(timestamp);
//				   }else{
//					   in += e.getValue()+""; 
//				   }
//				  in += ",";
//			    }
//				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");
//				stringBuilder.append(in);
//				if((i++) < list_me.size())stringBuilder.append("\r\n");
			}
			String fecha = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
			String nameFile = dir.getAbsolutePath() + File.separator + "E1" + parametros_empresa.getCodigo_ministerio().toUpperCase() + fecha + ".TXT";
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(nameFile));
            dos.writeBytes(stringBuilder.toString());
            dos.close();
            return nameFile;
		}
		return null;
	}
	
	
	private String crearE4(File dir) throws Exception{
		if(check_e4.isChecked()){
			Map param = new HashMap();
			param.put("codigo_empresa", sucursal.getCodigo_empresa());
			param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			param.put("fecha_init", dtx_init_e4.getValue());
			param.put("fecha_end", dtx_end_e4.getValue());
			List<Map> list_me = getServiceLocator().getRespuestaSolicitudE4Service().listarMap(param);
			int i = 0;
			StringBuilder stringBuilder = new StringBuilder();
			for (Map map : list_me) {
				String in = "";
				String[] record = {"a", "b","c", "d", "e", "f", "g"};
				for (String string : record) {
					 Object value = map.get(string);
					 if(value != null)
					 if(value instanceof Timestamp){
						 Timestamp timestamp = (Timestamp) value;
						 in += new SimpleDateFormat(format_time).format(timestamp);
					 }else  in += value+""; 
					 in += ",";
				}
				
				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");
				stringBuilder.append(in);
				if((i++) < list_me.size())stringBuilder.append("\r\n");
				
//				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");
//				stringBuilder.append(in);
//				if((i++) < list_me.size())stringBuilder.append("\r\n");
//				Iterator it = map.entrySet().iterator();
//				while (it.hasNext()) {
//				   Map.Entry e = (Map.Entry)it.next();
//				   if(e.getValue() != null)
//				   if(e.getKey().toString().startsWith("fecha")){
//					   Timestamp timestamp = (Timestamp) e.getValue();
//					   in += new SimpleDateFormat(format_time).format(timestamp);
//				   }else{
//					   in += e.getValue()+""; 
//				   }
//				   in += ",";
//			    }
//				if(in.trim().endsWith(","))in = in.replaceAll("[//,]$", "");
//				stringBuilder.append(in);
//				if((i++) < list_me.size())stringBuilder.append("\r\n");
			}
			String fecha = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
			String nameFile = dir.getAbsolutePath() + File.separator + "E4" + parametros_empresa.getCodigo_ministerio().toUpperCase() + fecha + ".TXT";
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(nameFile));
            dos.writeBytes(stringBuilder.toString());
            dos.close();
            return nameFile;
		}
		return null;
	}
	
	@Override
	public void init() throws Exception {
		 inicializarTiempo();
		 parametrizarBandbox();
	}
	
	
	private void parametrizarBandbox() {
		tbxCodigo_administradora.inicializar(null,
				btnLimpiarAseguradora);
		tbxCodigo_administradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("tercerizada", "N");
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo() + " " + registro.getNombre());
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}
}
