package cn.dsscm.service;

import cn.dsscm.domain.Provider;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author Long
 * @create 2022-03-22 13:25
 */
public interface ProviderService {

    List<Provider> getProviders();

    PageInfo<Provider> getProviders(Integer pageIndex,Integer pageSize,String queryProCode,String queryProName);

    boolean delUser(Integer id);

    Provider getProviderById(Integer id);

    void modifyProvider(Provider provider);

    void addProvider(Provider provider);
}
