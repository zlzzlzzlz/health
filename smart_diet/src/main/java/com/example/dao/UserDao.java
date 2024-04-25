package com.example.dao;

import com.example.entity.*;


import java.util.List;

public interface UserDao {
    User login(User user);
    void addUserBase(UserBase userBase);
    void updateUserBase(UserBase userBase);
    UserBase getUserBase(UserBase userBase);
    List<UserDiet> getUserDiet(UserDiet UserDiet);
    int addUserDiet(UserDiet UserDiet);
    List<Water> getWater(Water Water);
    void updateWater(Water Water);
    void addWater(Water Water);
    List<Article> getArticle(Article Article);
    List<Article> searchArticle(Article Article);
    int addHot(Article Article);
    int addArticle(Article Article);
    List<User> getAllUser(User User);
    int addUser(User User);
    int editUser(User User);
    int delUser(User User);
    int reset(User User);
    List<User> searchAllUser(User User);
    int addAdvice(Advice Advice);
    List<Advice> getAdvice(Advice Advice);
    List<Advice> searchAdvice(Advice Advice);
    int delArticle(Article Article);
    List<Eat> getEat(Eat Eat);
    List<Eat> searchEat(Eat Eat);
    Eat gettheEat(Eat Eat);
    int addEat(Eat Eat);
    int editEat(Eat Eat);
    int delEat(Eat Eat);
}
