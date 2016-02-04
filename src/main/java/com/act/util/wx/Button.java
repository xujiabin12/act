package com.act.util.wx;

/**
 * 微信按钮菜单的封装。
 * 理论上，应该分为ClickButton，ViewButton等，但是为了方便json反序列化，统一抽象为一个类。
 * 同时通过私有的构造函数和静态创建方法，避免错误的操作。
 * 
 * @author rikky.cai
 * @qq:6687523
 * @Email:6687523@qq.com
 *
 */
public class Button
{
	public static enum ButtonType{click, view}
	
	private String name;
	private ButtonType type;
    private String key;
    private String url;
    private Button[] sub_button;
    
    private Button()
    {
    	
    }
    
    private Button(String name, ButtonType type, String key, String url,
			Button[] sub_button)
	{
		this.name = name;
		this.type = type;
		this.key = key;
		this.url = url;
		this.sub_button = sub_button;
	}

	public static Button newClickButton(String name, String key)
    {
    	return new Button(name, ButtonType.click, key, null, null);
    }
	
	public static Button newViewButton(String name, String url)
	{
		return new Button(name, ButtonType.view, null, url, null);
	}
	
	public static Button newCompositorButton(String name, Button[] sub_button)
	{
		return new Button(name, null, null, null, sub_button);
	}
	
    public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public ButtonType getType()
	{
		return type;
	}
	public void setType(ButtonType type)
	{
		this.type = type;
	}
	public String getKey()
	{
		return key;
	}
	public void setKey(String key)
	{
		this.key = key;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	public Button[] getSub_button()
	{
		return sub_button;
	}
	public void setSub_button(Button[] sub_button)
	{
		this.sub_button = sub_button;
	}
	
	
    
}
