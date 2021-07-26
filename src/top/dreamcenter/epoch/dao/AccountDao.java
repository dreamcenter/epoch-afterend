package top.dreamcenter.epoch.dao;

import top.dreamcenter.epoch.entity.Account;

public interface AccountDao {

    /**
     * add a new account according to account obj
     * @param account
     * @return how many lines effected
     */
    int addOne(Account account);

    /**
     * update the info of the account
     * @param account
     * @return
     */
    int updateOne(Account account);

    /**
     * check if this account exist
     * @param nickname
     * @return true  exist
     *         false not_exist
     */
    boolean nickNameHas(String nickname);

    /**
     * get the password of an account
     * @param nickname
     * @return password
     */
    String passwordCheck(String nickname);

    /**
     * get account info through nickname
     * @param nickname
     * @return
     */
    Account getAccount(String nickname);

    /**
     * get quantity of accounts
     * @return counts
     */
    int getAccountQuantity();

    /**
     * when add a new account, some default data should be init
     * @param id
     * @return
     */
    int initAccountInfo(int id);
}
