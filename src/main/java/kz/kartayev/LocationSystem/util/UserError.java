package kz.kartayev.LocationSystem.util;

public class UserError extends RuntimeException{
    public UserError(String message){
        super(message);
    }
}
