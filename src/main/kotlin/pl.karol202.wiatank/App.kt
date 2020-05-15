package pl.karol202.wiatank

import kotlinx.html.dom.append
import kotlinx.html.js.canvas
import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.URenderBuilder
import pl.karol202.uranium.webcanvas.WC
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.draw.size
import pl.karol202.uranium.webcanvas.draw.startOnCanvas
import pl.karol202.uranium.webcanvas.values.Vector
import kotlin.browser.document

private val canvas = document.body!!.append.canvas { }

fun main() = startOnCanvas(canvas, renderInterval = 20, physicsInterval = 20) { app() }

class App(props: BasicProps) : WCAbstractComponent<BasicProps>(props),
                               UStateful<App.State>
{
    data class State(val targetPosition: Vector,
                     val initialTankPosition: Vector,
                     val initialTankDirection: Vector) : UState

    override var state by state(State(targetPosition = Vector.randomFraction() * canvas.size,
                                      initialTankPosition = Vector.randomFraction() * canvas.size,
                                      initialTankDirection = Vector.randomDirection()))

    override fun URenderBuilder<WC>.render()
    {
        + game(targetPosition = state.targetPosition,
               initialTankPosition = state.initialTankPosition,
               initialTankDirection = state.initialTankDirection)
    }
}

fun WCRenderScope.app(key: Any = AutoKey) = component(::App, BasicProps(key))
