package cn.ipanel.wendeng.service.shiro;


import cn.ipanel.wendeng.service.dao.ManagerRepository;
import cn.ipanel.wendeng.service.entity.ManagerInfo;
import cn.ipanel.wendeng.service.entity.RoleInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author luzh
 * createTime 2017年8月29日 上午10:01:00
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    
	@Resource
	private ManagerRepository managerRepository;

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param authToken token
	 * @return info
	 * @throws AuthenticationException e
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authToken;
		return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
	}

	/**
	 * 
	 * 授权
	 * 
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username =  SecurityUtils.getSubject().getPrincipal().toString();
		ManagerInfo managerInfo = managerRepository.findByUsername(username);
		List<RoleInfo> roleInfoList = managerInfo.getRoles();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roleSet = new HashSet<>();
		roleInfoList.forEach(role -> roleSet.add(role.getName()));
		info.setRoles(roleSet);
		return info;
	}

}
