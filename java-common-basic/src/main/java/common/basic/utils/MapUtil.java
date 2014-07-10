package common.basic.utils;

import common.basic.interfaces.ITransform;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public MapUtil() throws InstantiationException {
        throw new InstantiationException();
    }

    public static <TKey, TValue> boolean equals(Map<TKey, TValue> map1, Map<TKey, TValue> map2) {
        if(map1.size() != map1.size())
            return false;

        for (TKey key : map1.keySet()) {
            if(!map2.containsKey(key))
                return false;

            final TValue value1 = map1.get(key);
            final TValue value2 = map1.get(key);
            if (!value1.equals(value2))
                return false;
        }

        return true;
    }

    public static <TKey, TValue> boolean isNullOrEmpty(Map<TKey, TValue> map) {
        if(null == map)
            return true;

        if(map.isEmpty())
            return true;

        return false;
    }

    public static void convertYNStringToBoolean(Map<String, Object> map, String... arrayKey) {
        for (String key : arrayKey){
             map.put(key, BooleanUtil.convertYN((String) map.get(key)));
        }
    }

    public static void convertBigDecimalToInt(Map<String, Object> map) {
        for (String key : map.keySet()) {
            final BigDecimal bigDecimal = Cast.as(map.get(key), BigDecimal.class);
            if(bigDecimal == null)
                continue;

            map.put(key, bigDecimal.intValue());
        }
    }

    public static <TKey, TValue> Map<TKey, TValue> transformKey(Map<TKey, TValue> map, ITransform<TKey> transform) {
        HashMap<TKey, TValue> mapResult = new HashMap<TKey, TValue>();

        for (TKey key : map.keySet()) {
            mapResult.put(transform.transform(key), map.get(key));
        }

        return mapResult;
    }

}
