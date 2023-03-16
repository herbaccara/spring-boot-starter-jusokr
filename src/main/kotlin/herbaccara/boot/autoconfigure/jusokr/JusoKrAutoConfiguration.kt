package herbaccara.boot.autoconfigure.jusokr

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import herbaccara.jusokr.JusoKrService
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import java.util.*

@AutoConfiguration
@EnableConfigurationProperties(JusoKrProperties::class)
@ConditionalOnProperty(prefix = "jusokr", value = ["enabled"], havingValue = "true", matchIfMissing = true)
class JusoKrAutoConfiguration {

    @Bean
    fun jusoKrService(properties: JusoKrProperties): JusoKrService {
        if (properties.confmKey.isEmpty()) throw NullPointerException()

        val objectMapper = jacksonObjectMapper().apply {
            findAndRegisterModules()
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, properties.failOnUnknownProperties)
        }

        val restTemplate = RestTemplate()

        return JusoKrService(restTemplate, objectMapper, properties)
    }
}
