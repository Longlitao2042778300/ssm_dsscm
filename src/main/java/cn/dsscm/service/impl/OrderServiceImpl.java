package cn.dsscm.service.impl;

import cn.dsscm.domain.Order;
import cn.dsscm.mapper.OrderMapper;
import cn.dsscm.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Long
 * @create 2022-03-23 14:38
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Transactional
    public PageInfo<Order> getOrders(Integer pageIndex,Integer pageSize, String queryUserName, Integer status) {

        PageHelper.startPage(pageIndex,pageSize);

        List<Order> orders = orderMapper.getOrders(queryUserName, status);
        /*for (Order order : orders) {
            List<Product> products = order.getProducts();
            for (Product product : products) {
                product.setStock(orderMapper.getQuantity(order.getOid(),product.getId()));
            }
        }*/

        return new PageInfo<Order>(orders);

    }
}
