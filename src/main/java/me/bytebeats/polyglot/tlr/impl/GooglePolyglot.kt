package me.bytebeats.polyglot.tlr.impl

import com.fasterxml.jackson.databind.ObjectMapper
import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.AbstractPolyglot
import me.bytebeats.polyglot.util.GlotJsUtils
import me.bytebeats.polyglot.util.LogUtils
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.utils.URIBuilder
import org.apache.http.util.EntityUtils
import javax.script.Invocable
import javax.script.ScriptEngineManager

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/30 00:18
 * @version 1.0
 * @description GooglePolyglot depends on Google-translate-cn to offer translation service
 */

class GooglePolyglot() : AbstractPolyglot(URL) {
    companion object {
        private const val URL = "https://translate.google.cn/translate_a/single"
    }

    override fun addSupportedLanguages() {
        langs[Lang.ZH] = "zh-CN"
        langs[Lang.CHT] = "zh-TW"
        langs[Lang.EN] = "en"
        langs[Lang.JP] = "ja"
        langs[Lang.KOR] = "ko"
        langs[Lang.FRA] = "fr"
        langs[Lang.RU] = "ru"
        langs[Lang.DE] = "de"
        langs[Lang.SPA] = "es"
        langs[Lang.IT] = "it"
        langs[Lang.VIE] = "vi"
        langs[Lang.TH] = "th"
        langs[Lang.ARA] = "ar"
    }

    override fun parse(text: String): String {
        val mapper = ObjectMapper()
        val parent = mapper.readTree(text)[0]
        val dst = StringBuilder()
        for (node in parent) {
            dst.append(node[0].asText())
        }
        return dst.toString()
    }

    override fun query(): String {
        val uri = URIBuilder(url)
        for ((k, v) in formData) {
            uri.addParameter(k, v)
        }

        val request = HttpGet(uri.toString())
        val response = httpClient.execute(request)
        val entity = response.entity
        val result = EntityUtils.toString(entity, "UTF-8")
        close(entity, response)
        return result
    }

    override fun addFormData(from: Lang, to: Lang, text: String) {
        formData["client"] = "t"
        formData["sl"] = langs[from]!!
        formData["tl"] = langs[to]!!
        formData["q"] = text
        formData["tk"] = token(text)
        formData["hl"] = "zh-CN"
        formData["dt"] = "at"
        formData["dt"] = "bd"
        formData["dt"] = "ld"
        formData["dt"] = "md"
        formData["dt"] = "qca"
        formData["dt"] = "rw"
        formData["dt"] = "rm"
        formData["dt"] = "ss"
        formData["dt"] = "t"
        formData["ie"] = "UTF-8"
        formData["oe"] = "UTF-8"
        formData["source"] = "btn"
        formData["sssel"] = "0"
        formData["tssel"] = "0"
        formData["kc"] = "0"
    }

    private fun token(text: String): String {
        try {
            val engine = ScriptEngineManager().getEngineByName("js")
            val reader = GlotJsUtils.getReader(GlotJsUtils.JS_GOOGLE)
            engine.eval(reader)
            if (engine is Invocable) {
                return engine.invokeFunction("token", text).toString()
            }
        } catch (e: Exception) {
            LogUtils.info(e.message)
        }
        return ""
    }
}