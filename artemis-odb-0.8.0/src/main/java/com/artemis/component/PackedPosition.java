package com.artemis.component;

import com.artemis.Component;

public class PackedPosition extends Component
{
	public float x;
	public float y;

	public PackedPosition xy(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
}
