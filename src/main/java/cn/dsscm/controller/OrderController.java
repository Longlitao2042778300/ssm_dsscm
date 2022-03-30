package cn.dsscm.controller;

import cn.dsscm.domain.Order;
import cn.dsscm.service.OrderService;
import cn.dsscm.utils.Constants;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @author Long
 * @create 2022-03-22 21:27
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @RequestMapping("/list")
    public String getOrders(
            @RequestParam(value = "queryName",required = false) String queryUserName,
            @RequestParam(value = "pageIndex",required = false) Integer pageIndex,
            @RequestParam(value = "status",required = false) Integer status,
            Model model){

        // 设置页面容量
        int pageSize = Constants.pageSize;
        // 页码为空默认分第一页
        if (null == pageIndex) {
            pageIndex = 1;
        }
        PageInfo<Order> orderPageInfo = orderService.getOrders(pageIndex,pageSize,queryUserName, status);
        String[] statusList = {"请选择","待审核","审核通过","配货","卖家已发货","已收货"};
        model.addAttribute("pi", orderPageInfo);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("status", status);
        model.addAttribute("statusList", statusList);
        return "orderlist";
    }

}
