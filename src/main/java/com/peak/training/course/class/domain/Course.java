package com.peak.training.course;

import com.peak.training.common.domain.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Course extends BaseEntity {

    Integer courseId ;
    String categoryCode;
    String title;
    String courceTypeCode;
    int duaration;
    String description;
    String courseLevelCode ;
    String image;
    String video;
    String statusCode;


}
