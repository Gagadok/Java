package com.planetmanagement;

import com.planetmanagement.domain.Overlord;
import com.planetmanagement.repos.OverlordRep;
import static java.lang.Integer.parseInt;
import java.util.Map;
import javax.crypto.AEADBadTagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 *
 * @author Gagadok
 */
@Controller
public class WebController {
    @Autowired
    private OverlordRep overlordRep;

    @PostMapping
    public String AddOverlord(@RequestParam String overlord_name, @RequestParam String overlord_age, Map<String, Object> model) {
        Overlord overlord = new Overlord(overlord_name,  parseInt(overlord_age));
        overlordRep.save(overlord);
        
        Iterable<Overlord> overlords = overlordRep.findAll();
        model.put("overlords", overlords);
        
        return "main";
    }
    
    @GetMapping
    public String ShowOverlords(Map<String, Object> model){
        Iterable<Overlord> overlords = overlordRep.findAll();
        model.put("overlords", overlords);
        
        return "main";
    }
}
