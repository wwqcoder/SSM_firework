package cn.wwq.mapper;

import cn.wwq.pojo.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> findAll();

    void save(Product product);

    Product findProductById(Integer id);

    void update(Product product);

    void deleById(Integer id);

    /**
     * 查询总条数
     * @return
     */
    Integer findTotalCount();

    /**
     * 通过起始索引和每页显示条数查询分页
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<Product> findByPage(Integer startIndex, Integer pageSize);
}
