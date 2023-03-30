package herbaccara.jusokr.model

class PageableCommon(
    /***
     * 에러코드. 0 이면 정상, -999 면 시스템 에러
     */
    errorCode: String,
    errorMessage: String,
    val currentPage: Int,
    val countPerPage: Int,
    totalCount: Int
) : Common(errorCode, errorMessage, totalCount)
