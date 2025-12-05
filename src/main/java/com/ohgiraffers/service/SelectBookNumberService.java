package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SelectBookNumberService {

    private List<BookDTO> bookList;
    private Scanner sc;

    public SelectBookNumberService(List<BookDTO> bookList, Scanner sc) {
        this.bookList = bookList;
        this.sc = sc;
    }

    /**
     * 2. 도서 상세 조회(도서 번호)
     * @throws InputMismatchException
     */
    public void selectBookNumber() throws InputMismatchException{
        System.out.print("\n*** 도서 상세 조회(도서 번호) ***\n");
        System.out.print("상세 조회할 도서 번호 입력 : ");
        int bookNumber = sc.nextInt();

        // 서비스 호출
        BookDTO book = selectBookNumber(bookNumber);

        if(book == null){
            System.out.println("@@@ 일치하는 번호의 책이 없습니다. @@@");
            return;
        }

        System.out.println("책 번호: " + book.getNumber());
        System.out.println("책 제목: " + book.getTitle());
        System.out.println("책 저자: " + book.getAuthor());
        System.out.println("책 가격: " + book.getPrice() + "원");
    }

    /***
     * 책 목록에서 번호(number)가 일치하는 책을 찾아서 반환
     * @param bookNumber
     * @return BookDTO 또는 null
     */
    public BookDTO selectBookNumber(int bookNumber) {
        // 반복문을 이용해서 모든 책 인스턴스에 접근
        for (BookDTO book : bookList) {
            if (book.getNumber() == bookNumber) {
                return book;
            }
        }
        return null;
    }
}
