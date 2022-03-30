package cn.dsscm.service;

import cn.dsscm.domain.Order;
import com.github.pagehelper.PageInfo;

/**
 * @author Long
 * @create 2022-03-23 14:37
 */
public interface OrderService {
    PageInfo<Order> getOrders(Integer pageIndex,Integer pageSize, String queryUserName, Integer status);
}
