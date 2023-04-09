package kz.kartayev.LocationSystem.contollers;

import kz.kartayev.LocationSystem.dto.LocationDTO;
import kz.kartayev.LocationSystem.models.Location;
import kz.kartayev.LocationSystem.services.LocationService;
import kz.kartayev.LocationSystem.services.UserService;
import kz.kartayev.LocationSystem.util.LocationValidator;
import kz.kartayev.LocationSystem.util.UserError;
import kz.kartayev.LocationSystem.util.UserErrorResponse;
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
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;
    private final ModelMapper mapper;
    private final LocationValidator validator;
    private final UserService userService;
    @Autowired
    public LocationController(LocationService locationService, ModelMapper mapper, LocationValidator validator, UserService userService) {
        this.locationService = locationService;
        this.mapper = mapper;
        this.validator = validator;
        this.userService = userService;
    }

    @GetMapping
    public List<Location> index(){
        return locationService.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid LocationDTO locationDTO, BindingResult bindingResult){
        Location location = convertToLocation(locationDTO);
        location.setOwner(userService.findOne(locationDTO.getOwner()));
        validator.validate(location, bindingResult);
        if(bindingResult.hasErrors()){
            getFieldErrors(bindingResult);
        }
        locationService.save(location);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserError error){
        UserErrorResponse response = new UserErrorResponse(error.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Location convertToLocation(LocationDTO dto){
        return mapper.map(dto, Location.class);
    }
}
