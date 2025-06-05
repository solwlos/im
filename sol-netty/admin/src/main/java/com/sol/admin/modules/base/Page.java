package com.sol.admin.modules.base;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Page {
    private Integer current;
    private Integer size;
    private Integer start;
    public Page(Integer current,Integer size){
        this.current = current;
        this.size = size;
        this.start = (current - 1) * size;
    }
//    public Integer getStart(){
//        return (pageNum - 1) * pageSize;
//    }

}
