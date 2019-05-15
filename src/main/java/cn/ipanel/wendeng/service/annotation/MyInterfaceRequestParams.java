package cn.ipanel.wendeng.service.annotation;

import java.lang.annotation.*;

/**
 * @author luzh
 * createTime 2017年9月9日 下午5:46:03
 */
@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyInterfaceRequestParams {

	/** 要执行的操作类型比如：add操作 **/
	public String operationType() default "";

	/** 要执行的具体操作比如：添加用户 **/
	public String operationName() default "";
}
