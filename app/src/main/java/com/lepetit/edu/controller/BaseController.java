package com.lepetit.edu.controller;

import com.lepetit.edu.util.OKHttpUtil;

public abstract class BaseController {
    private OKHttpUtil okHttpUtil;

    public void newOKHttpUtilInstance() {
        okHttpUtil = new OKHttpUtil();
    }

    public OKHttpUtil getOKHttpUtil() {
        return okHttpUtil;
    }
}
