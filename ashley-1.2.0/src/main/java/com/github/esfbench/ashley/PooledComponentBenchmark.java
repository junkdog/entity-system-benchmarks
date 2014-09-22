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

package com.github.esfbench.ashley;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.PooledEngine;
import com.github.esfbench.JmhSettings;
import com.github.esfbench.ashley.component.PlainPosition;
import com.github.esfbench.ashley.component.PlainStructComponentA;
import com.github.esfbench.ashley.system.PlainPositionSystem;
import com.github.esfbench.ashley.system.PlainPositionSystem2;
import com.github.esfbench.ashley.system.PlainPositionSystem3;
import com.github.esfbench.ashley.system.PooledEntityDeleterSystem;

public class PooledComponentBenchmark extends JmhSettings {
	
	private Engine engine;

	@Setup
	public void init() {
		engine = new PooledEngine();
		engine.addSystem(new PlainPositionSystem());
		engine.addSystem(new PlainPositionSystem2());
		engine.addSystem(new PlainPositionSystem3());
		engine.addSystem(new PooledEntityDeleterSystem(JmhSettings.SEED, entityCount, PlainPosition.class, PlainStructComponentA.class));
	}		
	
	@Benchmark
	public void pooled() {
		engine.update(0);
	}
	
	public static void main(String[] args) throws Exception {
		new Runner(
			new OptionsBuilder()
				.include(".*plain.*")
				.param("entityCount", "1024", "4096")
				.build())
		.run();
	}
}
