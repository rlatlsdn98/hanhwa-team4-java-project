package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class SelectAllService {

  private List<BookDTO> bookList;

  // Run 에서 넘겨주는 bookList 그대로 받기
  public SelectAllService(List<BookDTO> bookList) {
      this.bookList = bookList;
  }

  // 1. 모든 도서 목록 조회
  public void selectAll() {

    if (bookList == null || bookList.isEmpty()) {
      System.out.println("조회할 도서가 없습니다.");
      return;
    }

    Scanner sc = new Scanner(System.in);
    int pageSize = 5;
    int currentPage = 1;
    int bookTotalCount = bookList.size();
    int totalPage = (bookTotalCount  % pageSize == 0 ? (bookTotalCount / pageSize) : (bookTotalCount / pageSize) + 1);

    while (true){
      System.out.println("\n*** 모든 도서 목록 조회 ***");
      System.out.println("(총 " + bookTotalCount + "권) | 현재 페이지 " + currentPage + "/" + totalPage);
      System.out.println("==========================================");

      int startIndex = (currentPage - 1) * pageSize;
      int endIndex = Math.min(startIndex + pageSize, bookTotalCount);

      for (int i = startIndex; i < endIndex; i++) {
        System.out.println(bookList.get(i));
      }

      System.out.println("==========================================");
      System.out.println("[1] 이전 페이지, [2] 다음 페이지");
      System.out.println("[3] 원하는 페이지로 이동하기 ");
      System.out.println("[4] 첫 페이지, [5] 마지막 페이지 ");
      System.out.println("[0] 나가기 ");
      System.out.print("메뉴를 선택 하세요 : ");
      String pageCommand = sc.nextLine();

      switch (pageCommand) {
        case "1" :
          if (currentPage > 1) currentPage--;
          else System.out.println("이미 첫 페이지 입니다.");
          break;

        case  "2" :
          if (currentPage < totalPage) currentPage++;
          else System.out.println("이미 마지막 페이지 입니다.");
          break;

        case "3":
          while (true) {
            System.out.print("이동할 페이지 입력 : ");
            try {

              int movePage = sc.nextInt();
              sc.nextLine();

              if (movePage >= 1 && movePage <= totalPage) {
                currentPage = movePage;
                break;
              }
              else {
                System.out.println("전체 페이지 범위를 벗어 났습니다.");
              }
            }catch (InputMismatchException e){
              System.out.println("잘못된 입력입니다. 숫자만 입력 하세요 ");
              sc.nextLine(); // 잘못 입력된 버퍼 제거

              // 다시 입력 받게 루프문으로 수정
            }
          }
        break;
        case "4":
          currentPage = 1;
          break;

        case"5":
          currentPage = totalPage;
          break;

        case "0" :
          System.out.println("메인 메뉴로 돌아갑니다");
          return;

        default:
          System.out.println("메뉴에 없는 번호입니다.");
      }

    }


    }
  }

