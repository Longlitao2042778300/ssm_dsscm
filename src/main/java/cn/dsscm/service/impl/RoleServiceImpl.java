package cn.dsscm.service.impl;

import cn.dsscm.domain.Role;
import cn.dsscm.mapper.RoleMapper;
import cn.dsscm.mapper.UserMapper;
import cn.dsscm.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Long
 * @create 2022-03-17 19:39
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserMapper userMapper;

    public List<Role> getRoleList() {
        return roleMapper.findAll();
    }

    public Role getRoleById(Integer id) {
        return roleMapper.findRoleById(id);
    }

    public void modifyRole(Role role) {
        roleMapper.modifyRole(role.getRoleName(),role.getId(),role.getModifyBy(),role.getModifyDate());
    }

    public Role isExistRoleCode(String roleCode) {
        return roleMapper.findRoleByRoleCode(roleCode);
    }

    public void addRole(Role role) {
        roleMapper.addRole(role);
    }

    @Transactional
    public boolean delRole(Integer id) {
        //当删除角色时，将角色关联的用户清除
        userMapper.modifyUserRoleByRoleId(0,id);

        return roleMapper.delRole(id) > 0;
    }
}
