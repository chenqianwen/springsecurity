# spring security 安全框架 #

[![vue](https://img.shields.io/badge/vue-2.4.2-brightgreen.svg)](https://github.com/vuejs/vue)
[![element-ui](https://img.shields.io/badge/element--ui-1.4.1-brightgreen.svg)](https://github.com/ElemeFE/element)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://github.com/PanJiaChen/vue-element-admin/blob/master/LICENSE)
[![GitHub release](https://img.shields.io/github/release/PanJiaChen/vue-element-admin.svg)]()


[线上地址](http://panjiachen.github.io/vue-element-admin)

[English Document](https://github.com/PanJiaChen/vue-element-admin/blob/master/README-en.md)

[wiki](https://github.com/PanJiaChen/vue-element-admin/wiki)

**本项目的定位是后台集成方案，不适合当基础模板来开发，模板建议使用 [vueAdmin-template](https://github.com/PanJiaChen/vueAdmin-template) ， 桌面端 [electron-vue-admin](https://github.com/PanJiaChen/electron-vue-admin)**



**注意：该项目目前使用element-ui@1.4.1版本，所以最低兼容 Vue 2.3.0**

## 前言

 **一.开发基于表单的认证**
 - 图形验证码基本参数可配置
 
        1.security-core中默认有图形验证码的参数
        2.demo中可配置图形验证码的参数覆盖1中的参数
        3.url中可传图形验证码的参数覆盖2中的参数
 
 - 图形验证码拦截的接口可配置
 
        1.一定会拦截登录的接口
        2.demo中可配置多个需要拦截的uri
    
 - 验证码的生成逻辑可配置
        1.security-core中默认有图形验证码的生成器
        2.demo中可实现ValidateCodeGenerator接口，重写图形验证码生成
        
 - 短信验证码的长度可配置
        1.security-core中默认有短信验证码的长度
        2.demo中可配置短信验证码的长度参数覆盖1中的参数
        
 - 短信验证码的发送逻辑可配置
        1.security-core中默认有短信验证发送逻辑DefaultSmsCodeSender
        2.demo中可实现SmsCodeSender接口，重写发送短信的逻辑
        
        
        ValidateCodeProcessor 处理整个验证码生成流程：生成，储存，发送
-- 子类实例化后的名称要以ValidateCodeProcessor结尾
        
        
        社交登陆的验证提供者是SocialAuthenticationProvider类
        
        QQ互联的接口流程：
        1.申请应用，获得以下数据
            appid：应用的唯一标识。在OAuth2.0认证过程中，appid的值即为oauth_consumer_key的值。
            appkey：appid对应的密钥，访问用户资源时用来验证应用的合法性。在OAuth2.0认证过程中，appkey的值即为oauth_consumer_secret的值。
        2.登陆其他client时，导向qq/wx的认证服务器
            
        3.获取授权码
            a. 获取对应的授权码接口地址：https://graph.qq.com/oauth2.0/authorize
                response_type	必须	授权类型，此值固定为“code”。
                client_id	必须	申请QQ登录成功后，分配给应用的appid。
                redirect_uri	必须	成功授权后的回调地址，必须是注册appid时填写的主域名下的地址，建议设置为网站首页或网站的用户中心。注意需要将url进行URLEncode。
                state	必须	client端的状态值。用于第三方应用防止CSRF攻击，成功授权后回调时会原样带回。请务必严格按照流程检查用户与state参数状态的绑定。
                scope	可选	请求用户授权时向用户显示的可进行授权的列表。可填写的值是API文档中列出的接口，以及一些动作型的授权（目前仅有：do_like），如果要填写多个接口名称，请用逗号隔开。
                例如：scope=get_user_info,list_album,upload_pic,do_like不传则默认请求对接口get_user_info进行授权。建议控制授权项的数量，只传入必要的接口名称，因为授权项越多，用户越可能拒绝进行任何授权。
                display	可选	仅PC网站接入时使用。用于展示的样式。不传则默认展示为PC下的样式。如果传入“mobile”，则展示为mobile端下的样式。
                g_ut	可选	仅WAP网站接入时使用。QQ登录页面版本（1：wml版本； 2：xhtml版本），默认值为1。
            b. 通过授权码获取Access Token接口地址：https://graph.qq.com/oauth2.0/token
                grant_type	必须	授权类型，在本步骤中，此值为“authorization_code”。
                client_id	必须	申请QQ登录成功后，分配给网站的appid。
                client_secret	必须	申请QQ登录成功后，分配给网站的appkey。
                code	必须	上一步返回的authorization code。如果用户成功登录并授权，则会跳转到指定的---回调地址---，并在URL中带上Authorization Code。
                例如，回调地址为www.qq.com/my.php，则跳转到：http://www.qq.com/my.php?code=520DD95263C1CFEA087******注意此code会在10分钟内过期。
                redirect_uri	必须	与上面一步中传入的redirect_uri保持一致。
            c. 通过Token获取用户OpenID接口地址https://graph.qq.com/oauth2.0/me
                OpenID是此网站上或应用中唯一对应用户身份的标识，网站或应用可将此ID进行存储，便于用户下次登录时辨识其身份，或将其与用户在网站上或应用中的原有账号进行绑定。
                access_token	必须	在Step1中获取到的access token。
        4. 通过access_token和open_id即可调用其他的接口   
        
##  Spring Social开发第三方登陆时流程
    0.  访问Client
    1.  将用户导向认证服务器
    2.  用户同意授权
    3.  认证服务器带上授权码回调Client
    4.  客户端根据授权码申请token
    5.  认证服务器发放token
    6.  客户端通过token获取用户信息（每个服务商都不相同）
    7.  根据得到的用户信息构建Authentication放入SecurityContext
    注:  SecurityContext放入认证后的Authentication实例之后，就表示登陆成功了！

- Spring Social封装了上述流程
    封装到了SocialAuthenticationFilter过滤器中,并将过滤器加入spring security的过滤器链中。
    1.  ServiceProvider接口: 服务提供商
    默认实现： AbstractOAuth2ServiceProvider
    其中包含了OAuth2Operations + Api
    2.  OAuth2Operations: 封装了1-5步流程    
    默认实现： OAuth2Template
    3.  Api: 封装第六步获取用户信息
    默认实现：AbstractOAuth2ApiBinding
    4.  Connection: 封装前六步得到的用户信息
    默认实现：OAuth2Connection
    5.  ConnectionFactory: 创建4的Connection
    默认实现：OAuth2ConnectionFactory
    为了创建Connection对象,ConnectionFactory包含了ServiceProvider接口实例。
    为了将每个服务商不同的数据结构转成Connection标准结构，ConnectionFactory包含了ApiAdapter,在Api和Connection适配。
    6.  UserConnectionRepository
    JdbcUsersConnectionRepository 操作第三方登陆存在数据表UserConnection中的数据
             
OAuth2AuthenticationService:
     public SocialAuthenticationToken getAuthToken(HttpServletRequest request, HttpServletResponse response) throws SocialAuthenticationRedirectException {
            String code = request.getParameter("code");
            // 没有授权码，则请求获取授权码
            if (!StringUtils.hasText(code)) {
                OAuth2Parameters params = new OAuth2Parameters();
                params.setRedirectUri(this.buildReturnToUrl(request));
                this.setScope(request, params);
                params.add("state", this.generateState(this.connectionFactory, request));
                this.addCustomParameters(params);
                throw new SocialAuthenticationRedirectException(this.getConnectionFactory().getOAuthOperations().buildAuthenticateUrl(params));
            } else if (StringUtils.hasText(code)) {
            // 有授权码，则请求获取access_Token
                try {
                    String returnToUrl = this.buildReturnToUrl(request);
                    AccessGrant accessGrant = this.getConnectionFactory().getOAuthOperations().exchangeForAccess(code, returnToUrl, (MultiValueMap)null);
                    Connection<S> connection = this.getConnectionFactory().createConnection(accessGrant);
                    return new SocialAuthenticationToken(connection, (Map)null);
                } catch (RestClientException var7) {
                    this.logger.debug("failed to exchange for access", var7);
                    return null;
                }
            } else {
                return null;
            }
        }     
        
SocialAuthenticationProvider: 真正认证社交登录逻辑：
    通过usersConnectionRepository把用户信息去查询社交用户是否存在
    List<String> userIds = this.usersConnectionRepository.findUserIdsWithConnection(connection);
    具体实现如下：
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        List<String> localUserIds = this.jdbcTemplate.queryForList("select userId from " + this.tablePrefix + "UserConnection where providerId = ? and providerUserId = ?", String.class, new Object[]{key.getProviderId(), key.getProviderUserId()});
        if (localUserIds.size() == 0 && this.connectionSignUp != null) {
            String newUserId = this.connectionSignUp.execute(connection);
            if (newUserId != null) {
                this.createConnectionRepository(newUserId).addConnection(connection);
                return Arrays.asList(newUserId);
            }
        }
        return localUserIds;
    }
    如果存在该社交用户，则构建出一个被认证的SocialAuthenticationToken。 完成认证。
    如果不存在该社交用户：
    如果UsersConnectionRepository配置了connectionSignUp属性，就会根据用户自己的实现返回一个新的newUserId，通过newUserId新增社交用户信息到数据库
    public void addConnection(Connection<?> connection) {
        try {
            ConnectionData data = connection.createData();
            int rank = (Integer)this.jdbcTemplate.queryForObject("select coalesce(max(rank) + 1, 1) as rank from " + this.tablePrefix + "UserConnection where userId = ? and providerId = ?", new Object[]{this.userId, data.getProviderId()}, Integer.class);
            this.jdbcTemplate.update("insert into " + this.tablePrefix + "UserConnection (userId, providerId, providerUserId, rank, displayName, profileUrl, imageUrl, accessToken, secret, refreshToken, expireTime) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{this.userId, data.getProviderId(), data.getProviderUserId(), rank, data.getDisplayName(), data.getProfileUrl(), data.getImageUrl(), this.encrypt(data.getAccessToken()), this.encrypt(data.getSecret()), this.encrypt(data.getRefreshToken()), data.getExpireTime()});
        } catch (DuplicateKeyException var4) {
            throw new DuplicateConnectionException(connection.getKey());
        }
    }
    如果UsersConnectionRepository没有配置了connectionSignUp属性，就会抛出异常throw new BadCredentialsException("Unknown access token");
    在SocialAuthenticationFilter中doAuthentication就会捕获异常，调转到注册页面
    if (this.signupUrl != null) {
        this.sessionStrategy.setAttribute(new ServletWebRequest(request), ProviderSignInAttempt.SESSION_ATTRIBUTE, new ProviderSignInAttempt(token.getConnection()));
        throw new SocialAuthenticationRedirectException(this.buildSignupUrl(request));
    } else {
        throw var5;
    }
    测试controller中用工具类进行注册，将对应的数据插入表中，下次社交登录就会查询到对应的数据
      providerSignInUtils.doPostSignUp(username,new ServletWebRequest(request));           
        
        
ServletWebRequest: spring的工具类，封装请求和相应。包含了ServletHttpRequest,ServletHttpResponse

AuthenticationManager : 选择provider来处理传进来的token
AuthenticationProvider : 接口 supports方法 判断支Token的类型


UsernamePasswordAuthenticationToken 中
    private final Object principal; // 用户名
    private Object credentials;// 密码
 
 
 
    
 OAuth2：
 请求授权码：
 http://localhost:8090/oauth/authorize?response_type=code&client_id=love&redirect_uri=http://example.com&scope=all
 
映射到TokenEndpoint中    


验证码逻辑:
browser:验证码存在session中
    
appid：应用的唯一标识。在OAuth2.0认证过程中，appid的值即为oauth_consumer_key的值。

appkey：appid对应的密钥，访问用户资源时用来验证应用的合法性。在OAuth2.0认证过程中，appkey的值即为oauth_consumer_secret的值。



## License

MIT
