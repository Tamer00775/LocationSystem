package kz.kartayev.LocationSystem.contollers;

import kz.kartayev.LocationSystem.dto.AccessDTO;
import kz.kartayev.LocationSystem.dto.LocationDTO;
import kz.kartayev.LocationSystem.dto.ShareDTO;
import kz.kartayev.LocationSystem.dto.UserDTO;
import kz.kartayev.LocationSystem.models.Access;
import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.models.Share;
import kz.kartayev.LocationSystem.models.User;
import kz.kartayev.LocationSystem.services.LocationService;
import kz.kartayev.LocationSystem.services.ShareService;
import kz.kartayev.LocationSystem.services.UserService;
import kz.kartayev.LocationSystem.util.*;
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
    private final ShareService shareService;
    private final ShareValidator shareValidator;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, UserValidator validator , LocationValidator locationValidator, LocationService locationService, ShareService shareService, ShareValidator shareValidator) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.locationValidator = locationValidator;
        this.locationService = locationService;
        this.shareService = shareService;
        this.shareValidator = shareValidator;
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
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid UserDTO userDTO,
                                          BindingResult bindingResult){
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

    @GetMapping("/{id}/friends")
    public List<User> myFriends(@PathVariable("id") int id){
        return userService.findFriend(id);
    }
    @GetMapping("/{id}/friends/{friend_id}")
    public User myFriends(@PathVariable("id") int id, @PathVariable("friend_id") int friend_id){
        return userService.findOne(friend_id);
    }
    @PostMapping("/{id}/friends/{friend_id}/manage")
    public ResponseEntity<HttpStatus> manageStatus(@PathVariable("id") int id,
                                                   @PathVariable("friend_id") int friend_id,
                                                   @RequestBody @Valid AccessDTO accessDTO,
                                                   BindingResult bindingResult){
        // TODO : Validation Access with Admin, Read, Owner
        if(bindingResult.hasErrors())
            getFieldErrors(bindingResult);
        shareService.manageAccess(id, friend_id, accessDTO.getAccess(), accessDTO.getLocation_id());
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping("/{id}/closure")
    public List<User> closures(@PathVariable("id") int id,
                               @RequestBody LocationDTO locationDTO, BindingResult bindingResult){
        List<Location> locations = locationService.locationStartsWith(locationDTO.getLocation());
        return userService.getAllFriendOnLocation(id, locations);
    }

    @PostMapping("/{id}/my/{idLoc}/share")
    public ResponseEntity<HttpStatus> share(@PathVariable("id") int id,
                                            @PathVariable("idLoc") int locId,
                                            @RequestBody @Valid ShareDTO shareDTO, BindingResult bindingResult){
        Share share = convertToShareDTO(shareDTO);
        share.setLocation_id(locId);
        share.setSend_id(id);
        shareValidator.validate(share, bindingResult);
        if(bindingResult.hasErrors())
            getFieldErrors(bindingResult);
        userService.share(share);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @PostMapping("{id}/new")
    public ResponseEntity<HttpStatus> newLocation(@PathVariable("id") int id,
                                                  @RequestBody @Valid LocationDTO locationDTO,
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
    private Share convertToShareDTO(ShareDTO shareDTO){
        return modelMapper.map(shareDTO, Share.class);
    }
}
