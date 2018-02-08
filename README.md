# spring security 安全框架 #

**基本原理**
- 请求 --> 
        SecurityContextPersistenceFilter(一定生效) --> 
- ----------------------------------------------------------------------------------------------------------
         BasicAuthenticationFilter(可配置生效) -->
         UsernamePasswordAuthenticationFilter(可配置生效) -->
         RememberMeAuthenticationFilter(可配置生效) -->
         SmsCodeAuthenticationFilter(可配置生效) -->
         SocialAuthenticationFilter(可配置生效) -->
         OAuth2AuthenticationProcessingFilter(可配置生效) -->
         OAuth2ClientAuthenticationProcessingFilter(可配置生效) -->
- ----------------------------------------------------------------------------------------------------------
         AnonymousAuthenticationFilter() -->
- ----------------------------------------------------------------------------------------------------------         
         ExceptionTranslationFilter(一定生效:捕获FilterSecurityInterceptor抛出的异常) -->
- ----------------------------------------------------------------------------------------------------------         
         FilterSecurityInterceptor(一定生效:匹配WebSecurityConfigurerAdapter验证) -->
- ----------------------------------------------------------------------------------------------------------         
         REST API

**控制授权**
- 请求 --> 
        FilterSecurityInterceptor(过滤器) -->  
        
        1.FilterSecurityInterceptor封装的用户请求信息 即*URL*
        
        2 .SecurityConfig(系统配置信息)
                 根据用户配置的 httpSecurity.authorizeRequests()
                                              .antMatchers(
                                                      securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".json",
                                                      securityProperties.getBrowser().getSession().getSessionInvalidUrl()+".html"
                                                      )
                                              .permitAll()
                                              .anyRequest()
                                              .authenticated()
                 封装成ConfigAttribute
        ConfigAttribute即*url对应的权限*
        3. SecurityContextHolder 
                获取封装在上下文中Authentication的用户的权限信息
         Authentication即*用户的权限信息*
         
        上述1,2,3 ---------->
            
        AccessDecisionManager(接口:授权决定管理者) -->
        
        AbstractAccessDecisionManager(抽象实现)                     <--------------   AccessDecisionVoter
        AffirmativeBased(具体实现:有一个通过则通过(spring默认))                        WebExpressionVoter 
        ConsensusBased(具体实现:比较通过和不通过数量)
        UnanimousBased(具体实现:有一个不通过则不通过)

- AnonymousAuthenticationFilter
匿名过滤器：处于配置的过滤器最后。
 doFilter方法，判断上下文中如果没有Authentication，则创建AnonymousAuthenticationToken封装用户信息principal是字符串anonymousUser而不是UserDetails.
 并将AnonymousAuthenticationToken放到SecurityContext中。
 如果前面配置的过滤器(如UsernamePasswordAuthenticationFilter)，过滤请求的URL，则SecurityContext中会存一个对应的Authentication。
 如果前面配置的过滤器，没有过滤到请求的URL，那么在AnonymousAuthenticationFilter中就会存AnonymousAuthenticationToken。

**请求拒绝流程**

进入FilterSecurityInterceptor的方法，调用invoke方法，参数是(1.)请求的URL
beforeInvocation方法表示在调用rest服务之前的授权判断的逻辑。

beforeInvocation方法中调用this.obtainSecurityMetadataSource().getAttributes(object)
即调用DefaultFilterInvocationSecurityMetadataSource类中的getAttributes方法
getAttributes方法中的通过requestMap得到了用户配置的antMatchers信息用来匹配，获得(2.)该请求需要具有的权限

beforeInvocation方法中调用authenticateIfRequired()获取认证的(3.)Authentication信息

根据1，2，3
beforeInvocation方法中调用this.accessDecisionManager.decide(authenticated, object, attributes);决定是否验证通过。
spring默认调用AffirmativeBased实现的decide方法:  
getDecisionVoters()获取投票器spring3.X以后，web请求只有一个WebExpressionVoter投票器，访问不通过抛出AccessDeniedException
进入FilterSecurityInterceptor的方法中捕获AccessDeniedException，抛给前一个过滤器ExceptionTranslationFilter

ExceptionTranslationFilter判断是否是AuthenticationException,如果不是则调用处理异常的方法handleSpringSecurityException
handleSpringSecurityException方法中判断是否是AccessDeniedException，如果是再判断Authentication是否匿名。
如果是匿名的认证，sendStartAuthentication()去做身份认证，调转到用户配置的loginPage的路径上
如果不是匿名的认证，则返回403的错误。

