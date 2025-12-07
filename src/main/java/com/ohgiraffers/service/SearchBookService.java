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

        List<BookDTO> searchBookList;

        // 서비스 호출
        // 입력 값이 초성인지 기본입력인지 구분
        if(isInitial(keyword)){
          searchBookList = searchByInitial(keyword);
          if(searchBookList == null || searchBookList.isEmpty()){
            System.out.println("초성 검색 결과가 없습니다.");
            return;
          }
        }
        else {
          searchBookList = searchBook(keyword);
          if(searchBookList == null || searchBookList.isEmpty()){
            System.out.println("검색 결과가 없습니다.");
            return;
          }
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

    private static final char[] INITIAL = { // 유니코드 인덱스
        'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ',
        'ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'
    };


  /**
   *  한 글자 초성 추출하기
   * @param ch 추출할 문자
   * @return
   */
  private char getInitial(char ch){
      if (ch < '가' || ch > '힣') return ch;

      // 한글 유니코드 구성  초성 * (21* 28) + 중성 * 28 + 종성 + 0xAC00(가)
      int code = ch - 0xAC00;
      return INITIAL[code / (21 * 28)];
      // 예시) 각 = AC01, AC01 - AC00 = 1 -> 1/(21 * 28) = 0 -> INITIAL[0] = 'ㄱ'
      //      라 = B77C, B77C - AC00 = B7C(2940) -> 2940/(21 * 28) = 5 -> INITIAL[5] = 'ㄹ'
    }

  /**
   * 입력 문자열을 한글 초성 문자열로 변경하기, (한글로만 구성)
   * @param searchText 변환할 문자열 ex) 거꾸로읽는세계사 -> ㄱㄲㄹㅇㅅㄱㅅ
   * @return
   */
  private String convertToInitial(String searchText){
      StringBuilder sb = new StringBuilder();

      for (char ch : searchText.toCharArray()){
        sb.append(getInitial(ch));
      }
      return sb.toString();
    }

  /**
   * 입력 받은 문자열이 초성 문자열인지 확인
   * @param keyword 확인할 문자열
   * @return
   */
  private boolean isInitial(String keyword){
    // 키워드 빈칸이면 안하면 true 나옴 -> 수정
    if (keyword == null || keyword.isEmpty()) return false;

    for (int i = 0; i < keyword.length(); i++){
      char ch = keyword.charAt(i);
      boolean isInitial = false;

      for (char base : INITIAL){
        if (ch == base){
          isInitial = true;
          break;
        }
      }

      if(!isInitial) return false;
    }
    return true;
  }

  /**
   * 입력된 초성 문자열에 해당되는 책이 있다면 목록에 추가
   * @param keyword 검색할 책 초성 문자열
   * @return
   */
  // 지금 초성 검색하는데 초성이 떨어져있어도 검색이 되는데?
  // 어떻게 붙은 초성만 검색하지??
  private List<BookDTO> searchByInitial(String keyword){
      List<BookDTO> searchedBookList = new ArrayList<>();

      for (BookDTO book : bookList) {

        String bookTitle = book.getTitle();
        String convertInitial = convertToInitial(bookTitle);

//        if (convertToInitial(book.getTitle()).contains(keyword)) {
        if (convertInitial.contains(keyword)) {
          searchedBookList.add(book);
        }
      }
      return searchedBookList;
    }



}
