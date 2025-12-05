package com.ohgiraffers.dto;

public class BookDTO implements Comparable<BookDTO>{

    /* 도서 정보를 저장할 DTO 클래스를 만들어보자 */
    private int number;
    private String title;
    private String author;
    private int price;

    public BookDTO() {

    }

    public BookDTO(int number, String title, String author, int price) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public BookDTO(BookDTO other){
        this.number = other.number;
        this.title = other.title;
        this.author = other.author;
        this.price = other.price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
         return number + ". " + title + " "+  author + " " +  + price + "원";
    }


    @Override
    public int compareTo(BookDTO o){
//        return this.price - o.price; 가격순서
        return this.title.compareTo(o.title);
    }


}
