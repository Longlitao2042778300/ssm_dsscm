package cn.dsscm.controller;

import cn.dsscm.domain.Product;
import cn.dsscm.domain.ProductCategory;
import cn.dsscm.domain.User;
import cn.dsscm.service.ProductCategoryService;
import cn.dsscm.service.ProductService;
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
import java.util.*;

/**
 * @author Long
 * @create 2022-03-16 15:54
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    @Resource
    private ProductService productService;
    @Resource
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/list")
    public String list(
            Model model,
            @RequestParam(value = "queryname", required = false) String queryname,
            @RequestParam(value = "categoryLevel1Id", required = false) Integer categoryLevel1Id,
            @RequestParam(value = "pageIndex", required = false) Integer pageIndex) {

        //设置当前页
        if (pageIndex == null) {
            pageIndex = 1;
        }
        //设置页面容量
        Integer pageSize = Constants.pageSize;

        //根据分页、搜索的条件查询product
        PageInfo<Product> productPageInfo = productService.findProducts(pageIndex, pageSize, queryname, categoryLevel1Id);

        //查出所有一级分类名称
        List<ProductCategory> pcList = productCategoryService.findProductCategoriesByType(1);


        //product的分页信息
        model.addAttribute("pi", productPageInfo);
        //一级分类名称
        model.addAttribute("pcList", pcList);
        //搜索的条件
        model.addAttribute("queryName", queryname);
        model.addAttribute("categoryLevel1Id", categoryLevel1Id);
        return "productlist";
    }

    @RequestMapping("/pcList.json")
    @ResponseBody
    public List<ProductCategory> getProductCategoryList(@RequestParam("parentId") Integer parentId) {
        return productCategoryService.findProductCategoriesByParentId(parentId);
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(
            Product product,
            HttpServletRequest request,
            HttpSession session,
            @RequestParam(value = "attaches", required = false) List<MultipartFile> attaches
    ) {

        //用来判断上传文件是否成功
        boolean flag = true;
        //图片上传后保存的位置
        String imgPath = null;

        if (!attaches.isEmpty()) {

            for (MultipartFile file : attaches) {

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
                    imgPath = UUID.randomUUID().toString() + "product_img." + suffix;
                    try {
                        file.transferTo(new File(Constants.dirPath + imgPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute("errorInfo", " * 上传失败！");
                        flag = false;
                    }
                }else {
                    request.setAttribute("errorInfo", " * 上传图片格式不正确,仅支持jpg、jpeg、png、pneg格式！");
                    flag = false;
                }

            }
        }
        if (flag) {
            product.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
            product.setCreationDate(new Date());
            product.setFileName(imgPath);

            try {
                productService.addProduct(product);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorInfo", " * 上传失败！");
                return "productadd";
            }
            return "redirect:/product/list";
        }
        return "productadd";
    }


    @RequestMapping(value = "/delProduct.json",method = RequestMethod.GET)
    @ResponseBody
    public Object delProductById(@RequestParam("id") Integer id){
        Map<String, String> resultMap = new HashMap<String, String>();

        if (productService.getProductById(id) == null){//先查询商品是否不存在
            resultMap.put("delResult","not exist");
        }else {
            //返回是否删除成功
            boolean flag = productService.delProduct(id);
            if (flag){
                resultMap.put("delResult","true");
            }else {
                resultMap.put("delResult","false");
            }
        }

        return JSONArray.toJSONString(resultMap);
    }

    /**
     * 做用户修改前，先将要修改的用户信息查询出来
     */
    @RequestMapping("/modify/{id}")
    public String modify(@PathVariable Integer id, Model model, HttpServletRequest request) {

        //根据id查询product，但是不将分类的id转成分类名
        Product product = productService.getProductById(id);

//        System.out.println(product);测试一级分类的id

        if (!StringUtils.isNullOrEmpty(product.getFileName())) {
            //  /upload为一个虚拟路径
            product.setFileName(Constants.dirPath + product.getFileName());
        }
        model.addAttribute(product);
        return "productmodify";
    }

    /**
     * 保存修改
     */
    @RequestMapping(value = "/modifySave",method = RequestMethod.POST)
    public String modifySave(
            Product product,
            @RequestParam(value = "attaches",required = false) List<MultipartFile> attaches,
            HttpServletRequest request,
            HttpSession session){

        //用来判断上传文件是否成功
        boolean flag = true;
        //图片上传后保存的位置
        String imgPath = null;

        if (!attaches.isEmpty()) {

            for (MultipartFile file : attaches) {

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
                    imgPath = UUID.randomUUID().toString() + "product_img." + suffix;
                    try {
                        file.transferTo(new File(Constants.dirPath + imgPath));
                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute("errorInfo", " * 上传失败！");
                        flag = false;
                    }
                }else {
                    request.setAttribute("errorInfo", " * 上传图片格式不正确,仅支持jpg、jpeg、png、pneg格式！");
                    flag = false;
                }
            }
        }
        if (flag) {
            product.setModifyBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
            product.setModifyDate(new Date());
            product.setFileName(imgPath);

            try {
                productService.updateProduct(product);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorInfo", " * 上传出错！");
                return "productmodify";
            }
            return "redirect:/product/list";
        }
        return "productmodify";

    }

    /**
     * 根据商品id查询商品信息
     */
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Integer id, Model model, HttpServletRequest request) {

        Product product = productService.getProductById(id);

        if (!StringUtils.isNullOrEmpty(product.getFileName())) {
            //  /upload为一个虚拟路径
            product.setFileName("/upload/" + product.getFileName());
        }
        model.addAttribute(product);
        return "productview";
    }

}
