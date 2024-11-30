package com.evanesce.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evanesce.entity.Agent;
import com.evanesce.repository.AgentDao;
import java.util.function.Consumer; // Importing functional interface for modular code

@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	private AgentDao agentDao;

	@Override
	public Agent loginAgent(String email, String password) {
		// Directly calling the repository method to authenticate the agent by email and password
		return agentDao.findByEmailAndPassword(email, password);
	}

	@Override
	public Agent hireAgent(Agent a) {
		// Save the agent object to the database using the repository
		return agentDao.save(a);
	}

	@Override
	public List<Agent> getAllAgents() {
		// Fetching all agents from the database
		return agentDao.findAll();
	}

	@Override
	public void deleteAgent(int id) {
		// Fetching the agent by id and then deleting from the database
		Agent agent = agentDao.getReferenceById(id);
		agentDao.delete(agent);
	}

	@Override
	public List<Agent> findByEmail(String email) {
		// Fetching agents based on email address
		return agentDao.findByEmail(email);
	}

	@Override
	public List<Agent> findByCity(String city) {
		// Fetching agents based on their city
		return agentDao.findByCity(city);
	}

	// Admin Module - Assign Agent Status
	@Override
	public String changeStatus(int id) {
		// Fetching the agent by id to check and update their status
		Agent agent = agentDao.findById(id);

		// Using a functional interface (Consumer) to encapsulate the logic for updating agent status
		Consumer<Agent> updateAgentStatus = a -> {
			if (a.isIsFree()) {  // Updated from a.is_is_free() to a.isFree()
				// If the agent is free, change their status to 'not free' and save the updated status
				a.setIsFree(false); // Updated from setIs_free to setFree
				agentDao.save(a); // Save the updated agent object
			}
		};

		// Applying the status change logic to the agent
		updateAgentStatus.accept(agent);

		// Return a success message indicating the status has been changed
		return "Changed work Status";
	}
}
