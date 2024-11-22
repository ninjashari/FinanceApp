package com.finance.app.financeapp.service;

import com.finance.app.financeapp.dto.Category;

import java.util.List;

public interface CategoryService {
    public Category createCategory(Long userId, Category category);

    public List<Category> getCategoriesByUser(Long userId);

    public Category updateCategory(Long userId, Long categoryId, Category categoryDetails);

    public void deleteCategory(Long userId, Long categoryId);
}

