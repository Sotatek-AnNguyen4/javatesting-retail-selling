package com.sotatek.rea.domain.manageretail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.rea.infrastructure.model.Retail;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("retail/")
public class ManageRetailController {

	@Autowired
	private ManageRetailService manageRetailService;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public Retail retailAdd(@RequestBody Retail retail) throws Exception {
		Retail result = manageRetailService.add(retail);
		if(result == null) {
			throw new Exception();
		}
        return result;
    }
	
	@RequestMapping(value = "update", method = RequestMethod.PUT)
    public Retail retailUpdate(@RequestBody Retail retail) throws Exception {
		Retail result = manageRetailService.update(retail);
		if(result == null) {
			throw new Exception();
		}
        return result;
    }
	
	@RequestMapping(value = "findByRetailId", method = RequestMethod.GET)
    public Retail retailUpdate(@RequestParam Long retailId) throws Exception {
		Retail result = manageRetailService.findByRetailId(retailId);
		if(result == null) {
			throw new Exception();
		}
        return result;
    }
}
