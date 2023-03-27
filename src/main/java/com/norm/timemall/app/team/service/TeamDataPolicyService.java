package com.norm.timemall.app.team.service;

import org.springframework.stereotype.Service;

@Service
public interface TeamDataPolicyService {
    boolean passIfBrandIsCreatorOfOasis(String oasisId);
}
