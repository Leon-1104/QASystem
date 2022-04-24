package com.leon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author 赵号龙
 * @since 2022-03-23
 */
@Getter
@Setter
@ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件名称")
    private String name;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("是否删除")
    private Boolean isDelete;

    @ApiModelProperty("是否禁用")
    private Boolean enable;

    @ApiModelProperty("文件md5")
    private String md5;


}
