package base;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.act.services.WxServices;
import com.act.util.HttpUtil;
import com.act.util.JsonUtil;
import com.act.util.wx.Button;
import com.act.util.wx.Menu;


public class MenuTest extends TestEnviroment{
	
	String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	
	
	@Autowired
	WxServices wxService;
	
	public static void main(String[] s){
		System.out.println("http://101.201.209.109/ueweb/static/gacallday.html".getBytes().length);
	}
	
	
	@Test
	public void createMenu(){
		 
		
		Button[] mainButtons = new Button[3];
		mainButtons[0] = Button.newCompositorButton("产品", 
				new Button[]{
					Button.newViewButton("项目介绍", "http://101.201.209.109/ueweb/static/gacdesc.html"),
					Button.newViewButton("直通大学", "http://101.201.209.109/ueweb/static/gacclass.html"),
					Button.newViewButton("全日制班", "http://101.201.209.109/ueweb/static/gacallday.html"),
					Button.newViewButton("周末假期", "http://101.201.209.109/ueweb/static/sunday.html"),
					Button.newViewButton("过渡课程", "http://101.201.209.109/ueweb/static/haiwai.html")
				}
		);
		mainButtons[1] = Button.newCompositorButton(
				"微课堂",
				new Button[] { Button.newViewButton("在线课堂", "http://www.cctalk.com/liveroom/507975/"),
						Button.newViewButton("家长课堂", "http://www.cctalk.com/liveroom/509485/")
						}
				);
		mainButtons[2] = Button.newViewButton(
				"联系我们",null);
		
		Menu menu = new Menu(mainButtons);
		
		String param = JsonUtil.objectToJson(menu);
		
		System.out.println(param);
		
		String token = wxService.getWxToken();
		
		url = url + token;
		
		try {
			String result = HttpUtil.doPost(url, param);
			
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * 根据页面名称返回对应的重定向地址
	 * 
	 * @param pageName
	 * @return
	 */
	private static String getRedirectUrl(String pageName, String pageType,
			String appId, String serverUrl,String subCategory) {
		String redirect_action = null;
		String sub="";
		if(subCategory!=null){
			sub=subCategory+"%2F";
		}
		if (pageType != null) {
			redirect_action = "http%3A%2F%2F" + serverUrl
					+ "%2Ftcweixin%2Fletter%2F" + sub+pageName + ".html"
					+ "?pageType=" + pageType;
		} else {
			redirect_action = "http%3A%2F%2F" + serverUrl
					+ "%2Ftcweixin%2Fletter%2F" +sub+ pageName + ".html";
		}

		return "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + appId + "&redirect_uri=" + redirect_action
				+ "&response_type=code" + "&scope=snsapi_base";
	}

}
