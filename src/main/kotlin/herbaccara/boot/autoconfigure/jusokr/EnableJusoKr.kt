package herbaccara.boot.autoconfigure.jusokr

import org.springframework.context.annotation.Import
import java.lang.annotation.*

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Import(JusoKrAutoConfiguration::class)
annotation class EnableJusoKr
