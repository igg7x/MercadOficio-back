package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.Review.ReviewDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.models.Category;
import com.example.demo.models.User;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.UserOfferingRepository;
import com.example.demo.services.mapper.Category.CategoryMapper;
import com.example.demo.services.mapper.User.UserMapper;
import com.example.demo.services.specifications.UserOfferingSpecifications;

import jakarta.transaction.Transactional;

@Service
public class UserOfferingService {

    private final UserService userService;
    private final CategoryService categoryService;
    private final UserMapper userMapperService;
    private final UserOfferingRepository userOfferingRepository;
    private final CategoryMapper categoryMapper;
    @Lazy
    @Autowired
    private ReviewService reviewService;

    public UserOfferingService(UserOfferingRepository userOfferingRepository,
            UserMapper userMapperService, CategoryService categoryService,
            CategoryMapper categoryMapper, UserService userService) {
        this.userOfferingRepository = userOfferingRepository;
        this.userMapperService = userMapperService;
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
        this.userService = userService;
    }

    public UserOffering getUserOffering(String email) {
        User user = userService.findByEmail(email);
        UserOffering userOffering = userOfferingRepository.findByUser(user).orElse(null);
        if (userOffering == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User Offering not found");
        }
        return userOffering;
    }

    public UserOfferingDTO getUserOfferingByEmail(String email, Pageable pageable) {
        User user = userService.findByEmail(email);

        UserOffering userOffering = userOfferingRepository.findByUser(user).orElse(null);
        if (userOffering == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User Offering not found");
        }

        List<CategorieDTO> userCategories = categoryService.getCategoriesFromUser(userOffering);
        Page<ReviewDTO> userReviews = reviewService.getReviewsByUserOffering(user.getEmail(), pageable);

        return userMapperService.UserOfferingtoUserOfferingDTO(userOffering, user,
                userCategories, userReviews.getContent());
    }

    public Page<UsersOfferingDTO> getUsersOffering(Pageable pageable) {
        Specification<UserOffering> spec = Specification
                .where(null);
        List<UserOffering> userOfferingList = userOfferingRepository.findAll(spec);
        List<UsersOfferingDTO> userOfferingDTOs = userMapperService
                .UserOfferingListtoUserOfferingDTOList(userOfferingList); // ADD the category List in each
        // object of the list
        return new PageImpl<>(userOfferingDTOs, pageable, userOfferingList.size());
    }

    public Page<UsersOfferingDTO> getUsersOfferingByCriteria(Map<String, String> searchCriteria, Pageable pageable) {
        Specification<UserOffering> spec = Specification
                .where(null);

        if (StringUtils.hasLength(searchCriteria.get("category"))) {
            spec = spec.and(UserOfferingSpecifications.filterByCategory(searchCriteria.get("category")));
        }
        if (StringUtils.hasLength(searchCriteria.get("location"))) {
            spec = spec.and(UserOfferingSpecifications.filterByLocation(searchCriteria.get("location")));
        }
        if (StringUtils.hasLength(searchCriteria.get("calification"))) {
            spec = spec
                    .and(UserOfferingSpecifications
                            .filterByCalification(Integer.parseInt(searchCriteria.get("minCalification")),
                                    Integer.parseInt(searchCriteria.get("maxCalification"))));
        }

        List<UserOffering> userOfferingList = userOfferingRepository.findAll(spec);

        List<UsersOfferingDTO> userOfferingDTOs = userMapperService
                .UserOfferingListtoUserOfferingDTOList(userOfferingList); // ADD the category List in each
        // object of the list
        return new PageImpl<>(userOfferingDTOs, pageable, userOfferingList.size());
    }

    @Transactional
    public UserOfferingDTO createUserOffering(CreateUserOfferingDTO createUserOfferingDto, String email) {

        User user = userService.findByEmail(email);
        List<Category> userCategories = categoryService.getAllCategories(createUserOfferingDto.getUserCategories());

        if (userCategories.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Category not found");
        }
        UserOffering userOfferingCreated = userMapperService.CreateUserOfferingDTOtoUserOffering(createUserOfferingDto,
                user, userCategories);

        userOfferingCreated = userOfferingRepository.save(userOfferingCreated);
        List<ReviewDTO> reviews = new ArrayList<>();
        return userMapperService.UserOfferingtoUserOfferingDTO(userOfferingCreated, user,
                categoryMapper.CategoryListtoCategoryDTOList(userCategories), reviews);
    }

    @Transactional
    public UserOfferingDTO updateUserOffering(String email, UpdateUserOfferingDTO userOfferingDTO) {
        User user = userService.findByEmail(email);

        UserOffering userOffering = userOfferingRepository.findByUser(user).orElse(null);
        if (userOffering == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User Offering not found");
        }

        userOffering = userMapperService.updateUserOfferingFromDTO(userOfferingDTO,
                userOffering);

        categoryService.updateCategories(userOfferingDTO, userOffering);

        userOffering = userOfferingRepository.save(userOffering);
        return userMapperService.UserOfferingtoUserOfferingDTO(userOffering, user,
                categoryMapper.CategoryListtoCategoryDTOList(userOffering.getUserCategories()), null);
    }
}
