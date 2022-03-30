package cn.dsscm.service.impl;

import cn.dsscm.domain.ProductCategory;
import cn.dsscm.mapper.ProductCategoryMapper;
import cn.dsscm.service.ProductCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("productCategoryService")
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    /**
     * 根据传过来的参数分辨要查询的是第几类商品 eg.1为一级分类名称
     */
    public List<ProductCategory> findProductCategoriesByType(Integer type) {
        return productCategoryMapper.findProductCategoriesByType(type);
    }

    /**
     * 根据一级分类返回二级，根据二级返回三级分类
     */
    public List<ProductCategory> findProductCategoriesByParentId(Integer parentId) {
        return productCategoryMapper.findProductCategoriesByParentId(parentId);
    }
}
