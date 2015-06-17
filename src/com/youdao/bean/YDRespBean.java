package com.youdao.bean;

import java.util.Arrays;

/**
 * Created by lingyfh on 15/6/15.
 */
public class YDRespBean {
	// 点击链接
	private String clk;
	// 点击跟踪 URL
	private String clktracker;
	// 展示跟踪 URL
	private String[] imptracker;
	// 小图
	private String iconimage;
	// 主图
	private String mainimage;
	// 文本
	private String text;
	// 标题
	private String title;

	public String getClk() {
		return clk;
	}

	public void setClk(String clk) {
		this.clk = clk;
	}

	public String getClktracker() {
		return clktracker;
	}

	public void setClktracker(String clktracker) {
		this.clktracker = clktracker;
	}

	public String[] getImptracker() {
		return imptracker;
	}

	public void setImptracker(String[] imptracker) {
		this.imptracker = imptracker;
	}

	public String getIconimage() {
		return iconimage;
	}

	public void setIconimage(String iconimage) {
		this.iconimage = iconimage;
	}

	public String getMainimage() {
		return mainimage;
	}

	public void setMainimage(String mainimage) {
		this.mainimage = mainimage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "YDRespBean{" + "clk='" + clk + '\'' + ", clktracker='"
				+ clktracker + '\'' + ", imptracker="
				+ Arrays.toString(imptracker) + ", iconimage='" + iconimage
				+ '\'' + ", mainimage='" + mainimage + '\'' + ", text='" + text
				+ '\'' + ", title='" + title + '\'' + '}';
	}
}
