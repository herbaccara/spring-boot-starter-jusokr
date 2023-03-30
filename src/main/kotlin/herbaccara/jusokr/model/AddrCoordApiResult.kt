package herbaccara.jusokr.model

import herbaccara.jusokr.model.AddrCoordApiResult.Juso

data class AddrCoordApiResult(
    override val common: Common,
    override val juso: List<Juso> = emptyList()
) : ApiResult<Juso> {

    data class Juso(
        val admCd: String,
        val rnMgtSn: String,
        val bdMgtSn: String,
        val udrtYn: String,
        val buldMnnm: Int,
        val buldSlno: Int,
        val entX: String,
        val entY: String,
        val bdNm: String
    )
}
