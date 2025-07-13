package main

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.html.URI
import org.intellij.markdown.parser.LinkMap
import org.intellij.markdown.parser.MarkdownParser
import java.io.File


fun main() {

    @Serializable
    data class Metadata(val title: String, val published: kotlinx.datetime.LocalDate, val excerpt: String, val image: String){

    }

    
    val txt = File("src/main/resources/blog.md").readText()
    val st = txt.split("---")

    val metaData = st[0].trimIndent()
    val blogText = st[1].trimIndent()

    val flavour = CommonMarkFlavourDescriptor()
    val tree = MarkdownParser(flavour).buildMarkdownTreeFromString(blogText)
    val htmlGeneratingProviders =
        flavour.createHtmlGeneratingProviders(LinkMap.buildLinkMap(tree, blogText), URI.create("google.com/"))
    val html =
        HtmlGenerator(blogText, tree, htmlGeneratingProviders, includeSrcPositions = false).generateHtml()

    println(html)

    val format = Json{ isLenient = true }
    val decoded = format.decodeFromString<Metadata>(metaData)
    println(decoded)

}
