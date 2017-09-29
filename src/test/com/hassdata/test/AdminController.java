package com.hassdata.test;

import com.hassdata.survey.util.MD5TUtils;
import org.junit.Test;

public class AdminController {
    @Test
    public void showMd5(){
        System.out.println(MD5TUtils.threeMD5("123123"));
    }
}
