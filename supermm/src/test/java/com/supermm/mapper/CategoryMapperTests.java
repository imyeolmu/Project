package com.supermm.mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.supermm.model.CategoryVO;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CategoryMapperTests {

	@Autowired
	private CategoryMapper mapper;
	
	// 카테고리등록
	@Test
	public void cateogryInputTest() throws Exception{
		
		CategoryVO cat = new CategoryVO();
		
	
		
		
		
	}
	
}
