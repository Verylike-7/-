package com.hema.hmfresh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hema.hmfresh.util.ModelProp;
import com.hema.hmfresh.util.ModelTitle;
import lombok.Data;

@TableName("tb_student")
@Data
@ModelTitle(name="学生列表")
public class Student {
    @TableId(type = IdType.AUTO)
    private long id;
    @TableField("student_no")
    @ModelProp(name = "编号", colIndex = 0, nullable = false)
    private long studentno;
    @ModelProp(name = "姓名", colIndex = 1, nullable = false)
    private String name;
    @ModelProp(name = "性别", colIndex = 2, nullable = false)
    private String sex;
    @ModelProp(name = "身份证", colIndex = 3, nullable = false)
    private String card;
    @ModelProp(name = "生日", colIndex = 4, nullable = false)
    private String birth;
    @TableField("enter_date")
    @ModelProp(name = "入学时间", colIndex = 5, nullable = false)
    private String enterdate;
    @ModelProp(name = "家庭地址", colIndex = 6)
    private String address;

}
