package cn.dsscm.service.impl;

import cn.dsscm.domain.User;
import cn.dsscm.mapper.UserMapper;
import cn.dsscm.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Long
 * @create 2022-03-16 17:04
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public User login(String userCode, String userPassword) {
        return userMapper.login(userCode,userPassword);
    }

    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }

    public boolean updatePassword(Integer id, String newPassword) {
        return userMapper.updatePassword(id, newPassword) > 0;
    }

    @Transactional
    public PageInfo<User> getUserList(String queryUserName, Integer queryUserRole, Integer pageIndex, int pageSize) {
        //开启分页
        PageHelper.startPage(pageIndex,pageSize);
        List<User> list = userMapper.getUserList(queryUserName, queryUserRole);

        return new PageInfo<User>(list);
    }

    /**
     * 保存用户时，判断输入的userCode是否存在
     */
    public User isUserCodeExist(String userCode) {
        return userMapper.isUserCodeExist(userCode);
    }

    /**
     * 添加用户，参数可以为空.我们这里采用前端页面必须填写的进行插入操作，选填的内容通过修改来操作
     */
    @Transactional
    public void addUser(User user) {

        userMapper.addUser(user);
//        System.out.println("受影响行数："+addUser);

        userMapper.modifyUser(user);
    }

    public void modifyUser(User user) {
        userMapper.modifyUser(user);
    }

    public boolean delUser(Integer id) {
        return userMapper.delUser(id) > 0;
    }
}
