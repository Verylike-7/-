package com.hema.hmfresh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hema.hmfresh.mapper.CourseMapper;
import com.hema.hmfresh.pojo.Course;
import com.hema.hmfresh.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
