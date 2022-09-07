package com.supermm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supermm.mapper.CategoryMapper;
import com.supermm.model.CategoryVO;
import com.supermm.model.Criteria;


@Service // 스프링이 해당 클래스가 service 역할을 하는 클래스로 인식하게함
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;
	
	// 카테고리 등록
	@Override
	public void cateInput(CategoryVO cvo) {
		
		System.out.println("service..categoryInput..");
		
		categoryMapper.cateInput(cvo);
	}
	
	// 카테고리 목록(페이징)
	@Override 
	public List<CategoryVO> cateListPaging(Criteria cri){
		System.out.println("service...cateList...");
		
		return categoryMapper.cateListPaging(cri);
	}
	// 카테고리 목록
	@Override 
	public List<CategoryVO> cateList(){
		System.out.println("service...cateList...");
		
		return categoryMapper.cateList();
	}
	
	// 카테고리 삭제
	@Override 
	public void cateDelete(String cateNum) {
		categoryMapper.cateDelete(cateNum);
	}
	// 카테고리 수정
	@Override
	public void cateUpdate(CategoryVO cvo) {
		categoryMapper.cateUpdate(cvo);
	}
	// 카테고리 수정(상세보기)
	@Override
	public CategoryVO cateDetail(String cateNum){
	    return categoryMapper.cateDetail(cateNum);
	 }
	
	// 전체리스트 개수
	@Override
	public int totalCnt(Criteria cri) {
		
		return categoryMapper.totalCnt(cri);
	}

	
	
}