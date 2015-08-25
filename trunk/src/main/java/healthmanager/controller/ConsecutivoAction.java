/*
 * consecutivoAction.java
 * 
 * Generado Automaticamente .
 * SoftComputo LTDA.
 */
package healthmanager.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import healthmanager.modelo.bean.*;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class ConsecutivoAction extends ZKWindow {

	private static final long serialVersionUID = -6430917161805030353L;

	// private static Logger log = Logger.getLogger(ConsecutivoAction.class);

	@View
	private Borderlayout groupboxEditar;

	@View
	private Listbox lbxConsecutivo;

	// private final String[] idsExcluyentes = {};

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {
		listarConsecutivos(lbxConsecutivo);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (lbxConsecutivo.getItemCount() == 0) {
			valida = false;
			mensaje = "No hay datos para actualizar";
		}

		if (valida) {
			for (int i = 0; i < lbxConsecutivo.getItemCount(); i++) {
				Listitem listitem = lbxConsecutivo.getItemAtIndex(i);
				List<Component> lista_cell = listitem.getChildren();

				Listcell listcell = (Listcell) lista_cell.get(1);
				Intbox texbox = ((Intbox) listcell.getFirstChild());
				if (texbox.getValue() == null) {
					valida = false;
					mensaje = "El valor del consecutivo no puede ir en blanco";
					i = lbxConsecutivo.getItemCount() - 1;
				} else if (texbox.getValue() <= 0) {
					valida = false;
					mensaje = "El valor del consecutivo no ser menor ni igual que cero (0)";
					i = lbxConsecutivo.getItemCount() - 1;
				}
			}
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...", mensaje);
		}

		return valida;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				List<Consecutivo> lista_consecutivos = new LinkedList<Consecutivo>();
				for (int i = 0; i < lbxConsecutivo.getItemCount(); i++) {
					Listitem listitem = lbxConsecutivo.getItemAtIndex(i);
					if (listitem.isSelected()) {
						List<Component> lista_cell = listitem.getChildren();

						Listcell listcell = (Listcell) lista_cell.get(1);
						String cod_cons = ((Intbox) listcell.getFirstChild())
								.getValue().toString();

						listcell = (Listcell) lista_cell.get(2);
						String tipo = ((Label) listcell.getFirstChild())
								.getValue();

						Consecutivo consecutivo = new Consecutivo();
						consecutivo.setCodigo_empresa(empresa
								.getCodigo_empresa());
						consecutivo.setCodigo_sucursal(sucursal
								.getCodigo_sucursal());
						consecutivo.setCreacion_date(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						consecutivo.setUltimo_update(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						consecutivo.setConsecutivo(cod_cons);
						consecutivo.setTipo(tipo);
						lista_consecutivos.add(consecutivo);
					}
				}

				getServiceLocator().getConsecutivoService().guardarDatos(
						lista_consecutivos);

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");

				listarConsecutivos(lbxConsecutivo);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	public void listarConsecutivos(Listbox listbox) {
		listbox.getItems().clear();
		Listitem listitem;

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo_empresa", empresa.getCodigo_empresa());
		param.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		List<Consecutivo> lista_consecutivo = getServiceLocator()
				.getConsecutivoService().listar(param);

		for (Consecutivo consecutivo : lista_consecutivo) {
			listitem = new Listitem();
			listitem.setValue(consecutivo);
			listitem.appendChild(new Listcell());

			Listcell listcell = new Listcell();
			Intbox textbox = new Intbox(Integer.parseInt(consecutivo
					.getConsecutivo()));
			textbox.setHflex("1");
			textbox.setStyle("text-transform:uppercase");
			textbox.setInplace(true);
			listcell.appendChild(textbox);
			listitem.appendChild(listcell);

			listcell = new Listcell();
			listcell.appendChild(new Label(consecutivo.getTipo()));
			listitem.appendChild(listcell);

			listcell = new Listcell(
					new SimpleDateFormat("yyyy-MM-dd HH:mm").format(consecutivo
							.getUltimo_update()));
			listitem.appendChild(listcell);

			listbox.appendChild(listitem);
		}
	}
}