package ExtractJson;
import org.json.*;


public class JsonExtractor {
    public static void main(String[] args) {
                String avroData = "{\"KSQL_COL_0\":{\"string\":\"{\\\"row\\\": {\\n    \\\"c85\\\": 12010,\\n    \\\"c96\\\": 1,\\n    \\\"c11\\\": 1,\\n    \\\"c99\\\": \\\"LEGACY\\\",\\n    \\\"c76\\\": \\\"NO\\\",\\n    \\\"c78\\\": 19980723,\\n    \\\"c2\\\": 12010,\\n    \\\"c3\\\": \\\"Commission Receivable\\\",\\n    \\\"c5\\\": \\\"Comm Recvble\\\",\\n    \\\"c6\\\": \\\"COMMI12010\\\",\\n    \\\"c7\\\": \\\"TR\\\",\\n    \\\"c8\\\": \\\"USD\\\",\\n    \\\"c9\\\": 1,\\n    \\\"c217\\\": \\\"78671_INPUTTER_OFS_MB.OFS.AUTH\\\",\\n    \\\"c216\\\": 1704062201,\\n    \\\"c219\\\": 1,\\n    \\\"id\\\": \\\"USD120100001\\\",\\n    \\\"c108\\\": \\\"NO\\\",\\n    \\\"c218\\\": \\\"GB0010001\\\",\\n    \\\"c93\\\": \\\"USD\\\",\\n    \\\"c95\\\": \\\"USD\\\",\\n    \\\"c215\\\": [\\n        \\\"1_DIM\\\",\\n        {\\n            \\\"m\\\": 2,\\n            \\\"content\\\": \\\"213_CONV.ACCOUNT.201405\\\"\\n        }\\n    ],\\n    \\\"c94\\\": 1,\\n    \\\"c214\\\": 1\\n}}\"}}}]";
            // Parsing JSON
            JSONObject jsonObject = new JSONObject(avroData);
        JSONObject innerObject = jsonObject.getJSONObject("KSQL_COL_0");
        String jsonString = innerObject.getString("string");
        JSONObject dataObject = new JSONObject(jsonString);
        JSONObject rowObject = dataObject.getJSONObject("row");

        // Extracting individual fields
        int c85 = rowObject.getInt("c85");
        int c96 = rowObject.getInt("c96");
        int c11 = rowObject.getInt("c11");
        String c99 = rowObject.getString("c99");
        String c76 = rowObject.getString("c76");
        int c78 = rowObject.getInt("c78");
        int c2 = rowObject.getInt("c2");
        String c3 = rowObject.getString("c3");
        String c5 = rowObject.getString("c5");
        String c6 = rowObject.getString("c6");
        String c7 = rowObject.getString("c7");
        String c8 = rowObject.getString("c8");
        int c9 = rowObject.getInt("c9");
        String c217 = rowObject.getString("c217");
        int c216 = rowObject.getInt("c216");
        int c219 = rowObject.getInt("c219");
        String id = rowObject.getString("id");
        String c108 = rowObject.getString("c108");
        String c218 = rowObject.getString("c218");
        String c93 = rowObject.getString("c93");
        String c95 = rowObject.getString("c95");
        JSONArray c215 = rowObject.getJSONArray("c215");
        int c94 = rowObject.getInt("c94");
        int c214 = rowObject.getInt("c214");

        // Print the extracted fields
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
