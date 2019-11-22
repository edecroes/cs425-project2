package edu.jsu.mcis.cs425.project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author edecroes
 */
public class Database {
    
    public HashMap getUserInfo(String username){
        
        HashMap<String, String> results = null;
               
        
        try {
        
            Connection conn = getConnection();
            
            String query = "SELECT * FROM user WHERE username = ?";
            
            PreparedStatement pstatement = conn.prepareStatement(query);
            pstatement.setString(1, username);
            
            boolean hasresults = pstatement.execute();            
                
            if ( hasresults ) {
                ResultSet resultset = pstatement.getResultSet();
                
                if(resultset.next()){
                    //Initalize HashMap; add user data from resultset
                    //use descriptive key name "id" for the ID, and "displayname" for the displayname
                    
                    results = new HashMap<>();
                    String id = String.valueOf((resultset.getInt("id")));
                    String displayname = resultset.getString("displayname");
                    results.put("userid", id);
                    results.put("displayname", displayname);
                   // results += getResultSetTable(resultset);
                    
                    
                }
            }

        }
          catch (Exception e) { e.printStackTrace(); }
        
        return results;
        
        
    }
    
    public String getSkillsListAsHTML(int userid){
        StringBuilder s = new StringBuilder();
        
        //get result set by running the query and iterate through the results
        //throw an exception if there is an error in the database
            String query = "SELECT skills.*, a.userid FROM skills LEFT JOIN "
                    + "(SELECT * FROM applicants_to_skills WHERE userid = ?) AS a "
                    + "ON skills.id = a.skillsid ORDER BY skills.description";
            
            try{
                
                Connection conn = getConnection();
                PreparedStatement pstatement = conn.prepareStatement(query);
                pstatement.setInt(1, userid);

                boolean hasresults = pstatement.execute(); 

                if(hasresults){
                    ResultSet resultset = pstatement.getResultSet();

                    while(resultset.next()){
                        String description = resultset.getString("description");
                        int id = resultset.getInt("id");
                        int user = resultset.getInt("userid");

                        s.append("<input type=\"checkbox\" name=\"skills\" value=\"");
                        s.append(id);
                        s.append("\" id=\"skills_").append(id).append( "\" ");

                        // is this box checked?
                        if(user != 0){
                            s.append("checked");
                        }

                        s.append(">\n");

                        s.append("<label for=\"skills_").append(id).append("\">");
                        s.append(description);
                        s.append("</label><br />\n\n");

                        //return as a json string
                        //SELECT skills.*, a.userid FROM skills LEFT JOIN (SELECT * FROM applicants_to_skills WHERE userid = 1) AS a ON skills.id = a.skillsid;
                    }
                }
            }     
            catch(Exception e){ e.printStackTrace(); }
        
        return s.toString();
    }
    
    //SELECT a.userid, j.name, s.skillsid FROM ((applicants_to_skills a JOIN skills_to_jobs s ON a.skillsid = s.skillsid)
    //JOIN jobs j on s.jobsid = j.id);


    
    public String getJobsListAsHTML(int userid) {
        StringBuilder s = new StringBuilder();
        
        //get result set by running the query and iterate through the results
        //throw an exception if there is an error in the database
        
        //String query = "SELECT * FROM 'user' WHERE username = ?";
        String query = "SELECT jobs.id, jobs.name, a.userid FROM jobs LEFT JOIN(SELECT * FROM applicants_to_jobs WHERE userid = ?) AS a ON jobs.id = a.jobsid WHERE jobs.id IN(SELECT jobsid AS id FROM(applicants_to_skills JOIN skills_to_jobs ON applicants_to_skills.skillsid = skills_to_jobs.skillsid) WHERE applicants_to_skills.userid = ?) ORDER BY jobs.name";
            try {
                Connection conn = getConnection();
                PreparedStatement pstatement = conn.prepareStatement(query);
                pstatement.setInt(1, userid);

                boolean hasresults = pstatement.execute(); 

                if(hasresults){
                    ResultSet resultset = pstatement.getResultSet();

                    while(resultset.next()){
                        String description = resultset.getString("description");
                        int id = resultset.getInt("id");
                        int user = resultset.getInt("userid");

                        s.append("<input type=\"checkbox\" name=\"skills\" value=\"");
                        s.append(id);
                        s.append("\" id=\"skills_").append(id).append( "\" ");

                        // is this box checked?
                        if(user != 0){
                            s.append("checked");
                        }

                        s.append("><br />");

                        s.append("<label for=\"skills_").append(id).append("\">");
                        s.append(description);
                        s.append("</label><br /><br />");

                        //return as a json string
                         
                    }
                }
                
            }
            catch(Exception e){ e.printStackTrace(); }
        
        
        
        return s.toString();
    }
    /*
    public setSkillsList(int userid, String[] skills){
        //skills = null;
        try{
            Connection conn = getConnection();
            //conn = DriverManager.getConnection(server, user, password);
            String query = "UPDATE * FROM 'user' WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            //add new selected skills r
        }
        catch(Exception e){ e.printStackTrace(); }
    }

    public setJobsList(int userid, String[] skills){
        skills = null;
    }
*/
    
    public Connection getConnection(){
        
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
