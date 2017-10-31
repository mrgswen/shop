package com.eleven.shop.bean;

import java.util.List;

public class PagingList<T> {
	/**
	 * ��ʾ��ҳ����
	 */
	private static final int PAGE_COUNT = 4;
	private int pageNow;
	private int pageSize;
	private int total;
	@Override
	public String toString() {
		return "PagingList [pageNow=" + pageNow + ", pageSize=" + pageSize + ", total=" + total + ", totalPages="
				+ totalPages + ", rows=" + rows + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}

	private int totalPages;
	private List<T> rows;
	private int startPage = 1;
	private int endPage = PAGE_COUNT;

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public void setPagingList(int pageNow, int pageSize, int totalSize, List<T> data) {
		this.pageNow = pageNow;
		this.pageSize = pageSize;
		this.total = totalSize;
		this.rows = data;
		this.totalPages = (int) Math.ceil((double) totalSize / pageSize);
		int half = PAGE_COUNT / 2 - 1;
		if (pageNow > half) {
			startPage = pageNow - half;
			endPage = pageNow + half + 1;
		}
		if (endPage > totalPages) {
			if (totalPages > PAGE_COUNT)
				startPage = totalPages - PAGE_COUNT + 1;
			else
				startPage = 1;
			endPage = totalPages;
		}

	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int totalSize) {
		this.total = totalSize;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setPageNowDatas(List<T> pageNowDatas) {
		this.rows = pageNowDatas;
	}

}
