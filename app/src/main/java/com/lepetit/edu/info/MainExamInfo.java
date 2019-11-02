package com.lepetit.edu.info;

public class MainExamInfo {
    private final String examTime;
    private final String course;
    private final String dayFromExam;

    public MainExamInfo(String examTime, String course, String dayFromExam) {
        this.examTime = examTime;
        this.course = course;
        this.dayFromExam = dayFromExam;
    }

    public String getExamTime() {
        return examTime;
    }

    public String getCourse() {
        return course;
    }

    public String getDayFromExam() {
        return dayFromExam;
    }
}
