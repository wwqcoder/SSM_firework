package cn.wwq.service;

import cn.wwq.pojo.PageBean;
import cn.wwq.pojo.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {

    /**
     * 查询全部产品
     * @return
     */
    List<Product> findAll();

    /**
     * 保存产品
     * @param product
     */
    void save(Product product);

    /**
     * 根据ID查询产品信息
     * @param id
     * @return
     */
    Product findProductById(Integer id);

    /**
     * 修改产品信息
     * @param product
     */
    void update(Product product);

    /**
     * 通过ID删除产品信息
     * @param id
     */
    void deleById(Integer id);

    /**
     * 通过多个id删除产品
     * @param ids
     */
    void deleByIds(Integer[] ids);

    /**
     * 根据分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageBean<Product> findByPage(Integer pageNum, Integer pageSize);

    public void testPageHelper(Integer pageNum, Integer pageSize);

    /**
     * 根据分页助手查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Product> findByPageHelper(Integer pageNum, Integer pageSize);
}
