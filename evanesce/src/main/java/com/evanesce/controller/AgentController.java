package com.evanesce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.evanesce.entity.Agent;
import com.evanesce.entity.Request;
import com.evanesce.service.AgentService;
import com.evanesce.service.RequestService;

@CrossOrigin
@RestController
public class AgentController {

	@Autowired
	private AgentService agentService;
	@Autowired
	private RequestService reqService;

	@PostMapping("/Agentlogin")
	public Agent loginAgent(@RequestBody Agent agent) {
		System.out.println("\n@PostMapping(\"/Agentlogin\")");
		System.out.println("loginAgent(@RequestBody Agent agent)");
		return agentService.loginAgent(agent.getEmail(), agent.getPassword());
	}

	//Admin Module - Assign Agent
	@GetMapping("/setStatus/{id}")
	public String setStatusOfAgent(@PathVariable int id) {
		System.out.println("\n@GetMapping(\"/setStatus/{id}\")s");
		System.out.println(" in set Status Method" + "request_id");
		System.out.println("AGENT ID" + id);
		return agentService.changeStatus(id);
	}

	@GetMapping("/setStatus/{id}/{request_id}")
	public String setStatusofAgentandSetRequest(@PathVariable int id, @PathVariable int request_id) {
		System.out.println("\n@GetMapping(\"/setStatus/{id}/{request_id}\")");
		System.out.println("setStatusofAgentandSetRequest(@PathVariable int id, @PathVariable int request_id)");
		System.out.println("AGENT ID" + id + "REQUEST ID" + request_id);
		return reqService.assignAgentIdToRequest(id, request_id);
	}

	//Agent Module - Card Agent Home viewAllRequests
	@GetMapping("/viewrequestbyagent/{id}")
	public List<Request> agentRequests(@PathVariable Agent id) {
		System.out.println("\n@GetMapping(\"/viewrequestbyagent/{id}\")");
		System.out.println("List<Request> agentRequests(@PathVariable Agent id)");
		System.out.println("AGENT ID" + id);
		return reqService.findAgentRequests(id);
	}

	@PostMapping("/findagentbyemail")
	public List<Agent> findByEmail(@RequestBody Agent agent) {
		System.out.println("\n@PostMapping(\"/findagentbyemail\")");
		System.out.println("List<Agent> findByEmail(@RequestBody Agent agent)");
		return agentService.findByEmail(agent.getEmail());
	}

	@GetMapping("/city_wise_agents/{city}")
	public List<Agent> getAgentsCityWise(@PathVariable String city) {
		System.out.println("\n@GetMapping(\"/city_wise_agents/{city}\")");
		System.out.println("List<Agent> getAgentsCityWise(@PathVariable String city) ");
		System.out.println("In City Wise Agents Apis");
		return agentService.findByCity(city);
	}

	// Admin - HireAgent
	@PostMapping("/Hireagent")
	public Agent hireAgent(@RequestBody Agent agent) {
		System.out.println("\n@PostMapping(\"/Hireagent\")");
		System.out.println("Agent hireAgent(@RequestBody Agent agent)");
		System.out.println(agent);
		return agentService.hireAgent(agent);
	}

	// Admin - Get All Agents
	@GetMapping("/getallagents")
	public List<Agent> getAllAgents(Agent agent) {
		System.out.println("\n@GetMapping(\"/getallagents\")");
		System.out.println("List<Agent> getAllAgents(Agent agent)");
		return agentService.getAllAgents();
	}

	// Admin - Delete Agent
	@DeleteMapping("deleteagent/{id}")
	public String deleteUser(@PathVariable int id) {
		System.out.println("\n@DeleteMapping(\"deleteagent/{id}\")");
		System.out.println("String deleteUser(@PathVariable int id)");
		agentService.deleteAgent(id);
		return "Deleted";
	}
}