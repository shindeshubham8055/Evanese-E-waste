package com.evanesce.service;

import java.util.List;
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
	public Agent hireAgent(Agent a) {
		return agentDao.save(a);
	}

	@Override
	public List<Agent> getAllAgents() {
		return this.agentDao.findAll();
	}

	@Override
	public void deleteAgent(int id) {
		Agent agent = agentDao.getReferenceById(id);
		agentDao.delete(agent);

	}

	@Override
	public List<Agent> findByEmail(String email) {
		return agentDao.findByEmail(email);
	}

	@Override
	public List<Agent> findByCity(String city) {
		return agentDao.findByCity(city);
	}

	//Admin Module - Assign Agent
	@Override
	public String changeStatus(int id) {
		Agent agent = agentDao.findById(id);
		if (agent.isIs_free() == true) {
			agent.setIs_free(false);
			System.out.println(agent.toString());
			agentDao.save(agent);
		}
		return "Changed work Status";
	}

}