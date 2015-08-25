package com.framework.macros.odontograma;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;

public class IndiceDental extends Groupbox implements AfterCompose{
	
	Combobox cbxConvenciones;
	Label numberLabel;
    
    public void reset(){
    	cbxConvenciones.setValue(""); 
    }

	public String getCbxConvenciones() {
		return cbxConvenciones.getValue();
	}

	public void setConvenciones(String convenciones) {
		cbxConvenciones.setValue(convenciones);
	}
	
	public void _setValueFrom(Combobox listbox, String valor) {
		for (int i = 0; i < listbox.getItemCount(); i++) {
			Comboitem listitem = listbox.getItemAtIndex(i);
			if (listitem.getValue().toString().equals(valor)) {
				listbox.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public String getNumber() {
		return numberLabel.getValue();
	}

	public void setDisabled(boolean estado) { 
		cbxConvenciones.setDisabled(estado);
	}

	@Override
	public void afterCompose() {
		cbxConvenciones = (Combobox) getFellow("cbxConvenciones");
		numberLabel = (Label)getFellow("numberLabel");
	}
}
