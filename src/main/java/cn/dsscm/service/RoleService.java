package cn.dsscm.service;

import cn.dsscm.domain.Role;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-17 18:17
 */
public interface RoleService {
    /**
     * 查所有role信息
     */
    List<Role> getRoleList();

    Role getRoleById(Integer id);

    void modifyRole(Role role);

    Role isExistRoleCode(String roleCode);

    void addRole(Role role);

    boolean delRole(Integer id);
}
