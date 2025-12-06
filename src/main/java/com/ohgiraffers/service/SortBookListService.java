package com.ohgiraffers.service;

import com.ohgiraffers.comparator.AscendingPrice;
import com.ohgiraffers.dto.BookDTO;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SortBookListService {

    private List<BookDTO> bookList;
    private Scanner sc;

    public SortBookListService(List<BookDTO> bookList, Scanner sc) {
        this.bookList = bookList;
        this.sc = sc;
    }


    /**
     *  도서 정렬
     */
    public void sortBookList(){
        System.out.print("\n*** 도서 정렬 목록 조회 ***\n");

        System.out.println("1. 도서명 오름차순");
        System.out.println("2. 가격 오름차순");
        System.out.print("번호 입력 : ");
        int sortingNumber = sc.nextInt();
        sc.nextLine();

        // 서비스 구현
        List<BookDTO> books = sortBookList(sortingNumber);

        for(BookDTO book : books){
            System.out.println(book);
        }
    }

    /**
     * List 복사본을 만들어서 정렬 후 반환
     * @param sortingNumber
     * @return sortedBookList
     */
    public List<BookDTO> sortBookList(int sortingNumber) {
        // 원본을 훼손하지 않기 위해 복사본을 정렬해서 반환. [Collections.sort() -> 원본 정렬]
        // 스트림을 이용한 List 깊은 복사
        List<BookDTO> sortedList = bookList.stream().map(BookDTO::new).collect(Collectors.toList());

        if (sortingNumber == 1) { // 도서명 오름차순
            Collections.sort(sortedList);
        } else if (sortingNumber == 2) { // 가격 오름차순
            Collections.sort(sortedList, new AscendingPrice());
        }

        return sortedList;
    }

}
