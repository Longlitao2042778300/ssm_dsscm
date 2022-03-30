package cn.dsscm.service.impl;

import cn.dsscm.domain.Product;
import cn.dsscm.mapper.ProductMapper;
import cn.dsscm.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Long
 * @create 2022-03-16 16:06
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;


    /**
     *  分页查询product。有可能需要根据 商品名模糊查询和一级分类名称进行查找
     */
    @Transactional
    public PageInfo<Product> findProducts(Integer pageIndex, Integer pageSize, String queryname, Integer categoryLevel1Id) {
        //开启分页
        PageHelper.startPage(pageIndex,pageSize);

        List<Product> products = productMapper.findAll(queryname,categoryLevel1Id);

        return new PageInfo<Product>(products);
    }

    /**
     * 添加商品，参数可以为空.这里我们采用前端页面必须填写的进行插入操作，选填的内容通过修改来操作
     */
    @Transactional
    public void addProduct(Product product) {
        productMapper.addProduct(product);

        productMapper.modifyProduct(product);
    }

    /**
     * 根据id查询对应的商品信息，不将分类id转分类名
     */
    public Product getProductById(Integer id) {
        return productMapper.getProductById(id);
    }

    /**
     * 根据id删除商品，返回删除是否成功
     */
    public boolean delProduct(Integer id) {
        return productMapper.delProductById(id) > 0;
    }

    public void updateProduct(Product product) {
        productMapper.modifyProduct(product);
    }

}
