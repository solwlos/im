package com.sol.admin.modules.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class EntitySearchQuery<T> {

    public EntitySearchQuery(T entity){
        this.entity = entity;
        this.page = null;
    }
    /**
     * 使用这个类，可以进行分页条件查询参数的封装
     */
    @ApiModelProperty(value = "分页条件查询参数")
    private Page page;
    // 用于实体搜索条件查询
    @ApiModelProperty(value = "用于实体搜索条件查询")
    private T entity;

    // 用于排序 asc、desc
    @ApiModelProperty(value = "排序方式:asc、desc")
    private String sort;

    // 指定那个排序字段
    @ApiModelProperty(value = "排序字段,如：create_time,会根据这个字段排序")
    private String field;

    // 去除分页
    public EntitySearchQuery<T> init(){
        this.page = null;
        return this;
    }

}