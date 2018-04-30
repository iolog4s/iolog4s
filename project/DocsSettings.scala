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
      micrositeBaseUrl          := "/iolog4s/",
      micrositeDocumentationUrl := "/iolog4s/docs/index.html",
      micrositeHomepage         := "http://iolog4s.org/",
      micrositeGithubOwner      := "iolog4s",
      micrositeGithubRepo       := "iolog4s",
      micrositeHighlightTheme   := "atom-one-light",
      //-------------- docs project ------------
      //micrositeImgDirectory := (resourceDirectory in Compile).value / "microsite" / "images",
      //micrositeCssDirectory := (resourceDirectory in Compile).value / "microsite" / "styles"
      //micrositeJsDirectory := (resourceDirectory in Compile).value / "microsite" / "scripts"
      micrositePalette := Map(
        "brand-primary"   -> "#A50000",
        "brand-secondary" -> "#A50000",
        "brand-tertiary"  -> "#000000",
        "gray-dark"       -> "#000000",
        "gray"            -> "#A50000",
        "gray-light"      -> "#A50000",
        "gray-lighter"    -> "#A50000",
        "white-color"     -> "#FFFFFF"
      ),
      micrositeFooterText := Some("""Ⓒ 2018 <a href="http://iolog4s.org/">iolog4s</a>"""),
      //------ same as default settings --------
      micrositePushSiteWith      := GHPagesPlugin,
      micrositeGitHostingService := GitHub,
      git.remoteRepo             := "git@github.com:iolog4s/iolog4s.git"
    ) ++ micrositeTasksSettings

}
