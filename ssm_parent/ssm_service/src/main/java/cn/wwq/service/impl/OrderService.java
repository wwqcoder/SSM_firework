package cn.wwq.service.impl;

import cn.wwq.pojo.Order;

import java.util.List;

public interface OrderService {

    /**
     * 查询所有订单信息
     * @return
     */
    List<Order> findAll();

    /**
     * 保存订单
     * @param order
     */
    void save(Order order);

    /**
     * 通过ID查询订单信息
     * @param id
     * @return
     */
    Order findOrderById(Integer id);

    /**
     * 修改订单信息
     * @param order
     */
    void update(Order order);

    /**
     * 删除单个订单
     * @param id
     */
    void dele(Integer id);

    /**
     * 批量删除订单
     * @param ids
     */
    void deleByIds(Integer[] ids);
}
