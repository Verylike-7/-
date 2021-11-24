package com.hema.hmfresh.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hema.hmfresh.pojo.Course;
import com.hema.hmfresh.pojo.PageResult;
import com.hema.hmfresh.service.CourseService;
import com.hema.hmfresh.util.ImportExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("save")
    public void saveCourse(@RequestBody Course course) {
        courseService.save(course);
    }

    @PutMapping("update")
    public void updateCourse(@RequestBody Course course) {
        courseService.updateById(course);
    }

    @DeleteMapping("/{cno}")
    public void deleteCourseById(@PathVariable("cno") String cno) {
        courseService.removeById(cno);
    }

    @GetMapping("/{cno}")
    public Course findById(@PathVariable("cno") String cno) {
        Course course = courseService.getById(cno);
        return course;

    }

    @GetMapping("/all")
    public PageResult<Course> queryCoursePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<Course> result = courseService.page(new Page<>(page, size));
        return new PageResult<>(result.getTotal(), result.getRecords());
    }


    /**
     * 导入excel表
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(path = "/import", method = RequestMethod.POST)
    public void uploadExcel(@RequestParam("file") MultipartFile file) {
        // 导入excel这的学生列表
        try {
            List<Course> courses = ImportExcelUtil.importExcel(Course.class, file.getInputStream());
            // 写入数据库
            courseService.saveBatch(courses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出excel模版
     *
     * @version 1.0
     * @since 1.0
     */
    @RequestMapping(path = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse response) {
        try {
            List<Course> list = courseService.list();
            response.setContentType("application/xls;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(("课程列表").getBytes("UTF-8"), "iso-8859-1") + ".xls");
            BufferedInputStream input = new BufferedInputStream(ImportExcelUtil.excelModelByClass(list, Course.class, null, null));
            byte buffBytes[] = new byte[1024];
            OutputStream os = response.getOutputStream();
            int read = 0;
            while ((read = input.read(buffBytes)) != -1) {
                os.write(buffBytes, 0, read);
            }
            os.flush();
            os.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
