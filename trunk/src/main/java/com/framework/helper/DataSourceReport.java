/**
 * 
 */
package com.framework.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author ferney
 * @author Luis Miguel
 */
public class DataSourceReport implements JRDataSource{
	
	private List list_data;
	private int index;

	public DataSourceReport() {
		index = -1;
		list_data = new LinkedList();
	}

	public void loadReport(List list) {
		list_data = list;
	}

	@Override
	public Object getFieldValue(JRField jRField) throws JRException {
		Map field = (Map) list_data.get(index);
		return field.get(jRField.getName());
	}

	@Override
	public boolean next() throws JRException {
		index++;
		return index < list_data.size();
	}
}
