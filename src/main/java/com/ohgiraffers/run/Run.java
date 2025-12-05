package com.ohgiraffers.run;

import com.ohgiraffers.dto.BookDTO;
import com.ohgiraffers.service.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Run {

    Scanner sc;
    private List<BookDTO> bookList;

    // Association
    private SelectAllService selectAllService;                  // 1. 모든 도서 조회
    private SelectBookNumberService selectBookNumberService;    // 2. 도서 상세 조회(도서 번호)
    private AddBookService addBookService;                      // 3. 도서 추가
    private UpdateBookService updateBookService;                // 4. 도서 수정
    private DeleteBookService deleteBookService;                // 5. 도서 제거
    private SearchBookService searchBookService;                // 6. 도서 검색
    private SortBookListService sortBookListService;            // 7. 도서 정렬


    public Run() {
        this.sc = new Scanner(System.in);
        this.bookList = readBookListFile();

        // Association
        this.selectAllService = new SelectAllService(this.bookList);
        this.selectBookNumberService = new SelectBookNumberService(this.bookList, this.sc);
        this.addBookService = new AddBookService(this.bookList, this.sc);
        this.updateBookService = new UpdateBookService(this.bookList, this.sc);
        this.deleteBookService = new DeleteBookService(this.bookList, this.sc);
        this.searchBookService = new SearchBookService(this.bookList, this.sc);
        this.sortBookListService = new SortBookListService(this.bookList, this.sc);

        this.run();
    }

    public void run() {

        int input = Integer.MIN_VALUE;

        do {
            System.out.println("\n========도서관리프로그램=======\n");
            System.out.println("1. 모든 도서 목록 조회");
            System.out.println("2. 도서 상세 조회(도서 번호)");
            System.out.println("3. 도서 추가");
            System.out.println("4. 도서 수정");
            System.out.println("5. 도서 제거");
            System.out.println("6. 도서 검색");
            System.out.println("7. 도서 정렬");
            System.out.println("0. 종료");

            try{
                System.out.print("번호 선택 >> ");
                input = sc.nextInt();
                sc.nextLine();

                switch(input) {
                    case 1: this.selectAllService.selectAll();        break;
                    case 2: this.selectBookNumberService.selectBookNumber(); break;
                    case 3: this.addBookService.addBook();          break;
                    case 4: this.updateBookService.updateBook();       break;
                    case 5: this.deleteBookService.deleteBook();       break;
                    case 6: this.searchBookService.searchBook();       break;
                    case 7: this.sortBookListService.sortBookList();     break;
                    case 0:
                        System.out.println("------------프로그램 종료-------------"); break;
                    default:
                        System.out.println("-----------메뉴 목록의 숫자 입력-----------");
                }

            }catch (InputMismatchException e){
                System.out.println("####입력 형식 오류####");
                sc.nextLine(); // 입력 버퍼에 남은 잘못된 문자열 제거.
                input = -1;
            }catch (Exception e){ // 나머지 예외 발생 처리
                System.out.println("#### 예외 발생. 관리자 문의 바람 ####");
                e.printStackTrace();
            }

        }while(input != 0);
    }

    private List<BookDTO> readBookListFile() {
        List<BookDTO> tempList = new ArrayList<>();

        // tempList << 파일 데이터를 읽어와서 List<BookDTO> 타입으로 저장.
        // 1. 파일 데이터를 읽어 오는 방법
        // 2. 읽어온 파일 데이터를 어떻게 tempList에 추가할지?
        try (BufferedReader br =
                     new BufferedReader(new FileReader("src/main/resources/members.txt"))) {

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

        } catch (IOException e) {
            System.out.println("⚠️ 파일 읽기 중 오류 발생: " + e.getMessage());
        }
        return tempList;
    }

}
