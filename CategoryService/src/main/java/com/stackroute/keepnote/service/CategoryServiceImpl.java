package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class CategoryServiceImpl implements CategoryService {

	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	private CategoryRepository categoryRepository;
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository)
	{
		this.categoryRepository = categoryRepository;
	}

	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	public Category createCategory(Category category) throws CategoryNotCreatedException {
		Optional<Category> optional =  categoryRepository.findById(category.getId());
		Category newCategory = categoryRepository.insert(category);
		if(newCategory==null)
		{
			throw new CategoryNotCreatedException("Not created");
		}
		else
		{
			return newCategory;
		}
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {
		boolean flag = true;
//
		Optional<Category> optional = categoryRepository.findById(categoryId);
		if(optional.isPresent())
		{
			categoryRepository.deleteById(categoryId);
			flag = true;
		}
		else
		{
			throw new CategoryDoesNoteExistsException("Category Does not exist");
		}
		return flag;
	}

	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {
		Optional<Category> optional =  categoryRepository.findById(category.getId());
		if(optional.isPresent())
		{
			String id = category.getId();
			String name = category.getCategoryName();
			String description = category.getCategoryDescription();
			String created = category.getCategoryCreatedBy();
			Date date = category.getCategoryCreationDate();
			Category newCategory = new Category(id,name,description,created,date);
			categoryRepository.save(newCategory);

			return category;
		}
		else
		{
			return category;
		}
	}

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {
		try{
			Optional<Category> optional =  categoryRepository.findById(categoryId);
			return optional.get();
		}
		catch (NoSuchElementException e)
		{
			throw new CategoryNotFoundException("Category not found");
		}


	}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		return categoryRepository.findAllCategoryByCategoryCreatedBy(userId);

	}

}
