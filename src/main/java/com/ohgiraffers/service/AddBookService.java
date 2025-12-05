package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.util.List;
import java.util.Scanner;

public class AddBookService {

    private List<BookDTO> bookList;
    private Scanner sc;

    public AddBookService(List<BookDTO> bookList, Scanner sc) {
        this.bookList = bookList;
        this.sc = sc;
    }

    /**
     * 3. 도서 추가
     */
    public void addBook(){
        System.out.print("\n*** 도서 추가 ***\n");

        System.out.print("책 제목 입력 : ");
        String title = sc.nextLine();

        System.out.print("책 저자 입력 : ");
        String author = sc.nextLine();

        System.out.print("책 가격 입력 : ");
        int price = sc.nextInt();
        sc.nextLine();

        // BookDTO 객체 생성 및 값 대입
        BookDTO book = new BookDTO();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPrice(price);

        // 서비스 호출
        int bookNumber = addBook(book);

        if(bookNumber == 0){
            System.out.println("@@@ 책 추가에 실패했습니다. @@@");
            return;
        }

        System.out.println(bookNumber + "번 책이 추가 되었습니다.");
    }


    /**
     * 책 목록에 새로운 책 추가. 단, "제목"이 중복되는 책은 추가X
     * 반환되는 number는 마지막 number + 1
     * @param newBook
     * @return number 또는 0
     */
    public int addBook(BookDTO newBook) {
        // 중복 체크
        for (BookDTO book : bookList) {
            if (book.getTitle().equals(newBook.getTitle())) {
                return 0;
            }
        }
        // 다음 번호 생성 -> 정확성을 위해 마지막 원소의 number를 구하고 + 1
        int nextNumber = bookList.get(bookList.size() - 1).getNumber() + 1;
        // 책 정보 목록에 추가
        newBook.setNumber(nextNumber);
        bookList.add(newBook);
        return newBook.getNumber();
    }
}
