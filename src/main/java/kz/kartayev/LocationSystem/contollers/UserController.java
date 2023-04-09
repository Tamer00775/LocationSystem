package kz.kartayev.LocationSystem.contollers;

import kz.kartayev.LocationSystem.dto.UserDTO;
import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.models.User;
import kz.kartayev.LocationSystem.services.LocationService;
import kz.kartayev.LocationSystem.services.UserService;
import kz.kartayev.LocationSystem.util.UserError;
import kz.kartayev.LocationSystem.util.UserErrorResponse;
import kz.kartayev.LocationSystem.util.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import static kz.kartayev.LocationSystem.util.ErrorUtil.getFieldErrors;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final LocationService locationService;
    private final ModelMapper modelMapper;
    private final UserValidator validator;
    @Autowired
    public UserController(UserService userService, LocationService locationService, ModelMapper modelMapper, UserValidator validator) {
        this.userService = userService;
        this.locationService = locationService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @GetMapping
    public List<User> getUsers(){

        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable("id") int id){
        return userService.findOne(id);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
        User user = convertToUser(userDTO);
        validator.validate(user, bindingResult);
        if(bindingResult.hasErrors()){
            getFieldErrors(bindingResult);
        }
        userService.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}/my")
    public Location myLocation(@PathVariable("id") int id){
        User user = userService.findOne(id);
        return locationService.findByOwner(user);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserError error){
        UserErrorResponse response = new UserErrorResponse(error.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }
}
