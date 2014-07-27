package com.artemis.component;

import com.artemis.Component;
import com.github.esfbench.util.Vec2f;

public class PooledPosition implements Component
{
	public float x;
	public float y;

	public PooledPosition xy(float x, float y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public PooledPosition add(Vec2f vec)
	{
		this.x += vec.x;
		this.y += vec.y;
		return this;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}

	@Override
	public void reset() {
		x = 0;
		y = 0;
	}
}
