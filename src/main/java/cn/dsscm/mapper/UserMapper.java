package cn.dsscm.mapper;

import cn.dsscm.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-16 17:05
 */
@Repository("userMapper")
public interface UserMapper {

    /**
     * 登录操作，根据userCode和userPassword
     */
    @Select("select * from tb_user where userCode=#{userCode} and userPassword=#{userPassword}")
    User login(@Param("userCode") String userCode,@Param("userPassword") String userPassword);

    /**
     * 根据用户id查user信息(一对一:一个用户对应一个角色)
     */
    @Select("select * from tb_user where id=#{id} ")
    @Result(column = "userRole",property = "userRoleName",javaType = String.class,
            one = @One(select = "cn.dsscm.mapper.RoleMapper.findRoleNameByUserRole"))
    User getUserById(@Param("id") Integer id);

    /**
     * 根据id修改用户表中的userPassword字段
     */
    @Update("update tb_user set userPassword=#{newPassword} where id=#{id}")
    int updatePassword(@Param("id") Integer id,@Param("newPassword") String newPassword);

    //没有加条件时，默认全查
    @Select("<script>"
                + "select * from tb_user "+
                "<trim prefix='where ' prefixOverrides='and' >" +
                    "<if test='userRole != null and userRole > 0'>"
                    +     "and userRole = #{userRole}"
                    + "</if>"
                    + "<if test='userName != null'>"
                    +     "and userName like CONCAT('%',#{userName},'%')"
                    + "</if>" +
                "</trim>"
                + "order by creationDate DESC" +
            "</script>")
    @Result(column = "userRole",property = "userRoleName",javaType = String.class,
            one = @One(select = "cn.dsscm.mapper.RoleMapper.findRoleNameByUserRole"))
    List<User> getUserList(@Param("userName") String queryUserName,@Param("userRole") Integer queryUserRole);

    /**
     * 保存用户时，判断输入的userCode是否存在
     */
    @Select("select * from tb_user where userCode=#{userCode}")
    User isUserCodeExist(@Param("userCode") String userCode);

    @Insert("insert into tb_user(userCode,userName,userPassword,birthday,userRole,createdBy,creationDate)" +
            "values(#{userCode},#{userName},#{userPassword},#{birthday},#{userRole},#{createdBy},#{creationDate})")
    @Options(keyProperty = "id",useGeneratedKeys = true,keyColumn = "id")
    void addUser(User user);

    @Update("<script>"
                +"update tb_user " +
                "<trim prefix='set ' suffixOverrides=',' suffix='where id=#{id}'>" +
                    "<if test='userCode != null'>userCode=#{userCode},</if>" +
                    "<if test='userName != null'>userName=#{userName},</if>" +
                    "<if test='userPassword != null'>userPassword=#{userPassword},</if>" +
                    "<if test='gender != null'>gender=#{gender},</if>" +
                    "<if test='birthday != null'>birthday=#{birthday},</if>" +
                    "<if test='phone != null'>phone=#{phone},</if>" +
                    "<if test='email != null'>email=#{email},</if>" +
                    "<if test='address != null'>address=#{address},</if>" +
                    "<if test='userDesc != null'>userDesc=#{userDesc},</if>" +
                    "<if test='userRole != null'>userRole=#{userRole},</if>" +
                    "<if test='modifyBy != null'>modifyBy=#{modifyBy},</if>" +
                    "<if test='modifyDate != null'>modifyDate=#{modifyDate},</if>" +
                    "<if test='imgPath != null'>imgPath=#{imgPath},</if>" +
                "</trim>" +
            "</script>")
    void modifyUser(User user);

    @Delete("delete from tb_user where id=#{id}")
    int delUser(@Param("id") Integer id);

    //当删除角色时，将角色关联的用户清除
    @Update("update tb_user set userRole=#{zero} where userRole=#{id}")
    void modifyUserRoleByRoleId(@Param("zero") Integer zero,@Param("id") Integer id);
}
