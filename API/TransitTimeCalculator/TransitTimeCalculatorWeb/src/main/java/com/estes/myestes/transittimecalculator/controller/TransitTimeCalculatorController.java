/**
 * @author: chandye
 *
 * Creation date: 12/19/2018
 */

package com.estes.myestes.transittimecalculator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.dto.AddressPoint;
import com.estes.framework.exception.ESTESException;
import com.estes.framework.logger.ESTESLogger;
import com.estes.framework.services.pointsuggest.iface.AddressPointSuggestService;
import com.estes.myestes.transittimecalculator.dto.DestinationPoint;
import com.estes.myestes.transittimecalculator.dto.DestinationTerminal;
import com.estes.myestes.transittimecalculator.dto.Point;
import com.estes.myestes.transittimecalculator.dto.TransitTime;
import com.estes.myestes.transittimecalculator.dto.TransitTimeResponse;
import com.estes.myestes.transittimecalculator.exception.TransitTimeCalculatorException;
import com.estes.myestes.transittimecalculator.service.iface.TransitTimeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for rest service interfaces
 */
@RestController
@Api(value="Transit time calculator")
public class TransitTimeCalculatorController
{
	@Autowired
	TransitTimeService transitTimeService;
	
	@Autowired
	AddressPointSuggestService addressPointSuggestService;
	
	@ApiOperation(value = "Calculating transit time between given origin and detination")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=DestinationTerminal.class, responseContainer="List", message="Terminals servicing point"),
			@ApiResponse(code=400, responseContainer="List",response=ServiceResponse.class, message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/calculate", method = RequestMethod.POST)
	public ResponseEntity<?> calculateTransitTime(@RequestBody TransitTime transitTime)
	{
		List<ServiceResponse> errors = validate(transitTime);
		
		if(errors.size()>0){
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		
		TransitTimeResponse transitTimeResponse= null;

		try {
			transitTimeResponse = transitTimeService.calculateTransitTime(transitTime);
		} catch (TransitTimeCalculatorException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Return successful response
		return new ResponseEntity<TransitTimeResponse>(transitTimeResponse, HttpStatus.OK);
	}

	private List<ServiceResponse> validate(TransitTime transitTime) {
		Point origin = transitTime.getOriginpoint();
		List<ServiceResponse> errors = new ArrayList<>();
		if(isValidPoint(origin)==false){
			ServiceResponse error = new ServiceResponse();
			error.setErrorCode("error");
			error.setMessage("Invalid Origin Point Address: "+origin.toString());
			error.setBadData(origin.toString());
			errors.add(error);
		}
		if(transitTime.getDestinationPoints()!=null && transitTime.getDestinationPoints().size()>0){
			for(DestinationPoint destination : transitTime.getDestinationPoints()){
				if(isValidPoint(destination.getPoint())==false){
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode("error");
					error.setMessage("Invalid Destination Point Address: "+destination.getPoint().toString());
					error.setBadData(destination.getPoint().toString());
					errors.add(error);
				}
			}
		}else{
			ServiceResponse error = new ServiceResponse();
			error.setErrorCode("error");
			error.setMessage("At least one destination point is required.");
			errors.add(error);
		}
		
		return errors;
	}
	private boolean isValidPoint(Point point){
		
		AddressPoint addressPoint= new AddressPoint(point.getCountry(),point.getCity(),point.getState(),point.getZip());
		List<AddressPoint> results = null;
		
		try{
			results = addressPointSuggestService.getAddressPoint(addressPoint, 1);
		} catch (ESTESException e) {
			ESTESLogger.log(getClass(), "Could not validate point now");
			return true;
		}

		return results!=null && results.size()==1;
		
	}
}
