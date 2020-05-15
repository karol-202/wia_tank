package pl.karol202.wiatank

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.URenderBuilder
import pl.karol202.uranium.webcanvas.WC
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.assets.loadImage
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.flip
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.physics.rigidbody
import pl.karol202.uranium.webcanvas.component.primitives.image
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.physics.collider.RectCollider
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Vector

private const val TANK_SPEED = 100.0

class Game(props: Props) : WCAbstractComponent<Game.Props>(props),
                           UStateful<Game.State>
{
	data class Props(override val key: Any,
	                 val targetPosition: Vector,
	                 val initialTankPosition: Vector,
	                 val initialTankDirection: Vector) : UProps

	data class State(val tankState: WCRigidbody.State,
	                 val flip: Boolean) : UState

	override var state by state(State(tankState = WCRigidbody.State(position = props.initialTankPosition,
	                                                                velocity = props.initialTankDirection * TANK_SPEED),
	                                  flip = false))

	override fun URenderBuilder<WC>.render()
	{
		+ target()
		+ tank(color = Color.named("red"))
	}

	private fun WCRenderScope.target() = translate(vector = props.targetPosition) {
		+ image(image = loadImage("assets/target.png"),
		        drawBounds = Bounds(-50.0, -50.0, 100.0, 100.0))
	}

	private fun WCRenderScope.tank(color: Color) = rigidbody(state = state.tankState,
	                                                         mass = 1.0,
	                                                         collider = RectCollider(Bounds(0.0, 0.0, 130.0, 60.0)),
	                                                         onStateChange = ::setTankState) {
		+ flip(horizontal = state.flip) {
			+ rectFill(key = 1,
			           bounds = Bounds(0.0, 0.0, 100.0, 60.0),
			           color = color)
			+ rectFill(key = 2,
			           bounds = Bounds(100.0, 25.0, 30.0, 10.0),
			           color = color)
		}
	}

	private fun setTankState(state: WCRigidbody.State) = setState { copy(tankState = state) }
}

fun WCRenderScope.game(key: Any = AutoKey,
                       targetPosition: Vector,
                       initialTankPosition: Vector,
                       initialTankDirection: Vector) =
		component(::Game, Game.Props(key, targetPosition, initialTankPosition, initialTankDirection))
