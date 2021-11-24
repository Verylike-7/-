package com.hema.hmfresh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hema.hmfresh.util.ModelProp;
import com.hema.hmfresh.util.ModelTitle;
import lombok.Data;

@TableName("tb_course")
@Data
@ModelTitle(name="课程列表")
public class Course {
    @TableId(type = IdType.AUTO)
    @ModelProp(name = "课程编号", colIndex = 0  , nullable = false)
    private Integer cno;
    @ModelProp(name = "课程名字", colIndex = 1  , nullable = false)
    private String cname;
}
