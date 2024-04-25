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
public class UserDiet {
    private String userid;
    private String breakfast;
    private String lunch;
    private String dinner;
    private String date;
    private String weight;
    private String food_heat;
    private String food_pic;
    private String allheat;
}
