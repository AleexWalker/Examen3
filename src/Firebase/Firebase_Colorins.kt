package Firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.*
import java.awt.*
import java.io.FileInputStream
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class colorins(var colorR: String,var colorG: String,var colorB: String)

class ConsultaFirebase : JFrame() {

    val etColor = JLabel("Color: ")
    val colorR = JLabel("255")
    val colorG = JLabel("255")
    val colorB = JLabel("255")

    val botoCanvi = JButton("Canvi color")

    // en iniciar posem un contenidor per als elements anteriors
    init {
        contentPane.layout = BorderLayout()
        canviColor((255).toString(),(255).toString(),(255).toString())

        val panell1 = JPanel(FlowLayout())
        etColor.font = etColor.font.deriveFont(40f)
        panell1.add(etColor)
        colorR.font = colorR.font.deriveFont(40f)
        panell1.add(colorR)
        colorG.font = colorG.font.deriveFont(40f)
        panell1.add(colorG)
        colorB.font = colorB.font.deriveFont(40f)
        panell1.add(colorB)
        contentPane.add(panell1,BorderLayout.NORTH)

        val panell2 = JPanel(FlowLayout())
        panell2.add(botoCanvi)
        contentPane.add(panell2,BorderLayout.SOUTH)

        setSize(500, 400)
        isVisible = true

        // Definició de les referències de Firebase
        val serviceAccount = FileInputStream("exadfirebase-firebase-adminsdk-wgvi4-9fb6b2831d.json")

        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://exadfirebase.firebaseio.com").build()

        FirebaseApp.initializeApp(options)
        val database = FirebaseDatabase.getInstance()

        // Operacions per a escoltar pels canvis en la subclau alu_xx
        // Dins haureu de posar canviColor(r,g,b) amb els 3 colors llegits de Firebase

        botoCanvi.addActionListener {
            val cR = (Math.random() * 256).toInt()
            val cG = (Math.random() * 256).toInt()
            val cB = (Math.random() * 256).toInt()

            // Operacions per a  guardar en Firebase els 3 números cR, cG i cB convertits a strings
            val colors = database.getReference("Alumne").child("alu_02")
            colors.child("cR").setValue(cR, null)
            colors.child("cG").setValue(cG, null)
            colors.child("cB").setValue(cB, null)

            colors.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot?) {
                    colorR.text = (dataSnapshot!!.child("cR").getValue().toString())
                    colorG.text = (dataSnapshot.child("cG").getValue().toString())
                    colorB.text = (dataSnapshot.child("cG").getValue().toString())
                    canviColor(dataSnapshot.child("cR").getValue().toString(), dataSnapshot.child("cG").getValue().toString(), dataSnapshot.child("cG").getValue().toString())
                }

                override
                fun onCancelled(databaseError: DatabaseError) {
                }
            })
        }
    }

    private fun canviColor(cR: String, cG: String, cB: String) {
        println("" + cR + " " + cG + " " + cB)
        colorR.text = cR
        colorG.text = cG
        colorB.text = cB
        contentPane.background = Color(cR.toInt(), cG.toInt(), cB.toInt())
    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        ConsultaFirebase().isVisible = true
    }
}