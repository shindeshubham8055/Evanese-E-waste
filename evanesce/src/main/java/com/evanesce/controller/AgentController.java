package com.evanesce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.evanesce.entity.Agent;
import com.evanesce.entity.Request;
import com.evanesce.service.AgentService;
import com.evanesce.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST Controller for managing Agent-related operations.
 */
@CrossOrigin
@RestController
public class AgentController {

	private static final Logger logger = LoggerFactory.getLogger(AgentController.class);

	@Autowired
	private AgentService agentService;

	@Autowired
	private RequestService reqService;

	/**
	 * Endpoint for agent login.
	 *
	 * @param agent The agent details (email and password).
	 * @return The authenticated Agent object.
	 */
	@PostMapping("/Agentlogin")
	public Agent loginAgent(@RequestBody Agent agent) {
		logger.info("Agent login endpoint accessed. Agent email: {}", agent.getEmail());
		return agentService.loginAgent(agent.getEmail(), agent.getPassword());
	}

	/**
	 * Endpoint for setting the status of an agent.
	 *
	 * @param id The agent ID.
	 * @return Status update confirmation message.
	 */
	@GetMapping("/setStatus/{id}")
	public String setStatusOfAgent(@PathVariable int id) {
		logger.info("Set status endpoint accessed for Agent ID: {}", id);
		return agentService.changeStatus(id);
	}

	/**
	 * Endpoint for setting the status of an agent and assigning a request.
	 *
	 * @param id The agent ID.
	 * @param request_id The request ID to assign.
	 * @return Confirmation message after assigning the request.
	 */
	@GetMapping("/setStatus/{id}/{request_id}")
	public String setStatusOfAgentAndSetRequest(@PathVariable int id, @PathVariable int request_id) {
		logger.info("Assigning request to agent - Agent ID: {}, Request ID: {}", id, request_id);
		return reqService.assignAgentIdToRequest(id, request_id);
	}

	/**
	 * Endpoint for viewing all requests assigned to a specific agent.
	 *
	 * @param id The agent ID.
	 * @return List of requests assigned to the agent.
	 */
	@GetMapping("/viewrequestbyagent/{id}")
	public List<Request> agentRequests(@PathVariable Agent id) {
		logger.info("Fetching requests for Agent ID: {}", id);
		return reqService.findAgentRequests(id);
	}

	/**
	 * Endpoint for finding agents by email.
	 *
	 * @param agent The agent details containing the email.
	 * @return List of agents matching the provided email.
	 */
	@PostMapping("/findagentbyemail")
	public List<Agent> findByEmail(@RequestBody Agent agent) {
		logger.info("Finding agent by email: {}", agent.getEmail());
		return agentService.findByEmail(agent.getEmail());
	}

	/**
	 * Endpoint for retrieving agents based on their city.
	 *
	 * @param city The city name.
	 * @return List of agents located in the specified city.
	 */
	@GetMapping("/city_wise_agents/{city}")
	public List<Agent> getAgentsCityWise(@PathVariable String city) {
		logger.info("Fetching agents in city: {}", city);
		return agentService.findByCity(city);
	}

	/**
	 * Endpoint for hiring a new agent.
	 *
	 * @param agent The agent details to be hired.
	 * @return The newly created Agent object.
	 */
	@PostMapping("/Hireagent")
	public Agent hireAgent(@RequestBody Agent agent) {
		logger.info("Hiring new agent: {}", agent);
		return agentService.hireAgent(agent);
	}

	/**
	 * Endpoint for retrieving all agents.
	 *
	 * @return List of all agents.
	 */
	@GetMapping("/getallagents")
	public List<Agent> getAllAgents() {
		logger.info("Fetching all agents.");
		return agentService.getAllAgents();
	}

	/**
	 * Endpoint for deleting an agent by ID.
	 *
	 * @param id The agent ID to be deleted.
	 * @return Confirmation message upon successful deletion.
	 */
	@DeleteMapping("/deleteagent/{id}")
	public String deleteAgent(@PathVariable int id) {
		logger.info("Deleting agent with ID: {}", id);
		agentService.deleteAgent(id);
		return "Agent deleted successfully.";
	}
}
