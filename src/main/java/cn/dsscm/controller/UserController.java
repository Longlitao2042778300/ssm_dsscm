package cn.dsscm.controller;

import cn.dsscm.domain.Role;
import cn.dsscm.domain.User;
import cn.dsscm.service.RoleService;
import cn.dsscm.service.UserService;
import cn.dsscm.utils.Constants;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Long
 * @create 2022-03-16 16:56
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;

    /**
     * 登录操作，根据userCode和userPassword
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String userCode,
                        @RequestParam String userPassword,
                        HttpServletRequest request,
                        HttpSession session){
        // 调用service方法，进行用户匹配
        User user = userService.login(userCode, userPassword);
        if (null != user) {// 登录成功
            // 放入session
            session.setAttribute(Constants.USER_SESSION, user);
            // 页面跳转
            return "redirect:/index.jsp";
        } else {
            // 页面跳转（login.jsp）带出提示信息进行转发
            request.setAttribute("error", "用户名或密码不正确");
            return "login";
        }
    }

    /**
     * 注销
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session,HttpServletRequest request) {
        // 清除session
        session.removeAttribute(Constants.USER_SESSION);
        request.setAttribute("tip","注销登录成功，你可以选择其他账号进行登录！");
        return "login";
    }

    //ajax后台验证--userCode是否已存在
    @RequestMapping(value = "/userCodeExist.json", method = RequestMethod.GET)
    @ResponseBody
    public Object isUserCodeExist(@RequestParam String userCode) {
        Map<String, String> resultMap = new HashMap<String, String>();

        if (StringUtils.isEmptyOrWhitespaceOnly(userCode)) {
            //传入的参数为空
            resultMap.put("result", "empty");
        } else {
            User user = userService.isUserCodeExist(userCode);
            if (user != null) {
                resultMap.put("result", "exist");
            } else {
                //不存在
                resultMap.put("result", "non-existent");
            }
        }

        return JSONArray.toJSONString(resultMap);
    }

    //ajax请求返回用户权限
    @RequestMapping(value = "/roleList.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> getRoleList() {
        for (Role role : roleService.getRoleList()) {
            System.out.println(role);
        }
        //获取用户权限
        return roleService.getRoleList();
    }

    /**
     * 修改密码时，判断输入的旧密码是否正确
     */
    @RequestMapping(value = "/pwdModify.json", method = RequestMethod.POST)
    @ResponseBody
    public Object getPwdByUserId(@RequestParam String oldpassword, HttpSession session) {
        Map<String, String> resultMap = new HashMap<String, String>();
        if (null == session.getAttribute(Constants.USER_SESSION)) {// session过期（session域中没有了user对象）

            resultMap.put("result", "sessionError");

        } else if (StringUtils.isNullOrEmpty(oldpassword)) {// 旧密码输入为空

            resultMap.put("result", "error");

        } else {

            String sessionPwd = ((User) session.getAttribute(Constants.USER_SESSION)).getUserPassword();
            // 旧密码输入正确时，返回true
            if (oldpassword.equals(sessionPwd)) {
                resultMap.put("result", "true");
                // 旧密码输入不正确时，返回false
            } else {
                resultMap.put("result", "false");
            }
        }
        //转成JSON格式响应数据
        return JSONArray.toJSONString(resultMap);
    }

    /**
     * 保存修改的用户密码
     */
    @RequestMapping(value = "/passwordSave", method = RequestMethod.POST)
    public String passwordSave(@RequestParam(value = "newpassword") String newPassword,
                               HttpSession session, HttpServletRequest request) {

        //获取session域中的user对象
        User user = (User) session.getAttribute(Constants.USER_SESSION);
        //判断修改是否成功
        boolean flag;
        if (user != null && !StringUtils.isNullOrEmpty(newPassword)) {
            //执行修改操作
            flag = userService.updatePassword(user.getId(), newPassword);

            if (flag) {
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
                session.removeAttribute(Constants.USER_SESSION);// session注销
                return "login";//修改完后，要求用户重新登录
            } else {
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
            }
        } else {
            request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
        }

        return "pwdmodify";//修改失败后，要求用户重新输入密码
    }

    /**
     * 根据用户id查询用户信息
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Integer id, Model model, HttpServletRequest request) {

        User user = userService.getUserById(id);

        if (!StringUtils.isNullOrEmpty(user.getImgPath())) {
            //  /upload为一个虚拟路径
            user.setImgPath("/upload/" + user.getImgPath());
        }
        model.addAttribute(user);
        return "userview";
    }

    @RequestMapping(value = "/list")
    public String getUserList(
            Model model,
            @RequestParam(value = "queryname", required = false) String queryUserName,
            @RequestParam(value = "queryUserRole", required = false) Integer queryUserRole,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex) {

        // 设置页面容量
        int pageSize = Constants.pageSize;
        // 没有传入页码默认为第一页
        if (null == pageIndex) {
            pageIndex = 1;
        }
        if (queryUserName == null) {
            queryUserName = "";
        }
        //根据当前页或用户名或用户角色进行的分页查询
        PageInfo<User> userPageInfo = userService.getUserList(queryUserName, queryUserRole, pageIndex, pageSize);
        //为查找角色时提供选择角色
        List<Role> roleList = roleService.getRoleList();

        model.addAttribute("pi", userPageInfo);
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        return "userlist";
    }

    /**
     * 插入用户，attaches为图片的name，图片可以不上传，但是上传了就必须上传成功，否则继续返回用户添加页面
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(
            User user,
            @RequestParam(name = "attaches", required = false) List<MultipartFile> attaches,
            HttpServletRequest request,
            HttpSession session) {

        //用来判断上传文件是否成功
        boolean flag = true;
        //图片上传后保存的位置
        String imgPath = null;

        if (!attaches.isEmpty()) {//如果文件上传不为空



            for (MultipartFile file : attaches) {
                if (file.isEmpty()){
                    continue;
                }

                //获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                //获取文件的扩展名
                String suffix = FilenameUtils.getExtension(originalFilename);

                if (file.getSize() > Constants.fileSize) {// 上传大小不得超过 500k
                    request.setAttribute("errorInfo", " * 上传大小不得超过 500k");
                    flag = false;
                } else if ("jpg".equalsIgnoreCase(suffix)
                        || "png".equalsIgnoreCase(suffix)
                        || "jpeg".equalsIgnoreCase(suffix)
                        || "pneg".equalsIgnoreCase(suffix)) {// 上传图片格式是否正确

                    //设置上传文件的文件名
                    imgPath = UUID.randomUUID().toString() + "user_img." + suffix;
                    try {
                        file.transferTo(new File(Constants.dirPath + imgPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute("errorInfo", " * 上传失败！");
                        flag = false;
                    }

                } else {// 上传图片格式不正确时
                    request.setAttribute("errorInfo", " * 上传图片格式不正确,仅支持jpg、jpeg、png、pneg格式");
                    flag = false;
                }
            }
        }
        if (flag) {
            user.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
            user.setCreationDate(new Date());
            user.setImgPath(imgPath);

            try {
                userService.addUser(user);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorInfo", " * 上传失败！");
                return "useradd";
            }
            return "redirect:/user/list";

        }
        //上传的文件如果有问题就跳到添加页面
        return "useradd";
    }

    /**
     * 做用户修改前，先将要修改的用户信息查询出来
     */
    @RequestMapping("/modify/{id}")
    public String modify(@PathVariable Integer id, Model model, HttpServletRequest request) {

        User user = userService.getUserById(id);

//        System.out.println(user);

        if (!StringUtils.isNullOrEmpty(user.getImgPath())) {
            //  /upload为一个虚拟路径
            user.setImgPath("/upload/" + user.getImgPath());
        }
        model.addAttribute(user);
        return "usermodify";
    }

    /**
     * 修改用户，attaches为图片的name，图片可以不上传，但是上传失败了就会继续返回用户添加页面
     */
    @RequestMapping(value = "/modifySave", method = RequestMethod.POST)
    public String modifySave(
            User user,
            @RequestParam(name = "attaches", required = false) List<MultipartFile> attaches,
            HttpServletRequest request,
            HttpSession session) {

        //用来判断上传文件是否成功
        boolean flag = true;
        //图片上传后保存的位置
        String imgPath = null;

        if (!attaches.isEmpty()) {//如果文件上传不为空

            for (MultipartFile file : attaches) {
                if (file.isEmpty()){
                    continue;
                }

                //获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                //获取文件的扩展名
                String suffix = FilenameUtils.getExtension(originalFilename);

                if (file.getSize() > Constants.fileSize) {// 上传大小不得超过 500k
                    request.setAttribute("errorInfo", " * 上传大小不得超过 500k");
                    flag = false;
                } else if ("jpg".equalsIgnoreCase(suffix)
                        || "png".equalsIgnoreCase(suffix)
                        || "jpeg".equalsIgnoreCase(suffix)
                        || "pneg".equalsIgnoreCase(suffix)) {// 上传图片格式是否正确

                    //设置上传文件的文件名
                    imgPath =  UUID.randomUUID().toString() + "user_img." + suffix;
                    try {
                        file.transferTo(new File(Constants.dirPath + imgPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute("errorInfo", " * 上传失败！");
                        flag = false;
                    }

                } else {// 上传图片格式不正确时
                    request.setAttribute("errorInfo", " * 上传图片格式不正确,仅支持jpg、jpeg、png、pneg格式");
                    flag = false;
                }
            }
        }
        if (flag) {
            user.setModifyBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
            user.setModifyDate(new Date());
            user.setImgPath(imgPath);

            try {
                userService.modifyUser(user);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorInfo", " * 上传失败！");
                return "redirect:/user/view/"+user.getId();
            }
            return "redirect:/user/list";

        }
        //上传的文件如果有问题就跳到修改页面
        return "usermodify";
    }

    /**
     * ajax验证删除是否成功，
     */
    @RequestMapping(value = "/delUser.json",method = RequestMethod.GET)
    @ResponseBody
    public Object delUser(@RequestParam("id") Integer id,HttpSession session){
        Map<String, String> resultMap = new HashMap<String, String>();
        //该账号已登录了
        if (((User)(session.getAttribute(Constants.USER_SESSION))).getId().equals(id)) {
            resultMap.put("delResult","logged in");
        }else if (userService.getUserById(id) == null){//先查询用户是否不存在
            resultMap.put("delResult","not exist");
        }else {
            //返回是否删除成功
            boolean flag = userService.delUser(id);
            if (flag){
                resultMap.put("delResult","true");
            }else {
                resultMap.put("delResult","false");
            }
        }

        return JSONArray.toJSONString(resultMap);
    }

}
