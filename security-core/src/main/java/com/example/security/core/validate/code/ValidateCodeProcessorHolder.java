package com.example.security.core.validate.code;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author： yl
 * @date： 2018/2/7-13:07
 * @Description：
 * 通过 类型 获取 验证码处理器的 工具类
 * 处理器的bean名称需要以 ValidateCodeProcessor 结尾
 * 类型的字符串 需要和 ValidateCodeProcessor 前的字符串 相等
 */
@Component
public class ValidateCodeProcessorHolder {

	/**
	 * 注入校验码处理器： {处理器bean的name：处理器类}
	 * 例如 [{imageValidateCodeProcessor：ImageCodeProcessor}]
	 */
	@Autowired
	private Map<String, ValidateCodeProcessor> validateCodeProcessors;

	public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType type) {
		return findValidateCodeProcessor(type.toString().toLowerCase());
	}

	/**
	 * 通过类型（image 或者 sms）获取对应的验证码处理器
	 * @param type : image 或者 sms
	 * @return
	 */
	public ValidateCodeProcessor findValidateCodeProcessor(String type) {
		String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
		ValidateCodeProcessor processor = validateCodeProcessors.get(name);
		if (processor == null) {
			throw new ValidateCodeException("验证码处理器" + name + "不存在");
		}
		return processor;
	}

}
