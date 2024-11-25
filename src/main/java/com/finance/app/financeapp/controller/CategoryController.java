package com.finance.app.financeapp.controller;

import com.finance.app.financeapp.dto.Category;
import com.finance.app.financeapp.service.CategoryService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A REST controller for handling HTTP requests related to category operations.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    Logger LOG = org.slf4j.LoggerFactory.getLogger(CategoryController.class);
    /**
     * Service responsible for managing category-related operations.
     * This service is autowired into the controller to facilitate dependency injection.
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * Creates a new category linked to a specific user.
     *
     * @param userId   the ID of the user to whom the category will be linked
     * @param category the category details to be created
     * @return a ResponseEntity containing the created category
     */
    @PostMapping("/{userId}")
    public ResponseEntity<Category> createCategory(@PathVariable Long userId, @RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(userId, category);
        LOG.info("Category created: {}", createdCategory);
        return ResponseEntity.ok(createdCategory);
    }

    /**
     * Retrieves a list of categories associated with a specific user.
     *
     * @param userId the ID of the user whose categories are to be retrieved
     * @return a ResponseEntity containing a list of categories associated with the specified user
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<Category>> getCategoriesByUser(@PathVariable Long userId) {
        List<Category> categories = categoryService.getCategoriesByUser(userId);
        LOG.info("Categories retrieved: {}", categories);
        return ResponseEntity.ok(categories);
    }

    /**
     * Updates an existing category for a given user.
     *
     * @param userId          the ID of the user who owns the category
     * @param categoryId      the ID of the category to be updated
     * @param categoryDetails the new details for the category
     * @return a ResponseEntity containing the updated category
     */
    @PutMapping("/{userId}/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId,
            @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(userId, categoryId, categoryDetails);
        LOG.info("Category updated: {}", updatedCategory);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Deletes a category associated with a specific user.
     *
     * @param userId     the ID of the user to whom the category is linked
     * @param categoryId the ID of the category to be deleted
     * @return a ResponseEntity containing a success message
     */
    @DeleteMapping("/{userId}/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long userId, @PathVariable Long categoryId) {
        categoryService.deleteCategory(userId, categoryId);
        LOG.info("Category deleted: {}", categoryId);
        return ResponseEntity.ok("Category deleted successfully");
    }
}
