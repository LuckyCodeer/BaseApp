package com.app.bean;

import androidx.databinding.ObservableField;

import java.io.Serializable;

/**
 * ObservableField 方式
 * created by yhw
 * date 2023/8/3
 */
public class Book implements Serializable {
    private ObservableField<String> bookName;
    private ObservableField<String> color;

    public ObservableField<String> getBookName() {
        return bookName;
    }

    public void setBookName(ObservableField<String> bookName) {
        this.bookName = bookName;
    }

    public ObservableField<String> getColor() {
        return color;
    }

    public void setColor(ObservableField<String> color) {
        this.color = color;
    }
}
