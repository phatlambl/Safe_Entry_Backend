package com.example.demo.dto.device;

import java.util.List;

import com.example.demo.model.device.Device;

public class ListDeviceDto {

	private Long totalItems;
	private int currentPage;
	private int pageSize;
	private List<Device> listDevice;
	
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
	public List<Device> getListDevice() {
		return listDevice;
	}
	public void setListDevice(List<Device> listDevice) {
		this.listDevice = listDevice;
	}
	
	public ListDeviceDto(Long totalItems, int currentPage, int pageSize, List<Device> listDevice) {
		
		this.totalItems = totalItems;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.listDevice = listDevice;
	}
	public ListDeviceDto() {
		
	}
	
	
}
