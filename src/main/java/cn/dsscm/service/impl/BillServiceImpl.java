package cn.dsscm.service.impl;

import cn.dsscm.domain.Bill;
import cn.dsscm.mapper.BillMapper;
import cn.dsscm.service.BillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Long
 * @create 2022-03-22 13:31
 */
@Service("billService")
public class BillServiceImpl implements BillService {

    @Resource
    private BillMapper billMapper;

    @Transactional
    public PageInfo<Bill> getBills(Integer pageIndex, int pageSize, String queryProductName, Integer queryIsPayment, Integer queryProviderId) {
        //开启分页
        PageHelper.startPage(pageIndex,pageSize);

        List<Bill> bills = billMapper.findBills(queryProductName,queryIsPayment,queryProviderId);

        return new PageInfo<Bill>(bills);

    }

    public void addSave(Bill bill) {
        billMapper.addSave(bill);
    }

    public Bill getBillById(Integer id) {
        return billMapper.findBillById(id);
    }

    public boolean delBillById(Integer id) {
        return billMapper.delBill(id) > 0;
    }

    public void modifyBill(Bill bill) {
        billMapper.modifyBill(bill);
    }
}
