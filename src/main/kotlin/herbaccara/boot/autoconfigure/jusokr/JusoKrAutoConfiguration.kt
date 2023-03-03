package herbaccara.boot.autoconfigure.jusokr

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import herbaccara.jusokr.JusoKrService
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import java.util.*

@AutoConfiguration
@EnableConfigurationProperties(JusoKrProperties::class)
@ConditionalOnProperty(prefix = "jusokr", value = ["enabled"], havingValue = "true")
class JusoKrAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun objectMapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            findAndRegisterModules()
        }
    }

    @Bean
    @ConditionalOnMissingBean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun jusoKrService(
        restTemplate : RestTemplate,
        objectMapper: ObjectMapper,
        properties: JusoKrProperties
    ): JusoKrService {
        if (properties.confmKey.isEmpty()) throw NullPointerException()

        return JusoKrService(restTemplate, objectMapper, properties)
    }
}
