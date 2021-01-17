package com.chenxi.bzbb.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

import java.math.BigDecimal;

public class AlipayUtils {

    public static String createTradeAppPay(AlipayClient alipayClient, String orderNum, BigDecimal total) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("限量款包包");
        model.setSubject("包治百病");
        model.setOutTradeNo(orderNum);
        model.setTimeoutExpress("30m");
        model.setTotalAmount(total.setScale(2).toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
        return response.getBody();
    }
}
