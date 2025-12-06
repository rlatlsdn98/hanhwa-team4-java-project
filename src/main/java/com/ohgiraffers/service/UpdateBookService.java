package com.ohgiraffers.service;

import com.ohgiraffers.configuration.Config;
import com.ohgiraffers.dto.BookDTO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UpdateBookService {

  private List<BookDTO> bookList;
  private Scanner sc;

  public UpdateBookService(List<BookDTO> bookList, Scanner sc) {
    this.bookList = bookList;
    this.sc = sc;
  }

  /**
   * 도서 수정
   */
  public void updateBook() {
    System.out.println("\n도서 정보 수정\n");
    // 1. 책 번호로 수정할 책이 있는지 검색
    //  -> 없으면 메서드 종료
    System.out.print("수정할 도서 번호 입력 : ");
    int bookNumber = sc.nextInt();
    sc.nextLine();
    BookDTO book = selectBookNumber(bookNumber); // 서비스 호출
    if (book == null) {
      System.out.println("@@@ 일치하는 번호의 책이 없습니다. @@@");
      return;
    }

    // 2. 해당 책의 제목, 내용, 가격을 수정
    System.out.print("수정할 책 제목 : ");
    String updateTitle = sc.nextLine();
    System.out.print("수정할 책 저자 : ");
    String updateAuthor = sc.nextLine();
    System.out.print("수정할 책 가격 : ");
    int updatePrice = sc.nextInt();

    book.setTitle(updateTitle);
    book.setAuthor(updateAuthor);
    book.setPrice(updatePrice);
    // 수정된 내용을 파일에 반영
    writeBookListFile();
    System.out.println("----------책 정보가 수정되었습니다----------");
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
   * 현재 bookList 내용을 기준으로 booklist.txt 전체를 다시 저장
   * Run.readBookListFile()과 같은 경로 사용
   */
  private void writeBookListFile() {

    try (BufferedWriter bw =
             new BufferedWriter(
                 new FileWriter(Config.OUTPUT_FILE_PATH.getFilePath()))) {

      for (BookDTO book : bookList) {
        // number title author price
        bw.write(book.getNumber() + " "
            + book.getTitle() + " "
            + book.getAuthor() + " "
            + book.getPrice());
        bw.newLine();
      }

      System.out.println("도서 목록이 파일에 반영되었습니다.");

    } catch (IOException e) {
      System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
    }
  }
}
