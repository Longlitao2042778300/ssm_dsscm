package cn.dsscm.mapper;

import cn.dsscm.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-16 16:43
 */
@Repository("productMapper")
public interface ProductMapper {

    @Select("<script>"
                +"SELECT * from tb_product "
                    +"<trim prefix='where' prefixOverrides='and'>" +
                        "<if test='name != null'>and name like CONCAT('%',#{name},'%')</if>" +
                        "<if test='pc1Id != null and pc1Id > 0'>and categoryLevel1Id=#{pc1Id}</if>" +
                    "</trim>"
                +"order by creationDate DESC" +
            "</script>")
    @Results({
            @Result(column = "categoryLevel1Id",property = "pc1name",javaType = String.class,
                    one = @One(select = "cn.dsscm.mapper.ProductCategoryMapper.queryNameById")),
            @Result(column = "categoryLevel2Id",property = "pc2name",javaType = String.class,
                    one = @One(select = "cn.dsscm.mapper.ProductCategoryMapper.queryNameById")),
            @Result(column = "categoryLevel3Id",property = "pc3name",javaType = String.class,
                    one = @One(select = "cn.dsscm.mapper.ProductCategoryMapper.queryNameById"))
    })
    List<Product> findAll(@Param("name") String queryname,@Param("pc1Id") Integer categoryLevel1Id);

    /**
     * 插入商品并把递增的id传回product对象
     */
    @Insert("insert into tb_product(name,price,placement,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,isDelete,createdBy,creationDate)" +
            " values(#{name},#{price},#{placement},#{stock},#{categoryLevel1Id},#{categoryLevel2Id},#{categoryLevel3Id},0,#{createdBy},#{creationDate})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    void addProduct(Product product);

    /**
     * 基于参数修改商品信息
     */
    @Update("<script>" +
                "update tb_product" +
                    "<trim prefix='set ' suffixOverrides=',' suffix=' where id = #{id}'>"+
                    "<if test='name != null'>name=#{name},</if>" +
                    "<if test='description != null'>description=#{description},</if>" +
                    "<if test='price != null'>price=#{price},</if>" +
                    "<if test='placement != null'>placement=#{placement},</if>" +
                    "<if test='stock != null'>stock=#{stock},</if>" +
                    "<if test='categoryLevel1Id != null'>categoryLevel1Id=#{categoryLevel1Id},</if>" +
                    "<if test='categoryLevel2Id != null'>categoryLevel2Id=#{categoryLevel2Id},</if>" +
                    "<if test='categoryLevel3Id != null'>categoryLevel3Id=#{categoryLevel3Id},</if>" +
                    "<if test='fileName != null'>fileName=#{fileName},</if>" +
                    "<if test='isDelete != null'>isDelete=#{isDelete},</if>" +
                    "<if test='modifyBy != null'>modifyBy=#{modifyBy},</if>" +
                    "<if test='modifyDate != null'>modifyDate=#{modifyDate},</if>" +
                    "</trim>" +
            "</script>")
    void modifyProduct(Product product);


//    根据id查询product，但是会将分类的id转成分类名
    @Select("select tb_product.*,categoryLevel1Id categoryLevel1IdCopy from tb_product where id=#{id}")
    @Results({
            @Result(column = "categoryLevel1Id",property = "pc1name",javaType = String.class,
                    one = @One(select = "cn.dsscm.mapper.ProductCategoryMapper.queryNameById")),
            @Result(column = "categoryLevel2Id",property = "pc2name",javaType = String.class,
                    one = @One(select = "cn.dsscm.mapper.ProductCategoryMapper.queryNameById")),
            @Result(column = "categoryLevel3Id",property = "pc3name",javaType = String.class,
                    one = @One(select = "cn.dsscm.mapper.ProductCategoryMapper.queryNameById"))
    })
    Product getProductById(@Param("id") Integer id);

    //根据传过来的订单id查询对应的商品id，再查询商品信息
    @Select("select p.*,quantity from tb_product p,tb_order_detail od " +
            "where p.id in(select productId from tb_order_detail where orderId=#{oid}) and p.id=od.productId and od.orderId=#{oid}")
    @Result(column = "quantity",property = "quantity")
    List<Product> getProductByIds(@Param("oid") Integer id);


    @Delete("delete from tb_product where id=#{id}")
    int delProductById(@Param("id") Integer id);


}
