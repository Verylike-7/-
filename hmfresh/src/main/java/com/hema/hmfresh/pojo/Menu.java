package com.hema.hmfresh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("tb_menu")
public class Menu {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String path;
    private String icon;
    private Boolean hasChildren;
    private Long parentId;
    private Date createTime;
    private Date updateTime;
    @TableField(exist = false)
    private List<Menu> submenus;
}
