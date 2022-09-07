package com.supermm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermm.model.ClientCriteria;
import com.supermm.model.ClientPageMakeDTO;
import com.supermm.model.Criteria;
import com.supermm.model.PageMakeDTO;
import com.supermm.model.ProductVO;
import com.supermm.service.ProductService;

@Controller("productController")
public class ProductController {

	@Autowired
	private ProductService service;

	//상품 목록 페이지 접속
	//	@RequestMapping(value = "/product-list", method = RequestMethod.GET)
	//	public String prodListForm(Model model) {
	//		
	//		System.out.println("상품 목록 페이지 접속");
	//		
	//		model.addAttribute("list", service.getProdList());
	//		
	//		return "admin/product/product-list";
	//	}	

	//상품 목록 페이지 접속(페이징 적용)
	@RequestMapping("/product-list")
	public String prodListForm(Criteria cri,Model model) {

		System.out.println("상품 목록 페이징");

		int totalCnt = service.getProdTotal(cri);

		// 페이징처리
		PageMakeDTO pageMake = new PageMakeDTO(cri, totalCnt);

		model.addAttribute("pageMake", pageMake);
		model.addAttribute("list", service.getProdListPaging(cri));
		//		System.out.println("list.........."+service.getProdListPaging(cri));

		return "admin/product/product-list";
	}	

	//상품 등록 페이지 접속
	@RequestMapping(value = "/product-input", method = RequestMethod.GET)
	public String ProductInputForm(Model model) throws Exception {

		System.out.println("상품 등록 페이지 접속");

		ObjectMapper objm = new ObjectMapper();

		List list = service.cateList();
		String cateList = objm.writeValueAsString(list);
		model.addAttribute("cateList", cateList);

		return "admin/product/product-input";
	}	

	//상품 등록
	@RequestMapping(value="/product-input", method = RequestMethod.POST)
	public String ProdInput(ProductVO prod, RedirectAttributes rttr) {

		System.out.println("상품 등록..");

		service.prodInput(prod);

		rttr.addFlashAttribute("result", "input success");

		return "redirect: /product-list";
	}

	//상품수정
	@RequestMapping(value="/product-update", method = RequestMethod.GET)
	public String prodUpdateGET(int pnum, Model model) {

		System.out.println("상품 수정페이지..");

		ProductVO prod = service.prodDetail(pnum);

		model.addAttribute("prod", prod);

		return "admin/product/product-update";
	}

	//상품수정
	@RequestMapping(value="/product-update", method = RequestMethod.POST)
	public String prodUpdatePOST(ProductVO prod, Model model) {

		System.out.println("상품 수정..");

		service.updateProduct(prod);
		model.addAttribute("prod", prod);

		return "redirect: /product-list";
	}

	//상품삭제
	@RequestMapping(value="/product-delete", method= RequestMethod.GET)
	public String prodDelete(String pnum) throws Exception {

		System.out.println("상품 삭제");

		service.prodDelete(pnum);

		return "admin/product/product-list";
	}

	//상품 선택삭제
	@RequestMapping(value="/product-delete")
	public String prodDeleteAjax(HttpServletRequest request) throws Exception {

		System.out.println("상품 선택 삭제");

		String[] ajaxMsg = request.getParameterValues("valueArr");

		int size = ajaxMsg.length;

		for(int i=0; i<size; i++) {
			System.out.println("i..."+i);
			service.prodDelete(ajaxMsg[i]);	
		}

		System.out.println("ajaxMsg.."+ajaxMsg);

		return "admin/product/product-list";
	}



	/********************* 클라이언트 ************************/

	//전체상품리스트
	@RequestMapping("/client-product-list")
	public String clientProductList(String pcategory_fk1, ClientCriteria cri, Model model, HttpSession session) {

		System.out.println("상품 목록 페이징");

		int totalCnt = service.getProdTotal(cri);

		// 페이징처리
		ClientPageMakeDTO pageMake = new ClientPageMakeDTO(cri, totalCnt);
		model.addAttribute("pageMake", pageMake);

		//상품
		model.addAttribute("list", service.getProdListPaging(cri));
		System.out.println("list.........."+service.getProdListPaging(cri));
		System.out.println("listsdfsdfsdfsdf.........."+service.prodSide(pcategory_fk1));

		//카테고리
		model.addAttribute("prodCateList", service.prodSide(pcategory_fk1));
		session.setAttribute("prodCateList", service.prodSide(pcategory_fk1));

		return "client/product/client-product-list";
	}	

	//카테고리별 상품리스트로 이동
	@RequestMapping(value="/client-product-list-category")
	public String cateProdLists(String pcategory_fk1, ClientCriteria cri, Model model) {
		System.out.println("상품 목록 페이징");

		int totalCnt = service.prodCateTotal(cri);

		// 페이징처리
		ClientPageMakeDTO pageMake = new ClientPageMakeDTO(cri, totalCnt);
		model.addAttribute("page", pageMake);

		//카테고리
		model.addAttribute("prodCateList", service.prodSide(pcategory_fk1));
		model.addAttribute("prodCate", service.prodCateList(cri));
		return "client/product/client-product-list-category";
	}	




}
