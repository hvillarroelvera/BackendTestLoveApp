package com.hector.apptestlove.persistencetest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	 public static Connection GetConnection()
	    {
	        Connection conexion=null;
	        String dnname="testlovebackend";
	        String dbhost = "127.2.225.130";
	        String dbport = "3306";
	       
	        try
	        {
	            Class.forName("com.mysql.jdbc.Driver");
	            String servidor = "jdbc:mysql://"+dbhost+":"+dbport+"/"+dnname;
	            String usuarioDB="adminRjlp3Eb";
	            String passwordDB="8yYWnfAkg89D";
	            conexion= DriverManager.getConnection(servidor,usuarioDB,passwordDB);
	        }
	        catch(ClassNotFoundException ex)
	        {
	            System.out.println("ha ocurrido un error: "+ex.getMessage());
	            conexion=null;
	        }
	        catch(SQLException ex)
	        {
	        	 System.out.println("ha ocurrido un error: "+ex.getMessage());
	            conexion=null;
	        }
	        catch(Exception ex)
	        {
	        	 System.out.println("ha ocurrido un error: "+ex.getMessage() );	        
	        	 conexion=null;
	        }
	        finally
	        {
	            return conexion;
	        }
	    }
	}
	
	

