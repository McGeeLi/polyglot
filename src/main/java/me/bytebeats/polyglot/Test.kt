package me.bytebeats.polyglot

import me.bytebeats.polyglot.lang.Lang
import me.bytebeats.polyglot.tlr.impl.*

/**
 * @author bytebeats
 * @email <happychinapc@gmail.com>
 * @github https://github.com/bytebeats
 * @created on 2020/8/27 17:30
 * @version 1.0
 * @description Test does testing works.
 */

private class Test {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Baidu----------------")
            val baidu = BaiduPolyglot()
            println(baidu.translate(Lang.EN, Lang.ZH, "God, are you testing me?"))
            println("Tencent----------------")
            val tencent = TencentPolyglot()
            println(tencent.translate(Lang.ZH, Lang.FRA, "德意志"))
            println("YouDao----------------")
            val youdao = YouDaoPolyglot()
            println(youdao.translate(Lang.ZH, Lang.EN, "忧郁的小乌龟"))
            println("Google----------------")
            val google = GooglePolyglot()
            println(google.translate(Lang.ZH, Lang.CHT, "台湾"))
            println("Bing----------------")
            val bing = BingPolyglot()
            println(bing.translate(Lang.ZH, Lang.FRA, "忧郁的小乌龟"))
            println("Sogou----------------")
            val sogou = SogouPolyglot()
            println(sogou.translate(Lang.ZH, Lang.CHT, "忧郁小乌龟"))
//            println("trycan----------------")
//            val trycan = TrycanPolyglot()
//            println(trycan.execute(Lang.ZH, Lang.CHT, "忧郁的小乌龟"))
            println("Omi----------------")
            val omi = OmiPolyglot()
            println(omi.translate(Lang.EN, Lang.ZH, "Blue turtle"))
//            println("iciba----------------")
//            val iciba = IcibaPolyglot()
//            println(iciba.execute(Lang.ZH, Lang.EN, "好的"))
        }
    }
}