package herbaccara.boot.autoconfigure.jusokr

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "jusokr")
@ConstructorBinding
data class JusoKrProperties(
    val enabled: Boolean = true,
    val rootUri: String = "https://business.juso.go.kr",
    val failOnUnknownProperties: Boolean = false,
    val confmKey: String
)
