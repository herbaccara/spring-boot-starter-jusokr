package herbaccara.jusokr.model

interface ApiResult<T> {
    val common: Common
    val juso: List<T>
}
