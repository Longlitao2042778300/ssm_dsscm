package cn.dsscm.controller;

import cn.dsscm.domain.Provider;
import cn.dsscm.domain.User;
import cn.dsscm.service.ProviderService;
import cn.dsscm.utils.Constants;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Long
 * @create 2022-03-24 11:21
 */
@Controller
@RequestMapping("/provider")
public class ProviderController {

    @Resource
    private ProviderService providerService;

    @RequestMapping("list")
    public String getProviders(
            Model model,
            @RequestParam(value = "queryProCode",required = false) String queryProCode,
            @RequestParam(value = "queryProName",required = false) String queryProName,
            @RequestParam(value = "pageIndex",required = false) Integer pageIndex){

        int pageSize = Constants.pageSize;
        if (pageIndex == null){
            pageIndex=1;
        }
        PageInfo<Provider> providerPageInfo = providerService.getProviders(pageIndex,pageSize,queryProCode,queryProName);

        model.addAttribute("queryProCode",queryProCode);
        model.addAttribute("queryProName",queryProName);
        model.addAttribute("pi",providerPageInfo);
        return "providerlist";
    }

    @RequestMapping("/addSave")
    public String addSave(
            Provider provider,
            MultipartFile[] attaches,
            HttpSession session,
            HttpServletRequest request){

        boolean flag = true;
        String imgPath = null;
        if (attaches != null){
            for(int i = 0; i < attaches.length;i++){

                MultipartFile file = attaches[i];
                //假设只传来单个图片
                if (file.isEmpty()){
                    continue;
                }
                //获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                //获取文件的扩展名
                String suffix = FilenameUtils.getExtension(originalFilename);

                if (file.getSize() > Constants.fileSize) {// 上传大小不得超过 500k
                    request.setAttribute("errorInfo", " * 上传大小不得超过 500k");
                    flag = false;
                } else if ("jpg".equalsIgnoreCase(suffix)
                        || "png".equalsIgnoreCase(suffix)
                        || "jpeg".equalsIgnoreCase(suffix)
                        || "pneg".equalsIgnoreCase(suffix)) {// 上传图片格式是否正确

                    //设置上传文件的文件名
                    imgPath = UUID.randomUUID().toString() + "provider_img." + suffix;
                    try {
                        file.transferTo(new File(Constants.dirPath + imgPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute("errorInfo", " * 上传失败！");
                        flag = false;
                    }

                } else {// 上传图片格式不正确时
                    request.setAttribute("errorInfo", " * 上传图片格式不正确,仅支持jpg、jpeg、png、pneg格式");
                    flag = false;
                }
                if (i==0){//上传的  企业营业执照
                    provider.setCompanyLicPicPath(imgPath);
                }else if (i == 1){//上传的  组织机构代码证
                    provider.setOrgCodePicPath(imgPath);
                }
            }
        }

        if (flag){
            //设置修改时间和修改者
            provider.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
            provider.setCreationDate(new Date());
            try {
                providerService.addProvider(provider);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorInfo", " * 上传失败！");
                return "provideradd";
            }
            return "redirect:/provider/list";
        }
        return "provideradd";

    }

    /**
     * 根据供应商id查询供应商信息
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Integer id, Model model) {

        Provider provider = providerService.getProviderById(id);

        if (!StringUtils.isNullOrEmpty(provider.getCompanyLicPicPath())) {
            //  /upload为一个虚拟路径
            provider.setCompanyLicPicPath("/upload/" + provider.getCompanyLicPicPath());
        }

        if (!StringUtils.isNullOrEmpty(provider.getOrgCodePicPath())) {
            //  /upload为一个虚拟路径
            provider.setOrgCodePicPath("/upload/" + provider.getOrgCodePicPath());
        }

        model.addAttribute(provider);
        return "providerview";
    }

    //在修改供应商前，先查询供应商信息
    @RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
    public String modify(@PathVariable Integer id, Model model) {

        Provider provider = providerService.getProviderById(id);

        if (!StringUtils.isNullOrEmpty(provider.getCompanyLicPicPath())) {
            //  /upload为一个虚拟路径
            provider.setCompanyLicPicPath("/upload/" + provider.getCompanyLicPicPath());
        }

        if (!StringUtils.isNullOrEmpty(provider.getOrgCodePicPath())) {
            //  /upload为一个虚拟路径
            provider.setOrgCodePicPath("/upload/" + provider.getOrgCodePicPath());
        }

        model.addAttribute(provider);
        return "providermodify";
    }

    //修改保存供应商信息
    @RequestMapping(value = "/modifySave",method = RequestMethod.POST)
    public String modifySave(
            Provider provider,
            MultipartFile[] attaches,
            HttpSession session,
            HttpServletRequest request){

        boolean flag = true;
        String imgPath = null;
        if (attaches != null){
            for(int i = 0; i < attaches.length;i++){

                MultipartFile file = attaches[i];
                //假设只传来单个图片
                if (file.isEmpty()){
                    continue;
                }
                //获取上传文件的原始名称
                String originalFilename = file.getOriginalFilename();
                //获取文件的扩展名
                String suffix = FilenameUtils.getExtension(originalFilename);

                if (file.getSize() > Constants.fileSize) {// 上传大小不得超过 500k
                    request.setAttribute("errorInfo", " * 上传大小不得超过 500k");
                    flag = false;
                } else if ("jpg".equalsIgnoreCase(suffix)
                        || "png".equalsIgnoreCase(suffix)
                        || "jpeg".equalsIgnoreCase(suffix)
                        || "pneg".equalsIgnoreCase(suffix)) {// 上传图片格式是否正确

                    //设置上传文件的文件名
                    imgPath = UUID.randomUUID().toString() + "provider_img." + suffix;
                    try {
                        file.transferTo(new File(Constants.dirPath + imgPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute("errorInfo", " * 上传失败！");
                        flag = false;
                    }

                } else {// 上传图片格式不正确时
                    request.setAttribute("errorInfo", " * 上传图片格式不正确,仅支持jpg、jpeg、png、pneg格式");
                    flag = false;
                }
                if (i==0){//上传的  企业营业执照
                    provider.setCompanyLicPicPath(imgPath);
                }else if (i == 1){//上传的  组织机构代码证
                    provider.setOrgCodePicPath(imgPath);
                }
            }
        }

        if (flag){
            //设置修改时间和修改者
            provider.setModifyBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
            provider.setModifyDate(new Date());
            try {
                providerService.modifyProvider(provider);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorInfo", " * 上传失败！");
                return "providermodify";
            }
            return "redirect:/provider/list";
        }
        return "providermodify";

    }





    /**
     * ajax验证删除是否成功，
     */
    @RequestMapping(value = "/del.json",method = RequestMethod.GET)
    @ResponseBody
    public Object delUser(@RequestParam("proId") Integer id){
        Map<String, String> resultMap = new HashMap<String, String>();

        if (StringUtils.isNullOrEmpty(String.valueOf(id))){//先查询用户是否不存在
            resultMap.put("delResult","not exist");
        }else {
            //返回是否删除成功
            boolean flag = providerService.delUser(id);
            if (flag){
                resultMap.put("delResult","true");
            }else {
                resultMap.put("delResult","false");
            }
        }

        return JSONArray.toJSONString(resultMap);
    }

}
