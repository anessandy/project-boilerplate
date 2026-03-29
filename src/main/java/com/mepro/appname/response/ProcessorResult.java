package com.mepro.appname.response;

import com.mepro.appname.validation.ValidationField;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessorResult {
    private Boolean success = true;
    private String viewName;
    private Map<String, Object> mapModel;
    private ModelAndView modelAndView;
    private RedirectView redirectView;
    private String jsonResponse;
    private List<ValidationField> validationFields;
    private HttpEntity<byte[]> binaryEntity;
    private Object jsonResponsesObj;
}
