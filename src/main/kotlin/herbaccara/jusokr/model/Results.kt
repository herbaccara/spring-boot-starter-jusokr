package herbaccara.jusokr.model

data class Results(
    val common: Common,
    val juso: List<Juso> = emptyList()
)
