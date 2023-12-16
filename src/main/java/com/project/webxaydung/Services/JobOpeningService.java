package com.project.webxaydung.Services;

import com.project.webxaydung.Dto.JobOpeningDTO;
import com.project.webxaydung.Models.JobOpening;
import com.project.webxaydung.Repositories.JopOpeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class JobOpeningService {
    private  final JopOpeningRepository jopOpeningRepository;

    public JobOpening insertJobopening(JobOpeningDTO jobOpenningDTO){

        JobOpening newJob= JobOpening
                .builder()
                .title(jobOpenningDTO.getTitle())
                .description(jobOpenningDTO.getDescription())
                .requirement(jobOpenningDTO.getRequirement())
                .start_day(jobOpenningDTO.getStart_day())
                .end_day(jobOpenningDTO.getEnd_day())
                .hiring_needs(jobOpenningDTO.getHiring_needs())
                .vacancies(jobOpenningDTO.getVacancies())
                .build();
        return jopOpeningRepository.save(newJob);
    }
    public List<JobOpening> getAllJobs(){
        return jopOpeningRepository.findAll();
    }
    public JobOpening getJobById(int id) {
        return jopOpeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }
    public List<JobOpening> getJobAvailable() {
        LocalDate currentDate = LocalDate.now();
        List<JobOpening> allJobOpenings = jopOpeningRepository.findAll();

        for (JobOpening jobOpening : allJobOpenings) {
            if (jobOpening.getIs_active() == 0) {
                continue;
            } else {
                LocalDate endDay = jobOpening.getEnd_day().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if (endDay.isBefore(currentDate) || jobOpening.getVacancies() == 0) {
                    jobOpening.setIs_active(0);
                    jopOpeningRepository.save(jobOpening); // Lưu cập nhật vào cơ sở dữ liệu
                }
            }
        }

        return jopOpeningRepository.findAvailableJobOpenings( new Date());
    }
    public JobOpening updateJobOpening(int id, JobOpeningDTO jobOpenningDTO) {
        // Kiểm tra xem ID có hợp lệ hay không
        if (!jopOpeningRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy công việc với ID: " + id);
        }

        // Lấy công việc từ cơ sở dữ liệu
        JobOpening existingJob = jopOpeningRepository.getOne(id);

        // Kiểm tra và cập nhật các trường không null từ DTO
        if (jobOpenningDTO.getTitle() != null) {
            existingJob.setTitle(jobOpenningDTO.getTitle());
        }
        if (jobOpenningDTO.getDescription() != null) {
            existingJob.setDescription(jobOpenningDTO.getDescription());
        }
        if (jobOpenningDTO.getRequirement() != null) {
            existingJob.setRequirement(jobOpenningDTO.getRequirement());
        }
        if (jobOpenningDTO.getStart_day() != null) {
            existingJob.setStart_day(jobOpenningDTO.getStart_day());
        }
        if (jobOpenningDTO.getEnd_day() != null) {
            existingJob.setEnd_day(jobOpenningDTO.getEnd_day());
        }
        if (jobOpenningDTO.getHiring_needs() != null) {
            existingJob.setHiring_needs(jobOpenningDTO.getHiring_needs());
        }
        if (jobOpenningDTO.getVacancies() != null) {
            existingJob.setVacancies(jobOpenningDTO.getVacancies());
        }
        return jopOpeningRepository.save(existingJob);
    }
    public void deletedjob(int  id) {
        jopOpeningRepository.deleteById(id);
    }

}
