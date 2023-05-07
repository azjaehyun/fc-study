package com.multi.imple.service;

import com.multi.domain.admin.AdminUser;
import com.multi.impl.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {
    @Autowired
    AdminUserRepository adminUserRepository;

    public List<AdminUser> getAllSamples() {
        return adminUserRepository.findAll();
    }

    public AdminUser createSample(AdminUser sample) {
        return adminUserRepository.save(sample);
    }

    public List<AdminUser> createSamples(List<AdminUser> sample) {
        return adminUserRepository.saveAll(sample);
    }

    public AdminUser getSampleById(Long id) {
        return adminUserRepository.findById(id).orElse(null);
    }

    public void deleteSampleById(Long id) {
        adminUserRepository.deleteById(id);
    }
}
