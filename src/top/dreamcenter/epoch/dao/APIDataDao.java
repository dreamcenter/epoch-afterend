package top.dreamcenter.epoch.dao;

import top.dreamcenter.epoch.entity.APIData;

import java.util.List;

public interface APIDataDao {
    /**
     * show all permission apis
     * @return
     */
    public List<APIData> showList();

    /**
     * show one's api
     * @param nickname
     * @return
     */
    public List<APIData> showOwn(String nickname);

    /**
     * change an api
     * @param data
     * @return
     */
    public int changeOne(APIData data);

    /**
     * delete an api
     * @param nickname
     * @param id
     * @return
     */
    public int deleteOne(String nickname, int id);

    /**
     * add a new api
     * @param apiData
     * @return
     */
    public int addOne(APIData apiData);

    /**
     * get one api info through its id
     * @param id
     * @return
     */
    public APIData getOne(int id);
}
