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
public class Article {
    private String userid;
    private String title;
    private String article;
    private String date;
    private String hot;
    private String id;
}