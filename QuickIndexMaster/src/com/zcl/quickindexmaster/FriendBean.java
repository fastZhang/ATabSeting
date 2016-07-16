package com.zcl.quickindexmaster;

public class FriendBean implements Comparable<FriendBean> {
	private String name;
	private String pinyin;

	// 使用成员变量生成构造方法：alt+shift+s->o
	public FriendBean(String name) {
		super();
		this.name = name;

		// 一开始就转化好拼音
		setPinyin(PinYinUtil.getPinyin(name));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(FriendBean another) {
		//字符串增序比较 
		return getPinyin().compareTo(another.getPinyin());
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

}
