package com.example.demo.dto.user;

import java.util.List;

public class ListUser {
	
	private Long totalItems;
	private int currentPage;
	private int pageSize;
	private List<UserDto> listUser;
	
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
	public List<UserDto> getListUser() {
		return listUser;
	}
	public void setListUser(List<UserDto> listUser) {
		this.listUser = listUser;
	}
	public ListUser(Long totalItems, int currentPage, int pageSize, List<UserDto> listUser) {		
		this.totalItems = totalItems;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.listUser = listUser;
	}
	public ListUser() {
		
	}
	
	
	

	
	
}
