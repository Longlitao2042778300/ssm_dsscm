package cn.dsscm.service.impl;

import cn.dsscm.domain.Provider;
import cn.dsscm.mapper.ProviderMapper;
import cn.dsscm.service.ProviderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Long
 * @create 2022-03-22 13:53
 */
@Service("providerService")
public class ProviderServiceImpl implements ProviderService {

    @Resource
    private ProviderMapper providerMapper;

    public List<Provider> getProviders() {
        return providerMapper.findProviders(null,null);
    }

    public PageInfo<Provider> getProviders(Integer pageIndex, Integer pageSize, String queryProCode, String queryProName) {
        //开启分页
        PageHelper.startPage(pageIndex,pageSize);
        return new PageInfo<Provider>(providerMapper.findProviders(queryProCode,queryProName));
    }

    public boolean delUser(Integer id) {
        return providerMapper.delProvider(id) > 0;
    }

    public Provider getProviderById(Integer id) {
        return providerMapper.findProviderById(id);
    }

    public void modifyProvider(Provider provider) {
        providerMapper.modifyProvider(provider);
    }

    public void addProvider(Provider provider) {
        providerMapper.addProvider(provider);
    }
}
