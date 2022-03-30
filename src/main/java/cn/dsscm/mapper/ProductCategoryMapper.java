package cn.dsscm.mapper;

import cn.dsscm.domain.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-20 16:08
 */
@Repository("productCategoryMapper")
public interface ProductCategoryMapper {

    /**
     * 根据传过来的参数分辨要查询的是第几类商品 eg.1为一级分类名称
     */
    @Select("select * from tb_product_category where type=#{type}")
    List<ProductCategory> findProductCategoriesByType(@Param("type") Integer type);

    /**
     * 根据id查分类名
     */
    @Select("select name from tb_product_category where id=#{id}")
    String queryNameById(@Param("id") Integer id);

    /**
     * 根据上一级分类查对应的下一级分类
     */
    @Select("select * from tb_product_category where parentId=#{parentId}")
    List<ProductCategory> findProductCategoriesByParentId(@Param("parentId") Integer parentId);
}
