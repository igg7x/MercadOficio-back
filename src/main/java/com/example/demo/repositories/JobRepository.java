package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Category;
import com.example.demo.models.Job;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

        List<Job> findByUserOfferingEmailAndReviewIsNotNull(String email);

        List<Job> findByCategoryAndStatusFalseAndDeletedFalse(Category category);

        @Query(value = "SELECT j.* FROM jobs j " +
                        "INNER JOIN categories c ON j.category_id = c.category_id " +
                        "INNER JOIN user_customers uc ON j.user_customer_id = uc.user_customer_id " +
                        "INNER JOIN users uss ON uc.user_id = uss.user_id " +
                        "WHERE c.category_name = :categoryName AND uss.is_banned = 0 AND uss.delete_at IS NULL AND j.status = 0 " +
                        "AND j.job_id NOT IN (" +
                        "    SELECT aj.job_id " +
                        "    FROM apply_jobs aj " +
                        "    JOIN user_offerings uo ON aj.user_offering_id = uo.user_offering_id " +
                        "    JOIN users u ON uo.user_id = u.user_id " +
                        "    WHERE u.email = :userOfferingEmail" +
                        ") " +
                        "AND j.deleted = 0 " +
                        "AND uss.user_id NOT IN (" +
                        "    SELECT uo.user_id FROM user_offerings uo " +
                        "    JOIN users u ON uo.user_id = u.user_id " +
                        "    WHERE u.email = :userOfferingEmail" +
                        ")", nativeQuery = true)
        List<Job> findByCategoriesAndUserOfferingEmail(
                        @Param("userOfferingEmail") String userOfferingEmail,
                        @Param("categoryName") String categoryName);

}
