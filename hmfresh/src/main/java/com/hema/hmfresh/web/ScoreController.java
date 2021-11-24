package com.hema.hmfresh.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hema.hmfresh.pojo.PageResult;
import com.hema.hmfresh.pojo.Score;
import com.hema.hmfresh.service.ScoreService;
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
@RequestMapping("score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    @PostMapping("save")
    public void saveScore(@RequestBody Score score) {
        scoreService.save(score);
    }

    @PutMapping("update")
    public void updateScore(@RequestBody Score score) {
        scoreService.updateById(score);
    }
    @DeleteMapping("/{id}")
    public void deleteScoreById(@PathVariable("id") Long id) {
        scoreService.removeById(id);
    }

    @GetMapping("/all")
    public PageResult<Score> queryCoursePage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "4") Integer size
    ) {
        Page<Score> result = scoreService.page(new Page<>(page, size));
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
            List<Score> scores= ImportExcelUtil.importExcel(Score.class, file.getInputStream());
            // 写入数据库
            scoreService.saveBatch(scores);
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
            List<Score> list = scoreService.list();
            response.setContentType("application/xls;charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(("成绩列表").getBytes("UTF-8"), "iso-8859-1") + ".xls");
            BufferedInputStream input = new BufferedInputStream(ImportExcelUtil.excelModelByClass(list, Score.class, null, null));
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
