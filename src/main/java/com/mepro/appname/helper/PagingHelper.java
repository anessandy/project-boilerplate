package com.mepro.appname.helper;

import com.mepro.appname.util.PagingUtil;

public class PagingHelper {
    public static int getStartRow(PagingUtil paging) {
        return paging.getOffset();
    }

    public static int getEndRow(PagingUtil paging) {
        return paging.getOffset() + paging.getSize();
    }

    public static String wrapPagingQuery(String innerQuery) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM ( ");
        sb.append("  SELECT a.*, ROWNUM rnum FROM ( ");
        sb.append(innerQuery);
        sb.append("  ) a WHERE ROWNUM <= :endRow ");
        sb.append(") WHERE rnum > :startRow ");

        return sb.toString();
    }
}
