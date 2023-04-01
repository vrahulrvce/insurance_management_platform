package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/policies")
public class InsurancePolicyController {

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    @GetMapping
    public List<InsurancePolicy> getAllPolicies() {
        return insurancePolicyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsurancePolicy> getPolicyById(@PathVariable(value = "id") Long policyId) {
        Optional<InsurancePolicy> optionalPolicy = insurancePolicyRepository.findById(policyId);
        return optionalPolicy.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InsurancePolicy> createPolicy(@RequestBody InsurancePolicy policy) {
        InsurancePolicy savedPolicy = insurancePolicyRepository.save(policy);
        return ResponseEntity.created(URI.create("/api/policies/" + savedPolicy.getId())).body(savedPolicy);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsurancePolicy> updatePolicy(@PathVariable(value = "id") Long policyId,
                                                        @RequestBody InsurancePolicy policy) {
        Optional<InsurancePolicy> optionalPolicy = insurancePolicyRepository.findById(policyId);
        if (optionalPolicy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        policy.setId(policyId);
        InsurancePolicy updatedPolicy = insurancePolicyRepository.save(policy);
        return ResponseEntity.ok(updatedPolicy);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePolicy(@PathVariable(value = "id") Long policyId) {
        Optional<InsurancePolicy> optionalPolicy = insurancePolicyRepository.findById(policyId);
        if (optionalPolicy.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        insurancePolicyRepository.deleteById(policyId);
        return ResponseEntity.noContent().build();
    }
}
