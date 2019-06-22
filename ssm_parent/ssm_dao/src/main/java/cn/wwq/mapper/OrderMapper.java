package cn.wwq.mapper;

import cn.wwq.pojo.Order;

import java.util.List;

public interface OrderMapper {

    List<Order> findAll();

    void save(Order order);

    Order findOrderById(Integer id);

    void update(Order order);

    void dele(Integer id);

}
