package com.nobrain.androidsamples.jsonparser.gson

import com.bluelinelabs.logansquare.LoganSquare
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nobrain.androidsamples.autovalue.MyAutovalueTypeAdapter
import com.nobrain.androidsamples.jsonparser.logan.LoganData
import com.vimeo.stag.generated.Stag
import org.junit.Test
import kotlin.system.measureTimeMillis


class SmallGsonTest {
    @Test
    @Throws(Exception::class)
    fun gsonTest() {
        var totalTime = 0L
        var gson = Gson()
        for (index in 1..10000) {
            totalTime += measureTimeMillis {
                gson.fromJson(javaClass.getResourceAsStream("/small.json").reader(),
                    Data::class.java)
            }
        }

        println("${String.format("%06d", totalTime)} : Small Gson")

    }

    @Test
    @Throws(Exception::class)
    fun typeAdapterTest() {
        var totalTime = 0L
        var gson = GsonBuilder()
            .registerTypeAdapterFactory(Stag.Factory())
            .create()
        for (index in 1..10000) {
            totalTime += measureTimeMillis {
                gson.fromJson(javaClass.getResourceAsStream("/small.json").reader(),
                    Data::class.java)
            }
        }

        println("${String.format("%06d", totalTime)} : Small Gson TypeAdapter")

    }


    @Test
    fun autovalueTest() {
        var totalTime = 0L
        val gson = GsonBuilder()
            .registerTypeAdapterFactory(MyAutovalueTypeAdapter.create())
            .create()

        for (index in 1..10000) {
            totalTime += measureTimeMillis {
                gson.fromJson(javaClass.getResourceAsStream("/small.json").reader(),
                    Data::class.java)
            }
        }
        println("${String.format("%06d", totalTime)} : Small AutoValue")

    }

    @Test
    fun jacksonTest() {
        var totalTime = 0L
        var jacksonMapper = ObjectMapper()
        for (index in 1..10000) {
            totalTime += measureTimeMillis {
                jacksonMapper.readValue(javaClass.getResourceAsStream("/small.json"), Data::class.java)
            }
        }
        println("${String.format("%06d", totalTime)} : Small Jackson")

    }

    @Test
    fun loganTest() {
        var totalTime = 0L
        val mapper = LoganSquare.mapperFor(LoganData::class.java)
        for (index in 1..10000) {
            totalTime += measureTimeMillis {
                mapper.parse(javaClass.getResourceAsStream("/small.json"))
            }
        }
        println("${String.format("%06d", totalTime)} : Small Logan")
    }


}