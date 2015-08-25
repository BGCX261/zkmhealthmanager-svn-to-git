/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Cuota_moderadora;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;

/**
 * @author ferney
 *
 */
public class Cuota_moderadoraAction  extends Action implements AfterCompose{
	
	private static Logger log = Logger.getLogger(Cuota_moderadoraAction.class);
	private static final long serialVersionUID = -9145887024839938515L;
	
	private Empresa empresa;
	private Usuarios usuarios;
	
	private Window form;
	
	private List<Elemento> lista_grupos;
	
	private Listbox lbxDetalle;

	@Override
	public void afterCompose() {
		
		loadComponents();
		cargarDatosSesion();
	}
	
	public void initCuota_moderadora()throws Exception{
		initGrupos();
	}
	
	public void loadComponents(){
		form = (Window) this.getFellow("formCuota_moderadora");
		
		lbxDetalle = (Listbox) this.getFellow("lbxDetalle");
	}
	
	public void cargarDatosSesion(){
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		
		empresa = ServiceLocatorWeb.getEmpresa(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}
	
	public void listarElementoListbox(Combobox combobox,boolean select){
		combobox.getChildren().clear();
		Comboitem comboitem;
		if(select){
			comboitem = new Comboitem();
			comboitem.setValue("");
			comboitem.setLabel("-- --");
			combobox.appendChild(comboitem);
		}
		
		String tipo = combobox.getName();
		List<Elemento> elementos = this.getServiceLocator().getElementoService().listar(tipo);
		
		for (Elemento elemento : elementos) {
			comboitem = new Comboitem();
			comboitem.setValue(elemento.getCodigo());
			comboitem.setLabel(elemento.getDescripcion());
			combobox.appendChild(comboitem);
		}
		if (combobox.getItemCount() > 0) {
			combobox.setSelectedIndex(0);
		}
	}
	
	public void initGrupos()throws Exception{
		try {
			lista_grupos = this.getServiceLocator().getElementoService().listar("grupo_cuota");
			lbxDetalle.getItems().clear();
			
			int i = 0;
			
			////log.info("lista_grupos: "+lista_grupos);
			
			for (Elemento grupo : lista_grupos) {
				
				////log.info("aqui comienza "+i);
				
				Cuota_moderadora cuota_moderadora = new Cuota_moderadora();
				cuota_moderadora.setCodigo_empresa(empresa.getCodigo_empresa());
				cuota_moderadora.setGrupo(grupo.getCodigo());
				cuota_moderadora = getServiceLocator().getCuota_moderadoraService().consultar(cuota_moderadora);
				
				Listitem listitem = new Listitem();
				listitem.setValue(i+"");
				
				Listcell listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				Textbox tbxGrupo = new Textbox(grupo.getCodigo());
				tbxGrupo.setHflex("1");
				tbxGrupo.setInplace(true);
				tbxGrupo.setReadonly(true);
				tbxGrupo.setId("grupo_"+i);
				listcell.appendChild(tbxGrupo);
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				final Combobox cmbTipo_nivel = new Combobox();
				cmbTipo_nivel.setHflex("1");
				cmbTipo_nivel.setReadonly(true);
				cmbTipo_nivel.setInplace(true);
				cmbTipo_nivel.setName("tipo_nivel_salario");
				cmbTipo_nivel.setId("tipo_nivel_"+i);
				listcell.appendChild(cmbTipo_nivel);
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				Combobox cmbNivel1 = new Combobox();
				cmbNivel1.setHflex("1");
				cmbNivel1.setReadonly(true);
				cmbNivel1.setInplace(true);
				cmbNivel1.setName("nivel_salario");
				cmbNivel1.setId("nivel1_"+i);
				listcell.appendChild(cmbNivel1);
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				final Combobox cmbNivel2 = new Combobox();
				cmbNivel2.setHflex("1");
				cmbNivel2.setReadonly(true);
				cmbNivel2.setInplace(true);
				cmbNivel2.setName("nivel_salario");
				cmbNivel2.setId("nivel2_"+i);
				listcell.appendChild(cmbNivel2);
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				Doublebox tbxValor_cuota = new Doublebox((cuota_moderadora!=null?cuota_moderadora.getValor_cuota():0.0));
				tbxValor_cuota.setHflex("1");
				tbxValor_cuota.setFormat("#,##0.00");
				tbxValor_cuota.setInplace(true);
				tbxValor_cuota.setStyle("text-align:right;");
				tbxValor_cuota.setId("valor_cuota_"+i);
				listcell.appendChild(tbxValor_cuota);
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				Doublebox tbxPorc_copago = new Doublebox((cuota_moderadora!=null?cuota_moderadora.getPorcentaje_copago():0.0));
				tbxPorc_copago.setHflex("1");
				tbxPorc_copago.setFormat("#,##0.00");
				tbxPorc_copago.setInplace(true);
				tbxPorc_copago.setStyle("text-align:right;");
				tbxPorc_copago.setId("porc_copago_"+i);
				listcell.appendChild(tbxPorc_copago);
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				Doublebox tbxLimite_evento = new Doublebox((cuota_moderadora!=null?cuota_moderadora.getLimite_evento():0.0));
				tbxLimite_evento.setHflex("1");
				tbxLimite_evento.setFormat("#,##0.00");
				tbxLimite_evento.setInplace(true);
				tbxLimite_evento.setStyle("text-align:right;");
				tbxLimite_evento.setId("limite_evento_"+i);
				listcell.appendChild(tbxLimite_evento);
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.setStyle("padding:1px;margin:0;border: 1px solid #D8D8D8;");
				listcell.setHeight("30px");
				Doublebox tbxMaximo_anio = new Doublebox((cuota_moderadora!=null?cuota_moderadora.getMaximo_anio():0.0));
				tbxMaximo_anio.setHflex("1");
				tbxMaximo_anio.setFormat("#,##0.00");
				tbxMaximo_anio.setInplace(true);
				tbxMaximo_anio.setStyle("text-align:right;");
				tbxMaximo_anio.setId("maximo_anio_"+i);
				listcell.appendChild(tbxMaximo_anio);
				listitem.appendChild(listcell);
				
				lbxDetalle.appendChild(listitem);
				
				i++;
				
				int index = 0;
				listarElementoListbox(cmbTipo_nivel, false);
				for(int j=0;j<cmbTipo_nivel.getItemCount();j++){
					Comboitem comboitem = cmbTipo_nivel.getItemAtIndex(j);
					if(cuota_moderadora!=null){
						if(comboitem.getValue().toString().equals(cuota_moderadora.getTipo_nivel())){
							index = j;
							j = cmbTipo_nivel.getItemCount();
						}
					}
					
				}
				if(index>=0){
					cmbTipo_nivel.setSelectedIndex(index);
				}
				
				index = 0;
				listarElementoListbox(cmbNivel1, false);
				for(int j=0;j<cmbNivel1.getItemCount();j++){
					Comboitem comboitem = cmbNivel1.getItemAtIndex(j);
					if(cuota_moderadora!=null){
						if(comboitem.getValue().toString().equals(((int)cuota_moderadora.getNivel1())+"")){
							index = j;
							j = cmbNivel1.getItemCount();
						}
					}
					
				}
				if(index>=0){
					cmbNivel1.setSelectedIndex(index);
				}
				
				index = 0;
				listarElementoListbox(cmbNivel2, false);
				for(int j=0;j<cmbNivel2.getItemCount();j++){
					Comboitem comboitem = cmbNivel2.getItemAtIndex(j);
					if(cuota_moderadora!=null){
						if(comboitem.getValue().toString().equals(((int)cuota_moderadora.getNivel2())+"")){
							index = j;
							j = cmbNivel2.getItemCount();
						}
					}
					
				}
				if(index>=0){
					cmbNivel2.setSelectedIndex(index);
				}
				
				cmbTipo_nivel.addEventListener("onSelect", new EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						
						if(cmbTipo_nivel.getSelectedItem().getValue().toString().equals("2")){
							cmbNivel2.setVisible(true);
						}else{
							cmbNivel2.setVisible(false);
						}
					}
				});
				
				if(cmbTipo_nivel.getSelectedItem().getValue().toString().equals("2")){
					cmbNivel2.setVisible(true);
				}else{
					cmbNivel2.setVisible(false);
				}
				
				////log.info("aqui termina "+i);
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	public void guardar(String accion)throws Exception{
		try {
			
			if(lbxDetalle.getItemCount()<=0){
				Messagebox.show("No hay tabla que guardar",
						"Error !!",Messagebox.OK,Messagebox.ERROR);
				return;
			}
			
			
			
			final List<Cuota_moderadora> lista_cuota = new LinkedList<Cuota_moderadora>();
			for (int i = 0; i < lbxDetalle.getItemCount(); i++) {
				
				Textbox tbxGrupo = (Textbox)form.getFellow("grupo_"+i);
				Combobox cmbTipo_nivel = (Combobox)form.getFellow("tipo_nivel_"+i);
				Combobox cmbNivel1 = (Combobox)form.getFellow("nivel1_"+i);
				Combobox cmbNivel2 = (Combobox)form.getFellow("nivel2_"+i);
				Doublebox tbxValor_cuota = (Doublebox)form.getFellow("valor_cuota_"+i);
				Doublebox tbxPorc_copago = (Doublebox)form.getFellow("porc_copago_"+i);
				Doublebox tbxLimite_evento = (Doublebox)form.getFellow("limite_evento_"+i);
				Doublebox tbxMaximo_anio = (Doublebox)form.getFellow("maximo_anio_"+i);
				
				Cuota_moderadora cuota_moderadora = new Cuota_moderadora();
				cuota_moderadora.setCodigo_empresa(empresa.getCodigo_empresa());
				cuota_moderadora.setGrupo(tbxGrupo.getValue());
				cuota_moderadora.setTipo_nivel(cmbTipo_nivel.getSelectedItem().getValue()+"");
				cuota_moderadora.setNivel1(Double.parseDouble(cmbNivel1.getSelectedItem().getValue()+""));
				cuota_moderadora.setNivel2(Double.parseDouble(cmbNivel2.getSelectedItem().getValue()+""));
				cuota_moderadora.setValor_cuota((tbxValor_cuota.getValue()!=null?tbxValor_cuota.getValue():0.0));
				cuota_moderadora.setPorcentaje_copago((tbxPorc_copago.getValue()!=null?tbxPorc_copago.getValue():0.0));
				cuota_moderadora.setLimite_evento((tbxLimite_evento.getValue()!=null?tbxLimite_evento.getValue():0.0));
				cuota_moderadora.setMaximo_anio((tbxMaximo_anio.getValue()!=null?tbxMaximo_anio.getValue():0.0));
				cuota_moderadora.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				cuota_moderadora.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				cuota_moderadora.setCreacion_user(usuarios.getCodigo());
				cuota_moderadora.setUltimo_user(usuarios.getCodigo());

				lista_cuota.add(cuota_moderadora);
				
			}
			
			if(accion.equals("registrar")){
				getServiceLocator().getCuota_moderadoraService().guardar(lista_cuota);
				initCuota_moderadora();
				Messagebox.show("Los datos se guardaron satisfactoriamente", 
						"Informacion .." ,
						Messagebox.OK, Messagebox.INFORMATION);
			}else if(accion.equals("eliminar")){
				
				Messagebox.show("Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									getServiceLocator().getCuota_moderadoraService().eliminar(lista_cuota);
									initCuota_moderadora();
									Messagebox.show("Informacion se eliminó satisfactoriamente !!",
											"Information", Messagebox.OK, Messagebox.INFORMATION);
								}
							}
						});
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!", 
				Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	

	@Override
	void adicionarPcd(Map pcd) throws Exception {
		
		
	}

}
