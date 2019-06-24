package com.stuffbox.yedivision.models;

public class lecture {
    public int id;
    public String room;
    public  String subject;
    public String teacher;
    public String time;

   public  lecture()
    {}

    public lecture(int id, String room, String subject, String teacher, String time) {
        this.id = id;
        this.room = room;
        this.subject = subject;
        this.teacher = teacher;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
