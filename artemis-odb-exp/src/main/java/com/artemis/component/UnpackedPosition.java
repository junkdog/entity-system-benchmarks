package com.artemis.component;

import java.nio.ByteBuffer;
import java.util.IdentityHashMap;
import java.util.Map;

import com.artemis.Entity;
import com.artemis.PackedComponent;
import com.artemis.PackedComponent.DisposedWithWorld;
import com.artemis.World;
import com.artemis.utils.Bag;

public class UnpackedPosition extends PackedComponent implements
		DisposedWithWorld {

	private int $stride;
	private static final int $_SIZE_OF = 8;
	private static Map<World, Bag<UnpackedPosition>> $store = new IdentityHashMap<World, Bag<UnpackedPosition>>();
	private ByteBuffer $data = null;
	private World $world;
	
	public UnpackedPosition(World world) {
		this.$world = world;
		Bag<UnpackedPosition> instances = $store.get(world);
		if (instances != null) {
			$data = instances.get(0).$data;
		} else {
			$data = ByteBuffer.allocateDirect(128 * $_SIZE_OF);
			
			instances = new Bag<UnpackedPosition>();
			$store.put(world, instances);
		}
		
		instances.add(this);
	}

	@Override
	protected void forEntity(Entity e) {
		this.$stride = $_SIZE_OF * e.getId();
	}

	@Override
	protected void reset() {
		$data.putFloat($stride + 0, 0);
		$data.putFloat($stride + 4, 0);
	}
	
	private void $grow()
	{
		int $size = $data.capacity();
		
		ByteBuffer newBuffer = ByteBuffer.allocateDirect($size * 2);
		for (int i = 0, s = $size; s > i; i++) {
			newBuffer.put(i, $data.get(i));
		}
		
		Bag<UnpackedPosition> instances = $store.get($world);
		for (int i = 0, s = instances.size(); s > i; i++) {
			UnpackedPosition obj = instances.get(i);
			obj.$data = newBuffer;
		}
	}
	
	@Override
	public void free(World world) {
		$store.remove(world);
	}
	
	public float x() {
		return $data.getFloat($stride + 0);
	}
	
	public float y() {
		return $data.getFloat($stride + 4);
	}
	
	public UnpackedPosition x(float value) {
		$data.putFloat($stride + 0, value);
		return this;
	}
	
	public void y(float value) {
		$data.putFloat($stride + 4, value);
	}

	@Override
	protected void ensureCapacity(int id) {
		if (($data.capacity() - $_SIZE_OF) <= (id * $_SIZE_OF)) $grow();
	}
}
