package cn.dsscm.mapper;

import cn.dsscm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Long
 * @create 2022-03-17 19:42
 */
@Repository("roleMapper")
public interface RoleMapper {

    @Select("select * from tb_role")
    List<Role> findAll();

    @Select("select * from tb_role where id=#{id}")
    Role findRoleById(@Param("id") Integer id);

    @Update("<script>" +
            "update tb_role " +
            "<trim prefix='set ' suffix='where id=#{id}'>" +
            "<if test='roleName != null'>roleName=#{roleName}</if>" +
            "<if test='modifyBy != null'>modifyBy=#{modifyBy},</if>" +
            "<if test='modifyDate != null'>modifyDate=#{modifyDate},</if>" +
            "</trim>" +
            "</script>")
    void modifyRole(@Param("roleName") String roleName, @Param("id") Integer id,@Param("modifyBy") Integer modifyBy,@Param("modifyDate") Date modifyDate);

    @Select("select * from tb_role where roleCode=#{roleCode}")
    Role findRoleByRoleCode(@Param("roleCode") String roleCode);

    @Select("select roleName from tb_role where id=#{id}")
    String findRoleNameByUserRole(Integer id);

    @Insert("<script>" +
            "insert into tb_role" +
                "<trim prefix='(' suffixOverrides=',' suffix=') '>" +
                    "<if test='roleCode != null'>roleCode,</if>" +
                    "<if test='roleName != null'>roleName,</if>" +
                    "<if test='createdBy != null'>createdBy,</if>" +
                    "<if test='creationDate != null'>creationDate,</if>" +
                "</trim>" +
                "<trim prefix='value(' suffixOverrides=',' suffix=')'>" +
                    "<if test='roleCode != null'>#{roleCode},</if>" +
                    "<if test='roleName != null'>#{roleName},</if>" +
                    "<if test='createdBy != null'>#{createdBy},</if>" +
                    "<if test='creationDate != null'>#{creationDate},</if>" +
                "</trim>" +
            "</script>")
    void addRole(Role role);

    @Delete("delete from tb_role where id=#{id}")
    int delRole(@Param("id") Integer id);
}
