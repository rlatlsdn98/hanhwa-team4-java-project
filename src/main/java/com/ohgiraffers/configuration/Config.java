package com.ohgiraffers.configuration;

public enum Config {

    OUTPUT_FILE_PATH("src/main/resources/booklist.txt"),
    INPUT_FILE_PATH("src/main/resources/booklist.txt");

    private String filePath;

    Config(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return this.filePath;
    }

}
