package cn.dsscm.service;

import cn.dsscm.domain.Bill;
import com.github.pagehelper.PageInfo;

/**
 * @author Long
 * @create 2022-03-22 13:24
 */
public interface BillService {
    PageInfo<Bill> getBills(Integer pageIndex, int pageSize, String queryProductName, Integer queryIsPayment, Integer queryProviderId);

    void addSave(Bill bill);

    Bill getBillById(Integer id);

    boolean delBillById(Integer id);

    void modifyBill(Bill bill);
}
