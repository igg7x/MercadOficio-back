package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// import java.util.Set;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.Job.CreateJobDTO;
import com.example.demo.DTO.Job.JobDTO;
import com.example.demo.DTO.Job.UpdateJobDTO;
import com.example.demo.Exceptions.JobNotFoundException;
import com.example.demo.models.Category;
import com.example.demo.models.Job;
import com.example.demo.models.Notification;
import com.example.demo.models.Notification.TypesNotification;
import com.example.demo.models.Review;
import com.example.demo.models.UserCustomer;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.JobRepository;
import com.example.demo.repositories.ReviewRepository;
import com.example.demo.services.mapper.Job.JobMapper;
import com.example.demo.services.specifications.JobSpecifications;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final UserCustomerService userCustomerService;
    private final CategoryService categoryService;
    private final JobMapper jobMapper;
    private final UserOfferingService userOfferingService;
    // private final EmailService emailService;
    private final NotificationService notificationService;
    private final ReviewRepository reviewRepository;

    public Job findJobById(String id) {
        Job job = jobRepository.findOne(JobSpecifications.findByJobIdAndDeletedAtIsNull(id)).orElse(null);
        if (job == null) {
            throw new JobNotFoundException("Trabajo no encontrado");
        }
        return job;
    }

    public JobDTO getJobById(String id) {
        Job job = findJobById(id);
        return jobMapper.JobtoJobDTO(job);
    }

    public Optional<Job> getJobByUserCustomerAndUserOffering(String userCustomerEmail, String userOfferingEmail) {
        UserCustomer userCustomer = userCustomerService.getUserCustomer(userCustomerEmail);
        return jobRepository
                .findOne(JobSpecifications.findByUserCustomerEmailAndStatusIsFalse(userCustomer.getUserCustomerId(),
                        userOfferingEmail));
    }

    @Transactional
    public JobDTO createJob(CreateJobDTO createJobDTO, String userCutomerEmail) {

        // if (userCutomerEmail.equals(createJobDTO.getUserCustomerEmail())) {
        // throw new IllegalArgumentException("Error");
        // }

        UserCustomer userCustomer = userCustomerService.getUserCustomer(createJobDTO.getUserCustomerEmail());
        Category category = categoryService.getCategory(createJobDTO.getCategory());
        Job job = jobMapper.CreateJobDTOtoJob(createJobDTO, userCustomer, category);
        Job jobCreated = jobRepository.save(job);

        List<UserOffering> userOfferings = userOfferingService.getUserOfferingsByCategory(category.getCategoryName(),
                userCutomerEmail);
        notifyUsersAboutNewJob(userOfferings, jobCreated);
        return jobMapper.JobtoJobDTO(jobCreated);
    }

    private void notifyUsersAboutNewJob(List<UserOffering> userOfferings, Job job) {
        List<String> usersEmailsToNotify = userOfferings.stream().map(userOffering -> userOffering.getUser().getEmail())
                .toList();
        Notification notificationNewJob = notificationService.createNotification("Nuevo Trabajo Disponible",
                TypesNotification.INFO, "El usuario: "
                        + job.getUserCustomer().getUser().getEmail()
                        + " ha publicado un nuevo trabajo en la categoria: "
                        + job.getCategory().getCategoryName());

        notificationService.sendNotifications(usersEmailsToNotify, notificationNewJob, job.getJobId());
    }

    @Transactional
    public JobDTO updateJob(String jobId, UpdateJobDTO updateJobDTO) {
        Job job = findJobById(jobId);
        Job jobToUpdate = jobMapper.UpdateJobDTOtoJob(updateJobDTO, job);
        Job jobUpdated = jobRepository.save(jobToUpdate);
        return jobMapper.JobtoJobDTO(jobUpdated);
    }

    @Transactional
    public JobDTO deleteJob(String jobId) {
        Job job = findJobById(jobId);
        job.setDeleted(true);
        jobRepository.save(job);
        Set<Review> reviews = job.getReviews();
        for (Review review : reviews) {
            review.setDeleted_at(LocalDateTime.now());
            reviewRepository.save(review);
        }
        // Set<ApplyJobs> applications = job.getApplyJobs();
        // for (ApplyJobs application : applications) {
        // emailService.sendEmail(application.getUserOffering().getUser().getEmail(),
        // "Trabajo Eliminado",
        // "El trabajo: " + job.getTitle() + " ha sido eliminado por el usuario: "
        // + job.getUserCustomer().getUser().getEmail());
        // }
        return jobMapper.JobtoJobDTO(job);
    }

    // public Page<ReviewDTO> getReviewsByUserOffering(String userOfferingEmail,
    // Pageable pageable) {
    // List<Job> jobs =
    // jobRepository.findByUserOfferingEmailAndReviewIsNotNull(userOfferingEmail);
    // List<ReviewDTO> reviewDTOs = jobMapper.ReviewListToReviewDTOList(jobs,
    // userOfferingEmail);
    // return new PageImpl<>(reviewDTOs, pageable, jobs.size());
    // }
    // Obtengo todos los JOBS que tengan las categorias que se pasan por parametro y
    // esten disponibles
    // (status = false) y no esten eliminados (deleted = false)

    public Page<JobDTO> getAllJobs(Pageable pageable, String userOfferingEmail) {
        List<Category> categoryList = userOfferingService.getUserOffering(userOfferingEmail).getUserCategories();
        List<Job> jobs = new ArrayList<>();
        for (Category category : categoryList) {
            jobs.addAll(
                    jobRepository.findByCategoriesAndUserOfferingEmail(userOfferingEmail, category.getCategoryName()));
        }
        Page<JobDTO> jobDTOsPage = new PageImpl<>(jobMapper.JobListToJobDTOList(jobs), pageable, jobs.size());
        return jobDTOsPage;

    }

    public Page<JobDTO> getAllHistorialJobsByUserCustomerEmail(String userCustomerEmail, Pageable pageable) {
        UserCustomer userCustomer = userCustomerService.getUserCustomer(userCustomerEmail);
        Page<Job> jobsPage = jobRepository
                .findAll(JobSpecifications.findByUserCustomerEmailAndStatusIsTrue(userCustomer.getUserCustomerId()),
                        pageable);
        Page<JobDTO> jobDTOsPage = jobsPage.map(jobMapper::JobtoJobDTO);

        return jobDTOsPage;
    }

    public Page<JobDTO> getAllActiveJobsByUserCustomerEmail(String userCustomerEmail, Pageable pageable) {
        UserCustomer userCustomer = userCustomerService.getUserCustomer(userCustomerEmail);
        Page<Job> jobsPage = jobRepository
                .findAll(
                        JobSpecifications.findByUserCustomerEmailAndStatusIsFalse(
                                userCustomer.getUserCustomerId()),
                        pageable);
        Page<JobDTO> jobDTOsPage = jobsPage.map(jobMapper::JobtoJobDTO);

        return jobDTOsPage;
    }

    public Page<JobDTO> getAllHistorialJobsByUserOfferingEmail(String userOfferingEmail, Pageable pageable) {
        Page<Job> jobsPage = jobRepository
                .findAll(JobSpecifications.findHistorialForUserOffering(userOfferingEmail), pageable);
        // List<Job> jobsList = jobRepository
        // .findHistorialJobsApplicationsByUserOfferingEmail(userOfferingEmail);
        // Page<Job> jobsPage = new PageImpl<>(jobsList, pageable, jobsList.size());

        Page<JobDTO> jobDTOsPage = jobsPage.map(jobMapper::JobtoJobDTO);
        return jobDTOsPage;
    }

    public long getJobsCountByCategory(String category) {
        return jobRepository.count(JobSpecifications.findByCategoryAndStatusIsFalse(category));
    }

    public long countTotalActiveJobs() {
        return jobRepository.count(JobSpecifications.findByStatusIsFalse());
    }
}
