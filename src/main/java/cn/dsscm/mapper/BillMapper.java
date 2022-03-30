package cn.dsscm.mapper;

import cn.dsscm.domain.Bill;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-22 13:38
 */
@Repository("billMapper")
public interface BillMapper {

    @Select("<script>" +
                "select b.*,p.proName as providerName from tb_bill b,tb_provider p where b.providerId=p.id" +
                "<trim prefixOverrides='and'>" +
                    "<if test='productName != null'> and productName like concat('%',#{productName},'%')</if>" +
                    "<if test='isPayment != null'> and isPayment=#{isPayment}</if>" +
                    "<if test='providerId != null'> and providerId=#{providerId}</if>" +
                "</trim>" +
                "order by creationDate DESC" +
            "</script>")
    /*@Results(
            @Result(javaType = String.class,column = "providerId",property = "providerName",
            one = @One(select = "cn.dsscm.mapper.ProviderMapper.findProviderNameById"))
    )*/
    List<Bill> findBills(@Param("productName") String queryProductName,@Param("isPayment") Integer queryIsPayment,@Param("providerId") Integer queryProviderId);


    @Insert("insert into tb_bill(billCode,productName,productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate) " +
            "values(#{billCode},#{productName},#{productUnit},#{productCount},#{totalPrice},#{isPayment},#{providerId},#{createdBy},#{creationDate})")
    void addSave(Bill bill);

    @Select("select b.*,p.proName as providerName from tb_bill b,tb_provider p " +
            "where b.providerId=p.id and b.id=#{id}")
    /*@Results(
            @Result(javaType = String.class,column = "providerId",property = "providerName",
                    one = @One(select = "cn.dsscm.mapper.ProviderMapper.findProviderNameById"))
    )*/
    Bill findBillById(@Param("id") Integer id);


    @Delete("delete from tb_bill where id=#{id}")
    int delBill(@Param("id") Integer id);

    @Update("<script>" +
                "update tb_bill " +
                "<trim prefix='set' suffixOverrides=',' suffix='where id = #{id}'>" +
                    "<if test='billCode != null'>billCode=#{billCode},</if>" +
                    "<if test='productId != null'>productId=#{productId},</if>" +
                    "<if test='productName != null'>productName=#{productName},</if>" +
                    "<if test='productDesc != null'>productDesc=#{productDesc},</if>" +
                    "<if test='productUnit != null'>productUnit=#{productUnit},</if>" +
                    "<if test='productCount != null'>productCount=#{productCount},</if>" +
                    "<if test='totalPrice != null'>totalPrice=#{totalPrice},</if>" +
                    "<if test='providerId != null'>providerId=#{providerId},</if>" +
                    "<if test='isPayment != null'>isPayment=#{isPayment},</if>" +
                    "<if test='modifyBy != null'>modifyBy=#{modifyBy},</if>" +
                    "<if test='modifyDate != null'>modifyDate=#{modifyDate},</if>" +
                "</trim>" +
            "</script>")
    void modifyBill(Bill bill);
}
