package hu.ps.ss.data.pojo;

import java.util.List;

public class PageResult<T> {

	private List<T> items;

	private PageDetails page;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public PageDetails getPage() {
		return page;
	}

	public void setPage(PageDetails page) {
		this.page = page;
	}
}
