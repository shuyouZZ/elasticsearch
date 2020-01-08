package com.learn.mbg;

import com.learn.common.constant.DatabaseConstant;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author dshuyou
 * @date 2019/11/26 15:03
 */
public class CreateSql {

    public String selectWithParamSql(Map<String, Object> params) {
        String table = String.valueOf(params.get(DatabaseConstant.TABLE));
        if(Pattern.matches(".*;.*--.*",table)){
            return null;
        }
        return new SQL() {
            {
                SELECT("*");
                FROM(table);
                if (params.get(DatabaseConstant.PRIMARY_KEY)!=null) {
                    String pk = String.valueOf(params.get(DatabaseConstant.PRIMARY_KEY));
                    WHERE(pk.concat("= #{id}"));
                }
            }

        }.toString();
    }
}
