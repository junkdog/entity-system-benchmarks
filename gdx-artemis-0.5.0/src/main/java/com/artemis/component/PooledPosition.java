package com.artemis.component;

import com.artemis.Component;

public class PooledPosition implements Component {
	public float x;
	public float y;

	public PooledPosition xy(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

	@Override
	public void reset() {
		x = 0; y = 0;
	}
}
