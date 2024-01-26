package com.csu.bean;

import java.math.BigDecimal;

/**
 * 购物车内的的购物项
 */
public class CartItem {
    private Book book;//书的图片，名字，单价
    private Integer count;//这本书在购物车内的数量
    private Double amount;//这本书在购物车中的金额(通过运算算出来)


    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        //设置图书的时候，设置金额
        BigDecimal price = new BigDecimal(this.book.getPrice() + "");//使用字符串的构造器
        BigDecimal count = new BigDecimal(this.count + "");
        this.amount = price.multiply(count).doubleValue();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
        //设置amount
        BigDecimal price = new BigDecimal(this.book.getPrice() + "");//使用字符串的构造器
        BigDecimal count1 = new BigDecimal(this.count + "");
        this.amount = price.multiply(count1).doubleValue();
    }

    public Double getAmount() {
        return amount;
    }

//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }

    public CartItem(Book book, Integer count) {
        this.book = book;
        this.count = count;
        //有参构造器计算amount
        BigDecimal price = new BigDecimal(this.book.getPrice() + "");//使用字符串的构造器
        BigDecimal count1 = new BigDecimal(this.count + "");
        this.amount = price.multiply(count1).doubleValue();
    }

    public CartItem() {
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", count=" + count +
                ", amount=" + amount +
                '}';
    }
}
