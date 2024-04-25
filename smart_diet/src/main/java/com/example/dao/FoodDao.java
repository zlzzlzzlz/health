package com.example.dao;
import com.example.entity.Food;
import com.example.entity.User;
import com.example.entity.UserBase;

import java.util.List;

public interface FoodDao {
    List<Food> getAllFood(Food food);
    List<Food> searchAllFood(Food food);
    int addFood(Food Food);
    boolean updateFood(Food Food);
    int delFood(Food Food);
}
