package System;

import java.io.Serializable;

public class Course implements Serializable {

    public String courseId = null;  //课程编号
    public String courseName = null;  //课程名
    public String courseMark = null;  //课程分数
    public String courseTea =null;   //任课老师

    public Course(String courseId, String courseName, String courseMark, String courseTea) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseMark = courseMark;
        this.courseTea = courseTea;
    }

    public String getCourseTea() {
        return courseTea;
    }

    public void setCourseTea(String courseTea) {
        this.courseTea = courseTea;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Course(String courseName) {
        super();
        this.courseName = courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseMark() {
        return courseMark;
    }

    public void setCourseMark(String courseMark) {
        this.courseMark = courseMark;
    }

    public Course(String courseId, String courseName, String courseMark) {
        super();
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseMark = courseMark;
    }

    public Course() {
        super();
    }

    public int searchCourse() {

        return 0;
    }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseName=" + courseName
                + ", courseMark=" + courseMark + "]";
    }
}