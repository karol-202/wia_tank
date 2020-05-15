package pl.karol202.wiatank

import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.common.BasicProps
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.URenderBuilder
import pl.karol202.uranium.webcanvas.WC
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.draw.startOnCanvas
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import kotlin.browser.document

private val canvas = document.body!!.append.canvas { }

fun main() = startOnCanvas(canvas, renderInterval = 20, physicsInterval = 20) { app() }

class App(props: BasicProps) : WCAbstractComponent<BasicProps>(props)
{
    override fun URenderBuilder<WC>.render()
    {
        + rectFill(bounds = Bounds(100.0, 100.0, 100.0, 100.0),
                   color = Color.named("red"))
    }
}

fun WCRenderScope.app(key: Any = AutoKey) = component(::App, BasicProps(key))
