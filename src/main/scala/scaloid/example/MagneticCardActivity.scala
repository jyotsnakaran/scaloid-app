package scaloid.example

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.telpo.tps550.api.magnetic.MagneticCard
import org.scaloid.common._
import scala.util.{Failure, Success, Try}

class MagneticCardActivity extends SActivity with SContext {

  lazy val processingBtn = new STextView()
  lazy val cardDetails = new STextView()

  lazy val swipeCardBtn = new SButton("Swipe Card For Details").onClick {
    // TODO : Implement a spinner until card rea is in progress
//   runOnUiThread(longToast("Reading in progress"))
    openMagneticCard()
  }

  lazy val readSuccessfulBtn = new SButton("Exit to Main Menu").visibility(View.INVISIBLE).onClick {
    val intent = SIntent[MainActivity]
    startActivity(intent)
  }

  private def displayCardDetails(cardData: Array[String]): Unit = {
    val cData = cardData(0).split('^')
    val cardNumber = cData(0).substring(2)
    val cardHolderName = cData(1).substring(0, cData(1).length - 1)

    cardDetails.append("Card Number is : " + cardNumber)
    cardDetails.append("\n\n\nCard Holder Name is : " + cardHolderName + "\n\n")

  }

  private def readOpenedCard() {
    Try {
      MagneticCard.startReading()
      MagneticCard.check(20000)
    } match {
      case Success(magneticCardDetails) =>
        swipeCardBtn.visibility(View.INVISIBLE)
//        processingBtn.visibility(View.INVISIBLE)

        displayCardDetails(magneticCardDetails)
        MagneticCard.close()

        readSuccessfulBtn.visibility(View.VISIBLE)
      case Failure(err) => MagneticCard.close()
        val intent = SIntent[MagneticCardActivity]
        startActivity(intent)
        toast("Timed Out While Reading Card!! \n Swipe Again to get Details")
    }
  }

  private def openMagneticCard(){
    Try {
      toast("Processing Request")
      MagneticCard.open()
    } match {
      case Success(data) => readOpenedCard()
      case Failure(err) => MagneticCard.close()
        val intent = SIntent[MagneticCardActivity]
        startActivity(intent)
        toast("Error while open Magnetic Card" + err)
    }
  }

  override def onCreate(savedState: Bundle) {
    super.onCreate(savedState)

    contentView = new SVerticalLayout {

      val primorisLogo = new SImageView(R.drawable.primoris)
      primorisLogo.here
      setBackgroundColor(Color.WHITE)

      style {
        case b: SButton => b.textColor(Color.GREEN)
        case t: STextView => t textSize 15.dip
          t.textColor(Color.BLACK)
        case e => e.backgroundColor(Color.YELLOW)
      }

      readSuccessfulBtn

      swipeCardBtn.here
      processingBtn.here
      cardDetails.here
      readSuccessfulBtn.here
    }
  }
}
