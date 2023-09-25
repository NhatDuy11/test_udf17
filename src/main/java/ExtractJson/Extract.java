package ExtractJson;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class Extract {
    public static void main(String[] args) {
        String jsonString = "{\"name\": \"John\", \"age\": 30, \"city\": \"New York\"}";

        try {

            JSONObject jsonObject = new JSONObject(jsonString);


            String name = jsonObject.getString("name");
            int age = jsonObject.getInt("age");
            String city = jsonObject.getString("city");


            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("City: " + city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
