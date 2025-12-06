package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class AddBookService {

  //  실제 파일 위치에 맞게 경로 상수로 관리
  private static final String BOOK_FILE_PATH = "src/members.txt";

  private List<BookDTO> bookList;
  private Scanner sc;

  public AddBookService(List<BookDTO> bookList, Scanner sc) {
    this.bookList = bookList;
    this.sc = sc;
  }

  /**
   * 3. 도서 추가 (사용자 입력)
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
   * 반환되는 number는 파일 기준 마지막 number + 1
   */
  public int addBook(BookDTO newBook) {
    // 중복 체크
    for (BookDTO book : bookList) {
      if (book.getTitle().equals(newBook.getTitle())) {
        return 0;
      }
    }

    //  파일 기준 마지막 번호를 가져와서 +1
    int lastNumber = getLastBookNumberFromFile();
    int nextNumber = lastNumber + 1;

    // 책 정보 목록에 추가
    newBook.setNumber(nextNumber);
    bookList.add(newBook);

    //  방금 추가한 책 정보만 파일에 저장
    writeBookListFile();

    return newBook.getNumber();
  }


  /**
   *  파일(members.txt)에서 가장 큰 번호(첫 번째 컬럼)를 읽어오는 메서드
   *    - 없으면 0 반환 → 첫 책은 1번부터 시작
   */
  private int getLastBookNumberFromFile() {

    int lastNumber = 0;

    File file = new File(BOOK_FILE_PATH);
    if (!file.exists()) {
      // 파일이 없으면 아직 아무도 없는 상태라고 가정
      return 0;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(BOOK_FILE_PATH))) {

      String line;
      while ((line = br.readLine()) != null) {

        line = line.trim();

        // 빈 줄은 스킵
        if (line.isEmpty()) {
          continue;
        }

        String[] parts = line.split(" ");

        // 첫 번째 값이 번호라고 가정
        try {
          int num = Integer.parseInt(parts[0]);
          if (num > lastNumber) {
            lastNumber = num;
          }
        } catch (NumberFormatException e) {
          // 혹시 이상한 줄 있으면 무시
        }
      }

    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    return lastNumber;
  }


  /**
   *  새로 추가된 마지막 책 한 권만 파일에 append 하는 방식으로 수정
   */
  private void writeBookListFile() {

    // bookList가 비어있으면 쓸 게 없음
    if (bookList.isEmpty()) return;

    // 방금 추가된 마지막 책
    BookDTO book = bookList.get(bookList.size() - 1);

    try (BufferedWriter bw =
             new BufferedWriter(new FileWriter(BOOK_FILE_PATH , true))) {

      // number title author price
      bw.write(book.getNumber() + " "
          + book.getTitle() + " "
          + book.getAuthor() + " "
          + book.getPrice());
      bw.newLine();

      System.out.println("도서 목록이 파일에 저장되었습니다.");

    } catch (IOException e) {
      System.out.println("파일 저장 중 오류 발생: " + e.getMessage());
    }
  }
}
