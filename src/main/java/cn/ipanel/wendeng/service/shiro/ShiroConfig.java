package cn.ipanel.wendeng.service.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.ipanel.wendeng.service.dao.RoleRepository;
import cn.ipanel.wendeng.service.entity.RoleInfo;
import cn.ipanel.wendeng.service.global.Globals;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 作者 z77z
 * date 创建时间：2017年2月10日 下午1:16:38
 * 
 */
@Configuration
public class ShiroConfig {

	@Resource
	private RoleRepository roleRepository;
	
	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。
	 * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 *
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 *
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		// 如果不设置，默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/loginSuccess");
		shiroFilterFactoryBean.setUnauthorizedUrl("/401.html");

		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/data/**", "anon");
		filterChainDefinitionMap.put("/dist/**", "anon");
		filterChainDefinitionMap.put("/json/**", "anon");
		filterChainDefinitionMap.put("/vendor/**", "anon");
		filterChainDefinitionMap.put("/bower_components/**", "anon");
		filterChainDefinitionMap.put("/**/*.css", "anon");
		filterChainDefinitionMap.put("/**/*.js", "anon");
		filterChainDefinitionMap.put("/**/*.woff*", "anon");
		filterChainDefinitionMap.put("/**/*.ttf", "anon");
		filterChainDefinitionMap.put("/**/*.png", "anon");
		filterChainDefinitionMap.put("/**/*.gif", "anon");
		filterChainDefinitionMap.put("/v2/api-docs", "anon");
		filterChainDefinitionMap.put("/swagger**", "anon");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/login", "anon");
		filterChainDefinitionMap.put("/login.html", "anon");
		filterChainDefinitionMap.put("/auth/login", "anon");
		filterChainDefinitionMap.put("/ipanel/**", "anon");
		filterChainDefinitionMap.put("/service/clean", "anon");
		// 从数据库加载所有链接
		List<RoleInfo> roles = (List<RoleInfo>) roleRepository.findAll();
		roles.forEach(role -> filterChainDefinitionMap.put(role.getUrl(), "roles[" + role.getName() + "]"));
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(myShiroRealm());
		securityManager.setSessionManager(sessionManager());
		// 不设置可能会报错
		securityManager.setRememberMeManager(null);
		return securityManager;
	}

	/**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 */
	@Bean
	public MyShiroRealm myShiroRealm() {
		return new MyShiroRealm();
	}

	/**
	 * shiro session的管理
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		// 全局session过期时间,单位毫秒,manager默认为30分钟
		sessionManager.setGlobalSessionTimeout(Globals.GLOBAL_LOGIN_SESSION_EXPIRE * 1000);
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		return sessionManager;
	}

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

}
