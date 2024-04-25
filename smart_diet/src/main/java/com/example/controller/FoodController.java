package com.example.controller;

import com.example.entity.Food;
import com.example.entity.User;
import com.example.entity.UserBase;
import com.example.entity.Water;
import com.example.service.FoodService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("food")
@ResponseBody
public class FoodController {
    @Autowired
    private FoodService foodService;

    @GetMapping("getAllFood")
    @ApiOperation(value="获取所有食物", notes="")
    public List<Food> getAllFood(Food food){
        List<Food> f=foodService.getAllFood(food);
        if(f!=null) return f;
        else return null;
    }

    @GetMapping("searchAllFood")
    @ApiOperation(value="搜索食物名称", notes="")
    public List<Food> searchAllFood(Food food){
        List<Food> f=foodService.searchAllFood(food);
        if(f!=null) return f;
        else return null;
    }

    @PostMapping("addFood")
    @ApiOperation(value="新增食物", notes="")
    public String addFood(Food Food){
        int userBaseDB=foodService.addFood(Food);
        if(userBaseDB==1)return "success";
        else return "fail";
    }

    @PostMapping("/updateFood")
    @ApiOperation(value="修改食物", notes="")
    public String updateFood(Food food, HttpSession session){
        boolean i = foodService.updateFood(food);
        if(i==true) return "success";
        else return "fail";
    }

    @DeleteMapping("delFood")
    @ApiOperation(value="删除食物", notes="")
    public String delFood(Food Food){
        int UserDB=foodService.delFood(Food);
        if(UserDB==1)return "success";
        else return "fail";
    }
}
