package com.shopme.admin.product;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Product;

@Service
@Transactional
public class ProductService {

	
	@Autowired
	private ProductRepository repo;
	
	public static final int PRODUCTS_PER_PAGE=4;
	
	public List<Product> listAll(){
		return (List<Product>) repo.findAll();
		
	}
	
	public Page<Product> listByPage(int pageNum, String sortField, String sortDir, String keyword,
			Integer categoryId) {
		Sort sort = Sort.by(sortField);
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
				
		Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE, sort);
		
		if (keyword != null && !keyword.isEmpty() ) {
			
			if(categoryId !=null && categoryId >0) {
				String categoryIdMatsh = "-" + String.valueOf(categoryId)+"-";
				return repo.searchInCategory(categoryId, categoryIdMatsh,keyword, pageable);
			}
			return repo.findAll(keyword, pageable);
		}
		if(categoryId !=null && categoryId >0) {
			String categoryIdMatsh = "-" + String.valueOf(categoryId)+"-";
			return repo.findAllInCategory(categoryId, categoryIdMatsh, pageable);
		}
		
		return repo.findAll(pageable);		
	}
	
	
	
	public Page<Product>  searchProducts(int pageNum, String sortField, String sortDir, String keyword) {
		
		
      Sort sort = Sort.by(sortField);
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
				
		Pageable pageable = PageRequest.of(pageNum - 1, 1, sort);
		
		if (keyword != null && !keyword.isEmpty() ) {
			return repo.searchProductsByName(keyword, pageable);
		}
		
			return repo.findAll(pageable);
		
		
	}
	


	
	
	
	
	public Product save(Product product) {
		if(product.getId() == null) {
			product.setCreatedTime(new Date());
		}
		if(product.getAlias() == null || product.getAlias().isEmpty()) {
			String defaultAlias = product.getName().replaceAll(" ", "-");
			product.setAlias(defaultAlias);
		}else {
			product.setAlias(product.getAlias().replaceAll(" ", "-"));
		}
		product.setUpdatedTime(new Date ());
		Product productInDB= repo.save(product);
		repo.updateReviewAverageRating(productInDB.getId());
		return productInDB;
		
	}
	
	public String isNameUnique(Integer id, String name) {
		boolean isCreatingNew = (id == null || id == 0);
		Product productByName = repo.findByName(name);
		
		if (isCreatingNew) {
			if (productByName != null) return "Duplicate";
		} else {
			if (productByName != null && productByName.getId() != id) {
				return "Duplicate";
			}
		}
		
		return "OK";
	}
	
	
	
	

	public Product get(Integer id) throws ProductNotFoundException  {
		// TODO Auto-generated method stub
		try {
			return repo.findById(id).get();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			throw new ProductNotFoundException("Not Found Excption"+id);
		}
	}
	
	public void delete(Integer id) throws ProductNotFoundException {
		Long countById= repo.countById(id);
		
		if(countById == null || countById == 0) {
			
			throw new ProductNotFoundException("Not Found Excption"+id);
			
		}
		repo.deleteById(id);
		
	}
	
	public void updateEnabledStatus(Integer id,boolean enabled) {
		
		repo.updateEnabledStatus(id, enabled);
	}

	
}
