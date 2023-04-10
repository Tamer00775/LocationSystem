package kz.kartayev.LocationSystem.util;

import kz.kartayev.LocationSystem.models.Share;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class ShareValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Share share = (Share)target;
        if(!share.getStatus().equals("ADMIN") || !share.getStatus().equals("READ")){
            errors.rejectValue("status", "", "You must choose the status! Ex: ADMIN or READ");
        }
    }
}
