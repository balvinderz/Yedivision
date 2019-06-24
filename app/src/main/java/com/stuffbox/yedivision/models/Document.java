package com.stuffbox.yedivision.models;

public class Document {
   private String name,link,subjectname;
    public Document()
    {}

    public Document(String name, String link, String subjectname) {
        this.name = name;
        this.link = link;
        this.subjectname = subjectname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }
}
