package com.usong.login.dao;

import com.usong.login.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserInfoDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public class UserInfoRowMapper implements RowMapper<UserInfo> {
        @Override
        public UserInfo mapRow(ResultSet resultSet, int i) throws SQLException {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(resultSet.getInt("id"));
            userInfo.setPassword(resultSet.getString("password"));
            userInfo.setName(resultSet.getString("name"));
            userInfo.setAccess(resultSet.getString("access"));
            return userInfo;
        }
    }

    public UserInfo getUserByName(String userName) {
        String sql = "SELECT id,password,name,access from user where name = :userName";
        Map<String,Object> map = new HashMap<>();
        map.put("userName",userName);

        List<UserInfo> list = namedParameterJdbcTemplate.query(sql,map,new UserInfoRowMapper());
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }
}
