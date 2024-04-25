package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserSerivceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(User user) {
        return userDao.login(user);
    }

    @Override
    public int addUserBase(UserBase userBase) {
        userDao.addUserBase(userBase);
        return 1;
    }

    @Override
    public boolean updateUserBase(UserBase userBase) {
        try {
            userDao.updateUserBase(userBase);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public UserBase getUserBase(UserBase userBase) {
        return userDao.getUserBase(userBase);
    }

    @Override
    public List<UserDiet> getUserDiet(UserDiet UserDiet) {
        return userDao.getUserDiet(UserDiet);
    }

    @Override
    public int addUserDiet(UserDiet UserDiet) {
        userDao.addUserDiet(UserDiet);
        return 1;
    }

    @Override
    public List<Water> getWater(Water Water) {
        return userDao.getWater(Water);
    }

    @Override
    public boolean updateWater(Water Water) {
        try {
            userDao.updateWater(Water);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int addWater(Water Water) {
        userDao.addWater(Water);
        return 1;
    }

    @Override
    public List<Article> getArticle(Article Article) {
        return userDao.getArticle(Article);
    }

    @Override
    public List<Article> searchArticle(Article Article) {
        return userDao.searchArticle(Article);
    }

    @Override
    public int addHot(Article Article) {
        userDao.addHot(Article);
        return 1;
    }

    @Override
    public int addArticle(Article Article) {
        userDao.addArticle(Article);
        return 1;
    }

    @Override
    public List<User> getAllUser(User User) {
        return userDao.getAllUser(User);
    }

    @Override
    public int addUser(User User) {
        userDao.addUser(User);
        return 1;
    }

    @Override
    public int editUser(User User) {
        userDao.editUser(User);
        return 1;
    }

    @Override
    public int delUser(User User) {
        userDao.delUser(User);
        return 1;
    }

    @Override
    public int reset(User User) {
        userDao.reset(User);
        return 1;
    }

    @Override
    public List<User> searchAllUser(User User) {
        return userDao.searchAllUser(User);
    }

    @Override
    public int addAdvice(Advice Advice) {
        userDao.addAdvice(Advice);
        return 1;
    }

    @Override
    public List<Advice> getAdvice(Advice Advice) {
        return userDao.getAdvice(Advice);
    }

    @Override
    public List<Advice> searchAdvice(Advice Advice) {
        return userDao.searchAdvice(Advice);
    }

    @Override
    public int delArticle(Article Article) {
        userDao.delArticle(Article);
        return 1;
    }

    @Override
    public List<Eat> getEat(Eat Eat) {
        return userDao.getEat(Eat); 
    }

    @Override
    public List<Eat> searchEat(Eat Eat) {
        return userDao.searchEat(Eat);
    }

    @Override
    public Eat gettheEat(Eat Eat) {
        return userDao.gettheEat(Eat);
    }

    @Override
    public int addEat(Eat Eat) {
        userDao.addEat(Eat);
        return 1;
    }

    @Override
    public int editEat(Eat Eat) {
        userDao.editEat(Eat);
        return 1;
    }

    @Override
    public int delEat(Eat Eat) {
        userDao.delEat(Eat);
        return 1;
    }


}
