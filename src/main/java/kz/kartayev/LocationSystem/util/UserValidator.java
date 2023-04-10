package kz.kartayev.LocationSystem.util;

import kz.kartayev.LocationSystem.dto.UserDTO;
import kz.kartayev.LocationSystem.models.User;
import kz.kartayev.LocationSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService service;
    @Autowired
    public UserValidator(UserService service) {
        this.service = service;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> check = Optional.ofNullable(service.findByEmail(user.getEmail()));
        if(check.isPresent()){
            errors.rejectValue("email", "", "This email already is used");
        }
    }
}
