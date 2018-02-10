# what you learnt #

1.@RequestParam
public List<User> find(@RequestParam(name = "username",required = false,defaultValue = "tom") String nickname)
接收参数名username赋值给nickname，required是否必须要传参，defaultValue默认值

2.多个@RequestParam参数时，可以声明对象接收参数
public List<User> find(UserQueryCondition condition,@PageableDefault(page = 1,size = 10,sort = "id,asc") Pageable pageable)
springMvc将查询参数组装到UserQueryCondition对象====>请求url后的参数，会组装成实体对象
spring提供Pageable接收分页参数：number，size，sort
@PageableDefault没有穿查询参数时，默认的参数值

3.@GetMapping("/{id:\\d+}")和@PathVariable(name = "id",required = false)
正则表达式指定PathVariable参数的类型为数字
public User findOne(@PathVariable(name = "id",required = false) String userId)
用userId接收参数名id的值

4.@RequestBody User user
public User save(@RequestBody User user)
post请求的请求体通过@RequestBody组装成实体对象

5.前后台日期用long交互

6.@Valid和BindingResult errors
public User save(@Valid @RequestBody User user, BindingResult errors)
@Valid校验user，如果没有参数BindingResult，校验不通过就返回400
如果有参数BindingResult，会继续进入方法，错误信息保存在BindingResult中
Hibernate Validator
@NotNull    值不能为空
@Null   值必须为空
@Pattern(regex=)    字符串匹配正则表达式
@Size(min=,max=)    集合元素在min和max之间
@CreditCardNumber(ignoreNonDigitCharacters) 字符串是信用卡号（美国标准）
@Email  字符串是email地址
@Length(min=,max=)  字符串长度
@NotBlank   字符串必须有字符
@NotEmpty   字符串不为null，集合由元素
@Range(min=,max=)   数字大于等于min，小于等于max
@SafeHtml   字符串是安全的html
@URL    字符串是合法的URL
@AssertFalse    布尔值必须要false
@AssertTrue 布尔值必须要true
@DecimalMax(value=,inclusive=)  值必须小于等于(inclusive=true)/小于(inclusive=false)value属性指定的值。可以朱杰仔字符串属性上。
@DecimalMin(value=，inclusive=)  值必须大于等于(inclusive=true)/大于(inclusive=false)value属性指定的值。可以朱杰仔字符串属性上。
@Digits(integer=,fraction=) 数字格式检查。integer指定整数部分最大长度，fraction指定小树部分的最大长度
@Future 值必须是未来日期
@Past   值必须是过去日期
@Max(value=)    值必须小于等于value值。不能注解在字符串上。
@Min(value=)    值必须大于等于value值。不能注解在字符串上。

7.POST,DELETE,PUT,GET
restful风格通过httpMethod区别增删改查

8.BasicErrorController
默认的错误处理的控制器：

请求头中含有text/html,则返回页面
@RequestMapping(produces = "text/html")
	public ModelAndView errorHtml(HttpServletRequest request,
			HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
				request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
		response.setStatus(status.value());
		ModelAndView modelAndView = resolveErrorView(request, response, status, model);
		return (modelAndView == null ? new ModelAndView("error", model) : modelAndView);
	}

请求头中没有text/html,则返回json
@RequestMapping
@ResponseBody
public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    Map<String, Object> body = getErrorAttributes(request,
            isIncludeStackTrace(request, MediaType.ALL));
    HttpStatus status = getStatus(request);
    return new ResponseEntity<Map<String, Object>>(body, status);
}

## License

MIT
