import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.jayway.jsonpath.JsonPath;
public class AvroParser {
    public static void main(String[] args) {
        String avroData = "{\"KSQL_COL_0\":{\"string\":\"{\\\"row\\\": {\\n    \\\"c85\\\": 12010,\\n    \\\"c96\\\": 1,\\n    \\\"c11\\\": 1,\\n    \\\"c99\\\": \\\"LEGACY\\\",\\n    \\\"c76\\\": \\\"NO\\\",\\n    \\\"c78\\\": 19980723,\\n    \\\"c2\\\": 12010,\\n    \\\"c3\\\": \\\"Commission Receivable\\\",\\n    \\\"c5\\\": \\\"Comm Recvble\\\",\\n    \\\"c6\\\": \\\"COMMI12010\\\",\\n    \\\"c7\\\": \\\"TR\\\",\\n    \\\"c8\\\": \\\"USD\\\",\\n    \\\"c9\\\": 1,\\n    \\\"c217\\\": \\\"78671_INPUTTER_OFS_MB.OFS.AUTH\\\",\\n    \\\"c216\\\": 1704062201,\\n    \\\"c219\\\": 1,\\n    \\\"id\\\": \\\"USD120100001\\\",\\n    \\\"c108\\\": \\\"NO\\\",\\n    \\\"c218\\\": \\\"GB0010001\\\",\\n    \\\"c93\\\": \\\"USD\\\",\\n    \\\"c95\\\": \\\"USD\\\",\\n    \\\"c215\\\": [\\n        \\\"1_DIM\\\",\\n        {\\n            \\\"m\\\": 2,\\n            \\\"content\\\": \\\"213_CONV.ACCOUNT.201405\\\"\\n        }\\n    ],\\n    \\\"c94\\\": 1,\\n    \\\"c214\\\": 1\\n}}\"}}}]";

        try {
            JSONObject avroJson = new JSONObject(avroData);
            String jsonString = avroJson.getJSONObject("KSQL_COL_0").getString("string");
            JSONObject jsonData = new JSONObject(jsonString).optJSONObject("row");
            if (jsonData != null) {
                printSchema(jsonData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void printSchema(JSONObject jsonData) {
        int c85 = jsonData.optInt("c85");
        int c96 = jsonData.optInt("c96");
        int c11 = jsonData.optInt("c11");
        String c99 = jsonData.optString("c99");
        String c76 = jsonData.optString("c76");
        int c78 = jsonData.optInt("c78");
        int c2 = jsonData.optInt("c2");
        String c3 = jsonData.optString("c3");
        String c5 = jsonData.optString("c5");
        String c6 = jsonData.optString("c6");
        String c7 = jsonData.optString("c7");
        String c8 = jsonData.optString("c8");
        int c9 = jsonData.optInt("c9");
        String c217 = jsonData.optString("c217");
        int c216 = jsonData.optInt("c216");
        int c219 = jsonData.optInt("c219");
        String id = jsonData.optString("id");
        String c108 = jsonData.optString("c108");
        String c218 = jsonData.optString("c218");
        String c93 = jsonData.optString("c93");
        String c95 = jsonData.optString("c95");
        JSONArray c215 = jsonData.optJSONArray("c215");
        int c94 = jsonData.optInt("c94");
        int c214 = jsonData.optInt("c214");

        System.out.println("c85: " + c85);
        System.out.println("c96: " + c96);
        System.out.println("c11: " + c11);
        System.out.println("c99: " + c99);
        System.out.println("c76: " + c76);
        System.out.println("c78: " + c78);
        System.out.println("c2: " + c2);
        System.out.println("c3: " + c3);
        System.out.println("c5: " + c5);
        System.out.println("c6: " + c6);
        System.out.println("c7: " + c7);
        System.out.println("c8: " + c8);
        System.out.println("c9: " + c9);
        System.out.println("c217: " + c217);
        System.out.println("c216: " + c216);
        System.out.println("c219: " + c219);
        System.out.println("id: " + id);
        System.out.println("c108: " + c108);
        System.out.println("c218: " + c218);
        System.out.println("c93: " + c93);
        System.out.println("c95: " + c95);
        System.out.println("c215: " + c215);
        System.out.println("c94: " + c94);
        System.out.println("c214: " + c214);
    }
}
