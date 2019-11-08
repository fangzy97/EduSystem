package com.lepetit.edu.callback;

import android.os.Bundle;

import java.text.ParseException;

public interface ParserHtmlCallback {
    void onParserHtmlComplete(Bundle bundle) throws ParseException;
}
