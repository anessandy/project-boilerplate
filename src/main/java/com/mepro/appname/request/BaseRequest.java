package com.mepro.appname.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseRequest {
    private String keyword;
    private int page = 1;
    private int size = 10;
}
