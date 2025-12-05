package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.util.List;
import java.util.Scanner;

public class DeleteBookService {

    private List<BookDTO> bookList;
    private Scanner sc;

    public DeleteBookService(List<BookDTO> bookList, Scanner sc) {
        this.bookList = bookList;
        this.sc = sc;
    }

    /**
     * 5. 도서 제거
     */
    public void deleteBook(){
        System.out.print("\n*** 도서 제거 ***\n");

        System.out.print("제거할 도서 번호 입력 : ");
        int bookNumber = sc.nextInt();
        sc.nextLine();

        // 서비스 호출
        BookDTO deletedBook = deleteBook(bookNumber);

        if(deletedBook == null){
            System.out.println("@@@ 일치하는 번호의 책이 없습니다. @@@");
            return;
        }

        System.out.println(deletedBook.getTitle() + "이/가 제거 되었습니다.");

    }

    /**
     * 도서 목록에서 번호가 일치하는 책을 제거하겠다
     * @param bookNumber
     * @return 제거된 BookDTO 또는 null
     */
    public BookDTO deleteBook(int bookNumber) {
        // 똑같은 번호의 책을 목록에서 제거 후 반환
        for (int i=0; i<bookList.size(); i++ ) {
            if (bookList.get(i).getNumber() == bookNumber) {
                return bookList.remove(i);
            }
        }
        return null;
    }


}
