package com.peak.training.course.course.service;

import com.peak.training.course.course.entity.Course;
import com.peak.training.course.course.port.CoursePort;
import com.peak.training.course.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CourseService implements CoursePort {

    @Autowired
    CourseRepository courseRepository ;


    @Override
    public Course createNewCourse(String categoryCode, String title, String courseTypeCode,
                                  int duaration, String description, String courseLevelCode,
                                  String image, String video, int updateUserId) {
        Course course = Course.createBuilder()
                .categoryCode(categoryCode)
                .title(title)
                .courseTypeCode(courseTypeCode)
                .description(description)
                .courseLevelCode(courseLevelCode)
                .image(image)
                .video(video)
                .build() ;
        courseRepository.save(course) ;

        return course ;
    }

    @Override
    public void updateCourseInfo(int courseId, String categoryCode, String title,
                                 String courseTypeCode, int duaration, String description,
                                 String courseLevelCode, String image, String video, int updateUserId) {
        Course course = courseRepository.getById(courseId) ;
        course.updateCourseInfo(categoryCode,title,courseTypeCode,duaration,description,courseLevelCode,image,video);
        courseRepository.save(course) ;
    }

    @Override
    public void approveCourse() {

    }

    @Override
    public void disableCourse() {

    }
}
