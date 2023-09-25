package removecharacter;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
@UdfDescription(name = "removeNullChars", description = "Removes null characters from a string")
public class removeNullChars {
    @Udf(description = "Removes null characters and retains the XML part")
    public String removeNullChars(String input) {
        if (input == null) {
            return null;
        }

        return input.replaceAll("\\p{C}", "");
    }
}
