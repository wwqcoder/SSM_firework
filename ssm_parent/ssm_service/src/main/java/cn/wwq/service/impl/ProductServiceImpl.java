package cn.wwq.service.impl;

import cn.wwq.mapper.ProductMapper;
import cn.wwq.pojo.PageBean;
import cn.wwq.pojo.Product;
import cn.wwq.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        return productMapper.findAll();
    }

    @Override
    public void save(Product product) {
        productMapper.save(product);
    }

    @Override
    public Product findProductById(Integer id) {
        return productMapper.findProductById(id);
    }

    @Override
    public void update(Product product) {
        productMapper.update(product);
    }

    @Override
    public void deleById(Integer id) {
        productMapper.deleById(id);
    }

    @Override
    public void deleByIds(Integer[] ids) {
        for (Integer id : ids) {
            productMapper.deleById(id);
        }
    }

    @Override
    public PageBean<Product> findByPage(Integer pageNum, Integer pageSize) {

        PageBean<Product> pageBean = new PageBean<>();

        pageBean.setPageNum(pageNum);
        pageBean.setPageSize(pageSize);

        Integer totalCount = productMapper.findTotalCount();
        pageBean.setTotalCount(totalCount);

        pageBean.setTotalPage((int) Math.ceil(totalCount*1.0/pageSize));

        Integer startIndex = (pageNum - 1) *pageSize;
        List<Product> products = productMapper.findByPage(startIndex,pageSize);
        pageBean.setPageList(products);
        return pageBean;
    }

    @Override
    public void testPageHelper(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> all = productMapper.findAll();
        for (Product product : all) {
            System.out.println(product);
        }

        PageInfo<Product> pageInfo = new PageInfo<>(all,3);

        System.out.println("每页条数："+pageInfo.getPageSize());
        System.out.println("当前页："+pageInfo.getPageNum());
        System.out.println("总条数："+pageInfo.getTotal());
        System.out.println("总页数："+pageInfo.getPages());
        System.out.println("数据："+pageInfo.getList().size());
        System.out.println("上一页："+pageInfo.getPrePage());
        System.out.println("下一页："+pageInfo.getNextPage());

    }

    @Override
    public PageInfo<Product> findByPageHelper(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        List<Product> productList = productMapper.findAll();

        PageInfo<Product> pageInfo = new PageInfo<>(productList,3);

        return pageInfo;
    }
}
