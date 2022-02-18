package Redis

import redis.clients.jedis.Jedis

fun main(){
    val conexion = Jedis("89.36.214.106")
    conexion.connect()
    conexion.auth("ieselcaminas.ad")

    val list = arrayListOf<String>()

    for (i in 1..25){
        list.add(i.toString())
    }

}