package com.shopme.setting;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopme.common.entity.Setting;

@Component
public class SettingFilter implements Filter {

	@Autowired
	private SettingService settingService;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		
		HttpServletRequest serveltRequest = (HttpServletRequest) request;
		String url =serveltRequest.getRequestURI().toString();
		if(url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png") || url.endsWith(".jpg")) {
			chain.doFilter(request, response);
			return;
		}
	List<Setting> listSetting=	settingService.getGenerarSetting();
	listSetting.forEach(setting ->{
		request.setAttribute(setting.getKey(),setting.getValue());
	});
		chain.doFilter(request, response);
	}

}
