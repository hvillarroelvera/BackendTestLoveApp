package com.hector.apptestlove.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

public class GcmDAO extends Conexion{

public int registrarGcm(GcmDTO gcm,String nom_user){
		
		int res=0;
		Connection con=Conexion.GetConnection();
		
		Statement stmt;
		
		try {
			
			 
             String sql="insert into gcm(codGcm,nom_user,expirationtime,appversion)values(?,?,?,?)";
             PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);
             pstm.setString(1, gcm.getGcm_codGcm());
             pstm.setString(2, nom_user);
             pstm.setLong(3, gcm.getExpirationTime());
             pstm.setInt(4, gcm.getAppVersion());
            
             res=pstm.executeUpdate();
             
		        
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
		
		return res;
	}


public GcmDTO getGcmXUsuario(UsuarioDTO usuario){
	
	Connection con=Conexion.GetConnection();
	int respuesta = 0;
	Statement stmt;
	GcmDTO gcm=new GcmDTO();
	
	try {
		
		 ResultSet rs=null;
       
		 String sql="SELECT codGcm,expirationtime,appversion FROM gcm WHERE nom_user=?";
			

		 
		 PreparedStatement pstm = con.prepareStatement(sql);
        
        pstm.setString(1, usuario.getNom_user());
       


        System.out.println("sql="+pstm.toString());
         rs=pstm.executeQuery();
        
	        if( rs.next() ) {
	        	
               gcm.setGcm_codGcm(rs.getString("codGcm"));
               gcm.setExpirationTime(rs.getLong("expirationtime"));
               gcm.setAppVersion(rs.getInt("appversion"));
	           return gcm;
	        	
	        }else{
	            

	        	return gcm;
	        }
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
	
		e.printStackTrace();
	}
  
   System.out.println("*******************************saliendo de getGcmXUsuario************************************");

	return gcm;
	
}

public int actualizarGcm(GcmDTO gcm,String nom_user){
	
	Connection con=Conexion.GetConnection();
	int respuesta=0;
	Statement stmt;
	
	String sql="UPDATE gcm SET codGcm=? , expirationtime=? , appversion? WHERE nom_user=?";
    PreparedStatement pstm;
    
	try {
		pstm = con.prepareStatement(sql);
		pstm.setString(1, gcm.getGcm_codGcm());
		pstm.setLong(2, gcm.getExpirationTime());
		pstm.setInt(3, gcm.getAppVersion());
		pstm.setString(4, nom_user);
		
		respuesta=pstm.executeUpdate();
		
		System.out.println("actualizarGcm sql="+pstm.toString());
		
		if(respuesta>0){
			return Constantes.SUCCESS;
		}else{
			return Constantes.NOT_SUCCESS;
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   
    
    return Constantes.NOT_SUCCESS;
	
	
}


	
}
