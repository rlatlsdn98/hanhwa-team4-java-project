package com.ohgiraffers.service;

import com.ohgiraffers.configuration.Config;
import com.ohgiraffers.dto.BookDTO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
   * 도서 제거
   */
  public void deleteBook() {
    System.out.print("\n*** 도서 제거 ***\n");

    System.out.print("제거할 도서 번호 입력 : ");
    int bookNumber = sc.nextInt();
    sc.nextLine();

    // 서비스 호출
    BookDTO deletedBook = deleteBook(bookNumber);

    if (deletedBook == null) {
      System.out.println("@@@ 일치하는 번호의 책이 없습니다. @@@");
      return;
    }

    System.out.println(deletedBook.getTitle() + "이/가 제거 되었습니다.");

  }

  /**
   * 도서 목록에서 번호가 일치하는 책을 제거하겠다
   *
   * @param bookNumber
   * @return 제거된 BookDTO 또는 null
   */
  public BookDTO deleteBook(int bookNumber) {
    // 똑같은 번호의 책을 목록에서 제거 후 반환
    for (int i = 0; i < bookList.size(); i++) {
      if (bookList.get(i).getNumber() == bookNumber) {
        BookDTO removed = bookList.remove(i);

        //  실제 파일 내용도 현재 bookList 기준으로 다시 저장
        writeBookListFile();

        return removed;
      }
    }
    return null;
  }

  /**
   * 현재 bookList 내용을 기준으로 members.txt를 통째로 다시 저장
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

      System.out.println("선택한 도서가 삭제되었습니다.");

    } catch (IOException e) {
      System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
    }
  }

}
