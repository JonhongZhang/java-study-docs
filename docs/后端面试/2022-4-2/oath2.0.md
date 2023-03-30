1. oath2.0 是什么？
    Oath2.0, 简单的理解就是一种授权机制，它是在客户端和资源之间的授权层，用来分离两种不同的角色。 在资源所有者同意向客户端颁发令牌后，客户端携带令牌可以访问资源所有者的所有资源； 
    Oath2.0 是Oath协议的一个版本，有2就有1， Oath2.0 不兼容1.0 ，相当于直接废弃1.0。
        for exmple:  在家肝文章饿了定了一个外卖，外卖小哥30秒火速到达了我家楼下，奈何有门禁进不来，可以输入密码进入，但出于安全的考虑我并不想告诉他密码。
        此时外卖小哥看到门禁有一个高级按钮“一键获取授权”，只要我这边同意，他会获取到一个有效期 2小时的令牌（token）正常出入。
        令牌（token）和 密码 的作用虽然相似都可以进入系统，但还有点不同。token 拥有权限范围，有时效性的，到期自动失效，而且无效修改。

2. Oath2.0 的授权方式？
    OAuth2.0 的授权简单理解其实就是获取令牌（token）的过程，OAuth 协议定义了四种获得令牌的授权方式（authorization grant ）如下：
        授权码（authorization-code）
        隐藏式（implicit）
        密码式（password）：
        客户端凭证（client credentials）
    但值得注意的是，不管我们使用哪一种授权方式，在三方应用申请令牌之前，都必须在系统中去申请身份唯一标识：客户端 ID（client ID）和 客户端密钥（client secret）。这样做可以保证 token 不被恶意使用。

    下面我们会分析每种授权方式的原理，在进入正题前，先了解 OAuth2.0 授权过程中几个重要的参数：
            response_type：code 表示要求返回授权码，token 表示直接返回令牌
            client_id：客户端身份标识
            client_secret：客户端密钥
            redirect_uri：重定向地址
            scope：表示授权的范围，read只读权限，all读写权限
            grant_type：表示授权的方式，AUTHORIZATION_CODE（授权码）、password（密码）、client_credentials（凭证式）、refresh_token 更新令牌
            state：应用程序传递的一个随机数，用来防止CSRF攻击。
        1. 授权码
            oauth2.0 四中授权中授权码方式是最复杂的，但同时也是安全系数最高的，比较常见的一种方式，，这种方式使用于兼具前后端的Web项目，因为有些项目只有后端或者，只有前端，并不适用于授权码模式。
                以微信登陆掘金为例，讲述授权码的流程：
                        用户 ： 
                                掘金                                            微信
                            1. 微信登陆倔金，
                            2. 掘金请求微信授予微信的权限
                                                                        3. 用户确认微信是否授权给掘金
                            4. 用户同意后，申请授权code                     5. 微信返回授权码code
                            6. 用授权码code，请求access_token               7. 校验通过，返回access_token
                            8. 请求头携带access_token 完成后续请求。
            用户选择WX登录掘金，掘金会向WX发起授权请求，接下来 WX询问用户是否同意授权（常见的弹窗授权）。response_type 为 code 要求返回授权码，scope 参数表示本次授权范围为只读权限，redirect_uri 重定向地址。
                ```url
                https://wx.com/oauth/authorize?
                                    response_type=code&
                                    client_id=CLIENT_ID&
                                    redirect_uri=http://juejin.im/callback&
                                    scope=read

                ```
            用户同意授权后，WX 根据 redirect_uri重定向并带上授权码。
                http://juejin.im/callback?code=AUTHORIZATION_CODE
            当掘金拿到授权码（code）时，带授权码和密匙等参数向WX申请令牌。grant_type表示本次授权为授权码方式 authorization_code ，获取令牌要带上客户端密匙 client_secret，和上一步得到的授权码 code。
            ```url
            https://wx.com/oauth/token?
                client_id=CLIENT_ID&
                client_secret=CLIENT_SECRET&
                grant_type=authorization_code&
                code=AUTHORIZATION_CODE&
                redirect_uri=http://juejin.im/callback
            ```
            最后 WX 收到请求后向 redirect_uri 地址发送 JSON 数据，其中的access_token 就是令牌。
            ```json
             {    
                "access_token":"ACCESS_TOKEN",
                "token_type":"bearer",
                "expires_in":2592000,
                "refresh_token":"REFRESH_TOKEN",
                "scope":"read",
                ......
            }

            ```
        2、隐藏式
            上边提到有一些Web应用是没有后端的， 属于纯前端应用，无法用上边的授权码模式。令牌的申请与存储都需要在前端完成，跳过了授权码这一步。
            前端应用直接获取 token，response_type 设置为 token，要求直接返回令牌，跳过授权码，WX授权通过后重定向到指定 redirect_uri 。
            ```url
            https://wx.com/oauth/authorize?
                response_type=token&
                client_id=CLIENT_ID&
                redirect_uri=http://juejin.im/callback&
                scope=read
            ```
        3、密码式
            密码模式比较好理解，用户在掘金直接输入自己的WX用户名和密码，掘金拿着信息直接去WX申请令牌，请求响应的 JSON结果中返回 token。grant_type 为 password 表示密码式授权。
            ```url
              https://wx.com/token?
                grant_type=password&
                username=USERNAME&
                password=PASSWORD&
                client_id=CLIENT_ID 
            ```
            这种授权方式缺点是显而易见的，非常的危险，如果采取此方式授权，该应用一定是可以高度信任的。

        4、凭证式
            凭证式和密码式很相似，主要适用于那些没有前端的命令行应用，可以用最简单的方式获取令牌，在请求响应的 JSON 结果中返回 token。
            grant_type 为 client_credentials 表示凭证式授权，client_id 和 client_secret 用来识别身份。
            ```url
            https://wx.com/token?
                grant_type=client_credentials&
                client_id=CLIENT_ID&
                client_secret=CLIENT_SECRET
            ```
3. 令牌的使用与更新            
        1、令牌怎么用？
            拿到令牌可以调用 WX API 请求数据了，那令牌该怎么用呢？

            每个到达WX的请求都必须带上 token，将 token 放在 http 请求头部的一个Authorization字段里。
            如果使用postman 模拟请求，要在Authorization -> Bearer Token 放入 token，注意：低版本postman 没有这个选项。

        2、令牌过期怎么办？
            token是有时效性的，一旦过期就需要重新获取，但是重走一遍授权流程，不仅麻烦而且用户体验也不好，那如何让更新令牌变得优雅一点呢？

            一般在颁发令牌时会一次发两个令牌，一个令牌用来请求API，另一个负责更新令牌 refresh_token。grant_type 为 refresh_token 请求为更新令牌，参数 refresh_token 是用于更新令牌的令牌。
            ```url
                https://wx.com/oauth/token?
                        grant_type=refresh_token&
                        client_id=CLIENT_ID&
                        client_secret=CLIENT_SECRET&
                        refresh_token=REFRESH_TOKEN
            ```
    OAuth2.0 授权其实并不是很难，只不过授权流程稍显麻烦，逻辑有些绕，OAuth2.0它是面试经常会被问到的知识点，还是应该多了解一下
            


