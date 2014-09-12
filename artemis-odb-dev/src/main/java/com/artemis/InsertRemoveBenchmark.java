/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.artemis;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.artemis.component.Comp1;
import com.artemis.component.PComp1;
import com.artemis.system.CompSystemA;
import com.artemis.system.CompSystemB;
import com.artemis.system.CompSystemC;
import com.artemis.system.CompSystemD;
import com.artemis.system.EntityManglerSystem;
import com.github.esfbench.JmhSettings;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 10, timeUnit = TimeUnit.SECONDS)
public class InsertRemoveBenchmark extends JmhSettings {
	
	private World world;
	private Constructor<Comp1> constructor;
	private MethodHandle constructorHandle;
	private MethodHandle constructorHandlePacked;
	private Constructor<PComp1> constructorPacked;
	private MethodHandle constructorUnreflectHandle;
	private MethodHandle constructorHandleUnreflectPacked;
	
	@Setup
	public void init() throws Exception{
		
		world = new World(new WorldConfiguration()
			.maxRebuiltIndicesPerTick(0));
		world.setSystem(new EntityManglerSystem(SEED, entityCount, 20));
		world.setSystem(new CompSystemA());
		world.setSystem(new CompSystemB());
		world.setSystem(new CompSystemC());
		world.setSystem(new CompSystemD());
		world.initialize();
		
		constructor = Comp1.class.getConstructor();
		constructorPacked = PComp1.class.getConstructor(World.class);
		constructorHandle = MethodHandles.lookup().findConstructor(Comp1.class, MethodType.methodType(void.class));
		constructorUnreflectHandle = MethodHandles.lookup().unreflectConstructor(constructor);
		constructorHandlePacked = MethodHandles.lookup().findConstructor(PComp1.class, MethodType.methodType(void.class, World.class));
		constructorHandleUnreflectPacked = MethodHandles.lookup().unreflectConstructor(constructorPacked);
	}		
	
	@Benchmark
	public void insert_remove_world() {
		world.process();
	}
	
	@Benchmark
	public Comp1 create_reflect() throws Throwable {
		return constructor.newInstance();
	}
	
	@Benchmark
	public Comp1 create_plain() throws Throwable {
		return new Comp1();
	}
	
	@Benchmark
	public Comp1 create_handle() throws Throwable {
		return (Comp1)constructorHandle.invokeExact();
	}
	
	@Benchmark
	public Comp1 create_handle_unreflect() throws Throwable {
		return (Comp1)constructorUnreflectHandle.invokeExact();
	}
	
	@Benchmark
	public PComp1 create_reflect_packed() throws Throwable {
		return constructorPacked.newInstance(world);
	}
	
	@Benchmark
	public PComp1 create_handle_packed() throws Throwable {
		return (PComp1)constructorHandlePacked.invokeExact(world);
	}
	
	@Benchmark
	public PComp1 create_handle_unreflect_packed() throws Throwable {
		return (PComp1)constructorHandleUnreflectPacked.invokeExact(world);
	}
	
	public static void main(String[] args) throws RunnerException {
		new Runner(
			new OptionsBuilder()
//				.include(".*insert_remove.*")
				.include(".*create_plain.*")
//				.param("entityCount", "1024", "4096")
				.param("entityCount", "1024")
				.build())
		.run();
		
//		new Scanner(System.in).nextLine();
//		
//		InsertRemoveBenchmark irb = new InsertRemoveBenchmark();
//		irb.entityCount = 1024;
//		irb.init();
//		for (int i = 0, s = 0xffffff; s > i; i++) {
//			irb.insert_remove_world();
//		}
	}
}
