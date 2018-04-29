import sbt._
import Keys._
import microsites.MicrositesPlugin.autoImport._
import com.typesafe.sbt.GitPlugin.autoImport._
import microsites._
import tut._

/**
  *
  * @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 26 Apr 2018
  *
  */
object DocsSettings {

  def micrositeSettings: Seq[Setting[_]] =
    Seq(
      micrositeName             := "iolog4s",
      micrositeDescription      := "Pure logging library for Scala built on top of cats-effect",
      micrositeBaseUrl          := "iolog4s",
      micrositeDocumentationUrl := "/docs/",
      micrositeHomepage         := "http://iolog4s.org/iolog4s",
      micrositeGithubOwner      := "iolog4s",
      micrositeGithubRepo       := "iolog4s",
      micrositeHighlightTheme   := "atom-one-light",
      //-------------- docs project ------------
      //micrositeImgDirectory := (resourceDirectory in Compile).value / "microsite" / "images",
      //micrositeCssDirectory := (resourceDirectory in Compile).value / "microsite" / "styles"
      //micrositeJsDirectory := (resourceDirectory in Compile).value / "microsite" / "scripts"
      micrositePalette := Map(
        "brand-primary"   -> "#E05236",
        "brand-secondary" -> "#3F3242",
        "brand-tertiary"  -> "#2D232F",
        "gray-dark"       -> "#453E46",
        "gray"            -> "#837F84",
        "gray-light"      -> "#E3E2E3",
        "gray-lighter"    -> "#F4F3F4",
        "white-color"     -> "#FFFFFF"
      ),
      //micrositeFavicons := Seq(
      //  MicrositeFavicon("favicon16x16.png", "16x16"),
      //  MicrositeFavicon("favicon32x32.png", "32x32")
      //),
      micrositeFooterText := Some("""Ⓒ 2018 <a href="http://iolog4s.org/">iolog4s</a>"""),
      //------ same as default settings --------
      micrositePushSiteWith      := GHPagesPlugin,
      micrositeGitHostingService := GitHub,
      git.remoteRepo := "git@github.com:iolog4s/iolog4s.git"

  ) ++ micrositeTasksSettings

}
