package com.example.web.async;

public class MockQueue {

    private String placeOrder;

    private String completeOrder;

    public String getPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(String placeOrder) throws Exception{
        System.out.println("接到下单请求:"+placeOrder);
        Thread.sleep(1000);
        this.completeOrder = placeOrder;
        System.out.println("下单处理完毕"+placeOrder);
    }

    public String getCompleteOrder() {
        return completeOrder;
    }

    public void setCompleteOrder(String completeOrder) {
        this.completeOrder = completeOrder;
    }
}
