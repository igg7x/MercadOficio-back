package com.example.demo.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.DTO.Categories.CategorieDTO;
import com.example.demo.DTO.User.UpdateUserDTO;
import com.example.demo.DTO.User.Offering.CreateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UpdateUserOfferingDTO;
import com.example.demo.DTO.User.Offering.UserOfferingCriteriaDTO;
import com.example.demo.DTO.User.Offering.UserOfferingDTO;
import com.example.demo.DTO.User.Offering.UsersOfferingDTO;
import com.example.demo.Exceptions.CategoryNotFoundException;
import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.auth.Roles;
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
            // return null;
            throw new UserNotFoundException("User Offering not found");
        }
        return userOffering;
    }

    public UserOfferingDTO getUserOfferingByEmail(String email, Pageable pageable) {

        UserOffering userOffering = getUserOffering(email);
        List<CategorieDTO> userCategories = categoryService.getCategoriesFromUser(userOffering);

        return userMapperService.UserOfferingtoUserOfferingDTO(userOffering.getUser(),
                userCategories);
    }

    public Page<UsersOfferingDTO> getUsersOffering(Pageable pageable) {
        Specification<UserOffering> spec = Specification
                .where(null);
        Page<UserOffering> userOfferingPage = userOfferingRepository.findAll(spec, pageable);
        Page<UsersOfferingDTO> userOfferingDTOPage = userOfferingPage.map(
                userOffering -> userMapperService.UserOfferingtoUserOfferingDTO(userOffering)); // ADD the category List
                                                                                                // in each
        // object of the list
        return userOfferingDTOPage;
    }

    public Page<UsersOfferingDTO> getUsersOfferingByCriteria(String userOfferingEmail,
            UserOfferingCriteriaDTO searchCriteria, Pageable pageable) {
        Specification<UserOffering> spec = Specification
                .where(UserOfferingSpecifications.isNotBanned()).and(
                        UserOfferingSpecifications.excludeUser(userOfferingEmail))
                .and(UserOfferingSpecifications.deletedAtIsNull());

        if (searchCriteria.getCategory() != null) {
            spec = spec.and(UserOfferingSpecifications.filterByCategory(searchCriteria.getCategory()));
        }
        if (searchCriteria.getLocation() != null) {
            spec = spec.and(UserOfferingSpecifications.filterByLocation(searchCriteria.getLocation()));
        }
        if (searchCriteria.getMinCalification() != null || searchCriteria.getMaxCalification() != null) {

            Integer minCalification = searchCriteria.getMinCalification() != null ? searchCriteria.getMinCalification()
                    : null;
            Integer maxCalification = searchCriteria.getMaxCalification() != null ? searchCriteria.getMaxCalification()
                    : null;

            spec = spec
                    .and(UserOfferingSpecifications
                            .filterByCalification(minCalification, maxCalification));
        }

        Page<UserOffering> userOfferingPage = userOfferingRepository.findAll(spec, pageable);

        Page<UsersOfferingDTO> usersOfferingDTOpage = userOfferingPage.map(
                userOffering -> userMapperService.UserOfferingtoUserOfferingDTO(userOffering));
        return usersOfferingDTOpage;
    }

    @Transactional
    public UserOfferingDTO createUserOffering(CreateUserOfferingDTO createUserOfferingDto, String email) {

        User user = userService.findByEmail(email);
        List<Category> userCategories = categoryService.getAllCategories(createUserOfferingDto.getUserCategories());

        if (userCategories.isEmpty()) {
            throw new CategoryNotFoundException("Category not found");
        }

        UserOffering userOfferingCreated = userMapperService.CreateUserOfferingDTOtoUserOffering(createUserOfferingDto,
                user, userCategories);

        userOfferingCreated = userOfferingRepository.save(userOfferingCreated);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        Set<Roles> roles = user.getRoles();
        roles.add(Roles.USER_OFFERING);
        updateUserDTO.setNewRoles(roles);
        userService.updateUser(email, updateUserDTO);

        return userMapperService.UserOfferingtoUserOfferingDTO(user,
                categoryMapper.CategoryListtoCategoryDTOList(userCategories));

    }

    @Transactional
    public UserOfferingDTO updateUserOffering(String email, UpdateUserOfferingDTO userOfferingDTO) {
        User user = userService.findByEmail(email);

        UserOffering userOffering = userOfferingRepository.findByUser(user).orElse(null);
        if (userOffering == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User Offering not found");
        }

        if (userOfferingDTO.getCategories() != null) {
            categoryService.updateCategories(userOfferingDTO, userOffering);
        }

        if (userOfferingDTO.getCalification() != null) {

            Integer reviewCount = reviewService.getReviewCount(user);
            Integer calificationUpdated = updateCalification(userOffering, userOfferingDTO.getCalification(),
                    reviewCount);
            userOffering.setCalification(calificationUpdated);
        }

        userOffering = userOfferingRepository.save(userOffering);

        UserOfferingDTO userUpdated = userMapperService.UserOfferingtoUserOfferingDTO(user,
                categoryMapper.CategoryListtoCategoryDTOList(userOffering.getUserCategories()));
        return userUpdated;
    }

    public List<UserOffering> getUserOfferingsByCategory(String category, String userEmail) {
        Specification<UserOffering> spec = Specification
                .where(UserOfferingSpecifications.isNotBanned()).and(
                        UserOfferingSpecifications.excludeUser(userEmail));

        List<UserOffering> userOfferings = userOfferingRepository
                .findAll(spec.and(UserOfferingSpecifications.filterByCategory(category)));
        return userOfferings;
    }

    @Transactional
    private Integer updateCalification(UserOffering userOffering, Integer newCalification, Integer reviewCount) {
        Integer currentCalification = userOffering.getCalification() != null ? userOffering.getCalification() : 0;
        Integer updatedCalification = ((currentCalification + newCalification) % 5  ) + 1;

        return updatedCalification;
    }

    public List<Category> getCategoriesByUserOfferingId(Long userOfferingId) {
        List<Category> categories = userOfferingRepository.findCategoriesByUserOfferingId(userOfferingId);
        return categories;
    }

    public Integer getUsersCountByCategory(String categoryName) {
        List<UserOffering> userOfferings = userOfferingRepository
                .findAll(UserOfferingSpecifications.filterByCategory(categoryName));
        return userOfferings.size();
    }

    public long getTotalUsersOfferings() {
        return userOfferingRepository.count();
    }

}
