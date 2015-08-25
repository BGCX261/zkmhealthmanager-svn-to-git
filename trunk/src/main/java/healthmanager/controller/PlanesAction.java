/*
 * planesAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */ 
package healthmanager.controller;



public class PlanesAction{

	
	
	public enum TypeProce {SOAT, ISS01, ISS04};
	
	public class TarifasAll {
		private Object pro;
		private TypeProce typeProce;
		private String codigo_pcd;

		public Object getPro() {
			return pro;
		}

		public void setPro(Object pro) {
			this.pro = pro;
		}

		public TypeProce getTypeProce() {
			return typeProce;
		}

		public void setTypeProce(TypeProce typeProce) {
			this.typeProce = typeProce;
		}

		public String getCodigo_pcd() {
			return codigo_pcd;
		}

		public void setCodigo_pcd(String codigo_pcd) {
			this.codigo_pcd = codigo_pcd;
		}
	}
}
