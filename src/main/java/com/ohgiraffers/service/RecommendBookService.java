package com.ohgiraffers.service;

import com.ohgiraffers.dto.BookDTO;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class RecommendBookService {
    private List<BookDTO> bookList;
    private static final String TODAY_BOOK_FILE_PATH = "src/main/resources/todaybook.txt";
    private BookDTO todayBook;
    private LocalDate savedDate;
    private LocalDate now = LocalDate.now();

    public RecommendBookService(List<BookDTO> bookList) {
        this.bookList = bookList;
    }

    /**
     * 8. 오늘의 추천 도서
     */
    public void recommendBook(){
        System.out.println("\n*** 오늘의 추천 도서 ***");
        if(bookList.isEmpty()){
            System.out.println("추천할 도서가 없습니다.");
            return;
        }
        loadTodayBook();
        System.out.println(todayBook);
    }

    /**
     * 저장된 오늘의 책 불러오기 또는 생성
     */
    private void loadTodayBook(){
        File file = new File(TODAY_BOOK_FILE_PATH);

        if (!file.exists() || file.length() == 0) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            // 1줄 = 날짜
            // 2줄 = 책ID
            String dateStr = br.readLine();
            String idStr = br.readLine();

            // 파일이 비어있을 경우 || 날짜가 다를 경우
            if(dateStr == null || idStr == null || !LocalDate.parse(dateStr).equals(now)){
                setTodayBook();
                return;
            }
            savedDate = LocalDate.parse(dateStr);
            int bookId = Integer.parseInt(idStr);

            todayBook = findBookById(bookId);

            // 저장된 추천 책이 삭제되었을 경우
            if(todayBook == null){
                setTodayBook();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 새로운 추천 책 생성
     * @return
     */
    private BookDTO setTodayBook(){
        Random random = new Random();
        int bookNumber = random.nextInt(bookList.size());

        todayBook = bookList.get(bookNumber);
        savedDate = LocalDate.now();
        saveTodayBook();
        return todayBook;
    }

    /**
     * 파일에 저장
     */
    private void saveTodayBook(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TODAY_BOOK_FILE_PATH))) {

            bw.write(now.toString());
            bw.newLine();
            bw.write(String.valueOf(todayBook.getNumber()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 오늘의 추천책이 bookList에 있는지 확인
     * @param bookNumber
     * @return
     */
    private BookDTO findBookById(int bookNumber){
        for (BookDTO book : bookList) {
            if (book.getNumber() == bookNumber) {
                return book;
            }
        }
        return null;
    }

}
