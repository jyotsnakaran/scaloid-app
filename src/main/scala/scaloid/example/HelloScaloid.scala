package scaloid.example

import android.graphics.Color
import android.view.{View, Window, WindowManager}
import org.scaloid.common._

class HelloScaloid extends SActivity with SContext {

  onCreate {
    val primorisLogo = new SImageView(R.drawable.primoris)

    contentView = new SVerticalLayout {

      primorisLogo.here

      setBackgroundColor(Color.WHITE)
      style {
        case b: SButton => b.textColor(Color.RED)
        case t: STextView => t textSize 10.dip
        case e => e.backgroundColor(Color.YELLOW)
      }

      lazy val swipeMagneticCard = new SButton("Magnetic Card").onClick{
        val intent = SIntent[MagneticCardActivity]
        startActivity(intent)
      }

      lazy val insertChip = new SButton("IC Card").onClick{
        val intent = SIntent[TestActivity]
        startActivity(intent)
      }

      swipeMagneticCard.here
      insertChip.here
    }
  }
}
