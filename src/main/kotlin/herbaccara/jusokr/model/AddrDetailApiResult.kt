package herbaccara.jusokr.model

import herbaccara.jusokr.model.AddrDetailApiResult.Juso

data class AddrDetailApiResult(
    override val common: Common,
    override val juso: List<Juso> = emptyList()
) : ApiResult<Juso> {

    data class Juso(
        val admCd: String,
        val rnMgtSn: String,
        val udrtYn: String,
        val buldMnnm: Int,
        val buldSlno: Int,
        val dongNm: String?,
        val floorNm: String?,
        val hoNm: String?
    )
}
