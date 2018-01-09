package com.cto.edu;

import com.cto.edu.blog.core.blogCategory.repository.BlogCategoryRepository;
import com.cto.edu.blog.entity.blogCategory.BlogCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApiBootJpaDataApplicationTests {

	@Autowired
	private BlogCategoryRepository blogCategoryRepository;

	@Test
	public void findAll() {
		List<BlogCategory> categoryList = blogCategoryRepository.findAll();
		System.out.println(categoryList.size());
	}

}
