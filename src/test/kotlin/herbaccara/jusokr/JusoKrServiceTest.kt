package herbaccara.jusokr

import herbaccara.boot.autoconfigure.jusokr.JusoKrAutoConfiguration
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(
    classes = [
        JusoKrAutoConfiguration::class
    ]
)
@TestPropertySource(locations = ["classpath:application.yml"])
class JusoKrServiceTest {

    @Autowired
    lateinit var jusoKrService: JusoKrService

    @Test
    fun addrLinkApi() {
        val addrLinkApi = jusoKrService.addrLinkApi("서울시 강남구", 1, 1000)
        println()
    }
}
