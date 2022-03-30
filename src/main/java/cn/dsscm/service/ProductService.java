package cn.dsscm.service;

import cn.dsscm.domain.Product;
import com.github.pagehelper.PageInfo;

/**
 * @author Long
 * @create 2022-03-16 16:05
 */

public interface ProductService {

    /**
     *     根据分页、搜索的条件查询product
     */
    PageInfo<Product> findProducts(Integer pageIndex,Integer pageSize,String queryname,Integer categoryLevel1Id);

    /**
     * 添加商品，
     */
    void addProduct(Product product);

    /**
     * 根据id查询product，会将分类的id转成分类名
     */
    Product getProductById(Integer id);

    boolean delProduct(Integer id);

    void updateProduct(Product product);
}
