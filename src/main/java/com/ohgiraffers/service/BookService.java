package com.ohgiraffers.service;

import com.ohgiraffers.comparator.AscendingPrice;
import com.ohgiraffers.dto.BookDTO;


import java.io.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    private List<BookDTO> bookList;

    public BookService() {
        bookList = readBookListFile();


    }

    private List<BookDTO> readBookListFile() {
        List<BookDTO> tempList = new ArrayList<>();

        // tempList << 파일 데이터를 읽어와서 List<BookDTO> 타입으로 저장.
        // 1. 파일 데이터를 읽어 오는 방법
        // 2. 읽어온 파일 데이터를 어떻게 tempList에 추가할지?
      try (BufferedReader br =
               new BufferedReader(new FileReader("src/members.txt"))) {

        String line;
        while ((line = br.readLine()) != null) {
          // 한 줄을 공백 기준으로 분리합니다.
          String[] parts = line.split(" ");

          if (parts.length >= 4) {
            // 파싱하여 BookDTO 객체 생성
            int bookId = Integer.parseInt(parts[0]);
            String title = parts[1];
            String author = parts[2];
            int price = Integer.parseInt(parts[3]);

            BookDTO book = new BookDTO(bookId, title, author, price);
            tempList.add(book);
            System.out.println( book.getTitle());
          }
        }

      } catch (FileNotFoundException e) {
        // 파일이 없는 경우 (가장 흔함): 빈 목록을 반환하거나 초기 데이터를 생성해야 함
        System.out.println("⚠️ 도서 데이터 파일(bookList.dat)을 찾을 수 없습니다. ");
      } catch (IOException e) {
        System.out.println("⚠️ 파일 읽기 중 오류 발생: " + e.getMessage());
      }
      return tempList;
    }

    public List<BookDTO> getBookList() {
        return bookList;
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