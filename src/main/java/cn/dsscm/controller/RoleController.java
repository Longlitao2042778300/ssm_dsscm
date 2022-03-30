package cn.dsscm.controller;

import cn.dsscm.domain.Role;
import cn.dsscm.domain.User;
import cn.dsscm.service.RoleService;
import cn.dsscm.utils.Constants;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Long
 * @create 2022-03-26 13:24
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @RequestMapping("/list")
    public String getRoles(Model model){

        List<Role> roles = roleService.getRoleList();

        model.addAttribute("roleList",roles);
        return "rolelist";
    }

    @RequestMapping("/modify/{id}")
    public String modify(
            @PathVariable("id") Integer id,
            Model model
    ){
        model.addAttribute("role",roleService.getRoleById(id));
        return "rolemodify";
    }

    @RequestMapping("/modifySave")
    public String modifySave(
            Role role,
            HttpSession session){

        role.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
        role.setCreationDate(new Date());

        roleService.modifyRole(role);

        return "redirect:/role/list";

    }

    //ajax处理角色编码是否存在
    @RequestMapping(value = "/roleCodeExist.json",method = RequestMethod.GET)
    @ResponseBody
    public String roleCodeExist(@RequestParam("roleCode") String roleCode){
        Map<String,String> resultMap = new HashMap<String, String>();

        if (roleService.isExistRoleCode(roleCode) != null){
            resultMap.put("result","exist");
        }else {
            resultMap.put("result","not exist");
        }
        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping("/addSave")
    public String addSave(Role role,HttpSession session){

        //设置创建者和创建时间
        role.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
        role.setCreationDate(new Date());
        //保存role对象
        roleService.addRole(role);

        return "redirect:/role/list";
    }

    @RequestMapping(value = "/delRole.json",method = RequestMethod.GET)
    @ResponseBody
    public String delRole(@RequestParam("id") Integer id){
        Map<String,String> resultMap = new HashMap<String, String>();

        if (StringUtils.isNullOrEmpty(String.valueOf(id))){
            resultMap.put("delResult","not exist");
        }else if (roleService.delRole(id)){
            resultMap.put("delResult","true");
        }else {
            resultMap.put("delResult","false");
        }

        return JSONArray.toJSONString(resultMap);
    }

}
