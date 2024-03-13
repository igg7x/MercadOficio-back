package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.Category;
import com.example.demo.models.UserCategories;
import com.example.demo.models.UserCategoriesKey;
import com.example.demo.models.UserOffering;
import com.example.demo.repositories.UserCategoriesRepository;

@Service
public class UserCategoriesService {

    private final UserCategoriesRepository userCategoriesRepository;

    public UserCategoriesService(UserCategoriesRepository userCategoriesRepository) {
        this.userCategoriesRepository = userCategoriesRepository;
    }

    public List<UserCategories> createUserCategories(List<Category> categories, UserOffering userOffering) {
        List<UserCategories> userCategories = new ArrayList<>();
        for (Category category : categories) {
            UserCategories userCategory = new UserCategories();
            UserCategoriesKey userCategoriesKey = new UserCategoriesKey(category.getCategoryId(),
                    userOffering.getUserOfferingId());
            userCategory.setId(userCategoriesKey);
            userCategory.setCategories(category);
            userCategory.setUserOffering(userOffering);
            userCategories.add(userCategoriesRepository.save(userCategory));
        }
        return userCategories;
    }

    public List<Category> getAllCategoriesFromUser(List<UserCategories> userCategories) {
        List<Category> categories = new ArrayList<>();
        for (UserCategories userCategory : userCategories) {
            categories.add(userCategory.getCategories());
        }
        return categories;
    }

}
