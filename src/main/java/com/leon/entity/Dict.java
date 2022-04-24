package com.leon.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 字典实体类
 *
 * @author Leon
 */
@TableName("sys_dict")
@Data
public class Dict {
    //名称
    private String name;
    private String value;
    private String type;
}
