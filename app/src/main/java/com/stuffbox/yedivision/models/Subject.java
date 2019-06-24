package com.stuffbox.yedivision.models;

import java.io.Serializable;

public class Subject implements Serializable  {
  private   String subjectname;
  private  int sem;
    Subject(){

    }
    Subject(String subjectname,int sem)
    {
        this.subjectname =subjectname;
        this.sem=sem;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public int getSem() {
        return sem;
    }

    public void setSem(int sem) {
        this.sem = sem;
    }
}
