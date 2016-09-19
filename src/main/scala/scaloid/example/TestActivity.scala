package scaloid.example

import android.graphics.Color
import org.scaloid.common._

class TestActivity extends SActivity {


  onCreate {
    contentView = new SVerticalLayout{
      style {
        case b: SButton => b.textColor(Color.RED)
        case t: STextView => t textSize 10.dip
        case e: SEditText => e.backgroundColor(Color.YELLOW).textColor(Color.BLACK)
      }

      lazy val testButton = new SButton("TEST Button").onClick{
          toast("In the new Activity")
      }

      testButton.here
    }
  }
}
