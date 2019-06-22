package cn.wwq.service.impl;

import cn.wwq.mapper.OrderMapper;
import cn.wwq.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public void save(Order order) {
        orderMapper.save(order);
    }

    @Override
    public Order findOrderById(Integer id) {
        return orderMapper.findOrderById(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.update(order);
    }

    @Override
    public void dele(Integer id) {
        orderMapper.dele(id);
    }

    @Override
    public void deleByIds(Integer[] ids) {
        for (Integer id : ids) {
            orderMapper.dele(id);
        }
    }
}
