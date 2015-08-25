/**
 * @author Luis Miguel Hernandez Perez
 */
package com.framework.res;

import healthmanager.controller.ZKWindow.View;

import java.lang.reflect.Field;
import java.sql.Timestamp;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class CargardorDeDatos {
	public static void initComponents(Window window) {
		try {
			Field[] fields = window.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				View view = field.getAnnotation(View.class);
				if (view != null) {
					Object object = window.getFellow("" + field.getName());
					field.set(window, object);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static void initComponentsSuperClass(Window window) {
		try {
			Field[] fields = window.getClass().getSuperclass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				View view = field.getAnnotation(View.class);
				if (view != null) {
					Object object = window.getFellow("" + field.getName());
					field.set(window, object);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static void initComponents(Component component) throws Exception {
		Field[] fields = component.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			View view = field.getAnnotation(View.class);
			if (view != null) {
				// Object object = window.getFellowIfAny("" +
				// field.getName());
				Object object = view.isMacro() ? component.getFellow(
						"" + field.getName()).getFirstChild() : component
						.getFellow("" + field.getName());
				if (object == null) {
					System.out.println("Component nulo: " + field.getName());
				} else {
					field.set(component, object);
				}
			}
		}
	}

	public static void cargarDatosViewEnBean(Window window, 
			Class<?> bean,
			Object objectBean) {
		try {
			Field[] fields =  window.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				 View view = field.getAnnotation(View.class);
				 if(view != null){
					 if(view.classField() == bean && !view.field().equalsIgnoreCase("$")){
						 Object object = field.get(window);
						 if(object != null){
							 if(object instanceof Textbox){
								 Textbox textbox = (Textbox) object;
								 String value  = textbox.getValue();
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 field2.set(objectBean, value);
							 }else if(object instanceof Datebox){
								 Datebox datebox = (Datebox) object;
								 if(datebox.getValue()!=null){
									 Timestamp fecha = new Timestamp(datebox.getValue().getTime());
									 Field field2 = bean.getDeclaredField(view.field());
									 field2.setAccessible(true);
									 field2.set(objectBean, fecha);
								 }
								 
							 }else if(object instanceof Listbox){
								 Listbox listbox = (Listbox) object;
								 if(listbox.getSelectedItem().getValue()!=null){
									 String value  = listbox.getSelectedItem().getValue().toString();
									 Field field2 = bean.getDeclaredField(view.field());
									 field2.setAccessible(true);
									 field2.set(objectBean, value);
								 }
								 
							 }else if(object instanceof Radiogroup){
								 Radiogroup radio = (Radiogroup) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 
								 if(field2.getType() ==  Boolean.class || field2.getType() == boolean.class){
										 
									 try {
										if(radio.getSelectedItem().getValue()!=null){
													 String value  = radio.getSelectedItem().getValue().toString();
													 field2.set(objectBean, (value.equals("S")?true:false));
												 } else{
													 field2.set(objectBean, false);
												 }
									} catch (Exception e) {
										System.out.println("Radio Eliminado: " + radio);
										e.printStackTrace();
									}
								 }else if(field2.getType() ==  String.class){
									 if(radio.getSelectedItem() != null){
										 String value  = radio.getSelectedItem().getValue().toString();
										 field2.set(objectBean, value);
									 }else{
										 field2.set(objectBean,"");
									 }
								 }
							 }else if(object instanceof Radio){
								 Radio textbox = (Radio) object;
								 boolean value  = textbox.isSelected();
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 if(view.isStringCheck())
									 field2.set(objectBean, value ? "S" : "N"); 
								 else
								    field2.set(objectBean, value);
							 }else if(object instanceof Checkbox){
								 Checkbox textbox = (Checkbox) object;
								 boolean value  = textbox.isChecked();
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 if(view.isStringCheck())
									 field2.set(objectBean, value ? "S" : "N"); 
								 else
								    field2.set(objectBean, value);
							 }else if(object instanceof Bandbox){
								 Bandbox textbox = (Bandbox) object;
								 String value  = textbox.getValue();
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 field2.set(objectBean, value);
							 }else if(object instanceof Label){
								 Label textbox = (Label) object;
								 String value  = textbox.getValue();
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 field2.set(objectBean, value);
							 }else if(object instanceof Intbox){
								 Intbox textbox = (Intbox) object;
								 Integer value  = textbox.getValue();
								 if(value == null)
									 value = 0;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 field2.set(objectBean, value.intValue());
							 }
						 } 
					 }
				 }
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		} 
	}
 
	public static void mostrarEnVista(Window window,
			Class<?> bean,
			Object objectBean) {
		
		try {
    		Field[] fields =  window.getClass().getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				 View view = field.getAnnotation(View.class);
				 if(view != null){
					 if(view.classField() == bean && !view.field().equalsIgnoreCase("$")){
						 Object object = field.get(window); 
						 if(object != null){
							 if(object instanceof Textbox){
								 Textbox textbox = (Textbox) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 String value = "" + field2.get(objectBean);
								 textbox.setValue(value);
							 }else if(object instanceof Bandbox){
								 Bandbox textbox = (Bandbox) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 String value = "" + field2.get(objectBean);
								 textbox.setValue(value + "");

							 }else if(object instanceof Label){
								 Label textbox = (Label) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 String value = "" + field2.get(objectBean);
								 textbox.setValue(value + "");
							 }else if(object instanceof Intbox){
								 Intbox textbox = (Intbox) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 String value = "" + field2.get(objectBean);
								 textbox.setText(value + "");
							 }else if(object instanceof Datebox){
								 Datebox datebox = (Datebox) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 if(field2.get(objectBean)!=null){
									 Timestamp fecha = (Timestamp) field2.get(objectBean);
									 datebox.setValue(fecha);
								 }
							 }else if(object instanceof Radio){
								 Radio textbox = (Radio) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 
								 if(field2.get(objectBean)!=null){
									 Object value = field2.get(objectBean);
									 if(value instanceof String){
										 String valor = (String) field2.get(objectBean);
										 textbox.setSelected(valor.equals("S"));   
									 }else{
										 Boolean valor = (Boolean) field2.get(objectBean);
										 textbox.setSelected(valor); 
									 }
								 }
							 }else if(object instanceof Checkbox){
								 Checkbox textbox = (Checkbox) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 if(field2.get(objectBean)!=null){
									 Object value = field2.get(objectBean);
									 if(value instanceof String){
										 String valor = (String) field2.get(objectBean);
										 textbox.setChecked(valor.equals("S"));   
									 }else{
										 Boolean valor = (Boolean) field2.get(objectBean);
										 textbox.setChecked(valor); 
									 }
								 }
							 }else if(object instanceof Listbox){
								 Listbox listbox = (Listbox) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 if(field2.get(objectBean)!=null){
									 String value = "" + field2.get(objectBean);
									 if(listbox.getItemCount()>0){
										 for(int i=0;i<listbox.getItemCount();i++){
											 Listitem listitem = listbox.getItemAtIndex(i);
											 if(listitem.getValue().toString().equals(value)){
												 listitem.setSelected(true);
												 i = listbox.getItemCount();
											 }
										 }
									 }
									 
								 }
							 }else if(object instanceof Radiogroup){
								 Radiogroup radiogroup = (Radiogroup) object;
								 Field field2 = bean.getDeclaredField(view.field());
								 field2.setAccessible(true);
								 if(field2.getType().toString().equals("class java.lang.Boolean")){
									 if(field2.get(objectBean)!=null){
										 String value = (((Boolean)field2.get(objectBean))?"S":"N");
										 if(radiogroup.getItemCount()>0){
											 for(int i=0;i<radiogroup.getItemCount();i++){
												 Radio radio = radiogroup.getItemAtIndex(i);
												 if(radio.getValue().toString().equals(value)){
													 radiogroup.setSelectedItem(radio);
													 i = radiogroup.getItemCount();
												 }
											 }
										 }
									 }
								 }else if(field2.getType() == String.class){
									 String value = (((String)field2.get(objectBean)));
									 if(radiogroup.getItemCount()>0){
										 for(int i=0;i<radiogroup.getItemCount();i++){
											 Radio radio = radiogroup.getItemAtIndex(i);
											 if(radio.getValue().toString().equals(value)){
												 radiogroup.setSelectedItem(radio);
												 break;
											 }
										 }
									 }
								 }
								 
							 }
						 } 
					 }
				 }
			}
    	} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
	}

}
