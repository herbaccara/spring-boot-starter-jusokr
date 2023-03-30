package herbaccara.jusokr.model

import herbaccara.jusokr.model.AddrEngApiResult.Juso

data class AddrEngApiResult(
    override val common: PageableCommon,
    override val juso: List<Juso> = emptyList()
) : ApiResult<Juso> {

    data class Juso(
        val roadAddr: String,
        val jibunAddr: String,
        val zipNo: String,
        val admCd: String,
        val rnMgtSn: String,
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
        val korAddr: String
    )
}
