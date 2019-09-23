package wang.ismy.common.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author MY
 * @date 2019/9/17 18:55
 */
public class JsonUtils {

    private static final Gson GSON = new Gson();

    public static String toJSON(Object obj){

        return GSON.toJson(obj);
    }


}
