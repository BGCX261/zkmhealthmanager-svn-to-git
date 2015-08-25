package healthmanager.controller;

import healthmanager.modelo.bean.Anexo9_entidad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;

public class AgregarPrestadorRemisiones extends ZKWindow {
	
	/* Componentes */
	@View(isMacro = true) private BandboxRegistrosMacro tbxPrestador;
	@View private Textbox tbxNomPrestador;
	@View private Toolbarbutton btnLimpiarPrestador;
	
	/* boton de guardado */
	@View private Button btnButton;
	
	/* Anexo 9*/
	private Anexo9_entidad anexo9_entidad;

	@Override
	public void init() throws Exception {
       parametrizarBandbox();
	}
	
	@Override
	public void params(Map<String, Object> map) {
		anexo9_entidad = (Anexo9_entidad) map.get("anexo9"); 
		//log.info("Anexo 9: " + anexo9_entidad); 
	}
	
	private void parametrizarBandbox() {
		tbxPrestador.inicializar(tbxNomPrestador, btnLimpiarPrestador);
		inicializarBandboxPrestadorServicio();
	}
	
	private void inicializarBandboxPrestadorServicio() {
		BandboxRegistrosIMG<Map> bandboxRegistrosIMG = new BandboxRegistrosIMG<Map>() {
			
			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Map registro) {
				bandbox.setValue((String)registro.get("codigo"));
				textboxInformacion.setValue((String)registro.get("nombre"));
				btnButton.setDisabled(false);
				return true;
			}
			
			@Override
			public void renderListitem(Listitem listitem, Map registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.get("codigo") + ""));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(registro.get("nit") + ""));
				listitem.appendChild(listcell);
				
				listcell = new Listcell();
				listcell.appendChild(new Label(registro.get("nombre") + ""));
				listitem.appendChild(listcell);
			}
			
			@Override
			public List<Map> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				Map<String, Object> parameters = new HashMap();
				parameters.put("paramTodo", "");
				parameters.put("value", "%" + valorBusqueda + "%");
				parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
				parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				
				/* parametros de busqueda de procedimiento */
				parameters.put("inner_process", "_sowUp");
				parameters.put("codigo_cups", anexo9_entidad.getCodigo_servicio_solicita());  
				parameters.put("tipo", anexo9_entidad.getTipo_solicitud1());
				/* agregacion de procedimiento */

				getServiceLocator().getAdministradoraService().setLimit(
						"limit 25 offset 0");

				List<Map> list = getServiceLocator()
						.getAdministradoraService().listarDesdeContratos(parameters);
				return list;
			}
			
			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				btnButton.setDisabled(true);		
			}
		};
		/* inyectamos el mismo evento */
		tbxPrestador.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}
	
	
	public void agregarPrestador(){
		try {
			Messagebox.show("Estas seguro que deseas remitir ha ese prestador.", "Informacion", Messagebox.YES + Messagebox.NO,
					Messagebox.EXCLAMATION, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							if("onYes".equalsIgnoreCase(event.getName())){
								String codigo_prestador = tbxPrestador.getValue();
								anexo9_entidad.setCodigo_prestador(codigo_prestador);
								getServiceLocator().getAnexo9EntidadService().actualizar(anexo9_entidad);
							}detach();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			MensajesUtil.mensajeError(e, "", this);
		}
	}
}
