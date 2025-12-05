package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SearchBookService {

    private List<BookDTO> bookList;
    private Scanner sc;

    public SearchBookService(List<BookDTO> bookList, Scanner sc) {
        this.bookList = bookList;
        this.sc = sc;
    }

    /**
     * 6. 도서 검색
     */
    public void searchBook(){
        System.out.print("\n*** 도서 검색(제목 부분 일치 검색) ***\n");

        System.out.print("검색할 책 제목 입력 : ");
        String keyword = sc.nextLine();

        // 서비스 호출
        List<BookDTO> searchBookList = searchBook(keyword);

        if(searchBookList == null || searchBookList.isEmpty()){
            System.out.println("검색 결과가 없습니다.");
            return;
        }

        for(BookDTO book : searchBookList){
            System.out.println(book);
        }
    }

    /**
     * 책 제목중 일부라도 키워드아 일치하면 모두 반환
     * @param keyword
     * @return searchedBookList
     */
    public List<BookDTO> searchBook(String keyword) {
        List<BookDTO> searchedBookList = new ArrayList<>();
        for (BookDTO book : bookList) {
            if (book.getTitle().contains(keyword)) {
                searchedBookList.add(book);
            }
        }
        return searchedBookList;
    }

}
