package kz.kartayev.LocationSystem.contollers;

import kz.kartayev.LocationSystem.dto.LocationDTO;
import kz.kartayev.LocationSystem.dto.UserDTO;
import kz.kartayev.LocationSystem.models.Access;
import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.models.User;
import kz.kartayev.LocationSystem.services.LocationService;
import kz.kartayev.LocationSystem.services.UserService;
import kz.kartayev.LocationSystem.util.LocationValidator;
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

    private final ModelMapper modelMapper;
    private final UserValidator validator;
    private final LocationValidator locationValidator;

    private final LocationService locationService;
    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserValidator validator , LocationValidator locationValidator, LocationService locationService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.locationValidator = locationValidator;
        this.locationService = locationService;
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
    public List<Location> myLocation(@PathVariable("id") int id){
        User user = userService.findOne(id);
        return user.getLocationList();
    }
    @PostMapping("{id}/new")
    public ResponseEntity<HttpStatus> newLocation(@PathVariable("id") int id, @RequestBody @Valid LocationDTO locationDTO,
                                                  BindingResult bindingResult){
        Location location = convertToLocation(locationDTO);
        locationValidator.validate(location, bindingResult);
        if(bindingResult.hasErrors()){
            getFieldErrors(bindingResult);
        }
        location.setOwner(userService.findOne(id));
        location.setStatus(String.valueOf(Access.OWNER));
        locationService.save(location);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserError error){
        UserErrorResponse response = new UserErrorResponse(error.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    private Location convertToLocation(LocationDTO locationDTO){
        return modelMapper.map(locationDTO, Location.class);
    }
}
