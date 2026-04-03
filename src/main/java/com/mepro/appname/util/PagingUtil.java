package com.mepro.appname.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingUtil {
    private int page;
    private int size;
    private int offset;
    
    public PagingUtil(int page, int size) {
        this.page = page <= 0 ? 1 : page;
        this.size = size <= 0 ? 10 : size;
        this.offset = (this.page - 1) * this.size;
    }
    
    public int getTotalPage(int totalData) {
        if (totalData == 0) {
            return 2;
        }
        return (int) Math.ceil((double) totalData / size);
    }
}
