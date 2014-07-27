package com.artemis.component;

import com.artemis.Component;

public class PooledStructComponentA implements Component {
	public float x, y, z;
	public short something;
	public boolean flag;
	
	public PooledStructComponentA setXyz(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}

	@Override
	public String toString() {
		return "StructComponentA [x=" + x + ", y=" + y + ", z=" + z + ", something=" + something + ", flag=" + flag +
			"]";
	}

	@Override
	public void reset() {
		x = 0;
		y = 0;
		z = 0;
		something = 0;
		flag = false;
	}
}
