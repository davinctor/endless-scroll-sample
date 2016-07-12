package tk.davictor.endless_scroll_sample.util.lorem_ipsum;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 14.06.16
 * Created by Victor Ponomarenko
 */
class DataProviderImpl implements DataProvider {
    private static final String VOCABULARY = "vocabulary.json";

    private final JSONObject json;
    private final BaseProducer baseProducer;

    public DataProviderImpl(Context context, BaseProducer baseProducer) {
        this.baseProducer = baseProducer;
        String json = FileUtil.readFromAssets(context, VOCABULARY);
        try {
            this.json = new JSONObject(json);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String getString(String key) {
        return readValue(json, key);
    }

    @Override
    public Integer getInt(String key) {
        return readValue(json, key);
    }

    @Override
    public List<String> getStringList(String key) {
        return readListValues(json, key);
    }

    @Override
    public List<Integer> getIntegerList(String key) {
        return readListValues(json, key);
    }

    @Override
    public String getRandString(String key) {
        return baseProducer.randomElement(getStringList(key));
    }

    @Override
    public String getRandString(String key, String subKey) {
        try {
            JSONObject json = this.json.getJSONObject(key);
            JSONArray jsonArray = json.getJSONArray(subKey);
            int index = baseProducer.randomBetween(0, jsonArray.length() - 1);
            return jsonArray.getString(index);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer getRandInteger(String key) {
        return baseProducer.randomElement(getIntegerList(key));
    }

    @SuppressWarnings("unchecked")
    private <T> T readValue(JSONObject json, String key) {
        try {
            return (T) json.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> readListValues(JSONObject json, String key) {
        try {
            return (List<T>) jsonArrayToList(json.getJSONArray(key));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private <T> List<T> jsonArrayToList(JSONArray jsonArray) {
        int length = jsonArray.length();
        List<T> list = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            try {
                //noinspection unchecked
                list.add((T) jsonArray.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}
