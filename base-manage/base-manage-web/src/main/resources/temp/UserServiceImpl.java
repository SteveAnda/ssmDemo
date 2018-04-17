package com.taotao.sso.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pageshop.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClients;
import com.taotao.uitl.JsonUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClients jedisClients;
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String content, Integer type) {
        //创建查询条件
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        //对数据进行校验：1、2、3分别代表username、phone、email
        //用户名校验
        if (1 == type) {
            criteria.andUsernameEqualTo(content);
            //电话校验
        } else if ( 2 == type) {
            criteria.andPhoneEqualTo(content);
            //email校验
        } else {
            criteria.andEmailEqualTo(content);
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {
        //补全信息
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //md5密码加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password) {
        //创建一个查询条件
        TbUserExample example = new TbUserExample();
        Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if(list==null||list.size()==0)
        {
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        TbUser tbUser = list.get(0);
        if(!DigestUtils.md5Digest(password.getBytes()).equals(tbUser.getPassword()))
        {
            return TaotaoResult.build(400,"用户名或密码错误");
        }
        //创建一个token
        String token = UUID.randomUUID().toString();
        //为 安全可以把密码变为null
        tbUser.setPassword(null);
        //存放在redis里
        jedisClients.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(tbUser));
        //设置session过期时间
        jedisClients.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        //返回token
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        //获取数据
        String string = jedisClients.get(token);
        if(StringUtils.isBlank(string))
        {
            return TaotaoResult.build(401, "此session已经过期，请重新登录");
        }
        //string有数据 先设置重新设置它的过期时间
        jedisClients.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        //返回用户信息
        return TaotaoResult.ok(JsonUtils.jsonToPojo(string, TbUser.class));
    }

}