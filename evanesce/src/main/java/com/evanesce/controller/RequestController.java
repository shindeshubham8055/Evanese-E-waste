package com.evanesce.controller;

import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evanesce.entity.Request;
import com.evanesce.service.RequestService;

@CrossOrigin
@RestController
public class RequestController {

	private static final Logger logger = Logger.getLogger(RequestController.class.getName());

	@Autowired
	private RequestService requestService;

	@PostMapping("/requests")
	public Request insertRequests(@RequestBody Request request) {
		logger.info("Handling POST request to insert a new request.");
		logger.info("Quantity: " + request.getQuantity());
		return requestService.insertRequests(request);
	}

	@GetMapping("/requests")
	public List<Request> getAllRequests() {
		logger.info("Handling GET request to retrieve all requests.");
		return requestService.getAllRequests();
	}

	@GetMapping("/requests/{remail}")
	public List<Request> getRequestsByEmail(@PathVariable String remail) {
		logger.info("Handling GET request to retrieve requests by email: " + remail);
		return requestService.getRequestsByEmail(remail);
	}

	@GetMapping("/getrequests/{rid}")
	public List<Request> getRequestsById(@PathVariable int rid) {
		logger.info("Handling GET request to retrieve request by ID: " + rid);
		return requestService.getRequestById(rid);
	}

	@PostMapping("/pendingrequests")
	public List<Request> getPendingRequests(@RequestBody Request request) {
		logger.info("Handling POST request to retrieve pending requests for email: " + request.getEmail());
		return requestService.pendingRequests(request.getEmail(), false);
	}

	@GetMapping("/viewallpendingrequests")
	public List<Request> getPendingRequests(boolean status) {
		logger.info("Handling GET request to view all pending requests.");
		return requestService.viewAllPendingRequests(false);
	}

	@GetMapping("/viewcollections")
	public List<Request> viewAllDonations(boolean status) {
		logger.info("Handling GET request to view all donations.");
		return requestService.viewAllDonations(status);
	}

	@PostMapping("/viewdonations")
	public List<Request> viewDonations(@RequestBody Request request) {
		logger.info("Handling POST request to view donations for email: " + request.getEmail());
		return requestService.viewDonations(request.getEmail(), true);
	}

	@PutMapping("/requests/{id}")
	public Request updateRequests(@PathVariable int id, @RequestBody Request request) {
		logger.info("Handling PUT request to update request with ID: " + id);
		return requestService.updateRequests(id, request);
	}

	@PutMapping("/pendingrequestbyUser")
	public Request updateRequestsByDonor(@RequestBody Request request) {
		logger.info("Handling PUT request to update pending request by donor.");
		return requestService.updatePendingRequestByUser(request);
	}

	@PutMapping("/requestspay/{reqid}")
	public Request updateRequests(@PathVariable String reqid, @RequestBody Request request) {
		logger.info("Handling PUT request to update payment for request ID: " + reqid);
		return requestService.updatePayment(request, Integer.parseInt(reqid));
	}

	@DeleteMapping("/requests/{rid}")
	public String deleteRequest(@PathVariable int rid) {
		logger.info("Handling DELETE request to remove request with ID: " + rid);
		requestService.deleteRequest(rid);
		return "Deleted";
	}

	@GetMapping("/imageuploadstatus/{imgreqid}")
	public boolean imageuploadstatus(@PathVariable String imgreqid) {
		logger.info("Handling GET request to check image upload status for request ID: " + imgreqid);
		int imgReqId = Integer.parseInt(imgreqid);
		return requestService.checkImageUploadStatus(imgReqId);
	}
}
