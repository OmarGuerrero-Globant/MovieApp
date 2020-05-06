import com.google.gson.*
import java.lang.reflect.Modifier
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object GsonProvider {
    val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    /**
     * Make gson with custom date time deserializer
     * @return [Gson] object
     */
    fun makeGson(): Gson {
        return makeDefaultGsonBuilder().create()
    }
    /**
     * Make gson which [DateDeserializer] and compatible with [RealmObject]
     * @return [Gson] object
     */

    private fun makeDefaultGsonBuilder(): GsonBuilder {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .registerTypeAdapter(Date::class.java, DateDeserializer())
    }
    private class DateDeserializer : JsonDeserializer<Date> {
        @Throws(JsonParseException::class)
        override fun deserialize(element: JsonElement, arg1: Type, arg2: JsonDeserializationContext): Date? {
            val date = element.asString
            val formatter = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            try {
                return formatter.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                return null
            }
        }
    }
}