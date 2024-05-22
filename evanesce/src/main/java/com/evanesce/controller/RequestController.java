package com.evanesce.controller;

import java.util.List;
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

	@Autowired
	private RequestService requestService;

	//User to Admins
	@PostMapping("/requests")
	public Request insertRequests(@RequestBody Request request) {
		System.out.println("\n@PostMapping(\"/requests\")");
		System.out.println("Request insertRequests(@RequestBody Request request)");
		System.out.println(request.getQuantity());
		return requestService.insertRequests(request);
	}

	@GetMapping("/requests")
	public List<Request> getAllRequests() {
		System.out.println("\n@GetMapping(\"/requests\")");
		System.out.println("List<Request> getAllRequests()");
		return requestService.getAllRequests();
	}

	@GetMapping("/requests/{remail}")
	public List<Request> getRequestsByEmail(@PathVariable String remail) {
		System.out.println("\n@GetMapping(\"/requests/{remail}\")");
		System.out.println("List<Request> getRequestsByEmail(@PathVariable String remail)");
		return requestService.getRequestsByEmail(remail);
	}

	@GetMapping("/getrequests/{rid}")
	public List<Request> getRequestsById(@PathVariable int rid) {
		System.out.println("\n@GetMapping(\"/getrequests/{rid}\")");
		System.out.println("List<Request> getRequestsById(@PathVariable int rid)");

		return requestService.getRequestById(rid);
	}

	@PostMapping("/pendingrequests")
	public List<Request> getPendingRequests(@RequestBody Request request) {
		System.out.println("\n@PostMapping(\"/pendingrequests\")");
		System.out.println("List<Request> getPendingRequests(@RequestBody Request request)");
		System.out.println("{\r\n"
				+ "    \"email\":\"user@gmail.com\",\r\n"
				+ "    \"boolean\":\"true\"\r\n"
				+ "}");
		return requestService.pendingRequests(request.getEmail(), false);
	}

	@GetMapping("/viewallpendingrequests")
	public List<Request> getPendingRequests(boolean status) {
		System.out.println("\n@GetMapping(\"/viewallpendingrequests\")");
		System.out.println("List<Request> getPendingRequests(boolean status)");
		return requestService.viewAllPendingRequests(false);
	}

	@GetMapping("/viewcollections")
	public List<Request> viewAllDonations(boolean status) {
		System.out.println("\n@GetMapping(\"/viewcollections\")");
		System.out.println("List<Request> viewAllDonations(boolean status)");
		return requestService.viewAllDonations(status);
	}

	@PostMapping("/viewdonations")
	public List<Request> viewDonations(@RequestBody Request request) {
		System.out.println("\n@PostMapping(\"/viewdonations\")");
		System.out.println(" List<Request> viewDonations(@RequestBody Request request)");
		System.out.println("By Default True => Send EmailID");
		return requestService.viewDonations(request.getEmail(), true);
	}

	//Agent Module request Card
	@PutMapping("/requests/{id}")
	public Request updateRequests(@PathVariable int id, @RequestBody Request request) {
		
		System.out.println("\n@PutMapping(\"/requests/{id}\")");
		System.out.println("Request updateRequests(@PathVariable int id, @RequestBody Request request)");
		System.out.println("IN UPDATE PAGE");
		System.out.print("In update Request : Agent id " + id + " Request " + request);
		return requestService.updateRequests(id, request);
	}

	@PutMapping("/pendingrequestbyUser")
	public Request updateRequestsByDonor(@RequestBody Request request) {
		System.out.println("\n@PutMapping(\"/pendingrequestbyUser\")");
		System.out.println("Request updateRequestsByDonor(@RequestBody Request request)");
		return requestService.updatePendingRequestByUser(request);
	}

	@PutMapping("/requestspay/{reqid}")
	public Request updateRequests(@PathVariable String reqid, @RequestBody Request request) {
		
		System.out.println("\n@PutMapping(\"/requestspay/{reqid}\")");
		System.out.println("Request updateRequests(@PathVariable String reqid, @RequestBody Request request)");
		System.out.print("In update Request : id" + reqid + " Request " + request);
		return requestService.updatePayment(request, Integer.parseInt(reqid));
	}

	@DeleteMapping("/requests/{rid}")
	public String deleteRequest(@PathVariable int rid) {
		System.out.println("\n@DeleteMapping(\"/requests/{rid}\")");
		System.out.println("String deleteRequest(@PathVariable int rid)");
		requestService.deleteRequest(rid);
		return "Deleted";
	}

	@GetMapping("/imageuploadstatus/{imgreqid}")
	public boolean imageuploadstatus(@PathVariable String imgreqid) {
		System.out.println("\n@GetMapping(\"/imageuploadstatus/{imgreqid}\")");
		System.out.println("boolean imageuploadstatus(@PathVariable String imgreqid)");
		System.out.println("http://localhost:8080/imageuploadstatus/52");
		int img_req_id = Integer.parseInt(imgreqid);
		return requestService.checkImageUploadStatus(img_req_id);

	}

}
