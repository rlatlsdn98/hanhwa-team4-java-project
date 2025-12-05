package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        int nextNumber;
        if(bookList.isEmpty()){
          nextNumber = 1;
        } else {
          nextNumber = bookList.get(bookList.size() - 1).getNumber() + 1;
        }
        // 책 정보 목록에 추가
        newBook.setNumber(nextNumber);
        bookList.add(newBook);

        // 추가한 책 정보 파일에 저장
        writeBookListFile();
        return newBook.getNumber();
    }

  private void writeBookListFile() {

    try (BufferedWriter bw =
             new BufferedWriter(new FileWriter("src/members.txt"))) {

      for (BookDTO book : bookList) {
        // number title author price
        bw.write(book.getNumber() + " "
            + book.getTitle() + " "
            + book.getAuthor() + " "
            + book.getPrice());
        bw.newLine();
      }

      System.out.println("도서 목록이 파일에 저장되었습니다.");

    } catch (IOException e) {
      System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
    }
  }
}
