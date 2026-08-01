package com.adplatform.adsponsor.utils;

import com.adplatform.common.exception.AdException;
import org.apache.commons.lang3.time.DateUtils;
import java.util.Date;

public class CommonUtils {

    private static String[] parsePatterns = {
        "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static Date parseStringDate(String dateString) throws AdException {
        try {
            return DateUtils.parseDate(dateString, parsePatterns);
        } catch (Exception e) {
            throw new AdException(e.getMessage());
        }
    }
}
