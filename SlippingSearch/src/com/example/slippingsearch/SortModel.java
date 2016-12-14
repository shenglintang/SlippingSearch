package com.example.slippingsearch;

import java.io.Serializable;

public class SortModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String name; // 显示的数据
	private String sortLetters; // 显示数据拼音的首字母
	private boolean isSelect;

	@Override
	public String toString() {
		return "SortModel [name=" + name + ", sortLetters=" + sortLetters + ", isSelect=" + isSelect + "]";
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

}
