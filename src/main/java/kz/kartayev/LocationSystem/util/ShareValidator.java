package kz.kartayev.LocationSystem.util;

import kz.kartayev.LocationSystem.models.Share;
import kz.kartayev.LocationSystem.services.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.List;

@Component
public class ShareValidator implements Validator {

    private final ShareService shareService;
    @Autowired
    public ShareValidator(ShareService shareService) {
        this.shareService = shareService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Share share = (Share)target;
        List<Share> shares = shareService.findAll();
        for(int i = 0; i < shares.size(); i++){
            if(shares.get(i).getSend_id() == share.getSend_id() &&
            shares.get(i).getGetter_id() == share.getGetter_id()&&
            shares.get(i).getLocation_id() == share.getLocation_id()){
                errors.rejectValue("getter_id", "","You are already "
                        + "shared this location for this user!");
            }
        }
        if(!share.getStatus().equals("ADMIN") || !share.getStatus().equals("READ")){
            errors.rejectValue("status", "", "You must choose the status!"
                    + " Ex: ADMIN or READ");
        }

    }
}
