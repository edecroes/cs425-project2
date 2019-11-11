/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.jsu.mcis.cs425.project2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author JSU
 */
public class Database {
    
    public HashMap getUserInfo(String username){
        
        HashMap<String, String> results = null;
        
        
        try {
        
            Connection conn = getConnection();
            
            String query = "SELECT * FROM 'user' WHERE username = ?";
            
            PreparedStatement pstatement = conn.prepareStatement(query);
            pstatement.setString(1, username);
            
            boolean hasresults = pstatement.execute();            
                
            if ( hasresults ) {
                ResultSet resultset = pstatement.getResultSet();
                
                if(resultset.next()){
                    //Initalize HashMap; add user data from resultset
                    //use descriptive key name "id" for the ID, and "displayname" for the displayname
                }
            }

        }
          catch (Exception e) { e.printStackTrace(); }
        
        return results;
        
        
    }
    
    private Connection getConnection(){
        
       Connection conn = null;
       
        try {
            
            Context envContext = new InitialContext();
            Context initContext  = (Context)envContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }        
        catch (Exception e) {e.printStackTrace();}
        
        return conn;
        
    }
}
