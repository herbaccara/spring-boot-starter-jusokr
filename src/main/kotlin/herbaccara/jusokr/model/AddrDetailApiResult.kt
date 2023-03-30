package herbaccara.jusokr.model

import herbaccara.jusokr.model.AddrDetailApiResult.Juso

data class AddrDetailApiResult(
    override val common: Common,
    override val juso: List<Juso> = emptyList()
) : ApiResult<Juso> {

    data class Juso(
        val buldMnnm: String,
        val rnMgtSn: String,
        val dongNm: String,
        val admCd: String,
        val buldSlno: String,
        val udrtYn: String
    )
}
