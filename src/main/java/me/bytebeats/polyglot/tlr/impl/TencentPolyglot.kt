package me.bytebeats.polyglot.tlr.impl

import com.fasterxml.jackson.databind.ObjectMapper
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.AbstractPolyglot
import me.bytebeats.polyglot.util.ParamUtils
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.util.EntityUtils

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/29 22:39
 * @version 1.0
 * @description TencentPolyglot depends on Tencent/QQ to offer translation service
 */

class TencentPolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "https://fanyi.qq.com/api/translate"
    }

    override fun addSupportedLanguages() {
        langs[Lang.ZH] = "zh"
        langs[Lang.EN] = "en"
        langs[Lang.JP] = "jp"
        langs[Lang.KOR] = "kr"
        langs[Lang.FRA] = "fr"
        langs[Lang.RU] = "ru"
        langs[Lang.DE] = "de"
        langs[Lang.SPA] = "es"
        langs[Lang.IT] = "it"
        langs[Lang.TR] = "tr"
        langs[Lang.PT] = "pt"
        langs[Lang.VIE] = "vi"
        langs[Lang.ID] = "id"
        langs[Lang.TH] = "th"
        langs[Lang.MS] = "ms"
        langs[Lang.ARA] = "ar"
        langs[Lang.HI] = "hi"
    }

    /**
     * {
     * "sessionUuid":"translate_uuid1598713222907",
     * "translate":{"errCode":0,"errMsg":"","sessionUuid":"translate_uuid1598713222907","source":"zh","target":"en","records":[{"sourceText":"德意志","targetText":"Germany","traceId":"07f3fe9c581f42c684d6a89bf4386a8d"}],"full":true,"options":{}},
     * "dict":{"word":"德意志","detailUrl":"https://dictweb.translator.qq.com/?id=634106360d0fd9bdd5f0c32c31276e60","enWords":["Germany","Deutschland"],"data":[{"en_hash":"634106360d0fd9bdd5f0c32c31276e60"}],"type":"dict","errCode":0,"errMsg":""},
     * "suggest":{"data":[{"id":"634106360d0fd9bdd5f0c32c31276e60","word":"德意志","suggest_translation":"Germany / Deutschland"}],"errCode":0,"errMsg":""},
     * "errCode":0,
     * "errMsg":"ok"
     * }
     */
    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val result = mapper.readTree(text).path("translate").path("records")[0].findValuesAsText("targetText")
        if (result.isEmpty()) {
            return ""
        }
        val dst = StringBuilder()
        for (v in result) {
            if (v.isBlank()) {
                continue
            }
            if (dst.isNotEmpty()) {
                dst.append("\n")
            }
            dst.append(v)
        }
        return dst.toString()
    }

    override fun query(): String {
        val request = HttpPost(url)
        request.entity = UrlEncodedFormEntity(ParamUtils.map2List(formData), "UTF-8")

        request.setHeader(
            "Cookie",
            "pgv_pvi=1705084928; ptui_loginuin=894072484; RK=mAZRrIPzYv; ptcz=8b45af582e9364916ec12acb830387b73c2d4c9bb6272bdf710ead1d36582fb0; pgv_pvid=298308139; tvfe_boss_uuid=130f8de49cadf041; fy_guid=5034187a-2473-4826-b642-7f98189285f5; ADHOC_MEMBERSHIP_CLIENT_ID1.0=a3427cb4-7fcc-4780-d795-19367eb07ba8; qtv=1a2fdfe6b95fe470; qtk=q7HYYVBOT2nCyJYCAl7PMDfA7OREbOjwX/+sS0uPR15/1WL70JPZzjoaY01WFs2JcTvQBYgafngxsIp7pYsTCM8HfpEFVaEh/4CfYWCFIJk/PQOEngkKMyWEj+XVUrCEVsb67qFT85YD6/OWh71w2w==; openCount=1; gr_user_id=ca0d5edc-7bc0-443b-87b6-65c86398475a; 8507d3409e6fad23_gr_session_id=627af602-9933-494c-9bf5-82aeb077142e; grwng_uid=ec18381f-451e-4b36-acc2-f17ae5c9869e; 8c66aca9f0d1ff2e_gr_session_id=755231cc-f017-44af-bb8e-d605457e4cbb; 8c66aca9f0d1ff2e_gr_session_id_755231cc-f017-44af-bb8e-d605457e4cbb=true"
        )
        request.setHeader("Origin", "https://fanyi.qq.com")

        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
//        println(result)
        close(entity, response)
        return result
    }

    override fun setFormData(from: Lang, to: Lang, text: String) {
        formData["source"] = langs[from]!!
        formData["target"] = langs[to]!!
        formData["sourceText"] = text
        formData["sessionUuid"] = "translate_uuid${System.currentTimeMillis()}"
    }
}