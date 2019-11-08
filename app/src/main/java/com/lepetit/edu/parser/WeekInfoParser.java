package com.lepetit.edu.parser;

import android.os.Bundle;

import com.lepetit.edu.callback.ParserHtmlCallback;
import com.lepetit.edu.inter.IParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.util.List;

public class WeekInfoParser implements IParser {

    @Override
    public void parserHtml(String html, ParserHtmlCallback callback) {
        Document document = Jsoup.parse(html);
        Elements tds = document.getElementsByTag("td");
        List<String> dayList = tds.eachAttr("title");
        String firstDay = dayList.get(0);
        String lastDay = dayList.get(dayList.size() - 1);
        // 格式化获取到的日期为yyyy-mm-dd
        firstDay = firstDay.replaceAll("\\u5e74", "-").replaceAll("\\u6708", "-");
        lastDay = lastDay.replaceAll("\\u5e74", "-").replaceAll("\\u6708", "-");
        // 调用回调函数将数据传出
        Bundle bundle = new Bundle();
        bundle.putString("firstDay", firstDay);
        bundle.putString("lastDay", lastDay);
        try {
            callback.onParserHtmlComplete(bundle);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
