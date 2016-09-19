package scaloid.example

import android.graphics.Color
import android.os.Bundle
import com.telpo.tps550.api.magnetic.MagneticCard
import org.scaloid.common._

import scala.util.{Failure, Success, Try}
/**
  * Created by knoldus on 16/9/16.
  */
class MainActivity extends SActivity {

  lazy val meToo = new STextView("Me too")
  lazy val newRedButton = new SButton(R.string.red)

  override def onCreate(savedState: Bundle) {
    super.onCreate(savedState)

    contentView = new SVerticalLayout {

      style {
        case b: SButton => b.textColor(Color.RED)
        case t: STextView => t textSize 10.dip
        case e: SEditText => e.backgroundColor(Color.YELLOW).textColor(Color.BLACK)
      }

      Try {
        MagneticCard.open()
      } match {
        case Success(data) =>

          val x: Int = MagneticCard.startReading()
          meToo.text("I got" + x)
          val y: Array[String] = MagneticCard.check(20000)
          meToo.text("I got : " + y(0))

          MagneticCard.close()
          toast("Open Success")
        case Failure(err) =>
          MagneticCard.close()
          toast("Error while open Magnetic Card" + err)
      }
      meToo.here
    }
  }

}
