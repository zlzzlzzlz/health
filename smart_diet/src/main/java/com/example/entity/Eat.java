package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data //去除get/set和构造方法，减少代码量
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain=true)
public class Eat {
    private String id;
    private String userid;
    private String pic;
    private String detail;
    private String hot;
    private String breakfast;
    private String lunch;
    private String dinner;

}
