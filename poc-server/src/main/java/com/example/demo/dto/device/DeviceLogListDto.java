package com.example.demo.dto.device;

import java.util.List;

public class DeviceLogListDto {

	private Long totalItems;
	private int currentPage;
	private int pageSize;
	private List<DeviceLogDto> data;
	
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
	public List<DeviceLogDto> getData() {
		return data;
	}
	public void setData(List<DeviceLogDto> data) {
		this.data = data;
	}
	public DeviceLogListDto(Long totalItems, int currentPage, int pageSize, List<DeviceLogDto> data) {		
		this.totalItems = totalItems;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.data = data;
	}
	public DeviceLogListDto() {
		
	}
}
