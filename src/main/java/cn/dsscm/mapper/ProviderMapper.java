package cn.dsscm.mapper;

import cn.dsscm.domain.Provider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-22 13:55
 */
@Repository("providerMapper")
public interface ProviderMapper {

//    @Select("select * from tb_provider")
//    List<Provider> findAll();

    @Select("<script>" +
            "select * from tb_provider " +
                "<trim prefix='where ' prefixOverrides='and'>" +
                    "<if test='proCode != null'> and proCode like concat('%',#{proCode},'%') </if>" +
                    "<if test='proName != null'> and proName like concat('%',#{proName},'%') </if>" +
                "</trim>" +
            "</script>")
    List<Provider> findProviders(@Param("proCode") String queryProCode,@Param("proName") String queryProName);

    @Delete("delete from tb_provider where id=#{id}")
    int delProvider(@Param("id") Integer id);

    @Select("select * from tb_provider where id=#{id}")
    Provider findProviderById(@Param("id") Integer id);

    @Update("<script>" +
            "update tb_provider " +
                "<trim prefix='set' suffixOverrides=',' suffix='where id = #{id}'>" +
                    "<if test='proCode != null'>proCode=#{proCode},</if>" +
                    "<if test='proName != null'>proName=#{proName},</if>" +
                    "<if test='proContact != null'>proContact=#{proContact},</if>" +
                    "<if test='proPhone != null'>proPhone=#{proPhone},</if>" +
                    "<if test='proAddress != null'>proAddress=#{proAddress},</if>" +
                    "<if test='proFax != null'>proFax=#{proFax},</if>" +
                    "<if test='proDesc != null'>proDesc=#{proDesc},</if>" +
                    "<if test='companyLicPicPath != null'>companyLicPicPath=#{companyLicPicPath},</if>" +
                    "<if test='orgCodePicPath != null'>orgCodePicPath=#{orgCodePicPath},</if>" +
                    "<if test='modifyBy != null'>modifyBy=#{modifyBy},</if>" +
                    "<if test='modifyDate != null'>modifyDate=#{modifyDate},</if>" +
                "</trim>" +
            "</script>")
    void modifyProvider(Provider provider);

    @Insert("<script>" +
            "insert into tb_provider" +
                "<trim prefix='(' suffixOverrides=',' suffix=') '>" +
                    "<if test='proCode != null'>proCode,</if>" +
                    "<if test='proName != null'>proName,</if>" +
                    "<if test='proContact != null'>proContact,</if>" +
                    "<if test='proPhone != null'>proPhone,</if>" +
                    "<if test='proAddress != null'>proAddress,</if>" +
                    "<if test='proFax != null'>proFax,</if>" +
                    "<if test='proDesc != null'>proDesc,</if>" +
                    "<if test='companyLicPicPath != null'>companyLicPicPath,</if>" +
                    "<if test='orgCodePicPath != null'>orgCodePicPath,</if>" +
                    "<if test='createdBy != null'>createdBy,</if>" +
                    "<if test='creationDate != null'>creationDate,</if>" +
                "</trim>" +
                "<trim prefix=' value(' suffixOverrides=',' suffix=') '>" +
                    "<if test='proCode != null'>#{proCode},</if>" +
                    "<if test='proName != null'>#{proName},</if>" +
                    "<if test='proContact != null'>#{proContact},</if>" +
                    "<if test='proPhone != null'>#{proPhone},</if>" +
                    "<if test='proAddress != null'>#{proAddress},</if>" +
                    "<if test='proFax != null'>#{proFax},</if>" +
                    "<if test='proDesc != null'>#{proDesc},</if>" +
                    "<if test='companyLicPicPath != null'>#{companyLicPicPath},</if>" +
                    "<if test='orgCodePicPath != null'>#{orgCodePicPath},</if>" +
                    "<if test='createdBy != null'>#{createdBy},</if>" +
                    "<if test='creationDate != null'>#{creationDate},</if>" +
                "</trim>" +
            "</script>")
    void addProvider(Provider provider);

    //根据供应商id查询供应商名字
    /*@Select("select proName from tb_provider where id=#{id}")
    String findProviderNameById(@Param("id") Integer providerId);*/
}
