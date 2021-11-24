package com.hema.hmfresh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hema.hmfresh.mapper.StudentMapper;
import com.hema.hmfresh.pojo.Student;
import com.hema.hmfresh.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
