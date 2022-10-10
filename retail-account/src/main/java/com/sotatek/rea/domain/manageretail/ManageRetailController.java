package com.sotatek.rea.domain.manageretail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sotatek.rea.infrastructure.model.Retail;
import com.sotatek.rea.infrastructure.util.ResponseData;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("retail")
public class ManageRetailController {

	@Autowired
	private ManageRetailService manageRetailService;
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseData<?> retailAdd(@RequestBody Retail retail) throws Exception {
        return manageRetailService.add(retail);
    }
	
	@RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResponseData<?> retailUpdate(@RequestBody Retail retail) throws Exception {
        return manageRetailService.update(retail);
    }
	
	@RequestMapping(value = "findByRetailId", method = RequestMethod.GET)
    public ResponseData<?> retailUpdate(@RequestParam Long retailId) throws Exception {
        return manageRetailService.findByRetailId(retailId);
    }
}
