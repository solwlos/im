package com.sol.admin.modules.base;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Page {
    private Integer pageNum;
    private Integer pageSize;
    private Integer start;
    public Page(Integer pageNum,Integer pageSize){
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.start = (pageNum - 1) * pageSize;
    }
//    public Integer getStart(){
//        return (pageNum - 1) * pageSize;
//    }

}
