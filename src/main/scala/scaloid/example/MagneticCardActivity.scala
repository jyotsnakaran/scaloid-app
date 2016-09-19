package scaloid.example

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.telpo.tps550.api.magnetic.MagneticCard
import org.scaloid.common._

import scala.util.{Failure, Success, Try}

class MagneticCardActivity extends SActivity with SContext {

  lazy val cardDetails = new STextView()

  lazy val cardNumberLabel = new STextView("Card Number").visibility(View.INVISIBLE)
  lazy val cardNumberText = new STextView().visibility(View.INVISIBLE)

  lazy val cardHolderLabel = new STextView("Card Holder Name").visibility(View.INVISIBLE)
  lazy val cardHolderText = new STextView().visibility(View.INVISIBLE)

  lazy val swipeCardBtn = new SButton("Swipe Card For Details")
  lazy val swipeInProgress = new STextView("Please swipe...").visibility(View.INVISIBLE)
  lazy val readSuccessfulBtn = new SButton("Exit to Main Menu").visibility(View.INVISIBLE).onClick{
    val intent = SIntent[TestActivity]
    startActivity(intent)
  }

  private def displayCardDetails(cardData: Array[String]): Unit = {
    val cData = cardData(0).split('^')
    val cardNumber = cData(0).substring(2)
    val cardHolderName = cData(1).substring(0,cData(1).length-1)


    cardDetails.append("\nCard Number is : " + cardNumber)
    cardDetails.append("\n\n\nCard Holder Name is : " + cardHolderName)

  }

  private def readOpenedCard() {
    Try{
      MagneticCard.startReading()
      MagneticCard.check(20000)
    } match{
      case Success(magneticCardDetails) =>
//        swipeInProgress.visibility(View.INVISIBLE)
        swipeCardBtn.visibility(View.INVISIBLE)


          displayCardDetails(magneticCardDetails)
//          cardDetails.append(data + ",\n\n")
          MagneticCard.close()

        readSuccessfulBtn.visibility(View.VISIBLE)
      case Failure(err) => MagneticCard.close()
        val intent = SIntent[MagneticCardActivity]
        startActivity(intent)
        toast("Timed Out While Reading Card!! \n Swipe Again to get Details")
    }
  }

  override def onCreate(savedState: Bundle) {
    super.onCreate(savedState)

    contentView = new SVerticalLayout {

      style {
        case b: SButton => b.textColor(Color.RED)
        case t: STextView => t textSize 20.dip
        case e: SEditText => e.backgroundColor(Color.YELLOW).textColor(Color.BLACK)
      }

      swipeCardBtn.here
//      swipeInProgress.here
      swipeCardBtn.onClick {
//        swipeInProgress.visibility(View.VISIBLE)
        Try {
          MagneticCard.open()
        } match {
          case Success(data) => readOpenedCard()
          case Failure(err) => MagneticCard.close()
            val intent = SIntent[MagneticCardActivity]
            startActivity(intent)
            toast("Error while open Magnetic Card" + err)
        }
      }

 /*     new SLinearLayout{
        cardNumberLabel.here
        cardNumberText.here
      }

      new SLinearLayout{
        cardHolderLabel.here
        cardHolderText.here
      }*/

      cardDetails.here
      readSuccessfulBtn.here
    }
  }
}
