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

fun main() = startOnCanvas(canvas, renderInterval = 20, physicsInterval = 20) { app(size = canvas.size) }

class App(props: Props) : WCAbstractComponent<App.Props>(props),
                               UStateful<App.State>
{
    data class Props(override val key: Any,
                     val size: Vector) : UProps

    data class State(val targetPosition: Vector,
                     val initialTankPosition: Vector,
                     val initialTankDirection: Vector) : UState

    override var state by state(createRandomState())

    private fun createRandomState() = State(targetPosition = Vector.randomFraction() * props.size,
                                            initialTankPosition = Vector.randomFraction() * props.size,
                                            initialTankDirection = Vector.randomDirection())

    override fun URenderBuilder<WC>.render()
    {
        + game(size = props.size,
               targetPosition = state.targetPosition,
               initialTankPosition = state.initialTankPosition,
               initialTankDirection = state.initialTankDirection,
               onGameEnd = ::onGameEnd)
    }

    private fun onGameEnd()
    {
        state = createRandomState()
    }
}

fun WCRenderScope.app(key: Any = AutoKey,
                      size: Vector) = component(::App, App.Props(key, size))
