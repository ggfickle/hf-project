package com.hf.demo.innerClassDemo.demo5;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/13 22:47
 */
public class Factories {

    public static void serviceCustomer(ServiceFactory serviceFactory) {
        Service factoryService = serviceFactory.getService();
        factoryService.method1();
        factoryService.method2();
    }

    public static void main(String[] args) {
        serviceCustomer(Impl2Service2.factory);
        serviceCustomer(ImplService1.serviceFactory);
    }
}
