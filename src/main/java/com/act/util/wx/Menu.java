package com.act.util.wx;


/**
 * 菜单定义
 * 
 * @author rikky.cai
 * @qq:6687523
 * @Email:6687523@qq.com
 *
 */
public class Menu
{
	private Button[] button;
	
	public Menu()
	{
	}
	
	public Menu(Button[] button)
	{
		this.button = button;
	}

	public Button[] getButton()
	{
		return button;
	}

	public void setButton(Button[] button)
	{
		this.button = button;
	}
}
