package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Anio_cuota_moderadora;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Periodo;
import healthmanager.modelo.bean.Salario_anual;
import healthmanager.modelo.service.PeriodoService;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.framework.constantes.IConstantes;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class CuotasModeradorasAction extends ZKWindow {
	
	
	/* componentes */
	@View private Rows rowAnioCuotaModeradora;
	@View private Listbox lbxAnio;
	@View private Doublebox dbxSalarioActivo;
	@View private Doublebox dbxSalarioPensionado;
	
	
	/* contenedor informacion */
	private List<Elemento> list_grupos;
	private List<Elemento> list_tipos_nivel;
	private List<Elemento> list_nivel;
	
	
	/* contenedor objetos */
	private List<Object> listDetalle = new ArrayList<Object>();

	@Override
	public void init() throws Exception {
	   inicializamosElementos(); 
       listarCombos(); 
       cargarInformacionAnioSeleccionado();
	}

	private void inicializamosElementos() {
		list_grupos = getServiceLocator().getElementoService().listar("grupo_cuota");
		list_tipos_nivel = getServiceLocator().getElementoService().listar("tipo_nivel_salario");
		list_nivel = getServiceLocator().getElementoService().listar("nivel_salario");
	}

	private void listarCombos() {
       listarPeriodos();	  	
	}
	 
	private void listarPeriodos() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", codigo_empresa);
		map.put("codigo_sucursal", codigo_sucursal);
		List<Periodo> periodos = getServiceLocator().getServicio(PeriodoService.class).listar(map);
		
		for (Periodo periodo : periodos) {
			Listitem listitem = new Listitem(periodo.getAnio(), periodo);
			listitem.setSelected(anio.toString().equals(periodo.getAnio()));
			lbxAnio.appendChild(listitem); 
		}
		if(lbxAnio.getSelectedItem() == null &&  lbxAnio.getItemCount() > 0){
			lbxAnio.setSelectedIndex(0); 
		}
	}

	/**
	 * Este metodo se ejecuta cada ves, que se selecciona un anio. 
	 * */
	public void cargarInformacionAnioSeleccionado() {
		listDetalle.clear();
		rowAnioCuotaModeradora.getChildren().clear();
		Periodo periodo = lbxAnio.getSelectedItem().getValue();
		
		Salario_anual salario_anual = new Salario_anual();
		salario_anual.setCodigo_empresa(codigo_empresa);
		salario_anual.setCodigo_sucursal(codigo_sucursal);
		salario_anual.setAnio(periodo.getAnio()); 
		salario_anual = getServiceLocator().getSalarioAnualService().consultar(salario_anual);
		
		if(salario_anual != null){
			Res.cargarAutomatica(dbxSalarioActivo, salario_anual, "valor_activos");
			Res.cargarAutomatica(dbxSalarioPensionado, salario_anual, "valor_pensionado"); 
		}else{
			/* inyectamos metodo  */
			salario_anual = new Salario_anual();
			salario_anual.setAnio(periodo.getAnio());
			salario_anual.setCodigo_empresa(codigo_empresa);
			salario_anual.setCodigo_sucursal(codigo_sucursal); 
			Res.cargarAutomatica(dbxSalarioActivo, salario_anual, "valor_activos");
			Res.cargarAutomatica(dbxSalarioPensionado, salario_anual, "valor_pensionado"); 
			listDetalle.add(salario_anual);
		}
		
		for (Elemento elementoGrupo : list_grupos) {
			 Anio_cuota_moderadora anio_cuota_moderadora = new Anio_cuota_moderadora();
			 anio_cuota_moderadora.setAnio(periodo.getAnio());
			 anio_cuota_moderadora.setCodigo_empresa(codigo_empresa);
			 anio_cuota_moderadora.setGrupo(elementoGrupo.getCodigo()); 
			 anio_cuota_moderadora = getServiceLocator().getAnio_cuota_moderadoraService().consultar(anio_cuota_moderadora);
			 rowAnioCuotaModeradora.appendChild(crearRow(elementoGrupo, anio_cuota_moderadora, periodo));
		}
		getFellow("gridRegistros").invalidate();
	}
	
	
	protected Row crearRow(Elemento grupo, Anio_cuota_moderadora cuota_moderadora, Periodo periodo) { 
		Row row =  new Row();
		
		if(cuota_moderadora == null){
			cuota_moderadora = new Anio_cuota_moderadora();
			cuota_moderadora.setAnio(periodo.getAnio()); 
			cuota_moderadora.setCodigo_empresa(codigo_empresa); 
			cuota_moderadora.setCreacion_date(new Timestamp(Calendar.getInstance().getTimeInMillis())); 
			cuota_moderadora.setCreacion_user(usuarios.getCodigo());
			cuota_moderadora.setUltimo_update(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			cuota_moderadora.setUltimo_user(usuarios.getCodigo());  
		}
		listDetalle.add(cuota_moderadora);
		
		/* inicalizamos carga de datos esquema */
		/* grupo */
		row.appendChild(new Label(grupo.getDescripcion())); 
		cuota_moderadora.setGrupo(grupo.getCodigo());  
		
		/* tipo de nivel */
		Listbox listboxTipo = getListBoxElemento(list_tipos_nivel);
		listboxTipo.setHflex("1");
		row.appendChild(listboxTipo);
		Res.cargarAutomatica(listboxTipo, cuota_moderadora, "tipo_nivel");
		
		
		/* niveles */
		Listbox listbox = getListBoxElemento(list_nivel);
		listbox.setHflex("1");
		row.appendChild(listbox);
		cargarAutomaticaEspecialNiveles(listbox, cuota_moderadora, "nivel1");
		
		/* niveles */
		Listbox listboxNivel2 = getListBoxElemento(list_nivel);
		listboxNivel2.setHflex("1");
		row.appendChild(listboxNivel2);
		cargarAutomaticaEspecialNiveles(listboxNivel2, cuota_moderadora, "nivel2");
		
		listboxTipo.setAttribute("listNivel2", listboxNivel2);
		listboxTipo.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				validarAccionTipos(((Listbox)event.getTarget())); 
			}
		});
		validarAccionTipos(listboxTipo); 
		
		/* cuota moderadora*/
		Doublebox doubleBox = new Doublebox(1);
		doubleBox.setHflex("1");
		doubleBox.setStyle("text-align:right"); 
		row.appendChild(doubleBox);
		Res.cargarAutomatica(doubleBox, cuota_moderadora, "valor_cuota");
		
		/* copago */
		doubleBox = new Doublebox(1);
		doubleBox.setHflex("1");
		row.appendChild(doubleBox);
		doubleBox.setStyle("text-align:right");
		Res.cargarAutomatica(doubleBox, cuota_moderadora, "porcentaje_copago");
		
		/* limite */
		doubleBox = new Doublebox(1);
		doubleBox.setHflex("1");
		row.appendChild(doubleBox);
		doubleBox.setStyle("text-align:right");
		Res.cargarAutomatica(doubleBox, cuota_moderadora, "limite_evento");
		
		/*  maximo */
		doubleBox = new Doublebox(1);
		doubleBox.setHflex("1");
		row.appendChild(doubleBox);
		doubleBox.setStyle("text-align:right");
		Res.cargarAutomatica(doubleBox, cuota_moderadora, "maximo_anio");
		
		/* fin carga de datos esquema */
		return row;
	}
	
	
	private void validarAccionTipos(Listbox listboxTipo){
		boolean visible_nivel2 = listboxTipo.getSelectedItem().getValue().equals("2");
		((Component)listboxTipo.getAttribute("listNivel2")).setVisible(visible_nivel2);
	}
	
	private void cargarAutomaticaEspecialNiveles(Component component, Object bean, String campo){
		try {
			/* obtenemos el valor */
			Field field = bean.getClass().getDeclaredField(campo);
			field.setAccessible(true); 
			Object valor = field.get(bean);
			component.setAttribute("bean", bean);
			component.setAttribute("field", field);
			/* inyectamos valor en compoenete */
			if(component instanceof Listbox){
				Listbox listbox = (Listbox) component;
				listbox.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Object valor = ((Listbox)arg0.getTarget()).getSelectedItem().getValue();
						Field  field = (Field)((Listbox)arg0.getTarget()).getAttribute("field");
						Object bean = (Object)((Listbox)arg0.getTarget()).getAttribute("bean");
						if(valor != null){
							if(valor.toString().matches("[0-9]*$") && !valor.toString().isEmpty()){
								field.set(bean, Double.parseDouble(valor.toString())); 
							}
						}
					}
				});
				if(valor != null){
					//log.info("Valor a seleccionar: " + valor); 
					Utilidades.setValueFrom(listbox, ((Double)valor).intValue() + ""); 
				}else{
					String valorTemp = listbox.getSelectedItem().getValue();
					if(valorTemp.toString().matches("[0-9]*$") && !valorTemp.toString().isEmpty()){
						field.set(bean, Double.parseDouble(valorTemp.toString())); 
					}
				}
			}
		} catch (Exception e) { 
			Clients.showNotification("Ocurrio un error reportelo a administrador del sistema, en este compoenete.", component);
			e.printStackTrace();
		}
	}
	
	
	private Listbox getListBoxElemento(List<Elemento> elementos) {
		Listbox listbox = new Listbox(); 
		listbox.setZclass("combobox");
		listbox.setMold("select");  
		for (Elemento elemento : elementos) {
			 listbox.appendChild(new Listitem(elemento.getDescripcion(), elemento.getCodigo())); 
		} 
		if(listbox.getItemCount() > 0){
			listbox.setSelectedIndex(0); 
		}
		return listbox;
	}
	
	public void guardarDatos(){
		try {
			if(validarForm()){ 
				//Cargamos los componentes //
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("listado_detalle", listDetalle); 
				map.put("user", usuarios);
				getServiceLocator().getAnio_cuota_moderadoraService().guardar(map);
				Notificaciones.mostrarNotificacionInformacion("Informacion ..","Los datos se guardaron satisfactoriamente", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
			}
		}catch (ValidacionRunTimeException e) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia", e.getMessage(), IConstantes.TIEMPO_NOTIFICACIONES_GENERALES); 
		}catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private boolean validarForm() {
		dbxSalarioActivo.setStyle("text-transform:uppercase;background-color:white;text-align:right");
		dbxSalarioPensionado.setStyle("text-transform:uppercase;background-color:white;text-align:right");
		
		boolean valida = true;
		
		String msj = "Los campos marcados con (*) son obligatorios";
		
		if(dbxSalarioActivo.getText().trim().equals("") || (dbxSalarioActivo.getValue() != null && dbxSalarioActivo.getValue() <= 0)){
			dbxSalarioActivo.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if(dbxSalarioPensionado.getText().trim().equals("") || (dbxSalarioPensionado.getValue() != null && dbxSalarioPensionado.getValue() <= 0)){
			dbxSalarioPensionado.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		
		if(!valida){
			Notificaciones.mostrarNotificacionAlerta(usuarios.getNombres()+" recuerde que...",msj, IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		}
		
		return valida;
	}
}
