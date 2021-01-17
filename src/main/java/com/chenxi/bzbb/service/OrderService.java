package com.chenxi.bzbb.service;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.chenxi.bzbb.domain.dao.*;
import com.chenxi.bzbb.domain.model.*;
import com.chenxi.bzbb.domain.model.RequestModel.CreateOrderRequestModel;
import com.chenxi.bzbb.utils.AjaxResult;
import com.chenxi.bzbb.utils.AlipayUtils;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    AlipayClient alipayClient;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    ShippingaddressMapper shippingaddressMapper;

    @Autowired
    GoodService goodService;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    FastmailMapper fastmailMapper;

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    UserCouponMapper userCouponMapper;

    @Autowired
    MyCouponMapper myCouponMapper;

    @Autowired
    OrderDisplayMapper orderDisplayMapper;

    @Transactional
    public AjaxResult createOrder(User user, CreateOrderRequestModel requestModel) throws AlipayApiException {
        Order order = new Order();
        BigDecimal total = new BigDecimal(0.00);
        //未处理的订单不能超过十个
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUidEqualTo(user.getId());
        criteria.andStatusEqualTo(0);
        int count = orderMapper.countByExample(orderExample);
        if (count > 9){
            return AjaxResult.me().setSuccess(false).setMsg("未支付订单不能超过10个");
        }


        //查询商品sp的价格
        List<Long> ids = new ArrayList<>();
        List<OrderInfo> orderInfos = requestModel.getOrderInfos();
        for (OrderInfo orderInfo:orderInfos){
            ids.add(orderInfo.getId());
        }
        List<Goodspecifications> goodspecifications = goodService.queryGoodspecificationsByIds(ids);
        if (goodspecifications.isEmpty()){
            return AjaxResult.me().setSuccess(false).setMsg("参数异常");
        }
        Map<Long,BigDecimal> priceMap = new HashMap<>();
        for (Goodspecifications gsp:goodspecifications){
            priceMap.put(gsp.getId(),gsp.getPrice());
        }
        List<OrderInfo> res = new ArrayList<>();
        for (OrderInfo orderInfo:orderInfos){
            Long id = orderInfo.getId();
            if (priceMap.containsKey(id)){
                orderInfo.setPrice(priceMap.get(id));
                total = total.add(orderInfo.getPrice().multiply(new BigDecimal(orderInfo.getCount())));
                res.add(orderInfo);
            }
        }
        //查询优惠券
        if (requestModel.getCoupon()!=null){
            UserCoupon userCoupon = userCouponMapper.selectByPrimaryKey(requestModel.getCoupon());
            //判断该是否有此优惠券记录是否属于该用户
            if (userCoupon!=null&& userCoupon.getUid().equals(user.getId())&&userCoupon.getStatus().equals(0)){
                Coupon coupon = couponMapper.selectByPrimaryKey(userCoupon.getCid());
                //判断这些商品总价是否满足优惠券条件
                if (total.compareTo(coupon.getRequirement())>=0){
                    //如果商品总价小于或者等于优惠券减免的价格 直接设置为0
                    if (total.compareTo(coupon.getReduction())<=0){
                        total = BigDecimal.ZERO;
                    }else {
                        //反之正常减免
                        total = total.subtract(coupon.getReduction());
                    }
                    userCoupon.setStatus(1);
                    userCouponMapper.updateByPrimaryKeySelective(userCoupon);
                    order.setCoupon(userCoupon.getId());
                }
            }
        }

        //查询快递费
        Fastmail fastmail = fastmailMapper.selectByPrimaryKey(requestModel.getFastMail());
        if (fastmail==null){
            return AjaxResult.me().setSuccess(false).setMsg("参数异常");
        }
        total = total.add(fastmail.getPrice());

        Date date = new Date();
        order.setCreatetime(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderNum = simpleDateFormat.format(date);
        order.setId(orderNum);
        order.setTotal(total);
        order.setUid(user.getId());
        order.setGoods(JSON.toJSONString(res));
        order.setAddress(requestModel.getAddressId());
        order.setFastmail(requestModel.getFastMail());
        orderMapper.insertSelective(order);
        return AjaxResult.me().setObject(AlipayUtils.createTradeAppPay(alipayClient,orderNum,total));
    }

    public List<Shippingaddress> queryUserAddress(User user, boolean def) {

        ShippingaddressExample shippingaddressExample = new ShippingaddressExample();
        ShippingaddressExample.Criteria criteria = shippingaddressExample.createCriteria();
        criteria.andUidEqualTo(user.getId());
        if (def){
            criteria.andDefEqualTo(1);
        }
        return shippingaddressMapper.selectByExample(shippingaddressExample);
    }

    public boolean createUserAddress(User user, Shippingaddress shippingaddress) {
        ShippingaddressExample shippingaddressExample = new ShippingaddressExample();
        ShippingaddressExample.Criteria criteria = shippingaddressExample.createCriteria();
        criteria.andUidEqualTo(user.getId());
        int count = shippingaddressMapper.countByExample(shippingaddressExample);
        if (count > 9){
            return false;
        }
        if (count == 0){
            shippingaddress.setDef(1);
        }else {
            if (shippingaddress.getDef() == 1){
                changeAddressDef(user.getId());
            }
        }
        shippingaddress.setUid(user.getId());
        shippingaddress.setCreatetime(new Date());
        shippingaddressMapper.insertSelective(shippingaddress);
        return true;
    }

    public boolean updateUserAddress(User user, Shippingaddress shippingaddress) {
        Shippingaddress shippingaddress1 = shippingaddressMapper.selectByPrimaryKey(shippingaddress.getId());
        if (shippingaddress1==null||!shippingaddress1.getUid().equals(user.getId())){
            return false;
        }
        //改变默认
        if (shippingaddress.getDef()==1){
           changeAddressDef(user.getId());
        }
        shippingaddressMapper.updateByPrimaryKeySelective(shippingaddress);
        return true;
    }

    public boolean delUserAddress(User user, Long id) {
        Shippingaddress shippingaddress = shippingaddressMapper.selectByPrimaryKey(id);
        if (shippingaddress==null||!shippingaddress.getUid().equals(user.getId())){
            return false;
        }
        shippingaddressMapper.deleteByPrimaryKey(id);
        return true;
    }


    public List<Order> queryUserWaitingForPayOrders(User user,boolean isStatus0) {
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUidEqualTo(user.getId());
        if (isStatus0){
            criteria.andStatusEqualTo(0);
            orderExample.setOrderByClause("'createtime' DESC");
        }else {
            criteria.andStatusGreaterThan(0);
            orderExample.setOrderByClause("'status' DESC");
        }
        List<Order> orders = orderMapper.selectByExample(orderExample);
        List<Order> res = new ArrayList<>();
        for (Order order:orders){
            order.setGoods(fillOrderInfo(order.getGoods()));
            res.add(order);
        }
        return res;
    }

    @Transactional
    public boolean checkOrder(User user,String outTradeNo) {
        Order order = orderMapper.selectByPrimaryKey(outTradeNo);
        //查不到订单  或者订单状态不是0  或者不属于对方账户
        if (order==null||!order.getStatus().equals(0)||!user.getId().equals(order.getUid())){
            return false;
        }
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent( "{"   +
                "\"out_trade_no\":\""+outTradeNo+"\""   +
                "}" );
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            System.out.println(response.getBody());
            if (response.isSuccess()){
                BigDecimal bigDecimal = new BigDecimal(response.getTotalAmount());
                //校验已付的金额是否等于订单的金额
                if (order.getTotal().equals(bigDecimal)){
                    order.setCreatetime(new Date());
                    order.setStatus(1);
                    orderMapper.updateByPrimaryKey(order);
                    return true;
                }
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Transactional
    public List<Coupon> queryUserCoupons(User user,Integer status) {
       return myCouponMapper.queryUserCoupons(user.getId(),status);
    }


    @Transactional
    public String continueOrder(User user, String orderId) throws AlipayApiException {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order.getUid().equals(user.getId())){
            return AlipayUtils.createTradeAppPay(alipayClient,order.getId(),order.getTotal());
        }
        return null;
    }

    @Transactional
    public Boolean cancelUserOrder(User user, String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order!=null&& order.getUid().equals(user.getId())&&order.getStatus().equals(0)){
            //取消订单退还优惠券
            Long coupon = order.getCoupon();
            if (coupon!=null){
                UserCoupon userCoupon = userCouponMapper.selectByPrimaryKey(coupon);
                if (userCoupon!=null){
                    userCoupon.setStatus(0);
                    userCouponMapper.updateByPrimaryKeySelective(userCoupon);
                }
            }
        }
        OrderExample orderExample = new OrderExample();
        OrderExample.Criteria criteria = orderExample.createCriteria();
        criteria.andUidEqualTo(user.getId());
        criteria.andIdEqualTo(orderId);
        criteria.andStatusEqualTo(0);
        int i = orderMapper.deleteByExample(orderExample);
        return i > 0;
    }

    @Transactional
    public List<OrderDisplay> queryAllOrders(Integer status) {
        List<OrderDisplay> orderDisplays = orderDisplayMapper.queryAllOrdersByStatus(status);
        List<OrderDisplay> res = new ArrayList<>();
        for (OrderDisplay orderDisplay:orderDisplays){
            orderDisplay.setGoods(fillOrderInfo(orderDisplay.getGoods()));
            res.add(orderDisplay);
        }
        return res;
    }

    private void changeAddressDef(Long uid){
        ShippingaddressExample shippingaddressExample = new ShippingaddressExample();
        ShippingaddressExample.Criteria criteria = shippingaddressExample.createCriteria();
        criteria.andUidEqualTo(uid);
        criteria.andDefEqualTo(1);
        List<Shippingaddress> shippingaddresses = shippingaddressMapper.selectByExample(shippingaddressExample);
        for (Shippingaddress shippingaddress2:shippingaddresses){
            shippingaddress2.setDef(null);
            shippingaddressMapper.updateByPrimaryKey(shippingaddress2);
        }
    }

    private String fillOrderInfo(String goods){
        List<OrderInfo> orderInfos = JSON.parseArray(goods,OrderInfo.class);
        Map<Long,OrderInfo> ids = new HashMap<>();
        for (OrderInfo orderInfo:orderInfos){
            ids.put(orderInfo.getId(),orderInfo);
        }
        List<OrderInfo> orderInfos1 = orderInfoMapper.selectedOrderInfosByIds(new ArrayList<>(ids.keySet()));
        for (OrderInfo orderInfo:orderInfos1){
            OrderInfo orderInfo1 = ids.get(orderInfo.getId());
            orderInfo.setPrice(orderInfo1.getPrice());
            orderInfo.setCount(orderInfo1.getCount());
            ids.put(orderInfo.getId(),orderInfo);
        }

        return JSON.toJSONString(ids.values());
    }

    public boolean sendOutOrder(Order order) {
        order.setStatus(2);
        return orderMapper.updateByPrimaryKeySelective(order)==1;
    }

    public Boolean confirmUserOrder(User user, String orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order!=null&& order.getUid().equals(user.getId())&&order.getStatus().equals(2)){
            order.setStatus(3);
            orderMapper.updateByPrimaryKeySelective(order);
            return true;
        }
        return false;
    }
}
