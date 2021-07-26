package top.dreamcenter.epoch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.dreamcenter.epoch.entity.Account;
import top.dreamcenter.todo.TodoB;

@Repository
public class AccountDaoImpl implements AccountDao{

    @Autowired
    private JdbcTemplate template;
    @Override
    public int addOne(Account account) {
        String sql = "INSERT INTO account VALUE (id,?,?)";
        return template.update(sql,account.getNickname().trim(),account.getPassword().trim());
    }

    @Override
    public int updateOne(Account account) {
        String sql = "UPDATE account_info set mail=?,avatar=?,visible=? WHERE cid=?";
        int result = template.update(sql,account.getMail(),
                account.getAvatar(),account.getVisible(),account.getId());
        return result;
    }

    @Override
    public boolean nickNameHas(String nickname) {
        String sql = "SELECT COUNT(*) FROM account WHERE nickname=?";
        int count = template.queryForObject(sql,Integer.class,nickname);
        return count > 0;
    }

    @Override
    public String passwordCheck(String nickname) {
        String sql = "SELECT password FROM account WHERE nickname=?";
        String password = template.queryForObject(sql,String.class,nickname);
        return password;
    }

    @Override
    public Account getAccount(String nickname) {
        String sql = "SELECT account.id,nickname,mail,avatar,visible FROM account,account_info " +
                     "WHERE account.id = account_info.cid and nickname=?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Account.class),nickname);
    }

    @Override
    public int getAccountQuantity() {
        String sql = "SELECT COUNT(*) FROM account";
        return template.queryForObject(sql, Integer.class);
    }

    @Override
    public int initAccountInfo(int id) {
        String sql = "INSERT INTO account_info VALUE (id,?,DEFAULT,DEFAULT,DEFAULT)";
        int result = template.update(sql, id);
        return result;
    }
}
