/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.ResultSet;
import java.sql.Statement;

import static Database.DBConnection.connection;

/**
 *
 * @author carolyn.sher
 */
public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    
    public static void makeQuery(String q){
        query =q;
        try{
            stmt=connection.createStatement();
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
             if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);
            
        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
    public static ResultSet getResult(){
        return result;
    }
}
