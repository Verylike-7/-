package com.hema.hmfresh.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hema.hmfresh.pojo.PageResult;
import com.hema.hmfresh.pojo.Student;
import com.hema.hmfresh.service.StudentService;
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
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("save")
    public void saveStudent(@RequestBody Student student) {
        studentService.save(student);
    }

    @PutMapping("update")
    public void updateStudent(@RequestBody Student student) {
        studentService.updateById(student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable("id") Long id) {
        studentService.removeById(id);
    }

    @GetMapping("/{id}")
    public Student findById(@PathVariable("id") Long id) {
        Student student = studentService.getById(id);
        return student;
    }

    @GetMapping("/all")
    public PageResult<Student> queryStudentPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<Student> result = studentService.page(new Page<>(page, size));
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
            List<Student> students = ImportExcelUtil.importExcel(Student.class, file.getInputStream());
            // 写入数据库
            studentService.saveBatch(students);
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
            List<Student> list = studentService.list();
            response.setContentType("application/xls;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(("学生列表").getBytes("UTF-8"), "iso-8859-1") + ".xls");
            BufferedInputStream input = new BufferedInputStream(ImportExcelUtil.excelModelByClass(list, Student.class, null, null));
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
