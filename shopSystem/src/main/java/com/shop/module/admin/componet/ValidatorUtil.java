package com.shop.module.admin.componet;

import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 12:11
 */
public class ValidatorUtil {
    private static final String status = "Y";
    private static final String statusE = "N";
    public static Boolean checkArray(List<String> strings){
        if (strings == null||strings.size()==0) {
            return false;
        }
        for (String s : strings) {
            if (s == null || s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static Boolean checkUserAdminStatus(String userStatus) {
        if (status.equals(userStatus) || statusE.equals(userStatus)) {
            return true;
        }
        return false;
    }
}
