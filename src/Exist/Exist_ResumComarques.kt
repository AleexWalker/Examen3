package Exist

import net.xqj.exist.ExistXQDataSource
import org.w3c.dom.Element
import javax.xml.xquery.XQConnection
import javax.xml.xquery.XQConstants
import javax.xml.xquery.XQResultSequence

fun main(){
    val s = ExistXQDataSource()
    s.setProperty("serverName", "89.36.214.106")
    s.setProperty("port", "8080")

    var conn: XQConnection = s.getConnection()
    var rs: XQResultSequence? = null

    val cntxt = conn.getStaticContext()
    cntxt.setScrollability(XQConstants.SCROLLTYPE_SCROLLABLE)
    conn.setStaticContext(cntxt)

    val sent = "/comarques//comarca"
    rs = conn.createExpression().executeQuery(sent)

    val query2 = "for \$comarca in /comarques//comarca\n" +
            "let \$nombre := \$comarca/nom\n" +
            "let \$pobles := sum(count(\$comarca/pobles/poble))\n" +
            "let \$habitantes := sum((\$comarca/pobles/poble/poblacio))\n" +
            "return concat(\$nombre, concat(\":\", concat(\"Pobles\", concat(\$pobles, concat(\"Habitants\", \$habitantes)))))"

    while (rs!!.next()) {
        val elemento = rs!!.`object` as Element
        println(elemento.getElementsByTagName("nom").item(0).firstChild.textContent + ":\n")

    }


    /*

    xquery version "3.1";

    for $comarca in /comarques//comarca
    let $nombre := $comarca/nom
    let $pobles := sum(count($comarca/pobles/poble))
    let $habitantes := sum(($comarca/pobles/poble/poblacio))
    return concat($nombre, concat(":", concat("Pobles", concat($pobles, concat("Habitants", $habitantes)))))

     */



}