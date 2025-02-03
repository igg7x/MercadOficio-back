package com.example.demo.services.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.example.demo.models.Report;
import com.example.demo.models.User;
import jakarta.persistence.criteria.Join;

public class ReportSpecifications {
    public static Specification<Report> findByReporterIdAndReporteredId(Long reporterId, Long reporteredId) {
        return (root, query, criteriaBuilder) -> {
            Join<Report, User> reporterJoin = root.join("reporterUserId");
            Join<Report, User> reporteredJoin = root.join("reportedUserId");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(reporterJoin.get("userId"), reporterId),
                    criteriaBuilder.equal(reporteredJoin.get("userId"), reporteredId),
                    criteriaBuilder.equal(root.get("reportStatus"), true));
        };
    }

    public static Specification<Report> findByReporteredId(Long userId) {

        return (root, query, criteriaBuilder) -> {
            Join<Report, User> reporteredJoin = root.join("reportedUserId");
            return criteriaBuilder.equal(reporteredJoin.get("userId"), userId);
        };
    }
}
