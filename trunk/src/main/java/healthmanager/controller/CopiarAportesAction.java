package healthmanager.controller;

import healthmanager.modelo.bean.Aportantes_ma;
import healthmanager.modelo.service.Aportantes_maService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class CopiarAportesAction extends ZKWindow {
	
	@View private Listbox lbxAnio;
	@View private Listbox lbxMes;
	@View private Listbox lbxAnioCopiar;
	@View private Listbox lbxMesCopiar;
	
	@View private Toolbarbutton btnLimpiarAportante;
	@View(isMacro = true) private BandboxRegistrosMacro tbxCodigo_aportante;
	@View private Textbox tbxInfoAportante;
	
	private Aportantes_maService aportantes_maService;

	@Override
	public void init() throws Exception {
       parametrizarBandbox();
       inicializarListBox(); 
	}
	
	private void inicializarListBox() {
		Utilidades.listarAnios(lbxAnio, 10);
		Utilidades.listarAnios(lbxAnioCopiar, 10);
		Utilidades.listarMeses(lbxMes); 
		Utilidades.listarMeses(lbxMesCopiar);  
	}

	private void parametrizarBandbox() {
		tbxCodigo_aportante.inicializar(tbxInfoAportante, btnLimpiarAportante); 
		tbxCodigo_aportante.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Aportantes_ma>() {

			@Override
			public void renderListitem(Listitem listitem, Aportantes_ma registro) {
				listitem.appendChild(new Listcell("" + registro.getCodigo()));
				listitem.appendChild(new Listcell("" + registro.getNro_identificacon()));
				listitem.appendChild(new Listcell("" + registro.getNombre()));
			}

			@Override
			public List<Aportantes_ma> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("codigo_empresa",
						getEmpresa().getCodigo_empresa());
				parametros.put("codigo_sucursal",
						getSucursal().getCodigo_sucursal());
				parametros.put("paramTodo", "paramTodo");
				parametros.put("value", valorBusqueda.toUpperCase());
				return aportantes_maService.listar(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Aportantes_ma registro) {
				bandbox.setValue("" + registro.getNro_identificacon()); 
				textboxInformacion.setValue("" + registro.getNombre()); 
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				
			} 
		});
	}

	public void copiarAportes(){
		try {
			if(validarFormulario()){ 
				Aportantes_ma aportantes_ma = tbxCodigo_aportante.getRegistroSeleccionado();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put(Aportantes_maService.PARAM_ANIO_COPIA, lbxAnioCopiar.getSelectedItem().getValue().toString()); 
				param.put(Aportantes_maService.PARAM_MES_COPIA, getMes(lbxMesCopiar));  
				param.put(Aportantes_maService.PARAM_ANIO, lbxAnio.getSelectedItem().getValue().toString()); 
				param.put(Aportantes_maService.PARAM_MES, getMes(lbxMes));  
				param.put(Aportantes_maService.PARAM_APORTANTE, aportantes_ma.getCodigo()); 
				param.put(Aportantes_maService.PARAM_EMPRESA, getSucursal().getCodigo_empresa());
				param.put(Aportantes_maService.PARAM_SUCURSAL, getSucursal().getCodigo_sucursal());
				int total_aportes =  aportantes_maService.guardarAportesOtroPeriodo(param);
				MensajesUtil.mensajeInformacion("Informacion",
						"La copia de los aportes se ha realizado satisfactoriamente!\n total aportes: "
								+ total_aportes);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this); 
		}
	}

	
	/**
	 * Este metodo me devuelve el mes conrespecto a como esta registrado en la base de datos
	 * @author Luis Miguel
	 * */
	private String getMes(Listbox lbx) {
		int mes = ((Integer) lbx.getSelectedItem().getValue()).intValue() + 1;
		return (mes <= 9 ? ("0" + mes) : (mes + "")); 
	}

	private boolean validarFormulario() {
		boolean validar = true;
		try {
			FormularioUtil.validarCamposObligatorios(tbxCodigo_aportante,
					lbxAnio, lbxAnioCopiar, lbxMes, lbxMesCopiar); 
		} catch (Exception e) {
			validar = false;
		}
		return validar;
	}

}
