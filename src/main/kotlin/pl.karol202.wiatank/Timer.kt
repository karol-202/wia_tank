package pl.karol202.wiatank

import pl.karol202.uranium.core.common.*
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.webcanvas.WCRenderBuilder
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.physics.physicsPerformer
import kotlin.time.Duration

class Timer(props: Props) : WCAbstractComponent<Timer.Props>(props),
                            UStateful<Timer.State>
{
	data class Props(override val key: Any,
	                 val tickTime: Duration,
	                 val onTick: () -> Unit) : UProps

	data class State(val currentTime: Duration = Duration.ZERO) : UState

	override var state by state(State())

	override fun WCRenderBuilder.render() {
		+ physicsPerformer { onUpdate(deltaTime) }
	}

	private fun onUpdate(deltaTime: Duration)
	{
		val newTime = state.currentTime + deltaTime
		if(newTime >= props.tickTime)
		{
			props.onTick()
			setState { copy(currentTime = newTime - props.tickTime) }
		}
		else setState { copy(currentTime = newTime) }
	}
}

fun WCRenderScope.timer(key: Any = AutoKey,
                        tickTime: Duration,
                        onTick: () -> Unit) =
		component(::Timer, Timer.Props(key, tickTime, onTick))
