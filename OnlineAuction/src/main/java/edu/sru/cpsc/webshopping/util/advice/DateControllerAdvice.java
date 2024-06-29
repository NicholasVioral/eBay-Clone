package edu.sru.cpsc.webshopping.util.advice;
import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import edu.sru.cpsc.webshopping.controller.AddWidgetController;

@ControllerAdvice(basePackageClasses = AddWidgetController.class)
public class DateControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if (text != null && !text.isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    setValue(LocalDateTime.parse(text, formatter));
                }
            }

            @Override
            public String getAsText() {
                return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format((LocalDateTime) getValue());
            }
        });
    }
}
