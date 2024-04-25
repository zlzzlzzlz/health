package com.example.service;
import com.example.entity.Food;

import java.util.List;

public interface FoodService {
    List<Food> getAllFood(Food food);
    List<Food> searchAllFood(Food food);
    int addFood(Food Food);
    boolean updateFood(Food Food);
    int delFood(Food Food);
}
