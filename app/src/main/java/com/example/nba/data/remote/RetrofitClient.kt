import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    // Kendi Render URL'ni yaz (Sonundaki '/' işaretini unutma)
    private const val BASE_URL = "https://nba-tahmin-api.onrender.com/"

    private val okHttpClient = OkHttpClient.Builder()
        // Render/Cloudflare ile SSL el sıkışma hatasını önlemek için HTTP 1.1 zorluyoruz
        .protocols(listOf(Protocol.HTTP_1_1))
        // Modern TLS şifreleme protokollerini tanımlıyoruz
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: NbaPredictionApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Özel OkHttpClient bağlandı
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbaPredictionApi::class.java)
    }
}
