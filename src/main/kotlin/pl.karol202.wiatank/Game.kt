package pl.karol202.wiatank

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.URenderBuilder
import pl.karol202.uranium.webcanvas.WC
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.assets.loadImage
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.flip
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.containers.translate
import pl.karol202.uranium.webcanvas.component.physics.WCRigidbody
import pl.karol202.uranium.webcanvas.component.physics.collider.collider
import pl.karol202.uranium.webcanvas.component.physics.collider.collisionDomain
import pl.karol202.uranium.webcanvas.component.physics.rigidbody
import pl.karol202.uranium.webcanvas.component.primitives.image
import pl.karol202.uranium.webcanvas.physics.collider.RectCollider
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Vector
import kotlin.math.abs
import kotlin.math.sign
import kotlin.time.seconds

private const val TANK_SPEED = 300.0
private const val TANK_BARREL_X_OFFSET = 130.0
private const val TANK_BARREL_Y_OFFSET = 32.5
private const val TANK_BOOM_RADIUS = 25.0
private const val TARGET_RADIUS = 50.0
private const val TARGET_AIM_RADIUS = 10.0

class Game(props: Props) : WCAbstractComponent<Game.Props>(props),
                           UStateful<Game.State>
{
	data class Props(override val key: Any,
	                 val size: Vector,
	                 val targetPosition: Vector,
	                 val initialTankPosition: Vector,
	                 val initialTankDirection: Vector,
	                 val onGameEnd: () -> Unit) : UProps

	data class State(val tankState: WCRigidbody.State,
	                 val flip: Boolean,
	                 val gameEnd: Boolean) : UState

	override var state by state(createNewState())

	private fun createNewState() = State(tankState = WCRigidbody.State(position = props.initialTankPosition,
	                                                                   velocity = props.initialTankDirection * TANK_SPEED),
	                                     flip = false,
	                                     gameEnd = false)

	override fun onUpdate(previousProps: Props?)
	{
		if(props != previousProps) state = createNewState()
	}

	override fun URenderBuilder<WC>.render()
	{
		if(!state.gameEnd)
			+ timer(key = 1, tickTime = 4.seconds, onTick = ::flipTank)
		else
			+ timer(key = 2, tickTime = 3.seconds, onTick = props.onGameEnd)

		+ target()
		+ collisionDomain {
			+ screenColliders()
			+ tankObject()
		}
	}

	private fun WCRenderScope.screenColliders() = group {
		+ collider(key = 1, collider = RectCollider(Bounds(0.0, -100.0, props.size.x, 100.0)))
		+ collider(key = 2, collider = RectCollider(Bounds(props.size.x, 0.0, 100.0, props.size.y)))
		+ collider(key = 3, collider = RectCollider(Bounds(0.0, props.size.y, props.size.x, 100.0)))
		+ collider(key = 4, collider = RectCollider(Bounds(-100.0, 0.0, 100.0, props.size.y)))
	}

	private fun WCRenderScope.target() = translate(vector = props.targetPosition) {
		+ image(image = loadImage("assets/target.png"),
		        drawBounds = Bounds(-TARGET_RADIUS, -TARGET_RADIUS, 2 * TARGET_RADIUS, 2 * TARGET_RADIUS))
	}

	private fun WCRenderScope.tankObject() = rigidbody(state = state.tankState,
	                                                   mass = 1.0,
	                                                   collider = RectCollider(Bounds(0.0, 0.0, 130.0, 60.0)),
	                                                   onStateChange = ::setTankState,
	                                                   onCollision = { bounce(it.selfNormal, 1.0) }) {
		+ translate(vector = if(state.flip) Vector(130.0, 0.0) else Vector.ZERO) {
			+ flip(horizontal = state.flip) {
				+ tank()
				if(state.gameEnd) + image(image = loadImage("assets/boom.png"),
				                          drawBounds = Bounds(x = TANK_BARREL_X_OFFSET - TANK_BOOM_RADIUS,
				                                              y = TANK_BARREL_Y_OFFSET - TANK_BOOM_RADIUS,
						                                      width = 2 * TANK_BOOM_RADIUS,
						                                      height = 2 * TANK_BOOM_RADIUS))
			}
		}
	}

	private fun flipTank() = setState { copy(flip = !flip) }

	private fun setTankState(tankState: WCRigidbody.State)
	{
		if(state.gameEnd) return
		val barrelY = tankState.position.y + TANK_BARREL_Y_OFFSET
		val targetYMatch = abs(barrelY - props.targetPosition.y) <= TARGET_AIM_RADIUS

		val targetDirection = (props.targetPosition.x - tankState.position.x - TANK_BARREL_X_OFFSET).sign
		val pointingDirection = if(state.flip) -1.0 else 1.0
		val targetDirectionMatch = targetDirection == pointingDirection

		val aimingAtTarget = targetYMatch && targetDirectionMatch
		setState { copy(tankState = tankState, gameEnd = aimingAtTarget) }
	}
}

fun WCRenderScope.game(key: Any = AutoKey,
                       size: Vector,
                       targetPosition: Vector,
                       initialTankPosition: Vector,
                       initialTankDirection: Vector,
                       onGameEnd: () -> Unit) =
		component(::Game, Game.Props(key, size, targetPosition, initialTankPosition, initialTankDirection, onGameEnd))
