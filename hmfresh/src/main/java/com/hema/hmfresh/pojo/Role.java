package com.hema.hmfresh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("tb_role")
public class Role {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Date createTime;
    private Date updateTime;
    @TableField(exist = false)
    private List<Long> menuIds;
}