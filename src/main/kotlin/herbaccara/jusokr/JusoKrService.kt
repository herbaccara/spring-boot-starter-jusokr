package herbaccara.jusokr

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import herbaccara.boot.autoconfigure.jusokr.JusoKrProperties
import herbaccara.jusokr.exception.JusoKrResponseException
import herbaccara.jusokr.form.SearchType
import herbaccara.jusokr.model.*
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
            for (c in specialCharacters) {
                filteredKeyword = filteredKeyword.replace(c, "")
            }
            var stop = false
            while (stop.not()) {
                var replace = false
                for (reservedWord in reservedWords) {
                    if (filteredKeyword.contains(reservedWord, true)) {
                        filteredKeyword = filteredKeyword.replace(reservedWord, "", true)
                        replace = true
                    }
                }
                stop = replace.not()
            }
            return filteredKeyword.replace("\\s+".toRegex(), " ")
        }
    }

    private inline fun <reified T : ApiResult<R>, R> getForObject(url: String): T {
        val json = restTemplate.getForObject<JsonNode>(URI(url))
        val results = objectMapper.readValue<T>(json["results"].toString())

        val common = results.common
        if (common.errorCode != "0") {
            throw JusoKrResponseException(common.errorCode, common.errorMessage)
        }

        return results
    }

    /***
     * 검색API - 좌표제공
     * https://business.juso.go.kr/addrlink/openApi/searchApi.do
     */
    @JvmOverloads
    fun addrCoordApi(
        admCd: String,
        rnMgtSn: String,
        udrtYn: String,
        buldMnnm: Int,
        buldSlno: Int
    ): AddrCoordApiResult {
        val url = "/addrlink/addrCoordApi.do"

        val endpoint = UriComponentsBuilder
            .fromHttpUrl("${properties.rootUri}$url")
            .queryParam("confmKey", properties.confmKey.coord)
            .queryParam("admCd", admCd.filter { it.isDigit() })
            .queryParam("rnMgtSn", rnMgtSn.filter { it.isDigit() })
            .queryParam("udrtYn", udrtYn.filter { it.isDigit() })
            .queryParam("buldMnnm", buldMnnm)
            .queryParam("buldSlno", buldSlno)
            .queryParam("resultType", "json")
            .toUriString()

        return getForObject(endpoint)
    }

    /***
     * 검색API - 상세주소
     * https://business.juso.go.kr/addrlink/openApi/searchApi.do
     */
    @JvmOverloads
    fun addrDetailApi(
        admCd: String,
        rnMgtSn: String,
        udrtYn: String,
        buldMnnm: Int,
        buldSlno: Int,
        searchType: SearchType? = null,
        dongNm: String? = null
    ): AddrDetailApiResult {
        val url = "/addrlink/addrDetailApi.do"

        val endpoint = UriComponentsBuilder
            .fromHttpUrl("${properties.rootUri}$url")
            .queryParam("confmKey", properties.confmKey.detail)
            .queryParam("admCd", admCd.filter { it.isDigit() })
            .queryParam("rnMgtSn", rnMgtSn.filter { it.isDigit() })
            .queryParam("udrtYn", udrtYn.filter { it.isDigit() })
            .queryParam("buldMnnm", buldMnnm)
            .queryParam("buldSlno", buldSlno)
            .apply {
                if (searchType != null) {
                    queryParam("searchType", searchType.name)
                }
                if (dongNm.isNullOrBlank().not()) {
                    queryParam("dongnm", filteredKeyword(dongNm!!))
                }
            }
            .queryParam("resultType", "json")
            .toUriString()

        return getForObject(endpoint)
    }

    /***
     * 검색API - 영문주소
     * https://business.juso.go.kr/addrlink/openApi/searchApi.do
     */
    @JvmOverloads
    fun addrEngApi(keyword: String, currentPage: Int = 1, countPerPage: Int = 10): AddrEngApiResult {
        val url = "/addrlink/addrEngApi.do"

        val endpoint = UriComponentsBuilder
            .fromHttpUrl("${properties.rootUri}$url")
            .queryParam("confmKey", properties.confmKey.eng)
            .queryParam("currentPage", currentPage)
            .queryParam("countPerPage", countPerPage)
            .queryParam("keyword", filteredKeyword(keyword))
            .queryParam("resultType", "json")
            .toUriString()

        return getForObject(endpoint)
    }

    /***
     * 검색API - 도로명주소
     * https://business.juso.go.kr/addrlink/openApi/searchApi.do
     */
    @JvmOverloads
    fun addrLinkApi(keyword: String, currentPage: Int = 1, countPerPage: Int = 10): AddrLinkApiResult {
        val url = "/addrlink/addrLinkApi.do"

        val endpoint = UriComponentsBuilder
            .fromHttpUrl("${properties.rootUri}$url")
            .queryParam("confmKey", properties.confmKey.link)
            .queryParam("currentPage", currentPage)
            .queryParam("countPerPage", countPerPage)
            .queryParam("keyword", filteredKeyword(keyword))
            .queryParam("resultType", "json")
            .toUriString()

        return getForObject(endpoint)
    }
}
