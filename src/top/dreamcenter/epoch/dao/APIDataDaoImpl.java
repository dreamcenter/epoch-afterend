package top.dreamcenter.epoch.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.dreamcenter.epoch.entity.APIData;

import java.util.List;

@Repository
public class APIDataDaoImpl implements APIDataDao{

    @Autowired
    private JdbcTemplate template;
    @Override
    public List<APIData> showList() {
        String sql = "SELECT * FROM api WHERE permission=1";
        List<APIData> list =
                template.query(sql, new BeanPropertyRowMapper<>(APIData.class));
        return list;
    }

    @Override
    public List<APIData> showOwn(String nickname) {
        String sql = "SELECT * FROM api WHERE master=?";
        List<APIData> list =
                template.query(sql,new BeanPropertyRowMapper<>(APIData.class),nickname);
        return list;
    }

    @Override
    public int changeOne(APIData data) {
        String sql = "UPDATE api SET master=?,permission=?,name=?,method=?,url=?,description=?,type=?,param=?" +
                " WHERE id=?";
        int count = template.update(sql,data.getMaster(),data.getPermission(),data.getName(),data.getMethod(),
                data.getUrl(),data.getDescription(),data.getType(),data.getParam(),data.getId());
        return count;
    }

    @Override
    public int deleteOne(String nickname, int id) {
        String sql = "DELETE FROM api WHERE master=? and id=?";
        int count = template.update(sql,nickname,id);
        return count;
    }

    @Override
    public int addOne(APIData data) {
        String sql = "INSERT INTO api VALUE(id,?,?,?,?,?,?,?,?)";
        int count = template.update(sql,data.getMaster(),data.getPermission(),data.getName(),data.getMethod(),
                data.getUrl(),data.getDescription(),data.getType(),data.getParam());
        return count;
    }

    @Override
    public APIData getOne(int id) {
        String sql = "SELECT * FROM api WHERE id=?";
        APIData apiData = template.queryForObject(sql,new BeanPropertyRowMapper<>(APIData.class),id);
        return apiData;
    }
}
