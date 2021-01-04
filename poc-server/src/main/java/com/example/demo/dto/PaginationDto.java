package com.example.demo.dto;

import java.util.List;

/*
 *DTO pagination
 * */
public class PaginationDto {

	private Long totalItems;
	private int currentPage;
	private int pageSize;
	private List<Object> data;
	
	public Long getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
	
	public PaginationDto(Long totalItems, int currentPage, int pageSize, List<Object> data) {
		super();
		this.totalItems = totalItems;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.data = data;
	}
	
	public PaginationDto() {
		
	}
	
	
	
	
	
}
