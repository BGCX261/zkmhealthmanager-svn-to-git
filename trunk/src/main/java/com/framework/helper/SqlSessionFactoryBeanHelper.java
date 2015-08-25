package com.framework.helper;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;

/**
 * Esta clase es un ayudador el cual te permite agregar mas de un mapper location
 * @author Luis Miguel Hernández
 * */
public class SqlSessionFactoryBeanHelper extends SqlSessionFactoryBean {
	
//	private static Logger log = Logger.getLogger(SqlSessionFactoryBeanHelper.class);
	
	private List<Resource[]> resources_list = null;
	private int length = 0;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		agregarMapperLocations(); 
		super.afterPropertiesSet();
	}

	private void agregarMapperLocations() {
		if(resources_list != null){
			//log.info("@agregarMapperLocations Concatenando recursos length: " + length);  
			Resource[] resourcess = new Resource[length];
			int i = 0;
			for (Resource[] resources : resources_list) {
				 for (Resource resource : resources) {
					resourcess[i++] = resource;
				}
			}
			setMapperLocations(resourcess); 
			resources_list = null;
			length = 0;
			System.gc();
			//log.info("Mapper locations inyectados... ");  
		}
	}
	
	private void addMapperLocations(Resource[] resources){
		 //log.info("@addMapperLocations Adicionando recursos length: " + resources.length);
		 if(this.resources_list == null)
			 this.resources_list = new ArrayList<Resource[]>();
		resources_list.add(resources);	 
		length += resources.length;
	}
	
	
	/* agregamos los mapper a los cuales van ha ir en el aplication context */
	public void setMapperLocation(Resource[] resources){
		addMapperLocations(resources);
	}
	
	public void setMapperLocation2(Resource[] resources){
		addMapperLocations(resources);
	}
	
	public void setMapperLocation3(Resource[] resources){
		addMapperLocations(resources);
	}
}
