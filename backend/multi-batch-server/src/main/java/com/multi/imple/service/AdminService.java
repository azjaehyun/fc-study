package com.multi.imple.service;

import com.multi.domain.admin.Admin;
import com.multi.impl.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    public List<Admin> getAllSamples() {
        return adminRepository.findAll();
    }

    public Admin createSample(Admin sample) {
        return adminRepository.save(sample);
    }

    public List<Admin> createSamples(List<Admin> sample) {
        return adminRepository.saveAll(sample);
    }

    public Admin getSampleById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    public void deleteSampleById(Long id) {
        adminRepository.deleteById(id);
    }
}
