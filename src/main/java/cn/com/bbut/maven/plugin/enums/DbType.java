package cn.com.bbut.maven.plugin.enums;

import lombok.Getter;
import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="https://www.fengwenyi.com">Erwin Feng</a>
 * @since 2021-08-23
 */
@Getter
public enum DbType {

    MYSQL("mysql"), ORACLE("oracle");

    private final String code;

    DbType(String code) {
        this.code = code;
    }

    public static DbType getDbType(String dbName) {
        if (StringUtils.isBlank(dbName)) {
            return null;
        }
        for (DbType dbType : DbType.values()) {
            if (dbType.code.equals(dbName.toLowerCase())) {
                return dbType;
            }
        }
        return null;
    }
}
