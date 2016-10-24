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

package com.github.esfbench.dustArtemis;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.artemis.Aspect;
import com.artemis.World;
import com.github.esfbench.JmhSettings;
import com.github.esfbench.dustArtemis.component.PlainPosition;
import com.github.esfbench.dustArtemis.component.PlainStructComponentA;
import com.github.esfbench.dustArtemis.system.BaselinePositionSystem;
import com.github.esfbench.dustArtemis.system.BaselinePositionSystem2;
import com.github.esfbench.dustArtemis.system.BaselinePositionSystem3;
import com.github.esfbench.dustArtemis.system.EntityDeleterSystem;

public class BaselineBenchmark extends JmhSettings
{

	private World world;

	@Setup
	public void init ()
	{
		// Pass the config file with reasonable size for the benchmark.
		CfgUtils.setDustArtemisConfig( entityCount );

		world = World.builder()
				.componentTypes( PlainPosition.class, PlainStructComponentA.class )
				.observer( BaselinePositionSystem::new, Aspect.all( PlainPosition.class ) )
				.observer( BaselinePositionSystem2::new, Aspect.all( PlainPosition.class ) )
				.observer( BaselinePositionSystem3::new, Aspect.all( PlainPosition.class ) )
				.observer( () -> new EntityDeleterSystem( SEED, entityCount ) )
				.build();
	}

	@Benchmark
	public void baseline ()
	{
		world.process();
	}

	public static void main ( String[] args ) throws Exception
	{
		new Runner(
				new OptionsBuilder()
						.include( BaselineBenchmark.class.getName() + ".*" )
						.param( "entityCount", "1024", "4096" )
						.build() )
								.run();
	}
}
