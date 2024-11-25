package com.finance.app.financeapp.service.impl;

import com.finance.app.financeapp.dto.Category;
import com.finance.app.financeapp.dto.User;
import com.finance.app.financeapp.repository.CategoryRepository;
import com.finance.app.financeapp.repository.UserRepository;
import com.finance.app.financeapp.service.CategoryService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service implementation for managing category-related operations.
 * <p>
 * This class provides concrete implementations for creating,
 * retrieving, updating, and deleting categories associated with users.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    Logger LOG = org.slf4j.LoggerFactory.getLogger(CategoryServiceImpl.class);
    /**
     * Repository for performing CRUD operations on Category entities.
     * <p>
     * This repository is used to interact with the database layer and
     * provides methods to save, retrieve, and delete Category entities.
     * It also includes custom methods such as finding categories by user ID.
     */
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Repository for performing CRUD operations on User entities.
     * This repository is used to interact with the database layer
     * and provides methods to save, retrieve, and delete User entities.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new category for a specific user.
     *
     * @param userId   The ID of the user for whom the category is being created.
     * @param category The category object to be created.
     * @return The newly created category.
     */
    @Override
    public Category createCategory(Long userId, Category category) {
        LOG.info("Creating category for user: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        category.setUser(user);
        category.setCreatedAt(new Date());
        category.setUpdatedAt(new Date());
        return categoryRepository.save(category);
    }

    /**
     * Retrieves a list of categories associated with a specific user.
     *
     * @param userId the ID of the user whose categories are to be retrieved
     * @return a list of Category entities associated with the specified user
     */
    @Override
    public List<Category> getCategoriesByUser(Long userId) {
        return categoryRepository.findByUserId(userId);
    }

    /**
     * Updates the details of an existing category for a specified user.
     *
     * @param userId          the ID of the user who owns the category
     * @param categoryId      the ID of the category to be updated
     * @param categoryDetails the new details to update the category with
     * @return the updated Category entity
     * @throws RuntimeException if the category is not found or the user is unauthorized to update the category
     */
    @Override
    public Category updateCategory(Long userId, Long categoryId, Category categoryDetails) {
        LOG.info("Updating category: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to access this category");
        }

        category.setName(categoryDetails.getName());
        category.setType(categoryDetails.getType());
        category.setUpdatedAt(new Date());
        return categoryRepository.save(category);
    }

    /**
     * Deletes a category for a given user by the specified category ID.
     *
     * @param userId     the ID of the user attempting to delete the category
     * @param categoryId the ID of the category to be deleted
     * @throws RuntimeException if the category is not found or if the user is not authorized to delete the category
     */
    @Override
    public void deleteCategory(Long userId, Long categoryId) {
        LOG.info("Deleting category: {}", categoryId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (!category.getUser().getId().equals(userId)) {
            LOG.info("Unauthorized to delete this category");
            throw new RuntimeException("Unauthorized to delete this category");
        }

        categoryRepository.delete(category);
        LOG.info("Category deleted");
    }
}
