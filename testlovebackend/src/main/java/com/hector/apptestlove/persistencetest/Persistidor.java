package com.hector.apptestlove.persistencetest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Persistidor {

	private Class clase;
	private Field[] fields;
	private Method[] methods;
	private ArrayList<ClaseTempData> fieldsStringAndType = new ArrayList<ClaseTempData>();
	
	public static final short ALL_VALUES_ENTITY = 1;
	public static final short NOT_NULL_VALUES_ENTITY = 2;
	
	
	
	public Class getClase() {
		return clase;
	}

	public void setClase(Class clase) {
		this.clase = clase;
	}

	public Method[] getMethods() {
		return methods;
	}

	public void setMethods(Method[] methods) {
		this.methods = methods;
	}

	public void insert(Object entity,short optionInsert){
		
		this.clase = entity.getClass();
		this.methods = this.clase.getDeclaredMethods();
		this.fields = this.clase.getFields();
		ClaseTempData claseTempData;
		
		for(Field field : fields){
			claseTempData = new ClaseTempData();
			claseTempData.setField(field.getName());
			claseTempData.setType(field.getType());
			fieldsStringAndType.add(claseTempData);
		}
		
		for(Method method : methods){
			String nameMethod;
			nameMethod = method.getName();
			if(nameMethod.startsWith("get")){
				
				obtenerValoresFromFields(fieldsStringAndType,method,nameMethod);
			}
			
			
		}
	}
	
	private void obtenerValoresFromFields(
			ArrayList<ClaseTempData> fieldsStringAndType2, Method method,String nameMethod) {
		
		ClaseTempData claseTempData;
		String nameMethodNoUpper,nameFieldNoUpper; 
		String nameMethodNoGetSubString;
		for(int i=0;i<fieldsStringAndType2.size();i++){
			claseTempData = fieldsStringAndType2.get(i);
			
			nameFieldNoUpper = claseTempData.getField().toLowerCase();
			nameMethodNoUpper = nameMethod.toLowerCase();
			nameMethodNoGetSubString = nameMethodNoUpper.substring(2);
			
			if(nameMethodNoGetSubString.equals(nameFieldNoUpper)){
				
			}
			
			
			
		}
		
		
	}

	public void select(){
		
	}
	public void update(){
		
	}
	
	public void delete(){
		
	}
	/*Terminar constructor de query*/
	/*private String buildStringInsert(String table,String[] columns){
		
		String sql="insert into "+table+" (nom_user,contacto)values(?,?)";

	}*/
	
}
