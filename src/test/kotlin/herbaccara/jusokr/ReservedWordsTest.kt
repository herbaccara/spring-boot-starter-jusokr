package herbaccara.jusokr

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ReservedWordsTest {

    @Test
    fun test() {
        val keyword = "%=Oraa iNsErT bbiNsErT<>"
        val filteredKeyword = JusoKrService.filteredKeyword(keyword)
        Assertions.assertEquals("aa bb", filteredKeyword)
    }

    @Test
    fun test2() {
        val keyword = "OO%RR123"
        val filteredKeyword = JusoKrService.filteredKeyword(keyword)
        println(filteredKeyword)
        Assertions.assertEquals("123", filteredKeyword)
    }
}
