package scaloid.example

import android.graphics.Color
import android.view.{View, Window}
import org.scaloid.common._

class HelloScaloid extends SActivity with SContext {

  onCreate {


    contentView = new SVerticalLayout{


      val uniqueId = getUniqueId

  /*    val view = new SWebView()
      view.setVerticalScrollBarEnabled(true)
      view.setBackgroundColor(Color.WHITE)
      view.here*/

      val currentView: View = findViewById(uniqueId)
      currentView.setBackground(Color.WHITE)
    /*  val rootView1 = currentView.getRootView

      rootView1.setBackgroundColor(Color.WHITE)
      */
      style {
        case b: SButton => b.textColor(Color.RED)
        case t: STextView => t textSize 10.dip
        case e: SEditText => e.backgroundColor(Color.YELLOW).textColor(Color.BLACK)
      }

      lazy val swipeMagneticCard = new SButton("Heyyy Click Me").onClick{
        val intent = SIntent[TestActivity]
        startActivity(intent)
      }

      swipeMagneticCard.here
    }



  }
}
