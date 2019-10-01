package wang.ismy.leyou.order.service;

import ch.qos.logback.classic.Logger;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wang.ismy.common.dto.CartDTO;
import wang.ismy.common.enums.ExceptionEnum;
import wang.ismy.common.exception.Assertion;
import wang.ismy.common.exception.BusinessException;
import wang.ismy.common.utils.IdWorker;
import wang.ismy.leyou.auth.entity.UserInfo;
import wang.ismy.leyou.order.client.AddressClient;
import wang.ismy.leyou.order.client.GoodsClient;
import wang.ismy.leyou.order.dto.AddressDTO;

import wang.ismy.leyou.order.dto.OrderDTO;
import wang.ismy.leyou.order.enums.OrderStatusEnum;
import wang.ismy.leyou.order.interceptor.UserInterceptor;
import wang.ismy.leyou.order.mapper.OrderDetailMapper;
import wang.ismy.leyou.order.mapper.OrderMapper;
import wang.ismy.leyou.order.mapper.OrderStatusMapper;
import wang.ismy.leyou.order.pojo.Order;
import wang.ismy.leyou.order.pojo.OrderDetail;
import wang.ismy.leyou.order.pojo.OrderStatus;
import wang.ismy.pojo.entity.Sku;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/9/30 20:13
 */
@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private OrderMapper orderMapper;

    private OrderDetailMapper orderDetailMapper;

    private OrderStatusMapper orderStatusMapper;

    private IdWorker idWorker;

    private AddressClient addressClient;

    private GoodsClient goodsClient;

    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(OrderDTO dto) {

        // 订单
        Order order = new Order();
        // 订单编号基本信息
        order.setOrderId(idWorker.nextId());
        order.setCreateTime(LocalDateTime.now());
        order.setPaymentType(dto.getPaymentType());

        // 用户信息
        UserInfo userInfo = UserInterceptor.getUserInfo();
        order.setUserId(userInfo.getId());
        order.setBuyerNick(userInfo.getUsername());
        order.setBuyerRate(false);
        // 收货人地址
        AddressDTO address = addressClient.find(dto.getAddressId());
        order.setReceiver(address.getName());
        order.setReceiverAddress(address.getAddress());
        order.setReceiverCity(address.getCity());
        order.setReceiverDistrict(address.getDistrict());
        order.setReceiverState(address.getState());
        order.setReceiverZip(address.getZipCode());
        order.setReceiverMobile(address.getPhone());
        // 金额

        Map<Long, Integer> cartMap = dto.getCarts().stream().collect(Collectors.toMap(CartDTO::getSkuId, CartDTO::getNum));

        List<Sku> skus = goodsClient.querySkuListByIdList(new ArrayList<>(cartMap.keySet()));

        long price = 0L;

        List<OrderDetail> detailList = new ArrayList<>();
        for (Sku sku : skus) {
            Integer num = cartMap.get(sku.getId());
            price += sku.getPrice() * num;
            // 订单详情
            OrderDetail detail = new OrderDetail();
            detail.setImage(StringUtils.substringBefore(sku.getImages(),","));
            detail.setSkuId(sku.getId());
            detail.setNum(num);
            detail.setTitle(sku.getTitle());
            detail.setPrice(sku.getPrice());
            detail.setOwnSpec(sku.getOwnSpec());

            detailList.add(detail);

        }
        order.setTotalPay(price);
        order.setActualPay(price+order.getPostFee());

        int count = orderMapper.insertSelective(order);
        if (count != 1){
            log.error("创建订单失败:{}",order);
            throw new BusinessException(ExceptionEnum.SERVER_ERROR);
        }

        detailList.forEach(d->d.setOrderId(order.getOrderId()));

        count = orderDetailMapper.insertList(detailList);
        if (count != detailList.size()){
            log.error("创建订单失败:{}",order);
            throw new BusinessException(ExceptionEnum.SERVER_ERROR);
        }

        // 订单状态
        OrderStatus status = new OrderStatus();
        status.setCreateTime(LocalDateTime.now());
        status.setOrderId(order.getOrderId());
        status.setStatus(OrderStatusEnum.UN_PAY.getCode());
        count = orderStatusMapper.insertSelective(status);
        if (count != 1){
            log.error("创建订单失败:{}",order);
            throw new BusinessException(ExceptionEnum.SERVER_ERROR);
        }

        // 库存操作
        goodsClient.decreaseStock(dto.getCarts());
        return order.getOrderId();
    }

    public Order queryOrderById(Long id) {

        Order order = orderMapper.selectByPrimaryKey(id);
        Assertion.assertNotNull(order);

        OrderDetail detail = new OrderDetail();
        detail.setOrderId(order.getOrderId());
        List<OrderDetail> detailList = orderDetailMapper.select(detail);

        Assertion.assertNotEmpty(detailList);
        order.setOrderDetails(detailList);

        OrderStatus status = orderStatusMapper.selectByPrimaryKey(order.getOrderId());
        order.setStatus(status);

        return order;
    }
}
