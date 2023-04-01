package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    
    @Autowired
    private ClaimRepository claimRepository;
    
    @GetMapping
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Claim> getClaimById(@PathVariable(value = "id") Long claimId) {
        Claim claim = claimRepository.findById(claimId).orElse(null);
        if (claim == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(claim);
    }
    
    @PostMapping
    public Claim createClaim(@RequestBody Claim claim) {
        return claimRepository.save(claim);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Claim> updateClaim(@PathVariable(value = "id") Long claimId, @RequestBody Claim claimDetails) {
        Claim claim = claimRepository.findById(claimId).orElse(null);
        if (claim == null) {
            return ResponseEntity.notFound().build();
        }
        claim.setClaimNumber(claimDetails.getClaimNumber());
        claim.setDescription(claimDetails.getDescription());
        claim.setClaimDate(claimDetails.getClaimDate());
        claim.setClaimStatus(claimDetails.getClaimStatus());
        claim.setInsurancePolicy(claimDetails.getInsurancePolicy());
        Claim updatedClaim = claimRepository.save(claim);
        return ResponseEntity.ok(updatedClaim);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteClaim(@PathVariable(value = "id") Long claimId) {
        Claim claim = claimRepository.findById(claimId).orElse(null);
        if (claim == null) {
            return ResponseEntity.notFound().build();
        }
        claimRepository.delete(claim);
        return ResponseEntity.noContent().build();
    }
}
