package herbaccara.jusokr.model

data class Common(
    /***
     * 에러코드. 0 이면 정상, -999 면 시스템 에러
     */
    val errorCode: String,
    val errorMessage: String,
    val currentPage: Int,
    val countPerPage: Int,
    val totalCount: Int
)
