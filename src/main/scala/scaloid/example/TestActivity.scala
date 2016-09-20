package scaloid.example

import android.graphics.Color
import org.scaloid.common._

class TestActivity extends SActivity {


  onCreate {
    contentView = new SVerticalLayout{

      val primorisLogo = new SImageView(R.drawable.primoris)
      primorisLogo.here
      setBackgroundColor(Color.WHITE)

      style {
        case b: SButton => b.textColor(Color.GREEN)
        case t: STextView => t textSize 15.dip
          t.textColor(Color.BLACK)
        case e => e.backgroundColor(Color.YELLOW)
      }

      lazy val testButton = new SButton("TEST Button").onClick{
          toast("In the new Activity")
      }

      testButton.here
    }
  }
}
