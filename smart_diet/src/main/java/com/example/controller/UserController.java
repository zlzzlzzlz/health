package com.example.controller;

import com.example.entity.*;
import com.example.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 登陆方法
     */
    @PostMapping("login")
    @ApiOperation(value="登录验证", notes="")
    public String login(User user, HttpSession session){
        User userDB=userService.login(user);
        if(userDB!=null){
            System.out.println(userDB);
            return "success";
        }else{
            return "fail";
        }
    }
    /**
     * 获取登陆人信息
     */
    @GetMapping("GetloginPerson")
    @ApiOperation(value="登录验证", notes="")
    public User GetloginPerson(User user, HttpSession session){
        User userDB=userService.login(user);
        if(userDB!=null){
            System.out.println(userDB);
            return userDB;
        }else{
            return null;
        }
    }
    /**
     * 新增用户基本信息
     */
    @PostMapping("addUserBase")
    @ApiOperation(value="新增用户基本信息", notes="")
    public String addUserBase(UserBase userbase){
        int userBaseDB=userService.addUserBase(userbase);
        if(userBaseDB==1)return "success";
        else return "fail";
    }
    /**
     * 更新用户基本信息
     */
    @PostMapping("/updateUserBase")
    @ApiOperation(value="更新用户基本信息", notes="")
    public String updateUserBase(UserBase userbase, HttpSession session){
        boolean i = userService.updateUserBase(userbase);
        if(i==true) return "success";
        else return "fail";
    }

    @GetMapping("getUserBase")
    @ApiOperation(value="获取用户基本信息", notes="")
    public UserBase getUserBase(UserBase userBase,HttpSession session){
        UserBase UB=userService.getUserBase(userBase);
        if(UB!=null) return UB;
        else return null;
    }

    @GetMapping("getUserDiet")
    @ApiOperation(value="获取当前用户用餐情况", notes="")
    public List<UserDiet> getAllFood(UserDiet userDiet){
        List<UserDiet> f=userService.getUserDiet(userDiet);
        if(f!=null) return f;
        else return null;
    }

    @PostMapping("addUserDiet")
    @ApiOperation(value="新增用户用餐情况", notes="")
    public String addUserDiet(UserDiet Userdiet){
        int userDietDB=userService.addUserDiet(Userdiet);
        if(userDietDB==1)return "success";
        else return "fail";
    }

    @GetMapping("getWater")
    @ApiOperation(value="获取当前用户饮水情况", notes="")
    public List<Water> getAllFood(Water water){
        List<Water> f=userService.getWater(water);
        if(f!=null) return f;
        else return null;
    }

    @PostMapping("/updateWater")
    @ApiOperation(value="更新用户饮水信息", notes="")
    public String updateWater(Water water, HttpSession session){
        boolean i = userService.updateWater(water);
        if(i==true) return "success";
        else return "fail";
    }

    @PostMapping("addWater")
    @ApiOperation(value="新增用户饮水情况", notes="")
    public String addWater(Water water){
        int WaterDB=userService.addWater(water);
        if(WaterDB==1)return "success";
        else return "fail";
    }

    @GetMapping("getArticle")
    @ApiOperation(value="获取文章", notes="")
    public List<Article> getAllFood(Article Article){
        List<Article> f=userService.getArticle(Article);
        if(f!=null) return f;
        else return null;
    }

    @GetMapping("searchArticle")
    @ApiOperation(value="获取文章", notes="")
    public List<Article> searchAllFood(Article Article){
        List<Article> f=userService.searchArticle(Article);
        if(f!=null) return f;
        else return null;
    }

    @PostMapping("/addHot")
    @ApiOperation(value="点赞", notes="")
    public String addHot(Article Article, HttpSession session){
        int i = userService.addHot(Article);
        if(i==1) return "success";
        else return "fail";
    }

    @PostMapping("addArticle")
    @ApiOperation(value="新增文章", notes="")
    public String addArticle(Article Article){
        int ArticleDB=userService.addArticle(Article);
        if(ArticleDB==1)return "success";
        else return "fail";
    }

    @GetMapping("getAllUser")
    @ApiOperation(value="获取当前所有用户", notes="")
    public List<User> getAllUser(User user){
        List<User> u=userService.getAllUser(user);
        if(u!=null) return u;
        else return null;
    }

    @PostMapping("addUser")
    @ApiOperation(value="新增用户", notes="")
    public String addUser(User User){
        int UserDB=userService.addUser(User);
        if(UserDB==1)return "success";
        else return "fail";
    }

    @PostMapping("editUser")
    @ApiOperation(value="修改用户信息", notes="")
    public String editUser(User User){
        int i = userService.editUser(User);
        if(i==1) return "success";
        else return "fail";
    }

    @DeleteMapping("delUser")
    @ApiOperation(value="删除用户", notes="")
    public String delUser(User User){
        int UserDB=userService.delUser(User);
        if(UserDB==1)return "success";
        else return "fail";
    }

    @PostMapping("reset")
    @ApiOperation(value="重置密码", notes="")
    public String reset(User User){
        int i = userService.reset(User);
        if(i==1) return "success";
        else return "fail";
    }

    @GetMapping("searchAllUser")
    @ApiOperation(value="搜索用户名称", notes="")
    public List<User> searchAllUser(User User){
        List<User> f=userService.searchAllUser(User);
        if(f!=null) return f;
        else return null;
    }

    @PostMapping("addAdvice")
    @ApiOperation(value="新增建议", notes="")
    public String addAdvice(Advice Advice){
        int userBaseDB=userService.addAdvice(Advice);
        if(userBaseDB==1)return "success";
        else return "fail";
    }

    @GetMapping("getAdvice")
    @ApiOperation(value="获取所有食物反馈", notes="")
    public List<Advice> getAdvice(Advice Advice){
        List<Advice> f=userService.getAdvice(Advice);
        if(f!=null) return f;
        else return null;
    }

    @GetMapping("searchAdvice")
    @ApiOperation(value="获取当前食物反馈", notes="")
    public List<Advice> searchAdvice(Advice Advice){
        List<Advice> f=userService.searchAdvice(Advice);
        if(f!=null) return f;
        else return null;
    }

    @DeleteMapping("delArticle")
    @ApiOperation(value="删除文章", notes="")
    public String delArticle(Article Article){
        int UserDB=userService.delArticle(Article);
        if(UserDB==1)return "success";
        else return "fail";
    }

    @GetMapping("getEat")
    @ApiOperation(value="获取所有食物反馈", notes="")
    public List<Eat> getEat(Eat Eat){
        List<Eat> f=userService.getEat(Eat);
        if(f!=null) return f;
        else return null;
    }

    @GetMapping("gettheEat")
    @ApiOperation(value="获取所有食物反馈", notes="")
    public Eat gettheEat(Eat Eat){
        Eat f=userService.gettheEat(Eat);
        if(f!=null) return f;
        else return null;
    }

    @GetMapping("searchEat")
    @ApiOperation(value="获取当前食谱", notes="")
    public List<Eat> searchEat(Eat Eat){
        List<Eat> f=userService.searchEat(Eat);
        if(f!=null) return f;
        else return null;
    }

    @PostMapping("addEat")
    @ApiOperation(value="新增食谱", notes="")
    public String addEat(Eat Eat){
        int userBaseDB=userService.addEat(Eat);
        if(userBaseDB==1)return "success";
        else return "fail";
    }

    @PostMapping("editEat")
    @ApiOperation(value="修改食谱", notes="")
    public String editEat(Eat Eat){
        int i = userService.editEat(Eat);
        if(i==1) return "success";
        else return "fail";
    }

    @DeleteMapping("delEat")
    @ApiOperation(value="删除食谱", notes="")
    public String delEat(Eat Eat){
        int UserDB=userService.delEat(Eat);
        if(UserDB==1)return "success";
        else return "fail";
    }
}
