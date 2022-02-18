package Mongo

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.bson.Document
import redis.clients.jedis.Jedis

fun main() {
    val con = MongoClient(MongoClientURI("mongodb://ad:ieselcaminas@89.36.214.106/?authSource=test"))
    val bd = con.getDatabase("test")

    val ordenar = Document()
    ordenar.put("InternetDiari", -1)

    val comunitats = bd.getCollection("EstadisticaInternet").find().sort(ordenar)
    for (numero in comunitats) {
        if (numero.get("Comprat3Mesos").toString() >= "50" && numero.get("Comprat3Mesos").toString() <= "55") {
            if (numero.get("InternetDiari").toString() > "80") {
                print("${numero.get("Nom")}")
                print("         ${numero.get("InternetDiari")}")
                print("  ${numero.get("Comprat3Mesos")}\n")
            }
        }
    }
}