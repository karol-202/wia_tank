package pl.karol202.wiatank

import pl.karol202.uranium.core.common.AutoKey
import pl.karol202.uranium.core.common.BasicProps
import pl.karol202.uranium.core.element.component
import pl.karol202.uranium.core.render.URenderBuilder
import pl.karol202.uranium.webcanvas.WC
import pl.karol202.uranium.webcanvas.WCRenderScope
import pl.karol202.uranium.webcanvas.component.base.WCAbstractComponent
import pl.karol202.uranium.webcanvas.component.containers.group
import pl.karol202.uranium.webcanvas.component.primitives.circleFill
import pl.karol202.uranium.webcanvas.component.primitives.pathFill
import pl.karol202.uranium.webcanvas.component.primitives.rectFill
import pl.karol202.uranium.webcanvas.component.primitives.text
import pl.karol202.uranium.webcanvas.values.Bounds
import pl.karol202.uranium.webcanvas.values.Color
import pl.karol202.uranium.webcanvas.values.Path
import pl.karol202.uranium.webcanvas.values.Vector

class Tank(props: BasicProps) : WCAbstractComponent<BasicProps>(props)
{
	override fun URenderBuilder<WC>.render()
	{
		+ base1(key = 0)
		+ wheel(key = 1, position = Vector(30.0, 60.0))
		+ wheel(key = 2, position = Vector(50.0, 60.0))
		+ wheel(key = 3, position = Vector(70.0, 60.0))
		+ wheel(key = 4, position = Vector(90.0, 60.0))
		+ base2(key = 5)
		+ fuelTank(key = 6)
		+ connector(key = 7)
		+ barrel(key = 8)
		+ turret(key = 9)
		+ text102(key = 10)
		+ textKarol(key = 11)
	}

	private fun WCRenderScope.base1(key: Any) =
			pathFill(key = key,
			         path = Path.closed(Vector(0.0, 50.0), Vector(120.0, 50.0), Vector(100.0, 70.0), Vector(20.0, 70.0)),
			         color = Color.raw("#8ea401"))

	private fun WCRenderScope.wheel(key: Any, position: Vector) = group(key = key) {
		+ circleFill(key = 0,
		             center = position,
		             radius = 8.0,
		             color = Color.raw("black"))
		+ circleFill(key = 1,
		             center = position,
		             radius = 6.0,
		             color = Color.raw("#dacd71"))
		+ circleFill(key = 2,
		             center = position,
		             radius = 1.0,
		             color = Color.raw("black"))
	}

	private fun WCRenderScope.base2(key: Any) =
			pathFill(key = key,
			         path = Path.closed(Vector(30.0, 50.0), Vector(110.0, 50.0), Vector(100.0, 40.0), Vector(30.0, 40.0)),
			         color = Color.raw("#576601"))

	private fun WCRenderScope.fuelTank(key: Any) =
			rectFill(key = key,
			         bounds = Bounds(10.0, 37.0, 20.0, 13.0),
			         color = Color.raw("#3a4300"))

	private fun WCRenderScope.connector(key: Any) =
			rectFill(key = key,
			         bounds = Bounds(50.0, 37.0, 25.0, 3.0),
			         color = Color.raw("#444444"))

	private fun WCRenderScope.barrel(key: Any) =
			rectFill(key = key,
			         bounds = Bounds(95.0, 30.0, 35.0, 5.0),
			         color = Color.raw("#444444"))

	private fun WCRenderScope.turret(key: Any) =
			pathFill(key = key,
			         path = Path.closed(Vector(30.0, 37.0), Vector(100.0, 37.0), Vector(95.0, 25.0), Vector(40.0, 25.0)),
			         color = Color.raw("#576601"))

	private fun WCRenderScope.text102(key: Any) =
			text(key = key,
			     text = "102",
			     font = "12px Arial",
			     position = Vector(45.0, 34.0),
			     color = Color.raw("white"))

	private fun WCRenderScope.textKarol(key: Any) =
			text(key = key,
			     text = "Karol",
			     font = "12px Arial",
			     position = Vector(45.0, 50.0),
			     color = Color.raw("white"))
}

fun WCRenderScope.tank(key: Any = AutoKey) = component(::Tank, BasicProps(key))
