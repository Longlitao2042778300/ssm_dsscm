package cn.dsscm.service;

import cn.dsscm.domain.User;
import com.github.pagehelper.PageInfo;

/**
 * @author Long
 * @create 2022-03-16 17:03
 */
public interface UserService {
    /**
     * 登录操作，根据userCode和userPassword
     */
    User login(String userCode, String userPassword);

    User getUserById(Integer id);

    /**
     * 根据id，修改密码
     */
    boolean updatePassword(Integer id, String newPassword);

    /**
     * 没有加条件时，默认显示第一页。有条件用户名或用户权限就按条件来进行查询
     */
    PageInfo<User> getUserList(String queryUserName, Integer queryUserRole, Integer pageIndex, int pageSize);

    /**
     * 在保存用户时，做ajax判断输入的userCode是否存在
     */
    User isUserCodeExist(String userCode);

    /**
     * 添加用户，参数可以为空.我们这里采用前端页面必须填写的进行插入操作，选填的内容通过修改来操作
     */
    void addUser(User user);

    /**
     * 根据id修改用户信息
     */
    void modifyUser(User user);

    /**
     * 根据id删除user对象
     */
    boolean delUser(Integer id);
}
