package scaloid.example

import org.junit.Assert._
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.{Robolectric, RobolectricTestRunner}

@RunWith(classOf[RobolectricTestRunner])
@Config(manifest = "src/main/AndroidManifest.xml")
class MainActivityTest {
  @Test def testButtonPressed(): Unit = {
    val activity = Robolectric.setupActivity(classOf[MainActivity])
    assertTrue(activity.meToo.text == "Me too")
    activity.redBtn.performClick()
    assertTrue(activity.meToo.text == "PRESSED")
  }
}