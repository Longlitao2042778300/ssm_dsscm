package cn.dsscm.service;

import cn.dsscm.domain.ProductCategory;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-20 15:51
 */

public interface ProductCategoryService {

    /**
     * 根据传过来的参数分辨要查询的是第几类商品 eg.1为一级分类名称
     */
    List<ProductCategory> findProductCategoriesByType(Integer type);

    /**
     * 根据一级分类返回二级，根据二级返回三级分类
     */
    List<ProductCategory> findProductCategoriesByParentId(Integer parentId);
}
