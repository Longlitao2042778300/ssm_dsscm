package cn.dsscm.controller;

import cn.dsscm.domain.Bill;
import cn.dsscm.domain.Provider;
import cn.dsscm.domain.User;
import cn.dsscm.service.BillService;
import cn.dsscm.service.ProviderService;
import cn.dsscm.utils.Constants;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Long
 * @create 2022-03-22 13:04
 */
@Controller
@RequestMapping("/bill")
public class BillController {

    @Resource
    private BillService billService;
    @Resource
    private ProviderService providerService;


    @RequestMapping("/list")
    public String getBillList(
            @RequestParam(value = "queryProductName", required = false) String queryProductName,
            @RequestParam(value = "queryProviderId", required = false) Integer queryProviderId,
            @RequestParam(value = "queryIsPayment", required = false) Integer queryIsPayment,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex,
            Model model) {

        //设置页面容量和当前页码
        int pageSize = Constants.pageSize;
        if (pageIndex == null) {
            pageIndex = 1;
        }

        //采购订单的分页数据
        PageInfo<Bill> billPageInfo = billService.getBills(pageIndex, pageSize, queryProductName, queryIsPayment, queryProviderId);

        //为查询供应商是准备数据
        List<Provider> providerList = providerService.getProviders();

        //设置页面回显的数据
        model.addAttribute("pi", billPageInfo);
        model.addAttribute("providerList", providerList);
        model.addAttribute("queryProductName", queryProductName);
        model.addAttribute("queryProviderId", queryProviderId);
        model.addAttribute("queryIsPayment", queryIsPayment);
        return "billlist";
    }

    //为添加采购订单的页面加载供应商数据信息
    @RequestMapping(value = "/providerList.json", method = RequestMethod.GET)
    @ResponseBody
    public List<Provider> getProviderList() {
        return providerService.getProviders();
    }

    //添加采购订单
    @RequestMapping("/addSave")
    public String addSave(
            Bill bill,
            HttpSession session) {

        bill.setCreatedBy(((User) (session.getAttribute(Constants.USER_SESSION))).getId());
        bill.setCreationDate(new Date());

        billService.addSave(bill);
        return "redirect:/bill/list";
    }

    //根据id删除采购订单
    @RequestMapping(value = "/delBill.json", method = RequestMethod.GET)
    @ResponseBody
    public String delBill(@RequestParam("id") Integer id) {
        Map<String, String> resultMap = new HashMap<String, String>();

        if (billService.getBillById(id) != null) {
            if (billService.delBillById(id)) {//删除成功
                resultMap.put("delResult","true");
            }else {//删除失败
                resultMap.put("delResult","false");
            }
        } else {
            resultMap.put("delResult", "not exist");//要删除的采购订单不存在时
        }

        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping(value = "/modify/{id}",method = RequestMethod.GET)
    public String modifyById(@PathVariable("id") Integer id,Model model){
        model.addAttribute("bill",billService.getBillById(id));
        return "billmodify";
    }

    //修改保存
    @RequestMapping(value = "/modifySave",method = RequestMethod.POST)
    public String modifySave(Bill bill,HttpSession session){
        bill.setModifyBy(((User) (session.getAttribute(Constants.USER_SESSION))).getId());
        bill.setModifyDate(new Date());

        billService.modifyBill(bill);
        return "redirect:/bill/list";
    }


    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    public String viewById(@PathVariable("id") Integer id,Model model){

//        System.out.println(billService.getBillById(id));

        model.addAttribute("bill",billService.getBillById(id));
        return "billview";
    }
}
