package herbaccara.jusokr

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import herbaccara.boot.autoconfigure.jusokr.JusoKrProperties
import herbaccara.jusokr.exception.JusoKrResponseException
import herbaccara.jusokr.model.Results
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

class JusoKrService(
    private val restTemplate: RestTemplate,
    private val objectMapper: ObjectMapper,
    private val properties: JusoKrProperties
) {
    companion object {

        private val reservedWords = listOf(
            "OR",
            "SELECT",
            "INSERT",
            "DELETE",
            "UPDATE",
            "CREATE",
            "DROP",
            "EXEC",
            "UNION",
            "FETCH",
            "DECLARE",
            "TRUNCATE"
        )

        private val specialCharacters = listOf("%", "=", ">", "<")

        internal fun filteredKeyword(keyword: String): String {
            var filteredKeyword = keyword
            for (reservedWord in reservedWords) {
                if (filteredKeyword.contains(reservedWord, true)) {
                    filteredKeyword = filteredKeyword.replace(reservedWord, "", true)
                }
            }
            for (c in specialCharacters) {
                filteredKeyword = filteredKeyword.replace(c, "")
            }
            return filteredKeyword.replace("\\s+".toRegex(), " ")
        }
    }

    /***
     * 검색API - 도로명주소
     * https://business.juso.go.kr/addrlink/openApi/searchApi.do
     */
    @JvmOverloads
    fun addrLinkApi(keyword: String, currentPage: Int = 1, countPerPage: Int = 10): Results {
        val uri = "/addrlink/addrLinkApi.do"
        val endpoint = UriComponentsBuilder
            .fromHttpUrl("${properties.rootUri}$uri")
            .queryParam("confmKey", properties.confmKey)
            .queryParam("currentPage", currentPage)
            .queryParam("countPerPage", countPerPage)
            .queryParam("keyword", filteredKeyword(keyword))
            .queryParam("resultType", "json")
            .toUriString()

        val json = restTemplate.getForObject<JsonNode>(URI(endpoint))
        val results = objectMapper.readValue<Results>(json["results"].toString())

        val common = results.common
        if (common.errorCode != "0") {
            throw JusoKrResponseException(common.errorCode, common.errorMessage)
        }

        return results
    }
}
