package edu.jsu.mcis.cs425.project2;

import java.util.HashMap;

public class BeanApplicant {
    
    private String username;
    private String displayname;
    private int id;
    private int userid;
    private String[] skills;
    private String[] jobs;
    
    Database db = new Database();
    
    public String getSkillsList(){
        Database db = new Database();
        return (db.getSkillsListAsHTML(userid));
        
    //research left joins, query that generates html
    //string builder
    
    }
    
    public void setUserInfo(){
        Database db = new Database();
        HashMap<String, String> userinfo = db.getUserInfo(username);
        id = Integer.parseInt(userinfo.get("id"));
        displayname = userinfo.get("displayname");
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getJobsList() {
        
        return ( db.getJobsListAsHTML(userid) );
    }
    public String[] getSkills(){
        return skills;
    }
    
    public void setSkillsList(String[] skills ) {
        db.setSkillsList(userid, skills);
        //"setSkillsList()" method checks first to see whether a new list of skills was received; 
        //if it was not, that means that the jobs page was opened directly instead of from the skills page,
        //and the jobs page will work with the list of skills already in the database.  
    }
}