package com.ohgiraffers.comparator;



import com.ohgiraffers.dto.BookDTO;

import java.util.Comparator;

public class AscendingPrice implements Comparator<BookDTO> {

    // 전달 받은 두 객체의 비교 방법을 정의해서 정렬 기준을 만듦
    //
    @Override
    public int compare(BookDTO o1, BookDTO o2) {
        return o2.getPrice() - o1.getPrice();
    }
}
