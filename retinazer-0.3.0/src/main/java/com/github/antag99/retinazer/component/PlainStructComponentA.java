package com.github.antag99.retinazer.component;

import com.github.antag99.retinazer.Component;

public class PlainStructComponentA implements Component {
	public float x, y, z;
	public short something;
	public boolean flag;
	
	public PlainStructComponentA setXyz(float x, float y, float z) {
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
}
