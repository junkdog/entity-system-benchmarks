package com.artemis.component;

import com.artemis.Component;
import com.artemis.annotations.PackedWeaver;

//@PackedWeaver
public class PlainPosition extends Component {
	public float x;
	public float y;

	public PlainPosition xy(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
}
