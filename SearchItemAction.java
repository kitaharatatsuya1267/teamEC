package com.internousdev.i1810a.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.i1810a.dao.MCategoryDAO;
import com.internousdev.i1810a.dao.ProductInfoDAO;
import com.internousdev.i1810a.dto.MCategoryDTO;
import com.internousdev.i1810a.dto.PaginationDTO;
import com.internousdev.i1810a.dto.ProductInfoDTO;
import com.internousdev.i1810a.util.InputChecker;
import com.internousdev.i1810a.util.Pagination;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware{
	private String categoryId;
	private String keywords;
	private String pageNo;
	private Map<String, Object> session;
	public String execute() {

		String result = ERROR;

		session.remove("currentPageNo");
		List<ProductInfoDTO> productInfoDtoList = new ArrayList<ProductInfoDTO>();
		List<MCategoryDTO> mCategoryDtoList = new ArrayList<MCategoryDTO>();
		List<String> keywordsErrorMessageList = new ArrayList<String>();
		InputChecker inputChecker = new InputChecker();
		if (StringUtils.isWhitespace(keywords)) {
			keywords ="";
		}
		String space = keywords.replaceAll("[　 ]+", " ").trim();
		String[] keywordsH = space.split(" ");
		if(!StringUtils.isEmpty(keywords)) {
			keywordsErrorMessageList = inputChecker.checkPattern("検索キーワード", keywords.replaceAll("[　 ]+","").trim(), 0, 16, "半角英数字","漢字","ひらがな","カタカナ");
		}
		
		if (keywordsErrorMessageList.contains("検索キーワードは0文字以上16文字以下で入力してください。")) {
			int i = keywordsErrorMessageList.indexOf("検索キーワードは0文字以上16文字以下で入力してください。");
			String str = keywordsErrorMessageList.get(i);
			keywordsErrorMessageList.set(i, str.substring(0, 8)+str.substring(13));
		}

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		switch (categoryId) {
			case "1":
				productInfoDtoList = productInfoDAO.getProductInfoListAll(keywordsH);
				result = SUCCESS;
				break;
			default:
				productInfoDtoList = productInfoDAO.getProductInfoListByKeywords(keywordsH, categoryId);
				result = SUCCESS;
				break;
		}
		Iterator<ProductInfoDTO> iterator = productInfoDtoList.iterator();
		if(!(iterator.hasNext())) {
			productInfoDtoList = null;
		}
		session.put("keywordsErrorMessageList", keywordsErrorMessageList);

		if(!session.containsKey("mCategoryList")) {
			MCategoryDAO mCategoryDao = new MCategoryDAO();
			mCategoryDtoList = mCategoryDao.getMCategoryList();
			session.put("mCategoryDtoList", mCategoryDtoList);
		}
		if(!(productInfoDtoList==null)) {
			Pagination pagination = new Pagination();
			PaginationDTO paginationDTO = new PaginationDTO();
			if(pageNo==null) {
				paginationDTO = pagination.initialize(productInfoDtoList, 9);
			}else {
				paginationDTO = pagination.getPage(productInfoDtoList, 9, pageNo);
			}
			session.put("productInfoDtoList", paginationDTO.getCurrentProductInfoPage());
			session.put("totalPageSize", paginationDTO.getTotalPageSize());
			session.put("currentPageNo", paginationDTO.getCurrentPageNo());
			session.put("totalRecordSize", paginationDTO.getTotalRecordSize());
			session.put("startRecordNo", paginationDTO.getStartRecordNo());
			session.put("endRecordNo", paginationDTO.getEndRecordNo());
			session.put("previousPage", paginationDTO.isHasPreviousPage());
			session.put("previousPageNo", paginationDTO.getPreviousPageNo());
			session.put("nextPage", paginationDTO.isHasNextPage());
			session.put("nextPageNo", paginationDTO.getNextPageNo());

		}else {
			session.put("productInfoDtoList", null);
		}
		return result;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}