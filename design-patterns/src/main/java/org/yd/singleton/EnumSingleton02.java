package org.yd.singleton;

/**
 * @Description 单例枚举类（模拟数据库链接）
 * @Author XUZS
 * @Date 21-4-7 17:43
 * @Version 1.0
 **/
public enum EnumSingleton02 {

    INSTANCE;

    private DBConnection dbConnection = null;

    private EnumSingleton02() {
        dbConnection = new DBConnection();
    }

    public DBConnection getConnection() {
        return dbConnection;
    }
}
