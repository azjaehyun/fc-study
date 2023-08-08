package com.projectsample.projectsample.imple.service;

import com.projectsample.projectsample.domain.SampleProject;
import com.projectsample.projectsample.impl.SampleProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SampleProjectService {
    @Autowired
    SampleProjectRepository sampleProjectRepository;

    public List<SampleProject> getAllSamples() {
        return sampleProjectRepository.findAll();
    }

    public SampleProject createSample(SampleProject sample) {
        return sampleProjectRepository.save(sample);
    }

    public List<SampleProject> createSamples(List<SampleProject> sample) {
        return sampleProjectRepository.saveAll(sample);
    }

    public SampleProject getSampleById(Long id) {
        return sampleProjectRepository.findById(id).orElse(null);
    }

    public void deleteSampleById(Long id) {
        sampleProjectRepository.deleteById(id);
    }
}
