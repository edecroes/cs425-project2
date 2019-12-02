package edu.jsu.mcis.cs425.project2;

import java.util.HashMap;

public class BeanApplicant {
    
    private String username;
    private String displayname;
    private int userid;
    private String[] skills;
    private String[] jobs;
    
    
    
    public String getSkillsList(){
        Database db = new Database();
        return (db.getSkillsListAsHTML(userid));
    }
    
    public void setUserInfo(){
        Database db = new Database();
        HashMap<String, String> userinfo = db.getUserInfo(username);
        userid = Integer.parseInt(userinfo.get("userid"));
        displayname = userinfo.get("displayname");
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getJobsList() {
        Database db = new Database();
        return ( db.getJobsListAsHTML(userid) );
    }
    
    public String[] getSkills(){
        return skills;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
    
    public void setSkillsList(){
        Database db = new Database();
        db.setSkillsList(userid, skills);
     }
    
    public void setJobsList() {
        Database db = new Database();
        db.setJobsList(userid, jobs);
     }

    public String[] getJobs() {
        return jobs;
    }

    public void setJobs(String[] jobs) {
        this.jobs = jobs;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }
    
    
    
}