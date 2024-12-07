package com.evanesce.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evanesce.entity.Agent;
import com.evanesce.repository.AgentDao;

@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	private AgentDao agentDao;

	@Override
	public Agent loginAgent(String email, String password) {
		return agentDao.findByEmailAndPassword(email, password);
	}

	@Override
	public Agent hireAgent(Agent agent) {
		return agentDao.save(agent);
	}

	@Override
	public List<Agent> getAllAgents() {
		return agentDao.findAll();
	}

	@Override
	public void deleteAgent(int id) {
		agentDao.deleteById(id); // Directly delete using ID
	}

	@Override
	public List<Agent> findByEmail(String email) {
		return agentDao.findByEmail(email);
	}

	@Override
	public List<Agent> findByCity(String city) {
		return agentDao.findByCity(city);
	}

	@Override
	public String changeStatus(int id) {
		Optional<Agent> optionalAgent = agentDao.findById(id);

		if (optionalAgent.isPresent()) {
			Agent agent = optionalAgent.get();
			if (agent.isFree()) {
				agent.setIsFree(false); // Update status
				agentDao.save(agent); // Save updated entity
				return "Changed work status successfully.";
			}
		}
		return "Agent not found or status unchanged.";
	}
}
