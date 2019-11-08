package com.lepetit.edu.controller;

import com.lepetit.edu.util.OKHttpUtil;

abstract class BaseController {
    private static OKHttpUtil okHttpUtil;

    void newOKHttpUtilInstance() {
        okHttpUtil = new OKHttpUtil();
    }

    OKHttpUtil getOKHttpUtil() {
        return okHttpUtil;
    }
}
