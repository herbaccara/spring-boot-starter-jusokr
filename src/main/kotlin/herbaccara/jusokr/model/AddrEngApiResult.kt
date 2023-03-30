package herbaccara.jusokr.model

import herbaccara.jusokr.model.AddrEngApiResult.Juso

data class AddrEngApiResult(
    override val common: PageableCommon,
    override val juso: List<Juso> = emptyList()
) : ApiResult<Juso> {

    data class Juso(
        val zipNo: String,
        val emdNm: String,
        val rn: String,
        val jibunAddr: String,
        val siNm: String,
        val sggNm: String,
        val admCd: String,
        val udrtYn: String,
        val lnbrMnnm: String,
        val roadAddr: String,
        val korAddr: String,
        val lnbrSlno: String,
        val buldMnnm: String,
        val bdKdcd: String,
        val rnMgtSn: String,
        val liNm: String,
        val mtYn: String,
        val buldSlno: String
    )
}
