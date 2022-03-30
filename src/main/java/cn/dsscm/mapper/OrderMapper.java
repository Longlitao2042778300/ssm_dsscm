package cn.dsscm.mapper;

import cn.dsscm.domain.Order;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-23 14:39
 */
@Repository("orderMapper")
public interface OrderMapper {

    @Select("<script>" +
            "select *,id oid from tb_order " +
                "<trim prefix='where ' prefixOverrides='and'>" +
                    "<if test='userName != null'> and userName like concat('%',#{userName},'%')</if>" +
                    "<if test='status != null and status > 0'> and status=#{status}</if>" +
                "</trim>" +
            "</script>")
    @Result(column = "oid", property = "products", javaType = List.class,
            many = @Many(select = "cn.dsscm.mapper.ProductMapper.getProductByIds"))
    List<Order> getOrders(@Param("userName") String userName, @Param("status") Integer status);


//    @Select("select quantity from tb_order_detail where orderId=#{orderId} and productId=#{productId}")
//    BigDecimal getQuantity(@Param("orderId") Integer orderId,@Param("productId") Integer productId);

}
