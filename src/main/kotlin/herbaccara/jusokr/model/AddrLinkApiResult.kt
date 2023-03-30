package herbaccara.jusokr.model

import herbaccara.jusokr.model.AddrLinkApiResult.Juso

data class AddrLinkApiResult(
    override val common: PageableCommon,
    override val juso: List<Juso> = emptyList()
) : ApiResult<Juso> {

    data class Juso(
        val roadAddr: String,
        val roadAddrPart1: String,
        val roadAddrPart2: String,
        val jibunAddr: String,
        val engAddr: String,
        val zipNo: String,
        val admCd: String,
        val rnMgtSn: String,
        val bdMgtSn: String,
        val detBdNmList: String,
        val bdNm: String,
        val bdKdcd: String,
        val siNm: String,
        val sggNm: String,
        val emdNm: String,
        val liNm: String,
        val rn: String,
        val udrtYn: String,
        val buldMnnm: Int,
        val buldSlno: Int,
        val mtYn: String,
        val lnbrMnnm: Int,
        val lnbrSlno: Int,
        val emdNo: String
    )
}
