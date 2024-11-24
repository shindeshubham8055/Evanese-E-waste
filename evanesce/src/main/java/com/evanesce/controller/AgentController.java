@CrossOrigin
@RestController
@RequestMapping("/agents") // Base path to improve URL readability
public class AgentController {

    @Autowired
    private AgentService agentService;
    @Autowired
    private RequestService reqService;

    private static final Logger logger = LoggerFactory.getLogger(AgentController.class);

    @PostMapping("/login")
    public ResponseEntity<?> loginAgent(@RequestBody @Valid Agent agent) {
        Agent result = agentService.loginAgent(agent.getEmail(), agent.getPassword());
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> setStatusOfAgent(@PathVariable int id) {
        logger.info("Updating status for Agent ID: {}", id);
        String status = agentService.changeStatus(id);
        return ResponseEntity.ok(status);
    }

    @PatchMapping("/{id}/assignRequest/{requestId}")
    public ResponseEntity<String> assignAgentToRequest(@PathVariable int id, @PathVariable int requestId) {
        logger.info("Assigning Request ID {} to Agent ID {}", requestId, id);
        String result = reqService.assignAgentIdToRequest(id, requestId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<List<Request>> getAgentRequests(@PathVariable int id) {
        logger.info("Fetching requests for Agent ID: {}", id);
        List<Request> requests = reqService.findAgentRequests(id);
        return ResponseEntity.ok(requests);
    }

    @PostMapping("/findByEmail")
    public ResponseEntity<List<Agent>> findByEmail(@RequestBody @Valid Agent agent) {
        List<Agent> agents = agentService.findByEmail(agent.getEmail());
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Agent>> getAgentsByCity(@PathVariable String city) {
        logger.info("Fetching agents in city: {}", city);
        List<Agent> agents = agentService.findByCity(city);
        return ResponseEntity.ok(agents);
    }

    @PostMapping("/hire")
    public ResponseEntity<Agent> hireAgent(@RequestBody @Valid Agent agent) {
        Agent hiredAgent = agentService.hireAgent(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(hiredAgent);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Agent>> getAllAgents() {
        List<Agent> agents = agentService.getAllAgents();
        return ResponseEntity.ok(agents);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAgent(@PathVariable int id) {
        agentService.deleteAgent(id);
        return ResponseEntity.ok("Agent deleted successfully");
    }
}