**请求ok流程**

由于用户配置的 .antMatchers(HttpMethod.GET,"/user/*").hasRole("ADMIN")中hasRole方法会将ADMIN组装成hasRole(ROLE_ADMIN)
所以UserDetailsService中需要给ROLE_ADMIN的权限
beforeInvocation方法中调用this.accessDecisionManager.decide(authenticated, object, attributes)方法时
匹配Authentication中的权限和用户配置ConfigAttribute需要的权限一致时beforeInvocation方法结束
FilterSecurityInterceptor类中继续调用fi.getChain().doFilter(fi.getRequest(), fi.getResponse());方法调用rest服务。
        

## 权限表达式
permitAll   永远返回true
denyAll 永远返回false
anonymous   当前用户是anonymous返回true
rememberMe  当前用户是rememberMe用户返回true
authenticated 当前用户不是anonymous返回true
fullAuthenticated 当前用户既不是anonymous也不是rememberMe返回true
hasRole(role)  用户拥有制定的角色时返回true ===>UserDetails中需要ROLE_前缀
hasAnyRole([role1,role2]) 用户拥有任一角色时返回true
hasAuthority(authority) 用户拥有指定的权限时返回true ===>UserDetails中不需要前缀
hasAnyAuthority([authority1,authority2]) 用户拥有任一权限时返回true  
hasIoAddress('127.0.0.1'') 请求发送的ip匹配时返回true                                             

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
- 子类实例化后的名称要以ValidateCodeProcessor结尾
        
        
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

openid: 授权用户唯一标识

##  Spring Security OAuth 开发APP认证框架

获取token请求--> TokenEndpoint -->  
1.  ClientDetailsService接口: 读取第三方应用的信息，根据clientId读取client配置信息
    默认实现： InMemoryClientDetailsService
将第三方应用信息封装到ClientDetails中。
createTokenRequest(Map<String, String> requestParameters, ClientDetails authenticatedClient)创建TokenRequest的方法。
TokenRequest tokenRequest = new TokenRequest(requestParameters, clientId, scopes, grantType);
并且在TokenRequest封装了请求信息ClientDetails信息。
2.  TokenGranter接口: 令牌授权者：分装了4种模式的实现。简单模式，授权码模式，用户名密码模式，客户端模式。
    默认实现： CompositeTokenGranter
AbstractTokenGranter抽象实现中，根据TokenRequest中的grant_type挑选对应的一种实现类生成令牌。
首先生成OAuth2Authentication
protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest)
生成过程中产生OAuth2Request和Authentication.
OAuth2Request： ClientDetails和TokenRequest数据的整合
Authentication: 授权用户的信息
OAuth2Request和Authentication 整合成一个对象OAuth2Authentication对象
OAuth2Authentication : 封装了哪个第三方应用和哪个用户授权等信息
3.  AuthorizationServerTokenServices接口: 生成OAuth2AccessToken
    默认实现：DefaultTokenServices
    默认的token生成是UUID
DefaultTokenServices中两接口TokenStore和TokenEnhancer
TokenStore：令牌的存取        
TokenEnhancer: 令牌增强器
    令牌生成逻辑：    
    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(UUID.randomUUID().toString());
        int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
        }
        token.setRefreshToken(refreshToken);
        token.setScope(authentication.getOAuth2Request().getScope());

        return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
    }

##  综上重构自己的登录
 自己的请求通过Filter,如果认证成功，都会进入AuthenticationSuccessHandler。在onAuthenticationSuccess方法中
 通过AuthorizationServerTokenServices接口: 生成OAuth2AccessToken 然后返回token。
createAccessToken方法需要OAuth2Authentication。
OAuth2Authentication创建需要OAuth2Request和Authentication。Authentication在onAuthenticationSuccess方法中已存在
OAuth2Request创建需要ClientDetails和TokenRequest。OAuth2Request storedOAuth2Request = requestFactory.createOAuth2Request(client, tokenRequest);
TokenRequest创建需要请求参数和ClientDetails。TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);
ClientDetails创建需要请求参数clientId.ClientDetails authenticatedClient = getClientDetailsService().loadClientByClientId(clientId);    
## License

MIT
