package com.lepetit.edu.inter;

import com.lepetit.edu.callback.ParserHtmlCallback;

/**
 * 解析获取到的html
 * */
public interface IParser {
    void parserHtml(String html, ParserHtmlCallback callback);
}
