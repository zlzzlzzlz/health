package com.example.service;

import com.example.dao.FoodDao;
import com.example.entity.Food;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FoodSerivcelmpl implements FoodService{

    @Autowired
    private FoodDao foodDao;

    @Override
    public List<Food> getAllFood(Food food) {
        return foodDao.getAllFood(food);
    }

    @Override
    public List<Food> searchAllFood(Food food) {
        return foodDao.searchAllFood(food);
    }

    @Override
    public int addFood(Food Food) {
        foodDao.addFood(Food);
        return 1;
    }

    @Override
    public boolean updateFood(Food Food) {
        try {
            foodDao.updateFood(Food);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int delFood(Food Food) {
        foodDao.delFood(Food);
        return 1;
    }
}
