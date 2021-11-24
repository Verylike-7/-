package com.hema.hmfresh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hema.hmfresh.util.ModelProp;
import com.hema.hmfresh.util.ModelTitle;
import lombok.Data;

@TableName("tb_score")
@Data
@ModelTitle(name="成绩列表")
public class Score {
    @TableId(type = IdType.AUTO)
    @ModelProp(name = "编号", colIndex = 0  , nullable = false)
    private Long id;
    @ModelProp(name = "学生编号", colIndex = 1  , nullable = false)
    private Long sid;
    @ModelProp(name = "课程编号", colIndex = 2  , nullable = false)
    private Integer  cno;
    @ModelProp(name = "课程成绩", colIndex = 3)
    private Double degree;
}
