package com.mepro.appname.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationField {
    private String fieldName;
    private String message;
    private Integer rowId;
}
